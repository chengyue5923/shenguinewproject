package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
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

public class MineChangePhoneNumberStepTwoActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.phoneTv)
    TextView phoneTv;
    @Bind(R.id.inputCodeTv)
    EditText inputCodeTv;
    @Bind(R.id.conformTv)
    TextView conformTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    String phone="";
    String code="";
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.conformTv:
                String strinputCodeTv=inputCodeTv.getText().toString();


                if(StringTools.isNullOrEmpty(strinputCodeTv)){
                    ToastTool.show("请输入密码");
                    break;
                }else{
//                    public void change_mobile(ViewNetCallBack callBack,String token,String pwd,String mobile,String code) {
                    MineInfoController.getInstance().change_mobile(this, UserPreference.getTOKEN(),strinputCodeTv,phone,code);
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {
        conformTv.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            phone=(String)getIntent().getSerializableExtra("phone");
            code=(String)getIntent().getSerializableExtra("code");
            phoneTv.setText("请输入账号密码，确认修改手机号为:"+phone);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_change_number_step_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        try {
            JSONObject object = new JSONObject(o.toString());
            if (object.getBoolean("status")) {
                ToastTool.show("修改手机号成功");
                Intent mInt=new Intent();
                mInt.putExtra("er","er");
                setResult(RESULT_OK,mInt);
                finish();
            } else {
                ToastTool.show(object.getString("message"));
            }

        } catch (Exception e) {
            ToastTool.show("修改手机号失败");
        }
    }

}
