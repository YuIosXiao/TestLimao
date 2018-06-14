package com.first.saccelerator.entity;

/**
 * 代理日志Bean
 *
 * Created by Z on 2017/5/3.
 */
public class ProxyLogInfo {

    public long _id;
    public String host; // 域名
    public String ip;   // ip
    public int port;    // 端口
    public int isProxy; // 0 false , 1 true
    public int time;    // 时间

    public ProxyLogInfo() {

    }

    public ProxyLogInfo(long _id, String host, String ip, int port, int isProxy, int time) {
        this._id = _id;
        this.host = host;
        this.ip = ip;
        this.port = port;
        this.isProxy = isProxy;
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProxyLogInfo{" +
                "_id=" + _id +
                ", host='" + host + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", isProxy=" + isProxy +
                ", time=" + time +
                '}';
    }
}