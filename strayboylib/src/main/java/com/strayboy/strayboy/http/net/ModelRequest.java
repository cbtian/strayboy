package com.strayboy.strayboy.http.net;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.strayboy.strayboy.base.BaseApplication;
import com.strayboy.strayboy.utils.NetWorkUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sherwin on 2016/12/26.
 */

public class ModelRequest<T extends CommonModel> extends Request<T> {

    public static final String TAG = "ModelRequest";
    /**
     * The default socket timeout in milliseconds
     */
    public static final int DEFAULT_TIMEOUT_MS = 60 * 1000;
    /**
     * The default number of retries
     */
    public static final int DEFAULT_MAX_RETRIES = 1;
    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;
    private static final Comparator<String> comparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };
    private static Gson gson = null;
    private BaseApplication app = BaseApplication.getInstance();
    private Class<T> clz = null;
    private Response.Listener<T> listener = null;
    private Map<String, String> params = null;
    private Map<String, String> header = null;

    public ModelRequest(int method, String url, Response.Listener<T> listener,
                        Response.ErrorListener errorListener, Class<T> clz) {

        super(method, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT));
        this.clz = clz;
        this.listener = listener;
    }


    private static String parseStrToMd5L32(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public void setPostParams(Map<String, String> params) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            Response.error(new VolleyError(""));
            app.showShortToast("当前网络不可用，请检查");
//            return;
        }
//        this.params = params;

        this.params = CommonModel.createLinkMap(params);
        appendSn();
    }


    private void appendSn() {
        if (params.size() > 0) {
            ArrayList<String> keyList = new ArrayList<String>(this.params.keySet());
            Collections.sort(keyList, comparator);
//            app.debug(TAG, "appendSn: " + keyList);
            StringBuilder sb = new StringBuilder();
            for (String key : keyList) {
                sb.append(key + "=" + params.get(key) + "&");
            }
//            app.debug(TAG, "appendSn: " + sb.toString().substring(0, sb.length() - 1));
            if (header == null) {
                header = new HashMap<>();
            }
//            app.debug(TAG, "appendSn: " + sb.toString().substring(0, sb.length() - 1));
            header.put("sn", parseStrToMd5L32(sb.toString().substring(0, sb.length() - 1)));
        }

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (header == null) {
            header = new HashMap<>();
        }
        return header;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        byte[] bys = super.getBody();
//        app.debug(TAG, "getBody: normal " + new String(bys));
        return bys;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        String data = new String(response.data);
        if (gson == null) {
            gson = new Gson();
        }

        app.debug(TAG, "--------------------------------------");
        app.debug(TAG, "request: " + getUrl());
        app.debug(TAG, "param: " + getParams());
        app.debug(TAG, "parseNetworkResponse: " + data);
//        try {
//            JSONObject jsonObject = new JSONObject(data);
//            int status = jsonObject.getInt("status");
//            if (!(status == MwResponse.STATUS_SUCCESS)){
//                app.showShortToast(MwResponse.getNetTips(status));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            T t = gson.fromJson(data, clz);
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            ExceptionModel exceptionModel = gson.fromJson(data, ExceptionModel.class);
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            String str = sw.toString();
            app.info("lyq", str);
//            String tips = exceptionModel.getNetTips();
//            if (!TextUtils.isEmpty(tips)){
//                app.showShortToast(tips);
//            }

            return Response.error(new VolleyError(exceptionModel.errcode + ""));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        app.debug(TAG, "ModelRequest = " + response.getClass());
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Override
    public Map<String, String> getParams() {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        return params;
    }

    private String loadParams() {
        StringBuilder sb = new StringBuilder();
        for (HashMap.Entry<String, String> entry : getParams().entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        return sb.toString();
    }


}
