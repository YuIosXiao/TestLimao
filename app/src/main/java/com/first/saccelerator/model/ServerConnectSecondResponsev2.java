package com.first.saccelerator.model;

/**
 * Created by admin on 2017/7/19.
 * 按指定服务器类型连接(认证)
 * v2
 */
public class ServerConnectSecondResponsev2 {


    /**
     * user_node_type_is_expired : false
     * is_consumed : false
     * proxy_session_token : Ywvz4YkAi_b9hU2IuGnxRSOePJJKz6tfk95N1PF_y1s=
     * user : {"current_coins":9958}
     * user_node_type : {"id":616,"expired_at":1493203060,"status":"按次","used_count":3}
     * node : {"id":1,"name":"pkSN75HEoq9nWhuVTThRvA==","url":"LdXg1+meZqeQ4vi6SLeDBg==","port":"OWJ+dDe88OnJ30DtKx/vEg==","encrypt_method":"pj2110m0YMAY/gpHz4tdfA==","password":"YLvZlicyeQ6Um7Cg6vxLTA=="}
     */

    private boolean user_node_type_is_expired;
    private boolean is_consumed;
    private String proxy_session_token;
    private UserBean user;
    private UserNodeTypeBean user_node_type;
    private NodeBean node;
    private int tab_id;

    public int getTab_id() {
        return tab_id;
    }

    public void setTab_id(int tab_id) {
        this.tab_id = tab_id;
    }

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
        private long expired_at;
        private String status;
        private int used_count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(long expired_at) {
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
         * name : pkSN75HEoq9nWhuVTThRvA==
         * url : LdXg1+meZqeQ4vi6SLeDBg==
         * port : OWJ+dDe88OnJ30DtKx/vEg==
         * encrypt_method : pj2110m0YMAY/gpHz4tdfA==
         * password : YLvZlicyeQ6Um7Cg6vxLTA==
         */

        private int id;
        private String name;
        private String url;
        private String port;
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

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
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
