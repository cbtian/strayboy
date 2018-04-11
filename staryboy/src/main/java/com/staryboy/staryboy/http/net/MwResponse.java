package com.staryboy.staryboy.http.net;

/**
 * Created by sherwin on 2016/12/26.
 */

public abstract class MwResponse extends CommonModel {

    public static final int STATUS_SUCCESS = 0;
    public static final String PARAM_USER_ID = "userid";
    public static final int NETWORKERROR = 10000;
    public int errcode = -1;
    public String errmsg;

    public MwResponse() {
        keysArray.add("status");
        keysArray.add("msg");
    }



    public static String getNetTips(int status) {
        return getNetTips("", status);
    }

    /**
     * 根据后台状态码进行提示文字信息
     *
     * @return
     */
    public static String getNetTips(String errorMsg, int status) {
        String tips = errorMsg;
        switch (status) {
            /**
             * 系统相关
             */
            case 10000:
//                tips = BaseApplication.getInstance().getString(R.string.error_code_10000);
                break;

        }
        return tips;
    }

    public boolean isResponseSuccess() {
        return errcode == STATUS_SUCCESS;
    }

}
