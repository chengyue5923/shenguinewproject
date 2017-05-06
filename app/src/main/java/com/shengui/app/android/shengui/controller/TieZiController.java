package com.shengui.app.android.shengui.controller;

import android.accessibilityservice.AccessibilityService;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class TieZiController {

    private static TieZiController instance;


    public static TieZiController getInstance() {
        if (instance == null)
            instance = new TieZiController();
        return instance;
    }

    /**设置帖子精华取消帖子精华
     *
     * @param callBack
     * @param id  帖子id
     * @param type  leixing  	操作类型：set，精华，cancel，取消精华
     */
    public void setDigest(ViewNetCallBack callBack,String id,String type) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.setDigest, new MapBuilder<String, Object>()
                        .add("id",id)
                        .add("type",type)
                        .add("token",UserPreference.getTOKEN())
                        .getMap(), callBack, String.class);
            }else{
                ToastTool.show("您还没有登录");
            }

        } catch (Exception e) {
        }

    }


    /**设置帖子置顶取消帖子置顶
     *
     * @param callBack
     * @param id  帖子id
     * @param type  leixing  	操作类型：set，置顶，cancel，取消置顶
     */
    public void setTop(ViewNetCallBack callBack,String id,String type) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                ConnectTool.httpRequest(HttpConfig.setTop, new MapBuilder<String, Object>()
                        .add("id", id)
                        .add("type", type)
                        .add("token", UserPreference.getTOKEN())
                        .getMap(), callBack, String.class);
            }else{
                ToastTool.show("您还没有登录");
            }
        } catch (Exception e) {
        }

    }



    /**设置帖子点赞取消帖子点赞
     *
     * @param callBack
     * @param post_id  帖子id
     * @param type  leixing  	操作类型：set，点赞，cancel，取消点赞
     */
    public void setDig(ViewNetCallBack callBack,String post_id,String type) {
        try {
                ConnectTool.httpRequest(HttpConfig.setDig, new MapBuilder<String, Object>()
                        .add("post_id", post_id)
                        .add("type", type)
                        .add("token", UserPreference.getTOKEN())
                        .getMap(), callBack, String.class);

        } catch (Exception e) {
        }

    }
    /**安板块获取帖子
     *
     * @param callBack
     */
    public void getTieZiListAlll(ViewNetCallBack callBack,int first,int size,String circle_id) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("circle_id",circle_id)
                        .add("size",size)
                        .add("u_id",UserPreference.getID())
                        .getMap(), callBack, TieZiDetailListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("circle_id",circle_id)
                        .add("size",size)
                        .getMap(), callBack, TieZiDetailListModel.class);
            }

        } catch (Exception e) {
        }

    }
    /**安板块获取帖子
     *
     * @param callBack
     */
    public void getTieZiListByisdigest(ViewNetCallBack callBack,int first,int size,String circle_id) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size)
                        .add("circle_id",circle_id)
                        .add("is_digest",1)
                        .add("u_id",UserPreference.getID()+"")
                        .getMap(), callBack, TieZiDetailListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("first",first)
                        .add("size",size)
                        .add("circle_id",circle_id)
                        .add("is_digest",1)
                        .getMap(), callBack, TieZiDetailListModel.class);
            }

        } catch (Exception e) {
        }

    }
    /**安板块获取帖子
     *
     * @param callBack
     */
    public void getTieZiListBySections(ViewNetCallBack callBack,int first,int size,String section_id,String circle_id) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("section_id",section_id)
                        .add("circle_id",circle_id)
                        .add("first",first)
                        .add("size",size)
                        .add("u_id",UserPreference.getUid()+"")
                        .getMap(), callBack, TieZiDetailListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("section_id",section_id)
                        .add("circle_id",circle_id)
                        .add("first",first)
                        .add("size",size)
                        .getMap(), callBack, TieZiDetailListModel.class);
            }
        } catch (Exception e) {
            Logger.e("exceptiopn"+e.getMessage());
        }

    }
    /**获取最新帖子
     *
     * @param callBack
     * @param type  leixing
     */
    public void getNewTieZiList(ViewNetCallBack callBack,int city_id,String circle_id,int section_id,String topic_id,int type,String first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
        }

    }

    /**获取最新帖子
     *
     * @param callBack
     */
    public void getNewTieZiList(ViewNetCallBack callBack,int first,int size,String order) {
        try {
            if(UserPreference.getTOKEN()!=null&& UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("circle_id","")
                        .add("section_id","")
                        .add("topic_id","")
                        .add("type","")
                        .add("first",first)
                        .add("size",size)
                        .add("order",order)
                        .add("u_id",UserPreference.getUid()+"")
                        .add("is_my_attention","")
//                    .add("u_id",UserPreference.getID())
                        .getMap(), callBack, TieZiDetailListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("circle_id","")
                        .add("section_id","")
                        .add("topic_id","")
                        .add("type","")
                        .add("first",first)
                        .add("size",size)
                        .add("order",order)
                        .add("is_my_attention","")
//                    .add("u_id",UserPreference.getID())
                        .getMap(), callBack, TieZiDetailListModel.class);
            }

        } catch (Exception e) {
            Logger.e("exceptieeeeopn"+e.getMessage());
        }

    }
    /**获取最新帖子
     *
     * @param callBack
     */
    public void getNewTieZiListHot(ViewNetCallBack callBack,int first,int size) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("circle_id","")
                        .add("section_id","")
                        .add("topic_id","")
                        .add("type","")
                        .add("first",first)
                        .add("u_id",UserPreference.getUid()+"")
                        .add("size",size)
                        .add("is_my_attention","0")
