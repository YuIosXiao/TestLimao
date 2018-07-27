package com.first.saccelerator.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.first.saccelerator.R;
import com.first.saccelerator.activity.BuyDiamondActivity2;
import com.first.saccelerator.activity.MainActivitySecond;
import com.first.saccelerator.activity.UserActivity;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.LoginRecordDataSource;
import com.first.saccelerator.database.ServerVaildPeriodDataSource;
import com.first.saccelerator.database.SettingsDataSource;
import com.first.saccelerator.dialog.CustomProgressDialog;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.BaseSubscriber;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.model.FunctionModel;
import com.first.saccelerator.model.IconHeaderItem;
import com.first.saccelerator.model.LoginRecordResponse;
import com.first.saccelerator.model.SigninResponseV2;
import com.first.saccelerator.presenter.FunctionCardPresenter;
import com.first.saccelerator.presenter.HeaderItemPresenter;
import com.first.saccelerator.utils.AESCrypt;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.DeviceUtils;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.NetworkUtils;
import com.first.saccelerator.utils.PhoneUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;
import com.first.saccelerator.utils.ToastUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XQ on 2018/6/11.
 * TV首页的Fragment
 */

public class MainFragment extends BrowseFragment {

    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        buildRowsAdapter();
        initData();
    }

    /**
     * 准备背景管理器
     */
    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(mActivity);
        mBackgroundManager.attach(mActivity.getWindow());
        mDefaultBackground = getResources().getDrawable(R.mipmap.main_center_bg);
        mMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        mBackgroundManager.setDrawable(mDefaultBackground);
    }

    /**
     * 设置UI元素
     */
    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));//设置标题
//        setBadgeDrawable(getActivity().getResources().getDrawable(
//                R.drawable.title_android_tv));//展示在标题栏上的图片(图片会隐藏标题)
        setHeadersState(HEADERS_DISABLED);//设置侧边栏显示状态 enabled 可见
        setHeadersTransitionOnBackEnabled(true);//暂不知道方法具体作用
        setBrandColor(getResources().getColor(R.color.appgreen1));//设置快速通道（侧边栏）背景
        setSearchAffordanceColor(getResources().getColor(R.color.crowded_yellow)); //搜索图标颜色
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new HeaderItemPresenter();
            }
        });
    }

    /**
     * 创建适配器并加载数据
     */
    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        addFunctionRow();
        setAdapter(rowsAdapter);

        /**
         * 点击监听
         */
        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof FunctionModel) {
                    FunctionModel functionModel = (FunctionModel) item;
                    Intent intent = functionModel.getIntent();
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            }
        });


    }

    private void addFunctionRow() {
        String headerName = getResources().getString(R.string.app_header_function_name);
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, headerName, R.mipmap.main_function);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionModel> functionModels = FunctionModel.getFunctionList(mActivity, classname_, class_, image_);
        int cardCount = functionModels.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionModels.get(i));
        }
