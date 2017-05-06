package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
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
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineOtherInfoNewActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushTieZiDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.view.MyFragmentManagerPagerAdapter;
import com.shengui.app.android.shengui.android.ui.view.MyFragmentPagerAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.CircleMemberListModel;
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
 * Created by admin on 2016/12/23.
 */

public class SgGuiQuanDetailActivityNewActivity extends BaseActivity implements View.OnClickListener  {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentManagerPagerAdapter myFragmentPagerAdapter;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab five;
    private ImageView backImageView;
    private TextView topLayout;
    private RelativeLayout titlenameLayout,allLayout;
    private TextView NameText,QuanZiNameText,QuanzitypeText,NumberTextView,tiezaiNumberText,addressQuanziText,textDetail;
    private CircleImageView personInfoIv;
    AppBarLayout appbarLayout;
//    private Toolbar mToolBar;
CollapsingToolbarLayout collapsingToolBar;
    private RelativeLayout reservedLayout,Buylayout;
    private LinearLayout personLayout;
    SwipeRefreshLayout swipeLayout;
    private QuanziList model;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        swipeLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
        collapsingToolBar=(CollapsingToolbarLayout)findViewById(R.id.collapsing_tool_bar);
        appbarLayout=(AppBarLayout)findViewById(R.id.appbarLayout);
        Buylayout=(RelativeLayout)findViewById(R.id.Buylayout);

        personLayout=(LinearLayout)findViewById(R.id.personLayout);
        NameText=(TextView)findViewById(R.id.NameText);
        QuanZiNameText=(TextView)findViewById(R.id.QuanZiNameText);
        QuanzitypeText=(TextView)findViewById(R.id.QuanzitypeText);
        NumberTextView=(TextView)findViewById(R.id.NumberTextView);
        tiezaiNumberText=(TextView)findViewById(R.id.tiezaiNumberText);
        addressQuanziText=(TextView)findViewById(R.id.addressQuanziText);
        textDetail=(TextView)findViewById(R.id.textDetail);
        personInfoIv=(CircleImageView)findViewById(R.id.personInfoIv);

        backImageView=(ImageView)findViewById(R.id.backImageView);
        topLayout=(TextView)findViewById(R.id.topLayout);
        titlenameLayout=(RelativeLayout)findViewById(R.id.titlenameLayout);
        allLayout=(RelativeLayout)findViewById(R.id.allLayout);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //隐藏掉系统原先的导航栏
//        mToolBar = (Toolbar) findViewById(R.id.mToolBar);
//        setSupportActionBar(mToolBar);   //把toolbar作为导航栏
        mTabLayout = (TabLayout) findViewById(R.id.tab_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
//        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(myFragmentPagerAdapter);


        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
//        mTabLayout.setupWithViewPager(mViewPager);


    }

    public void onrefresh(){
        GuiMiController.getInstance().CiecleContentDetail(SgGuiQuanDetailActivityNewActivity.this, Integer.parseInt(model.getId()), UserPreference.getUid());
    }

    @Override
    protected void initEvent() {
        backImageView.setOnClickListener(this);
        topLayout.setOnClickListener(this);
        titlenameLayout.setOnClickListener(this);
        allLayout.setOnClickListener(this);
//        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//                if (verticalOffset >= 0) {
//                    swipeLayout.setEnabled(true);
//                } else {
//                    swipeLayout.setEnabled(false);
//                }
//            }
//        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                initData();
                GuiMiController.getInstance().CiecleContentDetail(SgGuiQuanDetailActivityNewActivity.this, Integer.parseInt(model.getId()), UserPreference.getUid());
                GuiMiController.getInstance().CircleMemberList(SgGuiQuanDetailActivityNewActivity.this, Integer.parseInt(model.getId()), 0, 3);
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    swipeLayout.setEnabled(true);
                } else {
                    swipeLayout.setEnabled(false);
                }
            }
        });
//        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (collapsingToolBar.getHeight() + verticalOffset == 0) {
////                    Logger.e("deee");
//                    Buylayout.setVisibility(View.VISIBLE);
//                } else {
////                    Logger.e("deweweeee");
//                    Buylayout.setVisibility(View.GONE);
//                }
//            }
//        });
        Buylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    UserPreference.setTOPICID(model.getId());
                    UserPreference.setTOPICCONTENT(model.getTitle());
                    PopUpDialog();
                } else {
                    ToastTool.show("您还没有登录");
                }
            }
        });
        personInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startBigImage(SgGuiQuanDetailActivityNewActivity.this,model.getAvatar());
            }
        });
    }
    //发布弹框
    public void PopUpDialog() {
        SgActivityPushTieZiDialog PopUpDialogs = new SgActivityPushTieZiDialog(this,model.getTitle(),model.getId(),"circle");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserPreference.getISFINISHPOSR()!=null&&UserPreference.getISFINISHPOSR().length()>1){
            GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getId()), UserPreference.getUid());
            UserPreference.setISFINISHPOSR("");
        }
