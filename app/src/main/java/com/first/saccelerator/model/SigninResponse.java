package com.first.saccelerator.model;

import java.util.List;

/**
 * 用户登录
 * Created by ZhengSheng on 2017/3/21.
 */

public class SigninResponse {


    /**
     * only_has_username : false
     * proxy_session_id : 126
     * app_launch_id : 123123
     * signin_log_id : f426f784-263b-11e7-bad9-00163e060048
     * user : {"api_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTI2LCJ1c2VybmFtZSI6IjEzNTU0Mjk4MzY5IiwiZGV2aWNlX2lkIjoxMjZ9._1vuMfofGKBiwR3EtgDlIq6ww8EhkyRDaSjQ5HdYyg4","id":126,"username":"13554298369","password":"123456","email":null,"wechat":null,"weibo":null,"qq":null,"used_bytes":0,"max_bytes":0,"limit_bytes":0,"total_payment_amount":"0.0","current_coins":0,"total_coins":0,"is_checkin_today":false,"is_enabled":true,"created_at":1492481125,"new_message":false}
     * group : {"id":1,"name":"vip0","level":1,"next_need_coins":2}
     * device : {"id":126,"uuid":"f014077b-b4cf-3af3-b94b-61aa5c0a4758"}
     * user_node_types : [{"id":626,"name":"青铜服","level":1,"status":"按次","expired_at":1492481125,"used_count":0,"node_type_id":1},{"id":627,"name":"白银服","level":2,"status":"按次","expired_at":1492481125,"used_count":0,"node_type_id":2},{"id":628,"name":"黄金服","level":3,"status":"按次","expired_at":1492481125,"used_count":0,"node_type_id":3},{"id":629,"name":"铂金服","level":4,"status":"按次","expired_at":1492481125,"used_count":0,"node_type_id":4},{"id":630,"name":"钻石服","level":5,"status":"按次","expired_at":1492481125,"used_count":0,"node_type_id":5}]
     * node_types : [{"id":1,"name":"青铜服","level":1,"expense_coins":1,"user_group_id":1,"user_group_level":1,"node_regions":[{"id":3,"name":"美国","abbr":"us","nodes":[{"id":7,"name":"美国01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":8,"name":"美国02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":9,"name":"美国03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300}]},{"id":4,"name":"体验服","abbr":"cn","nodes":[]},{"id":5,"name":"测试服3","abbr":"cn","nodes":[]}]},{"id":2,"name":"白银服","level":2,"expense_coins":2,"user_group_id":3,"user_group_level":3,"node_regions":[{"id":1,"name":"香港","abbr":"hk","nodes":[{"id":1,"name":"香港01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":2,"name":"香港02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":3,"name":"香港03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300}]}]},{"id":3,"name":"黄金服","level":3,"expense_coins":5,"user_group_id":5,"user_group_level":5,"node_regions":[{"id":2,"name":"日本","abbr":"jp","nodes":[{"id":4,"name":"日本01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":5,"name":"日本02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":6,"name":"日本03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300}]}]},{"id":4,"name":"铂金服","level":4,"expense_coins":10,"user_group_id":7,"user_group_level":7,"node_regions":[]},{"id":5,"name":"钻石服","level":5,"expense_coins":20,"user_group_id":9,"user_group_level":9,"node_regions":[]}]
     * settings : {"NOTICE_CONTENT":"<div style=\"text-align:center;\"><strong><span style = \"line-height:2;color:#000000;font-size:18px\">老用户须知<\/span><\/strong><\/div> <span style = \"line-height:1.5;color:#575757;font-size:15px\">通过爱上VPN、风云VPN、急速VPN、爱上VPN pro四个收费版本强更至极光加速器免费版的用户，如果在四个老版本中申请了包月，可通过意见反馈申请退款，想体验新功能的用户可通过APPStore搜索\u201c极光加速器\u201d下载最新版<\/span>","SHARE_URL":"http://www.baidu.com/","ANDROID_VERSION":"1.0.1|0","UPDATE_CONTENT":"<div style=\"text-align:center;\"><strong><span style = \"line-height:2;color:#000000;font-size:18px\">老更新提示<\/span><\/strong><\/div> <span style = \"line-height:1.5;color:#575757;font-size:15px\">这是一个更新提示信息<\/span>","IS_UPDATE":"0"}
     */

