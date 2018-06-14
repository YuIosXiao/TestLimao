package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/18.
 * 登录失败记录
 */
public class LoginFailedResponse {

    private String failedid;//失败ID类型
    private String username;//失败用户名
    private long logintime;//登录时间
    private String uuid;//手机的UUID

    public LoginFailedResponse(String failedid, String username, long logintime, String uuid) {
        this.failedid = failedid;
        this.username = username;
        this.logintime = logintime;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public long getLogintime() {
        return logintime;
    }

    public void setLogintime(long logintime) {
        this.logintime = logintime;
    }


    @Override
    public String toString() {
        return "LoginFailedResponse{" +
                "uuid='" + uuid + '\'' +
                ", failedid='" + failedid + '\'' +
                ", username='" + username + '\'' +
                ", logintime=" + logintime +
                '}';
    }
}
