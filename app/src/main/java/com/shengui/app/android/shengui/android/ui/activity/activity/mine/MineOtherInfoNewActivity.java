package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.view.MineInfoPagerAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class MineOtherInfoNewActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    MineInfoPagerAdapter adapter;
    @Bind(R.id.tab_main)
    TabLayout tabMain;
    @Bind(R.id.iamge)
    View iamge;
    @Bind(R.id.vp_main)
    ViewPager vpMain;
    @Bind(R.id.bottomLayout)
    LinearLayout bottomLayout;
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.QuanZiNameText)
    TextView QuanZiNameText;
    @Bind(R.id.sexImages)
    ImageView sexImages;
    @Bind(R.id.topLauout)
    RelativeLayout topLauout;
    @Bind(R.id.addressQuanziText)
    TextView addressQuanziText;
    @Bind(R.id.titlenameLayout)
    RelativeLayout titlenameLayout;
    @Bind(R.id.allTextView)
    TextView allTextView;
    @Bind(R.id.allTextViewNumber)
    TextView allTextViewNumber;
    @Bind(R.id.aii)
    RelativeLayout aii;
    @Bind(R.id.listView)
    LinearLayout listView;
    @Bind(R.id.allLayout)
    RelativeLayout allLayout;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.chatTv)
    TextView chatTv;
    @Bind(R.id.focusTv)
    TextView focusTv;
    @Bind(R.id.bomLayout)
    LinearLayout bomLayout;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;

    private EmptyLayout mEmptyLayout;
    private RelativeLayout reservedLayout;
    String headPath;
    private String aver;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personInfoIv:
                if(!StringTools.isNullOrEmpty(headPath)){

                    IntentTools.startBigImage(this,headPath);
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.chatTv:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    if (otherUserId!=UserPreference.getUid()) {
                        IntentTools.startChat(this, otherUserId, name,aver);
                    }else{
                        ToastTool.show("不能和自己私聊");
                    }
                } else {
                  IntentTools.startLogin(this);
                }

                break;
            case R.id.focusTv:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    if (focusTv.getText().equals("关注")) {
                        if (otherUserId!=UserPreference.getUid()) {
                            focusTv.setText("取消关注");
                            MineInfoController.getInstance().Attentionadd(this, otherUserId + "", UserPreference.getTOKEN());
                        }else{
                            ToastTool.show("不能关注自己");
                        }

                    } else {
                        if (otherUserId!=UserPreference.getUid()) {
                            focusTv.setText("关注");
                            MineInfoController.getInstance().Attentiondel(this, otherUserId + "", UserPreference.getTOKEN());
                        }else{
                            ToastTool.show("不能关注自己");
                        }
                    }
                } else {
                    IntentTools.startLogin(this);
                }
                break;
        }
    }

    private String name;
    private int otherUserId;


    private String supplynumber;
    private String postnumber;

    public void setCount(){
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabMain.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.activity_tablayout_activity);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);//第一个tab被选中
            }
            if (i == 0) {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("他的供求");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                TextView textViewnumber = (TextView) tab.getCustomView().findViewById(R.id.gongNumber);
                textViewnumber.setText(supplynumber);//设置tab上的文字
                textViewnumber.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
            } else {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("他的动态");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                TextView textViewnumber = (TextView) tab.getCustomView().findViewById(R.id.gongNumber);
                textViewnumber.setText(postnumber);//设置tab上的文字
                textViewnumber.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        otherUserId = (int) getIntent().getSerializableExtra("otherinfo");
        String[] strings = getResources().getStringArray(R.array.mine_info);
        adapter = new MineInfoPagerAdapter(getSupportFragmentManager(), strings, otherUserId + "");
        vpMain.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        tabMain.setupWithViewPager(vpMain);



        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                TextView textViewnumber = (TextView) tab.getCustomView().findViewById(R.id.gongNumber);
                textViewnumber.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                if (tab.getPosition() == 0) {
                    textViewnumber.setText(supplynumber);
                } else {
                    textViewnumber.setText(postnumber);//设置tab上的文字
                }
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(false);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                TextView textViewnumber = (TextView) tab.getCustomView().findViewById(R.id.gongNumber);
                if (tab.getPosition() == 0) {
                    textViewnumber.setText(supplynumber);
                } else {
                    textViewnumber.setText(postnumber);//设置tab上的文字
                }
                textViewnumber.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        mEmptyLayout.showLoading();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
//        mEmptyLayout.showError();
    }

    @Override
    protected void initEvent() {
        focusTv.setOnClickListener(this);
        chatTv.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    swipeLayout.setEnabled(true);
                } else {
                    swipeLayout.setEnabled(false);
                }
            }
        });
