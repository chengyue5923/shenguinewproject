package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.GongQiuListModel;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class GongQiuController {

    private static GongQiuController instance;


    public static GongQiuController getInstance() {
        if (instance == null)
            instance = new GongQiuController();
        return instance;
    }

    /**供求列表
     *
     * @param callBack
     */
    public void getGongQiuList(ViewNetCallBack callBack,String cat_id,String city,String type,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.getSelectSupply, new MapBuilder<String, Object>()
                    .add("cat_id",cat_id)
                    .add("city",city)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    /**供求列表
     *
     * @param callBack
     */
    public void getGongQiuList(ViewNetCallBack callBack,String cat_id,String city,int type,int first,int size,String key) {
        try {
            ConnectTool.httpRequest(HttpConfig.getSelectSupply, new MapBuilder<String, Object>()
                    .add("cat_id",cat_id)
                    .add("city",city)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .add("k",key)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    /**供求列表
     *is_nearby	int	否	是否获取附近，1是，0否，为1将会获取附近的供求
     lng	string	是	经度，is_nearby为1时必须传入，看备注！！！
     lat	string	是	纬度，is_nearby为1时必须传入，看备注！！！
     * @param callBack
     */
    public void getGongQiuList(ViewNetCallBack callBack,String cat_id,String city,String type,int first,int size,String key,int is_nearby) {
        try {
            ConnectTool.httpRequest(HttpConfig.getSelectSupply, new MapBuilder<String, Object>()
                    .add("is_nearby",is_nearby)
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .add("cat_id",cat_id)
                    .add("city",city)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .add("k",key)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    /**我关注的供求列表
     *
     * @param callBack
     */
    public void getFocusGongQiuList(ViewNetCallBack callBack,String cat_id,String city,String type,int first,int sizem,String Token) {
        try {
            ConnectTool.httpRequest(HttpConfig.getSelectSupply, new MapBuilder<String, Object>()
                    .add("cat_id",cat_id)
                    .add("city",city)
                    .add("type",type)
                    .add("first",first)
                    .add("size",sizem)
                    .add("token",Token)
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    /**我关注的人的供求列表
     *
     * @param callBack
     */
    public void getMyFocusGongQiuList(ViewNetCallBack callBack,String cat_id,String city,String type,int first,int size,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.getattlist, new MapBuilder<String, Object>()
                    .add("cat_id",cat_id)
                    .add("city",city)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .add("token",token)
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    /**供求详情
     *
     * @param callBack
     */
    public void GongQiuDetail(ViewNetCallBack callBack,String cat_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.getGongQiuDetail, new MapBuilder<String, Object>()
//                    .add("cat_id",cat_id)
                    .add("u_id",UserPreference.getUserUid())
                    .add("id",cat_id)
                    .getMap(), callBack, GongQiuDetailModel.class);
        } catch (Exception e) {
        }
    }

    /**删除我发布的供求
     *
     * @param callBack
     */
    public void DeleteGongQiu(ViewNetCallBack callBack,String cat_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.DeleteGongQiu, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",cat_id)
                    .getMap(), callBack, GongQiuDetailModel.class);
        } catch (Exception e) {
        }

    }
}
