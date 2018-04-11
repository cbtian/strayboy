package com.tiancb.strayboy.logic;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.strayboy.strayboy.http.net.ModelRequest;
import com.tiancb.strayboy.bean.TestBean;

import java.util.HashMap;

/**
 * Created by tiancb on 2018/4/11.
 */

public class TestApi extends BaseLogic{
    private static TestApi instance;

    private TestApi() {
        super();
    }

    public static TestApi getInstance() {
        if (instance == null) {
            synchronized (TestApi.class) {
                instance = new TestApi();
            }
        }
        return instance;
    }
    public void test1(){
        HashMap<String, String> params = new HashMap<>();
        params.put("size", "20");
        params.put("sign", "be40419a23325");
        params.put("start", "1");
        params.put("vender", "1");
        params.put("userid", "957559780958928896");
        params.put("version", "2.1.0330");
        params.put("timestamp", "1523434708281");
        ModelRequest<TestBean> request = new ModelRequest<>(Request.Method.POST, "http://app.51suiyi.cn:16888/commentBeRepliedSpitslot", new Response.Listener<TestBean>() {
            @Override
            public void onResponse(TestBean response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },TestBean.class);
        request.setPostParams(params);
        app.addToRequestQueue(request);
    }

    public void test2(){
//
        ModelRequest<TestBean> request = new ModelRequest<>(Request.Method.GET, "http://a.app.qq.com/o/simple.jsp?pkgname=com.mw.mwapp", new Response.Listener<TestBean>() {
            @Override
            public void onResponse(TestBean response) {
                Log.i("Logic","");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Logic","");
            }
        },TestBean.class);
        app.addToRequestQueue(request);
    }

}
