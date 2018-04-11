package com.strayboy.strayboy.base;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;

import java.io.File;

/**
 * Created by tiancb on 2018/4/10.
 */

public class BaseApplication extends com.staryboy.strayboy.base.BaseUiApplication {
    private RequestQueue requestQueue;
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static BaseApplication getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(instance);
        }
        File cacheDir = new File(this.getCacheDir(), "volley");
        DiskBasedCache cache = new DiskBasedCache(cacheDir);
//        requestQueue.start();
        requestQueue.add(new ClearCacheRequest(cache, null));
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }


}
