package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.CollectResultBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorAskBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorDateBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorDetailBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorNoList;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryItemBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.NoResultBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UserQuestionListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.ListIndexImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.UserBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SGHJsonUtil {

    public static List<ListIndexImageBean.DataBean> listIndexImage(Context context) {
//        http://192.168.1.129/api/course/listIndexImage.json  /api/inquiry/listIndexImage.json


        String json = HttpUtil.getStringByOkHttp(Api.SGHBaseUrl + "/api/inquiry/listIndexImage.json", context);

        Log.e("test", "listIndexImage: "+json);

        ListIndexImageBean listIndexImageBean = null;
        try {

            listIndexImageBean = new Gson().fromJson(json, ListIndexImageBean.class);

        } catch (JsonSyntaxException e) {

            e.printStackTrace();

        }
        List<ListIndexImageBean.DataBean> data = listIndexImageBean.getData();


        return data;

    }

    public static List<InquiryListBean.DataBean> allInquiryList(Context context, int inquiryType,int p) {
//        "keyword：关键字；api/inquiry/finishlist.json
//        inquiryType：1：用户问诊、2：官方常见病例；（可不传，取所有类型的）"
        String url = "";
        if (inquiryType != 0) {
            url = Api.SGHBaseUrl + "/api/inquiry/finishlist.json?inquiryType=" + inquiryType+"&p="+p;
        } else {
            url = Api.SGHBaseUrl + "/api/inquiry/finishlist.json?p="+p;
        }

        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "allInquiryList: " + json);
        InquiryListBean inquiryListBean = null;

        inquiryListBean = new Gson().fromJson(json, InquiryListBean.class);
        List<InquiryListBean.DataBean> data = inquiryListBean.getData();
        List l = new ArrayList();

        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserImage(userDataBeen.get(j).getFace());
                    }
                    if (data.get(i).getDoctor() == null) {
                        continue;
                    } else {
                        if (data.get(i).getDoctor().equals(userDataBeen.get(j).getUserId())) {
                            data.get(i).setDoctorName(userDataBeen.get(j).getRealName());
                            data.get(i).setDoctorImage(userDataBeen.get(j).getFace());
                        }
                    }
                }
            }
        }
        return data;
    }


    public static List<InquiryListBean.DataBean> searchInquiryList(Context context, String keyword) {
//        "keyword：关键字；api/inquiry/finishlist.json
//        inquiryType：1：用户问诊、2：官方常见病例；（可不传，取所有类型的）"
        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/finishlist.json?keyword=" + keyword+"&p="+1;

        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "searchInquiryList: " + json);
        InquiryListBean inquiryListBean = null;

        inquiryListBean = new Gson().fromJson(json, InquiryListBean.class);
        List<InquiryListBean.DataBean> data = inquiryListBean.getData();
        List l = new ArrayList();

        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserImage(userDataBeen.get(j).getFace());
                    }
                    if (data.get(i).getDoctor() == null) {
                        continue;
                    } else {
                        if (data.get(i).getDoctor().equals(userDataBeen.get(j).getUserId())) {
                            data.get(i).setDoctorName(userDataBeen.get(j).getRealName());
                            data.get(i).setDoctorImage(userDataBeen.get(j).getFace());
                        }
                    }
                }
            }
        }
        return data;
    }

    public static List<DoctorNoList.DataBean> noList(Context context,int p) {
        //        api/inquiry/nolist.json
        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/nolist.json?p="+p;

        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "noList: " + json);
        DoctorNoList doctorNoList = new Gson().fromJson(json, DoctorNoList.class);
        List<DoctorNoList.DataBean> data = doctorNoList.getData();

        List l = new ArrayList();

        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }

            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserFace(userDataBeen.get(j).getFace());
                    }
                }
            }
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

        String userJson = HttpUtil.getStringByOkHttp(Api.SGHBaseUrl + "/api/user/ping.user.json?u=" + id, context);
        Log.e("test", "getUserDataBeen: " + userJson);

        UserBean userBean = null;

        try {
            userBean = new Gson().fromJson(userJson, UserBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return userBean.getData();
    }

    public static InquiryItemBean.DataBean inquiryItem(Context context, String inquiryId) {

//        "api/inquiry/item.json（问诊信息）
//        api/inquiry/comments/list.json（问答信息）"

        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/item.json?inquiryId=" + inquiryId;

        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "inquiryItem: " + json + url);

        InquiryItemBean inquiryItemBean = null;

        inquiryItemBean = new Gson().fromJson(json, InquiryItemBean.class);

        InquiryItemBean.DataBean data = inquiryItemBean.getData();
        List<String> list = new ArrayList<>();
        list.add(data.getUserId());

        List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, list);

        data.setUserName(userDataBeen.get(0).getRealName());
        data.setUserFace(userDataBeen.get(0).getFace());

        return data;

    }

    public static DoctorDateBean.DataBean doctorData(Context context, String userId, int type) {
        //     http://192.168.1.129/api/user/item.json
        String json = HttpUtil.getStringByOkHttp(Api.baseUrl + "/api/user/item.json?userId=" + userId + "&userType=" + type, context);
        Log.e("test", "doctorData: " + json);
        DoctorDateBean doctorDateBean = null;

        try {
            doctorDateBean = new Gson().fromJson(json, DoctorDateBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        DoctorDateBean.DataBean data = doctorDateBean.getData();

        return data;
    }

    public static List<InquiryMsgBean.DataBean> inquiryMsg(Context context, String inquiryId) {

//        "api/inquiry/item.json（问诊信息）
//        api/inquiry/comments/list.json（问答信息）"

        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/comments/list.json?inquiryId=" + inquiryId;

        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "inquiryMsg: " + url + json);

        InquiryMsgBean inquiryMsgBean = null;

        inquiryMsgBean = new Gson().fromJson(json, InquiryMsgBean.class);

        List<InquiryMsgBean.DataBean> data = inquiryMsgBean.getData();

        List l = new ArrayList();
        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
        }

        List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < userDataBeen.size(); j++) {
                if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                    data.get(i).setUserName(userDataBeen.get(j).getRealName());
                    data.get(i).setUserFace(userDataBeen.get(j).getFace());
                }
            }
        }

        for (int i = 0; i < data.size(); i++) {
            InquiryMsgBean.DataBean dataBean = data.get(i);
            if (dataBean.getContentType() == 4) {
                String media = dataBean.getMedia();
                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(Api.SGHBaseUrl + media);  //recordingFilePath（）为音频文件的路径
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                double duration = player.getDuration();//获取音频的时间
                Log.d("test", "### duration: " + duration);
                player.release();//记得释放资源
                dataBean.setMediaTime((int) (duration / 1000));
            }
        }

        return data;
    }

    public static boolean collectInquiry(Context context, FormBody formBody, int type) {
//        /api/inquiry/saveInquiryFav.json
//                /api/inquiry/cancelInquiryFav.json
        String url = "";

        if (type == 1) {
            url = Api.SGHBaseUrl + "/api/inquiry/saveInquiryFav.json";
        } else {
            url = Api.SGHBaseUrl + "/api/inquiry/cancelInquiryFav.json";
        }


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);//post 请求数据，没有表单数据请求，设为null

        Log.e("test", "myFavList: " + json);
        CollectResultBean collectResultBean = null;

        try {
            collectResultBean = new Gson().fromJson(json, CollectResultBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


        if (collectResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static List<UserQuestionListBean.DataBean> userQuestionList(Context context,int p) {
//        api/inquiry/mylist.json
        String url = "";

        url = Api.baseUrl + "/api/inquiry/mylist.json?p="+p;

        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "userQuestionList: " + json);

        UserQuestionListBean userQuestionListBean = null;


        userQuestionListBean = new Gson().fromJson(json, UserQuestionListBean.class);

        List<UserQuestionListBean.DataBean> data = userQuestionListBean.getData();

        List l = new ArrayList();
        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserFace(userDataBeen.get(j).getFace());
                    }
                    if (data.get(i).getDoctor() == null) {
                        continue;
                    } else {
                        if (data.get(i).getDoctor().equals(userDataBeen.get(j).getUserId())) {
                            data.get(i).setDoctorName(userDataBeen.get(j).getRealName());
                            data.get(i).setDoctorFace(userDataBeen.get(j).getFace());
                        }
                    }
                }
            }
        }


        return data;
    }

    public static List<InquiryListBean.DataBean> myFavList(Context context,int p) {
//        api/inquiry/fav.json

        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/fav.json?p="+p;

        String json = HttpUtil.getStringByOkHttp(url, context);
        InquiryListBean inquiryListBean = null;

        inquiryListBean = new Gson().fromJson(json, InquiryListBean.class);
        List<InquiryListBean.DataBean> data = inquiryListBean.getData();
        List l = new ArrayList();

        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserImage(userDataBeen.get(j).getFace());
                    }
                    if (data.get(i).getDoctor() == null) {
                        continue;
                    } else {
                        if (data.get(i).getDoctor().equals(userDataBeen.get(j).getUserId())) {
                            data.get(i).setDoctorName(userDataBeen.get(j).getRealName());
                            data.get(i).setDoctorImage(userDataBeen.get(j).getFace());
                        }
                    }
                }
            }
        }
        return data;
    }


    public static List<UploadBean> uploadImg(Context context, File[]  files) {
        //        "api/inquiry/save.json(保存问诊)；
//        api/public/upload.json；（附件上传）""1，保存问诊（intro：内容，files：文件路径，以“,”隔开多个文件）
//        2，附件上传（file：数据流；type类型：inquiry）"
        String url = "";
        url = Api.SGHBaseUrl + "/api/public/upload.json";


        List<UploadBean> jsons = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            UploadBean json = HttpUtil.postUploadImg(context, url, files[0]);
            jsons.add(json);
        }
        return jsons;
    }

    public static UploadBean uploadImg(Context context, String mImgUrl) {
        //        "api/inquiry/save.json(保存问诊)；
//        api/public/upload.json；（附件上传）""1，保存问诊（intro：内容，files：文件路径，以“,”隔开多个文件）
//        2，附件上传（file：数据流；type类型：inquiry）"
        String url = "";
        url = Api.SGHBaseUrl + "/api/public/upload.json";

        UploadBean json = HttpUtil.postUpload(context, url, mImgUrl, 1);

        return json;
    }

    public static UploadBean uploadVideo(Context context, String videoUrl) {
        //        "api/inquiry/save.json(保存问诊)；
//        api/public/upload.json；（附件上传）""1，保存问诊（intro：内容，files：文件路径，以“,”隔开多个文件）
//        2，附件上传（file：数据流；type类型：inquiry）"
        String url = "";
        url = Api.SGHBaseUrl + "/api/public/upload.json";

        UploadBean json = HttpUtil.postUpload(context, url, videoUrl, 2);

        return json;
    }

    public static UploadBean uploadAudio(Context context, String videoUrl) {
        //        "api/inquiry/save.json(保存问诊)；
//        api/public/upload.json；（附件上传）""1，保存问诊（intro：内容，files：文件路径，以“,”隔开多个文件）
//        2，附件上传（file：数据流；type类型：inquiry）"
        String url = "";
        url = Api.SGHBaseUrl + "/api/public/upload.json";

        UploadBean json = HttpUtil.postUpload(context, url, videoUrl, 3);

        return json;
    }

    public static Boolean SaveQuestion(Context context, FormBody formBody) {
        //        "api/inquiry/save.json(保存问诊)；
//        api/public/upload.json；（附件上传）""1，保存问诊（intro：内容，files：文件路径，以“,”隔开多个文件）
//        2，附件上传（file：数据流；type类型：inquiry）"
        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/save.json";


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);
        Log.e("test", "SaveQuestion: " + json);

        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }

    }

    public static SaveChatResultBean SaveChat(Context context, FormBody formBody) {
//        "api/public/upload.json（上传文件）（如果为文字则不需要调用）
//        api/inquiry/comments/save.json（提交追问信息）"

        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/comments/save.json";

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);
        Log.e("test", "SaveChat: " + json);

        SaveChatResultBean chatBean = new Gson().fromJson(json, SaveChatResultBean.class);


        List l = new ArrayList();

        l.add(chatBean.getData().getUserId());

        List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);


        for (int j = 0; j < userDataBeen.size(); j++) {
            chatBean.getData().setUserName(userDataBeen.get(j).getRealName());
            chatBean.getData().setUserFace(userDataBeen.get(j).getFace());
        }
        return chatBean;
    }

