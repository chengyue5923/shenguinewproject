package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class UserBean {

    /**
     * message : 执行成功
     * imageServer : http://192.168.1.129/image.server
     * time : 1490238905
     * courseServer : http://192.168.1.129
     * status : 1
     * data : [{"userId":"5346589759ab49ec9e49f0274c812d9f","realName":"李小龙","face":"/file/user/w3rewawefrq231432141234ewqasf3lu.jpg"},{"userId":"zengxiangtest","realName":"曾祥","face":"/file/user/w3rewawefrq231432141234ewqasf3lu.jpg"}]
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;
    @SerializedName("void")
    private Object voidX;
    private List<DataBean> data;

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

    public Object getVoidX() {
        return voidX;
    }

    public void setVoidX(Object voidX) {
        this.voidX = voidX;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 5346589759ab49ec9e49f0274c812d9f
         * realName : 李小龙
         * face : /file/user/w3rewawefrq231432141234ewqasf3lu.jpg
         */

        private String userId;
        private String realName;
        private String face;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
