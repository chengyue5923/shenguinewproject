package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

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
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgForgetPassActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CountDownTimeButton;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineChangePassWordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.nameEv)
    EditText nameEv;
    @Bind(R.id.infoLayout)
    RelativeLayout infoLayout;
    @Bind(R.id.paAgainTv)
    TextView paAgainTv;
    @Bind(R.id.passEv)
    EditText passEv;
    @Bind(R.id.infoagainLayout)
    RelativeLayout infoagainLayout;
    @Bind(R.id.codeTaxtview)
    EditText codeTaxtview;
    @Bind(R.id.codeLayout)
    RelativeLayout codeLayout;
    @Bind(R.id.logoutLayout)
    TextView logoutLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.codeTv)
    TextView codeTv;
    @Bind(R.id.sentCodeText)
    CountDownTimeButton sentCodeText;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.logoutLayout:
                String code=codeTaxtview.getText().toString();
                String pass = nameEv.getText().toString();
                String passagain = passEv.getText().toString();
                if(StringTools.isNullOrEmpty(codeTaxtview.getText().toString())){
                    ToastTool.show("验证码不能为空");
                    break;
                }
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    MineInfoController.getInstance().editpw(MineChangePassWordActivity.this,pass,passagain,code,UserPreference.getTOKEN());
                }else{
                    ToastTool.show("您还没有登录");
                }
                break;
        }

    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        logoutLayout.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        sentCodeText.setStartCountLisener(new CountDownTimeButton.StartCountLisener() {
            @Override
            public boolean startCount() {
                String code=codeTaxtview.getText().toString();
                String pass = nameEv.getText().toString();
                String passagain = passEv.getText().toString();
                Logger.e("password"+pass+"-------"+passagain);
                if (StringTools.isNullOrEmpty(pass)) {
                    ToastTool.show("新密码不能为空");
                    return false;
                }
                if (StringTools.isNullOrEmpty(passagain)) {
                    ToastTool.show("再次输入密码不能为空");
                    return false;
                }
                if(pass.length()<6||pass.length()>16){
                    ToastTool.show("新密码为6-16为的字母或数字");
                    return false;
                }
                if(passagain.length()<6||passagain.length()>16){
                    ToastTool.show("新密码为6-16为的字母或数字");
                    return false;
                }
                if(!pass.equals(passagain)){
                    ToastTool.show("两次输入的密码不匹配");
                    return false;
                }
                MineInfoController.getInstance().sendMobileCode(MineChangePassWordActivity.this, UserPreference.getPhone(),0);

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
        return R.layout.activity_mine_change_pass_activity;
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
        }
        if(flag== HttpConfig.editpw.getType()){

            Logger.e("dddd"+o.toString());
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("修改成功");
                    finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("修改失败");
            }
        }
    }

}