//        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("----->onResume");
        mBackgroundManager.setDrawable(mDefaultBackground);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        loginOptions = new HashMap<>();
        signinv2();
    }


    /**
     * 登录V2
     */
    private Map<String, String> loginOptions;
    public static boolean isLogining = false; // 是否正在登录（含分配账号登录）

    private void signinv2() {
        if (!"LoginSecondActivity".equals(StaticStateUtils.classname)) {
            startProgressDialog(mActivity, "加载中...");//loading条开始
            loginOptions.put("device_uuid", DeviceUtils.getUUID());                   // 设备uuid
//        loginOptions.put("device_uuid", "542e5bb9-fbee-37ee-88e2-194f97b74k56");                   // 设备uuid
            loginOptions.put("device_name", DeviceUtils.getModel());                           // 设备名(如: xxx的iphone)
            loginOptions.put("device_model", DeviceUtils.getModel());                 // 设备型号(如: iphone6)
            loginOptions.put("platform", "android");                                  // 设备系统平台(如: ios)
            loginOptions.put("system_version", Build.VERSION.RELEASE);                // 设备版本(如: 10)
            loginOptions.put("operator", PhoneUtils.getSimOperatorByMnc());           // 网络运营商(如: unicomm)
            loginOptions.put("net_env", NetworkUtils.getNetworkType());               // 网络环境(如: 4g、wifi)
            loginOptions.put("app_version", StaticStateUtils.app_version);        // 版本名，来自版本渠道信息接口获取到的ID
            loginOptions.put("app_version_number", AppUtils.getAppVersionName(mActivity)); // App版本(如: 1.0)
            loginOptions.put("app_channel", StaticStateUtils.getChannelName(mActivity));                                // app渠道, 来自版本渠道信息接口获取到的ID
            loginOptions.put("sdid", StringUtils.isBlank(DeviceUtils.getUniqueDeviceID(mActivity)) ? "" : DeviceUtils.getUniqueDeviceID(mActivity));        //设备共享码
            loginOptions.put("apiid", StringUtils.isBlank(Build.VERSION.SDK_INT) ? "" : StringUtils.toStringFromStringBuffer(Build.VERSION.SDK_INT));        //Android机型硬件api码

            SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
            String username = spUtils.getString(SPConstants.USER.USERNAME, null);
            String password = spUtils.getString(SPConstants.USER.PASSWORD, null);
            if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
                loginOptions.put("username", username);               // (非必须) 手机号
                try {
                    loginOptions.put("password", AESCrypt.decrypt(StaticStateUtils.key, password));               // (非必须) 密码
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            LogUtils.i("options :----->" + loginOptions.toString());
            isLogining = true;
            SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);
            configSP.put(SPConstants.CONFIG.LOGIN_TIME, System.currentTimeMillis());
            ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
            apiService.signinv4(loginOptions)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ApiResponse<SigninResponseV2>>() {
                        @Override
                        public void onSuccess(ApiResponse<SigninResponseV2> response) {
                            stopProgressDialog();//loading条结束
                            isLogining = false;
//                            LogUtils.i("onSuccess: " + response.toString());
                            saveDatav2(response.getData());
//                            refreshUI();
                        }

                        @Override
                        public void onFailure(String message, int error_code) {
                            LogUtils.e("onFailuremessage----->" + message + "|" + error_code);
                            stopProgressDialog();//loading条结束
                            isLogining = false;
                            if (error_code == 3) {//密码错误
//                                SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
//                                //跳转到登录页面
//                                Intent intent = new Intent(mActivity,
//                                        LoginSecondActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                                finish();
                            } else if (error_code == 777) {
//                                StaticStateUtils.initNetworkTimeoutControls(mActivity, StaticStateUtils.interface_signin);
//                                interfaceReconnect(StaticStateUtils.interface_signin);
                            } else if (error_code == 1) {//用户不存在
//                                StaticStateUtils.intentToJump(mActivity, LoginSecondActivity.class
//                                        , Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                mActivity.finish();
                            }
                            ToastUtils.showLongToastSafe(message);
                            //提交客户端日志方法
//                            StaticStateUtils.copyFileToUploadFile(StaticStateUtils.logdir, StaticStateUtils.uploadlogdir);
                            return;
                        }
                    });

        } else {

        }
    }

    /**
     * 保存登录后得到的数据
     */
    private List<SigninResponseV2.SettingsBean> settingsBeanList;

    private void saveDatav2(SigninResponseV2 data) {
        if (StringUtils.isBlank(data)) {
            return;
        }
        if (data.isOnly_has_username()) {
//            SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
//            spUtils.put(SPConstants.USER.USERNAME, data.getUsername());
//            spUtils.put(SPConstants.USER.PASSWORD, "");
//
//            //跳转到登录页面
//            Intent intent = new Intent(mActivity,
//                    LoginSecondActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
        } else {
            // ------------------保存至login_record表中-------------------
            int id = data.getUser().getId();
            long createdtime = data.getUser().getCreated_at();
            String username = data.getUser().getUsername();
            String password = data.getUser().getPassword();//被加密过了，需要解密
            String passwordencrypt = data.getUser().getPassword();//被加密过了，需要解密
            try {
                password = AESCrypt.decrypt(StaticStateUtils.key, password);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            String logintime = (System.currentTimeMillis() / 1000L) + "";
            LoginRecordResponse loginRecordResponse = new LoginRecordResponse(id, createdtime, username, password, logintime);
            LoginRecordDataSource loginRecordDataSource = new LoginRecordDataSource(new DBHelper(mActivity));
            if (!StringUtils.isBlank(loginRecordDataSource.find(id + "")) && !StringUtils.isBlank(loginRecordDataSource.find(id + "").getId())) {//更新操作
                loginRecordDataSource.update(loginRecordResponse);
                loginRecordDataSource.close();
            } else {//插入操作
                loginRecordDataSource.insert(loginRecordResponse);
                loginRecordDataSource.close();
            }
            // ------------------保存至settings表中-------------------
            SettingsDataSource settingsDataSource = new SettingsDataSource(new DBHelper(mActivity));
            String notice_content = data.getSettings().getNOTICE_CONTENT();
            String share_url = data.getSettings().getSHARE_URL();
            String android_version = data.getSettings().getANDROID_VERSION();
            String is_update = data.getSettings().getIS_UPDATE();
            String update_content = data.getSettings().getUPDATE_CONTENT();
            String share_img = data.getSettings().getSHARE_IMG();
            String UPDATE_URL = data.getSettings().getUPDATE_URL();
            if (settingsDataSource.findAll().size() > 0) {
                settingsDataSource.clear();
            }
            SigninResponseV2.SettingsBean settingsBean = new SigninResponseV2.SettingsBean(notice_content, share_url, android_version, is_update, update_content, share_img);
            settingsDataSource.insert(settingsBean);
            settingsBeanList = settingsDataSource.findAll();

            // ------------------保存至config.xml中----------------------
            SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
            sputilsconfig.put(SPConstants.CONFIG.APP_LAUNCH_ID, data.getApp_launch_id());
            sputilsconfig.put(SPConstants.CONFIG.SIGNIN_LOG_ID, data.getSignin_log_id());
            sputilsconfig.put(SPConstants.CONFIG.TELEGRAM_GROUP, data.getSettings().getTELEGRAM_GROUP());
            sputilsconfig.put(SPConstants.CONFIG.QQ_GROUP, data.getSettings().getQQ_GROUP());
            sputilsconfig.put(SPConstants.CONFIG.WX_OFFICAL_ACCOUNT, data.getSettings().getWX_OFFICAL_ACCOUNT());
            sputilsconfig.put(SPConstants.CONFIG.OFFICIAL_WEBSITE, data.getSettings().getOFFICIAL_WEBSITE());
            sputilsconfig.put(SPConstants.CONFIG.UPDATE_URL, UPDATE_URL);
            sputilsconfig.put(SPConstants.CONFIG.SHOW_NAVIGATION, data.getSettings().getSHOW_NAVIGATION());
            sputilsconfig.put(SPConstants.CONFIG.TIMEOUT, data.getSettings().getTIMEOUT());
            sputilsconfig.put(SPConstants.CONFIG.STARTPAGE_TIME, data.getSettings().getSTARTPAGE_TIME());
            sputilsconfig.put(SPConstants.CONFIG.PAY_DESC, data.getSettings().getPAY_DESC());
            sputilsconfig.put(SPConstants.CONFIG.POTATO_GROUP, data.getSettings().getPOTATO_GROUP());
            sputilsconfig.put(SPConstants.CONFIG.SINA_WEIBO, data.getSettings().getSINA_WEIBO());

            // ------------------保存至proxy.xml中----------------------
            SPUtils sp_proxy = new SPUtils(SPConstants.SP_PROXY);
            sp_proxy.put(SPConstants.PROXY.DLOG_ALLOW_SEND, data.getSettings().getDLOG_ALLOW_SEND());
            sp_proxy.put(SPConstants.PROXY.DLOG_POOL_MAX_COUNT, data.getSettings().getDLOG_POOL_MAX_COUNT());
            sp_proxy.put(SPConstants.PROXY.DLOG_POST_INTERVAL, data.getSettings().getDLOG_POST_INTERVAL());

            sp_proxy.put(SPConstants.PROXY.FLOG_ALLOW_SEND, data.getSettings().getFLOG_ALLOW_SEND());
            sp_proxy.put(SPConstants.PROXY.FLOG_POOL_MAX_COUNT, data.getSettings().getFLOG_POOL_MAX_COUNT());
            sp_proxy.put(SPConstants.PROXY.FLOG_POST_INTERVAL, data.getSettings().getFLOG_POST_INTERVAL());
            sp_proxy.put(SPConstants.PROXY.FLOG_CLEAN_INTERVAL, data.getSettings().getFLOG_CLEAN_INTERVAL());

            // ------------------保存至user.xml中----------------------
            SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
            spUtils.put(SPConstants.USER.SOCKS_SESSION_ID, data.getProxy_session_id());
//            String Proxy_session_token = data.getProxy_session_token();//被加密过，需要解密
//            try {
//                Proxy_session_token = AESCrypt.decrypt(StaticStateUtils.key, Proxy_session_token);
//            } catch (GeneralSecurityException e) {
//                e.printStackTrace();
//            }
//            spUtils.put(SPConstants.USER.SOCKS_SESSION_TOKEN, Proxy_session_token);
            SigninResponseV2.UserBean user = data.getUser();
            if (!StringUtils.isBlank(user)) {
                spUtils.put(SPConstants.USER.API_TOKEN, user.getApi_token());
                spUtils.put(SPConstants.USER.SOCKS_ACCOUNT_ID, user.getId());
                spUtils.put(SPConstants.USER.USERNAME, user.getUsername());
                spUtils.put(SPConstants.USER.PASSWORD, passwordencrypt);
                spUtils.put(SPConstants.USER.EMAIL, user.getEmail());
                spUtils.put(SPConstants.USER.WECHAT, user.getWechat());
                spUtils.put(SPConstants.USER.WEIBO, user.getWeibo());
                spUtils.put(SPConstants.USER.QQ, user.getQq());
                spUtils.put(SPConstants.USER.USED_BYTES, user.getUsed_bytes());
                spUtils.put(SPConstants.USER.MAX_BYTES, user.getMax_bytes());
                spUtils.put(SPConstants.USER.LIMIT_BYTES, user.getLimit_bytes());
                spUtils.put(SPConstants.USER.TOTAL_PAYMENT_AMOUNT, user.getTotal_payment_amount());
                spUtils.put(SPConstants.USER.CURRENT_COINS, user.getCurrent_coins());
                spUtils.put(SPConstants.USER.TOTAL_COINS, user.getTotal_coins());
                spUtils.put(SPConstants.USER.IS_CHECKIN_TODAY, user.isIs_checkin_today());
                spUtils.put(SPConstants.USER.IS_ENABLED, user.isIs_enabled());
                spUtils.put(SPConstants.USER.CREATED_AT, user.getCreated_at());
                spUtils.put(SPConstants.USER.NEW_MESSAGE, user.isNew_message());
                spUtils.put(SPConstants.USER.USERID, user.getId());
                spUtils.put(SPConstants.USER.PROMO_CODE, user.getPromo_code());
                spUtils.put(SPConstants.USER.PROMO_USERS_COUNT, user.getPromo_users_count());
                spUtils.put(SPConstants.USER.PROMO_COINS_COUNT, user.getPromo_coins_count());
                spUtils.put(SPConstants.USER.BINDED_PROMO_CODE, user.getBinded_promo_code());
                spUtils.put(SPConstants.USER.DEBUG_LOG_ENABLED, user.isDebug_log_enabled());
                spUtils.put(SPConstants.USER.DEBUG_LOG_START_AT, user.getDebug_log_start_at());
                spUtils.put(SPConstants.USER.DEBUG_LOG_END_AT, user.getDebug_log_end_at());
                spUtils.put(SPConstants.USER.DEBUG_LOG_MAX_DAYS, user.getDebug_log_max_days());
            }


            String proxy_url = sputilsconfig.getString(SPConstants.CONFIG.PROXY_URL, null);
            SPUtils historyusesp = new SPUtils(SPConstants.SP_HISTORYUSE);
            if (StringUtils.isBlank(proxy_url) && StringUtils.isBlank(historyusesp.getString(spUtils.getString(SPConstants.USER.USERNAME, "")))) {
                // 初始开启APP时，服务器默认选择第一个区域的第一条线路
                List<SigninResponseV2.NodeTypesBean> nodeTypes = data.getNode_types();
                if (nodeTypes != null && nodeTypes.size() > 0) {
                    SigninResponseV2.NodeTypesBean nodeTypesBean = nodeTypes.get(0);
                    String name = nodeTypesBean.getName();
                    SPUtils configSP = new SPUtils(SPConstants.SP_CONFIG);
                    configSP.put(SPConstants.CONFIG.PROXY_NAME, name);
                    configSP.put(SPConstants.CONFIG.PROXY_LEVEL, nodeTypesBean.getLevel());
                    configSP.put(SPConstants.CONFIG.PROXY_LEVEL_NAME, name);
                    configSP.put(SPConstants.CONFIG.PROXY_EXPENSE_COINS, nodeTypesBean.getExpense_coins());
                    configSP.put(SPConstants.CONFIG.PROXY_NODE_TYPE_ID, nodeTypesBean.getId());
                    LogUtils.i("save :PROXY_NAME =" + name);
                    //历史选择记录
                    SPUtils usersp = new SPUtils(SPConstants.SP_USER);
                    String historyuse = historyusesp.getString(usersp.getString(SPConstants.USER.USERNAME, ""), "");
                    if (StringUtils.isBlank(historyuse)) {
                        historyusesp.put(usersp.getString(SPConstants.USER.USERNAME, ""), data.getNode_types().get(0).getId() + "");
                    }
                }
            }

            // ------------------保存至server_vaild_period表中-------------------
            List<SigninResponseV2.UserNodeTypesBean> user_node_types = data.getUser_node_types();
            if (user_node_types != null && user_node_types.size() > 0) {
                ServerVaildPeriodDataSource dataSource = new ServerVaildPeriodDataSource(new DBHelper(mActivity));
                dataSource.insertList(user_node_types);
                dataSource.close();
            }

            // 更新弹窗
//            popUpdate(settingsBeanList, UPDATE_URL);
//            PopUpAD(settingsBeanList, UPDATE_URL);
            settingsDataSource.close();

            //提交客户端日志方法(客户端输出信息日志)
//            StaticStateUtils. copyFileToUploadFile(StaticStateUtils.logdir, StaticStateUtils.uploadlogdir);

            //提交客户端日志方法(用户http请求信息)
//            SPUtils sp_userhttphead = new SPUtils(SPConstants.SP_USERHTTPHEAD);
//            StaticStateUtils.toUpdateUserhttpheadData(sp_userhttphead, StaticStateUtils.userhttpheaddatasource);
        }


    }


    /**
     * desc 开始加载进度条
     *
     * @param context 上下文
     * @param msg     进度条内容
     */
    private CustomProgressDialog cProgressDialog;

    private void startProgressDialog(Context context, String msg) {
        if (cProgressDialog == null) {
            cProgressDialog = CustomProgressDialog
                    .createDialog(context, true);
            cProgressDialog.setMessage(msg);
        }
        cProgressDialog.show();
    }

    /**
     * desc 停止加载进度条
     */
    private void stopProgressDialog() {
        if (cProgressDialog != null) {
            cProgressDialog.dismiss();
            cProgressDialog = null;
        }
    }

    private String[] classname_ = {
            "连接", "购买", "个人中心"
    };

    private Object[] class_ = {
            MainActivitySecond.class, BuyDiamondActivity2.class, UserActivity.class
    };

    private int[] image_ = {
            R.mipmap.category_drama, R.mipmap.category_comedy, R.mipmap.category_action
    };


}
