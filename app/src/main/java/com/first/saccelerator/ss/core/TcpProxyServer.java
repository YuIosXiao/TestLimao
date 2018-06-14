package com.first.saccelerator.ss.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.ProxyIpDataSource;
import com.first.saccelerator.database.ProxyLogDataSource;
import com.first.saccelerator.model.ProxyIpResponse;
import com.first.saccelerator.ss.tcpip.CommonMethods;
import com.first.saccelerator.ss.tunnel.Tunnel;
import com.first.saccelerator.utils.ConvertUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;
import com.first.saccelerator.utils.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

public class TcpProxyServer implements Runnable {

    public boolean Stopped;
    public short Port;

    Selector m_Selector;
    ServerSocketChannel m_ServerSocketChannel;
    Thread m_ServerThread;
    private ProxyLogDataSource mDataSource;
    private ProxyIpDataSource ipDataSource;
    List<ProxyIpResponse> ipResponseList;
    private SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);
    private SPUtils userSP = new SPUtils(SPConstants.SP_USER);
    private SPUtils SP_PROXY = new SPUtils(SPConstants.SP_PROXY);

    public TcpProxyServer(int port) throws IOException {
        mDataSource = new ProxyLogDataSource(new DBHelper(Utils.getContext()));
        ipDataSource = new ProxyIpDataSource(new DBHelper(Utils.getContext()));
        m_Selector = Selector.open(); // 打开选择器
        m_ServerSocketChannel = ServerSocketChannel.open(); // 打开通道
        m_ServerSocketChannel.configureBlocking(false); // 非阻塞
        m_ServerSocketChannel.socket().bind(new InetSocketAddress(port));
        int interestSet = SelectionKey.OP_ACCEPT;
        m_ServerSocketChannel.register(m_Selector, interestSet); // 向通道注册选择器和对应事件标识
        this.Port = (short) m_ServerSocketChannel.socket().getLocalPort();
        System.out.printf("AsyncTcpServer listen on %d success.\n", this.Port & 0xFFFF);
    }

    public void start() {
        m_ServerThread = new Thread(this);
        m_ServerThread.setName("TcpProxyServerThread");
        m_ServerThread.start();
    }

    public void stop() {
        this.Stopped = true;
        if (m_Selector != null) {
            try {
                m_Selector.close();
                m_Selector = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (m_ServerSocketChannel != null) {
            try {
                m_ServerSocketChannel.close();
                m_ServerSocketChannel = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                m_Selector.select();
                Iterator<SelectionKey> keyIterator = m_Selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isValid()) {
                        try {
                            if (key.isReadable()) {
                                // a channel is ready for reading.
                                ((Tunnel) key.attachment()).onReadable(key);
                            } else if (key.isWritable()) {
                                // a channel is ready for writing.
                                ((Tunnel) key.attachment()).onWritable(key);
                            } else if (key.isConnectable()) {
                                // a connection was established with a remote server.

                                StaticStateUtils.userhttphead_remotehost = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.remotehost, "");
                                StaticStateUtils.userhttphead_remoteip = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.remoteip, "");
                                StaticStateUtils.userhttphead_remoteport = sp_userhttphead.getInt(SPConstants.USERHTTPHEAD.remoteport, 0);
                                StaticStateUtils.userhttphead_proxyform = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.proxyform);

                                ((Tunnel) key.attachment()).onConnectable();
                            } else if (key.isAcceptable()) {
                                // a connection was accepted by a ServerSocketChannel.
//                                System.out.println("NeedProxy-- onAccepted onAccepted onAccepted");
                                onAccepted(key);
                            }
                        } catch (Exception e) {
                            System.out.println("run----->" + e.toString());
                        }
                    }
                    // 删除已选的key,以防重复处理
                    keyIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.stop();
            System.out.println("TcpServer thread exited.");
        }
    }

    private SPUtils sp_userhttphead = new SPUtils(SPConstants.SP_USERHTTPHEAD);

    InetSocketAddress getDestAddress(SocketChannel localChannel) {
        short portKey = (short) localChannel.socket().getPort();
        NatSession session = NatSessionManager.getSession(portKey);
        if (session != null) {

            String remotehost = session.RemoteHost;
            String remoteip = CommonMethods.ipIntToString(session.RemoteIP);
            int remoteport = session.RemotePort & 0xFFFF;
            sp_userhttphead.put(SPConstants.USERHTTPHEAD.remotehost, remotehost);
            sp_userhttphead.put(SPConstants.USERHTTPHEAD.remoteip, remoteip);
            sp_userhttphead.put(SPConstants.USERHTTPHEAD.remoteport, remoteport);

            /**
             *发送去向IP地址
             */
//            ipResponseList = ipDataSource.findAll();
            String ALLOW_SEND_LOG = SP_PROXY.getString(SPConstants.PROXY.DLOG_ALLOW_SEND, "false");
            int LOG_POOL_MAX_COUNT = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.DLOG_POOL_MAX_COUNT, "50"));
            int POST_LOG_INTERVAL = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.DLOG_POST_INTERVAL, "600"));
