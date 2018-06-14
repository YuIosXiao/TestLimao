package com.first.saccelerator.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.first.saccelerator.App;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.BaseSubscriber;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.model.ApiResponse;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XQ on 2018/2/5.
 * Picasso工具类
 */
public class PicassoUtils {

    //开屏广告
    public static Picasso openPicasso;
    //弹出广告
    public static Picasso popupPicasso;

    //开屏广告图片缓存路径
    public static String openAddir = App.getApplication().getExternalFilesDir(null) + File.separator + "SAcceleratorOpenAd";
    //弹出广告图片缓存路径
    public static String popupAddir = App.getApplication().getExternalFilesDir(null) + File.separator + "SAcceleratorPopupAd";

    /**
     * 00001
     * 初始化 openPicasso
     *
     * @param mcontext
     * @return
     */
    public static Picasso initOpenAdPicasso(Context mcontext) {
        if (openPicasso == null) {
            File file = new File(openAddir);
            if (!file.exists()) {
                file.mkdirs();
            }

            long maxSize = Runtime.getRuntime().maxMemory() / 8;//设置图片缓存大小为运行时缓存的八分之一
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(new Cache(file, maxSize))
                    .build();

            openPicasso = new Picasso.Builder(mcontext)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
//            openPicasso.setIndicatorsEnabled(true);//左上角都会有一个小标记，分别是蓝色(从内存中获取，性能最佳)、绿色(从本地获取，性能一般)、红色三种颜色(从网络加载，性能最差)


            return openPicasso;
        } else {
            return openPicasso;
        }
    }

    /**
     * 00001
     * 从网络上面下载图片文件
     */


    public static void loadOpenPicFromUrl(String url, ImageView iv_picasso) {
        final SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
        openPicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .error(R.mipmap.ic_launcher) //下载出错显示的图片
//                .fit()
//                .into(iv_picasso, new Callback() {
//                    @Override
//                    public void onSuccess() {
////                LogUtils.i("loadPicFromUrl----->图片加载成功");
//                        sputilsad.put(SPConstants.AD.openad_imagesuccess, true);
//                    }
//
//                    @Override
//                    public void onError() {
////                LogUtils.i("loadPicFromUrl----->图片加载失败");
//                        sputilsad.put(SPConstants.AD.openad_imagesuccess, false);
//                    }
//                });
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        LogUtils.i("loadPicFromUrl----->图片加载成功");
                        sputilsad.put(SPConstants.AD.openad_imagesuccess, true);
                    }

                    @Override
                    public void onError() {
                        LogUtils.i("loadPicFromUrl----->图片加载失败");
                        sputilsad.put(SPConstants.AD.openad_imagesuccess, false);
                    }
                });
    }

    /**
     * 00001
     * 从本地缓存读取文件
     */
    public static void loadOpenPicFromFile(String url, ImageView iv_picasso) {
        openPicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.OFFLINE)//加载图片的时候只从本地读取，除非网络正常且本地找不到资源的情况下
