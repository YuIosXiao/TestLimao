package com.first.saccelerator.constants;

/**
 * SP的文件名称与字段名称的常量
 * Created by ZhengSheng on 2017/3/31.
 */
public interface SPConstants {

    //-----------------------------config.xml----------------------------------------
    String SP_CONFIG = "config";

    interface CONFIG {
        String IS_FIRST = "is_first"; // 是否第一次启动APP

        String GPS_DIALOG_HINT = "gps_dialog_hint"; // GPS弹窗提示

        String PROXY_URL = "proxy_url"; // 代理的ss地址
        String PROXY_NAME = "proxy_name"; // 代理服务器的名称
        String PROXY_LEVEL = "proxy_level"; // 代理服务器的等级（1,2,3...）
        String PROXY_LEVEL_NAME = "proxy_level_name"; // 代理服务器的等级（青铜、白银、黄金...）
        String PROXY_MAX_CONNECTIONS_COUNT = "proxy_max_connections_count"; // 服务器预设人数
        String PROXY_CONNECTIONS_COUNT = "proxy_connections_count"; // 服务器连接人数
        String PROXY_EXPENSE_COINS = "proxy_expense_coins"; // 连接服务器所需砖石
        String PROXY_NODE_ID = "proxy_node_id"; // 连接服务器的id

        String LOGIN_TIME = "login_time"; // 记录调用登录接口的时间

        String APP_LAUNCH_ID = "app_launch_id";//用于记录App运行日志, 此值只在App的一次运行周期内有效，否则不在请求中加入此参数
        String SIGNIN_LOG_ID = "signin_log_id";//用户登录日志ID，在请求操作日志等接口时使用

        String IS_DOMESTIC = "is_domestic"; // 是否是国内 true是  false不是
        String ROUTES_UPDATED_AT = "routes_updated_at"; // 规则更新时间
        String ROUTES_CHINA_IP_UPDATED_AT = "routes_china_ip_updated_at"; // china ip 规则更新时间
        String NODE_ID = "node_id"; // 连接服务器的id(提交服务器连接日志(认证))

        String PROXY_NODE_TYPE_ID = "proxy_node_type_id"; // 服务器类型ID

        String TELEGRAM_GROUP = "telegram_group";//telegram群分享显示信息
        String QQ_GROUP = "qq_group";//qq群显示信息
        String WX_OFFICAL_ACCOUNT = "wx_offical_account";//微信群显示信息
        String OFFICIAL_WEBSITE = "official_website";//官网地址

        String PROXY_NODE_URL = "proxy_node_url"; // 服务器ip地址

        String UPDATE_URL = "update_url";//更新字段
        String SHOW_NAVIGATION = "show_navigation";//更新字段

        String ROUTES_FOREIGN_IP_UPDATE_AT = "routes_foreign_ip_update_at"; // foreign ip 规则更新时间

        String USING_INTERRUPTED = "use_interrupted";                       //使用中服务到期

        String TIMEOUT = "timeout";//APP网络请求超时时间

        String STARTPAGE_TIME = "startpage_time";//APP启动页等待时间

        String baiyun_isfirst = "baiyun_isfirst";//主页白云提示框是否显示,只在第一装APP的时候显示，用户点击了服务器列表后，就消失了。

        String PAY_DESC = "pay_desc";//充值说明文字

        String USE_LANGUAGE = "use_language";//APP准备使用的语言（中文，英文）


        String POTATO_GROUP = "potato_group";//potato群分享显示信息
        String SINA_WEIBO = "sina_weibo";//新浪微博地址

        String VPNTOGGLEBUTTON="vpntogglebutton";//VPN程序智能开关

    }

    //-----------------------------user.xml----------------------------------------
    String SP_USER = "user";

