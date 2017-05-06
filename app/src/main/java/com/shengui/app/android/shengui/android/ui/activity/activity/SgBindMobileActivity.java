package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgRegisterActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgGetRegisterSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CountDownTimeButton;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/23.
 */

public class SgBindMobileActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.titleName)
    TextView titleName;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
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

    String openId="";
    private Dialog dialog;
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginTv:
                String codeEtStr=codeEt.getText().toString();

                Logger.e("password" + codeEtStr + "-------"+!codeEtStr.equals("")+"--");
                Logger.e("ddd"+(codeEtStr.length()==6));
                if(!codeEtStr.equals("")&&codeEtStr.length()==6){
                    MineInfoController.getInstance().connectbind(this,openId,phoneEt.getText().toString(),codeEtStr,flag);
                }else{
                    ToastTool.show("验证码不能为空");
                }
                break;
            case R.id.cancelTextView:
                finish();
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
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
                MineInfoController.getInstance().sendMobileCode(SgBindMobileActivity.this, pass,0);
                return true;
            }
        });
        sentCodeText.setCycle(60);
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onConnectEnd() {
        super.onConnectEnd();
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        loginTv.setOnClickListener(this);
    }

    String flag;
    @Override
    protected void initData() {

        if(getIntent()!=null){
            openId=getIntent().getStringExtra("Openid");
            flag=(String)getIntent().getSerializableExtra("flag");
        }
        dialog = DialogFacory.getInstance().createRunDialog(this, "请稍后...");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mobile_code_activity;
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
                    IntentTools.startMain(SgBindMobileActivity.this);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag==HttpConfig.sendMobileCode.getType()){
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
        }
        if(flag== HttpConfig.connectbind.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    LoginResultModel model=(LoginResultModel)result;
                    UserPreference.setUser(model);
                    ShareRemovePopUpDialog();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("注册失败");
            }
        }
    }

}
