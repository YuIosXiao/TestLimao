package com.first.saccelerator.model;

import java.util.List;

/**
 * Created by XQ on 2017/7/19.
 * 用户注册
 */
public class RegisteredResponse {


    /**
     * proxy_session_id : 151
     * proxy_session_token : jSNuIE79GowEljwb9vDuHh007drcpOTGW-eeJBSwcTc=
     * app_launch_id : 61622080-6b6f-11e7-bf54-784f4352f7e7
     * signin_log_id : 615f07b0-6b6f-11e7-bf54-784f4352f7e7
     * user : {"api_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTU2LCJ1c2VybmFtZSI6IjEzODg0NjMzMzk2IiwiZGV2aWNlX2lkIjoxNTR9.gyzxNP7fuCBZYA9eE_vtJl-w3hN3QaUUtFxDe9FBDZc","id":156,"username":"13884644496","email":null,"total_payment_amount":"0.0","current_coins":5,"total_coins":0,"password":"KmnCus5VN49nD2gpzwFooA==","used_bytes":0,"max_bytes":0,"limit_bytes":0,"is_checkin_today":false,"is_enabled":true,"created_at":1500351177,"new_message":false,"promo_code":"hgybrw","promo_users_count":0,"promo_coins_count":0,"binded_promo_code":"kdiekd"}
     * group : {"id":1,"name":"VIP0","level":1}
     * user_node_types : [{"id":332,"name":"精英服务","level":1,"status":"按次","expired_at":1500351177,"used_count":0,"node_type_id":1},{"id":333,"name":"王者服务","level":2,"status":"按次","expired_at":1500351177,"used_count":0,"node_type_id":2}]
     * node_types : [{"id":1,"name":"精英服务","level":1,"expense_coins":1,"user_group_id":1,"user_group_level":1,"description":"访问Facebook、twitter、instagram等网站；使用telegram等通讯工具；|当月累计使用20次，本月即可免费使用该服务"},{"id":2,"name":"王者服务","level":2,"expense_coins":3,"user_group_id":1,"user_group_level":1,"description":"享受\u201c精英服务\u201d所有内容；|流畅观看高清youtube、twich视频；|当月累计使用18次，本月即可免费使用该服务。"}]
     * ip_region : {"country":"本机地址","province":"本机地址","city":null}
     * settings : {"NOTICE_CONTENT":"all","QQ_GROUP":"敬请期待","WX_OFFICAL_ACCOUNT":"敬请期待","TELEGRAM_GROUP":"https://t.me/limaojiasuqi","OFFICIAL_WEBSITE":"http://www.limaojs.com/","ALLOW_SEND_LOG":"true","LOG_POOL_MAX_COUNT":"50","POST_LOG_INTERVAL":"600","UPDATE_URL":"http://www.baidu.com/","SHARE_URL":"http://share.licatjsq.com","SHARE_IMG":"https://licatjsq.com/share/1024.png","ANDROID_VERSION":"1.0.8|1","UPDATE_CONTENT":"<div style='text-align:center;'><strong><span style = 'line-height:2;color:#000000;font-size:18px'>老用户须知<\/span><\/strong><\/div> <span style = 'line-height:2;color:#575757;font-size:15px'>欢迎使用狸猫加速器<\/span>","IS_UPDATE":"0"}
     */

    private int proxy_session_id;
    private String proxy_session_token;
    private String app_launch_id;
    private String signin_log_id;
    private UserBean user;
    private GroupBean group;
    private IpRegionBean ip_region;
    private SettingsBean settings;
    private List<UserNodeTypesBean> user_node_types;
    private List<NodeTypesBean> node_types;

    public int getProxy_session_id() {
        return proxy_session_id;
    }

    public void setProxy_session_id(int proxy_session_id) {
        this.proxy_session_id = proxy_session_id;
    }

    public String getProxy_session_token() {
        return proxy_session_token;
    }

