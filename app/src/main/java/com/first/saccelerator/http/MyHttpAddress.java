package com.first.saccelerator.http;

import com.first.saccelerator.utils.StaticStateUtils;

/**
 * Created by XQ on 2017/5/17.
 * 存放 http请求地址的类
 */
public class MyHttpAddress {
    //1.36 提交代理服务器解析日志(认证)
    public static String PROXY_CONNECTION_LOGS = StaticStateUtils.usebath + "/api/client/v1/users/proxy_connection_logs";
    //1.51 提交客户端调试日志文件(认证)
    public static String CLIENT_DEBUG_LOGS = StaticStateUtils.usebath + "/api/client/v1/logs/client_debug_logs";

}
