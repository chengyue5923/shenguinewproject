package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/7.
 */

public class UserQuestionListBean {

    /**
     * data : [{"inquiryId":"23c894ebb9e84e6da029e826435b4370","type":1,"contentType":3,"title":"怕吧\n","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":0,"views":0,"createTime":1491473045,"files":["/file/inquiry/440b06c8678147ec9bac3fb07830f784.mov"],"buy":0},{"inquiryId":"17a87d69d3ac40e6ab03aed7e567006d","type":1,"contentType":3,"title":"！啦啦啦啦啦","price":0,"doctor":"zengxaingdoc","userId":"zengxiangtest","isAsk":null,"status":1,"views":5,"createTime":1491361640,"files":["/file/inquiry/0912b61929704b51a61741b3ae1982d2.mov"],"buy":0},{"inquiryId":"542daf464728416181b52acd5b245384","type":1,"contentType":3,"title":"哦哦哦","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":0,"views":1,"createTime":1491361587,"files":["/file/inquiry/1d8f6d0289e9469699fafc47c601ed6c.mov"],"buy":0},{"inquiryId":"89e79527a1a548e999c27d1f307ed450","type":1,"contentType":2,"title":"墨迹","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":0,"views":0,"createTime":1491361569,"files":["/file/inquiry/7e73cc45830a458eaa77a5e84b7bd8ba.jpg","/file/inquiry/8ccbc07a8f88437abba88449141606fc.jpg"],"buy":0},{"inquiryId":"0585309afefe4bf1b71ea198a5cf5612","type":1,"contentType":1,"title":"啦啦啦啦","price":0,"doctor":"zengxaingdoc","userId":"zengxiangtest","isAsk":1,"status":2,"views":4,"createTime":1491361557,"files":null,"buy":0},{"inquiryId":"596d2504c77f4ff183c6d5494b86cdba","type":1,"contentType":1,"title":"默默","price":0,"doctor":"zengxaingdoc","userId":"zengxiangtest","isAsk":1,"status":2,"views":9,"createTime":1491361241,"files":null,"buy":0},{"inquiryId":"b1463076e1d94940a7428b929717485b","type":1,"contentType":1,"title":"12313","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":9,"views":5,"createTime":1491355785,"files":null,"buy":0},{"inquiryId":"0805cd35a82842c28a0349b59d4be112","type":1,"contentType":1,"title":"6777777777","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":9,"views":2,"createTime":1491355519,"files":null,"buy":0},{"inquiryId":"2a74bf39769740cda61d230a309334fd","type":1,"contentType":3,"title":"我的龟的狂犬病了怎么办","price":0,"doctor":"zengxaingdoc","userId":"zengxiangtest","isAsk":2,"status":2,"views":16,"createTime":1491040065,"files":["/file/inquiry/44d8e30e93454ae1b4c4a95833673edb.mov"],"buy":0},{"inquiryId":"16db0789549949b7999d3dfe11c6d6ca","type":1,"contentType":3,"title":"666","price":0,"doctor":null,"userId":"zengxiangtest","isAsk":null,"status":9,"views":7,"createTime":1491038908,"files":["/file/inquiry/71a4259fdee646bcbe04d8b3d4b3364a.mov"],"buy":0}]
     * message : 执行成功
     * void : null
     * fileServer : http://sgdx.gui66.com
     * time : 1491554001
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
         * inquiryId : 23c894ebb9e84e6da029e826435b4370
         * type : 1
         * contentType : 3
         * title : 怕吧

         * price : 0
         * doctor : null
         * userId : zengxiangtest
         * isAsk : null
         * status : 0
         * views : 0
         * createTime : 1491473045
         * files : ["/file/inquiry/440b06c8678147ec9bac3fb07830f784.mov"]
         * buy : 0
         */

        private String id;
        private int type;
        private int contentType;
        private String title;
        private int price;
        private String doctor;
        private String userId;
        private String doctorName;
        private String doctorFace;
        private String userName;
        private String userFace;
        private int isAsk;
        private int status;
        private int views;
        private int createTime;
        private int buy;
        private List<String> files;

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getUserFace() {
            return userFace;
        }

        public void setUserFace(String userFace) {
            this.userFace = userFace;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDoctorFace() {
            return doctorFace;
        }

        public void setDoctorFace(String doctorFace) {
            this.doctorFace = doctorFace;
        }

        public String getId() {
            return id;
        }

        public void setId(String inquiryId) {
            this.id = inquiryId;
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
