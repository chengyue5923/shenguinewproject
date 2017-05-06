package com.shengui.app.android.shengui.utils.net;

import com.base.framwork.cach.preferce.PreferceManager;
import com.base.framwork.net.lisener.NetCallback;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.MD5Util;
import com.base.platform.utils.java.MapUtils;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanZiManagerMemberActivity;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.configer.enums.HttpConfigBean;
import com.shengui.app.android.shengui.configer.enums.HttpManager;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.ex.NoDataListExpetion;
import com.shengui.app.android.shengui.utils.im.LogoutEvent;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;


/**
 * 对底层接口的 对接类
 */
public class BaseNetImpl implements NetCallback {


    ViewNetCallBack listener;
    Class entityClass;
    HttpConfigBean config;
    Map<String, Object> param;
    HttpConfig con;

    public BaseNetImpl(ViewNetCallBack listener, Class entityClass, HttpConfig config) {
        this.listener = listener;
        this.entityClass = entityClass;
        this.config = HttpManager.getInstance().getConifgById(config);
        con = config;

    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public void dealReslut(String jsonstring) {
        try {
            Logger.e("dealReslutjson"+jsonstring);
            if (config.isCach()) {
                String url = MapUtils.map2UrlParams(config.getActioin(), param);
                PreferceManager.getInsance().saveValueBYkey(MD5Util.string2MD5(url), jsonstring);
            }
            JSONObject jsonObject = new JSONObject(jsonstring);
//            if (config.getId()==HttpConfig.ImgetDetailmessage.getType()) {
//                EventManager.getInstance().post(new LogoutEvent());
//                return;
//            }
//            if(config.getId()==HttpConfig.rankBetween.getType()||config.getId()==HttpConfig.rightList.getType()){
//                listener.onData((Serializable) GsonTool.jsonToArrayEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring,param );
//                return;
//            }

            if(config.getId()==HttpConfig.weixintoken.getType()||config.getId()==HttpConfig.weixininfo.getType()){
                listener.onData((Serializable) GsonTool.jsonToArrayEntity(jsonstring, entityClass), config.getId(), true, jsonstring, param);
                return;
            }

            if(config.getId()==HttpConfig.getTorType.getType()||config.getId()==HttpConfig.Circlepprove.getType()
                    ||config.getId()==HttpConfig.CitySelecter.getType()){
//                Logger.e("listerm---------"+listener);
//                Logger.e("istrue"+listener.getClass().equals(SgQuanZiManagerMemberActivity.class));
                JSONObject JSon=jsonObject.getJSONObject("data");
//                JSONObject date=JSon.getJSONObject("result");
                JSONArray allJSON=new JSONArray();
                Iterator<String> keys=JSon.keys();
                try{
                    while(keys.hasNext()){
                        JSONArray ja = JSon.getJSONArray(keys.next().toString());
                        for(int i=0;i<ja.length();i++){
                            allJSON.put(ja.get(i));
                        }
                    }
                }catch (Exception e){
                    Logger.e("e.get"+e.getMessage());
                }
                listener.onData((Serializable) GsonTool.jsonToArrayEntity(allJSON.toString(), entityClass), config.getId(), true, jsonstring, param);
                return;
            }
            if(config.getId()==HttpConfig.SearchIndex.getType()){
                try{
                    JSONObject JSon=jsonObject.getJSONObject("data");
                    JSONArray ja=JSon.getJSONArray("result");
                    listener.onData((Serializable) GsonTool.jsonToArrayEntity(ja.toString(), entityClass), config.getId(), true, jsonstring, param);

                }catch (Exception e){
                    Logger.e("e.get"+e.getMessage());
                }
                return;
            }
            if(config.getId()==HttpConfig.addTopicList.getType()||config.getId()==HttpConfig.Circlenoapprove.getType()){
                listener.onData((Serializable) GsonTool.jsonToArrayEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring, param);
                return;
            }
            if(config.getId()==HttpConfig.getnewlist.getType()){
                listener.onData((Serializable) GsonTool.jsonToEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring, param);
                return;
            }
            try {
                listener.onData((Serializable) GsonTool.jsonToEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring,param );
            } catch (Exception e) {
                if(jsonObject.getString(config.getType())!=null){
                    listener.onData((Serializable) GsonTool.jsonToArrayEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring, param);
                }
            }

        } catch (Exception e) {
            Logger.e("exceptionee"+e.getMessage());
            listener.onFail(new NoDataListExpetion());
        }
    }

    @Override
    public void dealFailer(Exception e, String jsonstring) {
        try {
            Logger.e("jsomn"+jsonstring);
            JSONObject jsonObject = new JSONObject(jsonstring);
            if (jsonObject.getInt("code") == 401&&config.getId()!=HttpConfig.yapull.getType()) {

//                EventManager.getInstance().post(new LogoutEvent());
                return;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        listener.onFail(e);
    }

    @Override
    public void onstart() {

        listener.onConnectStart();
        if (config.isCach()) {
            String url = MapUtils.map2UrlParams(config.getActioin(), param);
            try {
                String jsonstring1 = PreferceManager.getInsance().getValueBYkey(MD5Util.string2MD5(url));
                JSONObject jsonObject = new JSONObject(jsonstring1);
                try {
                    listener.onData((Serializable) GsonTool.jsonToEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring1,param );
                } catch (Exception e1) {
                    if(jsonObject.getString(config.getType())!=null){
                        listener.onData((Serializable) GsonTool.jsonToArrayEntity(jsonObject.getString(config.getType()), entityClass), config.getId(), true, jsonstring1, param);
                    }
                }
                return;
            } catch (Exception e1) {

            }
        }

    }

    @Override
    public void onEnd() {
        listener.onConnectEnd();
    }
}
