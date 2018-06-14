package com.first.saccelerator.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.first.saccelerator.App;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.ConnectionFailedDataSource;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.DynamicServerFailedDataSource;
import com.first.saccelerator.database.DynamicServersDataSource;
import com.first.saccelerator.database.LoginFailedDataSource;
import com.first.saccelerator.database.NoOperationDataSource;
import com.first.saccelerator.database.OperationLogDataSource;
import com.first.saccelerator.database.ProxyIpDataSource;
import com.first.saccelerator.database.RescueIPDataSource;
import com.first.saccelerator.database.UserhttpheadDataSource;
import com.first.saccelerator.dialog.NetworkTimeoutDialog;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.BaseSubscriber;
import com.first.saccelerator.http.CallbackWithRetry;
import com.first.saccelerator.http.MyHttpAddress;
import com.first.saccelerator.http.MyHttpUtil;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.http.RetrofitServiceManager2;
import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.model.AutoRenewResponse;
import com.first.saccelerator.model.ClientLogResponse;
import com.first.saccelerator.model.ConnectionFailedResponse;
import com.first.saccelerator.model.DynamicServerFailedResponse;
import com.first.saccelerator.model.DynamicServersResponse;
import com.first.saccelerator.model.LoginFailedResponse;
import com.first.saccelerator.model.NoOperationResponse;
import com.first.saccelerator.model.OperationLogResponse;
import com.first.saccelerator.model.ProxyIpResponse;
import com.first.saccelerator.model.RenewResponse;
import com.first.saccelerator.model.RescueIPServersResponse;
import com.first.saccelerator.model.RescueIpResponse;
import com.first.saccelerator.model.UserhttpheadResponse;
import com.first.saccelerator.receiver.NetworkReconnectionReceiver;
import com.first.saccelerator.receiver.NetworkTimeoutReceiver;
import com.first.saccelerator.ss.core.LocalVpnService;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by XQ on 2017/3/30.
 * 全局变量或方法
 */
public class StaticStateUtils {

    //测试用的token
    public static String token = "";

    //修改密码（找回密码）发送验证码倒计时数字
    public static long changepasswordcountdown = 0;
    public static boolean changepassworddisplayflag = true;//true 正常显示 false 倒计时显示
    public static boolean findpassworddisplayflag = true;//true 正常显示 false 倒计时显示
    //修改账号发送验证码倒计时数字
    public static long modifyaccountcountdown = 0;
    public static boolean modifyaccountdisplayflag = true;//true 正常显示 false 倒计时显示
    //账号注册发送验证码倒计时数字
    public static long registeredcountdown = 0;
    public static boolean registereddisplayflag = true;//true 正常显示 false 倒计时显示

    //全局类名
    public static String classname;

    //APP的版本号
    public static String app_version = "jichu";

//    public static String usebath = "https://zhulaoban8.com/";
//    public static String bath = "https://www.youtube.com/";
//    public static String bath1 = "https://www.facebook.com/";
//    public static String bath2 = "https://www.twitter.com/";
//    public static String bath3 = "https://www.youtube.com/";
//    public static String bath4 = "https://www.facebook.com/";

    //开发服务器地址
//    public static String usebath = "https://xiaoguoqi.com";
//    public static String bath = "https://xiaoguoqi.com/";
//    public static String bath1 = "https://xiaoguoqi.com";
//    public static String bath2 = "https://xiaoguoqi.com";
//    public static String bath3 = "https://xiaoguoqi.com";
//    public static String bath4 = "https://xiaoguoqi.com";
    //测试服务器地址
    public static String usebath = "https://staging.xiaoguoqi.com/";
    public static String bath = "https://staging.xiaoguoqi.com/";
    public static String bath1 = "https://staging.xiaoguoqi.com/";
    public static String bath2 = "https://staging.xiaoguoqi.com/";
    public static String bath3 = "https://staging.xiaoguoqi.com/";
    public static String bath4 = "https://staging.xiaoguoqi.com/";
    public static String bath5 = "https://staging.xiaoguoqi.com/";
    public static String bath6 = "https://staging.xiaoguoqi.com/";
    public static String bath7 = "https://staging.xiaoguoqi.com/";
    public static String bath8 = "https://staging.xiaoguoqi.com/";
    public static String bath9 = "https://staging.xiaoguoqi.com/";
    public static String bath10 = "https://staging.xiaoguoqi.com/";
    public static String bath11 = "https://staging.xiaoguoqi.com/";
    public static String bath12 = "https://staging.xiaoguoqi.com/";
    public static String bath13 = "https://staging.xiaoguoqi.com/";
    public static String bath14 = "https://staging.xiaoguoqi.com/";
    public static String bath15 = "https://staging.xiaoguoqi.com/";
    //正式地址
//    public static String usebath = "https://zhulaoban8.com/";
//    public static String bath = "https://zhulaoban8.com/";
//    public static String bath1 = "https://linlaoban8.com/";
//    public static String bath2 = "https://asdfqw.com/";
//    public static String bath3 = "https://bfsw1.com/";
//    public static String bath4 = "https://dpsdingzhu.com/";
//    public static String bath5 = "https://fffddd1.com/";
//    public static String bath6 = "https://wwwailmvpn1.com/";
//    public static String bath7 = "https://ailmvpn2.com/";
//    public static String bath8 = "https://lovelimao1.com/";
//    public static String bath9 = "https://lovelimao2.com/";
//    public static String bath10 = "https://ailimaovpn1.com/";
//    public static String bath11 = "https://ailimaovpn2.com/";
//    public static String bath12 = "https://vpnlimao1.com/";
//    public static String bath13 = "https://vpnlimao2.com/";
//    public static String bath14 = "https://limaovpn3.com/";
//    public static String bath15 = "https://limaovpn4.com/";

    //救援拉取地址
//    public static String pullrescuepath = "https://msquickly.xyz/rescueip.txt";
//    public static String pullrescuepath1 = "https://zslongt.xyz/rescueip.txt";
//    public static String pullrescuepath2 = "https://sendbang.xyz/rescueip.txt";
//    public static String pullrescuepath3 = "https://hoonlin.xyz/rescueip.txt";
//    public static String pullrescuepath4 = "https://vivivivian.com/rescueip.txt";
//    public static String pullrescuepath5 = "http://owko4a048.bkt.clouddn.com/rescueip.txt ";
//    public static String pullrescuepath6 = "http://lmother.oss-cn-beijing.aliyuncs.com/rescueip.txt";
    //测试救援拉取地址
    public static String pullrescuepath = "http://static.staging.xiaoguoqi.com/rescueip.txt";
    public static String pullrescuepath1 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";
    public static String pullrescuepath2 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";
    public static String pullrescuepath3 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";
    public static String pullrescuepath4 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";
    public static String pullrescuepath5 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";
    public static String pullrescuepath6 = "http://static.staging.xiaoguoqi.com/new/rescueip.txt";


    //首页弹出公告标识
    public static boolean noticesflag = true;
    //首页更新弹框标识
    public static boolean updateflag = true;
    //首页弹出广告标识
    public static boolean popudadflag = true;
    //保存的APK的名称
    public static String vpn_nickname = "";

    //默认使用IP位置
    public static int defaultipposition = -1;

    public static Activity basisActivity;

    //上次登录服务器ID
    public static String history_node_type_id = "";
    //上次登录服务器名称
    public static String historynode_type_name = "";

    //是否登录标识
    public static boolean signinflag = true;

    //使用VPN过滤配置表路径
    public static String configName = "";


    public static long startvpntime;//开始连接VPN时间
    public static long timedifference;//时间差


    //主界面 页面序列号
    public static int index = -1;


    //加密，解密秘钥
    public static String key = "86712786e2205b50e80721462334364d";


    public static String interface_send_verification_code = "interface_send_verification_code";
    public static String interface_signin = "interface_signin";
    public static String interface_reset_password = "interface_reset_password";
    public static String interface_connect1 = "interface_connect1";
    public static String interface_connectvpnii = "interface_connectvpnii";
    public static String interface_checkin = "interface_checkin";
    public static String interface_update_username = "interface_update_username";
    public static String interface_update_password = "interface_update_password";
    public static String interface_plans = "interface_plans";
    public static String interface_replies = "interface_replies";
    public static String interface_connecttype = "interface_connecttype";
    public static String interface_help_manuals = "interface_help_manuals";
    public static String interface_consumption_logs = "interface_consumption_logs";
    public static String interface_transaction_logs = "interface_transaction_logs";
    public static String interface_bind_promo_code = "interface_bind_promo_code";
    public static String interface_profile = "interface_profile";
    public static String interface_feedbacks = "interface_feedbacks";
    public static String interface_serverlistdata = "interface_serverlistdata";


    public static String userhttphead_remotehost = "";
    public static String userhttphead_remoteip = "";
    public static int userhttphead_remoteport = 0;
    public static String userhttphead_proxyform = "direct";

    //客户端提交ip日志地址,测试环境
    public static String userhttphead_ip = "https://staging.xiaoguoqi.com:7888/putdata_client";

    public static UserhttpheadDataSource userhttpheaddatasource = null;

    public static List<UserhttpheadResponse.ListBean> userhttpheadList = new ArrayList<>();

    //国内翻国外
    public static String originalFilePath_d2o = "";//vpn原始文件路径
    public static String targetFilePath_d2o = "";//vpn自定义白名单文件路径
    public static String usesFilePath_d2o = "";//vpn翻墙使用的文件路径
    //国外翻国内
    public static String originalFilePath_o2d = "";//vpn原始文件路径
    public static String targetFilePath_o2d = "";//vpn自定义白名单文件路径
    public static String usesFilePath_o2d = "";//vpn翻墙使用的文件路径


    public static List<AppUtils.AppInfo> appinfolist;//手机应用信息

