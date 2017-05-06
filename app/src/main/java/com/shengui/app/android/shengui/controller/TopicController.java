package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.TopicListModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class TopicController {

    private static TopicController instance;


    public static TopicController getInstance() {
        if (instance == null)
            instance = new TopicController();
        return instance;
    }

    //话题列表
    public void GetlistTopic(ViewNetCallBack callBack,int first,int size,String title) {
        try {
            ConnectTool.httpRequest(HttpConfig.getTopicList, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .add("title",title)
                    .getMap(), callBack, TopicListModel.class);
        } catch (Exception e) {
        }

    }
    //话题列表
    public void GetlistTopic(ViewNetCallBack callBack,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.getTopicList, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, TopicListModel.class);
        } catch (Exception e) {
        }

    }
    //话题列表
    public void recommend_topic(ViewNetCallBack callBack,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.recommend_topic, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("debug","1")
                    .getMap(), callBack, TopicListModel.class);
        } catch (Exception e) {
        }

    }
    //添加话题
    public void addTopic(ViewNetCallBack callBack,String title) {
        try {
            ConnectTool.httpRequest(HttpConfig.addTopicList, new MapBuilder<String, Object>()
                    .add("token",UserPreference.getTOKEN())
                    .add("title",title)
                    .getMap(), callBack, TopicListModel.class);
        } catch (Exception e) {
        }

    }
}
