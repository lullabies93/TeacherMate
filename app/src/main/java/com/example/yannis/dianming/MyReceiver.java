package com.example.yannis.dianming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yannis on 2017/3/2.
 */

public class MyReceiver extends BroadcastReceiver {
    public interface UpdateUIListenner {
        /**
         * 更新UI
         */
        void UpdateUI(Intent str);
    }

    UpdateUIListenner updateUIListenner;

    @Override
    public void onReceive(Context context, Intent intent) {
        updateUIListenner.UpdateUI(intent);
    }

    /**
     * 监听广播接收器的接收到的数据
     *
     * @param updateUIListenner
     */
    public void SetOnUpdateUIListenner(UpdateUIListenner updateUIListenner) {
        this.updateUIListenner = updateUIListenner;

    }
}
