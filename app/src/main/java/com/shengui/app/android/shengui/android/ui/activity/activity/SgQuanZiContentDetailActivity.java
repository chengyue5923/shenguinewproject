package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanziManageAvtivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvatiFriendsOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.CircleMemberListModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

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
 * Created by admin on 2016/12/26.
 */

public class SgQuanZiContentDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.backImageView)
    ImageView backImageView;
    @Bind(R.id.NameText)
    TextView NameText;
    @Bind(R.id.topLayout)
    TextView topLayout;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.nextDetailLayout)
    ImageView nextDetailLayout;
    @Bind(R.id.QuanZiNameText)
    TextView QuanZiNameText;
    @Bind(R.id.QuanzitypeText)
    TextView QuanzitypeText;
    @Bind(R.id.topLauout)
    RelativeLayout topLauout;
    @Bind(R.id.NumberTextView)
    TextView NumberTextView;
    @Bind(R.id.tiezaiNumberText)
    TextView tiezaiNumberText;
    @Bind(R.id.addressQuanziText)
    TextView addressQuanziText;
    @Bind(R.id.textDetail)
    TextView textDetail;
    @Bind(R.id.titlenameLayout)
    RelativeLayout titlenameLayout;
    @Bind(R.id.allTextView)
    TextView allTextView;
    @Bind(R.id.personInfoIvItemd)
    CircleImageView personInfoIvItemd;
    @Bind(R.id.personOnelayout)
    RelativeLayout personOnelayout;
    @Bind(R.id.personInfoIvItemtwo)
    CircleImageView personInfoIvItemtwo;
    @Bind(R.id.personTwolayout)
    RelativeLayout personTwolayout;
    @Bind(R.id.personInfoIvthree)
    CircleImageView personInfoIvthree;
    @Bind(R.id.personThreelayout)
    RelativeLayout personThreelayout;
    @Bind(R.id.personInfoIvfour)
    CircleImageView personInfoIvfour;
    @Bind(R.id.personfourlayout)
    RelativeLayout personfourlayout;
    @Bind(R.id.personInfoIvfive)
    CircleImageView personInfoIvfive;
    @Bind(R.id.personfivelayout)
    RelativeLayout personfivelayout;
    @Bind(R.id.personInfoIvItemdsix)
    CircleImageView personInfoIvItemdsix;
    @Bind(R.id.personsixlayout)
    RelativeLayout personsixlayout;
    @Bind(R.id.allLayout)
    RelativeLayout allLayout;
    @Bind(R.id.contentDetailText)
    TextView contentDetailText;
    @Bind(R.id.reportText)
    TextView reportText;
    @Bind(R.id.contentlayout)
    LinearLayout contentlayout;
    @Bind(R.id.exitText)
    TextView exitText;
    @Bind(R.id.JoinQuznIText)
    TextView JoinQuznIText;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.personLayout)
    LinearLayout personLayout;
    private RelativeLayout reservedLayout;
    private QuanziList models;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {
        allLayout.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        topLayout.setOnClickListener(this);
        exitText.setOnClickListener(this);
        JoinQuznIText.setOnClickListener(this);
        reportText.setOnClickListener(this);
        personInfoIv.setOnClickListener(this);
    }

    boolean falgNoShowBottom=false;
    @Override
    protected void initData() {

        if (getIntent() != null) {
            models = (QuanziList) getIntent().getSerializableExtra("models");
            if(getIntent().getSerializableExtra("flag")!=null){
                falgNoShowBottom=(boolean)getIntent().getSerializableExtra("flag");
            }
            if(falgNoShowBottom){
                JoinQuznIText.setVisibility(View.GONE);
            }else{
                JoinQuznIText.setVisibility(View.VISIBLE);
            }
            NameText.setText(models.getTitle());
            QuanZiNameText.setText(models.getTitle());
            NumberTextView.setText(models.getMember_num());
            tiezaiNumberText.setText(models.getPost_num());
            addressQuanziText.setText(models.getCity_name());
            contentDetailText.setText(models.getDesc());
            if(models.getIs_in()!=null){
                if(Integer.parseInt(models.getIs_in())==0){
                    exitText.setVisibility(View.GONE);
                    JoinQuznIText.setText("申请加入");
                }else{
                    JoinQuznIText.setText("进入圈子");
                }
            }
            try {
                Glide.with(this).load(models.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
            } catch (Exception e) {
                Logger.e("sd" + e.getMessage());
            }
            GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(models.getId()), 0, 3);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quanzi_content_detail_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.Circlepprove.getType()) {
            Logger.e("logger" + o);


            personLayout.removeAllViews();
//            CircleMemberListModel model = (CircleMemberListModel) result;

            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                JSONObject json=jsonObject.getJSONObject("data");
                if(json.getJSONObject("main")!=null){
                    JSONObject jsonObject1od=json.getJSONObject("main");
                    String JaAver=jsonObject1od.getString("avatar");

                    Logger.e("avavavv--"+JaAver);
                    reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_manage_activity, null);
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgQuanZiContentDetailActivity.this);
                    reservedLayout.setLayoutParams(layoutParams);
                    CircleImageView view = (CircleImageView) reservedLayout.findViewById(R.id.personInfoIvItemd);
                    try {
                        Glide.with(this).load(JaAver).asBitmap().placeholder(R.drawable.default_image).into(view);
                    } catch (Exception e) {
                        Logger.e("sd" + e.getMessage());
                    }
                    personLayout.addView(reservedLayout);

                }
                if(json.getJSONObject("result")!=null){
                    JSONObject data=json.getJSONObject("result");
                    Iterator<String> keys=data.keys();
                    List<CircleMemberDetail> model=new ArrayList<>();
                    while(keys.hasNext()){
                        JSONArray ja = data.getJSONArray(keys.next().toString());
//                    for(int i=0;i<ja.length();i++){
                        List<CircleMemberDetail> resultf=(List<CircleMemberDetail>) GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                        for(CircleMemberDetail ds:resultf){
                            model.add(ds);
                        }
//                    }
                    }
                    if (model.size() != 0) {
                        for (CircleMemberDetail h : model) {
                            Logger.e("cieldlee" + h.getAvatar());

                            reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_manage_activity, null);
                            LinearLayout.LayoutParams layoutParams =
                                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgQuanZiContentDetailActivity.this);
                            reservedLayout.setLayoutParams(layoutParams);

                            CircleImageView view = (CircleImageView) reservedLayout.findViewById(R.id.personInfoIvItemd);
                            try {
                                Glide.with(this).load(h.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(view);
                            } catch (Exception e) {
                                Logger.e("sd" + e.getMessage());
                            }
                            personLayout.addView(reservedLayout);
                        }
                }

                }


            }catch (Exception e){
                Logger.e("exception"+e.getMessage());
            }


        }
        if(flag==HttpConfig.ApplyJoinCircle.getType()){

            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    if(models.getMember_audit().equals("1")){
//                        IntentTools.startQuanziDetailSelf(this,models);
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(models.getId()), UserPreference.getUid());
                    }else{
                        ToastTool.show("等待群主审核");
                    }

                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("申请失败");
            }
        }
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
//            myFragmentPagerAdapter.init();
            QuanziList modelDetail = (QuanziList) result;
            IntentTools.startQuanziDetailSelf(this,modelDetail);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personInfoIv:
                if(!StringTools.isNullOrEmpty(models.getAvatar())){
                    IntentTools.startBigImage(this,models.getAvatar());
                }
                break;
            case R.id.backImageView:
                finish();
                break;
            case R.id.topLayout:
                ShareJinghuaPopUpDialog();
                break;
            case R.id.exitText:

                break;
            case R.id.JoinQuznIText:

                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(Integer.parseInt(models.getIs_in())==0){
                        Logger.e("JoinQuznIText.setText申请加入");

//                    if(models.getMember_audit().equals(""))
                        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
//                        if(models.getMember_audit().equals("1")){
//                            IntentTools.startQuanziDetailSelf(this,models);
                            GuiMiController.getInstance().ApplyJoinCircle(this,UserPreference.getTOKEN(),Integer.parseInt(models.getId()));
//                        }else{
//                            GuiMiController.getInstance().ApplyJoinCircle(this,UserPreference.getTOKEN(),Integer.parseInt(models.getId()));
//                        }
                        }else{
                            ToastTool.show("您还没有登录");
                        }
                    }else{
                        Logger.e("JoinQuznIText.进入圈子");
                        IntentTools.startQuanziDetailSelf(this,models);
                    }
//                IntentTools.startQuanziDetailSelf(this,models);
                }else{
                    IntentTools.startLogin(this);
                }

                break;
            case R.id.reportText:
                ShareReportPopUpDialog();
                break;
            case R.id.allLayout:
                IntentTools.startNewMember(this,models);
                break;
        }
    }

    //邀请好友弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvatiFriendsOtherPopUpDialog PopUpDialogs = new ShareInvatiFriendsOtherPopUpDialog(this,models);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog() {   //弹框
        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(this,models,"");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

}
