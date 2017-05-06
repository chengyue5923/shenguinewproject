package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public class InquiryItemBean {


    /**
     * data : {"inquiryId":"f8f2c8415ccf4e4792088125cf85b752","type":1,"contentType":2,"title":"我家的龟龟得了白眼病怎么办我家的龟龟得了白眼病怎么办我家的龟龟得了白眼病怎么办","intro":"Hhhhhh","price":0,"userId":"zengxiangtest","doctor":"zengxaingdoc","views":59,"status":3,"isAsk":2,"createTime":1490951191,"files":["/file/inquiry/0975783b84b04cbb84c5025af1efae3f.jpg","/file/inquiry/ea2047b3fc214eed99a2d71b516e927f.jpg"],"buy":0,"fav":1}
     * message : 执行成功
     * void : null
     * fileServer : http://sgdx.gui66.com
     * time : 1491545930
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
         * inquiryId : f8f2c8415ccf4e4792088125cf85b752
         * type : 1
         * contentType : 2
         * title : 我家的龟龟得了白眼病怎么办我家的龟龟得了白眼病怎么办我家的龟龟得了白眼病怎么办
         * intro : Hhhhhh
         * price : 0
         * userId : zengxiangtest
         * doctor : zengxaingdoc
         * views : 59
         * status : 3
         * isAsk : 2
         * createTime : 1490951191
         * files : ["/file/inquiry/0975783b84b04cbb84c5025af1efae3f.jpg","/file/inquiry/ea2047b3fc214eed99a2d71b516e927f.jpg"]
         * buy : 0
         * fav : 1
         */

        private String inquiryId;
        private int type;
        private int contentType;
        private String title;
        private String intro;
        private int price;
        private String userId;
        private String userName;
        private String userFace;
        private String doctor;
        private int views;
        private int status;
        private int isAsk;
        private int createTime;
        private int buy;
        private int fav;
        private List<String> files;

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

        public String getInquiryId() {
            return inquiryId;
        }

        public void setInquiryId(String inquiryId) {
            this.inquiryId = inquiryId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIsAsk() {
            return isAsk;
        }

        public void setIsAsk(int isAsk) {
            this.isAsk = isAsk;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getBuy() {
            return buy;
        }

        public void setBuy(int buy) {
            this.buy = buy;
        }

        public int getFav() {
            return fav;
        }

        public void setFav(int fav) {
            this.fav = fav;
        }

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }
    }
}
