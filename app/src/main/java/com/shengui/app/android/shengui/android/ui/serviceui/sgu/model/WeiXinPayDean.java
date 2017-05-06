package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/4/15.
 */

public class WeiXinPayDean {

    /**
     * data : {"appid":"wx15bb4b5149c1cbd2","noncestr":"Pxt1YoVEKhbIfdNM","package":"Sign=WXPay","partnerid":"1254371101","prepayid":"wx20170415231715a8b218df080677983777","sign":"D031828C2438348135DC9088B8B3D26D","timestamp":1492269479}
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492269479
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
         * appid : wx15bb4b5149c1cbd2
         * noncestr : Pxt1YoVEKhbIfdNM
         * package : Sign=WXPay
         * partnerid : 1254371101
         * prepayid : wx20170415231715a8b218df080677983777
         * sign : D031828C2438348135DC9088B8B3D26D
         * timestamp : 1492269479
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private int timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
