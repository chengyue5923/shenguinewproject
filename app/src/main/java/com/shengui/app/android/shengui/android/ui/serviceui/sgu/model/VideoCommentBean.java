package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class VideoCommentBean {

    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490711397
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : [{"commentId":"16b1721c724e4692a4118bb9e997cd26","userId":"zengxiangtest","receiver":null,"content":"111","createTime":1490704543,"top":0,"countComments":1,"data":[{"commentId":"5feaa70a29154b49b1977b0b7fe0ac7d","userId":"zengxiangtest","receiver":"zengxiangtest","content":"啦啦啦","createTime":1490704551}]},{"commentId":"4b19f38e559c4249bbbb2ade72bf648f","userId":"zengxiangtest","receiver":null,"content":"爸爸","createTime":1490704536,"top":0,"countComments":0},{"commentId":"3965be910e0d46a58d9074dacaaef9e1","userId":"zengxiangtest","receiver":null,"content":"无线","createTime":1490700215,"top":0,"countComments":0},{"commentId":"9528760f469b49328eb1c5d6f371aa60","userId":"zengxiangtest","receiver":null,"content":"哈哈","createTime":1490700160,"top":0,"countComments":0}]
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;
    @SerializedName("void")
    private Object voidX;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * commentId : 16b1721c724e4692a4118bb9e997cd26
         * userId : zengxiangtest
         * receiver : null
         * content : 111
         * createTime : 1490704543
         * top : 0
         * countComments : 1
         * data : [{"commentId":"5feaa70a29154b49b1977b0b7fe0ac7d","userId":"zengxiangtest","receiver":"zengxiangtest","content":"啦啦啦","createTime":1490704551}]
         */

        private String id;
        private String userId;
        private Object receiver;
        private String content;
        private int createTime;
        private int top;
        private int countComments;
        private List<DataBean> data;
        private String userName;
        private String userImage;

        private boolean tag;

        public boolean getTag() {
            return tag;
        }

        public void setTag(boolean tag) {
            this.tag = tag;
        }

        public DataBeanX(String commentId, String userId, String content, Object receiver, int createTime, int top, int countComments, String userName, String userImage) {
            this.id = commentId;
            this.userId = userId;
            this.content = content;
            this.receiver = receiver;
            this.createTime = createTime;
            this.top = top;
            this.countComments = countComments;
            this.userName = userName;
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
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

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getCountComments() {
            return countComments;
        }

        public void setCountComments(int countComments) {
            this.countComments = countComments;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }


        public static class DataBean {
            /**
             * commentId : 5feaa70a29154b49b1977b0b7fe0ac7d
             * userId : zengxiangtest
             * receiver : zengxiangtest
             * content : 啦啦啦
             * createTime : 1490704551
             */

            private String commentId;
            private String userId;
            private String receiver;
            private String ReceiverName;
            private String ReceiverImage;
            private String userName;
            private String userImage;
            private String content;
            private int createTime;

            public DataBean(String commentId, String userId, String receiver, String receiverName, String userName, String content, int createTime) {
                this.commentId = commentId;
                this.userId = userId;
                this.receiver = receiver;
                ReceiverName = receiverName;
                this.userName = userName;
                this.content = content;
                this.createTime = createTime;
            }

            public String getReceiverName() {
                return ReceiverName;
            }

            public void setReceiverName(String receiverName) {
                ReceiverName = receiverName;
            }

            public String getReceiverImage() {
                return ReceiverImage;
            }

            public void setReceiverImage(String receiverImage) {
                ReceiverImage = receiverImage;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
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
}
//   public DataBeanX(String commentId, String userId, Object receiver, String content, int createTime, int countComments, String userName, String userImage) {
//        this.commentId = commentId;
//        this.userId = userId;
//        this.receiver = receiver;
//        this.content = content;
//        this.createTime = createTime;
//        this.countComments = countComments;
//        this.userName = userName;
//        this.userImage = userImage;
//    }
//E/test: videoComment: {"message":"执行成功","imageServer":"http://sgdx.gui66.com","time":1490710994,"courseServer":"http://sgdx.gui66.com","status":1,"data":[{"commentId":"7579c7befa09450aa37c31de95d5f96b","userId":"zengxiangtest","receiver":null,"content":"552","createTime":1489822251,"top":1,"countComments":0},{"commentId":"2d6443d5825546a58f6bced2c71acc19","userId":"zengxiangtest","receiver":null,"content":"哈哈","createTime":1490710380,"top":0,"countComments":0},{"commentId":"45b976839e034d63ac1f161136d60a9f","userId":"zengxiangtest","receiver":null,"content":"写评论","createTime":1490708809,"top":0,"countComments":0},{"commentId":"6cc08e77fd914763a628955f7eab2c59","userId":"zengxiangtest","receiver":null,"content":"写评论","createTime":1490708785,"top":0,"countComments":0},{"commentId":"7a5d815d5b0242808d7a88746b61d5c6","userId":"zengxiangtest","receiver":null,"content":"写评论","createTime":1490708694,"top":0,"countComments":0},{"commentId":"0a2649f1401244f68d4fcfe278dadb9e","userId":"zengxiangtest","receiver":null,"content":"。。。","createTime":1490704947,"top":0,"countComments":0},{"commentId":"8494de8a7a874b308c1b9798e31f0d31","userId":"zengxiangtest","receiver":null,"content":"还","createTime":1490703681,"top":0,"countComments":0},{"commentId":"a75f365ceb484e2ba3e7c66e5bc200b2","userId":"zengxiangtest","receiver":null,"content":"看看","createTime":1490703575,"top":0,"countComments":0},{"commentId":"c8e7353dff0b44dda6bccfe8799ff82d","userId":"zengxiangtest","receiver":null,"content":"fff","createTime":1490703556,"top":0,"countComments":0},{"commentId":"74880eb5968f436fba856473d6aab8bd","userId":"zengxiangtest","receiver":null,"content":"ss","createTime":1490702909,"top":0,"countComments":0}],"void":null}