//        mEmptyLayout = new EmptyLayout(this, swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                initData();
                update();

            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        personInfoIv.setOnClickListener(this);
    }

    public void update(){
        MineInfoController.getInstance().get_my_fullinfoById(new ViewNetCallBack() {
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
                Logger.e("loggegetmyfullinfor" + o.toString());
                try {
                    JSONObject object = new JSONObject(o.toString());
                    if (object.getBoolean("status")) {
                        JSONObject ja = object.getJSONObject("data");
                        Logger.e("datajagetmyfullinfoa" + ja);
                        if (ja.getString("is_attention").equals("0")) {
                            focusTv.setText("关注");
                            adapter.setFocus(false);
                        } else {
                            focusTv.setText("取消关注");
                            adapter.setFocus(true);
                        }
                        adapter.adapterRefresh();
                    }
                } catch (Exception e) {
                }
            }
        }, otherUserId + "", UserPreference.getTOKEN());
        MineInfoController.getInstance().usual_circle(MineOtherInfoNewActivity.this, otherUserId + "");

    }

    @Override
    protected void initData() {
        if (getIntent().getSerializableExtra("otherinfo") != null) {
            MineInfoController.getInstance().get_my_fullinfoById(this, otherUserId + "", UserPreference.getTOKEN());
            MineInfoController.getInstance().usual_circle(this, otherUserId + "");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_new_other_activity;
    }



    public class OnCircleListener implements View.OnClickListener{
        QuanziList quanzimodel;
        public OnCircleListener( QuanziList quanzimo){
            quanzimodel=quanzimo;
        }
        @Override
        public void onClick(View v) {
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                GuiMiController.getInstance().CiecleContentDetail(new ViewNetCallBack() {
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
                        if (flag == HttpConfig.CircleDetail.getType()) {
                            Logger.e("logger" + result);
                            QuanziList QuanZiModel = (QuanziList) result;

                            //官方圈子
                            if (QuanZiModel.getIs_public().equals("1")) {
                                IntentTools.startQuanziManageOffical(MineOtherInfoNewActivity.this, QuanZiModel);
                                return;
                            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(this, Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
                            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                                IntentTools.startQuanziDetailSelf(MineOtherInfoNewActivity.this, QuanZiModel);
                                return;
                            }
                            if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                                IntentTools.startquanziDetail(MineOtherInfoNewActivity.this, QuanZiModel);
                                return;
                            }
                            if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                                IntentTools.startQuanziDetailSelf(MineOtherInfoNewActivity.this, QuanZiModel);
                                return;
                            }
                        }
                    }
                }, Integer.parseInt(quanzimodel.getId()), UserPreference.getUid());
            } else {
                IntentTools.startLogin(MineOtherInfoNewActivity.this);
            }
        }
    }
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
//        mEmptyLayout.showSuccess(false);
        if (flag == HttpConfig.usual_circle.getType()) {
            Logger.e("loggeusual_circler" + o.toString());
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    listView.removeAllViews();
                    JSONArray ja = object.getJSONArray("data");
                  final   List<QuanziList> listmodel = GsonTool.jsonToArrayEntity(ja.toString(), QuanziList.class);
//                    for(QuanziList i:listmodel){
//                        Logger.e("dfdfd"+i.getId()+i.getTitle());
//                    }
                    if(listmodel.size()<3){
                        allTextViewNumber.setText(listmodel.size() + "");
                    }else{
                        allTextViewNumber.setText(3+ "");
                    }
                    if (listmodel != null && listmodel.size() > 0) {
                        if(listmodel.size()<=3){
                            for ( int i = 0; i < listmodel.size(); i++) {
                                reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_usural_circle_activity, null);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(MineOtherInfoNewActivity.this);
                                reservedLayout.setLayoutParams(layoutParams);
                                CircleImageView view = (CircleImageView) reservedLayout.findViewById(R.id.personInfoIvItemd);
                                TextView textView = (TextView) reservedLayout.findViewById(R.id.textview);
                                try {
                                    view.setOnClickListener(new OnCircleListener(listmodel.get(i)));
                                    textView.setText(listmodel.get(i).getTitle());
                                    Glide.with(this).load(listmodel.get(i).getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(view);
                                } catch (Exception e) {
                                    Logger.e("sd" + e.getMessage());
                                }
                                listView.addView(reservedLayout);
                            }
                        }else{
                            for ( int i = 0; i < 3; i++) {
                                reservedLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_person_usural_circle_activity, null);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        reservedLayout.findViewById(R.id.personInfoIvItemd).setOnClickListener(MineOtherInfoNewActivity.this);
                                reservedLayout.setLayoutParams(layoutParams);
                                CircleImageView view = (CircleImageView) reservedLayout.findViewById(R.id.personInfoIvItemd);
                                TextView textView = (TextView) reservedLayout.findViewById(R.id.textview);
                                try {
                                    view.setOnClickListener(new OnCircleListener(listmodel.get(i)));
                                    textView.setText(listmodel.get(i).getTitle());
                                    Glide.with(this).load(listmodel.get(i).getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(view);
                                } catch (Exception e) {
                                    Logger.e("sd" + e.getMessage());
                                }
                                listView.addView(reservedLayout);
                            }


                        }

                    }

                }
            } catch (Exception e) {
            }
        }
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

            //官方圈子
            if (QuanZiModel.getIs_public().equals("1")) {
                IntentTools.startQuanziManageOffical(this, QuanZiModel);
                return;
            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(this, Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                IntentTools.startquanziDetail(this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                IntentTools.startQuanziDetailSelf(this, QuanZiModel);
                return;
            }
        }
        if (flag == HttpConfig.getmyfullinfo.getType()) {
            Logger.e("loggegetmyfullinfor" + o.toString());
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    JSONObject ja = object.getJSONObject("data");
                    Logger.e("datajagetmyfullinfoa" + ja);
                    Glide.with(this).load(ja.getString("avatar")).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
                     headPath=ja.getString("avatar");
                    QuanZiNameText.setText(ja.getString("name"));
                    name = ja.getString("name");
                    aver=ja.getString("avatar");
                    Logger.e("sdfsf-------------------"+ja.getString("is_attention"));
                    Logger.e("sdfsf-------------------"+ja.getString("is_attention").equals("0"));
                    if (ja.getString("is_attention").equals("0")) {
                        focusTv.setText("关注");
                        adapter.setFocus(false);
                    } else {
                        focusTv.setText("取消关注");
                        adapter.setFocus(true);
                    }
                    supplynumber = ja.getString("supply_num");
                    postnumber = ja.getString("post_num");
                    setCount();
                    if (ja.getString("sex").equals("0")) {
                        sexImages.setImageDrawable(getResources().getDrawable(R.drawable.women));
                    } else {
                        sexImages.setImageDrawable(getResources().getDrawable(R.drawable.male));
                    }
                    if(ja.getString("city_id").equals("0")){
                        addressQuanziText.setVisibility(View.GONE);
                    }else{
                        addressQuanziText.setVisibility(View.VISIBLE);
                        addressQuanziText.setText(ja.getString("city"));
                    }
                }
            } catch (Exception e) {
            }
        }
        if(flag==HttpConfig.Attentionadd.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    update();
                }
            }catch (Exception e){

            }


        }
        if(flag==HttpConfig.Attentiondel.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    update();
                }
            }catch (Exception e){

            }


        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