    /**
     * Activity页面跳转
     */
    public static void intentToJump(Context context, Class<?> cls, int flag) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(flag);
        context.startActivity(intent);
    }


    /**
     * 操作日志保存
     */
    public static void OperationLogRecord(int operationid, Context context) {
        long operationtime = System.currentTimeMillis() / 1000L;
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        String operationusename = spUtils.getString(SPConstants.USER.USERNAME, "");
        int userid = spUtils.getInt(SPConstants.USER.USERID);
//        LogUtils.i("OperationLogRecord-userid---->" + userid);
        if (!StringUtils.isBlank(userid) && userid > 0) {
            OperationLogDataSource dataSource = new OperationLogDataSource(new DBHelper(context));
            OperationLogResponse response = new OperationLogResponse(operationid, operationtime, operationusename, userid);
            dataSource.insert(response);
            dataSource.close();
        }
    }

    /**
     * 登录失败保存
     */
    public static void LoginFailedRecord(String uuid, String failedid, Context context) {
        long logintime = System.currentTimeMillis() / 1000L;
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        String username = spUtils.getString(SPConstants.USER.USERNAME, null);
        if (!StringUtils.isBlank(username)) {
            LoginFailedDataSource dataSource = new LoginFailedDataSource(new DBHelper(context));
            LoginFailedResponse response = new LoginFailedResponse(failedid, username, logintime, uuid);
            dataSource.insert(response);
            dataSource.close();
        }
    }

    /**
     * 用户未操作保存
     */
    public static void noOperationRecord(Context context) {
        long logintime = System.currentTimeMillis() / 1000L;
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        String username = spUtils.getString(SPConstants.USER.USERNAME, null);
        if (!StringUtils.isBlank(username)) {
            NoOperationDataSource dataSource = new NoOperationDataSource(new DBHelper(context));
            NoOperationResponse response = new NoOperationResponse(username, logintime);
            dataSource.insert(response);
            dataSource.close();
        }
    }

    /**
     * 连接失败操作保存
     */
    public static void connectionFailedRecord(String failedid, Context context) {
        long logintime = System.currentTimeMillis() / 1000L;
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        String username = spUtils.getString(SPConstants.USER.USERNAME, null);

        String servertypename = "";
        String serverareaname = "";
        String servername = "";
        List<String> proxynamelist = new ArrayList<String>();
        //连接失败保存信息
        SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);
        String proxyName = configSP.getString(SPConstants.CONFIG.PROXY_NAME, "");
//        if (!StringUtils.isBlank(proxyName)) {
//            String data1[] = proxyName.split("\\-");
//            if (data1.length > 1) {
//                for (int i = 0; i < data1.length; i++) {
//                    proxynamelist.add(data1[i]);
////                    LogUtils.i("data[i]----->" + data1[i]);
//                }
//                servertypename = data1[0];
//                serverareaname = data1[1];
//                servername = data1[2];
//            }
//        }
        if (!StringUtils.isBlank(proxyName)) {
            servertypename = proxyName;
        }
        if (!StringUtils.isBlank(username)) {
            ConnectionFailedDataSource dataSource = new ConnectionFailedDataSource(new DBHelper(context));
            ConnectionFailedResponse response = new ConnectionFailedResponse(failedid, username, logintime, servertypename, serverareaname, servername);
            dataSource.insert(response);
            dataSource.close();
        }
    }

    /**
     * 获取友盟渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
//                        System.out.println("channelName----->" + channelName);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 保存本地IP地址到dynamic_servers中
     */
//    public static String[] ipArray = new String[]{bath, bath1, bath2};
    public static String[] ipArray = new String[]{bath, bath1, bath2, bath3, bath4, bath5, bath6, bath7, bath8, bath9, bath10, bath11, bath12, bath13, bath14, bath15};


    public static void saveIp(Context context) {
        DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(context));
        for (int i = 0; i < ipArray.length; i++) {
            DynamicServersResponse.ServersBean response = new DynamicServersResponse.ServersBean(999, ipArray[i], "本地", i);
            dataSource.insert(response);
        }
        dataSource.close();
    }

    /**
     * 切换IP
     * 更新 XQ 2017.11.02
     */
    public static void changeIp(Context context) {

        dynamicServerFailedRecord(StaticStateUtils.usebath, context);
        DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(context));
        List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);

        defaultipposition = spUtils.getInt(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
        rescuefileposition = spUtils.getInt(SPConstants.DEFAULT.RESCUEFILEIPPOSITION, 0);

        if (!StringUtils.isBlank(list) && list.size() > 0) {
            if ((defaultipposition + 1) < list.size()) {
                StaticStateUtils.defaultipposition++;
                spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(StaticStateUtils.defaultipposition).getUrl());
                StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);


//                LogUtils.i("changeIp---usebath----->" + StaticStateUtils.usebath);
//                ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                String msg = "正在优化网络,请稍后";
                if (list.get(defaultipposition).getRegion().equals("本地")) {
                    msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                } else {
                    msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                }
                sendNetworkTimeoutReceiver(msg);

                changeipflag = true;
                detectDomainWhetherNormal(StaticStateUtils.basisActivity);

            } else {
                RescueIPDataSource rescueIPDataSource = new RescueIPDataSource(new DBHelper(StaticStateUtils.basisActivity));
                rescueIpList = rescueIPDataSource.findAll();
                if (!StringUtils.isBlank(rescueIpList) && rescueIpList.size() > 0) {//救援IP不为空
                    if (rescuefileposition >= rescueIpList.size()) {
                        defaultipposition = 0;
                        rescuefileposition = 0;
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
                        spUtils.put(SPConstants.DEFAULT.RESCUEFILEIPPOSITION, 0);
                        rescueIpList.clear();
                        rescueIPDataSource.clear();
                        spUtils.put(SPConstants.DEFAULT.RESCUEFILESPATH, "");
                        LogUtils.i("救援文件ip轮询完，清空数组----->");

                        dynamicServerFailedRecord(StaticStateUtils.usebath, context);
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(defaultipposition).getUrl());
                        StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);

//                        LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                        ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                        String msg = "正在优化网络,请稍后";
                        if (list.get(defaultipposition).getRegion().equals("本地")) {
                            msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        } else {
                            msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        }
                        sendNetworkTimeoutReceiver(msg);

                        changeipflag = true;
                        detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                    } else {
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIP, rescueIpList.get(rescuefileposition).getUrl());
                        StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);
                        rescuefileposition++;
//                        LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                        ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                        String msg = "正在优化网络,请稍后";
                        msg = msg + "(#" + "3" + rescuefileposition + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "3");
                        spUtils.put(SPConstants.DEFAULT.RESCUEFILEIPPOSITION, rescuefileposition);

                        sendNetworkTimeoutReceiver(msg);


                        changeipflag = true;
                        detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                    }

                } else {
                    String rescuefilespath = spUtils.getString(SPConstants.DEFAULT.RESCUEFILESPATH, "");
                    if (!StringUtils.isBlank(rescuefilespath) && new File(rescuefilespath).exists()) {
                        String fileName = rescuefilespath.substring(rescuefilespath.lastIndexOf("/") + 1);
                        resolveRescueFiles(fileName);
                    } else {
                        String[] pullrescuepaths = getPullRescuePaths();
                        if (pullrescuepathsposition < pullrescuepaths.length) {
                            pullRescueFiles(pullrescuepaths[pullrescuepathsposition]);
                        } else {
                            //继续循环动态ip列表中的ip
                            pullrescuepathsposition = 0;
                            defaultipposition = 0;
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);

                            spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(defaultipposition).getUrl());
                            StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);


