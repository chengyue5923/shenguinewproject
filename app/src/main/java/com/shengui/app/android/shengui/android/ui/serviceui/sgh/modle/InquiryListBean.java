package com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class InquiryListBean {

    /**
     * data : [{"inquiryId":"c2eb9bbcef6e4f078ae266b8ef3b2b97","type":1,"contentType":2,"intro":"哦哦哦哦哦","price":1,"doctor":"zengxaingdoc","userId":"zengxiangtest","status":1,"views":0,"createTime":1490939623,"files":["/file/inquiry/b3a4cf18e7fd4ee4b0e8598a479b65b6.jpg"]},{"inquiryId":"6bea4febf88c4958b2226e237862591e","type":1,"contentType":2,"intro":"66","price":1,"doctor":null,"userId":"zengxiangtest","status":0,"views":81,"createTime":1490861579,"files":["/file/inquiry/707e92a8c0bd42c681ba72dc083d9c74.jpg","/file/inquiry/9b29b67f6a92462a8006fac5edb45be8.jpg"]},{"inquiryId":"048357cd34504218b6ad78c35e861e45","type":1,"contentType":1,"intro":"6666666666666666666666666","price":1,"doctor":null,"userId":"zengxiangtest","status":0,"views":8,"createTime":1490855134,"files":null},{"inquiryId":"c12a0d478df74c5281d2af21c649080a","type":1,"contentType":2,"intro":"66666666dhhkljkfjlghkjldfhgdfjklghdjklfhkmdfnmfdnfnflkmnfhnfhnmdfnmdmkdlnfgdkmofndfkmohdkmohdmkofhmkodfmgodmofhmoidfmophimopdfgho,do,fights,opfhop,dog,phosphate,fhdipfoghdfhdihgdfhidfdg24tesdfgsjidgjspodgjsjkldgkjsdjkgskjldglkjgksjkgkjsjkgkjdglksdjkfgldjsgjlksdjkgjkldsgjkjkg;Kalgan;sjkdgjkkhgsfjksdjkgsdkjlgjklsdfgjklsdkljgklsjdfgjklskjldgjklsdjkgkljsdgkjlskjgjksdkjgskjdgk;sdkjgkjsjgksjkgejklgkjlklj2523","price":1,"doctor":"","userId":"zengxiangtest","status":0,"views":25,"createTime":1490854628,"files":["/file/inquiry/7b1241b71e1247148ff7bfb7740069db.jpg","/file/inquiry/6a9589a876b34e769e0addda83c9f176.jpg","/file/inquiry/98e6163d074f47a8b5d48859bf979201.jpg","/file/inquiry/ecb4da55b97146d8b47a831e5e8e36cc.jpg"]},{"inquiryId":"403b54f924a544919c06960b86375887","type":1,"contentType":2,"intro":"123132","price":1,"doctor":null,"userId":"zengxiangtest","status":0,"views":12,"createTime":1490794934,"files":["/file/inquiry/a3c83ad329b04e9e93cd67d6f370e472.jpg","/file/inquiry/2df3699fc93543499ea8d4421b0371e1.jpg","/file/inquiry/cff984a1a1f04cc7b80c7ebeef0a645a.jpg","/file/inquiry/433d2a7923364748a37215804e4977a1.jpg","/file/inquiry/fb2e4db5d5a348e481ce8734d2b8e415.jpg"]}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.129/
     * time : 1490950262
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
         * inquiryId : c2eb9bbcef6e4f078ae266b8ef3b2b97
         * type : 1
         * contentType : 2
         * intro : 哦哦哦哦哦
         * price : 1
         * doctor : zengxaingdoc
         * userId : zengxiangtest
         * status : 1
         * views : 0
         * createTime : 1490939623
         * files : ["/file/inquiry/b3a4cf18e7fd4ee4b0e8598a479b65b6.jpg"]
         */

        private String id;
        private int type;
        private int contentType;
        private String title;
        private int price;
        private String doctor;
        private String userId;
        private String doctorName;
        private String doctorImage;
        private String userName;
        private String userImage;
        private int status;
        private int views;
        private int createTime;
        private List<String> files;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getDoctorImage() {
            return doctorImage;
        }

        public void setDoctorImage(String doctorImage) {
            this.doctorImage = doctorImage;
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

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }
    }
}
//    private String doctorName;
//    private String doctorImage;
//    private String userName;
//    private String userImage;