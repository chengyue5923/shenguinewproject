package com.shengui.app.android.shengui.android.ui.fragment;


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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushTieZiDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareRemovePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.GuiMiBannerView;
import com.shengui.app.android.shengui.android.ui.utilsview.HorizontalListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.utilsview.SwipeRefreshLayoutCompat;
import com.shengui.app.android.shengui.android.ui.view.ActivityHotListAdapter;
import com.shengui.app.android.shengui.android.ui.view.MineGuiMiPagerAdapter;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.QuanZiListModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 16-7-11.
 */
public class GuiMiNewFragment extends BaseFragment implements ViewNetCallBack, ViewPager.OnPageChangeListener, View.OnClickListener, ScrollViewExtend.ObservableScrollViewCallbacks, SharePopUpDialog.DialogShareListener, ShareOtherPopUpDialog.OnClickLintener {

    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.pager)
    ImagePagerView pager;
    @Bind(R.id.signviewIm)
    ImageView signviewIm;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.signViewLayout)
    RelativeLayout signViewLayout;
    @Bind(R.id.guimiIm)
    ImageView guimiIm;
    @Bind(R.id.guimiViewLayout)
    RelativeLayout guimiViewLayout;
    @Bind(R.id.topchatIm)
    ImageView topchatIm;
    @Bind(R.id.topViewlayout)
    RelativeLayout topViewlayout;
    @Bind(R.id.activityviewIm)
    ImageView activityviewIm;
    @Bind(R.id.activityViewLayout)
    RelativeLayout activityViewLayout;
    @Bind(R.id.guimiQuanziLayout)
    LinearLayout guimiQuanziLayout;
    @Bind(R.id.listView)
    HorizontalListView listView;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.tab_main)
    TabLayout tabMain;
    @Bind(R.id.selectItemView)
    ImageView selectItemView;
    @Bind(R.id.vp_main)
    ViewPager vpMain;
    MineGuiMiPagerAdapter adapter;
    @Bind(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayoutCompat swipeLayout;
    @Bind(R.id.bannerView)
    GuiMiBannerView bannerViewView;
    private ActivityHotListAdapter mAdapter;
    List<QuanziList> list;
    List<ImageModel> focuslist;

    public static GuiMiNewFragment newInstance() {
        GuiMiNewFragment squareFragmentV2 = new GuiMiNewFragment();
        return squareFragmentV2;
    }

    SharePopUpDialog PopUpDialogs;
    private final String W_APPID = Constant.WXIDAPP_ID;
    private IWXAPI api;

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

    @Override
    public void onResume() {
        super.onResume();
        if(UserPreference.getISFINISHPOSR()!=null&&UserPreference.getISFINISHPOSR().length()>1){
            Logger.e("er-------------");
            adapter.refresh();
            UserPreference.setISFINISHPOSR("");
        }
        MineInfoController.getInstance().hot_circle(GuiMiNewFragment.this, 0, 8);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_guimi_activity, container, false);
        ButterKnife.bind(this, view);
        listView.setFocusable(false);
        inieView();
        initdate();
        initevent();
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
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                pager.stopService();
//                vpMain.removeAllViews();
//                inieView();
                MineInfoController.getInstance().hot_circle(GuiMiNewFragment.this, 0, 8);
                MineInfoController.getInstance().get_focus_nav(GuiMiNewFragment.this, UserPreference.getLng(), UserPreference.getLat());

                adapter.refresh();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        mAdapter = new ActivityHotListAdapter(getActivity());
        list = new ArrayList<>();
        listView.setAdapter(mAdapter);
        regToWx();
        return view;
    }

    private void regToWx() {
        api = WXAPIFactory.createWXAPI(getActivity(), W_APPID, true);
        api.registerApp(W_APPID);
    }

    private void initevent() {
        guimiQuanziLayout.setOnClickListener(this);
        signViewLayout.setOnClickListener(this);
        guimiViewLayout.setOnClickListener(this);
        topViewlayout.setOnClickListener(this);
        activityViewLayout.setOnClickListener(this);
        SendTextButton.setOnClickListener(this);
        searchlayout.setOnClickListener(this);
        Buylayout.setOnClickListener(this);
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolBar.getHeight() + verticalOffset == 0) {
//                    Logger.e("deee");
                    Buylayout.setVisibility(View.VISIBLE);
                } else {
//                    Logger.e("deweweeee");
                    Buylayout.setVisibility(View.GONE);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                IntentTools.startQuanziDetail(getActivity(), list.get(position));
                try {
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        Logger.e("dataUid" + list.get(position).getUser_id() + "-------------" + UserPreference.getUid());

                        //官方圈子
                        if (list.get(position).getIs_public().equals("1")) {
                            IntentTools.startQuanziManageOffical(getActivity(), list.get(position));
                            return;
                        }
                        if (Integer.parseInt(list.get(position).getUser_id()) == UserPreference.getUid()) {
                            IntentTools.startQuanziDetailSelf(getActivity(), list.get(position));
                            return;
                        }
                        if (Integer.parseInt(list.get(position).getIs_in()) == 0) {
                            IntentTools.startquanziDetail(getActivity(), list.get(position));
                            return;
                        }
                        if (Integer.parseInt(list.get(position).getIs_in()) == 1) {
                            IntentTools.startQuanziDetailSelf(getActivity(), list.get(position));
                            return;
                        }
                    } else {
                        //官方圈子
                        if (list.get(position).getIs_public().equals("1")) {
                            IntentTools.startQuanziManageOffical(getActivity(), list.get(position));
                            return;
                        }
                        IntentTools.startquanziDetail(getActivity(), list.get(position));
                    }

                } catch (Exception e) {
                    e.getMessage();
                }
            }
        });
