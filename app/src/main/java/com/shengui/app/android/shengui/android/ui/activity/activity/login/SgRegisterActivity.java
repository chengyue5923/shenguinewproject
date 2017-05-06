package com.shengui.app.android.shengui.android.ui.activity.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
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
import com.shengui.app.android.shengui.android.ui.dialog.SgGetPassSuccessDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SgGetRegisterSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CountDownTimeButton;
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

public class SgRegisterActivity extends BaseActivity implements View.OnClickListener {

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
    @Bind(R.id.sentCodeText)
    CountDownTimeButton sentCodeText;
    @Bind(R.id.codeEt)
    EditText codeEt;
    @Bind(R.id.passEt)
    EditText passEt;
    @Bind(R.id.passagainEt)
    EditText passagainEt;
    @Bind(R.id.loginTv)
    TextView loginTv;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.agreementTv)
    TextView agreementTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private boolean istrue = true;
    private static final String APPID = Constant.QQ_LOGIN_FLAG;

    private Tencent mTencent;
    private IUiListener loginListener;
    private IUiListener userInfoListener;
    private String scope;

    private UserInfo userInfo;
    private String openID="";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.image:
                istrue = !istrue;
                if (istrue) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                } else {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                }
                break;
            case R.id.agreementTv:
                IntentTools.startAgreement(this);
                break;
            case R.id.loginTv:
                if (istrue) {
//                    ShareRemovePopUpDialog();
                    Register();
                } else {
                    ToastTool.show("同意用户协议");
                }
                break;
            case R.id.statesText:
                IntentTools.startLogin(this);
                break;
            case R.id.QQLayout:

                login();
                break;
            case R.id.weixinLayout:
                loginToWeiXin();
                break;
        }
    }
    private void login() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(SgRegisterActivity.this, scope, loginListener);
        }
    }


    private void Register() {
        String codeEtStr =codeEt.getText().toString();
        String passEtStr =passEt.getText().toString();
        String passagainEtStr =passagainEt.getText().toString();
        if(codeEtStr.equals("")){
            ToastTool.show("请输入验证码");
            return;
        }else if(passEtStr.equals("")){
            ToastTool.show("请输入密码");
            return;
        }else if(passEtStr.length()<6){
            ToastTool.show("请按要求输入密码");
            return;
        }else if(passagainEtStr.equals("")){
            ToastTool.show("请再次输入密码");
            return;
        }else if(!passEtStr.equals(passagainEtStr)){
            ToastTool.show("输入的密码不一致");
            return;
        }
        LoginController.getInstance().register(this,phoneEt.getText().toString(),passEtStr,passagainEtStr,codeEtStr);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
    }

    @Override
    protected void initEvent() {
        weixinLayout.setOnClickListener(this);
        QQLayout.setOnClickListener(this);
        statesText.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        agreementTv.setOnClickListener(this);
        image.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        sentCodeText.setStartCountLisener(new CountDownTimeButton.StartCountLisener() {
            @Override
            public boolean startCount() {

                String pass = phoneEt.getText().toString();
                Logger.e("password" + pass + "-------");
                if (StringTools.isNullOrEmpty(pass)) {
                    ToastTool.show("手机号码不能为空");
                    return false;
                }
                if (pass.length() != 11) {
                    ToastTool.show("请输入正确的手机号码");
                    return false;
                }
                if(!pass.matches("^1[3|4|5|7|8]\\d{9}$")){
//                    sentCodeText.setBackgroundColor(getResources().getColor(R.color.selected_line_color));
//                   SmsController.getInstance().sendIdentifyCode(UserRegisterActivity.this, accountEt.getText().toString());
//                    return true;
//                }else{
                    ToastTool.show("请输入正确的手机号码");
                    return false;
                }
                MineInfoController.getInstance().sendMobileCode(SgRegisterActivity.this, pass,1);
                return true;
            }
        });
        sentCodeText.setCycle(60);
    }

    @Override
    protected void initData() {
        initDatas();
    }
    private void initDatas() {
        mTencent = Tencent.createInstance(APPID, SgRegisterActivity.this);
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

                    System.out.println("json=" + String.valueOf(jo));

                    System.out.println("msg="+msg);
                    if ("sucess".equals(msg)) {
                        Toast.makeText(SgRegisterActivity.this, "登录成功",
                                Toast.LENGTH_LONG).show();
                        openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                    }
                    System.out.println("开始获取用户信息");
                    if(mTencent.getQQToken() == null){
                        System.out.println("qqtoken == null");
                    }
                    openID = jo.getString("openid");
                    userInfo = new UserInfo(SgRegisterActivity.this, mTencent.getQQToken());
                    userInfo.getUserInfo(userInfoListener);
                } catch (Exception e) {
                    // TODO: handle exception
                    ToastTool.show("登录出错");
                }

            }
            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                ToastTool.show("登录取消");
            }
        };

        userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
                ToastTool.show("登录出错");
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
                    System.out.println("json=" + String.valueOf(jo));
                    if(ret == 100030){
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(SgRegisterActivity.this, "all", new IUiListener() {

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
                        SgRegisterActivity.this.runOnUiThread(r);
                    }else{
//                        String gender = jo.getString("gender");

                     MineInfoController.getInstance().connectlogin(SgRegisterActivity.this,openID,jo.toString(),"1");

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    ToastTool.show("登录出错");
                }
            }
            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                ToastTool.show("登录取消");
            }
        };
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

    //成功
    public void ShareRemovePopUpDialog() {   //弹框
        final SgGetRegisterSuccessDialog PopUpDialogs = new SgGetRegisterSuccessDialog(this);
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_register_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if(flag== HttpConfig.sendMobileCode.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("验证码已发出请查收");
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("验证码发送失败");
            }
        }else if(flag==HttpConfig.connectlogin.getType()){
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
        }
        else{
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
                ToastTool.show("注册失败");
            }
        }


    }

}
