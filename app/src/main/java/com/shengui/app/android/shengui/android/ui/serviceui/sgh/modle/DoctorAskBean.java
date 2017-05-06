package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */

public class DoctorAskBean {

    /**
     * data : [{"id":"2d990d0c48ce4c6a8d18d270cc8348ad","type":1,"contentType":2,"title":"。。。","price":0,"doctor":"13","userId":"48","isAsk":2,"status":1,"views":67,"createTime":1492141955,"files":["/file/inquiry/677785278a334e5b9f6db0146b5b1059.jpg"],"buy":0}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492243872
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
         * id : 2d990d0c48ce4c6a8d18d270cc8348ad
         * type : 1
         * contentType : 2
         * title : 。。。
         * price : 0
         * doctor : 13
         * userId : 48
         * isAsk : 2
         * status : 1
         * views : 67
         * createTime : 1492141955
         * files : ["/file/inquiry/677785278a334e5b9f6db0146b5b1059.jpg"]
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


        private int isAsk;
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

        public int getIsAsk() {
            return isAsk;
        }

        public void setIsAsk(int isAsk) {
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
