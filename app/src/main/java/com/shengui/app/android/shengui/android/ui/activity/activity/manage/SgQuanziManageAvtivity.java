package com.shengui.app.android.shengui.android.ui.activity.activity.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgGuiQuanManagerOfficalActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.CircleMemberListModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.SectionModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/27.
 */

public class SgQuanziManageAvtivity extends BaseActivity implements View.OnClickListener {
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
    @Bind(R.id.testname)
    TextView testname;
    @Bind(R.id.quanzhuImageTest)
    ImageView quanzhuImageTest;
    @Bind(R.id.quanzhuTest)
    TextView quanzhuTest;
    @Bind(R.id.NoneImageTest)
    ImageView NoneImageTest;
    @Bind(R.id.noneTest)
    TextView noneTest;
    @Bind(R.id.typename)
    TextView typename;
    @Bind(R.id.activity_sele)
    TextView activitySele;
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.changeQuanzhuText)
    TextView changeQuanzhuText;
//    @Bind(R.id.joinQuanziLayout)
//    TextView joinQuanziLayout;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.personLayout)
    LinearLayout personLayout;
    @Bind(R.id.sectionOne)
    TextView sectionOne;
    @Bind(R.id.sectionTwo)
    TextView sectionTwo;
    @Bind(R.id.sectionThree)
    TextView sectionThree;
    private String testType = "圈主审核";
    int model;
    QuanziList modelDetail;
    String plate="";
    private RelativeLayout reservedLayout;
    private static final int backflage = 1211;
    @Override
    protected void initView() {

        ButterKnife.bind(this);
        quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
        NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
    }

    @Override
    protected void initEvent() {
        topLayout.setOnClickListener(this);
        NoneImageTest.setOnClickListener(this);
        quanzhuImageTest.setOnClickListener(this);
        noneTest.setOnClickListener(this);
        quanzhuTest.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        titlenameLayout.setOnClickListener(this);
        changeQuanzhuText.setOnClickListener(this);
//        joinQuanziLayout.setOnClickListener(this);
        activitySele.setOnClickListener(this);
        allLayout.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == backflage && resultCode == 12313) {
            if(StringTools.isNullOrEmpty((String)data.getSerializableExtra("modelist"))){
                return;
            }
            String date = data.getStringExtra("modelist");
            Logger.e("dateresult" + date);
            plate = date;
            try {

                Logger.e("date" + GsonTool.jsonToStringArrayEntity(date));
                List<String> jsonArray = GsonTool.jsonToStringArrayEntity(date);
                    Logger.e("sixesizesize"+jsonArray.size());
                    switch (jsonArray.size()) {
                        case 0:
                            sectionOne.setVisibility(View.GONE);
                            sectionTwo.setVisibility(View.GONE);
                            sectionThree.setVisibility(View.GONE);
                            break;
                        case 1:
                            JSONObject ja = new JSONObject(jsonArray.get(0));
                            sectionOne.setText(ja.getString("title"));
                            sectionOne.setVisibility(View.VISIBLE);
                            sectionTwo.setVisibility(View.GONE);
                            sectionThree.setVisibility(View.GONE);
                            break;
                        case 2:
                            JSONObject jaas = new JSONObject(jsonArray.get(0));
                            sectionOne.setText(jaas.getString("title"));
                            sectionOne.setVisibility(View.VISIBLE);
                            JSONObject jams = new JSONObject(jsonArray.get(1));
                            sectionTwo.setText(jams.getString("title"));
                            sectionTwo.setVisibility(View.VISIBLE);
                            sectionThree.setVisibility(View.GONE);
                            break;
                        case 3:
                            JSONObject jaasc = new JSONObject(jsonArray.get(0));
                            sectionOne.setText(jaasc.getString("title"));
                            sectionOne.setVisibility(View.VISIBLE);
                            JSONObject jamsv = new JSONObject(jsonArray.get(1));
                            sectionTwo.setText(jamsv.getString("title"));
                            sectionTwo.setVisibility(View.VISIBLE);
                            JSONObject jamsvd = new JSONObject(jsonArray.get(2));
                            sectionThree.setText(jamsvd.getString("title"));
                            sectionThree.setVisibility(View.VISIBLE);
                            break;
                    }
                JSONArray js=new JSONArray();

                Logger.e("asection"+modelDetail.getSection().size()+"----"+jsonArray.size());
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject j = new JSONObject(jsonArray.get(i));
                    Logger.e("m.jsonArray()-title---"+j.getString("title"));
                }

                if(jsonArray.size()==0){
                    for(int i=0;i<modelDetail.getSection().size();i++){
                        JSONObject ja = new JSONObject();
                        ja.put("title",modelDetail.getSection().get(i).getTitle());
                        ja.put("circle_id", modelDetail.getSection().get(i).getCircle_id());
                        ja.put("type", "del");
                        ja.put("id",  modelDetail.getSection().get(i).getId());
                        js.put(ja);
                    }
                }
                else if(modelDetail.getSection().size()==jsonArray.size()){
                    Logger.e("qeical");
                    for(int i=0;i<modelDetail.getSection().size();i++){
                        JSONObject ja = new JSONObject();
                        JSONObject j = new JSONObject(jsonArray.get(i));
                        ja.put("title", j.getString("title"));
                        ja.put("circle_id", modelDetail.getSection().get(i).getCircle_id());
                        ja.put("type", "edit");
                        ja.put("id",  modelDetail.getSection().get(i).getId());
                        js.put(ja);
                    }
                }
                else if(modelDetail.getSection().size()>jsonArray.size()){
                    Logger.e("modelDetail.getSection().size()");
                    for(int i=0;i<modelDetail.getSection().size();i++){
                        if(i<jsonArray.size()){
                            JSONObject ja = new JSONObject();
                            JSONObject j = new JSONObject(jsonArray.get(i));
                            ja.put("title", j.getString("title"));
                            ja.put("circle_id", modelDetail.getSection().get(i).getCircle_id());
                            ja.put("type", "edit");
                            ja.put("id",  modelDetail.getSection().get(i).getId());
                            js.put(ja);
                        }else{
                            JSONObject ja = new JSONObject();
                            ja.put("title",  modelDetail.getSection().get(i).getTitle());
                            ja.put("circle_id", modelDetail.getSection().get(i).getCircle_id());
                            ja.put("type", "del");
                            ja.put("id",  modelDetail.getSection().get(i).getId());
                            js.put(ja);
                        }
                    }
                }
                else {
                    Logger.e("jsonArray.size()");
                    for(int i=0;i<jsonArray.size();i++){
                        if(i<modelDetail.getSection().size()){
                            JSONObject ja = new JSONObject();
                            JSONObject j = new JSONObject(jsonArray.get(i));
                            ja.put("title", j.getString("title"));
                            ja.put("circle_id", modelDetail.getSection().get(i).getCircle_id());
                            ja.put("type", "edit");
                            ja.put("id",  modelDetail.getSection().get(i).getId());
                            js.put(ja);
                        }else{
                            JSONObject ja = new JSONObject();
                            JSONObject j = new JSONObject(jsonArray.get(i));
                            ja.put("circle_id", modelDetail.getId());
                            ja.put("title", j.getString("title"));
                            ja.put("type", "add");
                            js.put(ja);
                        }
                    }
                }
//                if(modelDetail.getSection().size()>=jsonArray.size()){
//                    try{
//                        for(SectionModel m:modelDetail.getSection()) {
//                            Logger.e("m.getTitle()----"+m.getTitle());
//                            Logger.e("j.getStrin----"+m.getId()+m.getTitle());
//                            for (int i = 0; i < jsonArray.size(); i++) {
//                                JSONObject j = new JSONObject(jsonArray.get(i));
//                                JSONObject ja = new JSONObject();
//                                Logger.e("-------------------"+ja.getString("title"));
//                                if(m.getTitle().equals(ja.getString("title"))){
//                                    ja.put("title", m.getTitle());
//                                    ja.put("circle_id", m.getCircle_id());
//                                    ja.put("type", "edit");
//                                    ja.put("id", m.getId());
//                                    js.put(ja);
//                                    continue;
//                                }else{
//                                    ja.put("title", j.getString("title"));
//                                    ja.put("type", "add");
//                                    js.put(ja);
//                                }
//
//                            }
//                        }
//                    }catch (Exception e){
//                        Logger.e("exceptiopn"+e.getMessage());
//                    }
//
//                }else{
//                    for(int i=0;i<jsonArray.size();i++){
//                        JSONObject j = new JSONObject(jsonArray.get(i));
//                        JSONObject ja=new JSONObject();
//                        ja.put("title",j.getString("title"));
//                        ja.put("circle_id",modelDetail.getId());
//                        ja.put("type","add");
//                        for(SectionModel m:modelDetail.getSection()){
//                            Logger.e("title----"+j.getString("title"));
//                            Logger.e("m.getTitle()----"+m.getTitle());
//                            Logger.e("j.getStrin----"+j.getString("title").equals(m.getTitle()));
//                            if(j.getString("title").equals(m.getTitle())){
//                                ja.put("type","del");
//                                ja.put("id",m.getId());
//                                continue;
//                            }else{
//                                ja.put("type","add");
//                            }
//                        }
//                        js.put(ja);
//                    }
//                }
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    GuiMiController.getInstance().EditCircleSection(this,UserPreference.getTOKEN(),Integer.parseInt(modelDetail.getId()),js.toString());
                }else{
                    ToastTool.show("修改失败，您还没有登录");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        if(resultCode==RESULT_OK&&requestCode==1232){
            Intent mIntent = new Intent();
            setResult(1217, mIntent);
            finish();
        }
    }
    @Override
    protected void initData() {
        personLayout.removeAllViews();
        if (getIntent().getSerializableExtra("CircleId") != null) {
            model = (int) getIntent().getSerializableExtra("CircleId");
            GuiMiController.getInstance().CiecleContentDetail(this, model, UserPreference.getUid());
            GuiMiController.getInstance().CircleMemberList(this, model, 0, 6);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        GuiMiController.getInstance().CiecleContentDetail(this, model, UserPreference.getUid());
//        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quanzhu_manage_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);

            modelDetail = (QuanziList) result;
            Logger.e("logger" + modelDetail);
            QuanZiNameText.setText(modelDetail.getTitle());
            NumberTextView.setText(modelDetail.getMember_num());
            tiezaiNumberText.setText(modelDetail.getPost_num());
            addressQuanziText.setText(modelDetail.getCity_name());
            if (Integer.parseInt(modelDetail.getMember_audit()) == 0) {
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
            } else if (Integer.parseInt(modelDetail.getMember_audit()) == 1) {
                testType = "无需审核";
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
            } else {
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
            }
            if (modelDetail.getSection().size() != 0) {
                sectionOne.setVisibility(View.GONE);
                sectionTwo.setVisibility(View.GONE);
                sectionThree.setVisibility(View.GONE);
                switch (modelDetail.getSection().size()) {
                    case 1:
                        sectionOne.setText(modelDetail.getSection().get(0).getTitle());
                        sectionOne.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        sectionOne.setText(modelDetail.getSection().get(0).getTitle());
                        sectionOne.setVisibility(View.VISIBLE);
                        sectionTwo.setText(modelDetail.getSection().get(1).getTitle());
                        sectionTwo.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        sectionOne.setText(modelDetail.getSection().get(0).getTitle());
                        sectionOne.setVisibility(View.VISIBLE);
                        sectionTwo.setText(modelDetail.getSection().get(1).getTitle());
                        sectionTwo.setVisibility(View.VISIBLE);
                        sectionThree.setText(modelDetail.getSection().get(2).getTitle());
                        sectionThree.setVisibility(View.VISIBLE);
                        break;
                }
            }
            try {
                Glide.with(this).load(modelDetail.getAvatar()).asBitmap().placeholder(R.drawable.default_image_av).into(personInfoIv);
            } catch (Exception e) {
                Logger.e("sd" + e.getMessage());
            }
            titleEt.setText(modelDetail.getDesc());
        }
        if (flag == HttpConfig.Circlepprove.getType()) {
            Logger.e("logger" + result);
            personLayout.removeAllViews();

            List<CircleMemberDetail> model=new ArrayList<>();
//            CircleMemberListModel model = (CircleMemberListModel) result;
            try {
                JSONObject jsonObject=new JSONObject(o.toString());
                JSONObject json=jsonObject.getJSONObject("data");
                if(json.getJSONObject("main")!=null){
                    JSONObject jsonObject1od=json.getJSONObject("main");
                    String JaAver=jsonObject1od.getString("avatar");

                    Logger.e("avavavv--"+JaAver);
                    reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_manage_activity, null);
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgQuanziManageAvtivity.this);
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
                    while(keys.hasNext()){
                        JSONArray ja = data.getJSONArray(keys.next().toString());
//                    for(int i=0;i<ja.length();i++){
                        List<CircleMemberDetail> resultf=(List<CircleMemberDetail>)GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                        for(CircleMemberDetail ds:resultf){
                            model.add(ds);
                        }
//                    }
                    }
                }
                if (model.size() != 0) {
                    for (CircleMemberDetail h: model) {
                        Logger.e("cieldlee" + h.getAvatar());

                        reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_manage_activity, null);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgQuanziManageAvtivity.this);
                        reservedLayout.setLayoutParams(layoutParams);

                        CircleImageView view = (CircleImageView) reservedLayout.findViewById(R.id.personInfoIvItemd);
                        try {
                            Glide.with(this).load(h.getAvatar()).asBitmap().placeholder(R.drawable.default_pictures).into(view);
                        } catch (Exception e) {
                            Logger.e("sd" + e.getMessage());
                        }
                        personLayout.addView(reservedLayout);
                    }
                }
            } catch (Exception e) {
                Logger.e("exception"+e.getMessage());
            }
