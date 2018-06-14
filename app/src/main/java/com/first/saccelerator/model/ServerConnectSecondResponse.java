package com.first.saccelerator.model;

/**
 * Created by XQ on 2017/4/26.
 * 按指定服务器类型连接(认证)
 */
public class ServerConnectSecondResponse {


    /**
     * user_node_type_is_expired : false
     * is_consumed : false
     * proxy_session_token : WU3sxzjK3RmMExXsSVwB
     * user : {"current_coins":9958}
     * user_node_type : {"id":616,"expired_at":1493203060,"status":"按次","used_count":3}
     * node : {"id":1,"name":"香港01","url":"119.23.64.170","port":8388,"encrypt_method":"aes-128-cfb","password":"asdf"}
     */

    private boolean user_node_type_is_expired;
    private boolean is_consumed;
    private String proxy_session_token;
    private UserBean user;
    private UserNodeTypeBean user_node_type;
    private NodeBean node;

    public boolean isUser_node_type_is_expired() {
        return user_node_type_is_expired;
    }

    public void setUser_node_type_is_expired(boolean user_node_type_is_expired) {
        this.user_node_type_is_expired = user_node_type_is_expired;
    }

    public boolean isIs_consumed() {
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

    public NodeBean getNode() {
        return node;
    }

    public void setNode(NodeBean node) {
        this.node = node;
    }

    public static class UserBean {
        /**
         * current_coins : 9958
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
         * id : 616
         * expired_at : 1493203060
         * status : 按次
         * used_count : 3
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

    public static class NodeBean {
        /**
         * id : 1
         * name : 香港01
         * url : 119.23.64.170
         * port : 8388
         * encrypt_method : aes-128-cfb
         * password : asdf
         */

        private int id;
        private String name;
        private String url;
        private int port;
        private String encrypt_method;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getEncrypt_method() {
            return encrypt_method;
        }

        public void setEncrypt_method(String encrypt_method) {
            this.encrypt_method = encrypt_method;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
