package com.staryboy.staryboy.http.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.xianghui.strayboy.base.BaseActivity;
import com.xianghui.strayboy.base.BaseApplication;
import com.xianghui.strayboy.utils.NetWorkUtils;

/**
 * Created by admin on 2017/9/4.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetEvevt evevt = BaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetWorkUtils.getNetWorkState(context);
            BaseApplication.getInstance().info("BroadcastReceiver", "NetBroadcastReceiver:  " + netWorkState);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }
}
