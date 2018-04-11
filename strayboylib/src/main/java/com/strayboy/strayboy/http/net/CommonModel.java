package com.strayboy.strayboy.http.net;

import android.util.Log;

import com.strayboy.strayboy.base.BaseApplication;
import com.strayboy.strayboy.utils.MD5Utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sherwin on 2016/12/26.
 */

public abstract class CommonModel {

    public static String HOST = "";//"http://app.51suiyi.cn:16888/";//测试环境
    public static String SENSOR = "";//"http://app.51suiyi.cn:16888/";//埋点服务器
    public static String COLLECTHOST = "";//"http://collector.suiyishenghuo.com:26888/";//手机数据服务器
    public static String SHAREHOST = "";//shcedule.suiyishenghuo.com:46888/"
    public static Envment envment = Envment.Test;
    public static boolean ISBUGLYDEBUG = true;
    //        public static final String HOST = "http://app.suiyishenghuo.com:16888/";//生产环境
//    public static final String HOST = "http://115.28.25.154:16888/";//测试
//    public static final String HOST = "http://192.168.0.140:9090/";//测试
    protected ArrayList<String> keysArray = new ArrayList<>();

    public static void initEnv() {
        if (Envment.Product == envment) {
            HOST = "http://app.suiyishenghuo.com:16888/";
            BaseApplication.SHOW_LOG = false;
            ISBUGLYDEBUG = false;
            BaseApplication.isNotLogShow = true;
        } else {
            HOST = "http://app.51suiyi.cn:16888/";
            BaseApplication.SHOW_LOG = true;
            BaseApplication.isNotLogShow = false;
            ISBUGLYDEBUG = true;
        }
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */

    public static String createLinkString(Map<String, String> params) {
        String timestamp = System.currentTimeMillis() + "";
        params.put("timestamp", timestamp);
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        String tempStr = MD5Utils.encrypt(prestr);
        Log.i("CommonModel", "截取前 **** = " + tempStr);
        tempStr = tempStr.substring(9, 25);
        Log.i("CommonModel", "截取后 **** = " + tempStr);

        String result = MD5Utils.encrypt(tempStr + timestamp);
        Log.i("CommonModel", "最终加密结果 **** = " + result);
        result = result.substring(9, 25);//加密后的结果
        Log.i("CommonModel", "最终加密结果截取 **** = " + result);

        HashMap<String, String> resultParams = new HashMap<>();
        resultParams.put("timestamp", timestamp);
        resultParams.put("sign", result);
        return prestr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */

    public static Map<String, String> createLinkMap(Map<String, String> params) {
        /**
         * 添加公共参数
         */
        /************/
//        params.put(TokenModel.VERSION, MwApplication.getVersionName(MwApplication.getContext()) + "");
//        params.put(TokenModel.VENDER, "1");
        /************/

        String timestamp = System.currentTimeMillis() + "";
        params.put("timestamp", timestamp);
//        MD5RequestHelper signReqHelper = new MD5RequestHelper();
//        Log.i("CommonModel","服务器端加密"+signReqHelper.sign(params));
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if (key == null) {
                BaseApplication.getInstance().showShortToast("key传了Null");
                return new HashMap<String, String>();
            }
            String value = params.get(key);
            if (value == null) {
                value = "";
//                return new HashMap<String, String>();
                if (envment == Envment.Test) {
                    BaseApplication.getInstance().showShortToast(key + "值为Null");
                }
            }
            try {
                if (params.get(key) != null) {
                    value = URLEncoder.encode(params.get(key), "UTF-8").replace("+", "%20").replace("*", "%2A");
                    Log.i("CommonModel", "key ******************* = " + key + "，value ******************* = " + value);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (key.equals("token")) {
                Log.i("CommonModel", "token ******************* = " + value);
            }
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }


        String tempStr = MD5Utils.encrypt(prestr);
        Log.i("CommonModel", "截取前 **** = " + tempStr);
        tempStr = tempStr.substring(9, 25);
        Log.i("CommonModel", "截取后 **** = " + tempStr);

        String result = MD5Utils.encrypt(tempStr + timestamp);
        Log.i("CommonModel", "最终加密结果 **** = " + result);
        result = result.substring(9, 25);//加密后的结果
        Log.i("CommonModel", "最终加密结果截取 **** = " + result);


        HashMap<String, String> resultParams = new HashMap<>();
        resultParams.put("timestamp", timestamp);
        resultParams.put("sign", result);
        resultParams.putAll(params);
        ArrayList<String> keyList = new ArrayList<String>(resultParams.keySet());
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append(key + "=" + resultParams.get(key) + "&");
        }
        Log.i("CommonModel", "上传到服务器数据 **** = " + sb);

        return resultParams;
    }

    @Override
    public String toString() {
        Field[] fields = getClass().getFields();
        StringBuilder sb = new StringBuilder(getClass().getName() + "[");
        for (String key : keysArray) {
            for (Field f : fields) {
                if (key.equals(f.getName())) {
                    if (f.getType() == ArrayList.class) {
                        try {
                            ArrayList<?> list = (ArrayList) f.get(this);
                            sb.append(f.getName() + ": [");
                            for (Object obj : list) {
                                sb.append(obj + ",");
                            }
                            sb.append("]");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            sb.append(f.getName() + ":" + f.get(this) + ", ");
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public enum Envment {
        Product, Test
    }

}
