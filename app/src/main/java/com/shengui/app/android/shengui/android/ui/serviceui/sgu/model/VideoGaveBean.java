package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */

public class VideoGaveBean {

    /**
     * message : 执行成功
     * imageServer : http://192.168.1.129/image.server
     * time : 1490316787
     * courseServer : http://192.168.1.129
     * status : 1
     * data : [{"id":"20170318101917465740282451522228","objectId":"23453","objectType":11,"userId":"zengxiangtest","count":1,"price":1,"createTime":1489803557,"status":2,"memo":null},{"id":"20170318101648403325487732116242","objectId":"23453","objectType":11,"userId":"zengxiangtest","count":1,"price":1,"createTime":1489803408,"status":2,"memo":null}]
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
         * id : 20170318101917465740282451522228
         * objectId : 23453
         * objectType : 11
         * userId : zengxiangtest
         * count : 1
         * price : 1
         * createTime : 1489803557
         * status : 2
         * memo : null
         */

        private String id;
        private String objectId;
        private int objectType;
        private String userId;
        private int count;
        private int price;
        private int createTime;
        private int status;
        private Object memo;
        private String userName;
        private String userImage;

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

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public int getObjectType() {
            return objectType;
        }

        public void setObjectType(int objectType) {
            this.objectType = objectType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getMemo() {
            return memo;
        }

        public void setMemo(Object memo) {
            this.memo = memo;
        }
    }
}
