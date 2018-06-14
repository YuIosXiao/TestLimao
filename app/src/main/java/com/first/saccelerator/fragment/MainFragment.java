package com.first.saccelerator.fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.util.DisplayMetrics;

import com.first.saccelerator.R;
import com.first.saccelerator.model.FunctionModel;
import com.first.saccelerator.model.IconHeaderItem;
import com.first.saccelerator.presenter.FunctionCardPresenter;
import com.first.saccelerator.presenter.HeaderItemPresenter;
import com.first.saccelerator.utils.LogUtils;

import java.util.List;

/**
 * Created by XQ on 2018/6/11.
 * TV首页的Fragment
 */

public class MainFragment extends BrowseFragment {

    private BackgroundManager mBackgroundManager;
    private DisplayMetrics mMetrics;
    private Drawable mDefaultBackground;
    private ArrayObjectAdapter rowsAdapter;
    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        buildRowsAdapter();
    }

    /**
     * 准备背景管理器
     */
    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(mActivity);
        mBackgroundManager.attach(mActivity.getWindow());
        mDefaultBackground = getResources().getDrawable(R.mipmap.main_center_bg);
        mMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
        mBackgroundManager.setDrawable(mDefaultBackground);
    }

    /**
     * 设置UI元素
     */
    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));//设置标题
//        setBadgeDrawable(getActivity().getResources().getDrawable(
//                R.drawable.title_android_tv));//展示在标题栏上的图片(图片会隐藏标题)
        setHeadersState(HEADERS_DISABLED);//设置侧边栏显示状态 enabled 可见
        setHeadersTransitionOnBackEnabled(true);//暂不知道方法具体作用
        setBrandColor(getResources().getColor(R.color.appgreen1));//设置快速通道（侧边栏）背景
        setSearchAffordanceColor(getResources().getColor(R.color.crowded_yellow)); //搜索图标颜色
        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new HeaderItemPresenter();
            }
        });
    }

    /**
     * 创建适配器并加载数据
     */
    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        addFunctionRow();
        setAdapter(rowsAdapter);
    }

    private void addFunctionRow() {
        String headerName = getResources().getString(R.string.app_header_function_name);
        IconHeaderItem gridItemPresenterHeader = new IconHeaderItem(0, headerName, R.mipmap.main_function);
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new FunctionCardPresenter());
        List<FunctionModel> functionModels = FunctionModel.getFunctionList(mActivity);
        int cardCount = functionModels.size();
        for (int i = 0; i < cardCount; i++) {
            listRowAdapter.add(functionModels.get(i));
        }
//        HeaderItem header = new HeaderItem(0, headerName);
        rowsAdapter.add(new ListRow(gridItemPresenterHeader, listRowAdapter));
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("----->onResume");
        mBackgroundManager.setDrawable(mDefaultBackground);
    }


}
