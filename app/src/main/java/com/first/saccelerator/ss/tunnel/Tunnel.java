package com.first.saccelerator.ss.tunnel;

import android.annotation.SuppressLint;

import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.UserhttpheadDataSource;
import com.first.saccelerator.model.UserAgentResponse;
import com.first.saccelerator.model.UserAgentUtils;
import com.first.saccelerator.model.UserhttpheadResponse;
import com.first.saccelerator.ss.core.LocalVpnService;
import com.first.saccelerator.ss.core.ProxyConfig;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.ConvertUtils;
import com.first.saccelerator.utils.DateUtil;
import com.first.saccelerator.utils.DeviceUtils;
import com.first.saccelerator.utils.NetworkUtils;
import com.first.saccelerator.utils.PhoneUtils;
import com.first.saccelerator.utils.PracticalUtil;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.ScreenUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public abstract class Tunnel {

    final static ByteBuffer GL_BUFFER = ByteBuffer.allocateDirect(20000);
    public static long SessionCount;

    protected abstract void onConnected(ByteBuffer buffer) throws Exception;

    protected abstract boolean isTunnelEstablished();

    protected abstract void beforeSend(ByteBuffer buffer) throws Exception;

    protected abstract void afterReceived(ByteBuffer buffer) throws Exception;

    protected abstract void onDispose();

    private SocketChannel m_InnerChannel;
    private ByteBuffer m_SendRemainBuffer;
    private Selector m_Selector;
    private Tunnel m_BrotherTunnel;
    private boolean m_Disposed;
    private InetSocketAddress m_ServerEP;
    protected InetSocketAddress m_DestAddress;

    public Tunnel(SocketChannel innerChannel, Selector selector) {
        this.m_InnerChannel = innerChannel;
        this.m_Selector = selector;
        SessionCount++;
    }

    public Tunnel(InetSocketAddress serverAddress, Selector selector) throws IOException {
        SocketChannel innerChannel = SocketChannel.open();
        innerChannel.configureBlocking(false);
        this.m_InnerChannel = innerChannel;
        this.m_Selector = selector;
        this.m_ServerEP = serverAddress;
        SessionCount++;
    }

    public void setBrotherTunnel(Tunnel brotherTunnel) {
        m_BrotherTunnel = brotherTunnel;
    }

    public void connect(InetSocketAddress destAddress) throws Exception {
        if (LocalVpnService.Instance.protect(m_InnerChannel.socket())) {// 保护socket不走vpn
            m_DestAddress = destAddress;
            m_InnerChannel.register(m_Selector, SelectionKey.OP_CONNECT, this);//注册连接事件
            m_InnerChannel.connect(m_ServerEP);//连接目标
        } else {
            throw new Exception("VPN protect socket failed.");
        }
    }

    protected void beginReceive() throws Exception {
        if (m_InnerChannel.isBlocking()) {
            m_InnerChannel.configureBlocking(false);
        }
        // 将channel注册到selector上
        m_InnerChannel.register(m_Selector, SelectionKey.OP_READ, this);//注册读事件
    }


    protected boolean write(ByteBuffer buffer, boolean copyRemainData) throws Exception {
        int bytesSent;
        while (buffer.hasRemaining()) {
            bytesSent = m_InnerChannel.write(buffer);
            if (bytesSent == 0) {
                break;//不能再发送了，终止循环
            }
        }

        if (buffer.hasRemaining()) {//数据没有发送完毕
            if (copyRemainData) {//拷贝剩余数据，然后侦听写入事件，待可写入时写入。
                //拷贝剩余数据
                if (m_SendRemainBuffer == null) {
                    m_SendRemainBuffer = ByteBuffer.allocateDirect(buffer.capacity());
                }
                m_SendRemainBuffer.clear();
                m_SendRemainBuffer.put(buffer);
                m_SendRemainBuffer.flip();
                m_InnerChannel.register(m_Selector, SelectionKey.OP_WRITE, this);//注册写事件
            }
            return false;
        } else {//发送完毕了
            return true;
        }
    }

    protected void onTunnelEstablished() throws Exception {
        this.beginReceive();//开始接收数据
        m_BrotherTunnel.beginReceive();//兄弟也开始收数据吧
    }

    @SuppressLint("DefaultLocale")
    public void onConnectable() {
        SPUtils SP_PROXY = new SPUtils(SPConstants.SP_PROXY);
        SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);

        try {
            if (m_InnerChannel.finishConnect()) {//连接成功
                onConnected(GL_BUFFER);//通知子类TCP已连接，子类可以根据协议实现握手等。
            } else {//连接失败
                LocalVpnService.Instance.writeLog("1111111111Error: connect to %s failed.", m_ServerEP);
                this.dispose();
                connectionFailedToJudgeSend(SP_PROXY, configSP);
            }
        } catch (Exception e) {
            LocalVpnService.Instance.writeLog("2222222Error: connect to %s failed: %s", m_ServerEP, e);
            this.dispose();
//            LogUtils.i("m_ServerEP--getAddress--->" + m_ServerEP.toString());
//            LogUtils.i("m_DestAddress--getHostString--->" + m_DestAddress.getHostString());
//            LogUtils.i("m_DestAddress--getHostName--->" + m_DestAddress.getHostName());
            connectionFailedToJudgeSend(SP_PROXY, configSP);
        }
    }


    public void onReadable(SelectionKey key) {
        try {
            ByteBuffer buffer = GL_BUFFER;
            buffer.clear();
            int bytesRead = m_InnerChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                afterReceived(buffer);//先让子类处理，例如解密数据。

                boolean enabled = sp_userhttphead.getBoolean(SPConstants.USERHTTPHEAD.enabled, true);
                if (enabled) {
                    userHttpHeadObtain(buffer, bytesRead);
                }

                if (isTunnelEstablished() && buffer.hasRemaining()) {//将读到的数据，转发给兄弟。
                    m_BrotherTunnel.beforeSend(buffer);//发送之前，先让子类处理，例如做加密等。
                    if (!m_BrotherTunnel.write(buffer, true)) {
                        key.cancel();//兄弟吃不消，就取消读取事件。
                        if (ProxyConfig.IS_DEBUG)
                            System.out.printf("%s can not read more.\n", m_ServerEP);
                    }
                }
            } else if (bytesRead < 0) {
                this.dispose();//连接已关闭，释放资源。
            }
        } catch (Exception e) {
            e.printStackTrace();
//            LogUtils.i("onReadable----->" + e.getMessage());
            this.dispose();
        }
    }

    public void onWritable(SelectionKey key) {
        try {
            this.beforeSend(m_SendRemainBuffer);//发送之前，先让子类处理，例如做加密等。
            if (this.write(m_SendRemainBuffer, false)) {//如果剩余数据已经发送完毕
                key.cancel();//取消写事件。
                if (isTunnelEstablished()) {
                    m_BrotherTunnel.beginReceive();//这边数据发送完毕，通知兄弟可以收数据了。
                } else {
                    this.beginReceive();//开始接收代理服务器响应数据
                }
            }
        } catch (Exception e) {
            this.dispose();
        }
    }

    public void dispose() {
        disposeInternal(true);
    }

    void disposeInternal(boolean disposeBrother) {
        if (m_Disposed) {
            return;
        } else {
            try {
                m_InnerChannel.close();
            } catch (Exception e) {
            }

            if (m_BrotherTunnel != null && disposeBrother) {
                m_BrotherTunnel.disposeInternal(false);//把兄弟的资源也释放了。
            }

            m_InnerChannel = null;
            m_SendRemainBuffer = null;
            m_Selector = null;
            m_BrotherTunnel = null;
            m_Disposed = true;
            SessionCount--;

            onDispose();
        }
    }

    /**
     * 连接失败判断是否发送
     */
    public void connectionFailedToJudgeSend(SPUtils SP_PROXY, SPUtils configSP) {
        String FLOG_ALLOW_SEND = SP_PROXY.getString(SPConstants.PROXY.FLOG_ALLOW_SEND, "false");
        int FLOG_POOL_MAX_COUNT = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.FLOG_POOL_MAX_COUNT, "5"));
        int FLOG_POST_INTERVAL = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.FLOG_POST_INTERVAL, "60"));
        int FLOG_CLEAN_INTERVAL = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.FLOG_CLEAN_INTERVAL, "300"));

        String sourceaddress = configSP.getString(SPConstants.CONFIG.PROXY_NODE_URL, "");
        String targetaddress = m_DestAddress.getHostName();
        int FLOG_COUNTER = SP_PROXY.getInt(SPConstants.PROXY.FLOG_COUNTER, 0);
        String FLOG_INITIAL_IP = SP_PROXY.getString(SPConstants.PROXY.FLOG_INITIAL_IP, "");