//    api/inquiry/receive.json   inquiryId：问诊ID

    public static Boolean DoctorReceive(Context context, FormBody formBody) {
        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/receive.json";

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        Log.e("test", "DoctorReceive: " + json);
        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    //    api/inquiry/receivelist.json
    public static List<DoctorAskBean.DataBean> DoctorReceiveList(Context context, int type,int p) {
        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/receivelist.json?type=" + type+"&p="+p;
        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "DoctorReceiveList: " + json + type);

        DoctorAskBean doctorAskBean = new Gson().fromJson(json, DoctorAskBean.class);
        List<DoctorAskBean.DataBean> data = doctorAskBean.getData();
        List l = new ArrayList();

        if (data.size() == 0) {

        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserFace(userDataBeen.get(j).getFace());
                    }
                }
            }
        }
        return data;
    }

    //    /api/inquiry/closeInquiry.json
    public static Boolean closeInquiry(Context context, FormBody formBody) {

        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/closeInquiry.json";

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean tucaoSave(Context context, FormBody formBody) {

//        api/inquiry/tc/save.json

        String url = "";
        url = Api.SGHBaseUrl + "/api/inquiry/tc/save.json";


        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);

        if (noResultBean.getStatus() == 1) {
            return true;
        } else {
            return false;
        }

    }

    //    /api/inquiry/isPostInquiry.json
    public static Boolean isPostInquiry(Context context) {

        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/isPostInquiry.json";

        String json = HttpUtil.getStringByOkHttp(url, context);

        NoResultBean noResultBean = new Gson().fromJson(json, NoResultBean.class);

        if (noResultBean.getData() > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static DoctorDetailBean.DataBean DoctorDetail(Context context, String userId) {
//        /api/user/item.json  "userId:医生ID；userType：3；"

        String url = "";

        url = Api.SGHBaseUrl + "/api/user/item.json?userId=" + userId + "&userType=" + 3;

        String json = HttpUtil.getStringByOkHttp(url, context);
        Log.e("test", "DoctorDetail: " + json);

        DoctorDetailBean doctorDetailBean = new Gson().fromJson(json, DoctorDetailBean.class);
        DoctorDetailBean.DataBean data = doctorDetailBean.getData();
        if (doctorDetailBean.getStatus() == 1) {
            return data;
        } else {
            return null;
        }
    }

//    api/inquiry/receivelist.json
    public static  List<InquiryListBean.DataBean> doctorInquiry(Context context,String userId,int p){
        String url = "";

        url = Api.SGHBaseUrl + "/api/inquiry/finishlist.json?doctor=" + userId+"&p="+p ;

        String json = HttpUtil.getStringByOkHttp(url, context);

        Log.e("test", "doctorInquiry: "+json );

        InquiryListBean inquiryListBean = null;

        inquiryListBean = new Gson().fromJson(json, InquiryListBean.class);
        List<InquiryListBean.DataBean> data = inquiryListBean.getData();
        List l = new ArrayList();

        if (data.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < data.size(); i++) {
                if (!l.contains(data.get(i).getDoctor())) {
                    l.add(data.get(i).getDoctor());
                }
                if (!l.contains(data.get(i).getUserId())) {
                    l.add(data.get(i).getUserId());
                }
            }
            List<UserBean.DataBean> userDataBeen = getUserDataBeen(context, l);

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < userDataBeen.size(); j++) {
                    if (data.get(i).getUserId().equals(userDataBeen.get(j).getUserId())) {
                        data.get(i).setUserName(userDataBeen.get(j).getRealName());
                        data.get(i).setUserImage(userDataBeen.get(j).getFace());
                    }
                    if (data.get(i).getDoctor() == null) {
                        continue;
                    } else {
                        if (data.get(i).getDoctor().equals(userDataBeen.get(j).getUserId())) {
                            data.get(i).setDoctorName(userDataBeen.get(j).getRealName());
                            data.get(i).setDoctorImage(userDataBeen.get(j).getFace());
                        }
                    }
                }
            }
            return data;
        }
    }

    public static WeiXinPayDean weixinGave(Context context, String courseId, String price) {
        String url = "";

        url = Api.baseUrl + "/api/pay/wxInquiryRewards.json";
        FormBody formBody = new FormBody.Builder()//创建表单构造器
                .add("inquiryId", courseId)//添加表单参数
                .add("type", 1 + "")
                .add("price",price)
                .build();//生成简易表单型RequestBody

        String json = HttpUtil.postStringByOkHttp(context, url, formBody);

        Log.e("test", "onClick: " + json + courseId + price+url);

        WeiXinPayDean weiXinPayDean = new Gson().fromJson(json, WeiXinPayDean.class);

        return weiXinPayDean;

    }
}
