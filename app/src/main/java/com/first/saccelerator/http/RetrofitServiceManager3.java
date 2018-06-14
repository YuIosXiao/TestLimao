package com.first.saccelerator.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by XQ on 2018/2/24.
 * Retrofit的接口管理类(备用)
 */
public class RetrofitServiceManager3 {


    public static final boolean isDebug = true;
    private static int DEFAULT_CONNECT_TIME_OUT = 10; // 超时时间 10s
    private static int DEFAULT_READ_TIME_OUT = 10;

    private OkHttpClient.Builder httpClientBuilder;

    private OkHttpClient sClient;

    private RetrofitServiceManager3() {
        SPUtils sputilsconfig = new SPUtils(SPConstants.SP_CONFIG);
        int timeout = Integer.parseInt(sputilsconfig.getString(SPConstants.CONFIG.TIMEOUT, "0"));
        if (!StringUtils.isBlank(timeout) && timeout != 0) {
            DEFAULT_CONNECT_TIME_OUT = timeout;
            DEFAULT_READ_TIME_OUT = timeout;
        }
//        ToastUtils.showLongToast("连接超时时间:" + DEFAULT_CONNECT_TIME_OUT);
        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        httpClientBuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        if (AppUtils.isAppDebug(StaticStateUtils.basisActivity)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    if (message.startsWith("<--") || message.startsWith("{")) {
                        LogUtils.i(message);
                    }
                }
            });
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder
                    .addInterceptor(logging)
                    .addInterceptor(new OkHttpInterceptor())
                    .addNetworkInterceptor(new StethoInterceptor());
        }
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager3 INSTANCE = new RetrofitServiceManager3();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager3 getInstance() {
        return new RetrofitServiceManager3();
//        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的ApiService
     *
     * @return
     */
    public ApiService getApiService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        return new Retrofit.Builder()
                .baseUrl(StaticStateUtils.usebath)
//                .client(httpClientBuilder.build())
                .client(lgnoreHttps())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiService.class);
    }


    /**
     * 忽略https验证
     */
    public OkHttpClient lgnoreHttps() {
        sClient = httpClientBuilder.build();
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier hv1 = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        String workerClassName = "okhttp3.OkHttpClient";

        try {
            Class workerClass = Class.forName(workerClassName);
            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(sClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            sslSocketFactory.set(sClient, sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sClient;
    }

    /**
     * OkHttp拦截器 改变 请求的url
     */
    public class OkHttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            String url = "";
            url = StaticStateUtils.usebath;

            //获取request
            Request request = chain.request();
            //获取request的创建者builder
            Request.Builder builder = request.newBuilder();
            HttpUrl newBaseUrl = null;
            newBaseUrl = HttpUrl.parse(url);

            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();
            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(builder.url(newFullUrl).build());
        }
    }

}
