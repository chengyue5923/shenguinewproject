package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TeacherDataBean {


    /**
     * data : {"id":"21210","name":"6666","truename":"讲师测试专用","signature":"个性签名","extend":"简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介","avatar":"/file/user/gui_avatar_default.png","courseCount":5,"viewsCount":2}
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492227397
     * status : 1
     */

    private DataBean data;
    private String message;
    @SerializedName("void")
    private Object voidX;
    private String fileServer;
    private int time;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * id : 21210
         * name : 6666
         * truename : 讲师测试专用
         * signature : 个性签名
         * extend : 简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介
         * avatar : /file/user/gui_avatar_default.png
         * courseCount : 5
         * viewsCount : 2
         */

        private String id;
        private String name;
        private String truename;
        private String signature;
        private String extend;
        private String avatar;
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

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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
