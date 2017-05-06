package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PersonBean;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.CircleMemberListModel;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.QuanZiListModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

import java.io.File;
import java.util.HashMap;

/**
 * Created by admin on 2017/1/6.
 */

public class GuiMiController {

    private static GuiMiController instance;


    public static GuiMiController getInstance() {
        if (instance == null)
            instance = new GuiMiController();
        return instance;
    }

    //创建的圈子
    public void GetlistQuanzi(ViewNetCallBack callBack,String title,int member_audit, String section,String avatar,String desc,String city_id,String city_name) {
        try {
            ConnectTool.httpRequest(HttpConfig.CreateQuanZi, new MapBuilder<String, Object>()
                    .add("title",title)
                    .add("token",UserPreference.getTOKEN())
                    .add("member_audit",member_audit)
                    .add("lat",UserPreference.getLng())
                    .add("lng",UserPreference.getLat())
                    .add("section",section)
                    .add("avatar",avatar)
                    .add("desc",desc)
                    .add("city_id",city_id)
                    .add("city_name",city_name)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //删除圈子成员

    /**
     * token	string	是	用户token
      user_id	int	是	被审批用户id
     circle_id	int	是	圈子id
     * @param callBack
     * @param circle_id
     * @param user_id
     * @param token
     */
    public void CircleDeleteMemberList(ViewNetCallBack callBack,String circle_id,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Circleapprove_unpassed, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("user_id",user_id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //加入圈子审批
    public void CircleAddMemberList(ViewNetCallBack callBack,String circle_id,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.CircleUnpass, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("user_id",user_id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //获取圈子的成员列表  无圈主
    public void CircleMemberList(ViewNetCallBack callBack,int circle_id,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.Circlepprove, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("first",first)
                    .add("size",size)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, CircleMemberDetail.class);
        } catch (Exception e) {
        }

    }
    //获取圈子的成员列表`有圈主
    public void CircleMemberList(ViewNetCallBack callBack,int circle_id,int first,int size,int flage) {
        try {
            ConnectTool.httpRequest(HttpConfig.Circlepprove, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("first",first)
                    .add("size",size)
                    .add("main_in_member",1)
                    .add("token",UserPreference.getTOKEN())
                    .getMap(), callBack, CircleMemberDetail.class);
        } catch (Exception e) {
        }

    }
    //退出圈子
    public void exitCircle(ViewNetCallBack callBack,String token,int circle_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.exitCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("circle_id",circle_id)
                    .getMap(), callBack, CircleMemberDetail.class);
        } catch (Exception e) {
        }

    }
    //获取待审核进入圈子的成员列表
    public void CirclenoapproveList(ViewNetCallBack callBack,int circle_id,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.Circlenoapprove, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, CircleMemberDetail.class);
        } catch (Exception e) {
        }

    }
    //圈主转让

    /**
     * token	string	是	用户token
     user_id	int	是	被转让用户id
     circle_id	int	是	圈子id
     * @param callBack
     * @param circle_id
     */
    public void circle_transfer(ViewNetCallBack callBack,String circle_id,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.circle_transfer, new MapBuilder<String, Object>()
                    .add("circle_id",circle_id)
                    .add("user_id",user_id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //热门板块
    public void GethotPlatelist(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.hotPlateList, new MapBuilder<String, Object>()
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //加入收藏
    public void Favoriteadd(ViewNetCallBack callBack,String token ,String fav_id,String type) {
        try {
            ConnectTool.httpRequest(HttpConfig.Favoriteadd, new MapBuilder<String, Object>()
                    .add("type",type)
                    .add("fav_id",fav_id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    //删除收藏

    /**
     * token	string	是	用户token
     fav_id	int	是	帖子id或者供求id
     type	string	是	supply供求，post帖子
     * @param callBack
     */
    public void Favoritedel(ViewNetCallBack callBack,String token ,String fav_id,String type) {
        try {
            ConnectTool.httpRequest(HttpConfig.Favoritedel, new MapBuilder<String, Object>()
                    .add("type",type)
                    .add("fav_id",fav_id)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    //安话题获取帖子列表

    /**
     * topic_id	int	是	话题id
     first	int	否	从第多少条开始取
     size	int	否	每页获取数量
     * @param callBack
     */
    public void TopicTieZiList(ViewNetCallBack callBack,String topic_id ,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.TopicTieZiList, new MapBuilder<String, Object>()
                    .add("size",size)
                    .add("token",UserPreference.getTOKEN())
                    .add("first",first)
                    .add("topic_id",topic_id)
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
        }

    }
//圈子详情

    /**
     *
     key	参数类型	是否必须	参数解释
     id	int	是	圈子id
     u_id	int	否	当前用户id，用于获取是否已点赞
     * @param callBack
     */
    public void CiecleContentDetail(ViewNetCallBack callBack,int topic_id ,int u_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.CircleDetail, new MapBuilder<String, Object>()
                    .add("id",topic_id)
                    .add("u_id",u_id)
                    .getMap(), callBack, QuanziList.class);
        } catch (Exception e) {
        }

    }
    //圈子详情

    /**
     *
     key	参数类型	是否必须	参数解释
     id	int	是	圈子id
     u_id	int	否	当前用户id，用于获取是否已点赞
     * @param callBack
     */
    public void CiecleContentQuanziDetail(ViewNetCallBack callBack,int topic_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.CircleDetail, new MapBuilder<String, Object>()
                    .add("id",topic_id)
                    .getMap(), callBack, QuanziList.class);
        } catch (Exception e) {
        }

    }
//圈子详情

    /**
     *
     key	参数类型	是否必须	参数解释
     id	int	是	圈子id
     u_id	int	否	当前用户id，用于获取是否已点赞
     * @param callBack
     */
    public void CiecleContentDetail(ViewNetCallBack callBack,int topic_id ) {
        try {
            ConnectTool.httpRequest(HttpConfig.CircleDetail, new MapBuilder<String, Object>()
                    .add("id",topic_id)
                    .getMap(), callBack, QuanziList.class);
        } catch (Exception e) {
        }

    }
    //申请加入圈子

    /**
     *
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void ApplyJoinCircle(ViewNetCallBack callBack,String token ,int circle_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.ApplyJoinCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("circle_id",circle_id)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    /**
     *编辑圈子
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void EditCircle(ViewNetCallBack callBack,String token ,int circle_id,String avatar,String title,String lng,String lat,String city_id,String city_name) {
        try {
            ConnectTool.httpRequest(HttpConfig.EditCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",circle_id)
                    .add("avatar",avatar)
                    .add("title",title)
                    .add("lng",lng)
                    .add("lat",lat)
                    .add("city_id",city_id)
                    .add("city_name",city_name)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    /**
     *编辑圈子
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void EditCircleSection(ViewNetCallBack callBack,String token ,int circle_id,String section) {
        try {
            ConnectTool.httpRequest(HttpConfig.EditCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",circle_id)
                    .add("section",section)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    /**
     *编辑圈子
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void EditCircleMember(ViewNetCallBack callBack,String token ,int circle_id,int member_audit) {
        try {
            ConnectTool.httpRequest(HttpConfig.EditCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",circle_id)
                    .add("member_audit",member_audit)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    /**
     *编辑圈子
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void EditCircleMemberDesc(ViewNetCallBack callBack,String token ,int circle_id,String desc) {
        try {
            ConnectTool.httpRequest(HttpConfig.EditCircle, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",circle_id)
                    .add("desc",desc)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    /**
     *获取圈子中（关注、未关注）的成员列表
     token	string	是	用户token
     circle_id	int	是	圈子id
     * @param callBack
     */
    public void GetCirclrList(ViewNetCallBack callBack,String token ,int circle_id,String  type) {
        try {
            ConnectTool.httpRequest(HttpConfig.GetCircleFocusList, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("circle_id",circle_id)
                    .add("type",type)
                    .add("first",0)
                    .add("size",50)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
}
