package com.shengui.app.android.shengui.android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgSelectItemActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMEvent;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageEntity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushGongQiuDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.BannerView;
import com.shengui.app.android.shengui.android.ui.utilsview.ImageTouchPagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.WindowAdapter;
import com.shengui.app.android.shengui.android.ui.view.MineSelectPagerAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.dao.MessageDao;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CityModel;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yanbo on 16-7-11.
 */
public class SelectedFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener, ViewNetCallBack {

    @Bind(R.id.selectItemView)
    ImageView selectItemView;
    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.pager)
    ImageTouchPagerView pager;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.tab_main)
    TabLayout tabMain;
    @Bind(R.id.vp_main)
    ViewPager vpMain;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    MineSelectPagerAdapter adapter;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.imageView5)
    ImageView imageView5;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.bannerView)
    BannerView bannerViewView;
    List<CityModel> modelListData;
    public static SelectedFragment newInstance() {
        SelectedFragment squareFragmentV2 = new SelectedFragment();
        return squareFragmentV2;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_select_activity, container, false);
        ButterKnife.bind(this, view);
        MineInfoController.getInstance().get_focus_nav(SelectedFragment.this, UserPreference.getLng(), UserPreference.getLat());
        initView();
        initEvent();
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
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                pager.stopService();
//                vpMain.removeAllViews();
//                initView();
                adapter.refresh();
                try {
                    selectItemView.setImageDrawable(getResources().getDrawable(R.drawable.srach_image_view));
                    ShenGuiApplication.getInstance().getFsenv();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MineInfoController.getInstance().get_focus_nav(SelectedFragment.this, UserPreference.getLng(), UserPreference.getLat());
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        return view;
    }

    List<ImageModel> focuslist;
    List<ImageModel> navslist;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1324) {

            Logger.e("data" + data);
            String modelList = (String) data.getSerializableExtra("modelist");
//            List<CityModel> model=(List<CityModel>)data.getSerializableExtra("modelistdata");
//            Logger.e("mododelList------------" +model.size()+ modelList);
//            modelListData.clear();
//            if(model!=null&&model.size()>0){
//                modelListData=model;
//            }
            if (modelList.equals("1")) {
                selectItemView.setImageDrawable(getResources().getDrawable(R.drawable.ic_iamge_check));
            } else {
                selectItemView.setImageDrawable(getResources().getDrawable(R.drawable.srach_image_view));
            }
        }
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);

        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                //官方圈子
                if (QuanZiModel.getIs_public().equals("1")) {
                    IntentTools.startQuanziManageOffical(getActivity(), QuanZiModel);
                    return;
                }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(getActivity(), Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
                if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                    IntentTools.startQuanziDetailSelf(getActivity(), QuanZiModel);
                    return;
                }
                if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                    IntentTools.startquanziDetail(getActivity(), QuanZiModel);
                    return;
                }
                if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                    IntentTools.startQuanziDetailSelf(getActivity(), QuanZiModel);
                    return;
                }
            }else{
                if(QuanZiModel.getIs_public().equals("1")){
                    IntentTools.startQuanziManageOffical(getActivity(), QuanZiModel);
                    return;
                }
                IntentTools.startquanziDetail(getActivity(), QuanZiModel);
            }
        }

        if (flag == HttpConfig.CityLocaling.getType()) {
            Logger.e("ssss" + o.toString());

            try {
                LocationModel model = (LocationModel) result;
                UserPreference.SetLocationIng(model);

            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
        try {
            JSONObject jsonObject = new JSONObject(o.toString());

            JSONObject jaData = jsonObject.getJSONObject("data");
            JSONArray focus = jaData.getJSONArray("focus");
            JSONArray nav = jaData.getJSONArray("nav");
            focuslist = GsonTool.jsonToArrayEntity(focus.toString(), ImageModel.class);
            navslist = GsonTool.jsonToArrayEntity(nav.toString(), ImageModel.class);

//            mLoopAdapter = new TestLoopAdapter(getActivity(),mRollViewPager,focuslist);
//            mRollViewPager.setAdapter(mLoopAdapter);
//            mRollViewPager.setHintView(new IconHintView(getActivity(),R.drawable.point_focus,R.drawable.point_normal,24));
            ArrayList<String>  paths = new ArrayList<>();
            paths.clear();
            for (int i = 0; i < focuslist.size(); i++) {
                Logger.e("sssfocuslists" + focuslist.get(i).getImg());
                paths.add(focuslist.get(i).getImg());
            }
//            pager.initData(paths);
            //初始化数据
            bannerViewView.init(paths,R.drawable.default_pictures,true,R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused,true);
//            WindowAdapter.setHeight(getActivity(),bannerViewView,5,5);//设置bannerView的宽高，避免
            if (navslist.size() > 0) {
                Glide.with(this).load(navslist.get(0).getImg_1()).override(500, 180).centerCrop().into(image1);
                Glide.with(this).load(navslist.get(1).getImg_1()).override(500, 180).centerCrop().into(image2);
                Glide.with(this).load(navslist.get(2).getImg_1()).override(500, 180).centerCrop().into(imageView4);
                Glide.with(this).load(navslist.get(3).getImg_1()).override(500, 180).centerCrop().into(imageView5);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initEvent() {
//        getLocation();
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);

        selectItemView.setOnClickListener(this);
        searchlayout.setOnClickListener(this);
        SendTextButton.setOnClickListener(this);
        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolBar.getHeight() + verticalOffset == 0) {
                    Buylayout.setVisibility(View.VISIBLE);
                } else {
//                    Logger.e("deweweeee");
                    Buylayout.setVisibility(View.GONE);
                }
            }
        });
        Buylayout.setOnClickListener(this);
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
//        pager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Logger.e("ACTION_DOWN");
//                        pager.stopService();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
////                        swipeLayout.setEnabled(false);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Logger.e("ACTION_UP");
//                        pager.startPlay();
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
////                        swipeLayout.setEnabled(true);
//                        break;
//                }
//                return false;
//            }
//        });
//        pager.setOnItemClickLisener(new ImageTouchPagerView.OnItemClickLisener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Logger.e("posito" + position);
//                SkipActivity(focuslist.get(position));
//            }
//        });
        bannerViewView.setBannerOnClickListener(new BannerView.BannerOnClickListener() {
            @Override
            public void onClick(int position) {
                Log.e("sfff","wewrwrw"+position);
                SkipActivity(focuslist.get(position));
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("hidden ====       xxxxxxxxx"+hidden);
        if(hidden){
            //TODO now visible to user
            bannerViewView.stop();
        } else {
            //TODO now invisible to user
            bannerViewView.setAutoLoop(2000);
        }
    }



    private void initView() {
        String[] strings = getResources().getStringArray(R.array.mine_select);
        adapter = new MineSelectPagerAdapter(getChildFragmentManager(), strings);
        vpMain.setAdapter(adapter);
        vpMain.setOnPageChangeListener(this);
        vpMain.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        tabMain.setupWithViewPager(vpMain);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabMain.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.activity_select_tab);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);//第一个tab被选中
            }
            if (i == 0) {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("最新");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.VISIBLE);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));

                View iVs = (View) tab.getCustomView().findViewById(R.id.rightlineView);
                iVs.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("附近");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.GONE);
                View iVs = (View) tab.getCustomView().findViewById(R.id.rightlineView);
                iVs.setVisibility(View.VISIBLE);
            } else {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("关注");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.GONE);
                View iVs = (View) tab.getCustomView().findViewById(R.id.rightlineView);
                iVs.setVisibility(View.GONE);
            }

        }
        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.VISIBLE);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(false);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
