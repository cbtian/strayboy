package com.strayboy.strayboy.views.refreshview.demo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.strayboy.strayboy.R;
import com.strayboy.strayboy.views.refreshview.XRefreshView;
import com.strayboy.strayboy.views.refreshview.demo.ui.raindrop.RainDropHeader;


public class RainDropActivity extends Activity {
    private XRefreshView outView;
    private LinearLayout ll;
    private int count = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_scrollview);

        outView = (XRefreshView) findViewById(R.id.custom_view);
        ll = (LinearLayout) findViewById(R.id.ll);
        outView.setPullLoadEnable(true);
        outView.setAutoRefresh(false);
        outView.setAutoLoadMore(false);
        outView.setCustomHeaderView(new RainDropHeader(this));
        outView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh(boolean isPullDown) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        outView.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //setLoadComplete不要和stopLoadMore同时调用
                        if (count > 1) {
                            outView.setLoadComplete(true);
                        } else {
                            outView.stopLoadMore();
                        }
                        count++;
                    }
                }, 2000);
            }
        });
        for (int i = 0; i < 50; i++) {
            TextView tv = new TextView(this);
            tv.setTextIsSelectable(true);
            tv.setText("数据" + i);
            ll.addView(tv);
        }
    }

}
