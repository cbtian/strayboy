package com.staryboy.staryboy.base;

import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xianghui.strayboy.R;
import com.xianghui.strayboy.http.net.NetEvevt;
import com.xianghui.strayboy.utils.StatusBarUtil;
import com.xianghui.strayboy.views.MdStyleProgress;

/**
 * Created by tiancb on 2018/4/10.
 */

public class BaseUiActivity extends AppCompatActivity implements NetEvevt{

    public static final int LOADING_WAIT_TIME = 2500;
    public static final int LOADING_WAIT_TIME_SHORT = 1500;
    private static final int LOADING_MIN_DURATION = 2000;

    public static NetEvevt evevt;
    public Dialog loadingDialog;
    public TextView textView;
    public MdStyleProgress mdStyleProgress;
    private long loadingStartTime = 0;
    private boolean showingWhenInvisiable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        evevt = this;
        loadingDialog = new Dialog(this, R.style.dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading_layout, null);
        mdStyleProgress = view.findViewById(R.id.progress);
        textView =  view.findViewById(R.id.tipTextView);
        loadingDialog.setContentView(view);
        loadingDialog.setCancelable(false);
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    public void onNetChange(int netMobile) {

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (loadingDialog.isShowing()) {
            showingWhenInvisiable = true;
            loadingDialog.dismiss();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (showingWhenInvisiable) {
            loadingDialog.show();
            showingWhenInvisiable = false;
        }
    }
    public void showLoadingDialog() {
        try {
            loadingStartTime = System.currentTimeMillis();
            if (!isFinishing()) {
                loadingDialog.show();
            }
            if (textView != null) {
                textView.setText("加载中...");
            }
            mdStyleProgress.setStatus(MdStyleProgress.Status.Loading);
        } catch (Exception e) {

        }
    }


    public void showLoadingDialogSuccess(String tipsSuccess) {
        if (mdStyleProgress.getStatus() != MdStyleProgress.Status.LoadSuccess) {
            textView.setText(tipsSuccess);
            mdStyleProgress.setStatus(MdStyleProgress.Status.LoadSuccess);
            mdStyleProgress.startAnima();
        }
    }

    public void showLoadingDialogFailed(String tipsFail) {
        if (mdStyleProgress.getStatus() != MdStyleProgress.Status.LoadFail) {
            textView.setText(tipsFail);
            mdStyleProgress.setVisibility(View.VISIBLE);
            mdStyleProgress.setStatus(MdStyleProgress.Status.LoadFail);
            mdStyleProgress.failAnima();
        }
    }


    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }

    public void dismissLoadingDialog(Runnable finishOperation) {
        dismissLoadingDialog(LOADING_WAIT_TIME_SHORT, finishOperation);
    }

    public void dismissLoadingDialog(final int minWaitTime, final Runnable finishOperation) {
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... params) {
                int realWaitTime = minWaitTime;
                if (realWaitTime <= 0) {
                    realWaitTime = LOADING_MIN_DURATION;
                }
                if (System.currentTimeMillis() - loadingStartTime < realWaitTime) {
                    try {
                        Thread.sleep(realWaitTime -
                                (System.currentTimeMillis() - loadingStartTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                    if (finishOperation != null) {
//                    if (app.getBooleanFromSp(MwApplication.KEY_IS_LOGIN)){
                        finishOperation.run();
//                    }

                    }
                }
                super.onPostExecute(integer);
            }
        }.execute(0);
    }
    public void setStatusBar(){
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.clearFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置状态栏颜色
            window.setStatusBarColor(Color.parseColor("#ffffffff"));
            StatusBarUtil.StatusBarLightMode(this);
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                //设置状态栏颜色
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(Color.parseColor("#88232323"));
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeStatusBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
//        window.setStatusBarColor(Color.parseColor("#00232323"));
            window.setStatusBarColor(color);
            StatusBarUtil.StatusBarDarkMode(this, 3);
//            StatusBarUtil.StatusBarLightMode(this);
        } else {
            if (Build.VERSION.SDK_INT > 19) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                Window window = getWindow();
                //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                window.setStatusBarColor(color);
            }
        }
    }
}
