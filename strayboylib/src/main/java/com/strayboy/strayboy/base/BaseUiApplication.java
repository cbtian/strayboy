package com.strayboy.strayboy.base;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.xianghui.strayboy.http.net.CommonModel;
import com.xianghui.strayboy.views.MyToast;

import java.util.ArrayList;

/**
 * Created by tiancb on 2018/4/10.
 */

public class BaseUiApplication extends Application {
    public static final String TAG = "BaseUiApplication";
    public static boolean SHOW_LOG = true;
    public static boolean isNotLogShow = true;
    public ArrayList<Activity> activityList = new ArrayList<>();

    private MyToast toast = null;

    public void showLongToast(String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        if (toast == null) {
            toast = MyToast.makeText(this, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public void showShortToast(String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        if (toast == null) {
            toast = MyToast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    /**
     * 判断是否是开发者的账号  为了方便查询生产环境日志
     *
     * @return
     */
    public boolean isDev() {
        if (!isNotLogShow) {
            CommonModel.ISBUGLYDEBUG = true;
            SHOW_LOG = true;
        } else {
            CommonModel.ISBUGLYDEBUG = false;
            SHOW_LOG = false;
        }

        return SHOW_LOG;
    }

    // logger
    public void error(String tag, String message, Throwable t) {
        if (isDev()) {
            Log.e(tag, String.format("error: %s ; message %s",
                    t == null ? "null" : t.getClass().getName(), message));
        }
    }

    public void warning(String tag, String message) {
        if (isDev()) {
            Log.w(tag, message);
        }
    }

    public void debug(String tag, String message) {
        if (isDev()) {
            Log.i(tag, message);
        }
    }

    public void info(String tag, String message) {
        if (isDev()) {
            Log.i(tag, message);
        }
    }

    public void verbose(String tag, String message) {
        if (isDev()) {
            Log.v(tag, message);
        }
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }





}
