package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/12/20.
 * 客户端日志提交相关信息实体类
 */
public class ClientLogResponse {


    /**
     * token : asdf93kd9
     * key : 239dk390dk
     * enabled : true
     * host_url : https://xxx.com
     */

    private String token;
    private String key;
    private boolean enabled;
    private String host_url;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHost_url() {
        return host_url;
    }

    public void setHost_url(String host_url) {
        this.host_url = host_url;
    }
}
