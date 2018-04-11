package com.strayboy.strayboy;

import android.graphics.Bitmap;

public interface BitmapCallBack {
    void success(Bitmap data);
    void error(String errormsg);
}