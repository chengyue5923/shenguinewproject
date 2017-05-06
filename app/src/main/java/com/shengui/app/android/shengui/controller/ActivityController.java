package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityListModel;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.TopicListModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class ActivityController {

    private static ActivityController instance;


    public static ActivityController getInstance() {
        if (instance == null)
            instance = new ActivityController();
        return instance;
    }

    //活动报名

    /**
     * token	string	是	用户token
     a_id	int	是	活动id
     contact_user	string	否	联系人
     contact_mobile	string	否	联系电话
     * @param callBack

     */
    public void ActivitySingUp(ViewNetCallBack callBack,String token,String a_id,String contact_user,String contact_mobile) {
        try {
            ConnectTool.httpRequest(HttpConfig.ActivitySingUp, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("a_id",a_id)
                    .add("contact_user",contact_user)
                    .add("contact_mobile",contact_mobile)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //活动详情
    public void ActivityDetail(ViewNetCallBack callBack,String id) {
        try {
            ConnectTool.httpRequest(HttpConfig.ActivityDetails, new MapBuilder<String, Object>()
                    .add("id",id)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, ActivityModel.class);
        } catch (Exception e) {
        }

    }
    //活动列表
    public void ActivityLists(ViewNetCallBack callBack,String title,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.ActivityList, new MapBuilder<String, Object>()
                    .add("title",title)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, ActivityListModel.class);
        } catch (Exception e) {
        }

    }
    //活动订单
    public void ActivityOrderLists(ViewNetCallBack callBack,String token,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.ActivityOrder, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, ActivityListModel.class);
        } catch (Exception e) {
        }

    }
}
