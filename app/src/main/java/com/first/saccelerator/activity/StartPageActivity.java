package com.first.saccelerator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.first.saccelerator.App;
import com.first.saccelerator.R;
import com.first.saccelerator.base.BaseActivity;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.ConnectionFailedDataSource;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.DynamicServerFailedDataSource;
import com.first.saccelerator.database.LoginFailedDataSource;
import com.first.saccelerator.database.NoOperationDataSource;
import com.first.saccelerator.database.OperationLogDataSource;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.BaseSubscriber;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.model.ConnectionFailedResponse;
import com.first.saccelerator.model.DynamicServerFailedResponse;
import com.first.saccelerator.model.LoginFailedResponse;
import com.first.saccelerator.model.NoOperationResponse;
import com.first.saccelerator.model.OpenScreenAdResponse;
import com.first.saccelerator.model.OperationLogResponse;
import com.first.saccelerator.model.PopUpAdResponse;
import com.first.saccelerator.model.RoutesResponse;
import com.first.saccelerator.myclass.PublicPracticalMethod;
import com.first.saccelerator.receiver.MyInstalledReceiver;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.DeviceUtils;
import com.first.saccelerator.utils.FileUtils;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.PhoneUtils;
import com.first.saccelerator.utils.PicassoUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.ScreenUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XQ on 2017/3/25.
 * 启动页
 */
public class StartPageActivity extends BaseActivity {

    private Activity mActivity;

