package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityAddressListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AddressModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineAddressManageListActivity extends BaseActivity implements View.OnClickListener,ActivityAddressListAdapter.AddressOnClickListener {


    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.logoutLayout)
    TextView logoutLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    ActivityAddressListAdapter addressListAdapter;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logoutLayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startDefaultAddress(this);

                }else{
                    ToastTool.show("您还没有登录");
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
        cancelTextView.setOnClickListener(this);
        addressListAdapter=new ActivityAddressListAdapter(this);
        addressListAdapter.setOnclickListener(this);
        listview.setAdapter(addressListAdapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
    }

    @Override
    protected void initEvent() {
        logoutLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            MineInfoController.getInstance().Addressadd_list(this,UserPreference.getTOKEN());
        }else{
            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_address_manage_list_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if(flag== HttpConfig.Addressadd_list.getType()){
            addressListAdapter.setRes((List<AddressModel>)result);
        }
    }

    @Override
    public void deleteAddress(View view, final int position) {

        MineInfoController.getInstance().Addressdel(new ViewNetCallBack() {
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
                    JSONObject object = new JSONObject(o.toString());
                    if (object.getBoolean("status")) {
                        ToastTool.show("删除地址成功");
                        addressListAdapter.removeByPosition(position);
                        addressListAdapter.notifyDataSetChanged();
                    } else {
                        ToastTool.show(object.getString("message"));
                    }

                } catch (Exception e) {
                    ToastTool.show("删除地址失败");
                }
            }
        }, UserPreference.getTOKEN(), addressListAdapter.getItem(position).getId());
    }

    @Override
    public void changeAddress(View view, int position) {

        IntentTools.startChangeAddrelessDetailList(this,addressListAdapter.getItem(position));
    }
//    MineInfoController.getInstance().Addressedit_address(this, UserPreference.getTOKEN(),model.getId(),nameEvStr,provinceId,cityId,detailPhoneEvStr,phoneTVEvStr,isUsualAddress+"",phoneTVEvStr,"");

    @Override
    public void setDefault(View view, final int position) {

        AddressModel model=addressListAdapter.getItem(position);
        MineInfoController.getInstance().Addressedit_address(new ViewNetCallBack() {
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
                    JSONObject object = new JSONObject(o.toString());
                    if (object.getBoolean("status")) {
                        for(int i=0;i<addressListAdapter.getItems().size();i++){
                            addressListAdapter.getItem(i).setIs_usual("0");
                        }
                        addressListAdapter.getItem(position).setIs_usual("1");
                        addressListAdapter.notifyDataSetChanged();
                    } else {
                        ToastTool.show(object.getString("message"));
                    }

                } catch (Exception e) {
                }
            }
        },UserPreference.getTOKEN(),model.getId(),model.getName(),model.getProvince_id(),model.getCity_id(),model.getAddress(),model.getMobile(),"1",model.getMobile(),"");
    }
}
