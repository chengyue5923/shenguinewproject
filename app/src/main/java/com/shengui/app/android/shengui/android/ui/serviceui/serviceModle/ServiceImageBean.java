package com.shengui.app.android.shengui.android.ui.serviceui.serviceModle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class ServiceImageBean {


    /**
     * data : [{"id":"8991cf2ad0944888a028094185c027b4","host":"048357cd34504218b6ad78c35e861e45","memo":"开讲了","path":"/file/appserver/1492267278.jpg","type":"4"},{"id":"6e2be58fed134701a5d55bb2b157525a","host":"e8be9ee2e4034554b4bf3d8c00906dce","memo":"测试","path":"/file/appserver/1492267810.jpg","type":"4"}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492334390
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
         * id : 8991cf2ad0944888a028094185c027b4
         * host : 048357cd34504218b6ad78c35e861e45
         * memo : 开讲了
         * path : /file/appserver/1492267278.jpg
         * type : 4
         */

        private String id;
        private String host;
        private String memo;
        private String path;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
