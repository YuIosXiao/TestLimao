package com.first.saccelerator.activity;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.first.saccelerator.App;
import com.first.saccelerator.R;
import com.first.saccelerator.adapter.UserRecyclerAdapter;
import com.first.saccelerator.base.BaseActivity;
import com.first.saccelerator.model.UserCenterResponse;
import com.first.saccelerator.view.CustomLineRecyclerView;
import com.first.saccelerator.view.TvLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQ on 2018/7/26.
 * 个人中心页面
 */

public class UserActivity extends BaseActivity {

    private Activity mActivity;
    private TextView tv_include;//标题
    private CustomLineRecyclerView clrv_user_item;//各个功能的item
    private UserRecyclerAdapter userrecycleradapter;//适配器
    private TvLinearLayoutManager tvlinearlayoutmanager;
    private List<UserCenterResponse> usercenterresponselist;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user2;
    }

    @Override
    protected void initView() {
        mActivity = UserActivity.this;
        tv_include = findViewById(R.id.tv_include);
        clrv_user_item = findViewById(R.id.clrv_user_item);

    }

    @Override
    protected void initData() {
        tv_include.setText(App.getApplication().getString(R.string.tv_user_title));
        usercenterresponselist = new ArrayList<>();
        if (classname_.length > 0) {
            for (int i = 0; i < classname_.length; i++) {
                UserCenterResponse response = new UserCenterResponse();
                response.setName(classname_[i]);
                response.setImageid(image_[i]);
                response.setClassname(class_[i]);
                usercenterresponselist.add(response);
            }
        }

        //初始化TvLinearLayoutManager
        tvlinearlayoutmanager = new TvLinearLayoutManager(mActivity);
        tvlinearlayoutmanager.setAutoMeasureEnabled(false);
        tvlinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //装载初始化TvLinearLayoutManager
        clrv_user_item.setLayoutManager(tvlinearlayoutmanager);
        //初始化，设置adapter
        userrecycleradapter = new UserRecyclerAdapter(mActivity, usercenterresponselist);
        clrv_user_item.setAdapter(userrecycleradapter);
        //设置监听
        userrecycleradapter.setOnItemClickListener(new useritemOnItemClickListener());
    }

    @Override
    protected void initListener() {

    }

    private String[] classname_ = {
            App.getApplication().getString(R.string.tv_user_account), App.getApplication().getString(R.string.tv_user_changepassword)
    };
    private Object[] class_ = {
            BuyDiamondActivity2.class, BuyDiamondActivity2.class
    };
    private int[] image_ = {
            R.mipmap.user_account_two, R.mipmap.user_password_two
    };

    public class useritemOnItemClickListener implements UserRecyclerAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mActivity, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(mActivity, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }
    }


}
