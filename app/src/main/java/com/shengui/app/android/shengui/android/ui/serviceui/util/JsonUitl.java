package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.BaiduWeatherBean;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.ReminderBean;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.SGUViewPageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.ServiceImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.AllcommentsBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.CommentsCallBackBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.ListIndexImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.MyMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.NoResultBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeacherDataBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeachersTeamBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.UserBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.UserStatus;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoGaveBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoPublishCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;
import com.shengui.app.android.shengui.db.UserPreference;


import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;


/**
 * Created by Administrator on 2017/3/15.
 */

public class JsonUitl {

    public static List<ListIndexImageBean.DataBean> listIndexImage(Context context) {
//        http://192.168.1.129/api/course/listIndexImage.json

        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/listIndexImage.json", context);

        ListIndexImageBean listIndexImageBean = null;
        try {

            listIndexImageBean = new Gson().fromJson(json, ListIndexImageBean.class);

        } catch (JsonSyntaxException e) {

            e.printStackTrace();

        }
        List<ListIndexImageBean.DataBean> data = listIndexImageBean.getData();


        return data;

    }

    private static List requestUserNameAndImage(List data, Context context, int position) {
        List l = new ArrayList();

        if (position == 1) {
            data = videoComentUserData(data, context, l);
        } else if (position == 2) {
            data = VideoGaveUserData(data, context, l);
        } else if (position == 3) {
            data = VideoListViewUserData(data, context, l);
        } else if (position == 4) {
            data = allCommentsUserData(data, context, l);
        }

        return data;
    }

    public static List<UserBean.DataBean> getUserDataBeen(Context context, List l) {
        String id = null;
        for (int i = 0; i < l.size(); i++) {
            if (id == null) {
                id = (String) l.get(i);
            } else {
                id = id + "," + l.get(i);
            }

        }

        Log.e("test", "getUserDataBeen: " + id);

        String userJson = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/user/ping.user.json?u=" + id, context);
        Log.e("test", "getUserDataBeen: " + Api.baseUrl + "/api/user/ping.user.json?u=" + id);
        UserBean userBean = null;
        Log.e("test", "getUserDataBeen: " + userJson);
        try {
            userBean = new Gson().fromJson(userJson, UserBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return userBean.getData();
    }

    private static List<AllcommentsBean.DataBean> allCommentsUserData(List<AllcommentsBean.DataBean> data, Context context, List l) {

        for (int i = 0; i < data.size(); i++) {
            AllcommentsBean.DataBean dataBean = data.get(i);
            String userId = dataBean.getUserId();


            if (!l.contains(userId)) {
                l.add(userId);
            }
        }

        for (int i = 0; i < data.size(); i++) {
            AllcommentsBean.DataBean dataBean = data.get(i);
            String receiverId = dataBean.getReceiver();


            if (!l.contains(receiverId)) {
                l.add(receiverId);
            }
        }


        List<UserBean.DataBean> userData = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userData.size(); j++) {
                if (data.get(i).getUserId().equals(userData.get(j).getUserId())) {
                    data.get(i).setUserRealName(userData.get(j).getRealName());
                    data.get(i).setUserImage(userData.get(j).getFace());
                }
                if (data.get(i).getReceiver().equals(userData.get(j).getUserId())) {
                    data.get(i).setReceiverRealName(userData.get(j).getRealName());
                    data.get(i).setReceiverImage(userData.get(j).getFace());
                }
            }
        }

        return data;
    }

