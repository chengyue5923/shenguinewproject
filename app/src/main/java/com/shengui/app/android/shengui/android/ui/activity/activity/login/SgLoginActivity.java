package com.shengui.app.android.shengui.android.ui.activity.activity.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;

import com.kdmobi.gui.wxapi.WXEntryActivity;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.dialog.SgGetLoginSuccessDialog;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.LoginController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.QQloginModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class SgLoginActivity extends BaseActivity implements View.OnClickListener,WXEntryActivity.OnBackListener {
    @Bind(R.id.titleName)
    TextView titleName;
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.smart)
    TextView smart;
    @Bind(R.id.weixinImage)
    ImageView weixinImage;
    @Bind(R.id.weixinLayout)
    RelativeLayout weixinLayout;
    @Bind(R.id.qqImage)
    ImageView qqImage;
    @Bind(R.id.QQLayout)
    RelativeLayout QQLayout;
    @Bind(R.id.loginLaout)
    LinearLayout loginLaout;
    @Bind(R.id.confrmTv)
    TextView confrmTv;
    @Bind(R.id.phoneEt)
    EditText phoneEt;
    @Bind(R.id.passEt)
    EditText passEt;
    @Bind(R.id.loginTv)
    TextView loginTv;
    @Bind(R.id.getPass)
    TextView getPass;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private static final String APPID = Constant.QQ_LOGIN_FLAG;

    private Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private String scope;
    private String openID="";
    private UserInfo userInfo;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.getPass:
                IntentTools.startForgetPass(this);
                break;
            case R.id.statesText:
                IntentTools.startRegister(this);
                break;
            case R.id.loginTv:
                LoginEvent();

                break;
            case R.id.QQLayout:
                login();
                break;
            case R.id.weixinLayout:
                loginToWeiXin();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Logger.e("erer-----");
            if(data!=null){
                String datamessage=(String)data.getSerializableExtra("id");
                if(!StringTools.isNullOrEmpty(datamessage)){
                    Logger.e("erer"+datamessage);
                    finish();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loginToWeiXin(){
        ShenGuiApplication.getInstance().addActivityRecode(this);
        IWXAPI mApi = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_ID, true);
        mApi.registerApp(WXEntryActivity.APP_ID);
        if (mApi != null && mApi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test_neng";
            mApi.sendReq(req);
        } else
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();

    }
    private void login() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(SgLoginActivity.this, scope, loginListener);
        }
    }
    private void LoginEvent() {
        String phoneEtstr=phoneEt.getText().toString();
        String passEtstr=passEt.getText().toString();
        Log.e("test", "LoginEvent: "+phoneEtstr+passEtstr);
        if(phoneEtstr.equals("")||phoneEtstr.length()!=11){
            ToastTool.show("请输入正确手机号码");
            return;
        }
        if(passEtstr.equals("")||passEtstr.length()<6){
            ToastTool.show("请按要求输入密码");
            return;
        }



        LoginController.getInstance().loginUser(this,phoneEtstr,passEtstr);
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        getPass.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        getPass.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void initEvent() {
        weixinLayout.setOnClickListener(this);
        QQLayout.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        statesText.setOnClickListener(this);
        getPass.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        phoneEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                // 此处为得到焦点时的处理内容
                    phoneEt.setBackground(getResources().getDrawable(R.drawable.activity_login_item_number_change));
                } else {
                // 此处为失去焦点时的处理内容
                    phoneEt.setBackground(getResources().getDrawable(R.drawable.activity_login_item));
                }
            }
        });
        passEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    // 此处为得到焦点时的处理内容
                    passEt.setBackground(getResources().getDrawable(R.drawable.activity_login_item_pass_change));
                } else {
                    // 此处为失去焦点时的处理内容
                    passEt.setBackground(getResources().getDrawable(R.drawable.activity_login_item));
                }
            }
        });

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
                    JsonUitl.userStatus(SgLoginActivity.this);//神龟大学和医院
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void initData() {
        initDatas();
    }
    private void initDatas() {
        mTencent = Tencent.createInstance(APPID, SgLoginActivity.this);
        //要所有权限，不用再次申请增量权限，这里不要设置成get_user_info,add_t
        scope = "all";
        loginListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onComplete(Object value) {
                // TODO Auto-generated method stub

                System.out.println("有数据返回..");
                if (value == null) {
                    return;
                }

                try {
                    JSONObject jo = (JSONObject) value;

                    String msg = jo.getString("msg");

                    System.out.println("json=ddddd" + String.valueOf(jo));

                    System.out.println("msg="+msg);
                    if ("sucess".equals(msg)) {
                        Toast.makeText(SgLoginActivity.this, "登录成功",
                                Toast.LENGTH_LONG).show();

                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                    }
                    openID=jo.getString("openid");
                    System.out.println("开始获取用户信息");
                    if(mTencent.getQQToken() == null){
                        System.out.println("qqtoken == null");
                    }
                    userInfo = new UserInfo(SgLoginActivity.this, mTencent.getQQToken());
                    userInfo.getOpenId(userInfoListener);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=ssssssssss" + String.valueOf(jo));
                    if(ret == 100030){
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(SgLoginActivity.this, "all", new IUiListener() {

                                    @Override
                                    public void onError(UiError arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onComplete(Object arg0) {
                                        // TODO Auto-generated method stub

                                    }

                                    @Override
                                    public void onCancel() {
                                        // TODO Auto-generated method stub

                                    }
                                });
                            }
                        };

                        SgLoginActivity.this.runOnUiThread(r);
                    }else{
//                        String nickName = jo.getString("nickname");
//                        String gender = jo.getString("gender");
//                        Toast.makeText(SgLoginActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
                        MineInfoController.getInstance().connectlogin(SgLoginActivity.this,openID,jo.toString(),"1");
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_login_activity;
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
                        IntentTools.startBindMobile(this,openID,"1");
                    }else{
                        UserPreference.setUser(model.getUser_info());
                        ShareRemovePopUpDialog();
                    }
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                Logger.e("ddddd----------"+e.getMessage());
//                ToastTool.show("注册失败");
            }
        }else{
             try {
                 JSONObject object = new JSONObject(o.toString());
                 if (object.getBoolean("status")) {
                     LoginResultModel model=(LoginResultModel)result;
                     UserPreference.setUser(model);
                     ShareRemovePopUpDialog();
//                    int isFirst = object.optJSONObject("map").optInt("isFirst");
//                    if(isFirst ==0 ){
//                IntentTools.startSetPsw(FhLoginActivity.this, flagNumber, identifyNum, phoneNum);
//                    }else if(isFirst == 1){
//                        ToastTool.show("您已不是首次登录，请使用账号密码登录");
//                        switchToOldLogin();
//                    }
                 } else {
                     ToastTool.show(object.getString("message"));
                 }

             } catch (Exception e) {
                 ToastTool.show("登录失败");
             }
         }

    }
    @Override
    protected void onDestroy() {
        if (mTencent != null) {
            mTencent.logout(SgLoginActivity.this);
        }
        super.onDestroy();
    }


    @Override
    public void onListener(String openId, String allmeaage) {

    }
}
