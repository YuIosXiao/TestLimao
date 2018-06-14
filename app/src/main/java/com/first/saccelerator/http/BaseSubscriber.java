package com.first.saccelerator.http;


import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.utils.NetworkUtils;
import com.first.saccelerator.utils.StaticStateUtils;

import rx.Subscriber;

/**
 * 订阅基类
 * Created by ZhengSheng on 2017/3/20.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private boolean isReconnect;

    public abstract void onSuccess(T t);

    public abstract void onFailure(String message, int error_code);


    @Override
    public void onCompleted() {
        // hide load dialog
        unsubscribe();
    }

    @Override
    public void onError(Throwable e) {
        try {
            e.printStackTrace();
            onFailure("网络繁忙:" + ExceptionHandle.handleException(e).code, 777);
            StaticStateUtils.detectDomainWhetherNormal(StaticStateUtils.basisActivity);
//            LogUtils.i("handleException--code---->" + ExceptionHandle.handleException(e).code);
//            LogUtils.i("handleException--message---->" + ExceptionHandle.handleException(e).message);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {
        ApiResponse br = (ApiResponse) t;
        if (br == null) {
            onFailure("请求失败:" + 400, 400);
            return;
        }

        int error_code = 0;
        if (br.isSuccess()) {
            onSuccess(t);
        } else {
            error_code = br.getError_code();
            if (error_code != 100) {
                onFailure(br.getMessage(), error_code);
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        if (!NetworkUtils.isConnected()) {
            // 没有网络则取消订阅
            unsubscribe();
            onFailure("手机无网络", 800);
        }
        // show load dialog
    }

}