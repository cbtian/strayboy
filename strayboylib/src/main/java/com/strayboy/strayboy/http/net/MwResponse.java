package com.strayboy.strayboy.http.net;

/**
 * Created by sherwin on 2016/12/26.
 */

public abstract class MwResponse extends CommonModel {

    public abstract String getUrl();

    public static String defaultMsg = "网络不给力";

    public boolean Ret;
    public String Msg;

}
