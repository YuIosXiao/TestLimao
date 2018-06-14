package com.first.saccelerator.model;

/**
 * 完善资料绑定社交帐号(认证)
 * Created by ZhengSheng on 2017/3/22.
 */

public class UpdateProfileResponse extends ApiResponse {


    /**
     * id : 5e8479fc-fd9d-11e6-8216-784f4352f7e7
     * telephone : 13884633396
     * user_type : 1
     * service_state : 1
     * is_enabled : true
     * email : bindiry@gmail.com
     * wechat : asdfasdfasdf
     * weibo : asdfasdfasdf
     * qq : asdfasdfasdf
     * used_bytes : 0
     * max_bytes : -1
     * limit_bytes : -1
     * expired_at : 17266730701
     * created_at : 1488276301
     */

    private String id;
    private String telephone;
    private int user_type;
    private int service_state;
    private boolean is_enabled;
    private String email;
    private String wechat;
    private String weibo;
    private String qq;
    private long used_bytes;
    private long max_bytes;
    private long limit_bytes;
    private long expired_at;
    private long created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getService_state() {
        return service_state;
    }

    public void setService_state(int service_state) {
        this.service_state = service_state;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public long getUsed_bytes() {
        return used_bytes;
    }

    public void setUsed_bytes(long used_bytes) {
        this.used_bytes = used_bytes;
    }

    public long getMax_bytes() {
        return max_bytes;
    }

    public void setMax_bytes(long max_bytes) {
        this.max_bytes = max_bytes;
    }

    public long getLimit_bytes() {
        return limit_bytes;
    }

    public void setLimit_bytes(long limit_bytes) {
        this.limit_bytes = limit_bytes;
    }

    public long getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(long expired_at) {
        this.expired_at = expired_at;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "UpdateProfileResponse{" +
                "id='" + id + '\'' +
                ", telephone='" + telephone + '\'' +
                ", user_type=" + user_type +
                ", service_state=" + service_state +
                ", is_enabled=" + is_enabled +
                ", email='" + email + '\'' +
                ", wechat='" + wechat + '\'' +
                ", weibo='" + weibo + '\'' +
                ", qq='" + qq + '\'' +
                ", used_bytes=" + used_bytes +
                ", max_bytes=" + max_bytes +
                ", limit_bytes=" + limit_bytes +
                ", expired_at=" + expired_at +
                ", created_at=" + created_at +
                '}';
    }
}
