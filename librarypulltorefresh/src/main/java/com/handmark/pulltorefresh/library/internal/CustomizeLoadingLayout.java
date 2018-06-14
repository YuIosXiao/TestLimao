package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;

/**
 * Created by Xq on 2016/12/6.
 */
public class CustomizeLoadingLayout extends LoadingLayout {

    private AnimationDrawable animationDrawable;


    public CustomizeLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        mHeaderImage.setImageResource(R.drawable.progress_round_rocket);
        animationDrawable = (AnimationDrawable) mHeaderImage.getDrawable();
    }

    /**
     * 默认图片
     */
    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.progress_round_rocket;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {
        // NO-OP
    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        // NO-OP
    }

    /**
     * 下拉以刷新
     */
    @Override
    protected void pullToRefreshImpl() {
        // 播放帧动画
//        animationDrawable.start();
        mHeaderView.start();
    }

    /**
     * 正在刷新时回调
     */
    @Override
    protected void refreshingImpl() {
        // 播放帧动画
//        animationDrawable.start();
        mHeaderView.reset();
    }

    /**
     * 释放以刷新
     */
    @Override
    protected void releaseToRefreshImpl() {


    }

    /**
     * 重新设置
     */
    @Override
    protected void resetImpl() {
        mHeaderImage.setVisibility(View.VISIBLE);
        mHeaderImage.clearAnimation();

    }
}