//                            LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                            ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                            String msg = "正在优化网络,请稍后";
                            if (list.get(defaultipposition).getRegion().equals("本地")) {
                                msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                                spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                                spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                            } else {
                                msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                                spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                                spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                            }
                            sendNetworkTimeoutReceiver(msg);

                            changeipflag = true;
                            detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                        }
                    }
                }
                rescueIPDataSource.close();
            }
        }
        dataSource.close();
    }

    /**
     * 请求动态IP
     */
    public static List<DynamicServersResponse.ServersBean> serversbeanlist = new ArrayList<DynamicServersResponse.ServersBean>();

    public static void dynamicServers(final Context context) {
//
//        String token = spUtils.getString(SPConstants.USER.API_TOKEN, "");
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.dynamicservers()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<DynamicServersResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<DynamicServersResponse> response) {
                        final DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(context));
                        dataSource.clear();//先删除数据;
                        serversbeanlist = response.getData().getServers();


                        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
                        //获得动态IP个数后，记录下，对比之前的调整defaultipposition数量
                        int dynamicnum = spUtils.getInt(SPConstants.DEFAULT.DYNAMICNUM, 0);
                        int defaultipposition = spUtils.getInt(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
                        defaultipposition = defaultipposition + (serversbeanlist.size() - dynamicnum);
                        if (defaultipposition < -1) {
                            defaultipposition = -1;
                        }
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        spUtils.put(SPConstants.DEFAULT.DYNAMICNUM, serversbeanlist.size());


                        if (!StringUtils.isBlank(serversbeanlist) && serversbeanlist.size() > 0) {
                            for (int i = 0; i < serversbeanlist.size(); i++) {
                                int id = serversbeanlist.get(i).getId();
                                String url = serversbeanlist.get(i).getUrl();
                                String region = serversbeanlist.get(i).getRegion();
                                int priority = serversbeanlist.get(i).getPriority();
                                DynamicServersResponse.ServersBean serversbeanresponse = new DynamicServersResponse.ServersBean(id, url, region, priority);
                                dataSource.insert(serversbeanresponse);
                            }
                        }
                        StaticStateUtils.saveIp(context);//保存本地ip地址
                        dataSource.close();
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
//                        LogUtils.i("onFailuremessage----->" + message);
                    }
                });
    }

    /**
     * ip轮询成功后，再次请求动态IP
     * 创建者 XQ 20171122
     */
    public static void dynamicServers2(final Context context) {
        //
//        String token = spUtils.getString(SPConstants.USER.API_TOKEN, "");
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.dynamicservers()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<DynamicServersResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<DynamicServersResponse> response) {
                        final DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(context));
                        dataSource.clear();//先删除数据;
                        serversbeanlist = response.getData().getServers();


                        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
                        //获得动态IP个数后，记录下，对比之前的调整defaultipposition数量
                        int dynamicnum = spUtils.getInt(SPConstants.DEFAULT.DYNAMICNUM, 0);
                        int defaultipposition = spUtils.getInt(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
                        defaultipposition = defaultipposition + (serversbeanlist.size() - dynamicnum);
                        if (defaultipposition < -1) {
                            defaultipposition = -1;
                        }
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        spUtils.put(SPConstants.DEFAULT.DYNAMICNUM, serversbeanlist.size());


                        if (!StringUtils.isBlank(serversbeanlist) && serversbeanlist.size() > 0) {
                            for (int i = 0; i < serversbeanlist.size(); i++) {
                                int id = serversbeanlist.get(i).getId();
                                String url = serversbeanlist.get(i).getUrl();
                                String region = serversbeanlist.get(i).getRegion();
                                int priority = serversbeanlist.get(i).getPriority();
                                DynamicServersResponse.ServersBean serversbeanresponse = new DynamicServersResponse.ServersBean(id, url, region, priority);
                                dataSource.insert(serversbeanresponse);
                            }
                        }
                        StaticStateUtils.saveIp(context);//保存本地ip地址
                        dataSource.close();

                        StaticStateUtils.getDynamicAndRescue();

                        endNetworkTimeout();
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
//                        LogUtils.i("onFailuremessage----->" + message);
                        endNetworkTimeout();
                    }
                });
    }


    /**
     * 设置用户离线
     */
    public static void offline(int user_id) {
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.offline(user_id + "")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
//                        LogUtils.i("response.isSuccess()----->" + response.isSuccess());
                        //如果VPN运行中，断开VPN
                        if (LocalVpnService.IsRunning) {
                            LocalVpnService.IsRunning = false;
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailuremessage----->" + message);
                    }
                });

    }

    /**
     * 动态服务器连接失败
     */
    public static void dynamicServerFailedRecord(String url, Context context) {
        long createtime = System.currentTimeMillis() / 1000L;
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        String username = spUtils.getString(SPConstants.USER.USERNAME);
        int userid = spUtils.getInt(SPConstants.USER.USERID);
//        LogUtils.i("OperationLogRecord-userid---->" + userid);
        if (!StringUtils.isBlank(userid) && userid > 0) {
            DynamicServerFailedDataSource dataSource = new DynamicServerFailedDataSource(new DBHelper(context));
            DynamicServerFailedResponse response = new DynamicServerFailedResponse(username, url, createtime, userid);
            dataSource.insert(response);
            dataSource.close();
        }
    }


    /**
     * 服务器连接日志
     */
    public static void updateConnectionLog(String wait_time, int success) {
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);
        SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
        int node_id = sputilsconfig.getInt(SPConstants.CONFIG.PROXY_NODE_ID);
        LogUtils.i("node_id----->" + node_id);

        String domain = StaticStateUtils.usebath;
        domain = domain.replace("http://", "");
        domain = domain.replace("https://", "");
        domain = domain.replace("/", "");

        Map<String, String> options = new HashMap<>();
        options.put("node_id", node_id + "");
        options.put("wait_time", wait_time + "");
        options.put("success", success + "");
        options.put("domain", domain);

        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.connectionlog(token, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        LogUtils.i("connection_log:onSuccess----->" + response.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("connection_log:onFailure----->" + message);
                    }
                });
    }

    /**
     * 用户分享日志(认证)
     */
    public static void updateShare(String share_type, Context context) {
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);

        String app_channel = StaticStateUtils.getChannelName(context);//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(context) + "";//app版本号, 如: 0.1

        Map<String, String> options = new HashMap<>();
        options.put("app_channel", app_channel);
        options.put("app_version", app_version);
        options.put("app_version_number", app_version_number);
        options.put("share_type", share_type);//分享类型: 0:微信, 1:朋友圈, 2:QQ, 3:微博


        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.share(token, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse response) {
                        LogUtils.i("response.isSuccess()----->" + response.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailuremessage----->" + message);
                    }
                });
    }

    /**
     * 自动续费
     */
    public static void renew() {
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);
        final SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
        int node_id = sputilsconfig.getInt(SPConstants.CONFIG.PROXY_NODE_ID);

        Map<String, String> options = new HashMap<>();
        options.put("node_id", node_id + "");


        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.renew(token, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<RenewResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<RenewResponse> renewResponseApiResponse) {
                        int expired_at = renewResponseApiResponse.getData().getUser_node_type_expired_at();
                        LogUtils.i("有效期至----->" + TimeUtils.unixTimeStampFormat(expired_at, TimeUtils.DEFAULT_PATTERN));
                        if (renewResponseApiResponse.getData().isIs_renewd()) {
                            //更新当前钻石数量
                            sputilsconfig.put(SPConstants.USER.CURRENT_COINS, renewResponseApiResponse.getData().getCurrent_coins());
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailuremessage----->" + message + "|" + error_code);
                        if (error_code == 3) {//钻石不足，请充值
                            LocalVpnService.IsRunning = false;
                            SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
                            sputilsuser.put(SPConstants.USER.RENEW_DIAMOND_EXIST, false);
                        }
                    }
                });
    }

    /**
     * 客户端去向日志提交
     */
    public static boolean clientconnectionlogsflag = true;//阻止连续发送日志标识

    public static void clientconnectionlogs() {
        List<ProxyIpResponse> ipResponseList;
        final ProxyIpDataSource dataSource = new ProxyIpDataSource(new DBHelper(Utils.getContext()));
        ipResponseList = dataSource.findAll();
        if (!StringUtils.isBlank(ipResponseList) && ipResponseList.size() > 0) {
            String data = "";
            for (int i = 0; i < ipResponseList.size(); i++) {
                int userid = ipResponseList.get(i).getUserid();
                long creattime = ipResponseList.get(i).getCreattime();
                String sourceaddress = ipResponseList.get(i).getSourceaddress();
                String targetaddress = ipResponseList.get(i).getTargetaddress();
                int nodeid = ipResponseList.get(i).getNodeid();
                String targetip = ipResponseList.get(i).getTargetip();
                int port = ipResponseList.get(i).getPort();
                int isProxy = ipResponseList.get(i).getIsProxy();
                if (i == (ipResponseList.size() - 1)) {
                    data += userid + "," + nodeid + "," + sourceaddress + "," + targetaddress + "," + isProxy + "," + creattime + ",";
                } else {
                    data += userid + "," + nodeid + "," + sourceaddress + "," + targetaddress + "," + isProxy + "," + creattime + "|";
                }
            }
            LogUtils.i("data----->" + data);
            Map<String, String> map = new HashMap<>();
            map.put("uuid", DeviceUtils.getUUID());
            map.put("app_channel", StaticStateUtils.getChannelName(Utils.getContext()));
            map.put("app_version", StaticStateUtils.app_version);
            map.put("app_version_number", AppUtils.getAppVersionName(Utils.getContext()));
            map.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.clientconnectionlogs(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse<ProxyIpResponse>>() {
                        @Override
                        public void onSuccess(ApiResponse<ProxyIpResponse> proxyIpResponseApiResponse) {
                            if (proxyIpResponseApiResponse.isSuccess()) {
                                SPUtils SP_PROXY = new SPUtils(SPConstants.SP_PROXY);
                                SP_PROXY.put(SPConstants.PROXY.DLOG_STARTTIME, System.currentTimeMillis() + "");
                                dataSource.clear();
                            }
                            dataSource.close();
                            clientconnectionlogsflag = true;
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message + "|" + error_code);
                            clientconnectionlogsflag = true;
                        }
                    });
        }
    }

    /**
     * 代理服务器连接失败信息提交
     */
    public static void proxyconnectionfailed() {
        final SPUtils sp_proxy = new SPUtils(SPConstants.SP_PROXY);
        SPUtils sp_config = new SPUtils(SPConstants.SP_CONFIG);
        SPUtils sp_user = new SPUtils(SPConstants.SP_USER);
        if (!StringUtils.isBlank(sp_proxy.getString(SPConstants.PROXY.FLOG_INITIAL_IP))
                && !StringUtils.isBlank(sp_proxy.getString(SPConstants.PROXY.FLOG_TARGETADDRESS))) {
            int userid = sp_user.getInt(SPConstants.USER.USERID);
            int proxy_node_id = sp_config.getInt(SPConstants.CONFIG.PROXY_NODE_ID);
            String proxy_node_url = sp_config.getString(SPConstants.CONFIG.PROXY_NODE_URL);
            String flog_targetaddress = sp_proxy.getString(SPConstants.PROXY.FLOG_TARGETADDRESS);
            long flog_creattime = sp_proxy.getLong(SPConstants.PROXY.FLOG_CREATTIME);
            String data = userid + "," + proxy_node_id + "," + proxy_node_url + "," + flog_targetaddress + "," + flog_creattime;

            LogUtils.i("data----->" + data);
            Map<String, String> map = new HashMap<>();
            map.put("uuid", DeviceUtils.getUUID());
            map.put("app_channel", StaticStateUtils.getChannelName(Utils.getContext()));
            map.put("app_version", StaticStateUtils.app_version);
            map.put("app_version_number", AppUtils.getAppVersionName(Utils.getContext()));
            map.put("operator", PhoneUtils.getSimOperatorByMnc());
            map.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.proxyconnectionfailed(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse>() {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            if (apiResponse.isSuccess()) {
                                sp_proxy.put(SPConstants.PROXY.FLOG_COUNTER, 0);
                                sp_proxy.put(SPConstants.PROXY.FLOG_TARGETADDRESS, "");
                                sp_proxy.put(SPConstants.PROXY.FLOG_CREATTIME, "");
                                sp_proxy.put(SPConstants.PROXY.FLOG_INITIAL_IP, "");
                                sp_proxy.put(SPConstants.PROXY.FLOG_CLEAR_STARTTIME, System.currentTimeMillis());
                                sp_proxy.put(SPConstants.PROXY.FLOG_SEND_STARTTIME, System.currentTimeMillis());
                            }
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message + "|" + error_code);
                        }
                    });
        }
    }

    /**
     * desc 设置是否自动扣钻石续期(认证)
     *
     * @param auto_renew 是否在服务到期后自动扣除钻石续期, 0:否, 1:是
     */
    public static void setAutoRenew(int auto_renew) {
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);
        Map<String, String> options = new HashMap<>();
        options.put("auto_renew", auto_renew + "");

        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.setAutoRenew(token, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<AutoRenewResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<AutoRenewResponse> autoRenewResponseApiResponse) {
                        if (autoRenewResponseApiResponse.isSuccess()) {
                            if (autoRenewResponseApiResponse.getData().isAuto_renew()) {
                                ToastUtils.showLongToast("自动续费开关打开！");
                            } else {
                                ToastUtils.showLongToast("自动续费开关关闭！");
                            }
                            return;
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                        ToastUtils.showLongToast(message);
                        return;
                    }
                });
    }

    /**
     * ip或者域名检测方法，当网络连接不通的时候检测
     */
    public static int retryCount = 0;//ip或者域名超时次数
    public static final int TOTAL_RETRIES = 1;//超时最大次数
    public static boolean changeipflag = true;


    public static void detectDomainWhetherNormal(final Context context) {
        if (changeipflag) {
            changeipflag = false;
            ApiService apiService = RetrofitServiceManager2.getInstance().getApiService();
            Call<ResponseBody> call = apiService.location(StaticStateUtils.usebath + "api/client/v1/commons/location");
            call.enqueue(new CallbackWithRetry<ResponseBody>(call, context) {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (!StringUtils.isBlank(response.body())) {
                            String jsonString = StringUtils.byte2String(response.body().bytes());
//                            LogUtils.i("jsonString----->" + jsonString);
                            if (jsonString.contains("success") && jsonString.contains("is_domestic")) {
                                changeipflag = true;
                                retryCount = 0;
                                dynamicServers2(context);
//                                ToastUtils.showLongToastSafe("切换IP成功，请继续使用");
                                return;
                            }
                        }

                        if (retryCount++ < TOTAL_RETRIES) {
                            call.clone().enqueue(this);
                        } else {
                            retryCount = 0;
                            changeIp(context);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
//            Callback<ResponseBody> callback = new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        if (!StringUtils.isBlank(response.body())) {
//                            String jsonString = StringUtils.byte2String(response.body().bytes());
////                            LogUtils.i("jsonString----->" + jsonString);
//                            if (jsonString.contains("success") && jsonString.contains("is_domestic")) {
//                                changeipflag = true;
//                                retryCount = 0;
//                                dynamicServers2(context);
////                                ToastUtils.showLongToastSafe("切换IP成功，请继续使用");
//                                return;
//                            }
//                        }
//
//                        if (retryCount++ < TOTAL_RETRIES) {
//                            call.clone().enqueue(this);
//                        } else {
//                            retryCount = 0;
//                            changeIp(context);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    LogUtils.i("detectDomainWhetherNormalon--Failure--->" + t.getMessage());
//
//                    if (retryCount++ < TOTAL_RETRIES) {
//                        call.clone().enqueue(this);
//                    } else {
////                        LogUtils.i("我要开始切换ip了----->");
//                        retryCount = 0;
//                        changeIp(context);
//                    }
//                }
//            };
//            call.enqueue(callback);
        }
    }

    /**
     * 得到救援文件拉取ip组
     */
    //拉取救援ip的排序
    public static int pullrescuepathsposition = 0;

    public static String[] getPullRescuePaths() {
        String[] rescuepaths = new String[]{pullrescuepath, pullrescuepath1, pullrescuepath2,
                pullrescuepath3, pullrescuepath4, pullrescuepath5, pullrescuepath6};
//        String[] rescuepaths = new String[]{};
        return rescuepaths;
    }


    /**
     * 救援文件拉取
     */
    public static void pullRescueFiles(String url) {
        downloadConfig(url);
    }


    /**
     * 读取救援文件内容
     */
    public static int rescuefileposition = 0;//救援文件列表排序


    public static void resolveRescueFiles(String fileName) {
        String filepath = App.getApplication().getFilesDir() + File.separator + fileName;
//        String filepath = App.getApplication().getExternalFilesDir(null) + File.separator + fileName;

        //保存救援文件地址
        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
        spUtils.put(SPConstants.DEFAULT.RESCUEFILESPATH, filepath);

        String lineTxt = "";
        StringBuilder sbstr = new StringBuilder();
        try {
            File file = new File(filepath);
            if (file.isFile() && file.exists()) {
                List<String> list = FileUtils.readFile2List(filepath, "utf-8");
                if (decryptedRescueFiles(list)) {
                    List<String> listcopy = FileUtils.readFile2List(decryptedrescuefilespath, "utf-8");
                    if (!StringUtils.isBlank(listcopy) && listcopy.size() > 0) {
                        for (String list1 : listcopy) {
                            sbstr.append(list1.trim());
                        }
                        rescueIpAssignment(sbstr);
                        //删除rescueipcopy.txt
                        FileUtils.deleteFile(decryptedrescuefilespath);
                    }
                } else {
                    LogUtils.i("----->解析文件失败，请重试!");
//                        ToastUtils.showLongToast("解析文件失败，请重试!");
                    //删除救援文件
                    FileUtils.deleteFile(App.getApplication().getFilesDir() + File.separator + fileName);

                    //继续循环动态ip列表中的ip
                    pullrescuepathsposition = 0;

                    defaultipposition = 0;
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);

                    DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(StaticStateUtils.basisActivity));
                    List<DynamicServersResponse.ServersBean> list2 = dataSource.findAll();
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list2.get(defaultipposition).getUrl());
                    dataSource.close();
                    StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);


//                    LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                        ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                    String msg = "正在优化网络,请稍后";
                    if (list2.get(defaultipposition).getRegion().equals("本地")) {
                        msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    } else {
                        msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    }
                    sendNetworkTimeoutReceiver(msg);

                    changeipflag = true;
                    detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 救援文件中的救援ip赋值
     */
    public static List<RescueIPServersResponse> rescueIpList;

    public static void rescueIpAssignment(StringBuilder sbstr) {
        if (!StringUtils.isBlank(sbstr)) {
            RescueIpResponse rescueIpResponse = new Gson().fromJson(sbstr.toString(), RescueIpResponse.class);
            SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
//            rescueIpList = rescueIpResponse.getRescueip();
            if (!StringUtils.isBlank(rescueIpResponse.getRescueip()) && rescueIpResponse.getRescueip().size() > 0) {
                //保存救援IP到数据RescueIPDataSource中
                RescueIPDataSource rescueIPDataSource = new RescueIPDataSource(new DBHelper(StaticStateUtils.basisActivity));
                rescueIPDataSource.clear();
                for (int i = 0; i < rescueIpResponse.getRescueip().size(); i++) {
                    rescueIPDataSource.insert1(rescueIpResponse.getRescueip().get(i));
                }
                rescueIpList = rescueIPDataSource.findAll();


                //救援IP不为空，则从第一个开始 请求测试接口
                spUtils.put(SPConstants.DEFAULT.DEFAULTIP, rescueIpList.get(rescuefileposition).getUrl());
                StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);
                rescuefileposition++;
//                LogUtils.i("changeIp---usebath----->" + StaticStateUtils.usebath);
//                ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);


                String msg = "正在优化网络,请稍后";
                msg = msg + "(#" + "3" + rescuefileposition + ")";
                spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "3");
                spUtils.put(SPConstants.DEFAULT.RESCUEFILEIPPOSITION, rescuefileposition);
                sendNetworkTimeoutReceiver(msg);

                changeipflag = true;
                detectDomainWhetherNormal(StaticStateUtils.basisActivity);

                rescueIPDataSource.close();
            } else {
                String[] pullrescuepaths = getPullRescuePaths();
                if (pullrescuepathsposition < pullrescuepaths.length) {
                    pullRescueFiles(pullrescuepaths[pullrescuepathsposition]);
                } else {
                    //继续循环动态ip列表中的ip
                    pullrescuepathsposition = 0;

                    defaultipposition = 0;
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);


                    DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(StaticStateUtils.basisActivity));
                    List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(defaultipposition).getUrl());
                    dataSource.close();
                    StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);

//                    LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                    ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);


                    String msg = "正在优化网络,请稍后";
                    if (list.get(defaultipposition).getRegion().equals("本地")) {
                        msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    } else {
                        msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    }
                    sendNetworkTimeoutReceiver(msg);

                    changeipflag = true;
                    detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                }
            }
        }
    }


    /**
     * 下载配置文件
     *
     * @param url 下载地址
     */
    private static void downloadConfig(String url) {
        long currenttime = System.currentTimeMillis();
        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url + "?t=" + currenttime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    pullrescuepathsposition = 0;
                    if (write2Data(response.body(), fileName)) {
                        resolveRescueFiles(fileName);
                    }
                } else {
                    pullrescuepathsposition++;

                    SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
                    String[] pullrescuepaths = getPullRescuePaths();
                    if (pullrescuepathsposition < pullrescuepaths.length) {
                        pullRescueFiles(pullrescuepaths[pullrescuepathsposition]);
                    } else {
                        //继续循环动态ip列表中的ip
                        pullrescuepathsposition = 0;
                        defaultipposition = 0;
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
                        DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(StaticStateUtils.basisActivity));
                        List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(defaultipposition).getUrl());
                        dataSource.close();
                        StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);


