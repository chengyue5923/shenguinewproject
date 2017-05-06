package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */

public class MyMsgBean {

    /**
     * data : [{"id":"bd9207b511f343aa90361e28e40393f3","userId":"48","receiver":"48","content":"？？？","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492407374,"countName":"陆龟三剑侠"},{"id":"479f0d0caf9c4472bc8c02b8cb581c40","userId":"48","receiver":"48","content":"？。。","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492407091,"countName":"陆龟三剑侠"},{"id":"33a6ef7c38ff42609e6a22ee77ab350c","userId":"48","receiver":"48","content":"。。","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406676,"countName":"陆龟三剑侠"},{"id":"ae8e877433924d03baff4a62dbe4b5ea","userId":"48","receiver":"48","content":"。。。","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406090,"countName":"陆龟三剑侠"},{"id":"3161657407e747dfaf4693fbaae6b0f3","userId":"48","receiver":"48","content":"！！！","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406085,"countName":"陆龟三剑侠"},{"id":"c1641bd4dc1447068a34698cf8488b3a","userId":"48","receiver":"48","content":"！！！！","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406082,"countName":"陆龟三剑侠"},{"id":"10b304b2556a413d8b7c118662b9fe0f","userId":"48","receiver":"48","content":"！！！","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406078,"countName":"陆龟三剑侠"},{"id":"de477b98c73946e4a194794c6191d10e","userId":"48","receiver":"48","content":"。。","courseId":"1d9dd1a2b8384a14a339c456ec457332","createTime":1492406066,"countName":"陆龟三剑侠"},{"id":"c07cac2f47fc4011ad480c3da587ef2e","userId":"48","receiver":"48","content":"。。","courseId":"e8be9ee2e4034554b4bf3d8c00906dce","createTime":1492405503,"countName":"2015黄泽泥龟孵化"}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492409955
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
         * id : bd9207b511f343aa90361e28e40393f3
         * userId : 48
         * receiver : 48
         * content : ？？？
         * courseId : 1d9dd1a2b8384a14a339c456ec457332
         * createTime : 1492407374
         * countName : 陆龟三剑侠
         */

        private String id;
        private String userId;
        private String receiver;
        private String userName;
        private String userFace;
        private String receiveName;
        private String receiveFace;
        private String content;
        private String courseId;
        private int createTime;
        private String countName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserFace() {
            return userFace;
        }

        public void setUserFace(String userFace) {
            this.userFace = userFace;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceiveFace() {
            return receiveFace;
        }

        public void setReceiveFace(String receiveFace) {
            this.receiveFace = receiveFace;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getCountName() {
            return countName;
        }

        public void setCountName(String countName) {
            this.countName = countName;
        }
    }
}