    private ImageView iv_startpage_temporary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        LogUtils.i("手机屏幕宽度(像素)----->" + ScreenUtils.getScreenWidth() + "|" + "手机屏幕高度(像素)----->" + ScreenUtils.getScreenHeight());
        LogUtils.i("手机屏幕密度----->" + ScreenUtils.getScreenDensity(mActivity) + "|" + "手机屏幕densityDpi----->"
                + ScreenUtils.getScreendensityDpi(mActivity));
        LogUtils.i("手机屏幕宽度(dp)----->" + ScreenUtils.getScreenWidthDP(mActivity) + "|" + "手机屏幕高度(dp)----->" + ScreenUtils.getScreenHeightDP(mActivity));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_startpage;
    }

    /**
     * 初始化控件
     */
    public void initView() {
        mActivity = StartPageActivity.this;

        iv_startpage_temporary = (ImageView) findViewById(R.id.iv_startpage_temporary);


        SPUtils spUtils = new SPUtils(SPConstants.SP_CONFIG);
        final SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);

        long startpage_time = Long.parseLong(spUtils.getString(SPConstants.CONFIG.STARTPAGE_TIME, "2000"));

        initData();

        if (spUtils.getBoolean(SPConstants.CONFIG.IS_FIRST, true)) {
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        } else {
            // 跳转到主页面
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean openad_enabled = sputilsad.getBoolean(SPConstants.AD.openad_enabled, true);
                    boolean openad_imagesuccess = sputilsad.getBoolean(SPConstants.AD.openad_imagesuccess, false);
                    if (openad_enabled && openad_imagesuccess) {
                        Intent intent = new Intent(mActivity,
                                MainActivity.class);
                        intent.putExtra("classname", "StartPageActivity");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
//                        startActivity(new Intent(mActivity, MainActivitySecond.class));
                        StaticStateUtils.intentToJump(mActivity, MainActivity.class
                                , Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                    finish();
                }
            }, startpage_time);
        }
    }

    /**
     * 初始化数据
     */
    public void initData() {
        // 给用户设置别名，用来呼应极光推送
//        StaticStateUtils.setAlias(this);
        getSplashScreen();
        getPopUpAD();

        getRoutesConfig();
        uploadOperationLog();
        uploadLoginFailedLog();
        uploadNoOperationLog();
        uploadConnectionFailedLog();
        uploadDynamicServerFailedRecord();
//        LogUtils.i("getChannelName----->" + StaticStateUtils.getChannelName(mActivity));
        StaticStateUtils.dynamicServers(mActivity);

        new MyInstalledReceiver.AppInfoTask().executeOnExecutor(Executors.newCachedThreadPool(), App.getApplication());

    }


    /**
     * 定义一个内部计时器
     */
    private Timer timer;//时间类
    private int timecount = 2;//倒计时时间,默认是2秒
    private static final int PAGEJUMP = 1;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = PAGEJUMP;
            handler.sendMessage(message);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PAGEJUMP:
                    timecount--;
                    if (timecount < 0) {
                        //跳转到引导页
                        Intent intent = new Intent(
                                mActivity,
                                MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        timer.cancel();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NoOperationDataSource dataSource = new NoOperationDataSource(new DBHelper(mActivity));
        dataSource.delete("");
        dataSource.close();
    }

    /**
     * 上传操作日志内容
     */
    private List<OperationLogResponse> logResponseList;

    public void uploadOperationLog() {
        OperationLogResponse response;
        final OperationLogDataSource dataSource = new OperationLogDataSource(new DBHelper(mActivity));
        logResponseList = dataSource.findAll();//查询数据

        if (!StringUtils.isBlank(logResponseList) && logResponseList.size() > 0) {
            String operations = "";
            for (int i = 0; i < logResponseList.size(); i++) {
                String operationid = logResponseList.get(i).getOperationid() + "";
                String operationuserid = logResponseList.get(i).getOperationuserid() + "";
                String operationtime = logResponseList.get(i).getOperationtime() + "";
                if (i == (logResponseList.size() - 1)) {
                    operations += operationid + "," + operationuserid + "," + operationtime;
                } else {
                    operations += operationid + "," + operationuserid + "," + operationtime + "|";
                }
            }
            LogUtils.i("operations----->" + operations);
            SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
            String app_launch_id = sputilsconfig.getString(SPConstants.CONFIG.APP_LAUNCH_ID, "");//App运行日志ID, 此参数在用户登录后返回
            String signin_log_id = sputilsconfig.getString(SPConstants.CONFIG.SIGNIN_LOG_ID, "");//用户登录日志ID，此参数在用户登录后返回
            String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
            String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
            String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1

//            SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
//            String token = spUtils.getString(SPConstants.USER.API_TOKEN, "");


            Map<String, String> operationsmap = new HashMap<>();
            operationsmap.put("app_launch_id", app_launch_id);
            operationsmap.put("signin_log_id", signin_log_id);
            operationsmap.put("app_channel", app_channel);
            operationsmap.put("app_version", app_version);
            operationsmap.put("app_version_number", app_version_number);
            operationsmap.put("operations", operations);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.operationLog(operationsmap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse>() {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
//                            LogUtils.i("onSuccessmessage----->" + apiResponse.isSuccess());
                            if (apiResponse.isSuccess()) {
                                dataSource.clear();
                            }
                            dataSource.close();


                            //记录操作日志
                            StaticStateUtils.OperationLogRecord(1, mActivity);
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message);

                            //记录操作日志
                            StaticStateUtils.OperationLogRecord(1, mActivity);
                            dataSource.close();

                        }
                    });
        } else {
            //记录操作日志
            StaticStateUtils.OperationLogRecord(1, mActivity);
        }

    }

    /**
     * 上传登录失败信息
     */
    private List<LoginFailedResponse> loginFailedResponseList;

    public void uploadLoginFailedLog() {
        LoginFailedResponse response;
        final LoginFailedDataSource dataSource = new LoginFailedDataSource(new DBHelper(mActivity));
        loginFailedResponseList = dataSource.findAll();//查询数据
        if (!StringUtils.isBlank(loginFailedResponseList) && loginFailedResponseList.size() > 0) {
            String data = "";
            for (int i = 0; i < loginFailedResponseList.size(); i++) {
                String failedid = loginFailedResponseList.get(i).getFailedid();
                String username = loginFailedResponseList.get(i).getUsername();
                long logintime = loginFailedResponseList.get(i).getLogintime();
                if (i == (loginFailedResponseList.size() - 1)) {
                    data += failedid + "," + username + "," + logintime;
                } else {
                    data += failedid + "," + username + "," + logintime + "|";
                }
            }
//            LogUtils.i("data----->" + data);
            Map<String, String> map = new HashMap<>();
            map.put("uuid", DeviceUtils.getUUID());
            map.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.signinfailed(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse>() {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
//                            LogUtils.i("onSuccessmessage----->" + apiResponse.isSuccess());
                            if (apiResponse.isSuccess()) {
//                                LogUtils.i("uploadLoginFailedLog----->登录失败记录提交成功");
                                dataSource.clear();
                            }
                            dataSource.close();
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message);
                        }
                    });
        }
    }

    /**
     * 上传用户未操作信息
     */
    private List<NoOperationResponse> noOperationResponseList;

    public void uploadNoOperationLog() {
        NoOperationResponse response;
        final NoOperationDataSource dataSource = new NoOperationDataSource(new DBHelper(mActivity));
        dataSource.delete("");//删除用户名为空的数据
        noOperationResponseList = dataSource.findAll();//查询数据
        if (!StringUtils.isBlank(noOperationResponseList) && noOperationResponseList.size() > 0) {
            String data = "";
            for (int i = 0; i < noOperationResponseList.size(); i++) {
                String username = noOperationResponseList.get(i).getUsername();
                long creattime = noOperationResponseList.get(i).getCreattime();
                if (i == (noOperationResponseList.size() - 1)) {
                    data += username + "," + creattime;
                } else {
                    data += username + "," + creattime + "|";
                }
            }
//            LogUtils.i("data----->" + data);
            String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
            String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
            String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1
            String uuid = DeviceUtils.getUUID();// 设备uuid
            String device_model = DeviceUtils.getModel();// 设备型号(如: iphone6)
            String operator = PhoneUtils.getSimOperatorByMnc();// 网络运营商(如: unicomm)

            Map<String, String> map = new HashMap<>();
            map.put("uuid", uuid);
            map.put("app_channel", app_channel);
            map.put("app_version", app_version);
            map.put("app_version_number", app_version_number);
            map.put("device_model", device_model);
            map.put("operator", operator);
            map.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.nooperation(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse>() {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
//                            LogUtils.i("onSuccessmessage----->" + apiResponse.isSuccess());
                            if (apiResponse.isSuccess()) {
//                                LogUtils.i("uploadLoginFailedLog----->用户未操作记录提交成功");
                                dataSource.clear();
                            }
                            dataSource.close();

                            //默认认为用户没有操作
                            StaticStateUtils.noOperationRecord(mActivity);
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message);
                            //默认认为用户没有操作
                            StaticStateUtils.noOperationRecord(mActivity);
                            dataSource.close();
                        }
                    });
        } else {
            //默认认为用户没有操作
            StaticStateUtils.noOperationRecord(mActivity);
        }
    }

    /**
     * 上传用户连接VPN失败信息
     */
    private List<ConnectionFailedResponse> connectionFailedResponseList;

    public void uploadConnectionFailedLog() {
        ConnectionFailedResponse response;
        final ConnectionFailedDataSource dataSource = new ConnectionFailedDataSource(new DBHelper(mActivity));
        connectionFailedResponseList = dataSource.findAll();//查询数据
        if (!StringUtils.isBlank(connectionFailedResponseList) && connectionFailedResponseList.size() > 0) {
            String data = "";
            for (int i = 0; i < connectionFailedResponseList.size(); i++) {

                String failedid = connectionFailedResponseList.get(i).getFailedid();
                String username = connectionFailedResponseList.get(i).getUsername();
                long createtime = connectionFailedResponseList.get(i).getCreatetime();
                String servertypename = connectionFailedResponseList.get(i).getServertypename();
                String serverareaname = connectionFailedResponseList.get(i).getServerareaname();
                String servername = connectionFailedResponseList.get(i).getServername();

                if (i == (connectionFailedResponseList.size() - 1)) {
                    data += failedid + "," + username + "," + createtime + "," + servertypename + "," + serverareaname + "," + servername;
                } else {
                    data += failedid + "," + username + "," + createtime + "," + servertypename + "," + serverareaname + "," + servername + "|";
                }
            }
//            LogUtils.i("data----->" + data);
            String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
            String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
            String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1
            String uuid = DeviceUtils.getUUID();// 设备uuid
            String device_model = DeviceUtils.getModel();// 设备型号(如: iphone6)
            String operator = PhoneUtils.getSimOperatorByMnc();// 网络运营商(如: unicomm)

            Map<String, String> map = new HashMap<>();
            map.put("uuid", uuid);
            map.put("app_channel", app_channel);
            map.put("app_version", app_version);
            map.put("app_version_number", app_version_number);
            map.put("device_model", device_model);
            map.put("operator", operator);
            map.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.connectionfailed(map)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse>() {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
//                            LogUtils.i("onSuccessmessage----->" + apiResponse.isSuccess());
                            if (apiResponse.isSuccess()) {
//                                LogUtils.i("uploadLoginFailedLog----->用户未操作记录提交成功");
                                dataSource.clear();
                            }
                            dataSource.close();
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message);
                            dataSource.close();
                        }
                    });
        }
    }

    /**
     * 获得客户端路由配置表
     */
    private void getRoutesConfig() {
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.routesv3()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<RoutesResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<RoutesResponse> response) {
                        if (response != null && response.getData() != null) {
                            RoutesResponse data = response.getData();
                            SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);
                            boolean is_domestic = data.isIs_domestic();
                            configSP.put(SPConstants.CONFIG.IS_DOMESTIC, is_domestic);
                            int updated_at = data.getUpdated_at();
                            int routes_updated_at = configSP.getInt(SPConstants.CONFIG.ROUTES_UPDATED_AT, 0);

//                            int china_ip_updated_at = data.getChina_ip_update_at();
//                            int china_ip_routes_updated_at = configSP.getInt(SPConstants.CONFIG.ROUTES_CHINA_IP_UPDATED_AT, 0);
//
//                            int foreign_ip_update_at = data.getForeign_ip_update_at();
//                            int routes_foreign_ip_update_at = configSP.getInt(SPConstants.CONFIG.ROUTES_FOREIGN_IP_UPDATE_AT, 0);


                            /**
                             * 比对时间，如果相等表示配置文件没有更新
                             */
                            if (updated_at != routes_updated_at) {
                                configSP.put(SPConstants.CONFIG.ROUTES_UPDATED_AT, updated_at);
                                PublicPracticalMethod.newInstance().downloadVpnConfig(data.getD2o(), mActivity);
                                PublicPracticalMethod.newInstance().downloadVpnConfig(data.getO2d(), mActivity);
                            }
//                            if (china_ip_updated_at != china_ip_routes_updated_at) {
//                                configSP.put(SPConstants.CONFIG.ROUTES_CHINA_IP_UPDATED_AT, china_ip_updated_at);
//                                downloadConfig(data.getChina_ip_list());
//                            }
//                            if (foreign_ip_update_at != routes_foreign_ip_update_at) {
//                                configSP.put(SPConstants.CONFIG.ROUTES_FOREIGN_IP_UPDATE_AT, foreign_ip_update_at);
//                                downloadConfig(data.getForeign_ip_list());
//                            }
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }

    /**
     * 上传动态服务器连接失败信息
     */
    private List<DynamicServerFailedResponse> dynamicServerFailedResponseList;

    public void uploadDynamicServerFailedRecord() {
        DynamicServerFailedResponse response = null;
        final DynamicServerFailedDataSource dataSource = new DynamicServerFailedDataSource(new DBHelper(mActivity));
        dynamicServerFailedResponseList = dataSource.findAll();

        if (!StringUtils.isBlank(dynamicServerFailedResponseList) && dynamicServerFailedResponseList.size() > 0) {
            String data = "";
            for (int i = 0; i < dynamicServerFailedResponseList.size(); i++) {
//                String username = dynamicServerFailedResponseList.get(i).getUsername();
                String userid = dynamicServerFailedResponseList.get(i).getUserid() + "";
                String url = dynamicServerFailedResponseList.get(i).getUrl();
                String createtime = dynamicServerFailedResponseList.get(i).getCreatetime() + "";
                if (i == (dynamicServerFailedResponseList.size() - 1)) {
                    data += userid + "," + url + "," + createtime;
                } else {
                    data += userid + "," + url + "," + createtime + "|";
                }
            }
//            LogUtils.i("data----->" + data);

            String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
            String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
            String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1
            String device_model = DeviceUtils.getModel();//设备型号(如: iphone6)
            String platform = "android";// 设备系统平台(如: ios)
            String operator = PhoneUtils.getSimOperatorByMnc();// 网络运营商(如: unicomm)

            Map<String, String> operationsmap = new HashMap<>();
            operationsmap.put("app_channel", app_channel);
            operationsmap.put("app_version", app_version);
            operationsmap.put("app_version_number", app_version_number);
            operationsmap.put("device_model", device_model);
            operationsmap.put("platform", platform);
            operationsmap.put("operator", operator);
            operationsmap.put("data", data);
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.dynamicserverfailed(operationsmap)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse<DynamicServerFailedResponse>>() {
                        @Override
                        public void onSuccess(ApiResponse<DynamicServerFailedResponse> dynamicServerFailedResponseApiResponse) {
                            if (dynamicServerFailedResponseApiResponse.isSuccess()) {
                                dataSource.clear();
                            }
                            dataSource.close();
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.i("onFailuremessage----->" + message);
                            dataSource.close();
                        }
                    });
        }
    }

    /**
     * 获得开屏广告信息
     */
    public void getSplashScreen() {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1
        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.splashscreen(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<OpenScreenAdResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<OpenScreenAdResponse> openScreenAdResponseApiResponse) {
                        SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
                        if (!StringUtils.isBlank(openScreenAdResponseApiResponse.getData().getAd())) {
                            sputilsad.put(SPConstants.AD.openad_enabled, openScreenAdResponseApiResponse.getData().isIs_enabled());
                            sputilsad.put(SPConstants.AD.openad_overtime, openScreenAdResponseApiResponse.getData().getOvertime());
                            sputilsad.put(SPConstants.AD.openad_id, openScreenAdResponseApiResponse.getData().getAd().getId());
                            sputilsad.put(SPConstants.AD.openad_duration, openScreenAdResponseApiResponse.getData().getAd().getDuration());
                            sputilsad.put(SPConstants.AD.openad_imageurl, openScreenAdResponseApiResponse.getData().getAd().getImage_url());
                            sputilsad.put(SPConstants.AD.openad_linkurl, openScreenAdResponseApiResponse.getData().getAd().getLink_url());
                            String openad_updatedat = sputilsad.getString(SPConstants.AD.openad_updatedat, "0");
                            if (!openad_updatedat.equals(openScreenAdResponseApiResponse.getData().getAd().getUpdated_at())) {
                                FileUtils.deleteFilesInDir(PicassoUtils.openAddir);
//                                sputilsad.put(SPConstants.AD.openad_imagesuccess, false);
                                sputilsad.put(SPConstants.AD.openad_updatedat, openScreenAdResponseApiResponse.getData().getAd().getUpdated_at());
                                PicassoUtils.loadOpenPicFromUrl(openScreenAdResponseApiResponse.getData().getAd().getImage_url(), iv_startpage_temporary);
                            }

//                            String aurl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3917487116,1694656350&fm=27&gp=0.jpg";
//                            String burl = "http://www.dabaoku.net/katong-dongman/haizuiwang/images/032bf.jpg";
//                            int num = sputilsad.getInt(SPConstants.AD.testaaa, 0);
//                            if (num % 2 == 0) {
//                                sputilsad.put(SPConstants.AD.openad_imageurl, burl);
//                                sputilsad.put(SPConstants.AD.openad_updatedat, openScreenAdResponseApiResponse.getData().getAd().getUpdated_at());
//                                PicassoUtils.loadOpenPicFromUrl(burl, iv_startpage_temporary);
//                            } else {
//                                sputilsad.put(SPConstants.AD.openad_imageurl, aurl);
//                                sputilsad.put(SPConstants.AD.openad_updatedat, openScreenAdResponseApiResponse.getData().getAd().getUpdated_at());
//                                PicassoUtils.loadOpenPicFromUrl(aurl, iv_startpage_temporary);
//                            }
//                            sputilsad.put(SPConstants.AD.testaaa, num + 1);


                        } else {
                            sputilsad.put(SPConstants.AD.openad_enabled, openScreenAdResponseApiResponse.getData().isIs_enabled());
                            sputilsad.put(SPConstants.AD.openad_overtime, openScreenAdResponseApiResponse.getData().getOvertime());
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }

    /**
     * 获得弹出广告信息
     */
    public void getPopUpAD() {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(mActivity);//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(mActivity) + "";//app版本号, 如: 0.1
        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.popup(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<PopUpAdResponse>>() {
                    @Override
                    public void onSuccess(ApiResponse<PopUpAdResponse> popUpAdResponseApiResponse) {
                        SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
                        if (popUpAdResponseApiResponse.getData().isIs_enabled()) {
                            sputilsad.put(SPConstants.AD.popup_enabled, popUpAdResponseApiResponse.getData().isIs_enabled());
                            sputilsad.put(SPConstants.AD.popup_daytimes, popUpAdResponseApiResponse.getData().getDay_times());
                            sputilsad.put(SPConstants.AD.popup_imageurl, popUpAdResponseApiResponse.getData().getImage_url());
                            sputilsad.put(SPConstants.AD.popup_linkurl, popUpAdResponseApiResponse.getData().getLink_url());
                            String popup_updatedat = sputilsad.getString(SPConstants.AD.popup_updatedat, "0");
                            if (!popup_updatedat.equals(popUpAdResponseApiResponse.getData().getUpdated_at())) {
                                FileUtils.deleteFilesInDir(PicassoUtils.popupAddir);
//                                sputilsad.put(SPConstants.AD.popup_imagesuccess, false);
                                sputilsad.put(SPConstants.AD.popup_updatedat, popUpAdResponseApiResponse.getData().getUpdated_at());
                                PicassoUtils.loadPopupPicFromUrl(popUpAdResponseApiResponse.getData().getImage_url(), iv_startpage_temporary);
                            }
                        } else {
                            sputilsad.put(SPConstants.AD.popup_enabled, popUpAdResponseApiResponse.getData().isIs_enabled());
                        }

                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }


}
