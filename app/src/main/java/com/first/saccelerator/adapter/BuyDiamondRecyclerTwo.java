package com.first.saccelerator.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.model.Plansv2Response;
import com.first.saccelerator.view.CustomLineRecyclerView;

import java.util.List;

/**
 * Created by admin on 2018/6/25.
 */

public class BuyDiamondRecyclerTwo extends CustomLineRecyclerView.CustomAdapter<Plansv2Response.TabsBean.PlansBean> {


    public BuyDiamondRecyclerTwo(Context context, List<Plansv2Response.TabsBean.PlansBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new BuyDiamondTwoViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.adapter_buydiamondrecyclertwo;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        BuyDiamondTwoViewHolder holder = (BuyDiamondTwoViewHolder) viewHolder;
        holder.tv_buydiamond_description1.setText(mData.get(position).getDescription1());
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

    public class BuyDiamondTwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_buydiamond_price;
        private TextView tv_buydiamond_mark;
        private TextView tv_buydiamond_description1;
        private TextView tv_buydiamond_description2;
        private TextView tv_buydiamond_description3;
        private TextView tv_buydiamond_name;

        public BuyDiamondTwoViewHolder(View itemView) {
            super(itemView);
            tv_buydiamond_price = itemView.findViewById(R.id.tv_buydiamond_price);
            tv_buydiamond_mark = itemView.findViewById(R.id.tv_buydiamond_mark);
            tv_buydiamond_description1 = itemView.findViewById(R.id.tv_buydiamond_description1);
            tv_buydiamond_description2 = itemView.findViewById(R.id.tv_buydiamond_description2);
            tv_buydiamond_description3 = itemView.findViewById(R.id.tv_buydiamond_description3);
            tv_buydiamond_name = itemView.findViewById(R.id.tv_buydiamond_name);
        }
    }


}
