package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class TeachersTeamBean {


    /**
     * data : [{"id":"OO1","name":"神龟大学","avatar":"/file/user/gui_avatar_default.png","signature":"神龟网御用讲师","courseCount":11,"viewsCount":47}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492428561
     * status : 1
     */

    private String message;
    @SerializedName("void")
    private Object voidX;
    private String fileServer;
    private int time;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : OO1
         * name : 神龟大学
         * avatar : /file/user/gui_avatar_default.png
         * signature : 神龟网御用讲师
         * courseCount : 11
         * viewsCount : 47
         */

        private String id;
        private String name;
        private String avatar;
        private String signature;
        private int courseCount;
        private int viewsCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(int courseCount) {
            this.courseCount = courseCount;
        }

        public int getViewsCount() {
            return viewsCount;
        }

        public void setViewsCount(int viewsCount) {
            this.viewsCount = viewsCount;
        }
    }
}
