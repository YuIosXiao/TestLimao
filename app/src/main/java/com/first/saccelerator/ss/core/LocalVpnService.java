package com.first.saccelerator.ss.core;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.Handler;
import android.os.ParcelFileDescriptor;

import com.first.saccelerator.App;
import com.first.saccelerator.R;
import com.first.saccelerator.activity.MainActivitySecond;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.VPNAllowedDataSource;
import com.first.saccelerator.model.VPNAllowedResponse;
import com.first.saccelerator.ss.core.ProxyConfig.IPAddress;
import com.first.saccelerator.ss.dns.DnsPacket;
import com.first.saccelerator.ss.tcpip.CommonMethods;
import com.first.saccelerator.ss.tcpip.IPHeader;
import com.first.saccelerator.ss.tcpip.TCPHeader;
import com.first.saccelerator.ss.tcpip.UDPHeader;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * VPN Service
 */
public class LocalVpnService extends VpnService implements Runnable {

    public static LocalVpnService Instance;
    public static String ProxyUrl; // ss://method:password@host:port
    public static boolean IsRunning = false;

    private static int ID;
    private static int LOCAL_IP;
    private static ConcurrentHashMap<onStatusChangedListener, Object> m_OnStatusChangedListeners = new ConcurrentHashMap<onStatusChangedListener, Object>();

    private Thread m_VPNThread;
    private ParcelFileDescriptor m_VPNInterface;
    private TcpProxyServer m_TcpProxyServer;
    private DnsProxy m_DnsProxy;
    private FileOutputStream m_VPNOutputStream;

    private byte[] m_Packet;
    private IPHeader m_IPHeader;
    private TCPHeader m_TCPHeader;
    private UDPHeader m_UDPHeader;
    private ByteBuffer m_DNSBuffer;
    private Handler m_Handler;
    private long m_SentBytes;
    private long m_ReceivedBytes;

    public LocalVpnService() {
        ID++;
        m_Handler = new Handler();
        m_Packet = new byte[20000];
        m_IPHeader = new IPHeader(m_Packet, 0);
        m_TCPHeader = new TCPHeader(m_Packet, 20);
        m_UDPHeader = new UDPHeader(m_Packet, 20);
        m_DNSBuffer = ((ByteBuffer) ByteBuffer.wrap(m_Packet).position(28)).slice();
        Instance = this;

        System.out.printf("New VPNService(%d)\n", ID);
    }

    @Override
    public void onCreate() {
        System.out.printf("VPNService(%s) created.\n", ID);
        // Start a new session by creating a new thread.
        m_VPNThread = new Thread(this, "VPNServiceThread");
        m_VPNThread.start();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IsRunning = true;
        return super.onStartCommand(intent, flags, startId);
    }

    public interface onStatusChangedListener {
        public void onStatusChanged(String status, Boolean isRunning);

        public void onLogReceived(String logString);
    }

    public static void addOnStatusChangedListener(onStatusChangedListener listener) {
        if (!m_OnStatusChangedListeners.containsKey(listener)) {
            m_OnStatusChangedListeners.put(listener, 1);
        }
    }

    public static void removeOnStatusChangedListener(onStatusChangedListener listener) {
        if (m_OnStatusChangedListeners.containsKey(listener)) {
            m_OnStatusChangedListeners.remove(listener);
        }
    }

