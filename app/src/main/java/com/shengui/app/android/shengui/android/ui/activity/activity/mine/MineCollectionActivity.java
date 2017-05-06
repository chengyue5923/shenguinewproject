package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityMineCollecttionListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.FavoriteModels;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收藏
 * Created by lenovo on 2016/7/11.
 */
public class MineCollectionActivity extends BaseActivity implements View.OnClickListener, ActivityMineCollecttionListAdapter.RemoveListener,ScrollViewExtend.ObservableScrollViewCallbacks {

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
    ActivityMineCollecttionListAdapter collecttionListAdapter;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;


    private int firstNumber=0;
    private int size=15;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statesText:
                if (statesText.getText().equals("管理")) {
                    statesText.setText("完成");
                    collecttionListAdapter.SetShow(true);
                    collecttionListAdapter.notifyDataSetChanged();
                } else {
                    statesText.setText("管理");
                    collecttionListAdapter.SetShow(false);
                    collecttionListAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
        }

    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        statesText.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        scrollView.setCallbacks(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(collecttionListAdapter.getItem(position).getFav_type().equals("1")){
                    IntentTools.startGongQiuDetail(MineCollectionActivity.this,collecttionListAdapter.getItem(position).getId());
                }else{
                    IntentTools.startTieZiDetail(MineCollectionActivity.this,collecttionListAdapter.getItem(position).getId());
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            firstNumber=0;
            MineInfoController.getInstance().Favoriteget_my_favorite(this, firstNumber, size, UserPreference.getTOKEN());
        } else {
            ToastTool.show("您还没有登录");
        }
        collecttionListAdapter = new ActivityMineCollecttionListAdapter(this);
        collecttionListAdapter.SetRemoveListener(this);
//        List<ProductModel> list=new ArrayList<>();
//        for(int i=0;i<10;i++){
//            list.add(new ProductModel());
//        }
        listview.setAdapter(collecttionListAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_collection_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.Favoriteget_my_favorite.getType()) {
            FavoriteModels models = (FavoriteModels) result;
            Logger.e("logger" + result.toString());
            try {
                JSONObject ja=new JSONObject(o.toString());

                if(ja.getBoolean("status")){
                    if(firstNumber==0){
                        collecttionListAdapter.clearAll();
                        collecttionListAdapter.setRes(models.getResult());
                    }else{
                        collecttionListAdapter.append(models.getResult());
                    }
                    showmoreLayout.setVisibility(View.VISIBLE);
                    showmoreLayout.setText("您一共收藏了"+models.getCount()+"信息");
                }else{
                    showmoreLayout.setVisibility(View.GONE);
                    ToastTool.show(ja.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            collecttionListAdapter.setRes(models.getResult());

        }
    }

    @Override
    public void Remove(int position, View v) {

    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstNumber);
        firstNumber=firstNumber+size;
        MineInfoController.getInstance().Favoriteget_my_favorite(this, firstNumber, size, UserPreference.getTOKEN());
    }
}
