package com.first.saccelerator.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.model.IconHeaderItem;

/**
 * Created by admin on 2018/5/4.
 * 自定义分类标题
 */

public class HeaderItemPresenter extends RowHeaderPresenter {

    private float mUnselectedAlpha;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.presenter_header_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        if (((ListRow) o).getHeaderItem() instanceof IconHeaderItem) {
            View rootView = viewHolder.view;
            IconHeaderItem iconHeaderItem = (IconHeaderItem) ((ListRow) o).getHeaderItem();
            ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
            int iconResId = iconHeaderItem.getIconResId();
            if (iconResId != IconHeaderItem.ICON_NONE) { // Show icon only when it is set.
                Drawable icon = rootView.getResources().getDrawable(iconResId);
                iconView.setImageDrawable(icon);
            }

            TextView label = (TextView) rootView.findViewById(R.id.header_label);
            label.setText(iconHeaderItem.getName());
            rootView.setFocusable(true);
            rootView.setFocusableInTouchMode(true);
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    // TODO: TEMP - remove me when leanback onCreateViewHolder no longer sets the mUnselectAlpha,AND
    // also assumes the xml inflation will return a RowHeaderView
    @Override
    protected void onSelectLevelChanged(ViewHolder holder) {
        // this is a temporary fix
        holder.view.setAlpha(mUnselectedAlpha + holder.getSelectLevel() *
                (1.0f - mUnselectedAlpha));
    }
}
