package com.shengui.app.android.shengui.android.ui.serviceui.serviceModle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/16.
 */

public class ReminderBean {

    /**
     * data : 检查龟蛋的时候，只需要将表面的蛭石或者黄泥沙用小扫子扫开，观察表面是否有异常即可。如果要将龟蛋拿起，放下时不能将其颠倒。
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492332635
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
