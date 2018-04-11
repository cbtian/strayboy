package com.tiancb.strayboy;

import android.os.Bundle;

import com.fire.photoselector.activity.BaseActivity;
import com.tiancb.strayboy.logic.TestApi;

public class MainActivity extends BaseActivity {
    MainApplication mainApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainApplication = (MainApplication) MainApplication.getInstance();
//        TestApi.getInstance().test1();
        TestApi.getInstance().test2();
    }
}
