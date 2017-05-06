package com.shengui.app.android.shengui.android.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareRemovePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.HorizontalListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityHotListAdapter;
import com.shengui.app.android.shengui.android.ui.view.ActivityTieziListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 16-7-11.
 */
public class GuiMiFragment extends Fragment implements ViewNetCallBack, View.OnClickListener, ScrollViewExtend.ObservableScrollViewCallbacks, SharePopUpDialog.DialogShareListener {


    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.pager)
    ImagePagerView pager;
    @Bind(R.id.signviewIm)
    ImageView signviewIm;
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
    @Bind(R.id.newTextView)
    TextView newTextView;
    @Bind(R.id.newBottomLine)
    View newBottomLine;
    @Bind(R.id.localTextView)
    TextView localTextView;
    @Bind(R.id.newlocalLine)
    View newlocalLine;
    @Bind(R.id.AttentionTextView)
    TextView AttentionTextView;
    @Bind(R.id.newattentionLine)
    View newattentionLine;
    @Bind(R.id.selectItemView)
    ImageView selectItemView;
    @Bind(R.id.tabLayout)
    RelativeLayout tabLayout;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.newhindBottomLine)
    View newhindBottomLine;
    @Bind(R.id.hindnewTextView)
    TextView hindnewTextView;
    @Bind(R.id.hindlocalTextView)
    TextView hindlocalTextView;
    @Bind(R.id.newhindlocalLine)
    View newhindlocalLine;
    @Bind(R.id.hindAttentionTextView)
    TextView hindAttentionTextView;
    @Bind(R.id.newhindattentionLine)
    View newhindattentionLine;
    @Bind(R.id.hindLayout)
    LinearLayout hindLayout;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.coordinator_layout)
    RelativeLayout coordinatorLayout;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.guimiQuanziLayout)
    LinearLayout guimiQuanziLayout;
    @Bind(R.id.listView)
    HorizontalListView listView;
    private int mParallaxImageHeight;
    private ActivityHotListAdapter mAdapter ;
    public static GuiMiFragment newInstance() {
        GuiMiFragment squareFragmentV2 = new GuiMiFragment();
        return squareFragmentV2;
    }

    private static final int newItem = 0;
    private static final int localItem = 1;
    private static final int attentionItem = 2;
    List<TieZiDetailModel> listDate;
    SharePopUpDialog PopUpDialogs;
    private ActivityTieziListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_guimi_page, container,
                false);
        ButterKnife.bind(this, view);

        inieView();
        initdate();
        initevent();
        return view;
    }

    private void initevent() {
        searchlayout.setOnClickListener(this);
        PopUpDialogs = new SharePopUpDialog(getActivity());
        PopUpDialogs.setDialogListener(this);
        newTextView.setOnClickListener(this);
        localTextView.setOnClickListener(this);
        AttentionTextView.setOnClickListener(this);
        selectItemView.setOnClickListener(this);
        SendTextButton.setOnClickListener(this);
        signViewLayout.setOnClickListener(this);
        guimiViewLayout.setOnClickListener(this);
        topViewlayout.setOnClickListener(this);
        activityViewLayout.setOnClickListener(this);
        guimiQuanziLayout.setOnClickListener(this);
        hindnewTextView.setOnClickListener(this);
        hindlocalTextView.setOnClickListener(this);
        hindAttentionTextView.setOnClickListener(this);
        Buylayout.setOnClickListener(this);


        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        selectedRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
//                        selectedRefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        pager.setOnItemClickLisener(new ImagePagerView.OnItemClickLisener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.e("posito" + position);
            }
        });
    }

    private void ChangeItem(int flags) {
        switch (flags) {
            case newItem:
                newTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                localTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                AttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.white));

                hindnewTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case localItem:
                newTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                localTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                AttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindnewTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case attentionItem:
                newTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                localTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                AttentionTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindnewTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                break;
        }
    }

    private void initdate() {
//        TieZiController.getInstance().getNewTieZiList(this);

        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.twohundreadss);
        scrollView.setCallbacks(this);
        listview.setFocusable(false);
        listDate = new ArrayList<>();
        adapter = new ActivityTieziListAdapter(getActivity());
        listview.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            listDate.add(new TieZiDetailModel());
        }
        adapter.setRes(listDate);
    }

    private void inieView() {
        List<String> paths = new ArrayList<>();
        paths.add("http://m.easyto.com/m/zhulifuwu_banner.jpg");
        paths.add("http://m.easyto.com/m/japan/images/banner_3y_new.jpg");
        paths.add("http://m.easyto.com/m/japan/images/banner_5y_new.jpg");
        pager.initData(paths);
        mAdapter=new ActivityHotListAdapter(getActivity());
        List<ProductModel> list=new ArrayList<>();
        for(int i=0;i<8;i++){
            list.add(new ProductModel());
        }
//        mAdapter.setRes(list);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SendTextButton:
                PopUpDialog();
                break;
            case R.id.newTextView:
                ChangeItem(newItem);
                break;
            case R.id.localTextView:
                ChangeItem(localItem);
                break;
            case R.id.AttentionTextView:
                ChangeItem(attentionItem);
                break;
            case R.id.hindnewTextView:
                ChangeItem(newItem);
                break;
            case R.id.hindlocalTextView:
                ChangeItem(localItem);
                break;
            case R.id.hindAttentionTextView:
                ChangeItem(attentionItem);
                break;
            case R.id.selectItemView:
                IntentTools.startSelectItem(getActivity());
                break;
            case R.id.Buylayout:
                IntentTools.startPushTiezi(getActivity());
                break;
            case R.id.guimiQuanziLayout:
                IntentTools.startQuanziList(getActivity());
                break;
            case R.id.topViewlayout:
                IntentTools.startTopicList(getActivity());
                break;
            case R.id.activityViewLayout:
                IntentTools.startActivityList(getActivity());
                break;
            case R.id.guimiViewLayout:
                IntentTools.startQuanziList(getActivity());
                break;
            case R.id.signViewLayout:
                IntentTools.startsign(getActivity());
                break;
            case R.id.searchlayout:
                IntentTools.startSearchList(getActivity());
                break;
        }

    }

    //发布弹框
    public void PopUpDialog() {
        SgActivityPushDialog PopUpDialogs = new SgActivityPushDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    public void onScrollChanged(int scrollY) {
//        Logger.e("logger"+scrollY);
//        if (scrollY >= mParallaxImageHeight) {
//            hindLayout.setVisibility(View.VISIBLE);
//            Buylayout.setVisibility(View.VISIBLE);
//        } else {
//            hindLayout.setVisibility(View.GONE);
//            Buylayout.setVisibility(View.GONE);
//        }
    }

    //分享举报收藏弹窗
    public void SharePopUpDialog() {   //弹框
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //分享弹窗
    public void ShareOtherPopUpDialog() {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(getActivity(),new QuanziList(),model.getId());
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

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.getnewlist.getType()){

            TieZiDetailListModel model=(TieZiDetailListModel)result;
            Logger.e("--------------"+model.getCount()+""+model.getResult());
        }
    }

    @Override
    public void onclickShareItem(int flags, TieZiDetailModel model) {
            Logger.e("flagesss" + flags);
            if (flags == 0) {
                ToastTool.show("已收藏");
            } else if (flags == 1) {
                ShareOtherPopUpDialog();
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
}
