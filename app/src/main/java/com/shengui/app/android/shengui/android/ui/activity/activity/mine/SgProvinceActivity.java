package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityProvinceAdapter;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.ProvinceModel;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/24.
 */

public class SgProvinceActivity extends BaseActivity {
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
    LinearLayout activityMain;
    ActivityProvinceAdapter adapter;
    private EmptyLayout mEmptyLayout;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter=new ActivityProvinceAdapter(this);
        listview.setAdapter(adapter);
    }
    ProvinceModel model=new ProvinceModel();

    private static final int CITYCODE=1543;
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
                Logger.e("province"+adapter.getItem(position).getName());
                model.setParent_id(adapter.getItem(position).getId());
                model.setParent_name(adapter.getItem(position).getName());
                IntentTools.startCityByProvinceList(SgProvinceActivity.this,adapter.getItem(position).getId(),CITYCODE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==CITYCODE){
            Logger.e("datsssCITYCODEsssa"+data);
            if(data!=null){
                String cityId=(String)data.getSerializableExtra("cityid");
                String cityname=(String)data.getSerializableExtra("cityname");

                Logger.e("model"+cityId+cityname);
                Intent Intent=new Intent();
                Intent.putExtra("provinceId",model.getParent_id());
                Intent.putExtra("provincenmae",model.getParent_name());
                Intent.putExtra("cityid",cityId);
                Intent.putExtra("cityname",cityname);
                setResult(RESULT_OK, Intent);
                finish();
            }

        }
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
        MineInfoController.getInstance().Addressgetprovince(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_address_province_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        Logger.e("province"+result);
        mEmptyLayout.showSuccess(false);
        swipeLayout.setRefreshing(false);
        adapter.setRes((List<ProvinceModel>)result);

    }

}
