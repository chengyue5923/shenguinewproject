package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.BuildConfig;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.service.UpdateService;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.DataCleanmanager;
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

public class MineSettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.infoLayout)
    RelativeLayout infoLayout;
    @Bind(R.id.addressLayout)
    RelativeLayout addressLayout;
    @Bind(R.id.passeordLayout)
    RelativeLayout passeordLayout;
    @Bind(R.id.changePhoneLayout)
    RelativeLayout changePhoneLayout;
    @Bind(R.id.fileLayout)
    RelativeLayout fileLayout;
    @Bind(R.id.newCodeLayout)
    RelativeLayout newCodeLayout;
    @Bind(R.id.aboutLayout)
    RelativeLayout aboutLayout;
    @Bind(R.id.clearLayout)
    RelativeLayout clearLayout;
    @Bind(R.id.logoutLayout)
    TextView logoutLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.cleartv)
    TextView cleartv;
    @Bind(R.id.ClearSizeTv)
    TextView ClearSizeTv;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.infoLayout:
                if (isLogin()) {
                    IntentTools.startInfo(this);
                } else {
                    IntentTools.startLogin(this);
                }
                break;
            case R.id.addressLayout:
//                IntentTools.startDefaultAddress(this);
                if (isLogin()) {
                    IntentTools.startAddress(this);
                } else {
                    IntentTools.startLogin(this);
                }
                break;
            case R.id.passeordLayout:
                if (isLogin()) {
                    IntentTools.startCode(this);
                } else {
                    IntentTools.startLogin(this);
                }
                break;
            case R.id.changePhoneLayout:
                if (isLogin()) {
                    IntentTools.startChangePhone(this);
                } else {
                    IntentTools.startLogin(this);
                }
                break;
            case R.id.fileLayout:
                IntentTools.startAgreement(this);
                break;
            case R.id.newCodeLayout:
                upDateApp();
                break;
            case R.id.aboutLayout:
                MineInfoController.getInstance().AboutShenGui(this);
                break;
            case R.id.clearLayout:
                DataCleanmanager.clearAllCache(this);
                ClearSizeTv.setText("0k");
                break;
            case R.id.logoutLayout:
                if (isLogin()) {
                    UserPreference.cleanUser();
                    finish();
                } else {
                   IntentTools.startLogin(this);
                }

                break;
        }
    }

    private void upDateApp() {
        MineInfoController.getInstance().appUpdate(this, BuildConfig.VERSION_NAME);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        passeordLayout.setOnClickListener(this);
        changePhoneLayout.setOnClickListener(this);
        fileLayout.setOnClickListener(this);
        newCodeLayout.setOnClickListener(this);
        aboutLayout.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        logoutLayout.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        try {
            DataCleanmanager.getTotalCacheSize(this);
            Logger.e("datassgetTotalCacheSizes" + DataCleanmanager.getTotalCacheSize(this));
            ClearSizeTv.setText( DataCleanmanager.getTotalCacheSize(this));
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                logoutLayout.setVisibility(View.VISIBLE);
            } else {
                logoutLayout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_setting_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.AboutShenGui.getType()) {

            try {
                JSONObject js = new JSONObject(o.toString());
                if (js.getBoolean("status")) {
                    JSONObject jadata = js.getJSONObject("data");
                    String text = jadata.getString("content");
                    IntentTools.startTextDecorView(this, text);

                } else {
                    ToastTool.show("网络出错，请稍后重试");
                }
            } catch (JSONException e) {
                ToastTool.show("网络出错，请稍后重试");
                e.printStackTrace();
            }


        } else {
            try {
                JSONObject ja = new JSONObject(o.toString());
                if (ja.getBoolean("status")) {
                    JSONObject jadata = ja.getJSONObject("data");
                    String url = jadata.getString("url");
                    showNewVersionDialog(url);
//                    Intent intent= new Intent();
//                    intent.setAction("android.intent.action.VIEW");
//                    intent.addCategory("android.intent.category.DEFAULT");
//                    Uri content_url = Uri.parse(url);
//                    intent.setData(content_url);
//                    startActivity(intent);
                } else {
                    ToastTool.show("已是最新版本");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showNewVersionDialog(final String url) {
        DialogFacory.getInstance().createAlertDialog(this, "软件升级", "发现新版本,建议立即更新使用.", "更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent updateIntent = new Intent(MineSettingActivity.this, UpdateService.class);
                updateIntent.putExtra("url", url);
                startService(updateIntent);
            }
        }, null).show();
    }

    public boolean isLogin() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            return true;
        } else {
            return false;
        }
    }

}
