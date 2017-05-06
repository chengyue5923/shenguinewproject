package com.shengui.app.android.shengui.android.ui.activity.activity.topic;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityTopicListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.controller.TopicController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityListModel;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.TopicListModel;
import com.shengui.app.android.shengui.models.TopicModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/28.
 */

public class SgTopicListActivity extends BaseActivity implements View.OnClickListener, ListView.OnItemClickListener,ScrollViewExtend.ObservableScrollViewCallbacks   {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.InavateText)
    TextView InavateText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityTopicListAdapter Adapter;
    List<TopicModel> listdate;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private int firstnumber=0;
    private int size=15;
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.InavateText:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startCreateTop(this, 1214,"create");
                }else{
                    IntentTools.startLogin(this);
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        listdate = new ArrayList<>();
        Adapter = new ActivityTopicListAdapter(this);
        listview.setAdapter(Adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        scrollView.setCallbacks(this);
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
//        List<ProductModel> modelList=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            modelList.add(new ProductModel());
//
//        }
//        Adapter.setRes(modelList);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        InavateText.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        firstnumber=0;
        TopicController.getInstance().GetlistTopic(this, firstnumber, size, "");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_list_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.getTopicList.getType()) {
            Logger.e("flage" + result);
            TopicListModel model = (TopicListModel) result;
            if(firstnumber==0){
                Adapter.clearAll();
                Adapter.setRes(model.getResult());
            }else{
                Adapter.append(model.getResult());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startTopicDetail(this, Adapter.getItem(position).getId(),Adapter.getItem(position).getTitle());
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        TopicController.getInstance().GetlistTopic(this, firstnumber, size, "");
    }
}