//                .error(R.mipmap.ic_launcher) //下载出错显示的图片
                .fit().into(iv_picasso, new Callback() {
            @Override
            public void onSuccess() {
//                LogUtils.i("loadPicFromFile----->图片加载成功");
                SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
                sputilsad.put(SPConstants.AD.openad_playtimes, System.currentTimeMillis());
                splashscreenlog(sputilsad);
            }

            @Override
            public void onError() {
//                LogUtils.i("loadPicFromFile----->图片加载失败");
            }
        });
    }


    /**
     * 00001
     * 提交开屏广告展示成功日志
     */
    public static void splashscreenlog(SPUtils sputilsad) {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(App.getApplication());//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(App.getApplication()) + "";//app版本号, 如: 0.1

        Map<String, String> map = new HashMap<>();
        map.put("ad_id", sputilsad.getInt(SPConstants.AD.openad_id) + "");
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.splashscreenlog(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        LogUtils.i("onSuccess----->" + apiResponse.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }

    /**
     * 00001
     * 提交开屏广告点击日志
     */

    public static void splashscreenclicklog(SPUtils sputilsad) {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(App.getApplication());//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(App.getApplication()) + "";//app版本号, 如: 0.1
        Map<String, String> map = new HashMap<>();
        map.put("ad_id", sputilsad.getInt(SPConstants.AD.openad_id) + "");
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.splashscreenclicklog(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        LogUtils.i("onSuccess----->" + apiResponse.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }


    /**
     * 00002
     * 初始化 popupPicasso
     */
    public static Picasso initPopupAdPicasso(Context mcontext) {
        if (popupPicasso == null) {
            File file = new File(popupAddir);
            if (!file.exists()) {
                file.mkdirs();
            }

            long maxSize = Runtime.getRuntime().maxMemory() / 8;//设置图片缓存大小为运行时缓存的八分之一
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(new Cache(file, maxSize))
                    .build();

            popupPicasso = new Picasso.Builder(mcontext)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            return popupPicasso;
        } else {
            return popupPicasso;
        }
    }

    /**
     * 00002
     * 从网络上面下载图片文件
     */
    public static void loadPopupPicFromUrl(String url, ImageView iv_picasso) {
        final SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
        popupPicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .error(R.mipmap.ic_launcher) //下载出错显示的图片
//                .fit()
//                .into(iv_picasso, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        sputilsad.put(SPConstants.AD.popup_imagesuccess, true);
////                LogUtils.i("loadPicFromUrl----->图片加载成功");
//                    }
//
//                    @Override
//                    public void onError() {
//                        sputilsad.put(SPConstants.AD.popup_imagesuccess, false);
////                LogUtils.i("loadPicFromUrl----->图片加载失败");
//                    }
//                });
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        LogUtils.i("loadPicFromUrl----->图片加载成功");
                        sputilsad.put(SPConstants.AD.popup_imagesuccess, true);

                    }

                    @Override
                    public void onError() {
                        LogUtils.i("loadPicFromUrl----->图片加载失败");
                        sputilsad.put(SPConstants.AD.popup_imagesuccess, false);

                    }
                });
    }

    /**
     * 00002
     * 从本地缓存读取文件
     */
    public static void loadPopupPicFromFile(String url, ImageView iv_picasso) {
        popupPicasso.load(url)
                .config(Bitmap.Config.RGB_565)
//                .placeholder(R.mipmap.ic_launcher)  //设置占位图,下载图片时显示的
                .networkPolicy(NetworkPolicy.OFFLINE)//加载图片的时候只从本地读取，除非网络正常且本地找不到资源的情况下
//                .error(R.mipmap.ic_launcher) //下载出错显示的图片
                .fit().into(iv_picasso, new Callback() {
            @Override
            public void onSuccess() {
//                LogUtils.i("loadPicFromFile----->图片加载成功");
                SPUtils sputilsad = new SPUtils(SPConstants.SP_AD);
                int popup_playnumbers = sputilsad.getInt(SPConstants.AD.popup_playnumbers, 0);
                sputilsad.put(SPConstants.AD.popup_playnumbers, popup_playnumbers + 1);
                sputilsad.put(SPConstants.AD.popup_playtimes, DateUtil.getCurrentDate1());
                popuplog();
            }

            @Override
            public void onError() {
//                LogUtils.i("loadPicFromFile----->图片加载失败");
            }
        });
    }

    /**
     * 00002
     * 提交弹出广告展示成功日志
     */
    public static void popuplog() {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(App.getApplication());//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(App.getApplication()) + "";//app版本号, 如: 0.1

        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.popuplog(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        LogUtils.i("onSuccess----->" + apiResponse.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }


    /**
     * 00002
     * 提交弹出广告点击日志
     */
    public static void popupclicklog() {
        String uuid = DeviceUtils.getUUID();// 设备uuid
        String app_channel = StaticStateUtils.getChannelName(App.getApplication());//app渠道, 如: moren
        String app_version = StaticStateUtils.app_version;//app版本名, 如: jichu
        String app_version_number = AppUtils.getAppVersionName(App.getApplication()) + "";//app版本号, 如: 0.1

        Map<String, String> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("app_channel", app_channel);
        map.put("app_version", app_version);
        map.put("app_version_number", app_version_number);
        ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.popupclicklog(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        LogUtils.i("onSuccess----->" + apiResponse.isSuccess());
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.i("onFailure----->" + message + " | " + error_code);
                    }
                });
    }


}
