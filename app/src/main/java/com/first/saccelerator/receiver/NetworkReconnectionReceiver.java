package com.first.saccelerator.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by XQ on 2017/11/1.
 * 网络重连后发送广播
 */
public class NetworkReconnectionReceiver {
    private static NetworkReconnectionReceiver networkReconnectionReceiver = new NetworkReconnectionReceiver();

    public static NetworkReconnectionReceiver getInstance() {
        return networkReconnectionReceiver;
    }

    //注册广播接收者
    public void registerReceiver(Activity activity,
                                 BroadcastReceiver receiver, IntentFilter filter) {
        activity.registerReceiver(receiver, filter);
    }

    //注销广播接收者
    public void unregisterReceiver(Activity activity,
                                   BroadcastReceiver receiver) {
        activity.unregisterReceiver(receiver);
    }

    //发送广播
    public void sendBroadCast(Activity activity, Intent intent) {
        activity.sendBroadcast(intent);
    }
}
