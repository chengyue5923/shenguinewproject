package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/3/25.
 */

public class VideoBean {


    /**
     * message : 执行成功
     * imageServer : http://sgdx.gui66.com
     * time : 1490757574
     * courseServer : http://sgdx.gui66.com
     * status : 1
     * data : {"courseId":"23453","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00010","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"3","teacher":"5346589759ab49ec9e49f0274c812d9f","publishTime":1483203661,"price":1,"views":231,"type":1,"comments":67,"dir":"/file/course/video/test.mp4","buy":2,"fav":0}
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;
    private DataBean data;
    @SerializedName("void")
    private Object voidX;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getVoidX() {
        return voidX;
    }

    public void setVoidX(Object voidX) {
        this.voidX = voidX;
    }

    public static class DataBean {
        /**
         * courseId : 23453
         * name : 神龟讲座第一期活动主题“养龟与疾病防治”00010
         * cover : /file/course/1172435671f3c538b5l.jpg
         * intro : 3
         * teacher : 5346589759ab49ec9e49f0274c812d9f
         * publishTime : 1483203661
         * price : 1
         * views : 231
         * type : 1
         * comments : 67
         * dir : /file/course/video/test.mp4
         * buy : 2
         * fav : 0
         */

        private String courseId;
        private String name;
        private String cover;
        private String intro;
        private String teacher;
        private String teacherFace;
        private String teacherName;
        private int publishTime;
        private long price;
        private int views;
        private int type;
        private int comments;
        private String dir;
        private int buy;
        private int fav;

        public String getTeacherFace() {
            return teacherFace;
        }

        public void setTeacherFace(String teacherFace) {
            this.teacherFace = teacherFace;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
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
    }
}
