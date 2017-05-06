package com.shengui.app.android.shengui.android.ui.activity.activity.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityChangeSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityBaseNumberChangeTeamListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.QuanziList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/28.
 */

public class SgChangeQuanZhuActivity extends BaseActivity implements ActivityBaseNumberChangeTeamListAdapter.BaseItemListener, View.OnClickListener ,ScrollViewExtend.ObservableScrollViewCallbacks  {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.confirmText)
    TextView confirmText;
    @Bind(R.id.nameTextView)
    TextView nameTextView;
    @Bind(R.id.typeImage)
    ImageView typeImage;
    @Bind(R.id.addressTextView)
    TextView addressTextView;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityBaseNumberChangeTeamListAdapter numberAdapter;
    @Bind(R.id.selectMember)
    RelativeLayout selectMember;
    private int firstnumber=0;
    private int size=10;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        scrollView.setCallbacks(this);
        numberAdapter = new ActivityBaseNumberChangeTeamListAdapter(this);

        Memberlistview.setAdapter(numberAdapter);
        numberAdapter.setDialogListener(this);
        selectMember.setVisibility(View.GONE);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        confirmText.setOnClickListener(this);
    }

    QuanziList circlemodel;

    @Override
    protected void initData() {
        firstnumber=0;
        circlemodel = (QuanziList) getIntent().getSerializableExtra("QuanziList");
        GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(circlemodel.getId()),firstnumber, size);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_quan_zhu_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.Circlepprove.getType()) {
            Logger.e("lCirclepproveogger----------" + o);
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject json = jsonObject.getJSONObject("data");
                JSONObject data = json.getJSONObject("result");
                Iterator<String> keys = data.keys();
                List<AllTypeTorModel> modallist = new ArrayList<>();
                while (keys.hasNext()) {
                    AllTypeTorModel modelsd = new AllTypeTorModel();
                    List<CircleMemberDetail> listdate = new ArrayList<>();
                    JSONArray ja = data.getJSONArray(keys.next().toString());
//                    for (int i = 0; i < ja.length(); i++) {
                        List<CircleMemberDetail> resultf = (List<CircleMemberDetail>) GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                        for (CircleMemberDetail ds : resultf) {
                            listdate.add(ds);
                            modelsd.setCharac(ds.getName_first());
                        }
//                    }
                    modelsd.setModelList(listdate);
                    modallist.add(modelsd);
                }
                if(firstnumber==0){
                    numberAdapter.clearAll();
                    numberAdapter.setRes(modallist);
                }else{
                    numberAdapter.append(modallist);
                }
//                numberAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
        if(flag==HttpConfig.circle_transfer.getType()){
            Logger.e("lCicircle_transfer-----" + o);
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
//                    ToastTool.show("转让群主成功");
                    PopUpDialog();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("转让失败");
            }
        }

    }

    //发布成功弹窗
    public void PopUpDialog() {   //弹框
        final SgActivityChangeSuccessDialog PopUpDialogs = new SgActivityChangeSuccessDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
//        PopUpDialogs.dismiss();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    PopUpDialogs.dismiss();
                    Intent mIntent = new Intent();
                    setResult(RESULT_OK, mIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    CircleMemberDetail changeMember;
    @Override
    public void onItemConfirm(CircleMemberDetail model, int po) {
        Logger.e("positonddddddd----------------------------" + model.getId() + "----" + po);
        if(model!=null){
            selectMember.setVisibility(View.VISIBLE);
            Glide.with(this).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
            nameTextView.setText(model.getName());
            if(model.getSex().equals("0")){
                typeImage.setImageDrawable(getResources().getDrawable(R.drawable.women));
            }else{
                typeImage.setImageDrawable(getResources().getDrawable(R.drawable.male));
            }
            addressTextView.setText(model.getLocation());
        }
        changeMember=model;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmText:
                if(changeMember!=null){
                    if(UserPreference.getTOKEN()!=null&UserPreference.getTOKEN().length()>1){
                        GuiMiController.getInstance().circle_transfer(this,circlemodel.getId(),changeMember.getId(),UserPreference.getTOKEN());
                    }else{
                        ToastTool.show("你还没有登录");
                    }
                }else{
                    ToastTool.show("转让失败");
                }

//                PopUpDialog();
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(circlemodel.getId()), firstnumber, size);
    }
}
