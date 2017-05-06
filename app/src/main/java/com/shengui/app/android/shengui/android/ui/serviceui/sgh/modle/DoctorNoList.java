package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class DoctorNoList {

    /**
     * data : [{"inquiryId":"b1fb51db10064d7fa51688821ba82b21","type":1,"contentType":2,"title":"我的龟龟能不能吃啊","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":0,"views":0,"createTime":1491892739,"files":["/file/inquiry/19dfab23311e40abb2dafa22807c2c84.png"],"buy":0},{"inquiryId":"6772087e32544f8d83a940f2c858d19a","type":1,"contentType":2,"title":"哦哦哦","price":0,"doctor":null,"userId":"zengxiangtest1111","isAsk":null,"status":0,"views":2,"createTime":1491634015,"files":["/file/inquiry/e35ace21306149aa994b490ef3fca01d.jpg","/file/inquiry/f424d0cdc9ed44f8a3250264aab1a529.jpg"],"buy":0}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.129/
     * time : 1491892840
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
         * inquiryId : b1fb51db10064d7fa51688821ba82b21
         * type : 1
         * contentType : 2
         * title : 我的龟龟能不能吃啊
         * price : 0
         * doctor : null
         * userId : zengxiangtest
         * isAsk : null
         * status : 0
         * views : 0
         * createTime : 1491892739
         * files : ["/file/inquiry/19dfab23311e40abb2dafa22807c2c84.png"]
         * buy : 0
         */

        private String id;
        private int type;
        private int contentType;
        private String title;
        private int price;
        private String doctor;
        private String userId;
        private String userName;
        private String userFace;
        private Object isAsk;
        private int status;
        private int views;
        private int createTime;
        private int buy;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getIsAsk() {
            return isAsk;
        }

        public void setIsAsk(Object isAsk) {
            this.isAsk = isAsk;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
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

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }
    }
}
