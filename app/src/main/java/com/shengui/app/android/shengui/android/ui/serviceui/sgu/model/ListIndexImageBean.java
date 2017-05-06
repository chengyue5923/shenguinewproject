package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ListIndexImageBean {

    /**
     * data : [{"id":"54b5b390465641dd99ba50f6be0b7252","host":"http://www.gui66.com/","memo":"描述1","path":"/file/course/index/1489285840.jpg"},{"id":"50006b1ce3914ab4bfeaf074fc740641","host":"http://www.gui66.com/","memo":"描述2","path":"/file/course/index/1489285867.jpg"},{"id":"9cd7890b95124526906ea16186acd173","host":"http://www.gui66.com/","memo":"描述3","path":"/file/course/index/1489285878.jpg"},{"id":"8fa8b356445049ecaf99ee40f799639b","host":"123123","memo":"123123","path":"/file/course/index/1490607644.jpg","type":"2"},{"id":"8d960912672740ccb02994dec6aee778","host":"12","memo":"123","path":"/file/course/index/1491618523.jpg","type":"2"}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.129/
     * time : 1491965972
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
         * id : 54b5b390465641dd99ba50f6be0b7252
         * host : http://www.gui66.com/
         * memo : 描述1
         * path : /file/course/index/1489285840.jpg
         * type : 2
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
