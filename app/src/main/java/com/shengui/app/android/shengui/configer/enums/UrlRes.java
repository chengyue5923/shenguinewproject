package com.shengui.app.android.shengui.configer.enums;

import android.content.Context;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.shengui.app.android.shengui.configer.constants.Constant;


/**
 * 从资源中获取的rul 的res
 */
public class UrlRes {


    Context context;

    private String variableURL;

    public String getVariableURL() {
        return variableURL;
    }

    public void setVariableURL(String variableURL) {
        this.variableURL = variableURL;
        Logger.e("URL change to "+variableURL);
    }

    public static UrlRes getInstance() {

        return SingleClearCach.instance;

    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


//    http://sgwb.ngrok.cc  http://api.gui66.com/  http://192.168.1.128:80/
    public String getUrl() {
        if(StringTools.isNullOrEmpty(variableURL)){
            return "http://api.gui66.com/";
        }else{
            return variableURL;
        }
    }


    public String getPictureUrl() {
        if(StringTools.isNullOrEmpty(variableURL)){
            return "http://api.gui66.com/";
        }else{
            return variableURL;
        }
    }
    public String getPictureImageUrl(String url) {
            return "http://api.gui66.com/"+url;
    }
    public String getWXUrl() {
        return "https://api.weixin.qq.com/";
    }
    static class SingleClearCach {
        static UrlRes instance = new UrlRes();
    }
}