    public void setProxy_session_token(String proxy_session_token) {
        this.proxy_session_token = proxy_session_token;
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

    public IpRegionBean getIp_region() {
        return ip_region;
    }

    public void setIp_region(IpRegionBean ip_region) {
        this.ip_region = ip_region;
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
         * api_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTU2LCJ1c2VybmFtZSI6IjEzODg0NjMzMzk2IiwiZGV2aWNlX2lkIjoxNTR9.gyzxNP7fuCBZYA9eE_vtJl-w3hN3QaUUtFxDe9FBDZc
         * id : 156
         * username : 13884644496
         * email : null
         * total_payment_amount : 0.0
         * current_coins : 5
         * total_coins : 0
         * password : KmnCus5VN49nD2gpzwFooA==
         * used_bytes : 0
         * max_bytes : 0
         * limit_bytes : 0
         * is_checkin_today : false
         * is_enabled : true
         * created_at : 1500351177
         * new_message : false
         * promo_code : hgybrw
         * promo_users_count : 0
         * promo_coins_count : 0
         * binded_promo_code : kdiekd
         */

        private String api_token;
        private int id;
        private String username;
        private String email;
        private String wechat;
        private String weibo;
        private String qq;
        private String total_payment_amount;
        private int current_coins;
        private int total_coins;
        private String password;
        private int used_bytes;
        private int max_bytes;
        private int limit_bytes;
        private boolean is_checkin_today;
        private boolean is_enabled;
        private int created_at;
        private boolean new_message;
        private String promo_code;
        private int promo_users_count;
        private int promo_coins_count;
        private String binded_promo_code;

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

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getPromo_code() {
            return promo_code;
        }

        public void setPromo_code(String promo_code) {
            this.promo_code = promo_code;
        }

        public int getPromo_users_count() {
            return promo_users_count;
        }

        public void setPromo_users_count(int promo_users_count) {
            this.promo_users_count = promo_users_count;
        }

        public int getPromo_coins_count() {
            return promo_coins_count;
        }

        public void setPromo_coins_count(int promo_coins_count) {
            this.promo_coins_count = promo_coins_count;
        }

        public String getBinded_promo_code() {
            return binded_promo_code;
        }

        public void setBinded_promo_code(String binded_promo_code) {
            this.binded_promo_code = binded_promo_code;
        }
    }

    public static class GroupBean {
        /**
         * id : 1
         * name : VIP0
         * level : 1
         */

        private int id;
        private String name;
        private int level;

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
    }

    public static class IpRegionBean {
        /**
         * country : 本机地址
         * province : 本机地址
         * city : null
         */

        private String country;
        private String province;
        private Object city;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }
    }

    public static class SettingsBean {
        /**
         * NOTICE_CONTENT : all
         * QQ_GROUP : 敬请期待
         * WX_OFFICAL_ACCOUNT : 敬请期待
         * TELEGRAM_GROUP : https://t.me/limaojiasuqi
         * OFFICIAL_WEBSITE : http://www.limaojs.com/
         * ALLOW_SEND_LOG : true
         * LOG_POOL_MAX_COUNT : 50
         * POST_LOG_INTERVAL : 600
         * UPDATE_URL : http://www.baidu.com/
         * SHARE_URL : http://share.licatjsq.com
         * SHARE_IMG : https://licatjsq.com/share/1024.png
         * ANDROID_VERSION : 1.0.8|1
         * UPDATE_CONTENT : <div style='text-align:center;'><strong><span style = 'line-height:2;color:#000000;font-size:18px'>老用户须知</span></strong></div> <span style = 'line-height:2;color:#575757;font-size:15px'>欢迎使用狸猫加速器</span>
         * IS_UPDATE : 0
         */

        private String NOTICE_CONTENT;
        private String QQ_GROUP;
        private String WX_OFFICAL_ACCOUNT;
        private String TELEGRAM_GROUP;
        private String OFFICIAL_WEBSITE;
        private String ALLOW_SEND_LOG;
        private String LOG_POOL_MAX_COUNT;
        private String POST_LOG_INTERVAL;
        private String UPDATE_URL;
        private String SHARE_URL;
        private String SHARE_IMG;
        private String ANDROID_VERSION;
        private String UPDATE_CONTENT;
        private String IS_UPDATE;

        private String DLOG_ALLOW_SEND;
        private String DLOG_POOL_MAX_COUNT;
        private String DLOG_POST_INTERVAL;

        private String FLOG_ALLOW_SEND;
        private String FLOG_POOL_MAX_COUNT;
        private String FLOG_POST_INTERVAL;
        private String FLOG_CLEAN_INTERVAL;

        public SettingsBean(String NOTICE_CONTENT, String SHARE_URL, String ANDROID_VERSION, String IS_UPDATE, String UPDATE_CONTENT, String SHARE_IMG) {
            this.NOTICE_CONTENT = NOTICE_CONTENT;
            this.SHARE_URL = SHARE_URL;
            this.ANDROID_VERSION = ANDROID_VERSION;
            this.IS_UPDATE = IS_UPDATE;
            this.UPDATE_CONTENT = UPDATE_CONTENT;
            this.SHARE_IMG = SHARE_IMG;
        }

        public String getNOTICE_CONTENT() {
            return NOTICE_CONTENT;
        }

        public void setNOTICE_CONTENT(String NOTICE_CONTENT) {
            this.NOTICE_CONTENT = NOTICE_CONTENT;
        }

        public String getQQ_GROUP() {
            return QQ_GROUP;
        }

        public void setQQ_GROUP(String QQ_GROUP) {
            this.QQ_GROUP = QQ_GROUP;
        }

        public String getWX_OFFICAL_ACCOUNT() {
            return WX_OFFICAL_ACCOUNT;
        }

        public void setWX_OFFICAL_ACCOUNT(String WX_OFFICAL_ACCOUNT) {
            this.WX_OFFICAL_ACCOUNT = WX_OFFICAL_ACCOUNT;
        }

