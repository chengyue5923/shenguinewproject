package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PersonBean;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.QuanZiListModel;
import com.shengui.app.android.shengui.models.TorTypeModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/1/6.
 */

public class PushController {

    private static PushController instance;


    public static PushController getInstance() {
        if (instance == null)
            instance = new PushController();
        return instance;
    }

    //发帖子

    /**
     *
     * @param callBack
     * @param circle_id 圈子
     * @param contents 供求信息
     * @param imgs  图片集合
     * @param type  发布类型
     * @param section_id  板块
     * @param topic_id  话题
     */
    public void PushTieZi(ViewNetCallBack callBack, int circle_id, String contents, String imgs,String type ,String section_id, String topic_id,String city_id,String city_name ) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.pushTie, new MapBuilder<String, Object>()
                        .add("circle_id", circle_id)
                        .add("content", contents)
                        .add("imgs", imgs)
                        .add("type", type)
                        .add("city_id",UserPreference.getUsualCityId())
                        .add("section_id", section_id)
                        .add("topic_id", topic_id)
                        .add("city_id", city_id)
                        .add("city_name", city_name)
                        .add("token", UserPreference.getTOKEN())
                        .add("lng", UserPreference.getLat())
                        .add("lat", UserPreference.getLng())
                        .getMap(), callBack, String.class);
            }else{
                ToastTool.show("您还没有登录");
            }
        } catch (Exception e) {
        }

    }
    public void PushTieZi(ViewNetCallBack callBack, int circle_id, String contents, String imgs,String type ,String section_id, String topic_id,String city_id,String city_name ,String videosid) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.pushTie, new MapBuilder<String, Object>()
                        .add("circle_id", circle_id)
                        .add("content", contents)
                        .add("imgs", imgs)
                        .add("videos", videosid)
                        .add("type", type)
                        .add("city_id",570)
                        .add("section_id", section_id)
                        .add("topic_id", topic_id)
                        .add("city_id", city_id)
                        .add("city_name", city_name)
                        .add("token", UserPreference.getTOKEN())
                        .add("lng", UserPreference.getLat())
                        .add("lat", UserPreference.getLng())
                        .getMap(), callBack, String.class);
            }else{
                ToastTool.show("您还没有登录");
            }
        } catch (Exception e) {
        }

    }
    //发供求

    /**
     *
     * @param callBack
     * @param variety_id
     * @param title
     * @param contents
     * @param imgs
     * @param type
     */
    public void PushGongQiu(ViewNetCallBack callBack, String variety_id, String title, String contents, String imgs,int type ,String city_id,String city_name,String province,String videosid) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                ConnectTool.httpRequest(HttpConfig.pushGongQiu, new MapBuilder<String, Object>()
                        .add("variety_id", variety_id)
                        .add("title", title)
                        .add("contents", contents)
                        .add("imgs", imgs)
                        .add("type", type)
                        .add("videos", videosid)
                        .add("contact_user", UserPreference.getName())
                        .add("contact_mobile", UserPreference.getPhone())
                        .add("token", UserPreference.getTOKEN())
                        .add("lng", UserPreference.getLat())
                        .add("lat", UserPreference.getLng())
                        .add("city_id", city_id)
                        .add("city_name", city_name)
                        .add("province", province)
                        .getMap(), callBack, String.class);
            }else{
                ToastTool.show("您还没有登录");
            }
        } catch (Exception e) {
            Logger.e("exception"+e.getMessage());
        }

    }
    //获取乌龟的品种
    public void GetTorType(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.getTorType, new MapBuilder<String, Object>()
                    .getMap(), callBack, PersonBean.class);
        } catch (Exception e) {
        }

    }
    //获取乌龟的品种
    public void GetCityType(ViewNetCallBack callBack,String key) {
        try {
            ConnectTool.httpRequest(HttpConfig.CitySelecter, new MapBuilder<String, Object>()
                    .add("is_pub",key)
                    .getMap(), callBack, PersonBean.class);
        } catch (Exception e) {
        }

    }

    //我加入的圈子

    /**
     * first	int	否	从多少行开始取，默认0
     size	int	否	每页后去条数，默认10
     token	string	是	用户token，必须是登录状态
     type	string	否	my_jion我加入的，all全部，organize组织，my我的 客户端缺少一个热门的值
     * @param callBack
     * @param first
     * @param size
     * @param type
     */
    public void GetlistQuanzi(ViewNetCallBack callBack,int first,int size,String type,String city_id) {
        try {
            if(UserPreference.getUidStr()!=null&&UserPreference.getUidStr().length()>0){
                ConnectTool.httpRequest(HttpConfig.joinQuanZi, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size)
                        .add("token",UserPreference.getTOKEN())
                        .add("type",type)
                        .add("city_id",city_id)
                        .add("u_id",UserPreference.getUid())
                        .getMap(), callBack, QuanZiListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.joinQuanZi, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size)
                        .add("type",type)
                        .add("city_id",city_id)
                        .getMap(), callBack, QuanZiListModel.class);
            }
        } catch (Exception e) {
            Logger.e("dde.getmessage"+e.getMessage());
        }

    }

    //我加入的圈子

    /**
     * first	int	否	从多少行开始取，默认0
     size	int	否	每页后去条数，默认10
     token	string	是	用户token，必须是登录状态
     type	string	否	my_jion我加入的，all全部，organize组织，my我的 客户端缺少一个热门的值
     * @param callBack
     * @param first
     * @param size
     * @param type
     */
    public void GetlistQuanziUserPort(ViewNetCallBack callBack,int first,int size,String type,String city_id,String use_post) {
        try {
            if(UserPreference.getUidStr()!=null&&UserPreference.getUidStr().length()>0){
                ConnectTool.httpRequest(HttpConfig.joinQuanZi, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size)
                        .add("token",UserPreference.getTOKEN())
                        .add("type",type)
                        .add("use_post",use_post)
                        .add("city_id",city_id)
                        .add("u_id",UserPreference.getUid())
                        .getMap(), callBack, QuanZiListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.joinQuanZi, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size).add("use_post",use_post)
                        .add("type",type)
                        .add("city_id",city_id)
                        .getMap(), callBack, QuanZiListModel.class);
            }
        } catch (Exception e) {
            Logger.e("dde.getmessage"+e.getMessage());
        }

    }
    //图片上传
    public void upLoadFile(final ViewNetCallBack viewNetCallBack, File[] file, HttpConfig httpconfig) {
        try {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("img[]", file);
            hashMap.put("content-type", "multipart/form-data");
            hashMap.put("token", UserPreference.getTOKEN());

            ConnectTool.httpRequest(
                    httpconfig,
                    hashMap,
                    viewNetCallBack,
                    ImageUploadModel.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }
    //图片上传
    public void upLoadVideoFile(final ViewNetCallBack viewNetCallBack, File file, HttpConfig httpconfig) {

        Logger.e("---------"+file.exists());
        Logger.e("-----getAbsolutePath----"+file.getAbsolutePath());
        Logger.e("---------"+file.canRead());

        try {


            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("files", file);
            hashMap.put("content-type", "multipart/form-data");
            hashMap.put("token", UserPreference.getTOKEN());
            ConnectTool.httpRequest(
                    httpconfig,
                    hashMap,
                    viewNetCallBack,
                    ImageUploadModel.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }
}
