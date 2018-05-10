package com.strayboy.strayboy.http.interfaces;

/**
 * 接口请求数据回调
 * Created by tiancb on 2018/5/8.
 */

public interface ModelCallBack<T> {
    public void success(T response);
    public void error(boolean ret, String message);
}
