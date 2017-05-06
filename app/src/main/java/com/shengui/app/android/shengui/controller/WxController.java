package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * 登录注册
 * Created by yanbo on 2016/7/22.
 */
public class WxController {
    private static WxController instance;


    public static WxController getInstance() {
        if (instance == null)
            instance = new WxController();
        return instance;
    }

    public void weixintoken(ViewNetCallBack callBack, String appid, String secret, String code) {
        try {
            ConnectTool.httpRequest(HttpConfig.weixintoken, new MapBuilder<String, Object>()
                    .add("appid", appid)
                    .add("secret", secret)
                    .add("code", code)
                    .add("grant_type", "authorization_code")
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    public void weixininfo(ViewNetCallBack callBack, String access_token, String openid) {
        try {
            ConnectTool.httpRequest(HttpConfig.weixininfo, new MapBuilder<String, Object>()
                    .add("access_token", access_token)
                    .add("openid", openid)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

}