        public String getTELEGRAM_GROUP() {
            return TELEGRAM_GROUP;
        }

        public void setTELEGRAM_GROUP(String TELEGRAM_GROUP) {
            this.TELEGRAM_GROUP = TELEGRAM_GROUP;
        }

        public String getOFFICIAL_WEBSITE() {
            return OFFICIAL_WEBSITE;
        }

        public void setOFFICIAL_WEBSITE(String OFFICIAL_WEBSITE) {
            this.OFFICIAL_WEBSITE = OFFICIAL_WEBSITE;
        }

        public String getALLOW_SEND_LOG() {
            return ALLOW_SEND_LOG;
        }

        public void setALLOW_SEND_LOG(String ALLOW_SEND_LOG) {
            this.ALLOW_SEND_LOG = ALLOW_SEND_LOG;
        }

        public String getLOG_POOL_MAX_COUNT() {
            return LOG_POOL_MAX_COUNT;
        }

        public void setLOG_POOL_MAX_COUNT(String LOG_POOL_MAX_COUNT) {
            this.LOG_POOL_MAX_COUNT = LOG_POOL_MAX_COUNT;
        }

        public String getPOST_LOG_INTERVAL() {
            return POST_LOG_INTERVAL;
        }

        public void setPOST_LOG_INTERVAL(String POST_LOG_INTERVAL) {
            this.POST_LOG_INTERVAL = POST_LOG_INTERVAL;
        }

        public String getUPDATE_URL() {
            return UPDATE_URL;
        }

        public void setUPDATE_URL(String UPDATE_URL) {
            this.UPDATE_URL = UPDATE_URL;
        }

        public String getSHARE_URL() {
            return SHARE_URL;
        }

        public void setSHARE_URL(String SHARE_URL) {
            this.SHARE_URL = SHARE_URL;
        }

        public String getSHARE_IMG() {
            return SHARE_IMG;
        }

        public void setSHARE_IMG(String SHARE_IMG) {
            this.SHARE_IMG = SHARE_IMG;
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

        public String getDLOG_ALLOW_SEND() {
            return DLOG_ALLOW_SEND;
        }

        public void setDLOG_ALLOW_SEND(String DLOG_ALLOW_SEND) {
            this.DLOG_ALLOW_SEND = DLOG_ALLOW_SEND;
        }

        public String getDLOG_POOL_MAX_COUNT() {
            return DLOG_POOL_MAX_COUNT;
        }

        public void setDLOG_POOL_MAX_COUNT(String DLOG_POOL_MAX_COUNT) {
            this.DLOG_POOL_MAX_COUNT = DLOG_POOL_MAX_COUNT;
        }

        public String getDLOG_POST_INTERVAL() {
            return DLOG_POST_INTERVAL;
        }

        public void setDLOG_POST_INTERVAL(String DLOG_POST_INTERVAL) {
            this.DLOG_POST_INTERVAL = DLOG_POST_INTERVAL;
        }

        public String getFLOG_ALLOW_SEND() {
            return FLOG_ALLOW_SEND;
        }

        public void setFLOG_ALLOW_SEND(String FLOG_ALLOW_SEND) {
            this.FLOG_ALLOW_SEND = FLOG_ALLOW_SEND;
        }

        public String getFLOG_CLEAN_INTERVAL() {
            return FLOG_CLEAN_INTERVAL;
        }

        public void setFLOG_CLEAN_INTERVAL(String FLOG_CLEAN_INTERVAL) {
            this.FLOG_CLEAN_INTERVAL = FLOG_CLEAN_INTERVAL;
        }

        public String getFLOG_POOL_MAX_COUNT() {
            return FLOG_POOL_MAX_COUNT;
        }

        public void setFLOG_POOL_MAX_COUNT(String FLOG_POOL_MAX_COUNT) {
            this.FLOG_POOL_MAX_COUNT = FLOG_POOL_MAX_COUNT;
        }

        public String getFLOG_POST_INTERVAL() {
            return FLOG_POST_INTERVAL;
        }

        public void setFLOG_POST_INTERVAL(String FLOG_POST_INTERVAL) {
            this.FLOG_POST_INTERVAL = FLOG_POST_INTERVAL;
        }
    }

    public static class UserNodeTypesBean {
        /**
         * id : 332
         * name : 精英服务
         * level : 1
         * status : 按次
         * expired_at : 1500351177
         * used_count : 0
         * node_type_id : 1
         */

        private int id;
        private String name;
        private int level;
        private String status;
        private int expired_at;
        private int used_count;
        private int node_type_id;

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

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
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
         * name : 精英服务
         * level : 1
         * expense_coins : 1
         * user_group_id : 1
         * user_group_level : 1
         * description : 访问Facebook、twitter、instagram等网站；使用telegram等通讯工具；|当月累计使用20次，本月即可免费使用该服务
         */

        private int id;
        private String name;
        private int level;
        private int expense_coins;
        private int user_group_id;
        private int user_group_level;
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
