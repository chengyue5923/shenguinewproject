package com.base.framwork.file.net;




import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class HttpRequest {

    /**
     * http request
     *
     * @return
     */
    public static String httpRequest(HttpUriRequest post, int timeOut) {
        HttpClient hc = new DefaultHttpClient();


        hc.getParams().setIntParameter(
                HttpConnectionParams.SO_TIMEOUT, timeOut); // 超时设置
        hc.getParams().setIntParameter(
                HttpConnectionParams.CONNECTION_TIMEOUT, timeOut);// 连接超时
        return post(hc, post);
    }


    /**
     * @param client
     * @param post
     * @return
     */
    protected static String post(HttpClient client, HttpUriRequest post) {
        HttpResponse response = null;
        ByteArrayOutputStream out = null;
        InputStream in = null;
        try {
            Logger.e("------开始---------");
            response = client.execute(post);
            Logger.e("------结束---------");
            if (response.getStatusLine().getStatusCode() != 200) {
                Logger.e("------结束-----1----");
                post.abort();
                try {
                    Logger.e("------结束-----2----");
                    String msg = EntityUtils.toString(response.getEntity(),
                            "UTF-8");
                    Logger.e("HttpRequest original response:" + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(e.getLocalizedMessage(), e);

                }


            }
            HttpEntity responseEntity = response.getEntity();

            String message = "";
            if (responseEntity != null) {
                Logger.e("------结束-----3----");
                message = EntityUtils.toString(responseEntity, "UTF-8");
                Logger.ee("------结束-----4----" + message);
                if (StringTools.isNullOrEmpty(message)) {
                    throw new Exception();
                }
            }
            return message;
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
            Logger.e("HttpRequest's exception:" + e.getMessage());
            post.abort();
            if (e instanceof SocketTimeoutException) {
                // 连接超时
                Logger.e("----------------超时异常---------------");
//                throw  new Exception();
            } else if (e instanceof SocketException) {
                Logger.e("--------e.getMessage()---------" + e.getMessage());
                // 网络连接异常
                if (e.getMessage().indexOf("time out") == -1) {
                    Logger.e("--------e.getMessage()---------" + e.getMessage());

//                    throw  new Exception();
                }

//				else
//                    throw  new Exception();
            } else {
                // 数据请求错误
//                throw  new Exception();
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    Logger.e(e.getLocalizedMessage(), e);
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Logger.e(e.getLocalizedMessage(), e);
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}