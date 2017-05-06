package com.base.framwork.net.connect.http.impl;

import android.content.Context;

import com.base.framwork.cach.preferce.PreferceManager;
import com.base.framwork.exc.BadSeverExcetion;
import com.base.framwork.exc.BaseException;
import com.base.framwork.exc.NoConnectExctption;
import com.base.framwork.exc.TimeOutException;
import com.base.framwork.net.configer.Constans;
import com.base.framwork.net.lisener.HttpNetCallBack;
import com.base.framwork.net.lisener.NetCallback;
import com.base.framwork.utils.StringUtils;
import com.base.platform.utils.android.Common;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.MapUtils;
import com.base.platform.utils.java.StringTools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现 httpclient 的manager
 */
public class HttpClientManager implements HttpNetCallBack {

    static AsyncHttpClient client;
    static Context context;

    public static HttpClientManager getInstance(Context con) {
        context = con;
        client = new AsyncHttpClient();
        return SingleClearCach.instance;
    }

    @Override
    public void get(String url, Map<String, Object> params, long timeOut, boolean cach, NetCallback responesCallBack) throws BaseException {
        Logger.e("request_method----get");
        client.setTimeout((int) timeOut);
        client.setConnectTimeout((int) timeOut);
        url = MapUtils.map2UrlParams(url, params);
        Map<String, String> header = new HashMap<>();
        addHeader(header);
        Logger.e("--request==" + url);
        client.get(url, new ReslutCallBack(responesCallBack));
    }

    @Override
    public void getWithHeader(String url, Map<String, Object> params, Map<String, String> header, long timeOut, boolean cach, NetCallback responesCallBack) throws BaseException {
        Logger.e("request_method----get");
        client.setTimeout((int) timeOut);
        client.setConnectTimeout((int) timeOut);
        url = MapUtils.map2UrlParams(url, params);
        addHeader(header);
        Logger.e("------request==" + url);
        client.get(url, new ReslutCallBack(responesCallBack));
    }

    @Override
    public void post(String url, Map<String, Object> params, long timeOut, boolean cach, NetCallback responesCallBack) throws BaseException {
        Logger.e("request_method----post");
        client.setTimeout((int) timeOut);
        client.setConnectTimeout((int) timeOut);
        Map<String, String> header = new HashMap<>();
        addHeader(header);
        Logger.e("--request==" + url);
        client.post(context, url, addParam(params), new ReslutCallBack(responesCallBack));

    }

    @Override
    public void postWithHeader(String url, Map<String, Object> params, Map<String, String> header, long timeOut, boolean cach, NetCallback responesCallBack) throws BaseException {
        Logger.e("request_method----post");
        client.setTimeout((int) timeOut);
        client.setConnectTimeout((int) timeOut);
        addHeader(header);
        Logger.e("--request==" + url);
        client.post(context, url, addParam(params), new ReslutCallBack(responesCallBack));
    }

    @Override
    public void cancle() {
        client.cancelRequests(context, true);
    }

    @Override
    public void delete(String url, Map<String, Object> params, Map<String, String> header, long timeOut, boolean cach, NetCallback responesCallBack) throws BaseException {
        client.setTimeout((int) timeOut);
        client.setConnectTimeout((int) timeOut);
        addHeader(header);
        client.delete(url, addParam(params), new ReslutCallBack(responesCallBack));
    }

    private void addHeader(Map<String, String> header) {
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                Logger.e("header-key==" + entry.getKey());
                Logger.e("header-values==" + entry.getValue());
//                UserPreference.getFENVS()
                 client.addHeader(entry.getKey(),entry.getValue());
//                client.addHeader("CLIENTENV","eyJyZXNvbHV0aW9uIjoiNzIwKjEyODAiLCJwYWNrYWdlX25hbWUiOiJjb20uc2hlbmd1aS5hcHAuYW5kcm9pZC5zaGVuZ3VpIiwib3MiOiJhbmRyb2lkIiwib3NfdmVyc2lvbiI6IjQuNC4yIiwiZGV2aWNlX21vZGVsIjoiTEctSDgxOCIsImFwcF92ZXJzaW9uIjoiMS4zMC1kZWJ1ZyIsImxvY2F0aW9uIjoiMC4wLDAuMCIsImRldmljZV9pZCI6Ijg2NDM5NDAxMDEyODI2NCJ9");
            }
        }
        client.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");

