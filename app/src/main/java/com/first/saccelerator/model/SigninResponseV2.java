package com.first.saccelerator.model;

import java.util.List;

/**
 * 用户登录
 * Created by XQ on 2017/6/22.
 */
public class SigninResponseV2 {


    /**
     * only_has_username : false
     * proxy_session_id : 163
     * proxy_session_token : cz6lFjMdpo
     * app_launch_id : 823ecb84-1b72-11e7-845d-784f4352f7e7
     * signin_log_id : 823adbaa-1b72-11e7-845d-784f4352f7e7
     * user : {"api_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTYzLCJ1c2VybmFtZSI6InN1cDAwMDAwMTYzIiwiZGV2aWNlX2lkIjoxNjF9.yERJ8mfGk4MXuQUQWt8JT32x16WLetoonoNBZ0Y9nNg","id":163,"username":"sup00000163","password":"kvsxvzz7","email":null,"wechat":null,"weibo":null,"qq":null,"used_bytes":0,"max_bytes":0,"limit_bytes":0,"total_payment_amount":"0.0","current_coins":0,"total_coins":0,"is_checkin_today":false,"is_enabled":true,"created_at":1491556428,"new_message":false}
     * group : {"id":1,"name":"vip0","level":1}
     * device : {"id":161,"uuid":"a1de3867-7468-4002-8dd4-554707c4edc5"}
     * user_node_types : [{"id":811,"name":"青铜服","level":1,"status":"按次","expired_at":1491556428,"used_count":0,"node_type_id":1},{"id":815,"name":"钻石服","level":5,"status":"按次","expired_at":1491556428,"used_count":0,"node_type_id":5}]
     * node_types : [{"id":1,"name":"青铜服","level":1,"expense_coins":1,"user_group_id":1,"user_group_level":1},{"id":3,"name":"黄金服","level":3,"expense_coins":5,"user_group_id":5,"user_group_level":5},{"id":5,"name":"钻石服","level":5,"expense_coins":20,"user_group_id":9,"user_group_level":9}]
     * settings : {"APPSTORE_ID":"1206882364","IOS_VERSION":"1.0.3|1","SHARE_IMG":"http://120.77.247.50/ui/img/logo.png","IS_UPDATE":"1"}
     */

    private boolean only_has_username;
    private int proxy_session_id;
    private String proxy_session_token;
    private String app_launch_id;
    private String signin_log_id;
    private UserBean user;
    private GroupBean group;
    private DeviceBean device;
    private SettingsBean settings;
    private List<UserNodeTypesBean> user_node_types;
    private List<NodeTypesBean> node_types;

    private String username;

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
         * api_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTYzLCJ1c2VybmFtZSI6InN1cDAwMDAwMTYzIiwiZGV2aWNlX2lkIjoxNjF9.yERJ8mfGk4MXuQUQWt8JT32x16WLetoonoNBZ0Y9nNg
         * id : 163
         * username : sup00000163
         * password : kvsxvzz7
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
         * created_at : 1491556428
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
        private long used_bytes;
        private long max_bytes;
        private long limit_bytes;
        private String total_payment_amount;
        private int current_coins;
        private int total_coins;
        private boolean is_checkin_today;
        private boolean is_enabled;
        private long created_at;
        private boolean new_message;


        private String promo_code;
        private int promo_users_count;
        private int promo_coins_count;
        private String binded_promo_code;


        private boolean debug_log_enabled;
        private String debug_log_start_at;
        private String debug_log_end_at;
        private int debug_log_max_days;


        public boolean isDebug_log_enabled() {
            return debug_log_enabled;
        }

        public void setDebug_log_enabled(boolean debug_log_enabled) {
            this.debug_log_enabled = debug_log_enabled;
        }

        public String getDebug_log_end_at() {
            return debug_log_end_at;
        }

        public void setDebug_log_end_at(String debug_log_end_at) {
            this.debug_log_end_at = debug_log_end_at;
        }

        public int getDebug_log_max_days() {
            return debug_log_max_days;
        }

        public void setDebug_log_max_days(int debug_log_max_days) {
            this.debug_log_max_days = debug_log_max_days;
        }

        public String getDebug_log_start_at() {
            return debug_log_start_at;
        }

        public void setDebug_log_start_at(String debug_log_start_at) {
            this.debug_log_start_at = debug_log_start_at;
        }

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

        public long getLimit_bytes() {
            return limit_bytes;
        }

        public void setLimit_bytes(long limit_bytes) {
            this.limit_bytes = limit_bytes;
        }

        public long getMax_bytes() {
            return max_bytes;
        }

        public void setMax_bytes(long max_bytes) {
            this.max_bytes = max_bytes;
        }

        public long getUsed_bytes() {
            return used_bytes;
        }

        public void setUsed_bytes(long used_bytes) {
            this.used_bytes = used_bytes;
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

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public boolean isNew_message() {
            return new_message;
        }

        public void setNew_message(boolean new_message) {
            this.new_message = new_message;
        }

        public String getBinded_promo_code() {
            return binded_promo_code;
        }

        public void setBinded_promo_code(String binded_promo_code) {
            this.binded_promo_code = binded_promo_code;
        }

        public String getPromo_code() {
            return promo_code;
        }

        public void setPromo_code(String promo_code) {
            this.promo_code = promo_code;
        }

        public int getPromo_coins_count() {
            return promo_coins_count;
        }

        public void setPromo_coins_count(int promo_coins_count) {
            this.promo_coins_count = promo_coins_count;
        }

        public int getPromo_users_count() {
            return promo_users_count;
        }

        public void setPromo_users_count(int promo_users_count) {
            this.promo_users_count = promo_users_count;
        }
    }

