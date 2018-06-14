package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/11/20.
 * 救援IP 信息
 */
public class RescueIPServersResponse {

    private int id;
    private String url;
    private String region;
    private int priority;

    public RescueIPServersResponse(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
