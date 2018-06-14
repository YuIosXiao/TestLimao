package com.first.saccelerator.http;


import android.content.Context;

import com.first.saccelerator.utils.StaticStateUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by XQ on 2017/11/27.
 * IP轮询请求  回调接口
 */
public abstract class CallbackWithRetry<T> implements Callback<T> {


    private final Call<T> mcall;
    private Context mContext;

    protected CallbackWithRetry(Call<T> call, Context context) {
        this.mcall = call;
        this.mContext = context;
    }


    @Override
    public void onFailure(final Call<T> call, Throwable t) {


        if (t instanceof java.net.SocketTimeoutException) {
            if (StaticStateUtils.retryCount++ < StaticStateUtils.TOTAL_RETRIES) {
                retry(call);
            } else {
                //LogUtils.i("我要开始切换ip了----->");
                StaticStateUtils.retryCount = 0;
                StaticStateUtils.changeIp(mContext);
            }
        } else {
            //延迟2秒后执行
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    if (StaticStateUtils.retryCount++ < StaticStateUtils.TOTAL_RETRIES) {
                        retry(call);
                    } else {
                        //LogUtils.i("我要开始切换ip了----->");
                        StaticStateUtils.retryCount = 0;
                        StaticStateUtils.changeIp(mContext);
                    }
                }
            }, 2, TimeUnit.SECONDS);
        }
    }

    private void retry(Call<T> call) {
        call.clone().enqueue(this);
    }
}