    private void onStatusChanged(final String status, final boolean isRunning) {
        m_Handler.post(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<onStatusChangedListener, Object> entry : m_OnStatusChangedListeners.entrySet()) {
                    entry.getKey().onStatusChanged(status, isRunning);
                }
            }
        });
    }

    public void writeLog(final String format, Object... args) {
        final String logString = String.format(format, args);
        m_Handler.post(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<onStatusChangedListener, Object> entry : m_OnStatusChangedListeners.entrySet()) {
                    entry.getKey().onLogReceived(logString);
                }
            }
        });
    }

    public void sendUDPPacket(IPHeader ipHeader, UDPHeader udpHeader) {
        try {
            CommonMethods.ComputeUDPChecksum(ipHeader, udpHeader);
            this.m_VPNOutputStream.write(ipHeader.m_Data, ipHeader.m_Offset, ipHeader.getTotalLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getAppInstallID() {
        SharedPreferences preferences = getSharedPreferences("SmartProxy", MODE_PRIVATE);
        String appInstallID = preferences.getString("AppInstallID", null);
        if (appInstallID == null || appInstallID.isEmpty()) {
            appInstallID = UUID.randomUUID().toString();
            Editor editor = preferences.edit();
            editor.putString("AppInstallID", appInstallID);
            editor.commit();
        }
        return appInstallID;
    }

    String getVersionName() {
        try {
            PackageManager packageManager = getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (Exception e) {
            return "0.0";
        }
    }

    @Override
    public synchronized void run() {
        try {
            System.out.printf("VPNService(%s) work thread is runing...\n", ID);

            ProxyConfig.AppInstallID = getAppInstallID();//获取安装ID
            ProxyConfig.AppVersion = getVersionName();//获取版本号
//            System.out.printf("AppInstallID: %s\n", ProxyConfig.AppInstallID);
            writeLog("Android version: %s", Build.VERSION.RELEASE);
            writeLog("App version: %s", ProxyConfig.AppVersion);

//            File ipfile = new File(getExternalFilesDir(null) + File.separator + "foreign_ip_list2.txt");
//            if (ipfile.exists()) {
//                ChinaIpMaskManager.loadFromPath(getExternalFilesDir(null) + File.separator + "foreign_ip_list2.txt");//加载国外的IP段，用于IP分流。
//            } else {
//                ChinaIpMaskManager.loadFromRAW(getResources().openRawResource(R.raw.foreigniplist));
//            }
            waitUntilPreapred();//检查是否准备完毕。
            writeLog("Load config from file ...");
            try {
                File domainfile = null;
                if (App.isDomestic()) {//在国内
                    StaticStateUtils.configName = StaticStateUtils.usesFilePath_d2o;
                    domainfile = new File(StaticStateUtils.usesFilePath_d2o);
                } else {//在国外
                    StaticStateUtils.configName = StaticStateUtils.usesFilePath_o2d;
                    domainfile = new File(StaticStateUtils.usesFilePath_o2d);
                }
                if (domainfile.exists()) {
                    ProxyConfig.Instance.loadFromUrl(StaticStateUtils.configName);
                } else {
                    if (App.isDomestic()) {
                        ProxyConfig.Instance.loadFromFile(getResources().openRawResource(R.raw.androidd2o));
                    } else {
                        ProxyConfig.Instance.loadFromFile(getResources().openRawResource(R.raw.androido2d));
                    }
                }
//                ProxyConfig.Instance.loadFromUrl(getExternalFilesDir(null) + File.separator + StaticStateUtils.configName);
//                ProxyConfig.Instance.loadFromFile(getResources().openRawResource(R.raw.config));
                writeLog("Load done");
            } catch (Exception e) {
                String errString = e.getMessage();
                if (errString == null || errString.isEmpty()) {
                    errString = e.toString();
                }
                writeLog("Load failed with error: %s", errString);
            }

            m_TcpProxyServer = new TcpProxyServer(0);
            m_TcpProxyServer.start();
            writeLog("LocalTcpServer started.");

            m_DnsProxy = new DnsProxy();
            m_DnsProxy.start();
            writeLog("LocalDnsProxy started.");

            while (true) {
                if (IsRunning) {
                    //加载配置文件

                    writeLog("set shadowsocks/(http proxy)");
                    try {
                        ProxyConfig.Instance.m_ProxyList.clear();
                        ProxyConfig.Instance.addProxyToList(ProxyUrl);
                        writeLog("Proxy is: %s", ProxyConfig.Instance.getDefaultProxy());
                    } catch (Exception e) {

                        String errString = e.getMessage();
                        if (errString == null || errString.isEmpty()) {
                            errString = e.toString();
                        }
                        IsRunning = false;
                        onStatusChanged(errString, false);
                        continue;
                    }
                    String welcomeInfoString = ProxyConfig.Instance.getWelcomeInfo();
                    if (welcomeInfoString != null && !welcomeInfoString.isEmpty()) {
                        writeLog("%s", ProxyConfig.Instance.getWelcomeInfo());
                    }
                    writeLog("Global mode is " + (ProxyConfig.Instance.globalMode ? "on" : "off"));

                    runVPN();
                } else {
                    Thread.sleep(100);
                    dispose();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (Exception e) {
            e.printStackTrace();
            writeLog("Fatal error: %s", e.toString());
        } finally {
            writeLog("App terminated.");
            dispose();
        }
    }

    private void runVPN() throws Exception {
        SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
        boolean vpntogglebutton = sputilsconfig.getBoolean(SPConstants.CONFIG.VPNTOGGLEBUTTON, false);
        if (vpntogglebutton) {//表示VPN程序智能开关 开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                List<VPNAllowedResponse> vpnallowedresponselist = new ArrayList<>();
                VPNAllowedDataSource dataSource = new VPNAllowedDataSource(new DBHelper(App.getApplication()));
                vpnallowedresponselist = dataSource.findVPNAllow();
                dataSource.close();
                this.m_VPNInterface = establishVPN1(vpnallowedresponselist);
            } else {
                this.m_VPNInterface = establishVPN();
            }
        } else {
            this.m_VPNInterface = establishVPN();
        }


        this.m_VPNOutputStream = new FileOutputStream(m_VPNInterface.getFileDescriptor());
        FileInputStream in = new FileInputStream(m_VPNInterface.getFileDescriptor());
        int size = 0;
        while (size != -1 && IsRunning) {
            while ((size = in.read(m_Packet)) > 0 && IsRunning) {
                if (m_DnsProxy.Stopped || m_TcpProxyServer.Stopped) {
                    in.close();
                    throw new Exception("LocalServer stopped.");
                }
                onIPPacketReceived(m_IPHeader, size);
            }
            Thread.sleep(100);
        }
        in.close();
        disconnectVPN();
    }

    private void showLog(IPHeader ipHeader, String message) {
        System.out.println("YKZZLDX" + message);
    }

    void onIPPacketReceived(IPHeader ipHeader, int size) throws IOException {
//        System.out.println("****************  YKZZLDX  *******************");
        switch (ipHeader.getProtocol()) {
            case IPHeader.TCP:
//                System.out.println("YKZZLDX-- TCP DestinationIP: " + CommonMethods.ipIntToString(ipHeader.getDestinationIP()) + "---" + ipHeader.getDestinationIP() +
//                        " ,LOCAL_IP: " + CommonMethods.ipIntToString(LOCAL_IP) + " ,SourceIP: " + CommonMethods.ipIntToString(ipHeader.getSourceIP()) + "---" + ipHeader.getSourceIP());

//                if (LOCAL_IP != ipHeader.getSourceIP()) {
//                    LOCAL_IP = ipHeader.getSourceIP();
//                }

//                LogUtils.i("ipHeader+++++>" + "DestinationIP:" + ipHeader.getDestinationIP() + "," + CommonMethods.ipIntToString(ipHeader.getDestinationIP())
//                        + "===LOCAL_IP:" + LOCAL_IP + "," + CommonMethods.ipIntToString(LOCAL_IP) + "===SourceIP:" + ipHeader.getSourceIP() + "," + CommonMethods.ipIntToString(ipHeader.getSourceIP()));


                TCPHeader tcpHeader = m_TCPHeader;
                tcpHeader.m_Offset = ipHeader.getHeaderLength();
                if (ipHeader.getSourceIP() == LOCAL_IP) {
//                    System.out.println("YKZZLDX--  SourcePort==m_TcpProxyServer.Port ==> " + tcpHeader.getSourcePort() + " : " + m_TcpProxyServer.Port);
                    if (tcpHeader.getSourcePort() == m_TcpProxyServer.Port) {// 收到本地TCP服务器数据
//                        System.out.println("YKZZLDX--3333333");
                        NatSession session = NatSessionManager.getSession(tcpHeader.getDestinationPort());
                        if (session != null) {
//                            System.out.println("YKZZLDX--  RemoteHost : " + session.RemoteHost);
                            ipHeader.setSourceIP(ipHeader.getDestinationIP());
                            tcpHeader.setSourcePort(session.RemotePort);
                            ipHeader.setDestinationIP(LOCAL_IP);

                            CommonMethods.ComputeTCPChecksum(ipHeader, tcpHeader);
                            m_VPNOutputStream.write(ipHeader.m_Data, ipHeader.m_Offset, size);
                            m_ReceivedBytes += size;
                        } else {
//                            System.out.printf("YKZZLDX--  NoSession: %s %s\n", ipHeader.toString(), tcpHeader.toString());
                        }
                    } else {
//                        System.out.println("YKZZLDX--66666666666");

                        // 添加端口映射
                        int portKey = tcpHeader.getSourcePort();
                        NatSession session = NatSessionManager.getSession(portKey);
                        if (session == null || session.RemoteIP != ipHeader.getDestinationIP() || session.RemotePort != tcpHeader.getDestinationPort()) {
//                            System.out.println("YKZZLDX--77");

                            session = NatSessionManager.createSession(portKey, ipHeader.getDestinationIP(), tcpHeader.getDestinationPort());
                        }

                        session.LastNanoTime = System.nanoTime();
                        session.PacketSent++;//注意顺序

                        int tcpDataSize = ipHeader.getDataLength() - tcpHeader.getHeaderLength();
                        if (session.PacketSent == 2 && tcpDataSize == 0) {
//                            System.out.println("YKZZLDX--88888888888888888888888888888888888888888888888888888888888888");

                            return;//丢弃tcp握手的第二个ACK报文。因为客户端发数据的时候也会带上ACK，这样可以在服务器Accept之前分析出HOST信息。
                        }

                        //分析数据，找到host
                        if (session.BytesSent == 0 && tcpDataSize > 10) {
//                            System.out.println("YKZZLDX--99999");

                            int dataOffset = tcpHeader.m_Offset + tcpHeader.getHeaderLength();
                            String host = HttpHostHeaderParser.parseHost(tcpHeader.m_Data, dataOffset, tcpDataSize);
                            if (host != null) {
//                                System.out.println("YKZZLDX--10101010");

                                session.RemoteHost = host;
                            } else {
//                                System.out.println("YKZZLDX--11 11 11 11 11");

//                                System.out.printf("No host name found: %s", session.RemoteHost);
                            }
                        }

                        // 转发给本地TCP服务器
                        ipHeader.setSourceIP(ipHeader.getDestinationIP());
                        ipHeader.setDestinationIP(LOCAL_IP);
                        tcpHeader.setDestinationPort(m_TcpProxyServer.Port);

                        CommonMethods.ComputeTCPChecksum(ipHeader, tcpHeader);
                        m_VPNOutputStream.write(ipHeader.m_Data, ipHeader.m_Offset, size);
                        session.BytesSent += tcpDataSize;//注意顺序
                        m_SentBytes += size;
//                        System.out.println("YKZZLDX--0000 0000 0000 0000");

                    }
                }
                break;
            case IPHeader.UDP:
                // 转发DNS数据包：
//                System.out.println("YKZZLDX--udp");

                UDPHeader udpHeader = m_UDPHeader;
                udpHeader.m_Offset = ipHeader.getHeaderLength();
                if (ipHeader.getSourceIP() == LOCAL_IP && udpHeader.getDestinationPort() == 53) {
                    m_DNSBuffer.clear();
                    m_DNSBuffer.limit(ipHeader.getDataLength() - 8);
                    DnsPacket dnsPacket = DnsPacket.FromBytes(m_DNSBuffer);
                    if (dnsPacket != null && dnsPacket.Header.QuestionCount > 0) {
                        m_DnsProxy.onDnsRequestReceived(ipHeader, udpHeader, dnsPacket);
                    }
                }
                break;
        }
    }

    private void waitUntilPreapred() {
        while (prepare(this) != null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ParcelFileDescriptor establishVPN() throws Exception {
        Builder builder = new Builder();
        builder.setMtu(ProxyConfig.Instance.getMTU());
//        if (ProxyConfig.IS_DEBUG)
//            System.out.printf("establishVPN setMtu: %d\n", ProxyConfig.Instance.getMTU());

        IPAddress ipAddress = ProxyConfig.Instance.getDefaultLocalIP();
        LOCAL_IP = CommonMethods.ipStringToInt(ipAddress.Address);
        builder.addAddress(ipAddress.Address, ipAddress.PrefixLength);
//        if (ProxyConfig.IS_DEBUG)
//            System.out.printf("establishVPN addAddress: %s/%d\n", ipAddress.Address, ipAddress.PrefixLength);

        for (IPAddress dns : ProxyConfig.Instance.getDnsList()) {
            builder.addDnsServer(dns.Address);
//            if (ProxyConfig.IS_DEBUG)
//                System.out.printf("establishVPN addDnsServer: %s\n", dns.Address);
        }

        if (ProxyConfig.Instance.getRouteList().size() > 0) {
            for (IPAddress routeAddress : ProxyConfig.Instance.getRouteList()) {
                builder.addRoute(routeAddress.Address, routeAddress.PrefixLength);
//                if (ProxyConfig.IS_DEBUG)
//                    System.out.printf("establishVPN addRoute: %s/%d\n", routeAddress.Address, routeAddress.PrefixLength);
            }
            builder.addRoute(CommonMethods.ipIntToString(ProxyConfig.FAKE_NETWORK_IP), 16);

//            if (ProxyConfig.IS_DEBUG)
//                System.out.printf("establishVPN addRoute for FAKE_NETWORK: %s/%d\n", CommonMethods.ipIntToString(ProxyConfig.FAKE_NETWORK_IP), 16);
        } else {
            builder.addRoute("0.0.0.0", 0);
//            if (ProxyConfig.IS_DEBUG)
//                System.out.printf("establishVPN addDefaultRoute: 0.0.0.0/0\n");
        }


        Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
        Method method = SystemProperties.getMethod("get", new Class[]{String.class});
        ArrayList<String> servers = new ArrayList<String>();
        for (String name : new String[]{"net.dns1", "net.dns2", "net.dns3", "net.dns4",}) {
            String value = (String) method.invoke(null, name);
            if (value != null && !"".equals(value) && !servers.contains(value)) {
                if (value.indexOf(".") == -1) {
                    servers.add("172.25.82.1");
                    builder.addRoute("172.25.82.1", 32);
                } else {
                    servers.add(value);
                    builder.addRoute(value, 32);
                }
//                if (ProxyConfig.IS_DEBUG)
//                    System.out.printf("%s=%s\n", name, value);
            }
        }

//        Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(this, MainActivitySecond.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setConfigureIntent(pendingIntent);

        builder.setSession(ProxyConfig.Instance.getSessionName());
        ParcelFileDescriptor pfdDescriptor = builder.establish();
        onStatusChanged(ProxyConfig.Instance.getSessionName() + getString(R.string.vpn_connected_status), true);
        return pfdDescriptor;
    }

    /**
     * 根据用户选择的列表，实现选择应用经过VPN应用
     *
     * @param vpnallowedresponselist
     * @return
     * @throws Exception
     */
    private ParcelFileDescriptor establishVPN1(List<VPNAllowedResponse> vpnallowedresponselist) throws Exception {
        StaticStateUtils.getDynamicAndRescue();

        Builder builder = new Builder();
        builder.setMtu(ProxyConfig.Instance.getMTU());
        IPAddress ipAddress = ProxyConfig.Instance.getDefaultLocalIP();
        LOCAL_IP = CommonMethods.ipStringToInt(ipAddress.Address);
        builder.addAddress(ipAddress.Address, ipAddress.PrefixLength);
        builder.addRoute("0.0.0.0", 0);
        Intent intent = new Intent(this, MainActivitySecond.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setConfigureIntent(pendingIntent);
        builder.setSession(ProxyConfig.Instance.getSessionName());

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //不允许通过VPN的应用
//            builder.addDisallowedApplication("com.first.saccelerator");

                //允许通过VPN的应用
                builder.addAllowedApplication("com.first.saccelerator");//当选择应用通过的时候，必须要有本程序的应用
                if (vpnallowedresponselist.size() > 0) {
                    for (int i = 0; i < vpnallowedresponselist.size(); i++) {
                        builder.addAllowedApplication(vpnallowedresponselist.get(i).getPackageName());
                    }
                }
//            builder.addAllowedApplication("com.google.android.youtube");
//            builder.addAllowedApplication("com.android.vending");
//            builder.addAllowedApplication("com.facebook.katana");
//            builder.addAllowedApplication("com.browser2345");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("----->" + e.getMessage());
        }

        ParcelFileDescriptor pfdDescriptor = builder.establish();
        onStatusChanged(ProxyConfig.Instance.getSessionName() + getString(R.string.vpn_connected_status), true);
        return pfdDescriptor;
    }


    public void disconnectVPN() {
        try {
            if (m_VPNInterface != null) {
                m_VPNInterface.close();
                m_VPNInterface = null;
            }
        } catch (Exception e) {
            // ignore
        }
        onStatusChanged(ProxyConfig.Instance.getSessionName() + getString(R.string.vpn_disconnected_status), false);
        this.m_VPNOutputStream = null;
    }

    private synchronized void dispose() {
        // 断开VPN
        disconnectVPN();

        // 停止TcpServer
        if (m_TcpProxyServer != null) {

            m_TcpProxyServer.handler.removeCallbacksAndMessages(null);//移除所有的callbacks和messages，这样就有效的避免了由Handler引起的内存溢出

            m_TcpProxyServer.stop();
            m_TcpProxyServer = null;
            writeLog("LocalTcpServer stopped.");
        }

        // 停止DNS解析器
        if (m_DnsProxy != null) {
            m_DnsProxy.stop();
            m_DnsProxy = null;
            writeLog("LocalDnsProxy stopped.");
        }

        stopSelf();
        IsRunning = false;

        onDestroy();
//        System.exit(0);
    }

    @Override
    public void onDestroy() {
        System.out.printf("VPNService(%s) destoried.\n", ID);
        if (m_VPNThread != null) {
            m_VPNThread.interrupt();
        }
    }

}
