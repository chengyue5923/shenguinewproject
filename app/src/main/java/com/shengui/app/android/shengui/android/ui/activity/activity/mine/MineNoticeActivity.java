package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityChatListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.NoticeModel;
import com.shengui.app.android.shengui.models.NotiseListModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/5.
 */

public class MineNoticeActivity extends BaseActivity implements View.OnClickListener, ScrollViewExtend.ObservableScrollViewCallbacks {
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
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityChatListAdapter adapter;
    List<ProductModel> modelList;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    private int firstNumber = 0;
    private int size = 20;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.statesText:
                if(adapter.getItems().size()>0){
                    if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                        String str="";
                        if(adapter.getItems().size()>0){
                            for(NoticeModel m:adapter.getItems()){
                                Logger.e("adapter---"+m.getId());
                                str=str+m.getId()+",";
                            }
                        }else{
                            ToastTool.show("数据为空");
                        }
                        str=str.substring(0,str.length()-1);
                        Logger.e("straa---"+str);
                        MineInfoController.getInstance().usernotice_del(new ViewNetCallBack() {
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
                                        adapter.clearAll();
                                        showmoreLayout.setVisibility(View.VISIBLE);
                                    }else{
                                        ToastTool.show("清空失败，网络出错");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ToastTool.show("清空失败，网络出错");
                                }
                            }
                        }, str, UserPreference.getTOKEN());
                    }else{
                        ToastTool.show("您还没有登录");
                    }
                }



//                for(int i=0;i<adapter.getItems().size();i++){
//                    adapter.removeByPosition(i);
//                }
//                adapter.notifyDataSetChanged();
//                showmoreLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {
        firstNumber = 0;
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            MineInfoController.getInstance().usernotice(this, firstNumber, size, UserPreference.getTOKEN());
        } else {
            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        UserPreference.setHAVENOTICE("0");
        adapter = new ActivityChatListAdapter(this);
        listview.setAdapter(adapter);
        scrollView.setCallbacks(this);
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            swipeLayout.setEnabled(true);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initData();
                }
            });
            swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        } else {
            swipeLayout.setEnabled(false);
            ToastTool.show("您还没有登录");
        }

    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        statesText.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.e("adass"+adapter.getItem(position).getUrl_type()+adapter.getItem(position).getRedirect_url()+adapter.getItem(position).getTitle());

                switch (adapter.getItem(position).getRedirect_type()){
                    case "0":
                        if(!StringTools.isNullOrEmpty(adapter.getItem(position).getRedirect_url())){
                            IntentTools.startWebViewActivity(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url(),adapter.getItem(position).getTitle());
                        }
                        break;
                    case "1":
                        IntentTools.startTieZiDetail(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url());
                        break;
                    case "2":
                        IntentTools.startGongQiuDetail(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url());
                        break;
                    case "3":
                        IntentTools.startQuanziManageApply(MineNoticeActivity.this,Integer.parseInt(adapter.getItem(position).getRedirect_url()));
                        break;
                    case "4":
//                        IntentTools.startQuanziDetailById(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url());
                        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                            GuiMiController.getInstance().CiecleContentDetail(MineNoticeActivity.this, Integer.parseInt(adapter.getItem(position).getRedirect_url()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                        }else{
                            ToastTool.show("您还没有登录");
                        }
                        break;
                    case "5":
                        IntentTools.startTopicList(MineNoticeActivity.this);
                        break;
                    case "6":
                        IntentTools.startTopicDetail(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url());
                        break;
                    case "7":
                        IntentTools.startDetail(MineNoticeActivity.this, adapter.getItem(position).getRedirect_url());
                        break;
                    case "8":
                        IntentTools.startTextView(MineNoticeActivity.this,adapter.getItem(position).getRedirect_url());
                        break;
                    case "9":
                        IntentTools.startOtherDetail(MineNoticeActivity.this,Integer.parseInt(adapter.getItem(position).getRedirect_url()));
                        break;
                    case "15":
                        String [] temp = null;
                        temp = adapter.getItem(position).getContent().split(" ");
                        Logger.e("sdsds"+temp);
//                        for(int i=0;i<temp.length;i++){
//                            Logger.e("sdsds"+temp[i]);
//                        }
                        IntentTools.startChat(MineNoticeActivity.this,Integer.parseInt(adapter.getItem(position).getRedirect_url()),temp[0],"");
                        break;
                    case "16":
                        IntentTools.startsign(MineNoticeActivity.this);
                        break;
                    case "17":
                        IntentTools.startMain(MineNoticeActivity.this,"flag");
                        break;
                    case "18":
                        IntentTools.startQuanziManageApply(MineNoticeActivity.this,Integer.parseInt(adapter.getItem(position).getRedirect_url()));
                        break;
                }
            }
        });
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_notice_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.usernotice.getType()) {

            try {
                JSONObject ja = new JSONObject(o.toString());
                if (ja.getBoolean("status")) {
                    NotiseListModel model = (NotiseListModel) result;
                    if (model != null && model.getResult().size() > 0) {
                        listview.setVisibility(View.VISIBLE);
                        showmoreLayout.setVisibility(View.GONE);
                        if (firstNumber == 0) {
                            adapter.setRes(model.getResult());
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.append(model.getResult());
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        adapter.clearAll();
                        listview.setVisibility(View.GONE);
                        showmoreLayout.setVisibility(View.VISIBLE);
                    }
                }
//                else{
//                    adapter.clearAll();
//                    listview.setVisibility(View.GONE);
//                    showmoreLayout.setVisibility(View.VISIBLE);
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

            //官方圈子
            if(QuanZiModel.getIs_public().equals("1")){
                IntentTools.startQuanziManageOffical(this,QuanZiModel);
                return;
            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(getActivity(), Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(this,QuanZiModel);
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
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom" + firstNumber);
        firstNumber = firstNumber + size;
        MineInfoController.getInstance().usernotice(this, firstNumber, size, UserPreference.getTOKEN());
    }

}