//        if(UserPreference.getISPOSTFINISHPOSR()!=null&&UserPreference.getISPOSTFINISHPOSR().length()>1){
//            adapter.refresh();
//            UserPreference.setISPOSTFINISHPOSR("");
//        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IMStringEvent event) {
        Logger.e("oevent"+event.getIsfresh());
        if(event.getIsfresh()){
            adapter.refresh();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (UserPreference.getTOKEN() == null || UserPreference.getTOKEN().equals("")) {
            if (position == 2) {
                IntentTools.startLogin(getActivity());
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image1:
                SkipActivity(navslist.get(0));
                break;
            case R.id.image2:
                SkipActivity(navslist.get(1));
                break;
            case R.id.imageView4:
                SkipActivity(navslist.get(2));
                break;
            case R.id.imageView5:
                SkipActivity(navslist.get(3));
                break;
            case R.id.searchlayout:
                IntentTools.startSearchDate(getActivity());
                break;
            case R.id.SendTextButton:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    PopUpDialog();
                } else {
                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.selectItemView:
//                selectItemView.setImageDrawable(getResources().getDrawable(R.drawable.ic_iamge_check));
//                IntentTools.startSelectItem(SelectedFragment.this,1324);
                Intent intent = new Intent(getActivity(), SgSelectItemActivity.class);
                intent.putExtra("flag", vpMain.getCurrentItem());
                startActivityForResult(intent, 1324);
                break;
            case R.id.Buylayout:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    PopUpDialog();
                } else {
                    IntentTools.startLogin(getActivity());
                }

//                IntentTools.startPushGongQiu(getActivity());
                break;
        }
    }

    public void PopUpDialog() {   //弹框
        SgActivityPushGongQiuDialog PopUpDialogs = new SgActivityPushGongQiuDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onFail(Exception e) {
        swipeLayout.setRefreshing(false);
    }

    //首页轮播图和四张菱形图的跳转
    public void SkipActivity(ImageModel model) {
        if (model != null) {
            switch (model.getRedirect_type()) {
                case "0":
                    if(!StringTools.isNullOrEmpty( model.getRedirect_url())){
                        IntentTools.startWebViewActivity(getActivity(), model.getRedirect_url(), model.getName());
                    }
                    break;
                case "1":
                    IntentTools.startTieZiDetail(getActivity(), model.getRedirect_url());
                    break;
                case "2":
                    IntentTools.startGongQiuDetail(getActivity(), model.getRedirect_url());
                    break;
                case "3":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "4":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                    IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "5":
                    IntentTools.startTopicList(getActivity());
                    break;
                case "6":
                    IntentTools.startTopicDetail(getActivity(), model.getRedirect_url());
                    break;
                case "7":
                    IntentTools.startDetail(getActivity(), model.getRedirect_url());
                    break;
                case "8":
                    IntentTools.startEditTextView(getActivity(), model.getRedirect_url());
                    break;
                default:
                    break;
            }
        }
    }

}
