package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/16.
 */

public class DoctorDetailBean {

    /**
     * data : {"id":"31","name":"15111111111","truename":"哈哈哈","signature":"666","extend":"测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介","avatar":"/file/user/gui_avatar_default.png","inquiryCount":0}
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492340234
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
         * id : 31
         * name : 15111111111
         * truename : 哈哈哈
         * signature : 666
         * extend : 测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介测试侧四简介
         * avatar : /file/user/gui_avatar_default.png
         * inquiryCount : 0
         */

        private String id;
        private String name;
        private String truename;
        private String signature;
        private String extend;
        private String avatar;
        private int inquiryCount;
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

        public int getInquiryCount() {
            return inquiryCount;
        }

        public void setInquiryCount(int inquiryCount) {
            this.inquiryCount = inquiryCount;
        }
    }
}