//                    .add("u_id",UserPreference.getID())
                        .getMap(), callBack, TieZiDetailListModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                        .add("circle_id","")
                        .add("section_id","")
                        .add("topic_id","")
                        .add("type","")
                        .add("first",first)
                        .add("size",size)
                        .add("is_my_attention","0")
//                    .add("u_id",UserPreference.getID())
                        .getMap(), callBack, TieZiDetailListModel.class);
            }

        } catch (Exception e) {
            Logger.e("excewwwwptiopn"+e.getMessage());
        }

    }
    /**获取最新帖子
     *
     * @param callBack
     */
    public void getNewTieZiList(ViewNetCallBack callBack,int first,int size,int focus) {
        try {
            ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                    .add("circle_id","")
                    .add("section_id","")
                    .add("topic_id","")
                    .add("type","")
                    .add("first",first)
                    .add("size",size)
                    .add("is_my_attention",focus)
                    .add("token", UserPreference.getTOKEN())
                    .add("u_id",UserPreference.getUid()+"")
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
            Logger.e("exceptiopn"+e.getMessage());
        }

    }
    /**获取最新帖子
     *
     * @param callBack
     */
    public void getNewTieZiListById(ViewNetCallBack callBack,int first,int size,String id) {
        try {
            ConnectTool.httpRequest(HttpConfig.getnewlist, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .add("user_id",id)
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
        }

    }
    /**获取帖子下详情
     *
     * @param callBack
     */
    public void getNewTieZiDetail(ViewNetCallBack callBack,String id) {
        try {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ConnectTool.httpRequest(HttpConfig.get_info, new MapBuilder<String, Object>()
                        .add("id",id)
                        .add("u_id",UserPreference.getUid()+"")
                        .getMap(), callBack, TieZiDetailModel.class);
            }else{
                ConnectTool.httpRequest(HttpConfig.get_info, new MapBuilder<String, Object>()
                        .add("id",id)
                        .getMap(), callBack, TieZiDetailModel.class);
            }
        } catch (Exception e) {
        }

    }
    /**moveto板块
     *
     * @param callBack
     */
    public void MoveToSection(ViewNetCallBack callBack,String token,String id,String section_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.MoveToSection, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .add("section_id",section_id)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    /**动态删除帖子
     *
     * @param callBack
     */
    public void DeletTieeZi(ViewNetCallBack callBack,String id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.DeleteTieZi, new MapBuilder<String, Object>()
                    .add("id",id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
}
