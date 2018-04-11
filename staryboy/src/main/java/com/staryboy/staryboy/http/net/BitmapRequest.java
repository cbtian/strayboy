package com.staryboy.staryboy.http.net;

import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.xianghui.strayboy.base.BaseApplication;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sherwin on 2017/1/9.
 */

public class BitmapRequest<T> extends Request<T> {

    public static final String TAG = "ModelRequest";

    private static final String FILE_PART_NAME = "image";

    private BaseApplication app = BaseApplication.getInstance();
    private static Gson gson = null;
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private Listener<T> mListener;
    private Bitmap bitmap;
    protected Map<String, String> headers;
    private Map<String, String> params;
    private Class<T> clz = null;

    public BitmapRequest(String url, Listener<T> listener, ErrorListener errorListener, Bitmap bitmap, Class<T> clz) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        this.bitmap = bitmap;
        this.clz = clz;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        app.debug(TAG, "getHeaders: ");
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        return headers;
    }

    private void buildMultipartEntity() {
        for (String key : params.keySet()) {
            mBuilder.addTextBody(key, params.get(key));
            app.debug(TAG, "buildMultipartEntity: " + key + "=" + params.get(key));
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        mBuilder.addBinaryBody(FILE_PART_NAME, byteArray);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setCharset(Charset.forName(HTTP.UTF_8));

    }

    @Override
    public String getBodyContentType() {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        app.debug(TAG, "getBodyContentType: " + contentTypeHeader);
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.addTextBody("userid", "1234567");
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            app.debug(TAG, "getBody: " + e.getMessage());
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        app.debug(TAG, "getBody: " + new String(bos.toByteArray()));
        return bos.toByteArray();
    }

    @Override
    protected Map<String, String> getParams() {
        return this.params;
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

        try {
            T t = gson.fromJson(data, clz);
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new VolleyError("网络数据返回有误,无法解析"));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    public void setPostParams(Map<String, String> params) {
        this.params = params;
        buildMultipartEntity();
    }

}