package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/14.
 * 获得当前用户封号状态(认证) 解析类
 */
public class CommonsResponse {


    /**
     * user : {"is_enabled":true}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * is_enabled : true
         */

        private boolean is_enabled;

        public boolean isIs_enabled() {
            return is_enabled;
        }

        public void setIs_enabled(boolean is_enabled) {
            this.is_enabled = is_enabled;
        }
    }
}
