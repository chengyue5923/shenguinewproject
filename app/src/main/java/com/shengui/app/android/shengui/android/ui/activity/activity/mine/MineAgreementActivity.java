package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;

import org.json.JSONObject;
import org.xml.sax.XMLReader;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineAgreementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.backContent)
    EditText backContent;
    @Bind(R.id.numberTv)
    TextView numberTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        MineInfoController.getInstance().protocol(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        try {
            JSONObject object = new JSONObject(o.toString());
            if (object.getBoolean("status")) {
                JSONObject ja=object.getJSONObject("data");
                String content =ja.getString("content");
//                numberTv.setText(content);

                numberTv.setText(Html.fromHtml(content));

            } else {
                ToastTool.show(object.getString("message"));
            }

        } catch (Exception e) {
            ToastTool.show("登录失败");
        }
    }
}
