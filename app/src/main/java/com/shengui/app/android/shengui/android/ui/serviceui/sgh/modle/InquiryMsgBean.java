package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class InquiryMsgBean {

    /**
     * data : [{"id":"cf486b0376c44cf4bf41b2d0a9391844","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":1,"content":"111","createTime":1490952684,"userId":"zengxaingdoc","media":""},{"id":"66dcd44af8564fe4a10bac11591687ad","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":2,"content":"","createTime":1490952693,"userId":"zengxaingdoc","media":"/file/inquiry/6279085a835d48588c71611b238cfdad.jpg"},{"id":"01e399a232824b6fb796581d3c5a3270","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":2,"content":"","createTime":1490952693,"userId":"zengxaingdoc","media":"/file/inquiry/581be69ad3d64a849a562d4ee0b0127e.jpg"},{"id":"26dccdaff40b4f19b4bb89707fd5c676","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":1,"content":"55","createTime":1490953588,"userId":"zengxaingdoc","media":""},{"id":"5a88fbbd6b1644518c0d3469bf4aaef4","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":1,"content":"66","createTime":1490954057,"userId":"zengxaingdoc","media":""},{"id":"537611f330284625a50d1eb2b832c3df","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":1,"content":"111","createTime":1490954525,"userId":"zengxaingdoc","media":""},{"id":"336c472fc71c4ec8a5dc30f8234930dc","inquiryId":"f8f2c8415ccf4e4792088125cf85b752","contentType":1,"content":"6666","createTime":1490954562,"userId":"zengxiangtest","media":""}]
     * message : 执行成功
     * void : null
     * fileServer : http://sgdx.gui66.com
     * time : 1491528191
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
         * id : cf486b0376c44cf4bf41b2d0a9391844
         * inquiryId : f8f2c8415ccf4e4792088125cf85b752
         * contentType : 1
         * content : 111
         * createTime : 1490952684
         * userId : zengxaingdoc
         * media :
         */

        private String id;
        private String inquiryId;
        private int contentType;
        private String content;
        private int createTime;
        private String userId;
        private String userName;
        private String userFace;
        private String media;
        private int mediaTime;

        public int getMediaTime() {
            return mediaTime;
        }

        public void setMediaTime(int mediaTime) {
            this.mediaTime = mediaTime;
        }

        public DataBean(String id, String media, String userFace, String userName, String userId, int createTime, String content, String inquiryId, int contentType) {
            this.id = id;
            this.media = media;
            this.userFace = userFace;
            this.userName = userName;
            this.userId = userId;
            this.createTime = createTime;
            this.content = content;
            this.inquiryId = inquiryId;
            this.contentType = contentType;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInquiryId() {
            return inquiryId;
        }

        public void setInquiryId(String inquiryId) {
            this.inquiryId = inquiryId;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }
}
