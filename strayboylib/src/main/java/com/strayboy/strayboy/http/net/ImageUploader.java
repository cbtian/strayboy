package com.strayboy.strayboy.http.net;//package com.xianghui.strayboy.http.net;
//
//import android.graphics.Bitmap;
//import android.os.Environment;
//import android.util.Log;
//
//import com.alibaba.sdk.android.oss.*;
//import com.alibaba.sdk.android.oss.common.OSSLog;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
//import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//import com.alibaba.sdk.android.oss.model.PutObjectResult;
//import com.google.gson.Gson;
//import com.mw.mwapp.MwApplication;
//import com.mw.mwapp.ui.base.bean.UploadImageModel;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by sherwin on 2017/1/17.
// */
//
//public class ImageUploader {
//
//    public static final int AVATARIMAGE = 1;
//    public static final int OCRIMAGE = 2;
//    public static final int TASKIMAGE = 3;
//    public static final int POSTIMAGE = 4;
//    public String TAG = "ImageUploader";
//    private OSS oss;
//    private String IMAGEHOST = "http://suiyishenghuo.com/";
//    private MwApplication app = MwApplication.getInstance();
//    private Gson gson = new Gson();
//
//    private ImageUploader() {
////        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIPWDvibOMOUti", "wIplQoh0MXFGZvt65yDyeuzdLW8Hw1");
////        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("LTAIPWDvibOMOUti", "wIplQoh0MXFGZvt65yDyeuzdLW8Hw1", "<StsToken.SecurityToken>");
//        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIpKXLF4FlLTkF", "wtGlgu4RrclfyALLyRE1TbIj5rVIxI");
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog();
//        oss = new OSSClient(MwApplication.getContext(), "http://oss-cn-shenzhen.aliyuncs.com", credentialProvider, conf);
//
//
//    }
//
//    public static final ImageUploader getInstance() {
//        return new ImageUploader();
//    }
//
//    public static File byte2File(byte[] buf, String filePath, String fileName) {
//        BufferedOutputStream bos = null;
//        FileOutputStream fos = null;
//        File file = null;
//        try {
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                File dir = new File(filePath);
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                if (!dir.exists()) {
//                    dir.createNewFile();
//                }
//                file = new File(filePath + File.separator + fileName);
//                fos = new FileOutputStream(file);
//                bos = new BufferedOutputStream(fos);
//                bos.write(buf);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (bos != null) {
//                try {
//                    bos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return file;
//    }
//
//    private UploadImageModel uploadImage(String userid, Bitmap bitmap, int flag, String filename) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        try {
//            byte[] byteArray = stream.toByteArray();
//            if (flag == OCRIMAGE) {
//                HttpEntity entity = MultipartEntityBuilder
//                        .create()
//                        .addTextBody("userid", userid)
//                        .addBinaryBody("data", byteArray, ContentType.create("application/octet-stream"), filename)
//                        .build();
//
//                HttpPost httpPost = new HttpPost(UploadImageModel.URL);
//                httpPost.setEntity(entity);
//                HttpResponse response = null;
//                app.debug(TAG, "uploadImage: arguments prepared");
//                HttpClient httpClient = new DefaultHttpClient();
//                try {
//                    response = httpClient.execute(httpPost);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                HttpEntity result = response.getEntity();
//                app.debug(TAG, "uploadImage: result output");
//
//                String rst = null;
//                try {
//                    InputStream is = result.getContent();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
//                    byte[] buf = new byte[128];
//                    int len = 0;
//                    while ((len = is.read(buf)) > 0) {
//                        baos.write(buf, 0, len);
//                    }
//                    rst = new String(baos.toByteArray());
//                    app.debug(TAG, "uploadImage: " + rst);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (rst == null) {
//                    return null;
//                } else {
//                    return gson.fromJson(rst, UploadImageModel.class);
//                }
//            } else {
////            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "image/";
//                String objectKey = "";
////            File file = byte2File(byteArray,path,objectKey);
////            new PutObjectSamples(oss, "bucket-oss-suiyi","avatar/"+objectKey , file.getAbsolutePath()).asyncPutObjectFromLocalFile();
//
//                if (flag == TASKIMAGE) {
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
//                    Date date = new Date();
//                    objectKey = "task/" + userid + "/" + simpleDateFormat.format(date) + "/" + System.currentTimeMillis() + ".png";
//                } if (flag == POSTIMAGE){
//                    objectKey = "spitslot/" + userid + "/" + System.currentTimeMillis() + ".png";
//                }else {
//                    objectKey = "user/" + userid + "/avatar/" + System.currentTimeMillis() + ".png";
//                }
////            new Random().nextBytes(byteArray);
//
//                // 构造上传请求
//                PutObjectRequest put = new PutObjectRequest("bucket-oss-suiyi", objectKey, byteArray);
//                PutObjectResult putResult = null;
//                try {
//                    putResult = oss.putObject(put);
//
//                    Log.d("PutObject", "UploadSuccess");
//
//                    Log.d("ETag", putResult.getETag());
//                    Log.d("RequestId", putResult.getRequestId());
//                } catch (ClientException e) {
//                    // 本地异常如网络异常等
//                    e.printStackTrace();
//                } catch (ServiceException e) {
//                    // 服务异常
//                    Log.e("RequestId", e.getRequestId());
//                    Log.e("ErrorCode", e.getErrorCode());
//                    Log.e("HostId", e.getHostId());
//                    Log.e("RawMessage", e.getRawMessage());
//                }
//                if (putResult == null) {
//                    return null;
//                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("errcode", "0");
//                        jsonObject.put("errmsg", "");
//                        JSONObject result = new JSONObject();
//                        JSONObject content = new JSONObject();
//                        content.put("url", IMAGEHOST + objectKey);
//                        result.put("content", content);
//                        result.put("extra", "");
//                        result.put("items", new JSONArray());
//                        jsonObject.put("result", result);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    return gson.fromJson(jsonObject.toString(), UploadImageModel.class);
//                }
//
//
//            }
//        }catch (OutOfMemoryError error){
//            error.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public void upload(String userid, OnImageUploadedCallback callback, int flag, Bitmap... images) {
//        CountDownLatch latch = new CountDownLatch(images.length);
//        ExecutorService executor = Executors.newCachedThreadPool();
//        for (int i = 0; i < images.length; i++) {
//            Bitmap bitmap = images[i];
//            UploadRunnable uploadRunnable = new UploadRunnable(latch, userid, callback, bitmap, flag);
//            if (i == 0) {
//                uploadRunnable.setFileName("front");
//            }
//            if (i == 1) {
//                uploadRunnable.setFileName("reverse");
//            }
//            executor.execute(uploadRunnable);
//        }
//        executor.execute(new NoticeRunnable(latch, callback));
//    }
//
//    public interface OnImageUploadedCallback {
//        void onImageUploaded(Bitmap image, boolean success, String info, String path);
//
//        void onAllUploadFinished();
//    }
//
//    private class UploadRunnable implements Runnable {
//        private Bitmap bitmap = null;
//        private String userid = null;
//        private int flag;
//        private String fileName = "";
//        private CountDownLatch latch = null;
//        private OnImageUploadedCallback callback = null;
//
//        public UploadRunnable(CountDownLatch latch, String userid, OnImageUploadedCallback callback,
//                              Bitmap bitmap, int flag) {
//            this.latch = latch;
//            this.userid = userid;
//            this.bitmap = bitmap;
//            this.callback = callback;
//            this.flag = flag;
//        }
//
//        public void setFileName(String filename) {
//            fileName = filename;
//        }
//
//        @Override
//        public void run() {
//            UploadImageModel rst = null;
//            try {
//                rst = uploadImage(userid, bitmap, flag, fileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (callback != null) {
//                if (rst != null && rst.result != null && rst.result.content != null && rst.result.content.url != null) {
//                    callback.onImageUploaded(bitmap, rst.isResponseSuccess(), rst.errmsg, rst.result.content.url);
//                } else {
//                    callback.onImageUploaded(bitmap, false, "rst is null", null);
//                }
//            }
//
//            latch.countDown();
//        }
//    }
//
//    private class NoticeRunnable implements Runnable {
//        private CountDownLatch latch = null;
//        private OnImageUploadedCallback callback = null;
//
//        public NoticeRunnable(CountDownLatch latch, OnImageUploadedCallback callback) {
//            this.latch = latch;
//            this.callback = callback;
//        }
//
//        @Override
//        public void run() {
//            try {
//                latch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (callback != null) {
//                callback.onAllUploadFinished();
//            }
//        }
//    }
//}
