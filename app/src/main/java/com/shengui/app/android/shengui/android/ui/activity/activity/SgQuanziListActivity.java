package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityQuanziListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.models.QuanZiListModel;
import com.shengui.app.android.shengui.models.QuanziList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/20.
 */

public class SgQuanziListActivity extends BaseActivity implements View.OnClickListener,ScrollViewExtend.ObservableScrollViewCallbacks {
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.pushTextView)
    TextView pushTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    ActivityQuanziListAdapter adapter;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.ScrollView)
    ScrollViewExtend ScrollView;
    private int resultCode = 1010;
    private int firstnumber=0;
    private int size=30;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter = new ActivityQuanziListAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        pushTextView.setOnClickListener(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        listview.setAdapter(adapter);
        ScrollView.setCallbacks(this);
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
}

    @Override
    protected void initData() {
        firstnumber=0;
        PushController.getInstance().GetlistQuanziUserPort(this, firstnumber, size, "my_jion","","1");
//        modellist=new ArrayList<>();
//        for(int i=0;i<20;i++){
//            modellist.add(new ProductModel());
//        }
//        adapter.setRes(modellist);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quanzi_list_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.joinQuanZi.getType()) {
            try {
                JSONObject ja=new JSONObject(o.toString());
                if(ja.getBoolean("status")){
                    QuanZiListModel model = (QuanZiListModel) result;
                    if(firstnumber==0){
                        adapter.setRes(model.getResult());
                        adapter.notifyDataSetChanged();
                    }else{
                        adapter.append(model.getResult());
                        adapter.notifyDataSetChanged();
                    }

                }else{

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pushTextView:
                String s = "";
                String sname = "";
                for (QuanziList mo : adapter.getItems()) {
                    if (mo.isCheck()) {
                        Log.e("model", mo.toString());
                        s = mo.getId();
                        sname = mo.getTitle();
                    }
                }
                if (s.equals("")) {
                    ToastTool.show("请选择圈子");
                } else {
                    Log.e("modelsssss", s);
                    Intent mIntent = new Intent();
                    mIntent.putExtra("modelist", s);
                    mIntent.putExtra("sname", sname);
                    setResult(resultCode, mIntent);
                    finish();
                }
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
//        Logger.e("ddddddbottom"+firstnumber);
//        firstnumber=firstnumber+size;
//        PushController.getInstance().GetlistQuanziUserPort(this, firstnumber, size, "my_jion","","1");
    }
}
