package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/11.
 * 登录历史记录
 */
public class LoginRecordResponse {

    //用户ID
    private int id;

    //账号创建时间
    private long createdtime;
    //用户名
    private String username;
    //密码
    private String password;
    //登录时间
    private String logintime;


    public LoginRecordResponse(int id, long createdtime, String username, String password, String logintime) {
        this.id = id;
        this.createdtime = createdtime;
        this.username = username;
        this.password = password;
        this.logintime = logintime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogintime() {
        return logintime;
    }

    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }


    @Override
    public String toString() {
        return "LoginRecordResponse{" +
                "id=" + id +
                ", createdtime=" + createdtime +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", logintime='" + logintime + '\'' +
                '}';
    }
}