//                    LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                    ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                        String msg = "正在优化网络,请稍后";
                        if (list.get(defaultipposition).getRegion().equals("本地")) {
                            msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        } else {
                            msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                            spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                        }
                        sendNetworkTimeoutReceiver(msg);

                        changeipflag = true;
                        detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pullrescuepathsposition++;

                SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
                String[] pullrescuepaths = getPullRescuePaths();
                if (pullrescuepathsposition < pullrescuepaths.length) {
                    pullRescueFiles(pullrescuepaths[pullrescuepathsposition]);
                } else {
                    //继续循环动态ip列表中的ip
                    pullrescuepathsposition = 0;
                    defaultipposition = 0;
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, -1);
                    DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(StaticStateUtils.basisActivity));
                    List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
                    spUtils.put(SPConstants.DEFAULT.DEFAULTIP, list.get(defaultipposition).getUrl());
                    dataSource.close();
                    StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP);


//                    LogUtils.i("changeIp---usebath----->----->" + StaticStateUtils.usebath);
//                    ToastUtils.showLongToast("changeIp---usebath----->----->" + StaticStateUtils.usebath);

                    String msg = "正在优化网络,请稍后";
                    if (list.get(defaultipposition).getRegion().equals("本地")) {
                        msg = msg + "(#" + "1" + (defaultipposition - (list.size() - ipArray.length) + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "1");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    } else {
                        msg = msg + "(#" + "2" + (defaultipposition + 1) + ")";
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITIONTYPE, "2");
                        spUtils.put(SPConstants.DEFAULT.DEFAULTIPPOSITION, defaultipposition);
                    }
                    sendNetworkTimeoutReceiver(msg);

                    changeipflag = true;
                    detectDomainWhetherNormal(StaticStateUtils.basisActivity);
                }
            }
        });
    }

    /**
     * 把文件写入到data目录下
     *
     * @param body
     * @return
     */
    private static boolean write2Data(ResponseBody body, String fileName) {
        try {
            File futureStudioIconFile = new File(App.getApplication().getFilesDir() + File.separator + fileName);
//            File futureStudioIconFile = new File(App.getApplication().getExternalFilesDir(null) + File.separator + fileName);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 解密救援文件内容
     * 保存文件到rescueipcopy.txt
     */

    public static String decryptedrescuefilespath = App.getApplication().getFilesDir() + File.separator + "rescueipcopy.txt";
//    public static String decryptedrescuefilespath = App.getApplication().getExternalFilesDir(null) + File.separator + "rescueipcopy.txt";

    public static boolean decryptedRescueFiles(List<String> list) {
        boolean flag = false;
        if (!StringUtils.isBlank(list) && list.size() > 0) {
            String originalcontent = list.get(0);
            String translationcontent = "";
            try {
                translationcontent = AESCrypt.decrypt(StaticStateUtils.key, originalcontent);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            if (StringUtils.isBlank(translationcontent)) {
                flag = false;
            } else {
                flag = FileUtils.writeFileFromString(decryptedrescuefilespath, translationcontent, true);
            }
        } else {
            flag = false;
        }
        return flag;
    }


    /**
     * 00003
     * 写入日志文件到本机中(所有的LogUtils打印)
     * 日志记录用户判断用户错误信息
     */
//    public static String logdir = App.getApplication().getExternalFilesDir(null) + File.separator + "clientlog";//存放日志目录地址
    public static String logdir = App.getApplication().getFilesDir() + File.separator + "clientlog";//存放日志目录地址
    //    public static String uploadlogdir = App.getApplication().getExternalFilesDir(null) + File.separator + "uploadclientlog";//存放即将要上传日志目录地址
    public static String uploadlogdir = App.getApplication().getFilesDir() + File.separator + "uploadclientlog";//存放即将要上传日志目录地址
    public static FileOutputStream out = null;

    public static void writePrintLog(final String content) {
        long logdate = dateToLong(DateUtil.getCurrentDate());//获取当前时间（年，月，日）
        SPUtils userSPU = new SPUtils(SPConstants.SP_USER);
        int userid = userSPU.getInt(SPConstants.USER.USERID, 9999999);//获取用户当前ID
        if (FileUtils.createOrExistsDir(logdir)) {
            final String filepath = logdir + File.separator + logdate + "_" + "android_" + DeviceUtils.getUUID().replaceAll("-", "")
                    + ".txt";//用户文件地址
//            final String filepath = logdir + File.separator + "haha" + "_" + "android_" + DeviceUtils.getUUID().replaceAll("-", "")
//                    + ".txt";//用户文件地址

            try {
//                ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
//                new Thread(new Creater(queue, content + "\r\n")).start();//多线程往队列中写入数据
//                new Thread(new DealFile(out, queue)).start();//监听线程，不断从queue中读数据写入到文件中

                //多线程写信息到本地收集文件中
                final ExecutorService CreaterThreadPool = Executors.newCachedThreadPool();
                CreaterThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            Thread.sleep(1000);
                            File file = new File(filepath);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            out = new FileOutputStream(file, true);
                            out.write((content + "\r\n").getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                out.flush();
                                out.close();
                                CreaterThreadPool.shutdown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i("日志写入文件失败----->" + e.getMessage());
            }
        } else

        {
            LogUtils.i("日志文件目录【clientlog】创建失败");
        }


    }

    public static Long dateToLong(String date) {
        date = date.replaceAll("-", "");
        date = date.replaceAll(":", "");
        date = date.replaceAll(" ", "");
        return Long.parseLong(date);
    }

    /**
     * 多线程下写文件
     */
//将要写入文件的数据存入队列中
    static class Creater implements Runnable {
        private ConcurrentLinkedQueue<String> queue;
        private String contents;

        public Creater() {
        }

        public Creater(ConcurrentLinkedQueue<String> queue, String contents) {
            this.queue = queue;
            this.contents = contents;
        }

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.add(contents);
        }
    }

    //将队列中的数据写入到文件
    static class DealFile implements Runnable {
        private FileOutputStream out;
        private ConcurrentLinkedQueue<String> queue;

        public DealFile() {
        }

        public DealFile(FileOutputStream out, ConcurrentLinkedQueue<String> queue) {
            this.out = out;
            this.queue = queue;
        }

        public void run() {
            while (true) {//循环监听
                if (!queue.isEmpty()) {
                    try {
                        out.write(queue.poll().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 00003
     * 提交客户端调试日志文件(认证)
     */
    public static void uploadClientDebugLogs() {
        long logdate = StaticStateUtils.dateToLong(DateUtil.getCurrentDate());//获取当前时间（年，月，日）
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        int userid = sputilsuser.getInt(SPConstants.USER.USERID, 9999999);//获取用户当前ID
        boolean DEBUG_LOG_ENABLED = sputilsuser.getBoolean(SPConstants.USER.DEBUG_LOG_ENABLED, false);
        if (DEBUG_LOG_ENABLED) {
//        String filepath = logdir + File.separator + userid + "_" + logdate + ".txt";//用户文件地址
//        File file = FileUtils.getFileByPath(filepath);

            File dir = new File(uploadlogdir);
            final File[] files = dir.listFiles();

            if (files != null && files.length != 0) {
                String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);
                try {
                    MyHttpUtil myhttputil = new MyHttpUtil();
                    myhttputil.getClient().addHeader("Authorization", token);
                    RequestParams params = new RequestParams();
                    params.put("platform", "android");
                    params.put("uuid", DeviceUtils.getUUID());
                    params.put("log_file[]", files, "text/plain", null);
                    myhttputil.post(MyHttpAddress.CLIENT_DEBUG_LOGS, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String jsonString = StringUtils.byte2String(responseBody);
                            ApiResponse apiResponse = new Gson().fromJson(jsonString, ApiResponse.class);
                            LogUtils.i("jsonString----->" + jsonString);
                            deleteClientDebugLogs2(files);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            LogUtils.i("onFailure----->" + statusCode + "|||" + error.getMessage());
                            deleteClientDebugLogs2(files);
                        }
                    });


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 00003
     * 定期删除客户端调试日志文件
     */
    public static void deleteClientDebugLogs() {
        try {
            SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
            int debug_log_max_days = sputilsuser.getInt(SPConstants.USER.DEBUG_LOG_MAX_DAYS, 7);
            long logdate = StaticStateUtils.dateToLong(DateUtil.getCurrentDate());//获取当前时间（年，月，日）
            List<File> fileList = FileUtils.listFilesInDir(logdir);//获取目录下所有文件包括子目录
            if (fileList.size() > 0) {
                for (int i = 0; i < fileList.size(); i++) {
                    String filename = FileUtils.getFileName(fileList.get(i));
                    String[] filenameStrings = filename.split("_");
                    long filedate = Long.parseLong(filenameStrings[0]);
                    if ((logdate - filedate) > debug_log_max_days) {
                        FileUtils.deleteFile(fileList.get(i));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i("e.getMessage()----->" + e.getMessage());
        }
    }

    /**
     * 00003
     * 文件上传成功后 删除客户端调试日志文件
     */
    public static void deleteClientDebugLogs2(File[] files) {
        try {
            long logdate = StaticStateUtils.dateToLong(DateUtil.getCurrentDate());//获取当前时间（年，月，日）
            for (File file : files) {
//                String filename = FileUtils.getFileName(file);
//                String[] filenameStrings = filename.split("_");
//                long filedate = Long.parseLong(filenameStrings[filenameStrings.length - 1].substring(0, filenameStrings[filenameStrings.length - 1].lastIndexOf(".")));
                FileUtils.deleteFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i("e.getMessage()----->" + e.getMessage());
        }
    }


    /**
     * 00003
     * 将要上传的文件，复制到上传的文件夹中
     */
    public static void copyFileToUploadFile(String logdir, String uploadlogdir) {
        boolean uploadfalg = false;


        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        boolean DEBUG_LOG_ENABLED = sputilsuser.getBoolean(SPConstants.USER.DEBUG_LOG_ENABLED, false);


        if (DEBUG_LOG_ENABLED && !StringUtils.isBlank(sputilsuser.getString(SPConstants.USER.DEBUG_LOG_START_AT, ""))
                && !StringUtils.isBlank(sputilsuser.getString(SPConstants.USER.DEBUG_LOG_END_AT, ""))) {
            long DEBUG_LOG_START_AT = Long.parseLong(sputilsuser.getString(SPConstants.USER.DEBUG_LOG_START_AT, "").replaceAll("-", ""));
            long DEBUG_LOG_END_AT = Long.parseLong(sputilsuser.getString(SPConstants.USER.DEBUG_LOG_END_AT, "").replaceAll("-", ""));

            try {
                if (FileUtils.createOrExistsDir(uploadlogdir)) {
                    File dir = new File(logdir);
                    final File[] files = dir.listFiles();
                    for (File file : files) {
//                LogUtils.i("file----->" + file.getName());
                        String filename = FileUtils.getFileName(file);
                        String[] filenameStrings = filename.split("_");
                        long filedate = Long.parseLong(filenameStrings[0]);
//                        uploadfalg = FileUtils.copyFile(file.toString(), uploadlogdir + File.separator + filename);
                        if (filedate >= DEBUG_LOG_START_AT && filedate <= DEBUG_LOG_END_AT) {
                            uploadfalg = FileUtils.copyFile(file.toString(), uploadlogdir + File.separator + filename);
                            if (uploadfalg) {
                                uploadClientDebugLogs();//上传文件
                            }
                        } else {
                            LogUtils.i("复制失败，因为超出日期区间范围");
                        }
                    }
                } else {
                    LogUtils.i("日志文件目录【uploadclientlog】创建失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i("e.getMessage()----->" + e.getMessage());
            }
        } else {
            LogUtils.i("后台服务器传入的日期有异常");
        }


        deleteClientDebugLogs();
    }

    /**
     * 判断是ip还是域名
     */
    public static String pattern_domain = "\\w*\\.\\w*:";//域名访问
    public static String pattern_ip = "(\\d*\\.){3}\\d*";//ip正则验证

    public static boolean getIpOrDomain(String host) {
        Pattern ipPattern = Pattern.compile(pattern_ip);
        Matcher matcher = ipPattern.matcher(host);
        boolean ipAddressflag = matcher.find();
        return ipAddressflag;
    }

    public static NetworkTimeoutDialog networkTimeoutDialog;
    public static NetworkTimeoutReceiver networkTimeoutReceiver;


    /**
     * 初始化网络超时 控件
     * 创建者 XQ  2017.11.01
     */
    public static void initNetworkTimeoutControls(Activity activity, String interface_name) {
        if (networkTimeoutDialog == null) {
            networkTimeoutDialog = new NetworkTimeoutDialog(activity);
            networkTimeoutDialog.show();
        }

        if (networkTimeoutReceiver == null) {
            networkTimeoutReceiver = new NetworkTimeoutReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("networktimeout");
            StaticStateUtils.basisActivity.registerReceiver(networkTimeoutReceiver, intentFilter);
        }

        interfacename = interface_name;
    }

    /**
     * 发送网络超时广播内容
     * 创建者 XQ  2017.11.01
     */
    public static void sendNetworkTimeoutReceiver(String msg) {
        //发送广播
        Intent intent = new Intent();
        intent.setAction("networktimeout");
        intent.putExtra("msg", msg);
        StaticStateUtils.basisActivity.sendBroadcast(intent);
    }


    /**
     * 结束网络超时弹窗
     * 创建者 XQ 2017.11.02
     */

    public static void endNetworkTimeout() {
        if (networkTimeoutDialog != null) {
            networkTimeoutDialog.endAnimation();
            networkTimeoutDialog.dismiss();
            networkTimeoutDialog = null;
        }
        if (networkTimeoutReceiver != null) {
            StaticStateUtils.basisActivity.unregisterReceiver(networkTimeoutReceiver);
            networkTimeoutReceiver = null;
        }
        requestAPIReceiver();
    }

    /**
     * ip轮询切换成功后，发送广播，重新请求当前API
     * 创建者 XQ 2017.11.02
     */

    public static String interfacename;//重新请求API的标识

    public static void requestAPIReceiver() {
        //发送广播
        Intent intent = new Intent();
        intent.setAction(interfacename);
        NetworkReconnectionReceiver.getInstance().sendBroadCast(StaticStateUtils.basisActivity, intent);
    }


    /**
     * 00001
     * 获得客户端日志提交相关信息(认证)
     * 创建者 XQ  2017.12.21
     */

    public static void getClientLog(final SPUtils sp_userhttphead, final UserhttpheadDataSource dataSource) {
        SPUtils sputilsuser = new SPUtils(SPConstants.SP_USER);
        String token = sputilsuser.getString(SPConstants.USER.API_TOKEN);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.clientlog(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<ClientLogResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<ClientLogResponse> clientLogResponseApiResponse) {
//                        System.out.println("Token------>" + clientLogResponseApiResponse.getData().getToken());
                        if (clientLogResponseApiResponse.isSuccess()) {
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.token, clientLogResponseApiResponse.getData().getToken());
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.key, clientLogResponseApiResponse.getData().getKey());
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.enabled, clientLogResponseApiResponse.getData().isEnabled());
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.host_url, clientLogResponseApiResponse.getData().getHost_url());
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.reset_starttime, System.currentTimeMillis() + "");
                        }
                        userhttphead_flag = true;
                        toUpdateUserhttpheadData(sp_userhttphead, dataSource);
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                        userhttphead_flag = true;
                    }
                });
    }

    /**
     * 00001
     * 上传客户端日志提交相关信息
     * 创建者 XQ  2017.12.21
     */
    static int cyclesnum = 0;

    public static void uploadUserHttpHead(final Context context, final int count, final UserhttpheadDataSource dataSource) {
        final SPUtils sp_userhttphead = new SPUtils(SPConstants.SP_USERHTTPHEAD);
        String userhttphead_ip = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.host_url);
        String token = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.token);

//        final UserhttpheadDataSource dataSource = new UserhttpheadDataSource(new DBHelper(StaticStateUtils.basisActivity));
        final List<UserhttpheadResponse.ListBean> list = dataSource.findAllFromNumber(count + "");
        if (list.size() > 0) {
            UserhttpheadResponse userhttpheadResponse = new UserhttpheadResponse();
            userhttpheadResponse.setList(list);
            final String jsonStu = new Gson().toJson(userhttpheadResponse, UserhttpheadResponse.class);
            //压缩成gzip
            byte[] encrypt = GZipUtil.compress(jsonStu, GZipUtil.GZIP_ENCODE_UTF_8);
            String jsonstuencrypt = "";
            try {
                //加密数据
//            jsonstuencrypt = AESCrypt.encrypt(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.key), jsonStu);
                jsonstuencrypt = AESCrypt.encrypt1(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.key), encrypt);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
//        StringEntity stringEntity = new StringEntity(jsonStu, "UTF-8");
            StringEntity stringEntity = new StringEntity(jsonstuencrypt, "UTF-8");
            System.out.println("jsonStu______>" + cyclesnum + "_____" + uploadround + "_____" + list.size() + "_____" + jsonstuencrypt);

//            writePrintLog(jsonstuencrypt);
//            final int len_num = sp_userhttphead.getInt(SPConstants.USERHTTPHEAD.len_num, 0) + 1;
//            sp_userhttphead.put(SPConstants.USERHTTPHEAD.len_num, len_num);
//
//
            MyHttpUtil myhttputil = new MyHttpUtil();
            myhttputil.getClient().addHeader("CLIENTAUTHKEY", token);
            myhttputil.post(context, userhttphead_ip, stringEntity, "application/json;charset=UTF-8", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    LogUtils.i("statusCode_____>" + statusCode);
                    if (statusCode == 200) {
//                        getHttpDataLenFromFile(sp_userhttphead);
                        dataSource.deleteDataFromPrimaryid(list);
//                        dataSource.close();
                        cyclesnum++;
                        if (cyclesnum < uploadround) {
                            uploadUserHttpHead(StaticStateUtils.basisActivity, count, dataSource);
                        } else {
                            cyclesnum = 0;
                            userhttphead_flag = true;
                            sp_userhttphead.put(SPConstants.USERHTTPHEAD.send_starttime, System.currentTimeMillis() + "");
                        }
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    LogUtils.i("onFailure----->" + statusCode + "|||" + error.getMessage());
//                    getHttpDataLenFromFile(sp_userhttphead);
//                    dataSource.close();
                    cyclesnum++;
                    if (cyclesnum < uploadround) {
                        uploadUserHttpHead(StaticStateUtils.basisActivity, count, dataSource);
                    } else {
                        cyclesnum = 0;
                        userhttphead_flag = true;
                        sp_userhttphead.put(SPConstants.USERHTTPHEAD.send_starttime, System.currentTimeMillis() + "");
                    }
                }
            });
        } else {
            userhttphead_flag = true;
        }

    }


    /**
     * 测试上传客户端信息方法
     *
     * @param context
     * @param count
     */
//    public static void uploadUserHttpHead1(final Context context, final int count) {
//        final SPUtils sp_userhttphead = new SPUtils(SPConstants.SP_USERHTTPHEAD);
//        String userhttphead_ip = "http://192.168.125.58:7788/putdata_client";
//        String token = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.token);
//
//        final UserhttpheadDataSource dataSource = new UserhttpheadDataSource(new DBHelper(StaticStateUtils.basisActivity));
//        final List<UserhttpheadResponse.ListBean> list = dataSource.findAllFromNumber(count + "");
//        UserhttpheadResponse userhttpheadResponse = new UserhttpheadResponse();
//        userhttpheadResponse.setList(list);
//        final String jsonStu = new Gson().toJson(userhttpheadResponse, UserhttpheadResponse.class);
//        System.out.println("jsonStu______>" + cyclesnum + "_____" + uploadround + "_____" + list.size() + "_____" + jsonStu);
//
//        //压缩成gzip
//        byte[] a = GZipUtil.compress(jsonStu, GZipUtil.GZIP_ENCODE_UTF_8);
//        String encrypt = "";
//        try {
//            encrypt = AESCrypt.encrypt1(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.key), a);
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//        StringEntity stringEntity = new StringEntity(encrypt, "UTF-8");
////        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(a);
//
//
//        try {
//            MyHttpUtil myhttputil = new MyHttpUtil();
//            myhttputil.getClient().addHeader("CLIENTAUTHKEY", token);
//            myhttputil.post(context, userhttphead_ip, stringEntity, "application/json;charset=UTF-8", new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    LogUtils.i("statusCode_____>" + statusCode);
//                    if (statusCode == 200) {
//
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    LogUtils.i("onFailure----->" + statusCode + "|||" + error.getMessage());
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 00001
     * 从user_httphead数据库中获取 UserhttpheadResponse.ListBean数据
     * 创建者 XQ  2017.12.21
     */

    static int uploadround = 0;

    public static void getUserhttpheadData(Long number, SPUtils sp_userhttphead, UserhttpheadDataSource dataSource) {
        SPUtils SP_PROXY = new SPUtils(SPConstants.SP_PROXY);
        try {
//            int count = 50;//多少条数据,传一次
            int count = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.DLOG_POOL_MAX_COUNT, "50"));
            uploadround = (int) (number / count);
            if (uploadround == 0) {
                uploadround = 1;
            }
            uploadUserHttpHead(StaticStateUtils.basisActivity, count, dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 00001
     * 上传客户端日志提交相关信息 入口方法
     * 创建者 XQ  2017.12.21
     */

    public static boolean userhttphead_flag = true;

    public static void toUpdateUserhttpheadData(SPUtils sp_userhttphead, UserhttpheadDataSource dataSource) {
        if (userhttphead_flag) {
            userhttphead_flag = false;
            String reset_starttime = sp_userhttphead.getString(SPConstants.USERHTTPHEAD.reset_starttime, "");

            if (StringUtils.isBlank(reset_starttime)) {
                getClientLog(sp_userhttphead, dataSource);
            } else {
                long reset_starttime_L = Long.parseLong(reset_starttime);
                long reset_difference = System.currentTimeMillis() - reset_starttime_L;
                if (Integer.parseInt(ConvertUtils.Mymillis2FitTimeSpan(reset_difference)) > 3600) {//间隔请求时间暂时写为1个小时
                    getClientLog(sp_userhttphead, dataSource);
                } else {
                    boolean enabled = sp_userhttphead.getBoolean(SPConstants.USERHTTPHEAD.enabled, true);
                    if (enabled) {

                        long send_starttime = Long.parseLong(sp_userhttphead.getString(SPConstants.USERHTTPHEAD.send_starttime, "0"));
                        long send_difference = System.currentTimeMillis() - send_starttime;

                        SPUtils SP_PROXY = new SPUtils(SPConstants.SP_PROXY);
                        int LOG_POOL_MAX_COUNT = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.DLOG_POOL_MAX_COUNT, "50"));
                        int POST_LOG_INTERVAL = Integer.parseInt(SP_PROXY.getString(SPConstants.PROXY.DLOG_POST_INTERVAL, "600"));

//                        UserhttpheadDataSource dataSource = new UserhttpheadDataSource(new DBHelper(StaticStateUtils.basisActivity));
                        long number = dataSource.getCount();
//                        dataSource.close();

                        if (number > LOG_POOL_MAX_COUNT
                                || Integer.parseInt(ConvertUtils.Mymillis2FitTimeSpan(send_difference)) > POST_LOG_INTERVAL) {
                            getUserhttpheadData(number, sp_userhttphead, dataSource);
                        } else {
                            userhttphead_flag = true;
                        }
                    } else {
                        userhttphead_flag = true;
//                        UserhttpheadDataSource dataSource = new UserhttpheadDataSource(new DBHelper(StaticStateUtils.basisActivity));
                        dataSource.clear();
//                        dataSource.close();
                    }
                }
            }
        }
    }

    /**
     * 00001
     * 保存客户端日志提交相关信息，并且计算出上传内容大小
     * 创建者 XQ  2017.12.26
     */
//    public static void getHttpDataLenFromFile(SPUtils sp_userhttphead) {
//        String filepath = logdir + File.separator + "haha" + "_" + "android_" + DeviceUtils.getUUID().replaceAll("-", "")
//                + ".txt";//用户文件地址
//        File file = FileUtils.getFileByPath(filepath);
//        long len = FileUtils.getFileLength(file);
//        //保存文件大小
//        sp_userhttphead.put(SPConstants.USERHTTPHEAD.len,
//                len + sp_userhttphead.getLong(SPConstants.USERHTTPHEAD.len, 0));
//        if (len != -1) {
//            len = sp_userhttphead.getLong(SPConstants.USERHTTPHEAD.len, 0);
//            String FileSize = FileUtils.byte2FitMemorySize(len);
////                    LogUtils.i("filesize_____>" + len_num + "_____" + FileSize);
//        }
//
//        FileUtils.deleteFile(filepath);//删除文件
//    }


    /**
     * 00002
     * 判断是模拟器还是真机
     * 创建者 XQ  2017.12.26
     * return  true 真机；false 模拟器
     */
//    public static boolean realMobileMachine(Activity activity) {
//        boolean flag = true;
//        //IMEI号
//        List<String> imei_list = DeviceUtils.getUserIMEI();
//        if ("000000000000000".equals(imei_list.get(0))) {
//            flag = false;
//        }
//        //Serial序列号
//        String serial = android.os.Build.SERIAL;
//        if ("unknown".equals(serial)) {
//            flag = false;
//        }
//        //Mac地址
//        WifiManager wifimanage = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiinfo = wifimanage.getConnectionInfo();
//        if (StringUtils.isBlank(wifiinfo)) {
//            flag = false;
//        }
//        if (!flag) {
//            //模拟器起，直接退出APP
//            CustomDialogJumpLogin dialogJumpLogin = new CustomDialogJumpLogin();
//            dialogJumpLogin.CustomDialogtoEndApp(activity);
//        }
//        return flag;
//    }


    /**
     * 判断应用是否在前台
     * 创建者 XQ  2018.02.08
     */
    public static boolean isActive = true;

    public static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是英文版本还是中文版
     */
    public static String judgmentLanguage() {
        String language = "";
        int languagenum = 0;//默认为英文
        SPUtils sp_use_language = new SPUtils(SPConstants.SP_USE_LANGUAGE);
        languagenum = sp_use_language.getInt(SPConstants.USELANGUAGE.TAG_LANGUAGE, 0);
        if (0 == languagenum) {
            language = "zh-rCN";
        } else if (1 == languagenum) {
            language = "zh-rCN";
        } else if (2 == languagenum) {
            language = "zh-rTW";
        } else if (3 == languagenum) {
            language = "en";
        }
        return language;
    }

    /**
     * 服务器写死的英文翻译
     */
    public static String serverNametoEnglish(String parametername) {
        String servername = "";
        if (!StringUtils.isBlank(parametername)) {
            if (parametername.contains("精英")) {
                servername = "Elite Service";
            } else if (parametername.contains("王者")) {
                servername = "King Service";
            } else {
                servername = "Super Service";
            }
        }
        return servername;
    }

    /**
     * 套餐名称写死的英文翻译
     */
    public static String diamondMealtoEnglish(String parametername) {
        String servername = "";
        if (!StringUtils.isBlank(parametername)) {
            if (parametername.contains("精英")) {
                servername = "Elite Plan";
            } else if (parametername.contains("王者")) {
                servername = "King Plan";
            } else if (parametername.contains("钻石")) {
                servername = "Diamond Plan";
            } else {
                servername = "Super Service";
            }
        }
        return servername;
    }

    /**
     * 00004
     * 区分是域名还是ip段
     * 创建者 XQ  2018.04.24
     */
    public static Map<String, List<String>> domainOrIp(List<String> list) {
        Map<String, List<String>> listMap = new HashMap<>();
        List<String> domainList = new ArrayList<>();
        List<String> ipList = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String[] arrays = list.get(j).trim().split("\\s+");
            Pattern ipPattern = Pattern.compile(pattern_ip);
            for (int i = 0; i < arrays.length; i++) {
                Matcher matcher = ipPattern.matcher(arrays[i]);
                boolean ipAddressflag = matcher.find();
                if (ipAddressflag) {
                    ipList.add(arrays[i]);
                } else {
                    domainList.add(arrays[i]);
                }
            }
        }
        listMap.put("domainList", domainList);
        listMap.put("ipList", ipList);
        return listMap;
    }

    /**
     * 00004
     * 将android_d2o_customize文件数据转化成android_d2o文件格式
     * 创建者 XQ  2018.04.24
     */
    public static StringBuilder conversionFlieData(Map<String, List<String>> contentMap) {
        List<String> list = FileUtils.readFile2List("", "utf-8");
        StringBuilder sbstr = new StringBuilder();
        if (!StringUtils.isBlank(list) && list.size() > 0) {
            //文件中已经有内容
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toString().startsWith("proxy_domain")) {
                    if (contentMap.get("domainList").size() > 0) {
                        if (contentMap.get("domainList").size() == 1) {
                            sbstr.append(list.get(i).toString() + " " + contentMap.get("domainList").get(0) + "\r\n");
                        } else {
                            for (int j = 0; j < contentMap.get("domainList").size(); j++) {
                                if (j == 0) {
                                    sbstr.append(list.get(i).toString() + " " + contentMap.get("domainList").get(j) + " ");
                                } else if (j == (contentMap.get("domainList").size() - 1)) {
                                    sbstr.append(contentMap.get("domainList").get(j) + "\r\n");
                                } else {
                                    sbstr.append(contentMap.get("domainList").get(j) + " ");
                                }
                            }
                        }
                    } else {
                        sbstr.append(list.get(i).toString() + "\r\n");
                    }
                } else if (list.get(i).toString().startsWith("ipproxy")) {
                    if (contentMap.get("ipList").size() > 0) {
                        if (contentMap.get("ipList").size() == 1) {
                            sbstr.append(list.get(i).toString() + " " + contentMap.get("ipList").get(0) + "\r\n");
                        } else {
                            for (int j = 0; j < contentMap.get("ipList").size(); j++) {
                                if (j == 0) {
                                    sbstr.append(list.get(i).toString() + " " + contentMap.get("ipList").get(j) + " ");
                                } else if (j == (contentMap.get("ipList").size() - 1)) {
                                    sbstr.append(contentMap.get("ipList").get(j) + "\r\n");
                                } else {
                                    sbstr.append(contentMap.get("ipList").get(j) + " ");
                                }
                            }
                        }
                    } else {
                        sbstr.append(list.get(i).toString() + "\r\n");
                    }
                }
            }
        } else {
            //文件中没有内容
            if (contentMap.get("domainList").size() > 0) {
                if (contentMap.get("domainList").size() == 1) {
                    sbstr.append("proxy_domain" + " " + contentMap.get("domainList").get(0) + "\r\n");
                } else {
                    for (int j = 0; j < contentMap.get("domainList").size(); j++) {
                        if (j == 0) {
                            sbstr.append("proxy_domain" + " " + contentMap.get("domainList").get(j) + " ");
                        } else if (j == (contentMap.get("domainList").size() - 1)) {
                            sbstr.append(contentMap.get("domainList").get(j) + "\r\n");
                        } else {
                            sbstr.append(contentMap.get("domainList").get(j) + " ");
                        }
                    }
                }
            } else {
                sbstr.append("proxy_domain" + "\r\n");
            }
            if (contentMap.get("ipList").size() > 0) {
                if (contentMap.get("ipList").size() == 1) {
                    sbstr.append("ipproxy" + " " + contentMap.get("ipList").get(0) + "\r\n");
                } else {
                    for (int j = 0; j < contentMap.get("ipList").size(); j++) {
                        if (j == 0) {
                            sbstr.append("ipproxy" + " " + contentMap.get("ipList").get(j) + " ");
                        } else if (j == (contentMap.get("ipList").size() - 1)) {
                            sbstr.append(contentMap.get("ipList").get(j) + "\r\n");
                        } else {
                            sbstr.append(contentMap.get("ipList").get(j) + " ");
                        }
                    }
                }
            } else {
                sbstr.append("ipproxy" + "\r\n");
            }
        }
        return sbstr;
    }

    /**
     * 00004
     * 当 android_d2o_customize中没有内容的时候，将android_d2o文件内容复制到android_d2o_new文件中
     * 创建者 XQ  2018.04.24
     */
    public static boolean copyFileTOFile(String targetfilepath, String filepath) {
        boolean copyflag = false;
        if (FileUtils.isFileExists(filepath)) {
            if (FileUtils.copyFile(filepath, targetfilepath)) {
                copyflag = true;
            } else {
                copyflag = false;
            }
        } else {
            copyflag = false;
        }
        return copyflag;
    }

    /**
     * 00004
     * 合并android_d2o_new和android_d2o_customize文件内容
     * 创建者 XQ  2018.04.24
     */
    public static boolean mergeFiles(String originalFilePath, String usesFilePath, String targetFilePath) {
        boolean mergeflag = false;
        if (FileUtils.isFileExists(originalFilePath)) {
            String proxy_domain_target = "";
            String ipproxy_target = "";
            if (FileUtils.isFileExists(targetFilePath)) {
                List<String> targetList = FileUtils.readFile2List(targetFilePath, "utf-8");
                domainOrIp(targetList);
                StringBuilder sbstr = conversionFlieData(domainOrIp(targetList));
                String[] sbstrarray = sbstr.toString().split("\\r\\n");


                for (int i = 0; i < sbstrarray.length; i++) {//暂时只有proxy_domain和ipproxy 字段
                    if (sbstrarray[i].startsWith("proxy_domain")) {
                        proxy_domain_target = sbstrarray[i].replace("proxy_domain", "");
                    } else if (sbstrarray[i].startsWith("ipproxy")) {
                        ipproxy_target = sbstrarray[i].replace("ipproxy", "");
                    }
                }
            }
            List<String> originalList = FileUtils.readFile2List(originalFilePath, "utf-8");
            StringBuilder sbstr = new StringBuilder();
            if (!StringUtils.isBlank(originalList) && originalList.size() > 0) {
                for (int i = 0; i < originalList.size(); i++) {
                    if (originalList.get(i).toString().startsWith("proxy_domain")) {
                        if (!StringUtils.isBlank(proxy_domain_target)) {
                            sbstr.append(originalList.get(i).toString() + " " + proxy_domain_target.trim() + "\r\n");
                        } else {
                            sbstr.append(originalList.get(i).toString() + "\r\n");
                        }
                    } else if (originalList.get(i).toString().startsWith("ipproxy")) {
                        if (!StringUtils.isBlank(proxy_domain_target)) {
                            sbstr.append(originalList.get(i).toString() + " " + ipproxy_target.trim() + "\r\n");
                        } else {
                            sbstr.append(originalList.get(i).toString() + "\r\n");
                        }
                    } else {
                        sbstr.append(originalList.get(i).toString() + "\r\n");
                    }
                }
            }
            InputStream in = new ByteArrayInputStream(sbstr.toString().getBytes());
            if (FileUtils.writeFileFromIS(usesFilePath, in, false)) {
                LogUtils.i("----->文件写入成功！");
                mergeflag = true;
            } else {
                LogUtils.i("----->文件写入失败！");
                mergeflag = false;
            }
        } else {
            LogUtils.i("----->android_d2o不存在！");
            mergeflag = false;
        }
        return mergeflag;
    }

    /**
     * 00004
     * 初始化android_d2o_new(android_o2d_new)文件
     * 创建者 XQ  2018.04.24
     */
    public static boolean createNewRoutesConfig(String originalFilePath, String usesFilePath, int RID) {
        boolean createflag = false;
        if (FileUtils.isFileExists(originalFilePath)) {//如果android_d2o(android_o2d)文件存在
            if (!FileUtils.isFileExists(usesFilePath)) {
                createflag = copyFileTOFile(usesFilePath, originalFilePath);
            }
        } else {//如果android_d2o(android_o2d)文件不存在
            InputStream in = App.getApplication().getResources().openRawResource(RID);
            if (FileUtils.writeFileFromIS(usesFilePath, in, false)) {
                LogUtils.i("----->文件写入成功！");
                if (!FileUtils.isFileExists(usesFilePath)) {
                    createflag = copyFileTOFile(usesFilePath, originalFilePath);
                }
            } else {
                LogUtils.i("----->文件写入失败！");
            }
        }
        return createflag;
    }

    /**
     * 00005
     * 转化本地域名或者救援IP域名
     * 创建者 XQ  2018.05.16
     */
    public static String convertdomain(String domain) {
        String convert = "";
        if (domain.startsWith("https://") || domain.startsWith("http://")) {
            String bbb = domain.substring(domain.indexOf("//") + 2, domain.length());
            if (bbb.lastIndexOf("/") != -1) {
                convert = bbb.substring(0, bbb.length() - 1);
            } else {
                convert = bbb.substring(0, bbb.length());
            }
        } else {
            convert = domain;
        }
        return convert;
    }

    /**
     * 00005
     * 得到本地IP和救援IP的集合
     * 创建者 XQ  2018.05.16
     */
    public static List<String> dynamicandrescuelist = new ArrayList<>();

    public static void getDynamicAndRescue() {
        dynamicandrescuelist.clear();

        DynamicServersDataSource dynamicserversdatasource = new DynamicServersDataSource(new DBHelper(App.getApplication()));
        List<DynamicServersResponse.ServersBean> dynamiciplist = dynamicserversdatasource.findAll();
        dynamicserversdatasource.close();
        if (dynamiciplist.size() > 0) {
            for (int i = 0; i < dynamiciplist.size(); i++) {
                dynamicandrescuelist.add(convertdomain(dynamiciplist.get(i).getUrl()));
            }
        }
        RescueIPDataSource rescueipdatasource = new RescueIPDataSource(new DBHelper(App.getApplication()));
        List<RescueIPServersResponse> rescueiplist = rescueipdatasource.findAll();
        rescueipdatasource.close();
        if (rescueiplist.size() > 0) {
            for (int i = 0; i < rescueiplist.size(); i++) {
                dynamicandrescuelist.add(convertdomain(rescueiplist.get(i).getUrl()));
            }
        }
    }

}
