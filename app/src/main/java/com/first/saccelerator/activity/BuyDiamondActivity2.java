package com.first.saccelerator.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.first.saccelerator.App;
import com.first.saccelerator.R;
import com.first.saccelerator.adapter.BuyDiamondRecyclerOne;
import com.first.saccelerator.base.BaseActivity;
import com.first.saccelerator.constants.SPConstants;
import com.first.saccelerator.dialog.CustomProgressDialog;
import com.first.saccelerator.http.ApiService;
import com.first.saccelerator.http.BaseSubscriber;
import com.first.saccelerator.http.RetrofitServiceManager;
import com.first.saccelerator.model.ApiResponse;
import com.first.saccelerator.model.Plansv2Response;
import com.first.saccelerator.utils.AppUtils;
import com.first.saccelerator.utils.LogUtils;
import com.first.saccelerator.utils.SPUtils;
import com.first.saccelerator.utils.StaticStateUtils;
import com.first.saccelerator.utils.ToastUtils;
import com.first.saccelerator.view.CustomLineRecyclerView;
import com.first.saccelerator.view.TvLinearLayoutManager;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2018/6/14.
 */

public class BuyDiamondActivity2 extends BaseActivity {

    private Activity mActivity;
    private TextView tv_include;
    private CustomLineRecyclerView id_recycler_line;
    private BuyDiamondRecyclerOne adapter;
    private TvLinearLayoutManager tvLinearLayoutManager;
    private TextView tv_buydiamond_rechargedescription;
    private TextView tv_buydiamond_description;//描述
    private CustomLineRecyclerView clrv_buydiamond_prices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buydiamond_two;
    }

    @Override
    protected void initView() {
        mActivity = BuyDiamondActivity2.this;
        tv_include = findViewById(R.id.tv_include);
        id_recycler_line = findViewById(R.id.id_recycler_line);
        tv_buydiamond_rechargedescription = findViewById(R.id.tv_buydiamond_rechargedescription);
        tv_buydiamond_description = findViewById(R.id.tv_buydiamond_description);
        clrv_buydiamond_prices = findViewById(R.id.clrv_buydiamond_prices);
    }

    @Override
    protected void initData() {
        tv_include.setText(App.getApplication().getResources().getString(R.string.tv_buydiamond_titlename));
        tv_include.setTextColor(App.getApplication().getResources().getColor(R.color.appwhite));

        getPlansv2();
    }

    @Override
    protected void initListener() {
        startProgressDialog(mActivity, "加载中...");
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        final String token = spUtils.getString(SPConstants.USER.API_TOKEN, "");
        Map<String, String> options = new HashMap<>();
        options.put("platform", "android");
        options.put("app_channel", StaticStateUtils.getChannelName(mActivity));
        options.put("app_version", StaticStateUtils.app_version);
        options.put("app_version_number", AppUtils.getAppVersionName(mActivity) + "");
    }


    /**
     * 获得套餐数据(认证) V2接口
     */
    public void getPlansv2() {
        startProgressDialog(mActivity, "加载中...");
        SPUtils spUtils = new SPUtils(SPConstants.SP_USER);
        final String token = spUtils.getString(SPConstants.USER.API_TOKEN, "");

        Map<String, String> options = new HashMap<>();
        options.put("platform", "android");
        options.put("app_channel", StaticStateUtils.getChannelName(mActivity));
        options.put("app_version", StaticStateUtils.app_version);
        options.put("app_version_number", AppUtils.getAppVersionName(mActivity) + "");
        final ApiService apiService = RetrofitServiceManager.getInstance().getApiService();
        apiService.plansv2(token, options)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiResponse<Plansv2Response>>() {
                    @Override
                    public void onSuccess(ApiResponse<Plansv2Response> plansv2Response) {
                        stopProgressDialog();
                        if (plansv2Response.isSuccess() && plansv2Response.getData().getTabs().size() > 0) {
                            adapter = new BuyDiamondRecyclerOne(BuyDiamondActivity2.this, plansv2Response.getData().getTabs(), tv_buydiamond_description,
                                    clrv_buydiamond_prices);
                            id_recycler_line.setItemAnimator(new DefaultItemAnimator());
                            tvLinearLayoutManager = new TvLinearLayoutManager(mActivity);
                            tvLinearLayoutManager.setAutoMeasureEnabled(false);
                            tvLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            id_recycler_line.setLayoutManager(tvLinearLayoutManager);
                            id_recycler_line.setAdapter(adapter);
                            adapter.setOnItemClickListener(new LineOnItemClickListener());


                            tv_buydiamond_rechargedescription.setText(new SPUtils(SPConstants.SP_CONFIG).getString(SPConstants.CONFIG.PAY_DESC, ""));
                        }
                    }

                    @Override
                    public void onFailure(String message, int error_code) {
                        LogUtils.e("onFailuremessage----->" + message + "|" + error_code);
                        stopProgressDialog();
                        ToastUtils.showLongToastSafe(message);
                    }
                });


    }

    /**
     * RecyclerView点击事件
     */
    private class LineOnItemClickListener implements BuyDiamondRecyclerOne.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(BuyDiamondActivity2.this, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(BuyDiamondActivity2.this, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * desc 开始加载进度条
     *
     * @param context 上下文
     * @param msg     进度条内容
     */
    private CustomProgressDialog cProgressDialog;

    private void startProgressDialog(Context context, String msg) {
        if (cProgressDialog == null) {
            cProgressDialog = CustomProgressDialog
                    .createDialog(context, true);
            cProgressDialog.setMessage(msg);
        }
        cProgressDialog.show();
    }

    /**
     * desc 停止加载进度条
     */
    private void stopProgressDialog() {
        if (cProgressDialog != null) {
            cProgressDialog.dismiss();
            cProgressDialog = null;
        }
    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(final RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mRecyclerView, mToPosition);
                }
            }
        });
    }

}