    public static class GroupBean {
        /**
         * id : 1
         * name : vip0
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

    public static class DeviceBean {
        /**
         * id : 161
         * uuid : a1de3867-7468-4002-8dd4-554707c4edc5
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
        private String TELEGRAM_GROUP;
        private String QQ_GROUP;
        private String WX_OFFICAL_ACCOUNT;
        private String OFFICIAL_WEBSITE;

        private String DLOG_ALLOW_SEND;
        private String DLOG_POOL_MAX_COUNT;
        private String DLOG_POST_INTERVAL;

        private String FLOG_ALLOW_SEND;
        private String FLOG_POOL_MAX_COUNT;
        private String FLOG_POST_INTERVAL;
        private String FLOG_CLEAN_INTERVAL;

        private boolean SHOW_NAVIGATION;

        private String UPDATE_URL;

        private String TIMEOUT;

        private String STARTPAGE_TIME;

        private String PAY_DESC;

        private String POTATO_GROUP;
        private String SINA_WEIBO;


        public SettingsBean(String NOTICE_CONTENT, String SHARE_URL, String ANDROID_VERSION, String IS_UPDATE, String UPDATE_CONTENT, String SHARE_IMG) {
            this.ANDROID_VERSION = ANDROID_VERSION;
            this.IS_UPDATE = IS_UPDATE;
            this.NOTICE_CONTENT = NOTICE_CONTENT;
            this.SHARE_IMG = SHARE_IMG;
            this.SHARE_URL = SHARE_URL;
            this.UPDATE_CONTENT = UPDATE_CONTENT;
        }

        public String getPOTATO_GROUP() {
            return POTATO_GROUP;
        }

        public void setPOTATO_GROUP(String POTATO_GROUP) {
            this.POTATO_GROUP = POTATO_GROUP;
        }

        public String getSINA_WEIBO() {
            return SINA_WEIBO;
        }

        public void setSINA_WEIBO(String SINA_WEIBO) {
            this.SINA_WEIBO = SINA_WEIBO;
        }

        public String getPAY_DESC() {
            return PAY_DESC;
        }

        public void setPAY_DESC(String PAY_DESC) {
            this.PAY_DESC = PAY_DESC;
        }

        public String getANDROID_VERSION() {
            return ANDROID_VERSION;
        }

        public void setANDROID_VERSION(String ANDROID_VERSION) {
            this.ANDROID_VERSION = ANDROID_VERSION;
        }

        public String getIS_UPDATE() {
            return IS_UPDATE;
        }

        public void setIS_UPDATE(String IS_UPDATE) {
            this.IS_UPDATE = IS_UPDATE;
        }

        public String getNOTICE_CONTENT() {
            return NOTICE_CONTENT;
        }

        public void setNOTICE_CONTENT(String NOTICE_CONTENT) {
            this.NOTICE_CONTENT = NOTICE_CONTENT;
        }

        public String getSHARE_IMG() {
            return SHARE_IMG;
        }

        public void setSHARE_IMG(String SHARE_IMG) {
            this.SHARE_IMG = SHARE_IMG;
        }

        public String getSHARE_URL() {
            return SHARE_URL;
        }

        public void setSHARE_URL(String SHARE_URL) {
            this.SHARE_URL = SHARE_URL;
        }

        public String getUPDATE_CONTENT() {
            return UPDATE_CONTENT;
        }

        public void setUPDATE_CONTENT(String UPDATE_CONTENT) {
            this.UPDATE_CONTENT = UPDATE_CONTENT;
        }

        public String getOFFICIAL_WEBSITE() {
            return OFFICIAL_WEBSITE;
        }

        public void setOFFICIAL_WEBSITE(String OFFICIAL_WEBSITE) {
            this.OFFICIAL_WEBSITE = OFFICIAL_WEBSITE;
        }

        public String getQQ_GROUP() {
            return QQ_GROUP;
        }

        public void setQQ_GROUP(String QQ_GROUP) {
            this.QQ_GROUP = QQ_GROUP;
        }

        public String getTELEGRAM_GROUP() {
            return TELEGRAM_GROUP;
        }

        public void setTELEGRAM_GROUP(String TELEGRAM_GROUP) {
            this.TELEGRAM_GROUP = TELEGRAM_GROUP;
        }

        public String getWX_OFFICAL_ACCOUNT() {
            return WX_OFFICAL_ACCOUNT;
        }

        public void setWX_OFFICAL_ACCOUNT(String WX_OFFICAL_ACCOUNT) {
            this.WX_OFFICAL_ACCOUNT = WX_OFFICAL_ACCOUNT;
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

        public String getUPDATE_URL() {
            return UPDATE_URL;
        }

        public void setUPDATE_URL(String UPDATE_URL) {
            this.UPDATE_URL = UPDATE_URL;
        }

        public boolean getSHOW_NAVIGATION() {
            return SHOW_NAVIGATION;
        }

        public void setSHOW_NAVIGATION(boolean SHOW_NAVIGATION) {
            this.SHOW_NAVIGATION = SHOW_NAVIGATION;
        }

        public String getTIMEOUT() {
            return TIMEOUT;
        }

        public void setTIMEOUT(String TIMEOUT) {
            this.TIMEOUT = TIMEOUT;
        }

        public String getSTARTPAGE_TIME() {
            return STARTPAGE_TIME;
        }

        public void setSTARTPAGE_TIME(String STARTPAGE_TIME) {
            this.STARTPAGE_TIME = STARTPAGE_TIME;
        }
    }

    public static class UserNodeTypesBean {
        /**
         * id : 811
         * name : 青铜服
         * level : 1
         * status : 按次
         * expired_at : 1491556428
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
         */

        private int id;
        private String name;
        private int level;
        private int expense_coins;
        private int user_group_id;
        private int user_group_level;

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
    }
}
