package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/5/16.
 * 自动续费实体类
 */
public class RenewResponse {


    /**
     * user_node_type_id : 661
     * user_node_type_expired_at : 1495005208
     * current_coins : 9995
     * is_renewd : true
     */

    private int user_node_type_id;
    private int user_node_type_expired_at;
    private int current_coins;
    private boolean is_renewd;

    public int getUser_node_type_id() {
        return user_node_type_id;
    }

    public void setUser_node_type_id(int user_node_type_id) {
        this.user_node_type_id = user_node_type_id;
    }

    public int getUser_node_type_expired_at() {
        return user_node_type_expired_at;
    }

    public void setUser_node_type_expired_at(int user_node_type_expired_at) {
        this.user_node_type_expired_at = user_node_type_expired_at;
    }

    public int getCurrent_coins() {
        return current_coins;
    }

    public void setCurrent_coins(int current_coins) {
        this.current_coins = current_coins;
    }

    public boolean isIs_renewd() {
        return is_renewd;
    }

    public void setIs_renewd(boolean is_renewd) {
        this.is_renewd = is_renewd;
    }
}
