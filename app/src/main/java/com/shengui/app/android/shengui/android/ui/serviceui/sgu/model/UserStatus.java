package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/27.
 */

public class UserStatus {

    /**
     * data : {"id":"13","name":"。。。","truename":"。。。","role":"NORMAL_USER","userType":3,"avatar":"/file/user/13.jpg?20170413","sex":1,"lastLogin":1.492069011E9,"signature":""}
     * message : 执行成功
     * void : null
     * fileServer : http://sgwa.ngrok.cc/
     * time : 1492074303
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
         * id : 13
         * name : 。。。
         * truename : 。。。
         * role : NORMAL_USER
         * userType : 3.0
         * avatar : /file/user/13.jpg?20170413
         * sex : 1.0
         * lastLogin : 1.492069011E9
         * signature :
         */

        private String id;
        private String name;
        private String truename;
        private String role;
        private double userType;
        private String avatar;
        private double sex;
        private double lastLogin;
        private String signature;

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

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public double getUserType() {
            return userType;
        }

        public void setUserType(double userType) {
            this.userType = userType;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public double getSex() {
            return sex;
        }

        public void setSex(double sex) {
            this.sex = sex;
        }

        public double getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(double lastLogin) {
            this.lastLogin = lastLogin;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