    interface USER {
        String API_TOKEN = "api_token";                       // 之后API调用需认证的接口时所需要的token(目前还未设置过期策略)
        String SOCKS_ACCOUNT_ID = "socks_account_id";         // SOCKS头认证--account_id
        String SOCKS_SESSION_ID = "socks_session_id";         // SOCKS头认证--session_id
        String SOCKS_SESSION_TOKEN = "socks_session_token";   // SOCKS头认证--session_token
        String USERNAME = "username";                         // 用户的用户名，用户自行修改用户名后，会用来存储手机号
        String PASSWORD = "password";                         // 用户的密码，可自行修改
        String EMAIL = "email";                               // 用户绑定的email
        String WECHAT = "wechat";                             // 用户绑定的微信的openid
        String WEIBO = "weibo";                               // 用户绑定的微博的openid
        String QQ = "qq";                                     // 用户绑定的qq的openid
        String USED_BYTES = "used_bytes";                     // 用户已使用的流量
        String MAX_BYTES = "max_bytes";                       // 用户可用的最大流量, -1: 无限制
        String LIMIT_BYTES = "limit_bytes";                   // 用户被限制的速度(kb/s), -1: 无限制
        String TOTAL_PAYMENT_AMOUNT = "total_payment_amount"; // 用户的消费累计金额
        String CURRENT_COINS = "current_coins";               // 用户当前钻石数
        String TOTAL_COINS = "total_coins";                   // 用户拥有过的总钻石数
        String IS_CHECKIN_TODAY = "is_checkin_today";         // 用户今天是否签到过, true: 是, false: 否
        String IS_ENABLED = "is_enabled";                     // 用户帐号状态(如: 被封号)，true:正常, false:已封号
        String CREATED_AT = "created_at";                     // 帐号创建时间
        String NEW_MESSAGE = "new_message";                   // 是否有新消息, true: 是, false: 否

        String GROUP_ID = "group_id";                         // 用户组id
        String GROUP_NAME = "group_name";                     // 用户组名称
        String GROUP_LEVEL = "group_level";                   // 用户组等级
        String GROUP_NEED_COINS = "group_need_coins";         // 需要的砖石数
        String NEXT_NAME = "next_name";                       // 下一等级名称

        String IS_PAY_SUCCESS = "is_pay_success";              //是否支付成功
        String USERID = "userid";                              //用户ID


        String PROMO_CODE = "promo_code";//用户的推广码
        String PROMO_USERS_COUNT = "promo_users_count";//用户当前已经推广的人数
        String PROMO_COINS_COUNT = "promo_coins_count";//用户累计获得的推广钻石数
        String BINDED_PROMO_CODE = "binded_promo_code";//已绑定的推广码，如果为""，则表示还未绑定

        String RENEW_DIAMOND_EXIST = "renew_diamond_exist";//自动续费的时候 砖石是否存在

        String DEBUG_LOG_ENABLED = "debug_log_enabled";//是否上传此用户的调试日志
        String DEBUG_LOG_START_AT = "debug_log_start_at";//调试日志开始日期，如: 2017-09-11
        String DEBUG_LOG_END_AT = "debug_log_end_at";//调试日志结束时间，如: 2017-09-12
        String DEBUG_LOG_MAX_DAYS = "debug_log_max_days";//调试日志保存最大天数

        String USE_LANGUAGE = "use_language";//APP准备使用的语言（中文，英文）
    }

    //-----------------------------default.xml----------------------------------------
    String SP_DEFAULT = "default";

    interface DEFAULT {
        String DEFAULTIP = "defaultip";
        String RESCUEFILESPATH = "rescuefilespath";

        String DEFAULTIPPOSITION = "defaultipposition";//动态IP或者固定IP位置序号
        String DEFAULTIPPOSITIONTYPE = "defaultippositiontype";//IP的类型
        String RESCUEFILEIPPOSITION = "rescuefileipposition";//救援文件IP位置序号
        String DYNAMICNUM = "dynamicnum";//动态IP数量

    }

    //-----------------------------historyserveruse.xml----------------------------------------
    String SP_HISTORYUSE = "sp_historyuse";

    //-----------------------------proxylog.xml----------------------------------------
    String SP_PROXY = "proxy";

