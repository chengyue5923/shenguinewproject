package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class AllcommentsBean {


    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490773328
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : [{"commentId":"a7d2dd42280647c8b7f3054beb764736","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"555","createTime":1489815595},{"commentId":"28495301d26e4f349d4b5bfc4af5ffda","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"444","createTime":1489815592},{"commentId":"fba7729fd8fc4b3dad2d8749e623ab7b","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"333","createTime":1489815590},{"commentId":"bdc9c25207bb4da09308e018ccd0cc70","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"222","createTime":1489815588},{"commentId":"4d56f1eadaca442caba4c14bb34723f1","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"11","createTime":1489815586},{"commentId":"4e803613bc2a4cbfb3f1c8f8f84ebffc","userId":"zengxiangtest","receiver":"5346589759ab49ec9e49f0274c812d9f","content":"p","createTime":1489815584}]
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
         * commentId : a7d2dd42280647c8b7f3054beb764736
         * userId : zengxiangtest
         * receiver : 5346589759ab49ec9e49f0274c812d9f
         * content : 555
         * createTime : 1489815595
         */

        private String commentId;
        private String userId;
        private String receiver;
        private String receiverImage;
        private String userRealName;
        private String userImage;
        private String receiverRealName;
        private String content;
        private int createTime;

        public DataBean(String commentId, String userId, String receiver, String userRealName, String userImage, String receiverRealName, String content, int createTime) {
            this.commentId = commentId;
            this.userId = userId;
            this.receiver = receiver;
            this.userRealName = userRealName;
            this.userImage = userImage;
            this.receiverRealName = receiverRealName;
            this.content = content;
            this.createTime = createTime;
        }

        public String getReceiverImage() {
            return receiverImage;
        }

        public void setReceiverImage(String receiverImage) {
            this.receiverImage = receiverImage;
        }

        public String getReceiverRealName() {
            return receiverRealName;
        }

        public void setReceiverRealName(String receiverRealName) {
            this.receiverRealName = receiverRealName;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
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
