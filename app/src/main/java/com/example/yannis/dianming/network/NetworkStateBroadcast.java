package com.example.yannis.dianming.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yannis.dianming.base.Util;

/**
 * Created by yannis on 2016/12/14.
 */

public class NetworkStateBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Util.isNetworkAvailable(context)){
            Util.showToast(context, "the network is unavailable");
        }
    }
}
