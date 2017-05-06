package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.service.UpdateService;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushTieZiDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareRemovePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityManageTieziListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class MineDongtaiActivity extends BaseActivity implements View.OnClickListener,ScrollViewExtend.ObservableScrollViewCallbacks,ActivityManageTieziListAdapter.DialogListener , SharePopUpDialog.DialogShareListener{
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.showmoreLayout)
    TextView showmoreLayout;
    @Bind(R.id.senttieLayout)
    TextView senttieLayout;
    @Bind(R.id.sentvideoLayout)
    TextView sentvideoLayout;
    @Bind(R.id.sendmoreLayout)
    RelativeLayout sendmoreLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    ActivityManageTieziListAdapter adapter;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private int firstnumber=0;
    private int size=8;
    SharePopUpDialog PopUpDialogs;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statesText:
                if (statesText.getText().equals("管理")) {
                    statesText.setText("完成");
                    adapter.SetManage(true);
                    adapter.notifyDataSetChanged();
                } else {
                    statesText.setText("管理");
                    adapter.SetManage(false);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.Buylayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    PopUpDialog();
                }else{
                    ToastTool.show("您还没有登录");
                }

//                IntentTools.startPushTiezi(this);
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        adapter = new ActivityManageTieziListAdapter(this);
        adapter.setDialogListener(this);
        listview.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        scrollView.setCallbacks(this);
        PopUpDialogs = new SharePopUpDialog(this);
        PopUpDialogs.setDialogListener(this);
    }

    @Override
    protected void initEvent() {
        Buylayout.setOnClickListener(this);
        statesText.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserPreference.getISFINISHPOSR()!=null&&UserPreference.getISFINISHPOSR().length()>1){
            initData();
            UserPreference.setISFINISHPOSR("");
        }
    }

    @Override
    protected void initData() {
        showmoreLayout.setVisibility(View.GONE);
        firstnumber=0;
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            swipeLayout.setEnabled(true);
            MineInfoController.getInstance().get_my_post(this, firstnumber, 8, UserPreference.getTOKEN());
        } else {
            swipeLayout.setEnabled(false);
            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_dongtai_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.get_my_post.getType()) {
            Log.e("flagggg", result.toString());
            swipeLayout.setRefreshing(false);
//            adapter.setRes(model.getResult());
            try {
                JSONObject js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    TieZiDetailListModel model = (TieZiDetailListModel) result;
                    if(firstnumber==0&&model.getResult().size()==0){
                        showmoreLayout.setVisibility(View.VISIBLE);
                    }else{
                        if(firstnumber==0){
                            adapter.clearAll();
                            adapter.setRes(model.getResult());
                        }else{
                            adapter.append(model.getResult());
                        }
                    }
                }else{
//                    adapter.clearAll();
//                    ToastTool.show(js.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }   if(flag==HttpConfig.setTop.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show(jsonObject.getString("data"));
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        } if(flag==HttpConfig.setDigest.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show(jsonObject.getString("data"));
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }
        if(flag==HttpConfig.Favoriteadd.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("收藏成功");
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }if(flag==HttpConfig.Favoritedel.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("取消收藏");
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        } if(flag==HttpConfig.DeleteTieZi.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("删除成功");
                    initData();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }if(flag==HttpConfig.DeleteTieZi.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("删除成功");
                    initData();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        MineInfoController.getInstance().get_my_post(this, firstnumber, 8, UserPreference.getTOKEN());
    }

    @Override
    public void onClickNice(TieZiDetailModel model, View v, int position) {

    }

    @Override
    public void onCancelClickNice(TieZiDetailModel model, View v, int position) {

    }

    @Override
    public void CommentLayout(TieZiDetailModel model, View v, int position) {

    }
    //发布弹框
    public void PopUpDialog() {
        SgActivityPushTieZiDialog PopUpDialogs = new SgActivityPushTieZiDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }
    @Override
    public void PopDialog(TieZiDetailModel model, View v, int position) {
        SharePopUpDialog(model);
    }
    //分享举报收藏弹窗
    public void SharePopUpDialog(TieZiDetailModel m) {   //弹框
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.setModel(m);
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //分享弹窗
    public void ShareOtherPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(this,model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(this,new QuanziList(),model.getId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //移动弹窗
    public void ShareRemovePopUpDialog(TieZiDetailModel model) {   //弹框
        ShareRemovePopUpDialog PopUpDialogs = new ShareRemovePopUpDialog(this,model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //精华弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvationPopUpDialog PopUpDialogs = new ShareInvationPopUpDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }
    @Override
    public void onclickShareItem(int flags, TieZiDetailModel model) {
        Log.e("flagess3232rs" ,flags+""+model);
        if (flags == 0) {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                if(model.getIs_favorite().equals("0")){
                    GuiMiController.getInstance().Favoriteadd(this,UserPreference.getTOKEN(),model.getId(),"post");
                }else{
                    GuiMiController.getInstance().Favoritedel(this,UserPreference.getTOKEN(),model.getId(),"post");
                }
            }else{
                IntentTools.startLogin(this);
            }
//            ToastTool.show("已收藏");

        } else if (flags == 1) {
            ShareOtherPopUpDialog(model);
        } else if (flags == 2) {
            ShareReportPopUpDialog(model);
        } else if (flags == 3) {
            if(model.getCircle_section()!=null&&model.getCircle_section().size()>0){
                ShareRemovePopUpDialog(model);
            }else{
                Logger.e("pop");
                showCreateEidtCieclrDialog(model);
            }
        } else if (flags == 4) {
            if(model.getIs_digest().equals("0")){
                TieZiController.getInstance().setDigest(this,model.getId(),"set");
            }else{
                TieZiController.getInstance().setDigest(this,model.getId(),"cancel");
            }
//            ShareJinghuaPopUpDialog();
        } else if (flags == 5) {
//            ToastTool.show("已置顶");
            if(model.getIs_top().equals("0")){
                TieZiController.getInstance().setTop(this,model.getId(),"set");
            }else{
                TieZiController.getInstance().setTop(this,model.getId(),"cancel");
            }
        }else if (flags == 6){
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                MineInfoController.getInstance().quanzhuDelete(this, UserPreference.getTOKEN(), model.getId());
            } else {
                IntentTools.startLogin(this);
            }
        }else if(flags==7){
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                TieZiController.getInstance().DeletTieeZi(this,model.getId(),UserPreference.getTOKEN());
            }else{
                IntentTools.startLogin(this);
            }
        }
    }
    private void showCreateEidtCieclrDialog(final  TieZiDetailModel model) {
        DialogFacory.getInstance().createAlertDialog(this, "创建板块", "发现您所在的圈子没有板块是否去创建板块", "创建", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IntentTools.startQuanziManage(MineDongtaiActivity.this, Integer.parseInt(model.getCircle_id()));
            }
        }, null).show();
    }
}