    interface PROXY {
        String DLOG_ALLOW_SEND = "dlog_allow_send";//是否发送去向IP开关
        String DLOG_POOL_MAX_COUNT = "dlog_pool_max_count";//发送去向IP地址最大条数限制
        String DLOG_POST_INTERVAL = "dlog_post_interval";//发送去向ip地址间隔时间限制
        String DLOG_STARTTIME = "dlog_starttime"; //去向IP开始时间


        String FLOG_ALLOW_SEND = "flog_allow_send";//是否允许客户端发送失败日志
        String FLOG_POOL_MAX_COUNT = "flog_pool_max_count";//客户端失败日志池累计到多少条后提交
        String FLOG_POST_INTERVAL = "flog_post_interval";//客户端提交失败日志间隔时间(秒)
        String FLOG_CLEAN_INTERVAL = "flog_clean_interval";//客户端失败日志清理间隔时间(秒)
        String FLOG_INITIAL_IP = "flog_initial_ip";//最开始无法访问的服务器ip地址
        String FLOG_COUNTER = "flog_counter";//错误次数计数器
        String FLOG_CLEAR_STARTTIME = "flog_clear_starttime";//清理时间的开始时间
        String FLOG_SEND_STARTTIME = "flog_send_starttime";//发送间隔时间的开始时间
        String FLOG_TARGETADDRESS = "flog_targetaddress";//目标网站
        String FLOG_CREATTIME = "flog_creattime";//创建时间
    }


    //-----------------------------userhttphead.xml----------------------------------------
    String SP_USERHTTPHEAD = "userhttphead";

    interface USERHTTPHEAD {
        String remotehost = "remotehost";//远程连接的域名
        String remoteip = "remoteip";//远程连接的ip地址
        String remoteport = "remoteport";//远程连接的端口
        String longitude = "longitude";//经度
        String latitude = "latitude";//纬度
        String proxyform = "proxyform";//代理形式
        String number = "number";//数据条数
        String enabled = "enabled";//是否允许客户端发送失败日志
        String key = "key";//密钥，用于加解密数据
        String token = "token";//用于向接口提交数据时验证
        String host_url = "host_url";//提交服务器的URL
        String send_starttime = "send_starttime";//发送间隔时间的开始时间
        String reset_starttime = "reset_starttime";//重置请求获得客户端日志提交相关信息(认证)接口数据


        String len = "len";//文件长度，可以累加的
        String len_num = "len_num";//文件发送次数
    }

    //-----------------------------ad.xml----------------------------------------
    String SP_AD = "ad";

    interface AD {
        String openad_id = "openad_id";//开屏广告的id
        String openad_duration = "openad_duration";//开屏广告倒计时
        String openad_enabled = "openad_enabled";//开屏广告状态（开启or关闭）
        String openad_imageurl = "openad_imageurl";//开屏广告的url
        String openad_linkurl = "openad_linkurl";//开屏广告的点击链接地址
        String openad_updatedat = "openad_updatedat";//开屏广告后台更新时间
        String openad_playtimes = "openad_playtimes";//开屏广告播放的时间
        String openad_overtime = "openad_overtime";//开屏广告超时时间（超过这个值，就需要播放广告）
        String openad_imagesuccess = "openad_imagesuccess";//开屏广告图片下载成功标识

        String popup_enabled = "popup_enabled";//弹出广告开启状态
        String popup_daytimes = "popup_daytimes";//弹出广告每天展示次数
        String popup_imageurl = "popup_imageurl";//弹出广告图片URL
        String popup_linkurl = "popup_linkurl";//弹出广告链接URL
        String popup_updatedat = "popup_updatedat";//弹出广告后台更新时间
        String popup_playnumbers = "popup_playnumbers";//弹出广告播放成功的次数
        String popup_playtimes = "popup_playtimes";//弹出广告播放时间
        String popup_imagesuccess = "popup_imagesuccess";//弹出广告图片下载成功标识

        String testaaa = "testaaa";
    }

    //-----------------------------use_language.xml----------------------------------------
    String SP_USE_LANGUAGE = "use_language";

    interface USELANGUAGE {
        String TAG_LANGUAGE = "language_select";//选择的语言代码
        String TAG_SYSTEM_LANGUAGE = "system_language";//系统语言代码
    }

}