    private static List<VideoListViewBean.DataBean> VideoListViewUserData(List<VideoListViewBean.DataBean> data, Context context, List l) {
        for (int i = 0; i < data.size(); i++) {

            VideoListViewBean.DataBean dataBean = (data.get(i));
            String teacherId = dataBean.getTeacher();


            if (!l.contains(teacherId)) {
                l.add(teacherId);
            }
        }

        List<UserBean.DataBean> userData = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userData.size(); j++) {
                if (data.get(i).getTeacher().equals(userData.get(j).getUserId())) {
                    data.get(i).setTeacherName(userData.get(j).getRealName());
                    data.get(i).setTeacherFace(userData.get(j).getFace());
                }
            }
        }

        return data;
    }


    private static List<VideoGaveBean.DataBean> VideoGaveUserData(List<VideoGaveBean.DataBean> data, Context context, List l) {

        for (int i = 0; i < data.size(); i++) {
            VideoGaveBean.DataBean dataBean = data.get(i);
            String userId = dataBean.getUserId();


            if (!l.contains(userId)) {
                l.add(userId);
            }
        }


        List<UserBean.DataBean> userData = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userData.size(); j++) {
                if (data.get(i).getUserId().equals(userData.get(j).getUserId())) {
                    data.get(i).setUserName(userData.get(j).getRealName());
                    data.get(i).setUserImage(userData.get(j).getFace());
                }
            }
        }

        return data;
    }

    private static List<VideoCommentBean.DataBeanX> videoComentUserData(List<VideoCommentBean.DataBeanX> data, Context context, List l) {
        for (int i = 0; i < data.size(); i++) {
            VideoCommentBean.DataBeanX dataBeanX = data.get(i);
            String userId = dataBeanX.getUserId();

            if (!l.contains(userId)) {
                l.add(userId);

            }

            if (dataBeanX.getData() != null) {
                for (int i1 = 0; i1 < dataBeanX.getData().size(); i1++) {
                    String user = dataBeanX.getData().get(i1).getUserId();
                    if (!l.contains(user)) {
                        l.add(user);
                    }
                    String userReceiver = dataBeanX.getData().get(i1).getReceiver();
                    if (!l.contains(userReceiver)) {
                        l.add(userReceiver);
                    }
                }
            }
        }

        List<UserBean.DataBean> userData = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userData.size(); j++) {

                if (data.get(i).getUserId().equals(userData.get(j).getUserId())) {
                    data.get(i).setUserName(userData.get(j).getRealName());
                    data.get(i).setUserImage(userData.get(j).getFace());
                }

                if (data.get(i).getData() != null) {
                    for (int k = 0; k < data.get(i).getData().size(); k++) {
                        if (data.get(i).getData().get(k).getUserId().equals(userData.get(j).getUserId())) {
                            data.get(i).getData().get(k).setUserName(userData.get(j).getRealName());
                            data.get(i).getData().get(k).setUserImage(userData.get(j).getFace());
                        }
                        if (data.get(i).getData().get(k).getReceiver().equals(userData.get(j).getUserId())) {
                            data.get(i).getData().get(k).setReceiverName(userData.get(j).getRealName());
                            data.get(i).getData().get(k).setReceiverImage(userData.get(j).getFace());
                        }
                    }
                }
            }

        }

        return data;
    }

    public static List<VideoListViewBean.DataBean> videoListViewClassify(Context context, int classify) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/list.json?courseType=" + classify, context);

        VideoListViewBean videoListViewAllBean = null;
        try {

            videoListViewAllBean = new Gson().fromJson(json, VideoListViewBean.class);

        } catch (JsonSyntaxException e) {

            e.printStackTrace();

        }
        List<VideoListViewBean.DataBean> data = videoListViewAllBean.getData();

        List<VideoListViewBean.DataBean> dataBeen = requestUserNameAndImage(data, context, 3);

        return dataBeen;

    }

    public static List<VideoListViewBean.DataBean> videoListViewAll(Context context) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/list.json", context);

        Log.e("test", "videoListViewAll: " + json);
        VideoListViewBean videoListViewAllBean = null;
        try {

            videoListViewAllBean = new Gson().fromJson(json, VideoListViewBean.class);

        } catch (JsonSyntaxException e) {

            e.printStackTrace();

        }
        List<VideoListViewBean.DataBean> data = videoListViewAllBean.getData();

        List<VideoListViewBean.DataBean> dataBeen = requestUserNameAndImage(data, context, 3);

        return dataBeen;

    }

    public static List<VideoListViewBean.DataBean> videoListViewTui(Context context) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/listtui.json", context);

        Log.e("test", "videoListViewTui: " + json);

        VideoListViewBean videoListViewTuiBean = null;


        videoListViewTuiBean = new Gson().fromJson(json, VideoListViewBean.class);


        List<VideoListViewBean.DataBean> data = videoListViewTuiBean.getData();


        return data;
    }


    public static List<TeachersTeamBean.DataBean> teachersTeam(Context context) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/user/list-teacher.json", context);


        Log.e("test", "teachersTeam: " + json);
        TeachersTeamBean teachersTeamBean = null;

        try {
            teachersTeamBean = new Gson().fromJson(json, TeachersTeamBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<TeachersTeamBean.DataBean> data = teachersTeamBean.getData();

        return data;
    }

    public static TeacherDataBean.DataBean UserData(Context context, String userId, int type) {
        //     http://192.168.1.129/api/user/item.json
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/user/item.json?userId=" + userId + "&userType=" + type, context);

        TeacherDataBean teacherDataBean = null;

        Log.e("test", "UserData: " + json);

        try {
            teacherDataBean = new Gson().fromJson(json, TeacherDataBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        TeacherDataBean.DataBean data = teacherDataBean.getData();

        return data;
    }


    public static List<VideoListViewBean.DataBean> searchResult(Context context, String keyword) {

        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/list.json?keyWord=" + keyword, context);

        Log.e("test", "searchResult: "+json);

        VideoListViewBean videoListViewTuiBean = null;

        try {
            videoListViewTuiBean = new Gson().fromJson(json, VideoListViewBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<VideoListViewBean.DataBean> data = videoListViewTuiBean.getData();
        List<VideoListViewBean.DataBean> dataBeen = requestUserNameAndImage(data, context, 3);

        return dataBeen;

    }


    public static VideoBean.DataBean videoMsg(Context context, String videoId) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/item.json?courseId=" + videoId, context);

        Log.e("test", "videoMsg: " + json + Api.baseUrl + "/api/course/item.json?courseId=" + videoId);
        VideoBean videoBean = null;

        try {
            videoBean = new Gson().fromJson(json, VideoBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<String> l = new ArrayList<>();

        VideoBean.DataBean data = videoBean.getData();

        l.add(data.getTeacher());

        List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);
        if (userDataBeen.size() > 0) {
            data.setTeacherName(userDataBeen.get(0).getRealName());
            data.setTeacherFace(userDataBeen.get(0).getFace());
        }

        return data;

    }


    public static List<VideoCommentBean.DataBeanX> videoComment(Context context, String videoId) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/comments/list.json?courseId=" + videoId, context);

        Log.e("test", "videoComment: " + json);
        VideoCommentBean videoCommentBean = null;

        try {
            videoCommentBean = new Gson().fromJson(json, VideoCommentBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<VideoCommentBean.DataBeanX> data = videoCommentBean.getData();


        List<VideoCommentBean.DataBeanX> dataBeanXes = requestUserNameAndImage(data, context, 1);

        return dataBeanXes;

    }

    public static VideoPublishCommentBean videoPublishComment(Context context, FormBody formBody) {
        //http://192.168.1.129/api/course/comments/save.json "courseId：视频ID；content：评论内容；"


        String json = HttpUtil.postStringByOkHttp(context, Api.baseUrl + "/api/course/comments/save.json", formBody);

        Log.e("test", "videoPublishComment: " + json);

        VideoPublishCommentBean videoPublishCommentBean = null;

        videoPublishCommentBean = new Gson().fromJson(json, VideoPublishCommentBean.class);


        return videoPublishCommentBean;


    }


    public static List<VideoGaveBean.DataBean> videoGave(Context context, String videoId) {
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/course/rewards.json?courseId=" + videoId, context);

        VideoGaveBean videoGaveBean = null;

        try {
            videoGaveBean = new Gson().fromJson(json, VideoGaveBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<VideoGaveBean.DataBean> data = videoGaveBean.getData();

        data = requestUserNameAndImage(data, context, 2);

        return data;

    }


    public static boolean login(Context context, FormBody formBody) {


        String json = HttpUtil.postLoginByOkHttp(context, Api.baseUrl + "/api/user/loginUserAndPass.json", formBody);
        // （用户名和密码登录）http://192.168.1.129/api/user/loginUserAndPass.json

        Log.e("test", "login: " + json);
        UserStatus userLogin = null;

        userLogin = new Gson().fromJson(json, UserStatus.class);

       /* User.token = userLogin.getData().getToken();
        User.Login = true;
        User.userId = userLogin.getData().getUser().getUserId();
        User.userName = userLogin.getData().getUser().getName();
        User.userFace = userLogin.getData().getUser().getFace();
        User.userRealName = userLogin.getData().getUser().getRealName();
        User.userType = userLogin.getData().getUser().getUserType();*/

        return false;
    }

    public static void userStatus(Context context) {
//        api/user/user.json
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/user/user.json", context);

        UserStatus userStatus = new Gson().fromJson(json, UserStatus.class);

        if (userStatus.getStatus() == 1) {
            Log.e("test", "userStatus: " + json + Api.baseUrl + "/api/user/user.json");
            User.userType = (int) userStatus.getData().getUserType();
            User.userFace = userStatus.getData().getAvatar();
            User.userName = userStatus.getData().getName();
            User.userRealName = userStatus.getData().getTruename();
            User.userId = userStatus.getData().getId();
            User.Login = true;
            Log.e("test", "userStatus: " + User.userType + ((int) userStatus.getData().getLastLogin()));
        }
    }

    public static List<VideoListViewBean.DataBean> myViewsData(Context context, int type, int p) {
//        "观看历史：http://192.168.1.129/api/course/views.json
//        我的收藏：http://192.168.1.129/api/course/fav.json
//        购买记录：http://192.168.1.129/api/course/buys.json"

        String url = "";
        if (type == 0) {
            url = Api.baseUrl + "/api/course/fav.json?p=" + 1;
        } else if (type == 1) {
            url = Api.baseUrl + "/api/course/views.json?p=" + 1;
        } else if (type == 2) {
            url = Api.baseUrl + "/api/course/buys.json?p=" + 1;
        }

        String json = HttpUtil.getStringByOkHttp(url, context);
        VideoListViewBean videoListViewBean = null;
        try {
            videoListViewBean = new Gson().fromJson(json, VideoListViewBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<VideoListViewBean.DataBean> data = videoListViewBean.getData();
        data = requestUserNameAndImage(data, context, 3);

        return data;
    }

    public static List<MyMsgBean.DataBean> myMsgData(Context context, int p) {

        //http://192.168.1.129/api/course/comments/mycomments.json

        String url = "";

        url = Api.baseUrl + "/api/course/comments/mycomments.json?p=" + p;


        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "myMsgData: "+json );
        MyMsgBean myMsgBean = null;
        try {
            myMsgBean = new Gson().fromJson(json, MyMsgBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        List<String> l = new ArrayList<>();
        List<MyMsgBean.DataBean> data = myMsgBean.getData();
        for (int i = 0; i < data.size(); i++) {
            if (!l.contains(data.get(i).getUserId())) {
                l.add(data.get(i).getUserId());
            }
            if (!l.contains(data.get(i).getReceiver())) {
                l.add(data.get(i).getUserId());
            }
        }

        List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int i1 = 0; i1 < userDataBeen.size(); i1++) {
                if (userDataBeen.get(i1).getUserId().equals(data.get(i).getUserId())) {
                    data.get(i).setUserName(userDataBeen.get(i1).getRealName());
                    data.get(i).setUserFace(userDataBeen.get(i1).getFace());
                }
                if (userDataBeen.get(i1).getUserId().equals(data.get(i).getReceiver())) {
                    data.get(i).setReceiveName(userDataBeen.get(i1).getRealName());
                    data.get(i).setReceiveFace(userDataBeen.get(i1).getFace());
                }
            }
        }

        Log.e("test", "myViewsData: " + json);


        return data;
    }

    public static boolean collectVideo(Context context, FormBody formBody, int type) {
//        "（收藏）http://192.168.1.129/api/course/saveCourseFav.json
//        （取消收藏）http://192.168.1.129/api/course/cancelCourseFav.json"
        String url = "";

        if (type == 1) {
            url = Api.baseUrl + "/api/course/saveCourseFav.json";//1 收藏
        } else {
            url = Api.baseUrl + "/api/course/cancelCourseFav.json";//2 取消收藏
        }


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据，没有表单数据请求，设为null
        Log.e("test", "collectVideo: " + json);

        NoResultBean noResultBean = null;
//        VideoListViewBean videoListViewBean = null;
        try {
            noResultBean = new Gson().fromJson(json, NoResultBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean userViewRecord(Context context, FormBody formBody) {
        String url = "";


//        http://192.168.1.129/api/course/saveCourseViews.json


        url = Api.baseUrl + "/api/course/saveCourseViews.json";//1 收藏


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据，没有表单数据请求，设为null

        Log.e("test", "userViewRecord: " + json);

        NoResultBean noResultBean = null;

        try {
            noResultBean = new Gson().fromJson(json, NoResultBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static List<AllcommentsBean.DataBean> allComment(Context context, String courseId, String pid) {
//        http://192.168.1.129/api/course/comments/list.json
//        "courseId：视频ID；
//        pid：被回复人ID
//        "
        String url = "";

        url = Api.baseUrl + "/api/course/comments/list.json?courseId=" + courseId + "&pid=" + pid + "&p=" + 1;

        String json = HttpUtil.getStringByOkHttp(url, context);

        AllcommentsBean allcommentsBean = null;
        try {
            allcommentsBean = new Gson().fromJson(json, AllcommentsBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        List<AllcommentsBean.DataBean> data = allcommentsBean.getData();

        data = requestUserNameAndImage(data, context, 4);

        return data;
    }

    public static CommentsCallBackBean.DataBean commentsCallBack(Context context, FormBody formBody) {
//        http://192.168.1.129/api/course/comments/call.json

        String url = "";

        url = Api.baseUrl + "/api/course/comments/call.json";


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        Log.e("test", "commentsCallBack: " + json);

        CommentsCallBackBean commentsCallBackBean = null;

        try {
            commentsCallBackBean = new Gson().fromJson(json, CommentsCallBackBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        CommentsCallBackBean.DataBean data = commentsCallBackBean.getData();
        data = commentsCallBackUserData(data, context);

        return data;

    }


    private static CommentsCallBackBean.DataBean commentsCallBackUserData(CommentsCallBackBean.DataBean dataBean, Context context) {
        List l = new ArrayList();

        l.add(dataBean.getUserId());
        if (!l.contains(dataBean.getReceiver())) {
            l.add(dataBean.getReceiver());
        }

        List<UserBean.DataBean> userData = getUserDataBeen(context, l);

        for (int i = 0; i < userData.size(); i++) {
            if (dataBean.getUserId().equals(userData.get(i).getUserId())) {
                dataBean.setUserRealName(userData.get(i).getRealName());
                dataBean.setUserImage(userData.get(i).getFace());
            }
            if (dataBean.getReceiver().equals(userData.get(i).getUserId())) {
                dataBean.setReceiverRealName(userData.get(i).getRealName());
            }
        }

        return dataBean;
    }

    //    http://192.168.1.129/api/pay/weixinpay.json   courseId:视频ID
    public static WeiXinPayDean weixinpay(Context context, String courseId) {
        String url = "";

        url = Api.baseUrl + "/api/pay/wxCoursePay.json";
        FormBody formBody = new FormBody.Builder()//创建表单构造器
                .add("courseId", courseId)//添加表单参数
                .add("type", 1 + "")
                .build();//生成简易表单型RequestBody

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        WeiXinPayDean weiXinPayDean = new Gson().fromJson(json, WeiXinPayDean.class);

        return weiXinPayDean;

    }

    //    http://192.168.1.129/api/pay/weixinpay.json   courseId:视频ID
    public static WeiXinPayDean weixinGave(Context context, String courseId, String price) {
        String url = "";

        url = Api.baseUrl + "/api/pay/wxCourseRewards.json";
        FormBody formBody = new FormBody.Builder()//创建表单构造器
                .add("courseId", courseId)//添加表单参数
                .add("type", 1 + "")
                .add("price", price)
                .build();//生成简易表单型RequestBody

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        Log.e("test", "onClick: " + json + courseId + price);

        WeiXinPayDean weiXinPayDean = new Gson().fromJson(json, WeiXinPayDean.class);

        return weiXinPayDean;

    }

//    http://192.168.1.129/api/pay/weixxinCheck.json

    public static Boolean weiXinCheck(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/pay/weixxinCheck.json";

        Log.e("test", "WeiXinCheck: " + url);

        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "WeiXinCheck: " + json);

        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);
        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }


    public static List<BaiduWeatherBean.ResultsBean> baiduWeather(Context context) {
//        http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=45b99ca73e2d8b370aa419fd643d5a92
        String url = "";

        url = "http://api.map.baidu.com/telematics/v3/weather?location=" + UserPreference.getCityName() + "&output=json&ak=45b99ca73e2d8b370aa419fd643d5a92";

        String json = HttpUtil.getOtherByOkHttp(url, context);
        Log.e("test", "baiduWeather: " + json);

        BaiduWeatherBean baiduWeatherBean = new Gson().fromJson(json, BaiduWeatherBean.class);
        List<BaiduWeatherBean.ResultsBean> results = baiduWeatherBean.getResults();
        if (baiduWeatherBean.getError() == 0) {
            return results;
        } else {
            return null;
        }
    }

    public static ReminderBean reminder(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/server/reminder.json";

        String json = HttpUtil.getStringByOkHttp(url, context);

        ReminderBean reminderBean = new Gson().fromJson(json, ReminderBean.class);

        if (reminderBean.getStatus() == 1) {
            return reminderBean;
        } else {
            return null;
        }


    }

    public static List<ServiceImageBean.DataBean> serviceImage(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/server/image.json ";

        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "serviceImage: " + json);

        ServiceImageBean serviceImageBean = new Gson().fromJson(json, ServiceImageBean.class);

        List<ServiceImageBean.DataBean> data = serviceImageBean.getData();

        if (serviceImageBean.getStatus() == 1) {
            return data;
        } else {
            return null;
        }

    }

    //    api/server/special.json  专题信息 参数：key  ， key=INQUIRY 为病例专题，key=COURSE，视频专题
    public static List<SGUViewPageBean.DataBean> sGUspecial(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/server/special.json?key=" + "COURSE";

        String json = HttpUtil.getStringByOkHttp(url, context);

        SGUViewPageBean sguViewPageBean = new Gson().fromJson(json, SGUViewPageBean.class);

        List<SGUViewPageBean.DataBean> data = sguViewPageBean.getData();

        if (sguViewPageBean.getStatus() == 1) {
            return data;
        } else {
            return null;
        }
    }

    public static List<SGUViewPageBean.DataBean> sGHspecial(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/server/special.json?key=" + "INQUIRY";

        String json = HttpUtil.getStringByOkHttp(url, context);

        SGUViewPageBean sguViewPageBean = new Gson().fromJson(json, SGUViewPageBean.class);

        List<SGUViewPageBean.DataBean> data = sguViewPageBean.getData();

        if (sguViewPageBean.getStatus() == 1) {
            return data;
        } else {
            return null;
        }
    }


//    "http://192.168.1.129/api/course/view/del.json（删除观看记录）
//    http://192.168.1.129/api/course/view/delAll.json（清空观看记录）"

    public static void delVideoView(Context context, FormBody formBody) {
        String url = "";

        url = Api.baseUrl + "/api/course/view/del.json";


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        Log.e("test", "delVideoView: " + json);

    }

    public static Boolean delAllVideoView(Context context) {
        String url = "";

        url = Api.baseUrl + "/api/course/view/delAll.json";
        FormBody formBody = new FormBody.Builder().build();

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        NoResultBean noResultBean = new Gson().fromJson(json,NoResultBean.class);

        if (noResultBean.getStatus()==1){
            return true;
        }else {
            return false;
        }

    }


    //    "http://192.168.1.129/api/course/order/del.json（删除购买记录）
//    http://192.168.1.129/api/course/order/delAll.json（清空购买记录）"
    public static void delVideoOrder(Context context, FormBody formBody) {
        String url = "";

        url = Api.baseUrl + "/api/course/order/del.json";

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        Log.e("test", "delVideoView: " + json);
    }

    public static boolean delAllVideoOrder(Context context) {
        String url = "";

        FormBody formBody = new FormBody.Builder().build();
        url = Api.baseUrl + "/api/course/order/delAll.json";

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        NoResultBean noResultBean = new Gson().fromJson(json,NoResultBean.class);

        if (noResultBean.getStatus()==1){
            return true;
        }else {
            return false;
        }
    }

//    http://192.168.1.129/api/course/comments/delComments.json

    public static void delComments(Context context,FormBody formBody){
        String url = "";

        url = Api.baseUrl + "/api/course/comments/delComments.json";
        Log.e("test", "delComents: " + url);

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据

        Log.e("test", "delComents: " + json);
    }

    public static TeacherDataBean.DataBean teacherDetail(Context context,String teacherId){
//        http://192.168.1.129/api/user/item.json   "userId：用户ID；
//        userType：用户类型；（1：普通注册用户，2：讲师）"
        String url = "";

        url = Api.baseUrl + "/api/user/item.json?userId=" + teacherId+"&userType="+2;

        String json = HttpUtil.getStringByOkHttp(url, context);

        TeacherDataBean teacherDataBean = new Gson().fromJson(json, TeacherDataBean.class);

        TeacherDataBean.DataBean data = teacherDataBean.getData();

        return data;
    }

}
