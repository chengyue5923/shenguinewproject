package com.shengui.app.android.shengui.android.ui.activity.activity.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/9.
 */

public class SgXianXiaActivitySingUpActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.shareImage)
    ImageView shareImage;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.nameEt)
    EditText nameEt;
    @Bind(R.id.PhoneTV)
    TextView PhoneTV;
    @Bind(R.id.PhontEt)
    EditText PhontEt;
    @Bind(R.id.ConfirmTv)
    TextView ConfirmTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    String activityId="";
    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        ConfirmTv.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        if(getIntent()!=null){
            activityId=(String)getIntent().getSerializableExtra("activityId");
            Logger.e("ffffffactivityId"+activityId);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_xianxia_detail_activity_singup;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.ActivitySingUp.getType()){

            Logger.e("ffffff"+result);
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("报名成功");
                    finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("报名失败");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ConfirmTv:

                SingUp();

                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    private void SingUp() {
        String nameEtStr=nameEt.getText().toString();
        String PhontEtStr=PhontEt.getText().toString();

        if(nameEtStr.equals("")){
            ToastTool.show("请输入姓名");
            return;
        }
        if(PhontEtStr.length()!=11){
            ToastTool.show("请输入正确的联系方式");
            return;
        }

        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            ActivityController.getInstance().ActivitySingUp(this,UserPreference.getTOKEN(),activityId,nameEtStr,PhontEtStr);
        }else{
            ToastTool.show("您还没有登录");
        }
    }
}
