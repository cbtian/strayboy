package com.strayboy.strayboy.base;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.strayboy.strayboy.http.interfaces.ModelCallBack;
import com.strayboy.strayboy.http.net.ModelRequest;
import com.strayboy.strayboy.http.net.MwResponse;

import java.util.HashMap;

/**
 * 封装接口逻辑类
 * Created by tiancb on 2018/4/17.
 */

public class BaseLogic {
    public BaseApplication app;
    public BaseLogic(){
        app = BaseApplication.getInstance();
    }

    /**
     * 封装请求类
     * @param params
     * @param model
     * @param tClass
     * @param modelCallBack
     * @param <T>
     */
    public <T extends MwResponse> void getData(HashMap<String, String> params,
                                               T model, Class<T> tClass, final ModelCallBack modelCallBack){
        ModelRequest<T> request =
                new ModelRequest<>(Request.Method.POST, model.getUrl(),
                        new Response.Listener<T>() {
                            @Override
                            public void onResponse(T response) {
                                if (response != null && response.Ret) {
                                    if (modelCallBack != null){
                                        modelCallBack.success(response);
                                    }
                                } else {
                                    if (modelCallBack != null) {
                                        modelCallBack.error(response.Ret,response.Msg);
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (modelCallBack != null) {
                                    modelCallBack.error(false, MwResponse.defaultMsg);
                                }
                            }
                        },tClass

                );
        request.setPostParams(params);
        app.addToRequestQueue(request);
    }
}
