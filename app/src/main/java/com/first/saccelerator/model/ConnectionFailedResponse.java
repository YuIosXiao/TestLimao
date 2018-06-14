package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/18.
 * 用户连接VPN失败
 */
public class ConnectionFailedResponse {

    private String failedid;
    private String username;
    private long createtime;
    private String servertypename;
    private String serverareaname;
    private String servername;

    public ConnectionFailedResponse(String failedid, String username, long createtime, String servertypename, String serverareaname, String servername) {
        this.failedid = failedid;
        this.username = username;
        this.createtime = createtime;
        this.servertypename = servertypename;
        this.serverareaname = serverareaname;
        this.servername = servername;
    }

    public String getFailedid() {
        return failedid;
    }

    public void setFailedid(String failedid) {
        this.failedid = failedid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getServertypename() {
        return servertypename;
    }

    public void setServertypename(String servertypename) {
        this.servertypename = servertypename;
    }

    public String getServerareaname() {
        return serverareaname;
    }

    public void setServerareaname(String serverareaname) {
        this.serverareaname = serverareaname;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    @Override
    public String toString() {
        return "ConnectionFailedResponse{" +
                "failedid='" + failedid + '\'' +
                ", username='" + username + '\'' +
                ", createtime=" + createtime +
                ", servertypename='" + servertypename + '\'' +
                ", serverareaname='" + serverareaname + '\'' +
                ", servername='" + servername + '\'' +
                '}';
    }
}
