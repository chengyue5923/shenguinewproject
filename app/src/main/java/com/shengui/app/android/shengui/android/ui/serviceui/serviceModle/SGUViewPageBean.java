package com.shengui.app.android.shengui.android.ui.serviceui.serviceModle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/4/16.
 */

public class SGUViewPageBean {

    /**
     * data : [{"id":"3e7f8239cec5410b8982a2704f9b1b23","title":"巴西龟开食了","memo":"巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴","objectId":"00605ee259a644ab9388d7bcb3d1bbad","path":"/file/appserver/1492268031.jpg","objectType":"COURSE"},{"id":"1252a1cab6fd45ab804f0ab81e4353a3","title":"草莓遇上龟","memo":"遇到这种龟千万别帮它摘掉背上野草不然你会后悔的","objectId":"ba79a95686184ba7b6adb778cfe94869","path":"/file/appserver/1492268101.jpg","objectType":"COURSE"},{"id":"3b119b0b3c0f457d863437fb2ae0dffc","title":"大象和龟干的一些事","memo":"大象和龟的一些不能说的秘密","objectId":"08349e479d7d4752b0568ab4457d2721","path":"/file/appserver/1492268229.jpg","objectType":"COURSE"}]
     * message : 执行成功
     * void : null
     * fileServer : http://192.168.1.128:8080
     * time : 1492329173
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
         * id : 3e7f8239cec5410b8982a2704f9b1b23
         * title : 巴西龟开食了
         * memo : 巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴西龟开食了巴
         * objectId : 00605ee259a644ab9388d7bcb3d1bbad
         * path : /file/appserver/1492268031.jpg
         * objectType : COURSE
         */

        private String id;
        private String title;
        private String memo;
        private String objectId;
        private String path;
        private String objectType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getObjectType() {
            return objectType;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }
    }
}
