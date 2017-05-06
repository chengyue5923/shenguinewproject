package com.shengui.app.android.shengui.android.ui.activity.activity.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityXianxiaActivityListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityListModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class SgXianxiaActivityOrderListActivity extends BaseActivity implements View.OnClickListener, ListView.OnItemClickListener,ScrollViewExtend.ObservableScrollViewCallbacks   {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityXianxiaActivityListAdapter adapter;
    @Bind(R.id.noOrderTv)
    TextView noOrderTv;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private int firstnumber=0;
    private int size=10;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        adapter = new ActivityXianxiaActivityListAdapter(this);
//        List<ActivityModel> model=new ArrayList<>();
//        for(int i=0;i<5;i++){
//            model.add(new ActivityModel());
//        }
//        adapter.setRes(model);
        Memberlistview.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        scrollView.setCallbacks(this);
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
    }

    @Override
    protected void initEvent() {
        Memberlistview.setOnItemClickListener(this);
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        noOrderTv.setVisibility(View.GONE);
        firstnumber=0;
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {

            ActivityController.getInstance().ActivityOrderLists(this, UserPreference.getTOKEN(), firstnumber, size);
        } else {
            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_xianxia_order_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.ActivityOrder.getType()) {

            Logger.e("llll" + result);

            ActivityListModel model = (ActivityListModel) result;
            Logger.e("lllmodelmodelmodell" + model.getCount());
            if(model.getResult().size()==0){
                noOrderTv.setVisibility(View.VISIBLE);
            }
//            adapter.setRes(model.getResult());
            if(firstnumber==0){
                adapter.clearAll();
                adapter.setRes(model.getResult());
            }else{
                adapter.append(model.getResult());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startDetail(this,adapter.getItem(position).getId(),"issign");
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        ActivityController.getInstance().ActivityOrderLists(this, UserPreference.getTOKEN(), firstnumber, size);
    }
}
