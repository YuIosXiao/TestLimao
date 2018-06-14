package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/7/10.
 */
public class ProxyIpResponse {

    private int userid;
    private long creattime;
    private String sourceaddress;
    private String targetaddress;
    private int nodeid;
    private String targetip;   // ip
    private int port;    // 端口
    private int isProxy; // 0 false , 1 true

    public ProxyIpResponse(int userid, long creattime, String sourceaddress, String targetaddress, int nodeid, String targetip, int port, int isProxy) {
        this.userid = userid;
        this.creattime = creattime;
        this.sourceaddress = sourceaddress;
        this.targetaddress = targetaddress;
        this.nodeid = nodeid;
        this.targetip = targetip;
        this.port = port;
        this.isProxy = isProxy;
    }


    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }

    public int getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(int isProxy) {
        this.isProxy = isProxy;
    }

    public int getNodeid() {
        return nodeid;
    }

    public void setNodeid(int nodeid) {
        this.nodeid = nodeid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSourceaddress() {
        return sourceaddress;
    }

    public void setSourceaddress(String sourceaddress) {
        this.sourceaddress = sourceaddress;
    }

    public String getTargetaddress() {
        return targetaddress;
    }

    public void setTargetaddress(String targetaddress) {
        this.targetaddress = targetaddress;
    }

    public String getTargetip() {
        return targetip;
    }

    public void setTargetip(String targetip) {
        this.targetip = targetip;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ProxyIpResponse{" +
                "creattime=" + creattime +
                ", userid=" + userid +
                ", sourceaddress='" + sourceaddress + '\'' +
                ", targetaddress='" + targetaddress + '\'' +
                ", nodeid=" + nodeid +
                ", targetip='" + targetip + '\'' +
                ", port=" + port +
                ", isProxy=" + isProxy +
                '}';
    }
}
