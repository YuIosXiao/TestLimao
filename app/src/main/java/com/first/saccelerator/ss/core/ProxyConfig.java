package com.first.saccelerator.ss.core;

import android.annotation.SuppressLint;
import android.os.Build;

import com.first.saccelerator.App;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.ss.tcpip.CommonMethods;
import com.first.saccelerator.ss.tunnel.Config;
import com.first.saccelerator.ss.tunnel.httpconnect.HttpConnectConfig;
import com.first.saccelerator.ss.tunnel.shadowsocks.ShadowsocksConfig;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProxyConfig {
    public static final ProxyConfig Instance = new ProxyConfig();
    public final static boolean IS_DEBUG = AppUtils.isAppDebug(App.getApplication());
    public static String AppInstallID;
    public static String AppVersion;
    public final static int FAKE_NETWORK_MASK = CommonMethods.ipStringToInt("255.255.0.0");
    public final static int FAKE_NETWORK_IP = CommonMethods.ipStringToInt("10.231.0.0");

    ArrayList<IPAddress> m_IpList;
    ArrayList<IPAddress> m_DnsList;
    ArrayList<IPAddress> m_RouteList;
    public ArrayList<Config> m_ProxyList;
    //    HashMap<String, Boolean> m_DomainMap;
    Map<String, Boolean> m_DomainMap;

    public boolean globalMode = false;

    int m_dns_ttl;
    String m_welcome_info;
    String m_session_name;
    String m_user_agent;
    boolean m_outside_china_use_proxy = true;
    boolean m_isolate_http_host_header = true;
    int m_mtu;

    Timer m_Timer;

    public class IPAddress {
        public final String Address;
        public final int PrefixLength;

        public IPAddress(String address, int prefixLength) {
            this.Address = address;
            this.PrefixLength = prefixLength;
        }

        public IPAddress(String ipAddresString) {
            String[] arrStrings = ipAddresString.split("/");
            String address = arrStrings[0];
            int prefixLength = 32;
            if (arrStrings.length > 1) {
                prefixLength = Integer.parseInt(arrStrings[1]);
            }
            this.Address = address;
            this.PrefixLength = prefixLength;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public String toString() {
            return String.format("%s/%d", Address, PrefixLength);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            } else {
                return this.toString().equals(o.toString());
            }
        }
    }

    public ProxyConfig() {
        m_IpList = new ArrayList<IPAddress>();
        m_DnsList = new ArrayList<IPAddress>();
        m_RouteList = new ArrayList<IPAddress>();
        m_ProxyList = new ArrayList<Config>();
        //        m_DomainMap = new HashMap<String, Boolean>();
//        m_DomainMap = Collections.synchronizedMap(new LinkedHashMap<String, Boolean>());
        m_DomainMap = new LinkedHashMap<String, Boolean>();

        m_Timer = new Timer();
        m_Timer.schedule(m_Task, 120000, 120000);//每两分钟刷新一次。
    }

    TimerTask m_Task = new TimerTask() {
        @Override
        public void run() {
            refreshProxyServer();//定时更新dns缓存
        }

        //定时更新dns缓存
        void refreshProxyServer() {
            try {
                for (int i = 0; i < m_ProxyList.size(); i++) {
                    try {
                        Config config = m_ProxyList.get(0);
                        InetAddress address = InetAddress.getByName(config.ServerAddress.getHostName());
                        if (address != null && !address.equals(config.ServerAddress.getAddress())) {
                            config.ServerAddress = new InetSocketAddress(address, config.ServerAddress.getPort());
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {

            }
        }
    };


    public static boolean isFakeIP(int ip) {
        return (ip & ProxyConfig.FAKE_NETWORK_MASK) == ProxyConfig.FAKE_NETWORK_IP;
    }

    public Config getDefaultProxy() {
        if (m_ProxyList.size() > 0) {
            return m_ProxyList.get(0);
        } else {
            return null;
        }
    }

    public Config getDefaultTunnelConfig(InetSocketAddress destAddress) {
        return getDefaultProxy();
    }

    public IPAddress getDefaultLocalIP() {
        if (m_IpList.size() > 0) {
            return m_IpList.get(0);
        } else {
            return new IPAddress("10.8.0.2", 32);
        }
    }

    public ArrayList<IPAddress> getDnsList() {
        return m_DnsList;
    }

    public ArrayList<IPAddress> getRouteList() {
        return m_RouteList;
    }

    public int getDnsTTL() {
        if (m_dns_ttl < 30) {
            m_dns_ttl = 30;
        }
        return m_dns_ttl;
    }

    public String getWelcomeInfo() {
        return m_welcome_info;
    }

    public String getSessionName() {
        m_session_name = new SPUtils(SPConstants.SP_CONFIG).getString(SPConstants.CONFIG.PROXY_NAME, "");
        return m_session_name;
    }

    public String getUserAgent() {
        if (m_user_agent == null || m_user_agent.isEmpty()) {
            m_user_agent = System.getProperty("http.agent");
        }
        return m_user_agent;
    }

    public int getMTU() {
        if (m_mtu > 1400 && m_mtu <= 20000) {
            return m_mtu;
        } else {
            return 20000;
        }
    }

    private Boolean getDomainState(String domain) {
//        domain = domain.toLowerCase();
//        Set<String> keySet = m_DomainMap.keySet();
//        for (String key : keySet) {
//            if (domain.contains(key)) {
//                return m_DomainMap.get(key);
//            }
//        }
        domain = domain.toLowerCase();
        for (Iterator it = m_DomainMap.entrySet().iterator(); it.hasNext(); ) {//HashMap两种遍历方法（keyset & entryset快些）
            Map.Entry entry = (Map.Entry) it.next();
            if (domain.contains((String) entry.getKey())) {
                return (Boolean) entry.getValue();
            }
        }
        return null;
    }

    private String pattern_domain = "\\w*\\.\\w*:";//域名访问
    private String pattern_ip = "(\\d*\\.){3}\\d*";//ip正则验证

    public boolean needProxy(String host, int ip) {
        SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
        boolean vpntogglebutton = sputilsconfig.getBoolean(SPConstants.CONFIG.VPNTOGGLEBUTTON, false);

        if (globalMode) {
            return true;
        }
        boolean isDomestic = App.isDomestic();
        if (host != null) {
            Pattern ipPattern = Pattern.compile(pattern_ip);
            Matcher matcher = ipPattern.matcher(host);
            boolean ipAddressflag = matcher.find();
//            LogUtils.i("ipAddressflag___>" + ipAddressflag);
            if (ipAddressflag) {//判断输入的host（是ip还是域名）
//                LogUtils.i("isFakeIP(ip)___>" + isDomestic + "&&&&&" + host + "&&&&&" + ipAddressflag + "&&&&&" + ip + "&&&&&" + isFakeIP(ip));
                if (isFakeIP(ip)) {
                    return true;
                }
                if (m_outside_china_use_proxy && ip != 0) {
                    boolean proxyip = ChinaIpMaskManager.isIPInChina(ip);//需要走代理的ip判断 true 走代理 false 不走代理
//                    LogUtils.i("ip区域 host___ip___ipIntToString___foreignIP___isDomestic___>" + host + "___" + ip + "___" + CommonMethods.ipIntToString(ip)
//                            + "___" + proxyip + "_____" + isDomestic);
                    if (vpntogglebutton) {
                        return true;
                    } else {
                        return proxyip;
                    }
                }
            } else {
                if (vpntogglebutton) {
                    if (StaticStateUtils.dynamicandrescuelist.size() > 0) {
                        for (int i = 0; i < StaticStateUtils.dynamicandrescuelist.size(); i++) {
                            if (StaticStateUtils.dynamicandrescuelist.get(i).equals(host)) {
//                                LogUtils.i("----->" + StaticStateUtils.dynamicandrescuelist.get(i));
                                return false;
                            }
                        }
                        return true;
                    } else {
                        return true;
                    }
                } else {
                    Boolean stateBoolean = getDomainState(host);
                    if (stateBoolean != null) {
                        return stateBoolean.booleanValue();
                    } else {
                        if (m_outside_china_use_proxy && ip != 0) {
                            boolean proxyip = ChinaIpMaskManager.isIPInChina(ip);//需要走代理的ip判断 true 走代理 false 不走代理
//                            LogUtils.i("域名区域 host___ip___foreignIP___isDomestic___>" + host + "___" + CommonMethods.ipIntToString(ip)
//                                    + "___" + proxyip + "_____" + isDomestic);
                            if (vpntogglebutton) {
                                return true;
                            } else {
                                return proxyip;
                            }
                        }
                    }
                }
            }
        }

        if (vpntogglebutton) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isIsolateHttpHostHeader() {
        return m_isolate_http_host_header;
    }

    private String[] downloadConfig(String url) throws Exception {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet requestGet = new HttpGet(url);

            requestGet.addHeader("X-Android-MODEL", Build.MODEL);
            requestGet.addHeader("X-Android-SDK_INT", Integer.toString(Build.VERSION.SDK_INT));
            requestGet.addHeader("X-Android-RELEASE", Build.VERSION.RELEASE);
            requestGet.addHeader("X-App-Version", AppVersion);
            requestGet.addHeader("X-App-Install-SOCKS_ACCOUNT_ID", AppInstallID);
            requestGet.setHeader("User-Agent", System.getProperty("http.agent"));
            HttpResponse response = client.execute(requestGet);

            String configString = EntityUtils.toString(response.getEntity(), "UTF-8");
            String[] lines = configString.split("\\n");
            return lines;
        } catch (Exception e) {
            throw new Exception(String.format("Download config file from %s failed.", url));
        }
    }

    private String[] readConfigFromFile(String path) throws Exception {
        StringBuilder sBuilder = new StringBuilder();
        FileInputStream inputStream = null;
        try {
            byte[] buffer = new byte[8192];
            int count = 0;
            inputStream = new FileInputStream(path);
            while ((count = inputStream.read(buffer)) > 0) {
                sBuilder.append(new String(buffer, 0, count, "UTF-8"));
            }
            return sBuilder.toString().split("\\n");
        } catch (Exception e) {
            throw new Exception(String.format("Can't read config file: %s", path));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    public void loadFromFile(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        loadFromLines(new String(bytes).split("\\r?\\n"));
    }

    public void loadFromUrl(String url) throws Exception {
        String[] lines = null;
        if (url.charAt(0) == '/') {
            lines = readConfigFromFile(url);
        } else {
            lines = downloadConfig(url);
        }
        loadFromLines(lines);
    }

    protected void loadFromLines(String[] lines) throws Exception {

        m_IpList.clear();
        m_DnsList.clear();
        m_RouteList.clear();
        m_ProxyList.clear();
        m_DomainMap.clear();

        int lineNumber = 0;
        for (String line : lines) {
            lineNumber++;
            String[] items = line.split("\\s+");
            if (items.length < 2) {
                continue;
            }

            String tagString = items[0].toLowerCase(Locale.ENGLISH).trim();
            try {
                if (!tagString.startsWith("#")) {
                    if (ProxyConfig.IS_DEBUG)
                        System.out.println(line);

                    if (tagString.equals("ip")) {
                        addIPAddressToList(items, 1, m_IpList);
                    } else if (tagString.equals("dns")) {
                        addIPAddressToList(items, 1, m_DnsList);
                    } else if (tagString.equals("route")) {
                        addIPAddressToList(items, 1, m_RouteList);
                    } else if (tagString.equals("proxy")) {
                        addProxyToList(items, 1);
                    } else if (tagString.equals("direct_domain")) {
                        addDomainToHashMap(items, 1, false);
                    } else if (tagString.equals("proxy_domain")) {
                        addDomainToHashMap(items, 1, true);
                    } else if (tagString.equals("dns_ttl")) {
                        m_dns_ttl = Integer.parseInt(items[1]);
                    } else if (tagString.equals("welcome_info")) {
                        m_welcome_info = line.substring(line.indexOf(" ")).trim();
                    } else if (tagString.equals("session_name")) {
//                        m_session_name = items[1];
                        m_session_name = new SPUtils(SPConstants.SP_CONFIG).getString(SPConstants.CONFIG.PROXY_NAME, items[1]);
                    } else if (tagString.equals("user_agent")) {
                        m_user_agent = line.substring(line.indexOf(" ")).trim();
                    } else if (tagString.equals("outside_china_use_proxy")) {
                        m_outside_china_use_proxy = convertToBool(items[1]);
                    } else if (tagString.equals("isolate_http_host_header")) {
                        m_isolate_http_host_header = convertToBool(items[1]);
                    } else if (tagString.equals("mtu")) {
                        m_mtu = Integer.parseInt(items[1]);
                    } else if (tagString.equals("ipproxy")) {//新增一个字段ipproxy  表示当前IP段是需要走代理的
//                        LogUtil.i("items.length----->" + items.length);
                        if (items.length > 1) {
                            ChinaIpMaskManager.loadIp(items);
                        }
                    }
                }
            } catch (Exception e) {
                throw new Exception(String.format("config file parse error: line:%d, tag:%s, error:%s", lineNumber, tagString, e));
            }

        }

        //查找默认代理。
        if (m_ProxyList.size() == 0) {
            tryAddProxy(lines);
        }
    }

    private void tryAddProxy(String[] lines) {
        for (String line : lines) {
            Pattern p = Pattern.compile("proxy\\s+([^:]+):(\\d+)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(line);
            while (m.find()) {
                HttpConnectConfig config = new HttpConnectConfig();
                config.ServerAddress = new InetSocketAddress(m.group(1), Integer.parseInt(m.group(2)));
                if (!m_ProxyList.contains(config)) {
                    m_ProxyList.add(config);
                    m_DomainMap.put(config.ServerAddress.getHostName(), false);
                }
            }
        }
    }

    public void addProxyToList(String proxyString) throws Exception {
        Config config = null;
        if (proxyString.startsWith("ss://")) {
            config = ShadowsocksConfig.parse(proxyString);
        } else {
            if (!proxyString.toLowerCase().startsWith("http://")) {
                proxyString = "http://" + proxyString;
            }
            config = HttpConnectConfig.parse(proxyString);
        }
        if (!m_ProxyList.contains(config)) {
            m_ProxyList.add(config);
//            m_DomainMap.put(config.ServerAddress.getHostName(), false);
            String hostName = config.ServerAddress.getHostString();
            if (StaticStateUtils.getIpOrDomain(hostName)) {
//                hostName = config.ServerAddress.getAddress().toString().replace("/", "");
            } else {
                hostName = config.ServerAddress.getHostName();
            }
            m_DomainMap.put(hostName, false);
        }
    }

    private void addProxyToList(String[] items, int offset) throws Exception {
        for (int i = offset; i < items.length; i++) {
            addProxyToList(items[i].trim());
        }
    }

    private void addDomainToHashMap(String[] items, int offset, Boolean state) {
        for (int i = offset; i < items.length; i++) {
            String domainString = items[i].toLowerCase().trim();
            if (domainString.charAt(0) == '.') {
                domainString = domainString.substring(1);
            }
            m_DomainMap.put(domainString, state);
        }
    }

    private boolean convertToBool(String valueString) {
        if (valueString == null || valueString.isEmpty())
            return false;
        valueString = valueString.toLowerCase(Locale.ENGLISH).trim();
        if (valueString.equals("on") || valueString.equals("1") || valueString.equals("true") || valueString.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }


    private void addIPAddressToList(String[] items, int offset, ArrayList<IPAddress> list) {
        for (int i = offset; i < items.length; i++) {
            String item = items[i].trim().toLowerCase();
            if (item.startsWith("#")) {
                break;
            } else {
                IPAddress ip = new IPAddress(item);
                if (!list.contains(ip)) {
                    list.add(ip);
                }
            }
        }
    }

}