//            for(CircleMemberDetail m:model){
//                Logger.e("mnodoe"+m.getId());
//            }


        }
        if(flag==HttpConfig.EditCircle.getType()){
            GuiMiController.getInstance().CiecleContentDetail(this, model, UserPreference.getUid());
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.allLayout:
                IntentTools.startNewMember(this,modelDetail);
//                IntentTools.startMember(this,modelDetail.getId());
                break;
            case R.id.backImageView:
                finish();
                break;
            case R.id.titlenameLayout:
                IntentTools.startJoinManage(this,modelDetail);
                break;
            case R.id.quanzhuTest:
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                changeCheckType(0);
                break;
            case R.id.noneTest:
                testType = "无需审核";
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                changeCheckType(1);
                break;
            case R.id.quanzhuImageTest:
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                changeCheckType(0);
                break;
            case R.id.NoneImageTest:
                testType = "无需审核";
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                changeCheckType(1);
                break;
            case R.id.changeQuanzhuText:
                IntentTools.startChangeManager(this,modelDetail,1232);
                break;
//            case R.id.joinQuanziLayout:
//                IntentTools.startQuanzhuJoinManage(this,modelDetail);
//                break;
            case R.id.activity_sele:
                IntentTools.startSelectBankuai(this,backflage,modelDetail);
                break;
            case R.id.topLayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    String titleEtStr=titleEt.getText().toString();
                    if(StringTools.isNullOrEmpty(titleEtStr)){
                        ToastTool.show("修改失败，描述不能为空");
                    }else{
                        GuiMiController.getInstance().EditCircleMemberDesc(new ViewNetCallBack() {
                            @Override
                            public void onConnectStart() {

                            }

                            @Override
                            public void onConnectEnd() {

                            }

                            @Override
                            public void onFail(Exception e) {

                            }

                            @Override
                            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

                                try {
                                    JSONObject ja=new JSONObject(o.toString());
                                    if(ja.getBoolean("status")){
                                        ToastTool.show("修改成功");
                                    }else{
                                        ToastTool.show("修改失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, UserPreference.getTOKEN(), Integer.parseInt(modelDetail.getId()), titleEtStr);
                    }
                }else{
                    ToastTool.show("修改失败，您还没有登录");
                }
                break;
        }
    }
    public void changeCheckType(int flages){
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            GuiMiController.getInstance().EditCircleMember(this,UserPreference.getTOKEN(),Integer.parseInt(modelDetail.getId()),flages);
        }else{
            ToastTool.show("修改失败，您还没有登录");
        }
    }


}
