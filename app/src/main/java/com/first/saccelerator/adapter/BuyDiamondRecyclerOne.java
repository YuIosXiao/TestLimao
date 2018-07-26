package com.first.saccelerator.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.first.saccelerator.R;
import com.first.saccelerator.model.Plansv2Response;
import com.first.saccelerator.view.CustomLineRecyclerView;
import com.first.saccelerator.view.TvLinearLayoutManager;

import java.util.List;

/**
 * Created by admin on 2018/6/21.
 */

public class BuyDiamondRecyclerOne extends CustomLineRecyclerView.CustomAdapter<Plansv2Response.TabsBean> {


    private TextView tv_buydiamond_description;
    private CustomLineRecyclerView clrv_buydiamond_prices;
    private TvLinearLayoutManager tvLinearLayoutManager2;
    private Context mcontext;
    private BuyDiamondRecyclerTwo adpter;

    public BuyDiamondRecyclerOne(Context context, List<Plansv2Response.TabsBean> data, TextView tv_buydiamond_description,
                                 CustomLineRecyclerView clrv_buydiamond_prices) {
        super(context, data);
        this.mcontext = context;
        this.tv_buydiamond_description = tv_buydiamond_description;
        this.clrv_buydiamond_prices = clrv_buydiamond_prices;
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
        tv_buydiamond_description.setText(mData.get(position).getDescription());
        adpter = new BuyDiamondRecyclerTwo(mContext, mData.get(position).getPlans());
        tvLinearLayoutManager2 = new TvLinearLayoutManager(mcontext);
        tvLinearLayoutManager2.setAutoMeasureEnabled(false);
        tvLinearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        clrv_buydiamond_prices.setLayoutManager(tvLinearLayoutManager2);
        clrv_buydiamond_prices.setAdapter(adpter);
        adpter.setOnItemClickListener(new LineOnItemClickListener());
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


    /**
     * RecyclerView点击事件
     */
    private class LineOnItemClickListener implements BuyDiamondRecyclerOne.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(mcontext, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toast.makeText(mcontext, "Line_click:" + position, Toast.LENGTH_SHORT).show();
        }
    }


}
