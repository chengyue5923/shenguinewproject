package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/10.
 */

public class UploadBean {

    /**
     * data : /file/inquiry/ff8b7e394ef84269922cbdc0183eb595.jpg
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.129/
     * time : 1491812530
     * status : 1
     */

    private String data;
    private String message;
    @SerializedName("void")
    private Object voidX;
    private String fileServer;
    private int time;
    private int status;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getVoidX() {
        return voidX;
    }

    public void setVoidX(Object voidX) {
        this.voidX = voidX;
    }

    public String getFileServer() {
        return fileServer;
    }

    public void setFileServer(String fileServer) {
        this.fileServer = fileServer;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
