package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/18.
 * 用户未操作
 */
public class NoOperationResponse {

    private String username;
    private long creattime;

    public NoOperationResponse(String username, long creattime) {
        this.username = username;
        this.creattime = creattime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }


    @Override
    public String toString() {
        return "NoOperationResponse{" +
                "username='" + username + '\'' +
                ", creattime=" + creattime +
                '}';
    }
}
