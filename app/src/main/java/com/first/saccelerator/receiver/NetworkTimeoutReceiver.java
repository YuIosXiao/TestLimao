package com.first.saccelerator.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.first.saccelerator.dialog.NetworkTimeoutDialog;


/**
 * Created by XQ on 2017/11/1.
 * 网络超时，ip轮询请求广播
 */
public class NetworkTimeoutReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String msg = intent.getStringExtra("msg");
        NetworkTimeoutDialog.tv_loadingmsg.setText(msg);
    }
}
