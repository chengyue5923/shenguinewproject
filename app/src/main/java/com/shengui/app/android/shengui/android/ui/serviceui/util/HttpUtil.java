package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;
import com.shengui.app.android.shengui.db.UserPreference;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/15.
 * http://192.168.1.129/api/course/list.json
 * http://192.168.1.129/api/course/listtui.json
 */

public class HttpUtil {


    private static OkHttpClient okHttpClient;


    public static String getStringByOkHttp(String url, Context context) {

        String json = "";
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))
                .build();

        Request request = null;

        if (UserPreference.getTOKEN() == null) {
            request = new Request.Builder()
                    .url(url)
                    .build();
        } else {
            request = new Request.Builder()
                    .addHeader("token_id", UserPreference.getTOKEN())
                    .url(url)
                    .build();
        }

        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getOtherByOkHttp(String url, Context context) {

        String json = "";
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))
                .build();

        Request request = null;

        request = new Request.Builder()
                .url(url)
                .build();


        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getTokenStringByOkHttp(Context context, String url, String token) {
        String json = "";
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))
                .build();

        Request request = new Request.Builder()
                .addHeader("token_id", UserPreference.getTOKEN())
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();


            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;

    }

    public static String postStringByOkHttp(Context context, String url, FormBody formBody) {
        String json = "";

//     formBody = new FormBody.Builder()//创建表单构造器
//                .add("name","zhangsan")//添加表单参数
//                .add("age","20")
//                .build();//生成简易表单型RequestBody


        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .cache(new Cache(context.getExternalCacheDir(), 10 * 1024 * 1024))
                .build();

        Request request;

        request = new Request.Builder()
                .addHeader("token_id", UserPreference.getTOKEN())
                .url(url)
                .post(formBody)//使用表单formBody作为requestBody
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;


    }


    public static String postLoginByOkHttp(Context context, String url, FormBody formBody) {
        String json = "";

//     formBody = new FormBody.Builder()//创建表单构造器
//                .add("name","zhangsan")//添加表单参数
//                .add("age","20")
//                .build();//生成简易表单型RequestBody


        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)//使用表单formBody作为requestBody
                .build();


        try {
            Response response = okHttpClient.newCall(request).execute();


            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;


    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType VIDE0_TYPE_MP4 = MediaType.parse("video/mp4");
    private static final MediaType AUDIO_TYPE_AMR = MediaType.parse("audio/amr");


    public static UploadBean postUpload(Context context, String uri, String path, int type) {

        String json = "";
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        File f = new File(path);
        if (f != null) {
            if (type == 1) {
                builder.addFormDataPart("file", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            } else if (type == 2) {
                builder.addFormDataPart("file", f.getName(), RequestBody.create(VIDE0_TYPE_MP4, f));
            } else if (type == 3) {
                builder.addFormDataPart("file", f.getName(), RequestBody.create(AUDIO_TYPE_AMR, f));
            }
        }
        //添加其它信息
        builder.addFormDataPart("type", "inquiry");

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .addHeader("token_id", UserPreference.getTOKEN())
                .url(uri)//地址
                .post(requestBody)//添加请求体
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("test", "postUpload: " + json + "  " + UserPreference.getTOKEN());
        UploadBean uploadBean = new Gson().fromJson(json, UploadBean.class);
        return uploadBean;
    }

    public static UploadBean postUploadImg(Context context, String uri, File file) {

        String json = "";
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));

        //添加其它信息
        builder.addFormDataPart("type", "inquiry");

        MultipartBody requestBody = builder.build();
        //构建请求
        Request request = new Request.Builder()
                .addHeader("token_id", UserPreference.getTOKEN())
                .url(uri)//地址
                .post(requestBody)//添加请求体
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                json = response.body().string();
            } else {
                json = "response not successful";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("test", "postUpload: " + json + "  " + UserPreference.getTOKEN());
        UploadBean uploadBean = new Gson().fromJson(json, UploadBean.class);
        return uploadBean;
    }

    public static String loginByPost(String path, String courseId, int price, String rewarder) {

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");


//            "courseId：视频ID；
//            price：金额;(分)；
//            rewarder：打赏人;(分)"

            //数据准备
            String data = "courseId=" + courseId + "&price=" + price + "&rewarder=" + rewarder;
            Log.e("test", "loginByPost: " + data);
            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length() + "");
            connection.setRequestProperty("token_id", UserPreference.getTOKEN());

            //post的方式提交实际上是留的方式提交给服务器
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());


            //获得结果码
            int responseCode = connection.getResponseCode();
            Log.e("test", "responseCode: " + responseCode);
            if (responseCode == 200) {
                //请求成功
                InputStream is = connection.getInputStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(is));
                String message = "";
                String line = null;
                while ((line = input.readLine()) != null) {
                    message += line;
                }

                Log.e("test", "loginByPost: " + message);
                return message;
            } else {
                //请求失败
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
