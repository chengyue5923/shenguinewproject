package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/29.
 */

public class NoResultBean {

    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490768760
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : 1
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;
    private int data;
    @SerializedName("void")
    private Object voidX;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCourseServer() {
        return courseServer;
    }

    public void setCourseServer(String courseServer) {
        this.courseServer = courseServer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Object getVoidX() {
        return voidX;
    }

    public void setVoidX(Object voidX) {
        this.voidX = voidX;
    }
}
