package com.strayboy.strayboy.views.refreshview.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.strayboy.strayboy.R;
import com.strayboy.strayboy.views.refreshview.utils.LogUtils;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //如果不想XRefreshView后台输出log，此处传入false即可
        LogUtils.enableLog(true);
    }

    public void onClick(View v) {
        Intent intent = null;
        int i = v.getId();
        if (i == R.id.bt_list) {
            intent = new Intent(this, ListViewActivity.class);

        } else if (i == R.id.bt_gridview) {
            intent = new Intent(this, GridViewActivity.class);

        } else if (i == R.id.bt_webView) {
            intent = new Intent(this, WebViewActivity.class);

        } else if (i == R.id.bt_customview) {
            intent = new Intent(this, CustomViewActivity.class);

        } else if (i == R.id.bt_recylerView) {
            intent = new Intent(this, RecyclerViewsActivity.class);

        } else if (i == R.id.bt_scrollview) {
            intent = new Intent(this, ScrollViewActivity.class);

        } else if (i == R.id.bt_headAd) {
            intent = new Intent(this, HeadAdActivity.class);

        } else if (i == R.id.bt_not_full_screen) {
            intent = new Intent(this, NotFullScreenActivity.class);

        } else if (i == R.id.bt_not_fullscreen_nofooter) {
            intent = new Intent(this, NotFullScreenWithoutFooterActivity.class);

        } else if (i == R.id.bt_emptyview) {
            intent = new Intent(this, EmptyViewActivity.class);

        } else if (i == R.id.bt_smileview) {
            intent = new Intent(this, SmileViewActivity.class);

        } else if (i == R.id.bt_rain) {//                intent = new Intent(this, RainDropActivity.class);

        } else {
        }
        if (intent != null) {
            startActivity(intent);
        }

    }
}
