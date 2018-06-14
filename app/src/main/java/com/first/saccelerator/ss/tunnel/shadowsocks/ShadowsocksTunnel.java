package com.first.saccelerator.ss.tunnel.shadowsocks;

import android.util.Log;

import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.constants.ServerResultCode;
import com.first.saccelerator.ss.core.LocalVpnService;
import com.first.saccelerator.ss.tunnel.Tunnel;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.ToastUtils;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class ShadowsocksTunnel extends Tunnel {

    private ICrypt m_Encryptor;
    private ShadowsocksConfig m_Config;
    private boolean m_TunnelEstablished;
    /**
     * 是否接收验证信息
     * true  : 已接收
     * false : 未接收
     */
    private boolean m_authRetReceived = false;

    public ShadowsocksTunnel(ShadowsocksConfig config, Selector selector) throws Exception {
        super(config.ServerAddress, selector);
        m_Config = config;
        m_Encryptor = CryptFactory.get(m_Config.EncryptMethod, m_Config.Password);

    }

    @Override
    protected void onConnected(ByteBuffer buffer) throws Exception {

        buffer.clear();
        // https://shadowsocks.org/en/spec/protocol.html
        buffer.put((byte) 0x03);//domain
        byte[] domainBytes = m_DestAddress.getHostName().getBytes();
        buffer.put((byte) domainBytes.length);//domain length;
        buffer.put(domainBytes);
        buffer.putShort((short) m_DestAddress.getPort());
        buffer.flip();
        byte[] _header = new byte[buffer.limit()];
        buffer.get(_header);

        buffer.clear();
        buffer.put(m_Encryptor.encrypt(_header));
        buffer.flip();

        if (write(buffer, true)) {

            // 在socks头中添加socks服务端验证信息
            SPUtils userSP = new SPUtils(SPConstants.SP_USER);
            ByteBuffer buffer_auth = ByteBuffer.allocateDirect(20000);
            int account_id = userSP.getInt(SPConstants.USER.SOCKS_ACCOUNT_ID, 0);
            int session_id = userSP.getInt(SPConstants.USER.SOCKS_SESSION_ID, 0);
            String session_token = userSP.getString(SPConstants.USER.SOCKS_SESSION_TOKEN, "");
            String all = account_id + "|" + session_id + "|" + session_token;
//            String all = account_id + "|" + session_id + "|" + "my9zw-ggJsEy_u1ruo8y";
            LogUtils.i("ProxyParam", "AuthParam: " + all);
            LogUtils.i("ProxyParam", "signin----->: " + all);
            short len = (short) all.length();
            buffer_auth.putShort((byte) len);
            buffer_auth.put(all.getBytes());
            buffer_auth.flip();
            byte[] _header1 = new byte[buffer_auth.limit()];
            buffer_auth.get(_header1);
            buffer_auth.clear();
            buffer_auth.put(m_Encryptor.encrypt(_header1));
            buffer_auth.flip();

            if (write(buffer_auth, true)) { // 发送验证信息
                m_TunnelEstablished = true;
                onTunnelEstablished();
            } else {
                m_TunnelEstablished = true;
                this.beginReceive();
            }
        } else {
            m_TunnelEstablished = true;
            this.beginReceive();
        }
    }

    @Override
    protected boolean isTunnelEstablished() {
        return m_TunnelEstablished;
    }

    @Override
    protected void beforeSend(ByteBuffer buffer) throws Exception {
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        byte[] newbytes = m_Encryptor.encrypt(bytes);
        buffer.clear();
        buffer.put(newbytes);
        buffer.flip();
    }

    @Override
    protected void afterReceived(ByteBuffer buffer) throws Exception {

        if (m_authRetReceived) {
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            byte[] newbytes = m_Encryptor.decrypt(bytes);
            buffer.clear();
            buffer.put(newbytes);
            buffer.flip();
        } else {
            // 接收到服务器返回的验证响应信息
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            byte[] newbytes = m_Encryptor.decrypt(bytes);
            /**
             *收到解码后长度为0的缓冲区表示此段加密缓冲只包括iv，没有payload
             */
            if (newbytes.length == 0) {
                buffer.clear();
                buffer.flip();
                Log.e("ShadowsocksTunnel_1", "not receive all the result code: " + newbytes);
                return;
            }
            int resultCode = byteArrayToInt(newbytes);
            Log.e("ShadowsocksTunnel_1", "返回为0则验证成功 ：" + resultCode);
            // 清除响应信息
            buffer.clear();
            buffer.flip();
            switch (resultCode) {
                case ServerResultCode.SVPN_CODE_SERVER_USER_DISABLED:
                    LocalVpnService.IsRunning = false;
                    break;
                case ServerResultCode.SVPN_CODE_SERVER_SHUT_DOWN:
                    new SPUtils(SPConstants.SP_CONFIG).put(SPConstants.CONFIG.USING_INTERRUPTED, true);
                    LocalVpnService.IsRunning = false;
                    break;
                case ServerResultCode.SVPN_CODE_SERVER_EXPIRES:
//                    LocalVpnService.IsRunning = false;
//                    ToastUtils.showShortToastSafe("您使用的线路已过期，请续费后使用！");
                    StaticStateUtils.renew();
                    break;
                case ServerResultCode.SVPN_CODE_SERVER_CURRENCY_NOTENOUGH:
                    LocalVpnService.IsRunning = false;
                    ToastUtils.showShortToastSafe("您的虚拟货币不够！");
                    break;
                case ServerResultCode.SVPN_CODE_SUCCESS:
                default:
                    m_authRetReceived = true;
                    break;
            }
        }

    }

    /**
     * 取前4位转换成uint16
     *
     * @param b bytes
     * @return uint16值
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    @Override
    protected void onDispose() {
        m_Config = null;
        m_Encryptor = null;
    }

}
