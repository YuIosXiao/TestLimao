package com.first.saccelerator.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.model.UserCenterResponse;
import com.first.saccelerator.view.CustomLineRecyclerView;

import java.util.List;

/**
 * Created by XQ on 2018/7/27.
 */

public class UserRecyclerAdapter extends CustomLineRecyclerView.CustomAdapter<UserCenterResponse> {


    public UserRecyclerAdapter(Context context, List<UserCenterResponse> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new UserRecyclerViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.adapter_userrecycler;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        UserRecyclerViewHolder holder = (UserRecyclerViewHolder) viewHolder;
        holder.tv_user_itemname.setText(mData.get(position).getName());
        holder.iv_user_icon.setImageDrawable(mContext.getResources().getDrawable(mData.get(position).getImageid()));
    }

    @Override
    protected void onItemFocus(View itemView, int position) {
        if (Build.VERSION.SDK_INT >= 21) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).translationZ(1).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.10f).scaleY(1.10f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {
        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).start();
        } else {
//            LogUtils.i("CustomGridRecyclerAdapter.normalStatus.scale build version < 21");
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected int getCount() {
        return mData.size();
    }


    public class UserRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_user_icon;
        private TextView tv_user_itemname;

        public UserRecyclerViewHolder(View itemView) {
            super(itemView);
            iv_user_icon = itemView.findViewById(R.id.iv_user_icon);
            tv_user_itemname = itemView.findViewById(R.id.tv_user_itemname);
        }
    }


}
