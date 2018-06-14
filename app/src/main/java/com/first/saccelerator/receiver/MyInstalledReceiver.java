package com.first.saccelerator.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by XQ on 2018/5/28.
 * 当应用卸载或者安装的时候监听
 */

public class MyInstalledReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {//安装
//            String packageName = intent.getDataString();
//            LogUtils.i("----->安装了 :" + packageName);
            new AppInfoTask().executeOnExecutor(Executors.newCachedThreadPool(), context);
        }
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {//卸载
//            String packageName = intent.getDataString();
//            LogUtils.i("----->卸载 :" + packageName);
            new AppInfoTask().executeOnExecutor(Executors.newCachedThreadPool(), context);
        }
    }

    /**
     * 当有应用安装或者卸载的时候，调用此方法
     */
    public static class AppInfoTask extends AsyncTask<Context, Void, List<AppUtils.AppInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<AppUtils.AppInfo> doInBackground(Context... contexts) {
            List<AppUtils.AppInfo> appinfolist = AppUtils.getAppsInfo(contexts[0]);
            return appinfolist;
        }

        @Override
        protected void onPostExecute(List<AppUtils.AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            if (!StringUtils.isBlank(StaticStateUtils.appinfolist) && StaticStateUtils.appinfolist.size() > 0) {
                StaticStateUtils.appinfolist.clear();
                StaticStateUtils.appinfolist = appInfos;
            } else {
                StaticStateUtils.appinfolist = appInfos;
            }
        }
    }
}
