package com.kdmobi.gui.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.video.VideoPreviewActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgGetLoginSuccessDialog;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.WxController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QQloginModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by admin on 2017/3/7.
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler,ViewNetCallBack {
    private static final String APP_SECRET = "cda4e898a08d4d865ed67e1f8791e67f";
    private IWXAPI api;
    public static final String APP_ID = Constant.WXIDAPP_ID;
    private static String uuid;

    private String openID="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);//必须调用此句话
    }

    //微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq req) {
        Logger.e("onReq"+req);
    }
    //app发送消息给微信，微信返回的消息回调函数,根据不同的返回码来判断操作是否成功
    @Override
    public void onResp(BaseResp resp) {
        String result ;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result ="分享成功";
                //发送成功
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                if (sendResp != null) {
                    String code = sendResp.code;
                    getAccess_token(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result ="分享取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result ="分享拒绝";
                break;
            default:
                result ="分享 -"+resp.errCode;
                break;
        }

//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }
    /**
     * 获取openid accessToken值用于后期操作
     * @param code 请求码
     */
    private void getAccess_token(final String code) {
        Logger.e("code====="+code);
//        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
//                + APP_ID
//                + "&secret="
//                + APP_SECRET
//                + "&code="
//                + code
//                + "&grant_type=authorization_code";
//        Logger.e("getAccess_token：" + path);


        WxController.getInstance().weixintoken(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {

            }

            @Override
            public void onConnectEnd() {

            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                Logger.e("indfod--eeeeeeeeeeeee--"+o.toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(o.toString());
                    String openid = jsonObject.getString("openid").toString().trim();
                    openID=jsonObject.getString("openid").toString().trim();
                    String access_token = jsonObject.getString("access_token").toString().trim();
                    getUserMesg(access_token, openid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, APP_ID, APP_SECRET, code);

        //网络请求，根据自己的请求方式
//        VolleyRequest.get(this, path, "getAccess_token", false, null, new VolleyRequest.Callback() {
//            @Override
//            public void onSuccess(String result) {
//                LogUtils.log("getAccess_token_result:" + result);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(result);
//                    String openid = jsonObject.getString("openid").toString().trim();
//                    String access_token = jsonObject.getString("access_token").toString().trim();
//                    getUserMesg(access_token, openid);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
    }

    /**
     * 获取微信的个人信息
     * @param access_token
     * @param openid
     */
    private void getUserMesg(final String access_token, final String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid;
        Logger.e("getUserMesg：" + path);

        Logger.e("code==access_tokenaccess_token==="+access_token+openid);


        WxController.getInstance().weixininfo(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {

            }

            @Override
            public void onConnectEnd() {

            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                JSONObject jsonObject = null;
                try {
                    Logger.e("json"+o.toString());
                    jsonObject = new JSONObject(o.toString());
                    String openid = jsonObject.getString("openid");
                    MineInfoController.getInstance().connectlogin(WXEntryActivity.this,openid,o.toString(),"2");
//                    listener.onListener(openid,o.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, access_token, openid);
        //网络请求，根据自己的请求方式

//        VolleyRequest.get(this, path, "getAccess_token", false, null, new VolleyRequest.Callback() {
//            @Override
//            public void onSuccess(String result) {
//                LogUtils.log("getUserMesg_result:" + result);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(result);
//                    String nickname = jsonObject.getString("nickname");
//                    int sex = Integer.parseInt(jsonObject.get("sex").toString());
//                    String headimgurl = jsonObject.getString("headimgurl");
//
//                    LogUtils.log("用户基本信息:");
//                    LogUtils.log("nickname:" + nickname);
//                    LogUtils.log("sex:" + sex);
//                    LogUtils.log("headimgurl:" + headimgurl);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                finish();
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
    }
    OnBackListener listener;
    public void setOnListerner(OnBackListener list){
        listener=list;
    }
    public interface  OnBackListener{
        void onListener(String openId, String allmeaage);
    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.connectlogin.getType()){
            try {
                Logger.e("ddddd----------"+o.toString());
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    QQloginModel model=(QQloginModel)result;
                    if(model.getUser_status()!=null&&model.getUser_status().equals("new")){
                        Logger.e("ddddd----------"+openID+model.getUser_status());
                        IntentTools.startBindMobile(this,openID,"2");
                    }else{
                        UserPreference.setUser(model.getUser_info());
//                        ShareRemovePopUpDialog();
//                        IntentTools.startMain(this,"123");

                        ShenGuiApplication.getInstance().clearAcCach();
                        Intent Mintin=new Intent();
                        Mintin.putExtra("id","fina");
                        setResult(RESULT_OK, Mintin);
                        finish();
//                        IntentTools.startMain(this,"123");
                    }
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                Logger.e("ddddd----------"+e.getMessage());
//                ToastTool.show("注册失败");
            }
        }
    }

    //成功
    public void ShareRemovePopUpDialog() {   //弹框
        final SgGetLoginSuccessDialog PopUpDialogs = new SgGetLoginSuccessDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    PopUpDialogs.dismiss();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}