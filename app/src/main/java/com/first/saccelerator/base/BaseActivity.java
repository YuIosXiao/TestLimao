package com.first.saccelerator.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.first.saccelerator.utils.LocalManageUtil;
import com.first.saccelerator.utils.StaticStateUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类
 * Created by Z on 2017/3/17.
 */

public abstract class BaseActivity extends Activity {

    private Unbinder unbind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 禁止横竖屏切换
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 去掉系统默认的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 初始化视图之前操作
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        // 通过注解绑定控件
        unbind = ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //把整个Layout顶上去露出获得焦点的EditText,不压缩多余空间
        init(savedInstanceState);
        StaticStateUtils.basisActivity = BaseActivity.this;

        initView();
        initData();
        initListener();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        LocalManageUtil localmanageutil = new LocalManageUtil(newBase);
        localmanageutil.saveSystemCurrentLanguage(newBase);
        super.attachBaseContext(localmanageutil.setLocal(newBase));
    }

    /**
     * 在设置视图内容之前
     * 需要做什么操作可以写在该方法中
     */
    protected void onBeforeSetContentLayout() {
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StaticStateUtils.isActive) {
            StaticStateUtils.isActive = true;

//            SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
//            long openad_overtime = sputilsad.getLong(SPConstants.AD.openad_overtime, 0);
//            long openad_playtimes = sputilsad.getLong(SPConstants.AD.openad_playtimes, 0);
//            boolean openad_enabled = sputilsad.getBoolean(SPConstants.AD.openad_enabled, true);
//            if (!(0 == openad_playtimes)) {
//                long timedifference = System.currentTimeMillis() - openad_playtimes;
//                if (openad_enabled && (Long.parseLong(ConvertUtils.Mymillis2FitTimeSpan(timedifference)) > openad_overtime)) {//时间差超过开屏广告间隔时间(秒)
//                    Intent intent = new Intent(BaseActivity.this,
//                            OpenScreenAdActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }
//            }
//            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//            ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
//            String shortClassName = info.topActivity.getShortClassName();    //类名
//            LogUtils.i("BaseActivity----->" + shortClassName);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!StaticStateUtils.isForeground(this)) {
            StaticStateUtils.isActive = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName();    //类名
//        LogUtils.i("onDestroy--BaseActivity--->" + shortClassName);
        if (shortClassName.contains("Launcher")) {
            finish();
            System.exit(0);
        }
    }

//    @Override
//    public void onTrimMemory(int level) {
//        super.onTrimMemory(level);
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
//        String shortClassName = info.topActivity.getShortClassName();    //类名
//        LogUtils.i("shortClassName--level----->" + shortClassName + "=====" + level);
//    }

    /**
     * 视图XML id
     * ** 必须要重写 **
     *
     * @return 视图XML id
     */
    protected abstract int getLayoutId();

    protected void init(Bundle savedInstanceState) {
    }

    /**
     * 加载UI控件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initData();

    /**
     * 加载监听
     */
    protected abstract void initListener();


}