package com.shengui.app.android.shengui.android.ui.serviceui.sgu.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class VideoListViewBean {

    /**
     * message : 执行成功
     * imageServer : http://192.168.1.129/image.server
     * time : 1489549116
     * courseServer : http://192.168.1.129
     * status : 1
     * data : [{"courseId":"23453","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00010","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"3","teacher":"11","price":3,"views":76,"comments":22,"publishTime":1483203661},{"courseId":"2","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"执行效率比完全生成HTML标记的CGI要高许多；PHP还可以执行编译后代码，编译可以达到加密和优化代码运行，使代码运行更快","teacher":"5346589759ab49ec9e49f0274c812d9f","price":22,"views":842,"comments":195,"publishTime":1483203661},{"courseId":"33451","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00019","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"11","teacher":"20","price":11,"views":21,"comments":5,"publishTime":1483203661},{"courseId":"23452","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d0008","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"22","teacher":"9","price":22,"views":5,"comments":2,"publishTime":1473203661},{"courseId":"23451","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d0002","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"11","teacher":"5346589759ab49ec9e49f0274c812d9f","price":11,"views":10,"comments":1,"publishTime":1453203661},{"courseId":"234523","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d0009","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"23","teacher":"10","price":0,"views":1,"comments":0,"publishTime":1493203661},{"courseId":"3","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00018","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"3","teacher":"19","price":3,"views":5,"comments":3,"publishTime":1483203661},{"courseId":"23454","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00011","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"4","teacher":"12","price":0,"views":1,"comments":0,"publishTime":1483203661},{"courseId":"23455","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00012","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"5","teacher":"13","price":5,"views":1,"comments":0,"publishTime":1483203661},{"courseId":"23456","name":"神龟讲座第一期活动主题\u201c养龟与疾病防治\u201d00014","cover":"/file/course/1172435671f3c538b5l.jpg","intro":"6","teacher":"15","price":6,"views":0,"comments":0,"publishTime":1483203661}]
     * void : null
     */

    private String message;
    private String imageServer;
    private int time;
    private String courseServer;
    private int status;

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
         * courseId : 23453
         * name : 神龟讲座第一期活动主题“养龟与疾病防治”00010
         * cover : /file/course/1172435671f3c538b5l.jpg
         * intro : 3
         * teacher : 11
         * price : 3
         * views : 76
         * comments : 22
         * publishTime : 1483203661
         */

        private String id;
        private String name;
        private String cover;
        private String intro;
        private String teacher;
        private String teacherName;
        private String teacherFace;
        private int price;
        private int views;
        private int comments;
        private int publishTime;

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherFace() {
            return teacherFace;
        }

        public void setTeacherFace(String teacherFace) {
            this.teacherFace = teacherFace;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public int getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(int publishTime) {
            this.publishTime = publishTime;
        }
    }
}