//        myFragmentPagerAdapter.refresh(model);
//        initData();
//        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getId()), UserPreference.getUid());
    }

    @Override
    protected void initData() {

        if(getIntent()!=null){
            model=(QuanziList)getIntent().getSerializableExtra("model");
            if(model.getSection()!=null&&model.getSection().size()!=0){
                String[] mTitles = new String[model.getSection().size()+2];
                mTitles[0]="全部";
                mTitles[1]="精华";
                for(int i=0;i<model.getSection().size();i++){
                    mTitles[i+2]=model.getSection().get(i).getTitle();
                }
                myFragmentPagerAdapter = new MyFragmentManagerPagerAdapter(getSupportFragmentManager(),mTitles,model);
                mViewPager.setAdapter(myFragmentPagerAdapter);
                mViewPager.setOffscreenPageLimit(4);
            }else{
                String[] mTitles = new String[]{"全部", "精华"};
                myFragmentPagerAdapter = new MyFragmentManagerPagerAdapter(getSupportFragmentManager(),mTitles,model);
                mViewPager.setAdapter(myFragmentPagerAdapter);
                mViewPager.setOffscreenPageLimit(4);
            }
            mTabLayout.setupWithViewPager(mViewPager);
            //指定Tab的位置
            switch (mTabLayout.getTabCount()){
                case 2:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    break;
                case 3:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    break;
                case 4:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    four = mTabLayout.getTabAt(3);
                    break;
                case 5:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    four = mTabLayout.getTabAt(3);
                    five = mTabLayout.getTabAt(4);
                    break;
            }

            NameText.setText(model.getTitle());
            QuanZiNameText.setText(model.getTitle());
            NumberTextView.setText(model.getMember_num());
            tiezaiNumberText.setText(model.getPost_num());
            addressQuanziText.setText(model.getCity_name());
            textDetail.setText(model.getDesc());
            try{
                Glide.with(this).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image_av).into(personInfoIv);
            }catch (Exception e){
                Logger.e("sd"+e.getMessage());
            }
            GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(model.getId()), 0,3);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gui_quan_detail_new_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
//            myFragmentPagerAdapter.init();
            QuanziList modelDetail = (QuanziList) result;
            NameText.setText(modelDetail.getTitle());
            QuanZiNameText.setText(modelDetail.getTitle());
            NumberTextView.setText(modelDetail.getMember_num());
            tiezaiNumberText.setText(modelDetail.getPost_num());
            addressQuanziText.setText(modelDetail.getCity_name());
            textDetail.setText(modelDetail.getDesc());

            if(modelDetail.getSection()!=null&&modelDetail.getSection().size()!=0){
                String[] mTitles = new String[modelDetail.getSection().size()+2];
                mTitles[0]="全部";
                mTitles[1]="精华";
                for(int i=0;i<modelDetail.getSection().size();i++){
                    mTitles[i+2]=modelDetail.getSection().get(i).getTitle();
                }
                myFragmentPagerAdapter = new MyFragmentManagerPagerAdapter(getSupportFragmentManager(),mTitles,modelDetail);
                mViewPager.setAdapter(myFragmentPagerAdapter);
            }else{
                String[] mTitles = new String[]{"全部", "精华"};
                myFragmentPagerAdapter = new MyFragmentManagerPagerAdapter(getSupportFragmentManager(),mTitles,modelDetail);
                mViewPager.setAdapter(myFragmentPagerAdapter);
            }
            mTabLayout.setupWithViewPager(mViewPager);
            //指定Tab的位置
            switch (mTabLayout.getTabCount()){
                case 2:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    break;
                case 3:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    break;
                case 4:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    four = mTabLayout.getTabAt(3);
                    break;
                case 5:
                    one = mTabLayout.getTabAt(0);
                    two = mTabLayout.getTabAt(1);
                    three = mTabLayout.getTabAt(2);
                    four = mTabLayout.getTabAt(3);
                    five = mTabLayout.getTabAt(4);
                    break;
            }


        }
        if (flag == HttpConfig.Circlepprove.getType()) {
            Logger.e("logger" + result);
            personLayout.removeAllViews();
            List<CircleMemberDetail> model = new ArrayList<>();
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
                    reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgGuiQuanDetailActivityNewActivity.this);
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
                            reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(SgGuiQuanDetailActivityNewActivity.this);
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



            } catch (Exception e) {
                Logger.e("exception"+e.getMessage());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1217){
            Logger.e("123----");
            model.setUser_id("");
        }
        if(requestCode==1121){
            GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getId()), UserPreference.getUid());
        }
        if(resultCode==RESULT_OK&&requestCode==123){

            if(data.getSerializableExtra("variety_id")!=null){
                String datasdfs=(String)data.getSerializableExtra("variety_id");
                if(datasdfs.equals("1")){
                    finish();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backImageView:
                finish();
                break;
            case R.id.topLayout:
                IntentTools.startTopicList(this);
                break;
            case R.id.titlenameLayout:
//                IntentTools.startquanziDetail(this,model);
                Logger.e("reetretetete------------"+model.toString());
                Logger.e("reetretetete"+model.getUser_id()+UserPreference.getUid());
                if (Integer.parseInt(model.getUser_id()) == UserPreference.getUid()) {
                    IntentTools.startQuanziManage(this, Integer.parseInt(model.getId()),1121);
                }else{
                    IntentTools.startQuanziBackDetailSelf(this,model,123);
                }
                break;
            case R.id.allLayout:
                IntentTools.startNewMember(this,model);
                break;
        }

    }
}