//        pager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
////                        selectedRefresh.setEnabled(false);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
////                        selectedRefresh.setEnabled(true);
//                        break;
//                }
//                return false;
//            }
//        });
//        pager.setOnItemClickLisener(new ImagePagerView.OnItemClickLisener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Logger.e("posito" + position);
//                SkipActivity(focuslist.get(position));
//            }
//        });
        bannerViewView.setBannerOnClickListener(new GuiMiBannerView.BannerOnClickListener() {
            @Override
            public void onClick(int position) {
                Log.e("sfff","wewrwrw"+position);
                SkipActivity(focuslist.get(position));
            }
        });
    }

    private void ChangeItem(int flags) {
        switch (flags) {
        }
    }

    private void initdate() {
//         PushController.getInstance().GetlistQuanzi(this, 0, 8, "all");
        MineInfoController.getInstance().hot_circle(this, 0, 8);
        MineInfoController.getInstance().get_focus_nav(GuiMiNewFragment.this, UserPreference.getLng(), UserPreference.getLat());
    }

    private void inieView() {
        String[] strings = getResources().getStringArray(R.array.mine_select);
        adapter = new MineGuiMiPagerAdapter(getChildFragmentManager(), strings);
        vpMain.setAdapter(adapter);
        vpMain.setOnPageChangeListener(this);
        vpMain.setOffscreenPageLimit(4);
        tabMain.setupWithViewPager(vpMain);

        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabMain.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.activity_select_tab);//给每一个tab设置view
            if (i == 0) {
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
                textViewtitle.setText("热门");//设置tab上的文字
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchlayout:
                IntentTools.startSearchList(getActivity());
                break;
            case R.id.SendTextButton:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    PopUpDialog();
                } else {
                    IntentTools.startLogin(getActivity());
                }
//                IntentTools.startPushTiezi(getActivity());
                break;
            case R.id.signViewLayout:
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    IntentTools.startsign(getActivity());
                } else {
                    IntentTools.startLogin(getActivity());
                }
//                IntentTools.startsign(getActivity());
                break;
            case R.id.guimiViewLayout:
                IntentTools.startQuanziList(getActivity());
                break;
            case R.id.topViewlayout:
                IntentTools.startTopicList(getActivity());
                break;
            case R.id.activityViewLayout:
//                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                IntentTools.startActivityList(getActivity());

//                }else{
//
//                    IntentTools.startLogin(getActivity());
//                }
                break;
            case R.id.guimiQuanziLayout:
                IntentTools.startQuanziList(getActivity());
                break;
            case R.id.Buylayout:
//                IntentTools.startPushTiezi(getActivity());
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    PopUpDialog();
                } else {
                    IntentTools.startLogin(getActivity());
                }

                break;
        }

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
//                    IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "4":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                    IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
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

    //发布弹框
    public void PopUpDialog() {
        SgActivityPushTieZiDialog PopUpDialogs = new SgActivityPushTieZiDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    public void onScrollChanged(int scrollY) {
    }

    //分享举报收藏弹窗
    public void SharePopUpDialog() {   //弹框
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //分享弹窗
    public void ShareOtherPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(getActivity(), model);
        PopUpDialogs.listener(this);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog(TieZiDetailModel model) {   //弹框

        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(getActivity(), new QuanziList(), model.getId());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //移动弹窗
    public void ShareRemovePopUpDialog() {   //弹框
        ShareRemovePopUpDialog PopUpDialogs = new ShareRemovePopUpDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //精华弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvationPopUpDialog PopUpDialogs = new ShareInvationPopUpDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    public void onScrollBottom() {
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

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

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
        }

        Logger.e("HotActivityFragmentmodlll" + o.toString());
        if (flag == HttpConfig.hot_circle.getType()) {
            QuanZiListModel model = (QuanZiListModel) result;
            list = model.getResult();
            Logger.e("HotActivityFragmentmodlll" + model.getCount());
            mAdapter.setRes(list);
        }
        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            JSONObject jaData = jsonObject.getJSONObject("data");
            JSONArray focus = jaData.getJSONArray("guimi_focus");
            focuslist = GsonTool.jsonToArrayEntity(focus.toString(), ImageModel.class);
            ArrayList<String>  paths = new ArrayList<>();
            paths.clear();
            for (int i = 0; i < focuslist.size(); i++) {
                paths.add(focuslist.get(i).getImg());
            }
            bannerViewView.init(paths,R.drawable.default_pictures,true,R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused,true);
//            pager.initData(paths);
        } catch (JSONException e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void onclickShareItem(int flags, TieZiDetailModel model) {
        Logger.e("flagesss" + flags);
        if (flags == 0) {
            ToastTool.show("已收藏");
        } else if (flags == 1) {
            ShareOtherPopUpDialog(model);
        } else if (flags == 2) {
            ShareReportPopUpDialog(model);
        } else if (flags == 3) {
            ShareRemovePopUpDialog();
        } else if (flags == 4) {
            ShareJinghuaPopUpDialog();
        } else if (flags == 5) {
//            ToastTool.show("已置顶");
        }
    }

    @Override
    public void OnClick(TieZiDetailModel mo) {

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = Constant.PostShareUrl + mo.getId();
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if(mo.getCircle().length()>14){
            msg.title =mo.getCircle().substring(0,14)+"...";
        }else{
            msg.title =mo.getCircle();
        }
        if(mo.getContent().length()>14){
            msg.description =mo.getContent().substring(0,14)+"...";
        }else{
            msg.description =mo.getContent();
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = SendMessageToWX.Req.WXSceneSession;

        api.sendReq(req);
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
}