    private boolean only_has_username;
    private int proxy_session_id;
    private String app_launch_id;
    private String signin_log_id;
    private UserBean user;
    private GroupBean group;
    private DeviceBean device;
    private SettingsBean settings;
    private List<UserNodeTypesBean> user_node_types;
    private List<NodeTypesBean> node_types;

    private String username;
    private String proxy_session_token;

    public String getProxy_session_token() {
        return proxy_session_token;
    }

    public void setProxy_session_token(String proxy_session_token) {
        this.proxy_session_token = proxy_session_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnly_has_username() {
        return only_has_username;
    }

    public void setOnly_has_username(boolean only_has_username) {
        this.only_has_username = only_has_username;
    }

    public int getProxy_session_id() {
        return proxy_session_id;
    }

    public void setProxy_session_id(int proxy_session_id) {
        this.proxy_session_id = proxy_session_id;
    }

    public String getApp_launch_id() {
        return app_launch_id;
    }

    public void setApp_launch_id(String app_launch_id) {
        this.app_launch_id = app_launch_id;
    }

    public String getSignin_log_id() {
        return signin_log_id;
    }

    public void setSignin_log_id(String signin_log_id) {
        this.signin_log_id = signin_log_id;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public SettingsBean getSettings() {
        return settings;
    }

    public void setSettings(SettingsBean settings) {
        this.settings = settings;
    }

    public List<UserNodeTypesBean> getUser_node_types() {
        return user_node_types;
    }

    public void setUser_node_types(List<UserNodeTypesBean> user_node_types) {
        this.user_node_types = user_node_types;
    }

    public List<NodeTypesBean> getNode_types() {
        return node_types;
    }

    public void setNode_types(List<NodeTypesBean> node_types) {
        this.node_types = node_types;
    }

    public static class UserBean {
        /**
         * api_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTI2LCJ1c2VybmFtZSI6IjEzNTU0Mjk4MzY5IiwiZGV2aWNlX2lkIjoxMjZ9._1vuMfofGKBiwR3EtgDlIq6ww8EhkyRDaSjQ5HdYyg4
         * id : 126
         * username : 13554298369
         * password : 123456
         * email : null
         * wechat : null
         * weibo : null
         * qq : null
         * used_bytes : 0
         * max_bytes : 0
         * limit_bytes : 0
         * total_payment_amount : 0.0
         * current_coins : 0
         * total_coins : 0
         * is_checkin_today : false
         * is_enabled : true
         * created_at : 1492481125
         * new_message : false
         */

        private String api_token;
        private int id;
        private String username;
        private String password;
        private String email;
        private String wechat;
        private String weibo;
        private String qq;
        private int used_bytes;
        private int max_bytes;
        private int limit_bytes;
        private String total_payment_amount;
        private int current_coins;
        private int total_coins;
        private boolean is_checkin_today;
        private boolean is_enabled;
        private int created_at;
        private boolean new_message;

        public String getApi_token() {
            return api_token;
        }

        public void setApi_token(String api_token) {
            this.api_token = api_token;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public boolean is_checkin_today() {
            return is_checkin_today;
        }

        public boolean is_enabled() {
            return is_enabled;
        }

        public int getUsed_bytes() {
            return used_bytes;
        }

        public void setUsed_bytes(int used_bytes) {
            this.used_bytes = used_bytes;
        }

        public int getMax_bytes() {
            return max_bytes;
        }

        public void setMax_bytes(int max_bytes) {
            this.max_bytes = max_bytes;
        }

        public int getLimit_bytes() {
            return limit_bytes;
        }

        public void setLimit_bytes(int limit_bytes) {
            this.limit_bytes = limit_bytes;
        }

        public String getTotal_payment_amount() {
            return total_payment_amount;
        }

        public void setTotal_payment_amount(String total_payment_amount) {
            this.total_payment_amount = total_payment_amount;
        }

        public int getCurrent_coins() {
            return current_coins;
        }

        public void setCurrent_coins(int current_coins) {
            this.current_coins = current_coins;
        }

        public int getTotal_coins() {
            return total_coins;
        }

        public void setTotal_coins(int total_coins) {
            this.total_coins = total_coins;
        }

        public boolean isIs_checkin_today() {
            return is_checkin_today;
        }

        public void setIs_checkin_today(boolean is_checkin_today) {
            this.is_checkin_today = is_checkin_today;
        }

        public boolean isIs_enabled() {
            return is_enabled;
        }

        public void setIs_enabled(boolean is_enabled) {
            this.is_enabled = is_enabled;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public boolean isNew_message() {
            return new_message;
        }

        public void setNew_message(boolean new_message) {
            this.new_message = new_message;
        }
    }

    public static class GroupBean {
        /**
         * id : 1
         * name : vip0
         * level : 1
         * next_need_coins : 2
         */

        private int id;
        private String name;
        private int level;
        private int next_need_coins;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getNext_need_coins() {
            return next_need_coins;
        }

        public void setNext_need_coins(int next_need_coins) {
            this.next_need_coins = next_need_coins;
        }
    }

    public static class DeviceBean {
        /**
         * id : 126
         * uuid : f014077b-b4cf-3af3-b94b-61aa5c0a4758
         */

        private int id;
        private String uuid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }

    public static class SettingsBean {
        /**
         * NOTICE_CONTENT : <div style="text-align:center;"><strong><span style = "line-height:2;color:#000000;font-size:18px">老用户须知</span></strong></div> <span style = "line-height:1.5;color:#575757;font-size:15px">通过爱上VPN、风云VPN、急速VPN、爱上VPN pro四个收费版本强更至极光加速器免费版的用户，如果在四个老版本中申请了包月，可通过意见反馈申请退款，想体验新功能的用户可通过APPStore搜索“极光加速器”下载最新版</span>
         * SHARE_URL : http://www.baidu.com/
         * ANDROID_VERSION : 1.0.1|0
         * UPDATE_CONTENT : <div style="text-align:center;"><strong><span style = "line-height:2;color:#000000;font-size:18px">老更新提示</span></strong></div> <span style = "line-height:1.5;color:#575757;font-size:15px">这是一个更新提示信息</span>
         * IS_UPDATE : 0
         */

        private String NOTICE_CONTENT;
        private String SHARE_URL;
        private String ANDROID_VERSION;
        private String UPDATE_CONTENT;
        private String IS_UPDATE;
        private String SHARE_IMG;

        public SettingsBean(String NOTICE_CONTENT, String SHARE_URL, String ANDROID_VERSION, String UPDATE_CONTENT, String IS_UPDATE, String SHARE_IMG) {
            this.NOTICE_CONTENT = NOTICE_CONTENT;
            this.SHARE_URL = SHARE_URL;
            this.ANDROID_VERSION = ANDROID_VERSION;
            this.UPDATE_CONTENT = UPDATE_CONTENT;
            this.IS_UPDATE = IS_UPDATE;
            this.SHARE_IMG = SHARE_IMG;
        }

        public String getSHARE_IMG() {
            return SHARE_IMG;
        }

        public void setSHARE_IMG(String SHARE_IMG) {
            this.SHARE_IMG = SHARE_IMG;
        }

        public String getNOTICE_CONTENT() {
            return NOTICE_CONTENT;
        }

        public void setNOTICE_CONTENT(String NOTICE_CONTENT) {
            this.NOTICE_CONTENT = NOTICE_CONTENT;
        }

        public String getSHARE_URL() {
            return SHARE_URL;
        }

        public void setSHARE_URL(String SHARE_URL) {
            this.SHARE_URL = SHARE_URL;
        }

        public String getANDROID_VERSION() {
            return ANDROID_VERSION;
        }

        public void setANDROID_VERSION(String ANDROID_VERSION) {
            this.ANDROID_VERSION = ANDROID_VERSION;
        }

        public String getUPDATE_CONTENT() {
            return UPDATE_CONTENT;
        }

        public void setUPDATE_CONTENT(String UPDATE_CONTENT) {
            this.UPDATE_CONTENT = UPDATE_CONTENT;
        }

        public String getIS_UPDATE() {
            return IS_UPDATE;
        }

        public void setIS_UPDATE(String IS_UPDATE) {
            this.IS_UPDATE = IS_UPDATE;
        }
    }

    public static class UserNodeTypesBean {
        /**
         * id : 626
         * name : 青铜服
         * level : 1
         * status : 按次
         * expired_at : 1492481125
         * used_count : 0
         * node_type_id : 1
         */

        private int id;
        private String name;
        private int level;
        private String status;
        private long expired_at;
        private int used_count;
        private int node_type_id;

        public UserNodeTypesBean(int id, String name, int level, String status, long expired_at, int used_count, int node_type_id) {
            this.id = id;
            this.name = name;
            this.level = level;
            this.status = status;
            this.expired_at = expired_at;
            this.used_count = used_count;
            this.node_type_id = node_type_id;
        }

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(long expired_at) {
            this.expired_at = expired_at;
        }

        public int getUsed_count() {
            return used_count;
        }

        public void setUsed_count(int used_count) {
            this.used_count = used_count;
        }

        public int getNode_type_id() {
            return node_type_id;
        }

        public void setNode_type_id(int node_type_id) {
            this.node_type_id = node_type_id;
        }
    }

    public static class NodeTypesBean {
        /**
         * id : 1
         * name : 青铜服
         * level : 1
         * expense_coins : 1
         * user_group_id : 1
         * user_group_level : 1
         * node_regions : [{"id":3,"name":"美国","abbr":"us","nodes":[{"id":7,"name":"美国01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":8,"name":"美国02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":9,"name":"美国03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300}]},{"id":4,"name":"体验服","abbr":"cn","nodes":[]},{"id":5,"name":"测试服3","abbr":"cn","nodes":[]}]
         */

        private int id;
        private String name;
        private int level;
        private int expense_coins;
        private int user_group_id;
        private int user_group_level;
        private List<NodeRegionsBean> node_regions;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getExpense_coins() {
            return expense_coins;
        }

        public void setExpense_coins(int expense_coins) {
            this.expense_coins = expense_coins;
        }

        public int getUser_group_id() {
            return user_group_id;
        }

        public void setUser_group_id(int user_group_id) {
            this.user_group_id = user_group_id;
        }

        public int getUser_group_level() {
            return user_group_level;
        }

        public void setUser_group_level(int user_group_level) {
            this.user_group_level = user_group_level;
        }

        public List<NodeRegionsBean> getNode_regions() {
            return node_regions;
        }

        public void setNode_regions(List<NodeRegionsBean> node_regions) {
            this.node_regions = node_regions;
        }

        public static class NodeRegionsBean {
            /**
             * id : 3
             * name : 美国
             * abbr : us
             * nodes : [{"id":7,"name":"美国01","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":8,"name":"美国02","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300},{"id":9,"name":"美国03","url":"119.23.64.170","port":8388,"password":"asdf","encrypt_method":"aes-128-cfb","connections_count":0,"max_connections_count":300}]
             */

            private int id;
            private String name;
            private String abbr;
            private List<NodesBean> nodes;

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

            public String getAbbr() {
                return abbr;
            }

            public void setAbbr(String abbr) {
                this.abbr = abbr;
            }

            public List<NodesBean> getNodes() {
                return nodes;
            }

            public void setNodes(List<NodesBean> nodes) {
                this.nodes = nodes;
            }

            public static class NodesBean {
                /**
                 * id : 7
                 * name : 美国01
                 * url : 119.23.64.170
                 * port : 8388
                 * password : asdf
                 * encrypt_method : aes-128-cfb
                 * connections_count : 0
                 * max_connections_count : 300
                 */

                private int id;
                private String name;
                private String url;
                private int port;
                private String password;
                private String encrypt_method;
                private int connections_count;
                private int max_connections_count;

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

                public String getPassword() {
                    return password;
                }

                public void setPassword(String password) {
                    this.password = password;
                }

                public String getEncrypt_method() {
                    return encrypt_method;
                }

                public void setEncrypt_method(String encrypt_method) {
                    this.encrypt_method = encrypt_method;
                }

                public int getConnections_count() {
                    return connections_count;
                }

                public void setConnections_count(int connections_count) {
                    this.connections_count = connections_count;
                }

                public int getMax_connections_count() {
                    return max_connections_count;
                }

                public void setMax_connections_count(int max_connections_count) {
                    this.max_connections_count = max_connections_count;
                }
            }
        }
    }
}
