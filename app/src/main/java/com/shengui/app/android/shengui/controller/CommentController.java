package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityListModel;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.CommentModels;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class CommentController {

    private static CommentController instance;


    public static CommentController getInstance() {
        if (instance == null)
            instance = new CommentController();
        return instance;
    }


    /**
     * 帖子评论列表
     * @param callBack
     * @param post_id
     * @param first
     * @param size
     */

    public void CommomentList(ViewNetCallBack callBack,int  post_id,int  first,int  size) {
        try {
            ConnectTool.httpRequest(HttpConfig.CommentGetlist, new MapBuilder<String, Object>()
                    .add("post_id",post_id)
                    .add("token", UserPreference.getTOKEN())
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, CommentModels.class);
        } catch (Exception e) {
        }

    }
    //帖子评论接口
    public void CommomemtPup(ViewNetCallBack callBack,String  token,int post_id,int review_user_id,String  contents) {
        try {
            ConnectTool.httpRequest(HttpConfig.CommentPub, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("post_id",post_id)
                    .add("review_user_id",review_user_id)
                    .add("contents",contents)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //帖子评论点赞

    /**
     * token	string	是	用户token
     comment_id	int	是	评论id
     type	string	是	set点赞，cancel取消点赞
     * @param callBack
     * @param token
     * @param comment_id
     * @param type
     */
    public void CommomentDig(ViewNetCallBack callBack,String token,int comment_id,String type) {
        try {
            ConnectTool.httpRequest(HttpConfig.CommentSet_dig, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("comment_id",comment_id)
                    .add("type",type)
                    .getMap(), callBack, ActivityListModel.class);
        } catch (Exception e) {
        }

    }
}