//        if (!StringTools.isNullOrEmpty(PreferceManager.getInsance().getValueBYkey(Constans.jseesion))) {
//            String session = PreferceManager.getInsance().getValueBYkey(Constans.jseesion);
//            client.addHeader("Cookie", "JSESSIONID=" + session);
//            Logger.e("session = " + session);
//        }


    }

    private RequestParams addParam(Map<String, Object> param) {
        RequestParams params1 = new RequestParams();
        for (Map.Entry<String, ?> entry : param.entrySet()) {
            if ("file".equals(entry.getKey())) {
                try {
                    params1.put("file", (File) entry.getValue());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if ("img[]".equals(entry.getKey())) {
                try {
                    params1.put("img[]", (File[])entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ("files".equals(entry.getKey())) {
                try {
                    params1.put("files", (File)entry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ("image".equals(entry.getKey())) {
                try {
                    params1.put("img[]", (File) entry.getValue());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            params1.put(entry.getKey(), entry.getValue());
            Logger.e("param-key==" + entry.getKey());
            Logger.e("param-values==" + entry.getValue());
        }
        return params1;
    }

    static class SingleClearCach {
        static HttpClientManager instance = new HttpClientManager();
    }

    public class ReslutCallBack extends AsyncHttpResponseHandler {

        NetCallback responesCallBack;

        public ReslutCallBack(NetCallback responesCallBack) {
            this.responesCallBack = responesCallBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            responesCallBack.onstart();


        }

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {

            try {
                String reslut = new String(bytes, "utf-8");
                Logger.e("lodderreslut"+reslut);
                JSONObject object = new JSONObject(reslut);
//                if (object.getBoolean("status")) {
//                    saveCookie(headers);
//                }
                responesCallBack.dealReslut(reslut);
                Logger.e("输出结果=" + reslut);

            } catch (Exception e) {
                Logger.e("转换异常"+e.getMessage());
            }
            responesCallBack.onEnd();


        }

        private void saveCookie(Header[] response) {
            for (int i = 0; i < response.length; i++) {
                Logger.e("response.name   == " + response[i].getName() + "    response.value   == " + response[i].getValue());
                if (StringUtils.equalsIgnoreCase("Set-Cookie", response[i].getName())) {
                    String value = response[i].getValue();
                    value = StringUtils.substringBefore(value, ";");
                    value = StringUtils.substringAfter(value, "=");
                    if (!value.contains("delete"))
                        PreferceManager.getInsance().saveValueBYkey(Constans.jseesion, value);
                    break;
                }
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Logger.e("网络请求失败");
            Logger.e(throwable.getLocalizedMessage());
            String error = "";
            try {
                error = new String(bytes, "utf-8");
            } catch (Exception e) {
            }

            responesCallBack.onEnd();
            if(throwable instanceof NoClassDefFoundError){
                responesCallBack.dealFailer(new TimeOutException("socket time out>>>error message:" + throwable.getLocalizedMessage()), error);
                return;
            }
            if (throwable instanceof SocketTimeoutException) {
                responesCallBack.dealFailer(new TimeOutException("socket time out>>>error message:" + throwable.getLocalizedMessage()), error);
                return;
            }
            if (!Common.isNetworkAvailable()) {
                responesCallBack.dealFailer(new NoConnectExctption("no network>>>error message:" + throwable.getLocalizedMessage()), error);
                return;
            }
            responesCallBack.dealFailer(new BadSeverExcetion("bad server>>>error message:" + throwable.getLocalizedMessage()), error);
        }
    }

}
