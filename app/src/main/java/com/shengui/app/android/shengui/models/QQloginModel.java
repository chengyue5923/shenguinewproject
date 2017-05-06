package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/23.
 */

public class QQloginModel implements Serializable {

    String user_status;
    LoginResultModel user_info;

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public LoginResultModel getUser_info() {
        return user_info;
    }

    public void setUser_info(LoginResultModel user_info) {
        this.user_info = user_info;
    }
}
