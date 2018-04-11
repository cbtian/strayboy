package com.strayboy.strayboy.views;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.staryboy.staryboy.R;


/**
 * Created by tiancb on 2017/12/23.
 */

public class MyToast {
    private Toast mToast;
    TextView textView;
    private MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_empty_toast, null);
        textView = v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
        setGravity(Gravity.CENTER,0,0);

    }

    public void setText(String  text){
        textView.setText(text);
    }
    public void setDuration(int duration){
        mToast.setDuration(duration);
    }
    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