//        if ("true".equals(FLOG_ALLOW_SEND)) {
        if ("禁止".equals(FLOG_ALLOW_SEND)) {
            if (StringUtils.isBlank(FLOG_INITIAL_IP)) {
                FLOG_COUNTER = 1;
                FLOG_INITIAL_IP = sourceaddress;
                SP_PROXY.put(SPConstants.PROXY.FLOG_COUNTER, FLOG_COUNTER);
                SP_PROXY.put(SPConstants.PROXY.FLOG_TARGETADDRESS, targetaddress);
                SP_PROXY.put(SPConstants.PROXY.FLOG_CREATTIME, System.currentTimeMillis() / 1000);
                SP_PROXY.put(SPConstants.PROXY.FLOG_INITIAL_IP, FLOG_INITIAL_IP);
                SP_PROXY.put(SPConstants.PROXY.FLOG_CLEAR_STARTTIME, System.currentTimeMillis());
            } else if (!StringUtils.isBlank(FLOG_INITIAL_IP) && !sourceaddress.equals(FLOG_INITIAL_IP)) {
                FLOG_COUNTER = 1;
                SP_PROXY.put(SPConstants.PROXY.FLOG_COUNTER, FLOG_COUNTER);
                SP_PROXY.put(SPConstants.PROXY.FLOG_TARGETADDRESS, targetaddress);
                SP_PROXY.put(SPConstants.PROXY.FLOG_CREATTIME, System.currentTimeMillis() / 1000);
                SP_PROXY.put(SPConstants.PROXY.FLOG_INITIAL_IP, FLOG_INITIAL_IP);
                SP_PROXY.put(SPConstants.PROXY.FLOG_CLEAR_STARTTIME, System.currentTimeMillis());
            } else if (sourceaddress.equals(FLOG_INITIAL_IP)) {
                FLOG_COUNTER++;
                SP_PROXY.put(SPConstants.PROXY.FLOG_COUNTER, FLOG_COUNTER);
                SP_PROXY.put(SPConstants.PROXY.FLOG_TARGETADDRESS, targetaddress);
                SP_PROXY.put(SPConstants.PROXY.FLOG_CREATTIME, System.currentTimeMillis() / 1000);
                SP_PROXY.put(SPConstants.PROXY.FLOG_INITIAL_IP, FLOG_INITIAL_IP);
            }


            long FLOG_CLEAR_STARTTIME = SP_PROXY.getLong(SPConstants.PROXY.FLOG_CLEAR_STARTTIME, 0);
            long FLOG_CLEAR_DIFFERENCE = System.currentTimeMillis() - FLOG_CLEAR_STARTTIME;

//            LogUtils.i("FLOG_CLEAR_DIFFERENCE----->" + ConvertUtils.Mymillis2FitTimeSpan(FLOG_CLEAR_DIFFERENCE));
            if (0 != FLOG_CLEAR_STARTTIME
                    && Integer.parseInt(ConvertUtils.Mymillis2FitTimeSpan(FLOG_CLEAR_DIFFERENCE)) > FLOG_CLEAN_INTERVAL
                    && FLOG_COUNTER < FLOG_POOL_MAX_COUNT) {
                FLOG_COUNTER = 0;
                SP_PROXY.put(SPConstants.PROXY.FLOG_COUNTER, FLOG_COUNTER);
                SP_PROXY.put(SPConstants.PROXY.FLOG_TARGETADDRESS, "");
                SP_PROXY.put(SPConstants.PROXY.FLOG_CREATTIME, "");
                SP_PROXY.put(SPConstants.PROXY.FLOG_INITIAL_IP, "");
                SP_PROXY.put(SPConstants.PROXY.FLOG_CLEAR_STARTTIME, System.currentTimeMillis());
            }

            long FLOG_SEND_STARTTIME = SP_PROXY.getLong(SPConstants.PROXY.FLOG_SEND_STARTTIME, 0);
            if (0 != FLOG_SEND_STARTTIME) {
                long FLOG_SEND_DIFFERENCE = System.currentTimeMillis() - FLOG_SEND_STARTTIME;
//                LogUtils.i("FLOG_SEND_DIFFERENCE----->" + ConvertUtils.Mymillis2FitTimeSpan(FLOG_SEND_DIFFERENCE));
                if (FLOG_COUNTER > FLOG_POOL_MAX_COUNT
                        && Integer.parseInt(ConvertUtils.Mymillis2FitTimeSpan(FLOG_SEND_DIFFERENCE)) > FLOG_POST_INTERVAL) {
                    //发送日志
                    StaticStateUtils.proxyconnectionfailed();
                }
            } else {
                SP_PROXY.put(SPConstants.PROXY.FLOG_SEND_STARTTIME, System.currentTimeMillis());
            }
        }
    }

    /**
     * http、https等请求中用户信息等获取
     */
    private SPUtils userSP = new SPUtils(SPConstants.SP_USER);
    private SPUtils sp_userhttphead = new SPUtils(SPConstants.SP_USERHTTPHEAD);
    private SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);


    private String user_agent = "";

    public void userHttpHeadObtain(ByteBuffer buffer, int bytesRead) {
        try {
            if (buffer.hasRemaining()) {
                final UserhttpheadResponse.ListBean listBean = new UserhttpheadResponse.ListBean();
                UserhttpheadResponse.ListBean.LocBean locBean = new UserhttpheadResponse.ListBean.LocBean();
                List<Double> doubleList = new ArrayList<>();
                UserAgentUtils agentUtils = new UserAgentUtils();

                String s = "";
//                System.out.println("分割线start");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes1 = new byte[bytesRead];
                buffer.get(bytes1);
                baos.write(bytes1);
                BufferedReader b = new BufferedReader(new StringReader(new String(baos.toByteArray())));
                //第一行
                s = b.readLine();
                String[] firstLine = s.split(" ");
                if (firstLine.length == 3) {
                    listBean.setTtp("http");

                    if ("get".equals(firstLine[0].toLowerCase().trim())
                            || "post".equals(firstLine[0].toLowerCase().trim())) {
                        listBean.setTtu(StaticStateUtils.userhttphead_remotehost + firstLine[1]);
                    } else {
                        listBean.setTtu(StaticStateUtils.userhttphead_remotehost);
                    }

                } else {
                    listBean.setTtp("https");
                    listBean.setTtu(StaticStateUtils.userhttphead_remotehost);
                }
                listBean.setTta(StaticStateUtils.userhttphead_remoteip);
                listBean.setTtpot(StaticStateUtils.userhttphead_remoteport);
                listBean.setUid(userSP.getInt(SPConstants.USER.USERID, -99));
                listBean.setUnm(userSP.getString(SPConstants.USER.USERNAME));
                listBean.setUip("");
                listBean.setCnty("");
                listBean.setPvc("");
                listBean.setCity("");

                doubleList.clear();
                doubleList.add(Double.valueOf(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.longitude, "0.00")));//经度
                doubleList.add(Double.valueOf(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.latitude, "0.00")));//纬度
                locBean.setType("point");
                locBean.setCoordinates(doubleList);
                listBean.setLoc(locBean);

                listBean.setUuid(DeviceUtils.getUUID());
                List<String> imeilist = DeviceUtils.getUserIMEI();
                if (imeilist.size() == 1) {
                    listBean.setImei1(imeilist.get(0));
                    listBean.setImei2("");
                } else if (imeilist.size() == 2) {
                    listBean.setImei1(imeilist.get(0));
                    listBean.setImei2(imeilist.get(1));
                }
                List<String> imsilist = DeviceUtils.getUserIMSI();
                if (imsilist.size() == 0) {
                    listBean.setSim1("");
                    listBean.setSim2("");
                } else if (imsilist.size() == 1) {
                    listBean.setSim1(imsilist.get(0));
                    listBean.setSim2("");
                } else if (imsilist.size() == 2) {
                    listBean.setSim1(imsilist.get(0));
                    listBean.setSim2(imsilist.get(1));
                }
                listBean.setCc(StaticStateUtils.getChannelName(StaticStateUtils.basisActivity));
                listBean.setCvn(StaticStateUtils.app_version);
                listBean.setCv(AppUtils.getAppVersionName(StaticStateUtils.basisActivity));
                listBean.setNte(NetworkUtils.getConnectedType(StaticStateUtils.basisActivity));
                listBean.setNto(PhoneUtils.getSimOperatorByMnc());
                listBean.setNtb(NetworkUtils.getNetworkType());
                listBean.setCt(StaticStateUtils.userhttphead_proxyform);
                listBean.setCd(DateUtil.getCurrentDateTime());
                listBean.setSd(DateUtil.getCurrentDateTime());
                listBean.setPrni(configSP.getInt(SPConstants.CONFIG.NODE_ID, -99));
                String proxy_url = configSP.getString(SPConstants.CONFIG.PROXY_URL);
                listBean.setPri(proxy_url.substring(proxy_url.indexOf("@") + 1)
                        .substring(0, proxy_url.substring(proxy_url.indexOf("@") + 1).indexOf(":")));
                listBean.setDn(android.os.Build.USER);
                listBean.setDm(android.os.Build.MODEL);
                listBean.setDr(ScreenUtils.getScreenWidth() + "x" + ScreenUtils.getScreenHeight());
                listBean.setDos("Android");
                listBean.setDosv(android.os.Build.VERSION.RELEASE);
                //剩下的信息
                s = b.readLine();
                while (s != null) {
                    String[] split = s.split(":");
                    if (split.length > 0) {
                        if ("user-agent".equals(split[0].toLowerCase().trim())) {
                            user_agent = split[1].toLowerCase();
                        }
                    }
                    s = b.readLine();
                }
                b.close();
                UserAgentResponse agentResponse = agentUtils.judgeBrowser(user_agent);
                listBean.setBt(agentResponse.getBrowsertype());
                listBean.setBv(agentResponse.getBrowserversion());
                listBean.setBk(agentResponse.getKerneltype());
                listBean.setBkv(agentResponse.getKernelversion());
                user_agent = "";
                StaticStateUtils.userhttpheadList.add(listBean);
//                LogUtils.i("kill--userhttpheadList--->" + StaticStateUtils.userhttpheadList.size());
                if (UserhttpheadDataSource.result) {
                    if (StaticStateUtils.userhttpheadList.size() > 50) {
                        List<UserhttpheadResponse.ListBean> copylistBeen = null;
                        try {
                            copylistBeen = PracticalUtil.deepCopy(StaticStateUtils.userhttpheadList);
                            StaticStateUtils.userhttpheadList.clear();
//                            LogUtils.i("kill--copylistBeen--->" + copylistBeen.size());
                            StaticStateUtils.userhttpheaddatasource.insertList(copylistBeen);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
//                String jsonStu = new Gson().toJson(listBean, UserhttpheadResponse.ListBean.class);
//                System.out.println("jsonStu_____>" + jsonStu);
//                System.out.println("分割线end");
                buffer.rewind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
