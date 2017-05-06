package com.shengui.app.android.shengui.utils.net;

import android.util.Log;

import com.base.framwork.net.factory.HttpRequestFactory;
import com.base.framwork.net.lisener.HttpNetCallBack;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.configer.enums.HttpConfigBean;
import com.shengui.app.android.shengui.configer.enums.HttpManager;
import com.shengui.app.android.shengui.configer.enums.UrlRes;
import com.shengui.app.android.shengui.db.UserPreference;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 连接底层的 http工具类
 */
public class ConnectTool {

    /**
     * http request
     *
     * @param configs  配置的枚举
     * @param param    hm
     * @param listener 监听的
     * @param cl       lass
     * @throws Exception
     */
    public static void httpRequest(HttpConfig configs, Map<String, Object> param, ViewNetCallBack listener,
                                   Class cl) throws Exception {

        HttpConfigBean config = HttpManager.getInstance().getConifgById(configs);
        String url;
        if(configs==HttpConfig.weixintoken||configs==HttpConfig.weixininfo){
             url = UrlRes.getInstance().getWXUrl() + config.getActioin() + "?";
        }else{
             url = UrlRes.getInstance().getUrl() + config.getActioin() + "?";
        }
//        String url = UrlRes.getInstance().getUrl() + config.getActioin() + "?";
        HttpRequestFactory until = HttpRequestFactory.getInstance();
        until.setNetType(HttpRequestFactory.HTTPCLIENT);
        HttpNetCallBack callBack = until.getHttpRequst();
        BaseNetImpl implLinener = new BaseNetImpl(listener, cl, configs);
        HashMap<String, String> header = new HashMap<>();
        header.put("CLIENTENV",UserPreference.getFENVS());
        implLinener.setParam(param);
        if (param.size() == 0) {
        } else {
            Iterator iter = param.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = entry.getKey().toString();
                Object val = entry.getValue();
            }
        }
        switch (config.getShowEnumsMethod()) {
            case GET:
                if (config.isHeader()) {
                    Log.e("test", "httpRequest: "+url +header.get("mobile"));
                    callBack.getWithHeader(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                    return;
                }
                callBack.get(url, param, config.getTimeout(), config.isCach(), implLinener);
                return;
            case POST:
                if (config.isHeader()) {
                    callBack.postWithHeader(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                    return;
                }
                callBack.post(url, param, config.getTimeout(), config.isCach(), implLinener);
                return;
            case DELETE:
                callBack.delete(url, param, header, config.getTimeout(), config.isCach(), implLinener);
                return;
            default:
                break;
        }
    }

    public static String getRequest(HttpConfig configs,Map<String, String> params) {
        String content = null;
        StringBuilder buf = new StringBuilder();
        if (params != null && params.size() != 0) {


            for (Map.Entry<String, String> entry : params.entrySet()) {

                try {
                    buf.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                buf.append("&");

            }
            buf.deleteCharAt(buf.length() - 1);
        }
        content = buf.toString();

        HttpConfigBean config = HttpManager.getInstance().getConifgById(configs);
        String url = UrlRes.getInstance().getUrl() + config.getActioin() + "?"+content;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getMethod = new HttpGet(url);

            HttpResponse response = null;
            response = httpClient.execute(getMethod);

            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    content = EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
