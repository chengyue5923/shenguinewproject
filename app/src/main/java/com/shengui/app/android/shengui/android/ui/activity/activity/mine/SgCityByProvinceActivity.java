package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityProvinceAdapter;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.ProvinceModel;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/24.
 */

public class SgCityByProvinceActivity extends BaseActivity {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    ActivityProvinceAdapter adapter;
    private EmptyLayout mEmptyLayout;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter=new ActivityProvinceAdapter(this);
        listview.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.e("eeee"+adapter.getItem(position).getId()+adapter.getItem(position).getName());
                Intent Intent=new Intent();
                Intent.putExtra("cityid",adapter.getItem(position).getId());
                Intent.putExtra("cityname",adapter.getItem(position).getName());
                setResult(RESULT_OK, Intent);
                finish();
            }
        });
        mEmptyLayout = new EmptyLayout(this, scrollView);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
    }
    @Override
    public void onConnectStart() {
        super.onConnectStart();
        mEmptyLayout.showLoading();
    }
    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        mEmptyLayout.showError();
    }
    @Override
    protected void initData() {
        if(getIntent()!=null){
            String id=(String)getIntent().getSerializableExtra("pathid");
            MineInfoController.getInstance().Addressget_city_by_province(this,id);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_address_province_city_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        Logger.e("province"+result);
        mEmptyLayout.showSuccess(false);
        swipeLayout.setRefreshing(false);
        adapter.setRes((List<ProvinceModel>)result);

    }

}
