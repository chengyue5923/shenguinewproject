package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AddressModel;
import com.shengui.app.android.shengui.models.EditTextModel;
import com.shengui.app.android.shengui.models.FansOrAttentionModels;
import com.shengui.app.android.shengui.models.FavoriteModels;
import com.shengui.app.android.shengui.models.GongQiuListModel;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.NotiseListModel;
import com.shengui.app.android.shengui.models.ProvinceModel;
import com.shengui.app.android.shengui.models.QQloginModel;
import com.shengui.app.android.shengui.models.QuanZiListModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.models.TopicListModel;
import com.shengui.app.android.shengui.models.UpDateModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * Created by admin on 2017/1/6.
 */

public class MineInfoController {

    private static MineInfoController instance;


    public static MineInfoController getInstance() {
        if (instance == null)
            instance = new MineInfoController();
        return instance;
    }
    //清消息
    public void quanzhuDelete(ViewNetCallBack callBack,String  token,String  id) {
        try {
            ConnectTool.httpRequest(HttpConfig.quanzhuDelete, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //清消息
    public void forgetPass(ViewNetCallBack callBack,String  mobile,String  pwd,String  re_pwd,String  code) {
        try {
            ConnectTool.httpRequest(HttpConfig.forgetPass, new MapBuilder<String, Object>()
                    .add("mobile",mobile)
                    .add("pwd",pwd)
                    .add("re_pwd",re_pwd)
                    .add("code",code)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }


    //清消息
    public void set_user_notice(ViewNetCallBack callBack,String  user_id,String  target_type,String  target_id,String  title,String  token,String  content) {
        try {
            ConnectTool.httpRequest(HttpConfig.set_user_notice, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("user_id",user_id)
                    .add("target_type",target_type)
                    .add("target_id",target_id)
                    .add("title",title)
                    .add("content",content)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    //清空用户消息
    public void usernotice_del(ViewNetCallBack callBack,String ids,String  token) {
        try {
            ConnectTool.httpRequest(HttpConfig.usernotice_del, new MapBuilder<String, Object>()
                    .add("ids",ids)
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //获取安装引导图
    public void get_install_boot_map(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_install_boot_map, new MapBuilder<String, Object>()
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .getMap(), callBack, ImageModel.class);
        } catch (Exception e) {
        }

    }
    //用户消息
    public void usernotice(ViewNetCallBack callBack,int first,int size,String  token) {
        try {
            ConnectTool.httpRequest(HttpConfig.usernotice, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .add("token",token)
                    .getMap(), callBack, NotiseListModel.class);
        } catch (Exception e) {
        }

    }

    //获取文章内容
    public void get_article(ViewNetCallBack callBack,int id) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_article, new MapBuilder<String, Object>()
                    .add("id",id)
                    .getMap(), callBack, EditTextModel.class);
        } catch (Exception e) {
        }

    }
    //关于神龟
    public void AboutShenGui(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.AboutShenGui, new MapBuilder<String, Object>()
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //appshengji
    public void appUpdate(ViewNetCallBack callBack,String version) {
        try {
            ConnectTool.httpRequest(HttpConfig.appUpdate, new MapBuilder<String, Object>()
                    .add("version",version)
                    .getMap(), callBack, UpDateModel.class);
        } catch (Exception e) {
        }

    }
    //我发布的供求列表
    public void get_my_supply(ViewNetCallBack callBack,int first,int size,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_my_supply, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .add("token",token)
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    //我发布的供求列表
    public void get_my_supply_byUid(ViewNetCallBack callBack,int first,int size,String user_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_my_supply, new MapBuilder<String, Object>()
                    .add("first",first)
                    .add("size",size)
                    .add("user_id",user_id)
                    .getMap(), callBack, GongQiuListModel.class);
        } catch (Exception e) {
        }

    }
    //获取我发布的帖子列表
    public void get_my_post(ViewNetCallBack callBack,int first,int size ,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_my_post, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
        }

    }
    //获取我发布的帖子列表
    public void get_my_postById(ViewNetCallBack callBack,int first,int size ,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_my_post, new MapBuilder<String, Object>()
                    .add("user_id",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, TieZiDetailListModel.class);
        } catch (Exception e) {
        }

    }
    //获取我的收藏
    public void Favoriteget_my_favorite(ViewNetCallBack callBack,int first,int size ,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Favoriteget_my_favorite, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, FavoriteModels.class);
        } catch (Exception e) {
        }

    }

    //获取我关注的人的列表
    public void get_my_attension(ViewNetCallBack callBack,int first,int size ,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_my_attension, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, FansOrAttentionModels.class);
        } catch (Exception e) {
        }

    }

