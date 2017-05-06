package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/28.
 */

public class VideoPublishCommentBean{


    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490696276
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : {"commentId":"74177e3a3db94d158a7e0ceb3225232e","userId":"zengxiangtest","receiver":null,"content":"测试","createTime":1490696276}
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
         * commentId : 74177e3a3db94d158a7e0ceb3225232e
         * userId : zengxiangtest
         * receiver : null
         * content : 测试
         * createTime : 1490696276
         */

        private String commentId;
        private String userId;
        private Object receiver;
        private String content;
        private int createTime;

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

        public Object getReceiver() {
            return receiver;
        }

        public void setReceiver(Object receiver) {
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