//            if ("true".equals(ALLOW_SEND_LOG)) {
            if ("禁止".equals(ALLOW_SEND_LOG)) {//暂时禁止掉 客户端去向日志提交（发送去向IP地址）方法
                if (!StringUtils.isBlank(ipResponseList) && ipResponseList.size() == 0) {
                    SP_PROXY.put(SPConstants.PROXY.DLOG_STARTTIME, System.currentTimeMillis() + "");
                }
                long LOGS_STARTTIME = Long.parseLong(SP_PROXY.getString(SPConstants.PROXY.DLOG_STARTTIME));
                long LOGS_DIFFERENCE = System.currentTimeMillis() - LOGS_STARTTIME;
//                LogUtils.i("Mymillis2FitTimeSpan----->" + ConvertUtils.Mymillis2FitTimeSpan(LOGS_DIFFERENCE));
                if (!StringUtils.isBlank(ipResponseList) && ipResponseList.size() > 0) {
                    if (ipResponseList.size() > LOG_POOL_MAX_COUNT
                            || Integer.parseInt(ConvertUtils.Mymillis2FitTimeSpan(LOGS_DIFFERENCE)) > POST_LOG_INTERVAL) {
                        if (StaticStateUtils.clientconnectionlogsflag) {
                            StaticStateUtils.clientconnectionlogsflag = false;
                            StaticStateUtils.clientconnectionlogs();
                        }
                    }
                }
            }

            if (ProxyConfig.Instance.needProxy(session.RemoteHost, session.RemoteIP)) {
                sp_userhttphead.put(SPConstants.USERHTTPHEAD.proxyform, "proxy");

//                mDataSource.insert(session.RemoteHost, CommonMethods.ipIntToString(session.RemoteIP), session.RemotePort & 0xFFFF, 1, (int) (System.currentTimeMillis() / 1000));
                int userid = userSP.getInt(SPConstants.USER.USERID, -99);
                long creattime = (System.currentTimeMillis() / 1000);
                String sourceaddress = configSP.getString(SPConstants.CONFIG.PROXY_NODE_URL, "");
                String targetaddress = session.RemoteHost;
                int nodeid = configSP.getInt(SPConstants.CONFIG.PROXY_NODE_ID);
                String targetip = CommonMethods.ipIntToString(session.RemoteIP);
                int port = session.RemotePort & 0xFFFF;
                int isProxy = 1;
                ipDataSource.insertOrUpdate(userid, creattime, sourceaddress, targetaddress, nodeid, targetip, port, isProxy);
                if (ProxyConfig.IS_DEBUG) {
                    System.out.printf("NeedProxy----->%d/%d:[PROXY] %s=>%s:%d\n", NatSessionManager.getSessionCount(), Tunnel.SessionCount, session.RemoteHost, CommonMethods.ipIntToString(session.RemoteIP), session.RemotePort & 0xFFFF);
                }
                return InetSocketAddress.createUnresolved(session.RemoteHost, session.RemotePort & 0xFFFF);
            } else {
                sp_userhttphead.put(SPConstants.USERHTTPHEAD.proxyform, "direct");


//                mDataSource.insert(session.RemoteHost, CommonMethods.ipIntToString(session.RemoteIP), session.RemotePort & 0xFFFF, 0, (int) (System.currentTimeMillis() / 1000));
                int userid = userSP.getInt(SPConstants.USER.USERID, -99);
                long creattime = (System.currentTimeMillis() / 1000);
                String sourceaddress = configSP.getString(SPConstants.CONFIG.PROXY_NODE_URL, "");
                String targetaddress = session.RemoteHost;
                int nodeid = configSP.getInt(SPConstants.CONFIG.PROXY_NODE_ID);
                String targetip = CommonMethods.ipIntToString(session.RemoteIP);
                int port = session.RemotePort & 0xFFFF;
                int isProxy = 0;
                ipDataSource.insertOrUpdate(userid, creattime, sourceaddress, targetaddress, nodeid, targetip, port, isProxy);
                if (ProxyConfig.IS_DEBUG) {
                    System.out.printf("NotNeedProxy----->%d/%d:[PROXY] %s=>%s:%d\n", NatSessionManager.getSessionCount(), Tunnel.SessionCount, session.RemoteHost, CommonMethods.ipIntToString(session.RemoteIP), session.RemotePort & 0xFFFF);
                }
                return new InetSocketAddress(localChannel.socket().getInetAddress(), session.RemotePort & 0xFFFF);
            }
        } else {
            System.out.println("NeedProxy-- else else else");
        }
        return null;
    }

    void onAccepted(SelectionKey key) {

        Message message = Message.obtain();
        message.what = sendmessage;
        handler.sendMessage(message);

        Tunnel localTunnel = null;
        try {
            // 获得和客户端连接的通道
            SocketChannel localChannel = m_ServerSocketChannel.accept();
            localTunnel = TunnelFactory.wrap(localChannel, m_Selector);

            InetSocketAddress destAddress = getDestAddress(localChannel);
            if (destAddress != null) {
                Tunnel remoteTunnel = TunnelFactory.createTunnelByConfig(destAddress, m_Selector);
                remoteTunnel.setBrotherTunnel(localTunnel);//关联兄弟
                localTunnel.setBrotherTunnel(remoteTunnel);//关联兄弟
                remoteTunnel.connect(destAddress);//开始连接
            } else {
                LocalVpnService.Instance.writeLog("Error: socket(%s:%d) target host is null.", localChannel.socket().getInetAddress().toString(), localChannel.socket().getPort());
                localTunnel.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LocalVpnService.Instance.writeLog("Error: remote socket create failed: %s", e.toString());
            if (localTunnel != null) {
                localTunnel.dispose();
            }
        }
    }

    /**
     *
     */
    private static final int sendmessage = 1;
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case sendmessage:
                    StaticStateUtils.toUpdateUserhttpheadData(sp_userhttphead, StaticStateUtils.userhttpheaddatasource);
                    break;
            }
        }
    };
}