    //获取关注我的人的列表
    public void get_attension_my(ViewNetCallBack callBack,int first,int size ,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_attension_my, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, FansOrAttentionModels.class);
        } catch (Exception e) {
        }

    }
    //


    //关注quanzi
    public void AttentionCircleadd(ViewNetCallBack callBack,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Attentionadd, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("user_id",user_id)
                    .add("att_type","2")
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    //取消关注某人
    public void AttentionCirclrdel(ViewNetCallBack callBack,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Attentiondel, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("user_id",user_id)
                    .add("att_type","2")
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }



    //关注某人
    public void Attentionadd(ViewNetCallBack callBack,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Attentionadd, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("user_id",user_id)
                    .add("att_type","1")
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }

    //取消关注某人
    public void Attentiondel(ViewNetCallBack callBack,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Attentiondel, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("user_id",user_id)
                    .add("att_type","1")
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //用户签到
    public void SignUser(ViewNetCallBack callBack,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.SignUser, new MapBuilder<String, Object>()
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //获取用户自然月内的签到信息
    public void SingListInfo(ViewNetCallBack callBack,String string,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.SingListInfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("month",string)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //获取签到的规则信息
    public void SingGiftInfo(ViewNetCallBack callBack,String string,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.SingGiftInfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //获取中奖名单
    public void GiftListInfo(ViewNetCallBack callBack,String string,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.GiftListInfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }

    }
    //BindJpush
    public void BindJpush(ViewNetCallBack callBack,String registration_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.BindJpush, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("registration_id",registration_id)
                    .add("type",2)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }

    //修改用户密码
    public void editpw(ViewNetCallBack callBack,String pwd,String re_pwd,String code,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.editpw, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("pwd",pwd)
                    .add("re_pwd",re_pwd)
                    .add("code",code)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }

    //修改用户信息
    public void edituserinfo(ViewNetCallBack callBack,String token,String name,String truename,String sex,String avatar,String signature) {
        try {
            ConnectTool.httpRequest(HttpConfig.edituserinfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("name",name)
                    .add("truename",truename)
                    .add("sex",sex)
                    .add("avatar",avatar)
                    .add("signature",signature)
                    .add("province_id","")
                    .add("city_id",UserPreference.getUid())
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //修改用户信息
    public void edituserinfo(ViewNetCallBack callBack,String token,String name,String truename,String sex,String signature) {
        try {
            ConnectTool.httpRequest(HttpConfig.edituserinfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("name",name)
                    .add("truename",truename)
                    .add("sex",sex)
                    .add("signature",signature)
                    .add("province_id","")
                    .add("city_id",UserPreference.getUid())
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //根据经纬度获取系统中城市的信息
    public void cityLocaling(ViewNetCallBack callBack,String lng,String lat) {
        try {
            ConnectTool.httpRequest(HttpConfig.CityLocaling, new MapBuilder<String, Object>()
                    .add("lng",lng)
                    .add("lat",lat)
                    .getMap(), callBack, LocationModel.class);
        } catch (Exception e) {
        }
    }
    //发送手机验证码
    public void sendMobileCode(ViewNetCallBack callBack,String mobile,int flag) {
        try {
            ConnectTool.httpRequest(HttpConfig.sendMobileCode, new MapBuilder<String, Object>()
                    .add("mobile",mobile)
                    .add("is_exist",flag)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }
    //degnlu
    public void connectlogin(ViewNetCallBack callBack,String oauth_user_id,String oauth_user_info,String oauth_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.connectlogin, new MapBuilder<String, Object>()
                    .add("oauth_user_id",oauth_user_id)
                    .add("oauth_user_info",oauth_user_info)
                    .add("oauth_id",oauth_id)
                    .getMap(), callBack, QQloginModel.class);
        } catch (Exception e) {
        }
    }
    //第三方用户绑定用户手机
    public void connectbind(ViewNetCallBack callBack,String oauth_user_id,String mobile,String code,String oauth_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.connectbind, new MapBuilder<String, Object>()
                    .add("oauth_user_id",oauth_user_id)
                    .add("mobile",mobile)
                    .add("code",code)
                    .add("oauth_id",oauth_id)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //我的信息
    public void get_my_fullinfo(ViewNetCallBack callBack,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.getmyfullinfo, new MapBuilder<String, Object>()
                    .add("token",token)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //我的信息
    public void get_my_fullinfoById(ViewNetCallBack callBack,String user_id,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.getmyfullinfo, new MapBuilder<String, Object>()
                    .add("user_id",user_id)
                    .add("token",token)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //获取省份列表
    public void Addressgetprovince(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressgetprovince, new MapBuilder<String, Object>()
                    .getMap(), callBack, ProvinceModel.class);
        } catch (Exception e) {
        }
    }

    //根据省id获取城市列表
    public void Addressget_city_by_province(ViewNetCallBack callBack,String province_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressget_city_by_province, new MapBuilder<String, Object>()
                    .add("province_id",province_id)
                    .getMap(), callBack, ProvinceModel.class);
        } catch (Exception e) {
        }
    }

    //收货地址列表
    public void Addressadd_list(ViewNetCallBack callBack,String token) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressadd_list, new MapBuilder<String, Object>()
                    .add("token",token)
                    .getMap(), callBack, AddressModel.class);
        } catch (Exception e) {
        }
    }


    //删除收货地址
    public void Addressdel(ViewNetCallBack callBack,String token,String id) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressdel, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .getMap(), callBack, AddressModel.class);
        } catch (Exception e) {
        }
    }


    //更换手机号
    public void change_mobile(ViewNetCallBack callBack,String token,String pwd,String mobile,String code) {
        try {
            ConnectTool.httpRequest(HttpConfig.change_mobile, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("pwd",pwd)
                    .add("mobile",mobile)
                    .add("code",code)
                    .getMap(), callBack, AddressModel.class);
        } catch (Exception e) {
        }
    }

    //bianji 收货地址

    /**
     * token	string	是
     name	string	是	收货人
     province_id	int	是	省id
     city_id	int	是	城市id
     address	string	是	详细地址
     mobile	int	是	手机号
     is_usual	int	否	是否默认地址：1默认
     tel	string	否	电话
     zipcode	int	否	邮编
     * @param callBack
     * @param token
     */
    public void Addressedit_address(ViewNetCallBack callBack,String token,String id,String name,String province_id,String city_id,String address,String mobile,String is_usual,String tel,String zipcode) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressedit_address, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .add("name",name)
                    .add("province_id",province_id)
                    .add("city_id",city_id)
                    .add("address",address)
                    .add("mobile",mobile)
                    .add("is_usual",is_usual)
                    .add("tel",tel)
                    .add("zipcode",zipcode)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }
    public void Addressedit_address_setdefault(ViewNetCallBack callBack,String token,String id,String is_usual) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressedit_address, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .add("is_usual",is_usual)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }

    //添加收货地址

    /**
     * token	string	是
     name	string	是	收货人
     province_id	int	是	省id
     city_id	int	是	城市id
     address	string	是	详细地址
     mobile	int	是	手机号
     is_usual	int	否	是否默认地址：1默认
     tel	string	否	电话
     zipcode	int	否	邮编
     * @param callBack
     * @param token
     */
    public void Addressadd(ViewNetCallBack callBack,String token,String name,String province_id,String city_id,String address,String mobile,String is_usual,String tel,String zipcode) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressadd, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("name",name)
                    .add("province_id",province_id)
                    .add("city_id",city_id)
                    .add("address",address)
                    .add("mobile",mobile)
                    .add("is_usual",is_usual)
                    .add("tel",tel)
                    .add("zipcode",zipcode)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }


    //收货地址详情
    public void Addressadd_info(ViewNetCallBack callBack,String token,String id) {
        try {
            ConnectTool.httpRequest(HttpConfig.Addressadd_info, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("id",id)
                    .getMap(), callBack, ProvinceModel.class);
        } catch (Exception e) {
        }
    }


    //获取首页焦点图及导航四项（四个菱形图片）
    public void get_focus_nav(ViewNetCallBack callBack,String lng,String lat) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_focus_nav, new MapBuilder<String, Object>()
                    .add("lng",lat)
                    .add("lat",lng)
                    .getMap(), callBack, ProvinceModel.class);
        } catch (Exception e) {
        }
    }
    //获取引导页广告图片
    public void get_ad_page(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.get_ad_page, new MapBuilder<String, Object>()
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .getMap(), callBack, ImageModel.class);
        } catch (Exception e) {
        }
    }
    //用户协议
    public void protocol(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.protocol, new MapBuilder<String, Object>()
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }
    //意见反馈
    public void feedback(ViewNetCallBack callBack,String token,String content) {
        try {
            ConnectTool.httpRequest(HttpConfig.feedback, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("content",content)
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }
    //搜索
    public void searchindex(ViewNetCallBack callBack,String k,String type,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.SearchIndex, new MapBuilder<String, Object>()
                    .add("k",k)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, QuanziList.class);
        } catch (Exception e) {
        }
    }
    //搜索
    public void searchindexGuiMI(ViewNetCallBack callBack,String k,String type,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.SearchIndex, new MapBuilder<String, Object>()
                    .add("k",k)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //搜索
    public void searchindexTieZi(ViewNetCallBack callBack,String k,String type,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.SearchIndex, new MapBuilder<String, Object>()
                    .add("k",k)
                    .add("type",type)
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, TieZiDetailModel.class);
        } catch (Exception e) {
        }
    }

    //搜索供求时候的大家都在搜
    public void supply_hot_search(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.supply_hot_search, new MapBuilder<String, Object>()
                    .getMap(), callBack, ImageModel.class);
        } catch (Exception e) {
        }
    }

    //圈子大家都在搜
    public void search_hot_search(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.search_hot_search, new MapBuilder<String, Object>()
                    .getMap(), callBack, ImageModel.class);
        } catch (Exception e) {
        }
    }
    //我常去的圈子
    public void usual_circle(ViewNetCallBack callBack,String userid) {
        try {
            ConnectTool.httpRequest(HttpConfig.usual_circle, new MapBuilder<String, Object>()
                    .add("user_id",userid)
                    .getMap(), callBack, QuanZiListModel.class);
        } catch (Exception e) {
        }
    }
    //热门闺蜜圈
    public void hot_circle(ViewNetCallBack callBack,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.hot_circle, new MapBuilder<String, Object>()
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .add("token",UserPreference.getTOKEN())
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, QuanZiListModel.class);
        } catch (Exception e) {
        }
    }
    //热门闺蜜圈
    public void hot_circle(ViewNetCallBack callBack,int first,int size,String cityid) {
        try {
            ConnectTool.httpRequest(HttpConfig.hot_circle, new MapBuilder<String, Object>()
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .add("first",first)
                    .add("token",UserPreference.getTOKEN())
                    .add("city_id",cityid)
                    .add("size",size)
                    .getMap(), callBack, QuanZiListModel.class);
        } catch (Exception e) {
        }
    }

    //举报帖子
    public void expose_post(ViewNetCallBack callBack,String token,String content,String id,String typeid) {
        try {
            ConnectTool.httpRequest(HttpConfig.expose_post, new MapBuilder<String, Object>()
                    .add("token",token)
                    .add("content",content)
                    .add("id",id)
                    .add("type",typeid)
                    .getMap(), callBack, QuanZiListModel.class);
        } catch (Exception e) {
        }
    }

    //搜索附近龟蜜
    public void searchnearby(ViewNetCallBack callBack,int first,int size) {
        try {
            ConnectTool.httpRequest(HttpConfig.searchnearby, new MapBuilder<String, Object>()
                    .add("lng",UserPreference.getLat())
                    .add("lat",UserPreference.getLng())
                    .add("first",first)
                    .add("size",size)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }
    }
    //更新用户位置信息
    public void loginlocation(ViewNetCallBack callBack) {
        try {
            ConnectTool.httpRequest(HttpConfig.loginlocation, new MapBuilder<String, Object>()
                    .add("token",UserPreference.getTOKEN())
                    .add("lat",UserPreference.getLng())
                    .add("lat",UserPreference.getLng())
                    .getMap(), callBack, String.class);
        } catch (Exception e) {
        }
    }
}


