package com.shengui.app.android.shengui.android.ui.activity.activity.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityXianxiaActivityListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityListModel;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class SgXianxiaActivityListActivity extends BaseActivity implements View.OnClickListener, ListViewCompat.OnItemClickListener,ScrollViewExtend.ObservableScrollViewCallbacks   {
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
    @Bind(R.id.orderText)
    TextView orderText;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
//    private EmptyLayout mEmptyLayout;
    private int firstnumber=0;
    private int size=10;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.orderText:
                if(UserPreference.getTOKEN()!=null&UserPreference.getTOKEN().length()>1){
                    IntentTools.startActivityDetail(this);
                }else{
                    IntentTools.startLogin(this);
                }

                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
//        mEmptyLayout = new EmptyLayout(this, scrollView);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        scrollView.setCallbacks(this);
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        adapter = new ActivityXianxiaActivityListAdapter(this);
        Memberlistview.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        orderText.setOnClickListener(this);
        Memberlistview.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        firstnumber=0;
        ActivityController.getInstance().ActivityLists(this, "", firstnumber, size);
    }
    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        mEmptyLayout.showLoading();
    }
    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        swipeLayout.setRefreshing(false);
//        mEmptyLayout.showError();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_activity_xianxia_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.ActivityList.getType()) {
            swipeLayout.setRefreshing(false);
//            mEmptyLayout.showSuccess(false);
            try {
                JSONObject ja = new JSONObject(o.toString());
                if (ja.getBoolean("status")) {
                    Logger.e("flage" + result);
                    ActivityListModel model = (ActivityListModel) result;
                    for (ActivityModel m : model.getResult()) {
                        Logger.e("mode" + m.toString());
                    }
                    if (firstnumber == 0) {
                        adapter.clearAll();
                        adapter.setRes(model.getResult());
                    } else {
                        adapter.append(model.getResult());
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startDetail(this, adapter.getItem(position).getId());
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        ActivityController.getInstance().ActivityLists(this, "", firstnumber, size);
    }
}
