package com.shengui.app.android.shengui.android.ui.serviceui.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/13.
 */

public class SaveChatResultBean {

    /**
     * data : {"content":"","id":"6cb230cc73774fc2b9afdf6e4acf70f9","createTime":1492138504,"inquiryId":"ae5938c175794ebf8846b7e85fb6cd7e","userId":"13","contentType":"4","media":"/file/inquiry/e423e97def72444eb6f84f82bdc907a9.amr"}
     * message : 执行成功
     * void : null
     * fileServer : http://sgwa.ngrok.cc/
     * time : 1492138504
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
         * content :
         * id : 6cb230cc73774fc2b9afdf6e4acf70f9
         * createTime : 1492138504
         * inquiryId : ae5938c175794ebf8846b7e85fb6cd7e
         * userId : 13
         * contentType : 4
         * media : /file/inquiry/e423e97def72444eb6f84f82bdc907a9.amr
         */

        private String content;
        private String id;
        private int createTime;
        private String inquiryId;
        private String userId;
        private String userName;
        private String userFace;
        private String contentType;
        private String media;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public String getInquiryId() {
            return inquiryId;
        }

        public void setInquiryId(String inquiryId) {
            this.inquiryId = inquiryId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }
}
