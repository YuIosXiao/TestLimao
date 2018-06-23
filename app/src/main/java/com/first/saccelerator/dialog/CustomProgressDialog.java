package com.first.saccelerator.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.first.saccelerator.R;
import com.first.saccelerator.view.SlackLoadingView;

/**
 * Created by XQ on 2017/4/10.
 * 自定义的进度条Dialog
 */
public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
    private static SlackLoadingView mLoadingView;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context, boolean cancelableflag) {
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.dialog_customprogress2);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//        customProgressDialog.setCancelable(cancelableflag);
        customProgressDialog.setCanceledOnTouchOutside(false);
        mLoadingView = (SlackLoadingView) customProgressDialog.findViewById(R.id.loading_view);
        mLoadingView.setLineLength(18 / 100f);
        mLoadingView.setDuration(15 / 100f);
        mLoadingView.start();

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }

//        ImageView imageView = (ImageView) customProgressDialog
//                .findViewById(R.id.loadingImageView);
//        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
//                .getBackground();
//        animationDrawable.start();
    }

    /**
     * [Summary] setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public CustomProgressDialog setTitile(String strTitle) {
        return customProgressDialog;
    }

    /**
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) customProgressDialog
                .findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoadingView.reset();
    }
}
