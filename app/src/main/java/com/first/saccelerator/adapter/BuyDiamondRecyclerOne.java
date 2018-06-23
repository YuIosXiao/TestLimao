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
 * Created by admin on 2018/6/21.
 */

public class BuyDiamondRecyclerOne extends CustomLineRecyclerView.CustomAdapter<Plansv2Response.TabsBean> {


    public BuyDiamondRecyclerOne(Context context, List<Plansv2Response.TabsBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new BuyDiamondViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.adapter_buydiamondrecyclerone;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        BuyDiamondViewHolder holder = (BuyDiamondViewHolder) viewHolder;
        holder.tv_focus.setText(mData.get(position).getName());
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

    public class BuyDiamondViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_focus;

        public BuyDiamondViewHolder(View itemView) {
            super(itemView);
            tv_focus = (TextView) itemView.findViewById(R.id.tv_focus);
        }
    }

}
