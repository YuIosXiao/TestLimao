package com.first.saccelerator.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by XQ on 2017/5/17.
 * AsyncHttp工具类
 */
public class MyHttpUtil {
    // 实例化对象
//    private static AsyncHttpClient aHttpClient = new AsyncHttpClient();
    private static AsyncHttpClient aHttpClient = new AsyncHttpClient(true, 80, 443);//括号里面的内容是支持https请求


    // 静态块设置连接时间
    static {
        // 设置链接超时为15秒，如果不设置，默认为10s
        aHttpClient.setTimeout(10000);
    }

    public static AsyncHttpClient getClient() {
        return aHttpClient;
    }

    /**
     * 用一个完整url获取一个string对象
     *
     * @param urlString
     * @param res
     */
    public static void get(String urlString, AsyncHttpResponseHandler res) {
        aHttpClient.get(urlString, res);
    }

    /**
     * url里面带参数
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        aHttpClient.get(urlString, params, res);
    }

    /**
     * 不带参数，获取json对象或者数组
     *
     * @param urlString
     * @param res
     */
    public static void get(String urlString, JsonHttpResponseHandler res) {
        aHttpClient.get(urlString, res);
    }

    /**
     * 带参数，获取json对象或者数组
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
        aHttpClient.get(urlString, params, res);
    }

    /**
     * 下载数据使用，会返回byte数据
     *
     * @param uString
     * @param bHandler
     */
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        aHttpClient.get(uString, bHandler);
    }

    /**
     * 用一个完整url获取一个string对象
     *
     * @param urlString
     * @param res
     */
    public static void post(String urlString, AsyncHttpResponseHandler res) {
        aHttpClient.post(urlString, res);
    }

    /**
     * url里面带参数
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
        aHttpClient.post(urlString, params, res);
    }

    /**
     * 不带参数，获取json对象或者数组
     *
     * @param urlString
     * @param res
     */
    public static void post(String urlString, JsonHttpResponseHandler res) {
        aHttpClient.post(urlString, res);
    }

    /**
     * 带参数，获取json对象或者数组
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void post(String urlString, RequestParams params,
                            JsonHttpResponseHandler res) {
        aHttpClient.post(urlString, params, res);
    }

    /**
     * 下载数据使用，会返回byte数据
     *
     * @param uString
     * @param bHandler
     */
    public static void post(String uString, BinaryHttpResponseHandler bHandler) {
        aHttpClient.post(uString, bHandler);
    }

    /**
     * 传递json数据，以后String数据为主
     */
    public static void post(Context context, String url, StringEntity stringentity, String type, AsyncHttpResponseHandler res) {
        aHttpClient.post(context, url, stringentity, type, res);
    }

    /**
     * 传递数据，以byte数据为主
     */
    public static void post(Context context, String url, ByteArrayEntity byteArrayEntity, String type, AsyncHttpResponseHandler res) {
        aHttpClient.post(context, url, byteArrayEntity, type, res);
    }

}
