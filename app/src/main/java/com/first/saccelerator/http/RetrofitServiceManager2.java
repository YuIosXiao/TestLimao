package com.first.saccelerator.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.StaticStateUtils;
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
 * Retrofit的接口管理类
 * 测试空接口 是否能联通 请求时间调整成5秒
 * Created by XQ on 2017/3/20.
 */

public class RetrofitServiceManager2 {

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    public static final boolean isDebug = true;
    private static final int DEFAULT_CONNECT_TIME_OUT = 5; // 超时时间 15s
    private static final int DEFAULT_READ_TIME_OUT = 5;

    private OkHttpClient.Builder httpClientBuilder;

    private OkHttpClient sClient;

    private RetrofitServiceManager2() {

        httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        httpClientBuilder.connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        httpClientBuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        if (AppUtils.isAppDebug(StaticStateUtils.basisActivity)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging)
                    .addNetworkInterceptor(new StethoInterceptor());
        }
//        httpClientBuilder.addInterceptor(new OkHttpInterceptor());
    }

    private static class SingletonHolder {
        private static final RetrofitServiceManager2 INSTANCE = new RetrofitServiceManager2();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static RetrofitServiceManager2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的ApiService
     *
     * @return
     */
    public ApiService getApiService() {
        return new Retrofit.Builder()
                .baseUrl(StaticStateUtils.usebath.trim())
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
     * Okhttp拦截器，修改请求url
     * 以后IP错误重连，多次请求导致程序OOM的时候，就用该方法替换
     * Created by XQ on 2018/1/23.
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