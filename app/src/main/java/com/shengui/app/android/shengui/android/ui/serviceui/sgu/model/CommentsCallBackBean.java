package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/29.
 */

public class CommentsCallBackBean {

    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490786218
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : {"commentId":"225c11cc8a0f45e0a0de8952cf2df118","userId":"5346589759ab49ec9e49f0274c812d9f","receiver":"zengxiangtest","content":"。。。","createTime":1490786218}
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getVoidX() {
        return voidX;
    }

    public void setVoidX(Object voidX) {
        this.voidX = voidX;
    }

    public static class DataBean {
        /**
         * commentId : 225c11cc8a0f45e0a0de8952cf2df118
         * userId : 5346589759ab49ec9e49f0274c812d9f
         * receiver : zengxiangtest
         * content : 。。。
         * createTime : 1490786218
         */

        private String commentId;
        private String userId;
        private String userRealName;
        private String userImage;
        private String receiverRealName;
        private String receiver;
        private String content;
        private int createTime;

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getReceiverRealName() {
            return receiverRealName;
        }

        public void setReceiverRealName(String receiverRealName) {
            this.receiverRealName = receiverRealName;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
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

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }
    }
}
