package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/13.
 * 连接指定服务器线路(认证)解析类
 */
public class ServerConnectResponse {


    /**
     * proxy_session_token : aypbDmqsgrr7wATfrzkX
     * user : {"current_coins":9959}
     * user_node_type : {"id":20,"expired_at":1491566486,"status":"按次","used_count":2}
     */

    private boolean user_node_type_is_expired;
    private boolean is_consumed;
    private String proxy_session_token;
    private UserBean user;
    private UserNodeTypeBean user_node_type;

    public boolean isUser_node_type_is_expired() {
        return user_node_type_is_expired;
    }

    public void setUser_node_type_is_expired(boolean user_node_type_is_expired) {
        this.user_node_type_is_expired = user_node_type_is_expired;
    }

    public boolean is_consumed() {
        return is_consumed;
    }

    public void setIs_consumed(boolean is_consumed) {
        this.is_consumed = is_consumed;
    }

    public String getProxy_session_token() {
        return proxy_session_token;
    }

    public void setProxy_session_token(String proxy_session_token) {
        this.proxy_session_token = proxy_session_token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserNodeTypeBean getUser_node_type() {
        return user_node_type;
    }

    public void setUser_node_type(UserNodeTypeBean user_node_type) {
        this.user_node_type = user_node_type;
    }

    public static class UserBean {
        /**
         * current_coins : 9959
         */

        private int current_coins;

        public int getCurrent_coins() {
            return current_coins;
        }

        public void setCurrent_coins(int current_coins) {
            this.current_coins = current_coins;
        }
    }

    public static class UserNodeTypeBean {
        /**
         * id : 20
         * expired_at : 1491566486
         * status : 按次
         * used_count : 2
         */

        private int id;
        private int expired_at;
        private String status;
        private int used_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
            this.expired_at = expired_at;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUsed_count() {
            return used_count;
        }

        public void setUsed_count(int used_count) {
            this.used_count = used_count;
        }
    }

    public ServerConnectResponse(String proxy_session_token, UserBean user, UserNodeTypeBean user_node_type) {
        this.proxy_session_token = proxy_session_token;
        this.user = user;
        this.user_node_type = user_node_type;
    }


    @Override
    public String toString() {
        return "ServerConnectResponse{" +
                "user_node_type_is_expired=" + user_node_type_is_expired +
                ", is_consumed=" + is_consumed +
                ", proxy_session_token='" + proxy_session_token + '\'' +
                ", user=" + user +
                ", user_node_type=" + user_node_type +
                '}';
    }
}
