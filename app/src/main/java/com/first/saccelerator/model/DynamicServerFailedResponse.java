package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/5/5.
 * 动态服务器连接失败信息
 */
public class DynamicServerFailedResponse {

    private String username;
    private String url;
    private long createtime;
    private int userid;

    public DynamicServerFailedResponse(String username, String url, long createtime, int userid) {
        this.username = username;
        this.url = url;
        this.createtime = createtime;
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "DynamicServerFailedResponse{" +
                "username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", createtime=" + createtime +
                ", userid=" + userid +
                '}';
    }
}
