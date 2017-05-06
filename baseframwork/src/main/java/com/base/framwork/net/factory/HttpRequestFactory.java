package com.base.framwork.net.factory;

import android.content.Context;

import com.base.framwork.net.connect.http.impl.HttpClientManager;
import com.base.framwork.net.lisener.HttpNetCallBack;


/**
 * http request 的请求工厂
 */
public class HttpRequestFactory {
    /**
     * httpclient 的sdk调用
     */
    public final static int HTTPCLIENT = 100;
    /**
     * volley    sdk调用
    */
    public final static int VOLLEY = 101;
    /**
     * context
     */
    Context context;
    private int netType = HTTPCLIENT;

    public static HttpRequestFactory getInstance() {
        return SingleClearCach.instance;
    }

    /**
     * Type的 设置
     *
     * @param netType
     */

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * @return 返回 各自的http管理类
     */
    public HttpNetCallBack getHttpRequst() {
        HttpNetCallBack request = null;
        switch (netType) {
            case HTTPCLIENT:
                request = HttpClientManager.getInstance(context);
                break;
            case VOLLEY:
                request = HttpClientManager.getInstance(context);
                break;
            default:
                break;
        }
        return request;
    }

    static class SingleClearCach {
        static HttpRequestFactory instance = new HttpRequestFactory();
    }


}
