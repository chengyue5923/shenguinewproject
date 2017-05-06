package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AddressModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineAddressManageEditActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.nameEv)
    EditText nameEv;
    @Bind(R.id.infoLayout)
    RelativeLayout infoLayout;
    @Bind(R.id.phoneTv)
    TextView phoneTv;
    @Bind(R.id.phoneTVEv)
    EditText phoneTVEv;
    @Bind(R.id.phoneLayout)
    RelativeLayout phoneLayout;
    @Bind(R.id.passeordLayout)
    RelativeLayout passeordLayout;
    @Bind(R.id.addressDetailTv)
    TextView addressDetailTv;
    @Bind(R.id.detailPhoneEv)
    EditText detailPhoneEv;
    @Bind(R.id.DetailAddressLayout)
    RelativeLayout DetailAddressLayout;
    @Bind(R.id.detaultAddresTv)
    TextView detaultAddresTv;
    @Bind(R.id.detaultTv)
    TextView detaultTv;
    @Bind(R.id.logoutLayout)
    TextView logoutLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.togglebat)
    ToggleButton togglebat;

    private static final int ProvinceCode = 1020;
    @Bind(R.id.iamgeeIv)
    ImageView iamgeeIv;
    @Bind(R.id.addressTvs)
    TextView addressTvs;

    private int isUsualAddress=0;  //1 默认 0 不是
    private String  cityId;
    private String provinceId;
    AddressModel model;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancelTextView:
                finish();
                break;
            case R.id.passeordLayout:
                IntentTools.startProvinceList(this, ProvinceCode);
                break;
            case R.id.logoutLayout:
                addressAdd();
                break;
        }
    }

    private void addressAdd() {
        String nameEvStr=nameEv.getText().toString();
        String phoneTVEvStr=phoneTVEv.getText().toString();
        String addressTvsStr=addressTvs.getText().toString();
        String detailPhoneEvStr=detailPhoneEv.getText().toString();
        if(StringTools.isNullOrEmpty(nameEvStr)){
            ToastTool.show("请填写收货人");
            return;
        }
        if(StringTools.isNullOrEmpty(phoneTVEvStr)){
            ToastTool.show("请填写手机号");
            return;
        }
        if(StringTools.isNullOrEmpty(addressTvsStr)){
            ToastTool.show("请选择收货地址");
            return;
        }
        if(StringTools.isNullOrEmpty(detailPhoneEvStr)){
            ToastTool.show("请填写详细收货地址");
            return;
        }
        MineInfoController.getInstance().Addressedit_address(this, UserPreference.getTOKEN(),model.getId(),nameEvStr,provinceId,cityId,detailPhoneEvStr,phoneTVEvStr,isUsualAddress+"",phoneTVEvStr,"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ProvinceCode) {
            Logger.e("datssssssa" + data);
            if (data != null) {
                String city = (String) data.getSerializableExtra("cityname");
                String citycityId = (String) data.getSerializableExtra("cityid");
                String Province = (String) data.getSerializableExtra("provincenmae");
                String ProvinceId = (String) data.getSerializableExtra("provinceId");
                Logger.e("daaaa" + city + citycityId + Province + ProvinceId);
                cityId=citycityId;
                provinceId=ProvinceId;
                addressTvs.setText(Province+"-"+city);
            }
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        logoutLayout.setOnClickListener(this);
        passeordLayout.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        togglebat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Logger.e("ischeck" + isChecked);
                if(isChecked){
                    isUsualAddress=1;
                }else{
                    isUsualAddress=0;
                }
            }
        });
    }

    @Override
    protected void initData() {
        if(getIntent()!=null){
            model=(AddressModel)getIntent().getSerializableExtra("CityCode");
            nameEv.setText(model.getName());
            phoneTVEv.setText(model.getMobile());
            addressTvs.setText(model.getProvince()+"-"+model.getCity());
            detailPhoneEv.setText(model.getAddress());
            provinceId=model.getProvince_id();
            cityId=model.getCity_id();
            if(model.getIs_usual().equals("1")){
                togglebat.setChecked(true);
            }else{
                togglebat.setChecked(false);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_address_manage_change_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.Addressedit_address.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("编辑地址成功");
                    finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                ToastTool.show("编辑地址失败");
            }
        }
    }

}
