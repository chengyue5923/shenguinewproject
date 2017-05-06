package com.shengui.app.android.shengui.configer.enums;

/**
 * Created by Wesley on 2015/3/4.
 */
public enum HttpMethod {


    GET(0),
    POST(1),
    DELETE(2);
    private int method;

    HttpMethod(int method) {
        this.method = method;
    }

    public static HttpMethod getMethodByType(int method) {
        if (method == 0) {
            return GET;
        }
        if (method == 1) {
            return POST;
        }
        return GET;
    }

    public int getMethod() {
        return method;
    }


}
