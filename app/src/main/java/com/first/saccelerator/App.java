package com.first.saccelerator;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.database.DBHelper;
import com.first.saccelerator.database.DynamicServersDataSource;
import com.first.saccelerator.database.UserhttpheadDataSource;
import com.first.saccelerator.model.DynamicServersResponse;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.CrashHandler;
import com.first.saccelerator.utils.LocalManageUtil;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.PicassoUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;
import com.first.saccelerator.utils.UADplus;
import com.first.saccelerator.utils.Utils;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

/**
 * Created by XQ on 2017/3/17.
 * 这是新分支用来做界面改版的。
 */

public class App extends MultiDexApplication {

    private static Application mApp;
    public static LogUtils.Builder lBuilder;

    public static Boolean isDomestic;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Utils.init(mApp);

        lBuilder = new LogUtils.Builder()
                .setLogSwitch(AppUtils.isAppDebug(mApp))// 设置log总开关，默认开
//                .setGlobalTag("CMJ")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose

        // Init Stetho
        Stetho.initializeWithDefaults(this);
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
        // 加载全部异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        LogUtils.i("App", "SHA1 : " + sHA1(this));
        //开启comsunny测试模式
//        Comsunny.setSandbox(false);
        // 初始化Comsunny
//        Comsunny.setAppIdAndSecret("9a4c76c8-0c2f-4085-a580-c6f9a443ffe1",
//                Test Mode Secret
//                "bbbf66dd-1d67-47f4-baf9-73e791877612");
//        Live Mode Secret
//                "3e305f8d-9b0b-454b-b679-7b71e48ef08b");
//        //初始化微信支付app
//        String initInfo = CSPay.initWechatPay(this, "wx54885e310e3f8890");
//        if (initInfo != null) {
//            Toast.makeText(this, "微信初始化失败：" + initInfo, Toast.LENGTH_LONG).show();
//        }
        getResources();//解决APP字体大小不随系统字体大小变化而变化


        /**
         * 初始化APP应用的时候，当dynamic_servers列表内数据不存在的时候，设置默认使用ip
         */
        DynamicServersDataSource dataSource = new DynamicServersDataSource(new DBHelper(this));
        List<DynamicServersResponse.ServersBean> list = dataSource.findAll();
        if (!StringUtils.isBlank(list) && list.size() == 0) {
            StaticStateUtils.saveIp(this);

        }
        SPUtils spUtils = new SPUtils(SPConstants.SP_DEFAULT);
        if (StringUtils.isBlank(spUtils.getString(SPConstants.DEFAULT.DEFAULTIP, ""))) {
            StaticStateUtils.usebath = StaticStateUtils.bath;
        } else {
            StaticStateUtils.usebath = spUtils.getString(SPConstants.DEFAULT.DEFAULTIP, "");
        }
        spUtils.put(SPConstants.DEFAULT.DEFAULTIP, StaticStateUtils.usebath);
        dataSource.close();

        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);

        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);

        UADplus uaDplus = new UADplus();
        uaDplus.sendMessage(this, "591bc20b4544cb6496001782");

        /**
         *初始化开屏广告的Picasso
         */
        PicassoUtils.initOpenAdPicasso(mApp);
        /**
         * 初始化弹框广告的Picasso
         */
        PicassoUtils.initPopupAdPicasso(mApp);


        /**
         * 初始化shareSDK
         */
        MobSDK.init(this, null, null);

        StaticStateUtils.userhttpheaddatasource = new UserhttpheadDataSource(new DBHelper(mApp));

        vpnPathAssignment();
    }


    @Override
    protected void attachBaseContext(Context base) {
        LocalManageUtil localmanageutil = new LocalManageUtil(base);
        localmanageutil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(localmanageutil.setLocal(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //保存系统选择语言
        LocalManageUtil localmanageutil = new LocalManageUtil(getApplicationContext());
        localmanageutil.onConfigurationChanged(getApplicationContext());
    }

    /**
     * 获取Application实例
     *
     * @return Application实例
     */
    public static Application getApplication() {
        return mApp;
    }

    /**
     * 判断是开发debug模式，还是发布release模式
     *
     * @param context
     */
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否在国内
     *
     * @return
     */
    public static boolean isDomestic() {
        isDomestic = new SPUtils(SPConstants.SP_CONFIG).getBoolean(SPConstants.CONFIG.IS_DOMESTIC, true);//默认取值是在国内
//        LogUtils.i("isDomestic----->" + isDomestic);
        return isDomestic.booleanValue();
    }

    /**
     * 解决APP字体大小不随系统字体大小变化而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    /**
     * VPN路径复制
     */
    private void vpnPathAssignment() {
        StaticStateUtils.originalFilePath_d2o = getExternalFilesDir(null) + File.separator + "android_d2o";
        StaticStateUtils.targetFilePath_d2o = getExternalFilesDir("myfolder") + File.separator + "android_d2o_customize";
        StaticStateUtils.usesFilePath_d2o = getExternalFilesDir("myfolder") + File.separator + "android_d2o_new";

        StaticStateUtils.originalFilePath_o2d = getExternalFilesDir(null) + File.separator + "android_o2d";
        StaticStateUtils.targetFilePath_o2d = getExternalFilesDir("myfolder") + File.separator + "android_o2d_customize";
        StaticStateUtils.usesFilePath_o2d = getExternalFilesDir("myfolder") + File.separator + "android_o2d_new";
    }


}
