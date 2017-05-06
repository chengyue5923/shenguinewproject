package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgRegisterActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CountDownTimeButton;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineChangePhoneNumberActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.phoneTv)
    TextView phoneTv;
    @Bind(R.id.inoutnumberTv)
    EditText inoutnumberTv;
    @Bind(R.id.sentCodeText)
    CountDownTimeButton sentCodeText;
    @Bind(R.id.inputCodeTv)
    EditText inputCodeTv;
    @Bind(R.id.conformTv)
    TextView conformTv;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.agreementTv)
    TextView agreementTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private boolean istrue=true;


    String phone="";
    public static final int flas=9090;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.conformTv:
                String code=inputCodeTv.getText().toString();
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(code.equals("")||code.length()<6){
                        ToastTool.show("请输入正确格式的验证码");
                        break;
                    }else if(!istrue){
                        ToastTool.show("同意用户协议才能提交");
                        break;
                    }else{
//                    finish();
                        IntentTools.startPhoneList(this,phone,code,flas);
                    }
                }else{
                    ToastTool.show("您还没有登录");
                }
                break;
            case R.id.image:
                istrue=!istrue;
                if(istrue){
                    image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                }else{
                    image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                }
                break;
            case R.id.agreementTv:
                IntentTools.startAgreement(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==flas&&resultCode==RESULT_OK){

            if(data!=null){
                finish();
            }
        }

    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        phoneTv.setText("您的手机号："+UserPreference.getPhone());
        image.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
    }

    @Override
    protected void initEvent() {
        agreementTv.setOnClickListener(this);
        image.setOnClickListener(this);
        conformTv.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        sentCodeText.setStartCountLisener(new CountDownTimeButton.StartCountLisener() {
            @Override
            public boolean startCount() {

                String pass = inoutnumberTv.getText().toString();
                Logger.e("password"+pass+"-------");
                if (StringTools.isNullOrEmpty(pass)) {
                    ToastTool.show("手机号码不能为空");
                    return false;
                }
                if(pass.length()!=11){
                    ToastTool.show("请输入正确的手机号码");
                    return false;
                }
                if(!pass.matches("^1[3|4|5|7|8]\\d{9}$")){
////                    sentCodeText.setBackgroundColor(getResources().getColor(R.color.selected_line_color));
////                   SmsController.getInstance().sendIdentifyCode(UserRegisterActivity.this, accountEt.getText().toString());
////                    return true;
////                }else{
                    ToastTool.show("请输入正确的手机号码");
                    return false;
                }
                phone=pass;
//                LoginController.getInstance().getSms(FhForgetPasswordActivity.this, mobile);
                MineInfoController.getInstance().sendMobileCode(MineChangePhoneNumberActivity.this, pass,1);
                return true;
            }
        });
        sentCodeText.setCycle(60);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_change_number_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.sendMobileCode.getType()){
            try {
                JSONObject  js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    ToastTool.show("验证码已发出请查收");
                }else{
                    ToastTool.show(js.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
