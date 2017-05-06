package com.shengui.app.android.shengui.android.ui.activity.activity.manage;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvatiFriendsOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityBaseMemberAdapter;
import com.shengui.app.android.shengui.android.ui.view.ActivityMemberListMyAttentionAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
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

public class SgQuanZiManagerMemberNewActivity extends BaseActivity implements View.OnClickListener,ScrollViewExtend.ObservableScrollViewCallbacks   {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.InavateText)
    TextView InavateText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.confirmlistview)
    NoScrollListView confirmlistview;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityMemberListMyAttentionAdapter adapter;
    ActivityBaseMemberAdapter listAdapter;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private QuanziList model;
    private int firstnumber=0;
    private int size=10;
    @Override
    protected void initView() {

        ButterKnife.bind(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        adapter = new ActivityMemberListMyAttentionAdapter(this);
        confirmlistview.setAdapter(adapter);
        listAdapter = new ActivityBaseMemberAdapter(this);
        Memberlistview.setAdapter(listAdapter);
        Memberlistview.setFocusable(false);
        scrollView.setCallbacks(this);
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
    }

    @Override
    protected void initEvent() {
        InavateText.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        firstnumber=0;
        if (getIntent() != null) {
            model = (QuanziList) getIntent().getSerializableExtra("mode");
            GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(model.getId()), firstnumber, size,1);
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                GuiMiController.getInstance().GetCirclrList(this, UserPreference.getTOKEN(), Integer.parseInt(model.getId()), "1");
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manager_memger_list_activity_new;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.GetCircleFocusList.getType()) {
            Logger.e("loggereee" + o);
//            FansOrAttentionModels models =(FansOrAttentionModels)result;
//            adapter.setRes(models.getResult());
            try {
                adapter.clearAll();
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject json = jsonObject.getJSONObject("data");
                JSONObject data = json.getJSONObject("result");
                Iterator<String> keys = data.keys();
                List<CircleMemberDetail> listdate = new ArrayList<>();
                while (keys.hasNext()) {
                    AllTypeTorModel modelsd = new AllTypeTorModel();

                    JSONArray ja = data.getJSONArray(keys.next().toString());
                    List<CircleMemberDetail> resultf = (List<CircleMemberDetail>) GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                    for (CircleMemberDetail ds : resultf) {
                        listdate.add(ds);
                    }
                }
                if(listdate.size()>0){
                    adapter.setRes(listdate);
                }
//                if(firstnumber==0){
//                    listAdapter.clearAll();
//                    listAdapter.setRes(modallist);
//                }else{
//                    listAdapter.append(modallist);
//                }
//                listAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }

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
                    listAdapter.clearAll();
                    listAdapter.setRes(modallist);
                }else{
                    listAdapter.append(modallist);
                }
//                listAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
    }

    //邀请好友弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvatiFriendsOtherPopUpDialog PopUpDialogs = new ShareInvatiFriendsOtherPopUpDialog(this,model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.InavateText:
                ShareJinghuaPopUpDialog();
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
        GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(model.getId()), firstnumber, size);
    }
}
