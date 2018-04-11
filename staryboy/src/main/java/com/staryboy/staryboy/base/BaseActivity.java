package com.staryboy.staryboy.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by tiancb on 2018/4/10.
 */

public class BaseActivity extends BaseUiActivity{

    public static String BUNDLE = "bundle";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void goActivity(Class<?> cls, Boolean isFinish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void goActivity(Class<?> cls, Bundle bundle, Boolean isFinish) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
        if (isFinish) {
            finish();
        }
    }

    public void goActivity(Class<?> cls, Boolean isFinish, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public void goActivity(Class<?> cls, Bundle bundle, Boolean isFinish, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(BUNDLE, bundle);
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            finish();
        }
    }

    public void goActivity(Class<?> cls, Bundle bundle, Boolean isFinish, int requestCode, boolean new_task) {
        Intent intent = new Intent(this, cls);
        if (new_task) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(BUNDLE, bundle);
        startActivityForResult(intent, requestCode);
        if (isFinish) {
            finish();
        }
    }

}
