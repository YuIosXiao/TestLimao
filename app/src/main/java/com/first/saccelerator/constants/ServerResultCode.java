package com.first.saccelerator.constants;

/**
 * Created by Z on 2017/4/25.
 */

public interface ServerResultCode {

    int SVPN_CODE_SUCCESS = 0;        // 连接SVPN服务器成功
    int SVPN_CODE_SERVER_EXPIRES = 3; // 用户服务器类型到期
    int SVPN_CODE_SERVER_CURRENCY_NOTENOUGH = 1; // 用户虚拟币不够
    int SVPN_CODE_SERVER_SHUT_DOWN = 7; // 用户服务器类型到期,关闭VPN
    int SVPN_CODE_SERVER_USER_DISABLED = 8; //用户被禁用了，直接断掉VPN
}
