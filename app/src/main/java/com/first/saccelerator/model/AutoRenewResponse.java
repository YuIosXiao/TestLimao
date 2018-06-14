package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/8/7.
 * 是否自动续费实体类
 */
public class AutoRenewResponse {


    /**
     * auto_renew : false
     */

    private boolean auto_renew;

    public boolean isAuto_renew() {
        return auto_renew;
    }

    public void setAuto_renew(boolean auto_renew) {
        this.auto_renew = auto_renew;
    }
}
