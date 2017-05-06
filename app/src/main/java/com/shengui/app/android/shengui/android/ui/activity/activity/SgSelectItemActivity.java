package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.fragment.MainSelectDetailFragment;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollGridView;
import com.shengui.app.android.shengui.android.ui.view.ActivityCityAdapter;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.models.CityModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/15.
 */

public class SgSelectItemActivity extends BaseActivity implements View.OnClickListener, ActivityCityAdapter.GridListener {
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sellTextView)
    TextView sellTextView;
    @Bind(R.id.BuyTextView)
    TextView BuyTextView;
    @Bind(R.id.addressSelectLayout)
    RelativeLayout addressSelectLayout;
    @Bind(R.id.typeSelectLayout)
    RelativeLayout typeSelectLayout;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.SureButtonView)
    TextView SureButtonView;
    @Bind(R.id.drawer_layout)
    LinearLayout drawerLayout;
    @Bind(R.id.gview)
    NoScrollGridView gview;
    @Bind(R.id.itemTextivew)
    TextView itemTextivew;
    private String variety_id = "";
    private final static int typeSelect = 10121;
    private final static int citySelect = 10125;
    ActivityCityAdapter adapter;

    List<CityModel> cityList;
    CityModel model;
    int currentitemFragement;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.itemTextivew:
                if(adapter.getItems().size()>0){
                    adapter.clearAll();
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.sellTextView:
                ChangeTypeItem(true);
                break;
            case R.id.BuyTextView:
                ChangeTypeItem(false);
                break;
            case R.id.addressSelectLayout:
                IntentTools.startCityList(this, citySelect);
                break;
            case R.id.typeSelectLayout:
                IntentTools.startSelectType(this, typeSelect);
                break;
            case R.id.SureButtonView:
                try {
                    List<CityModel> modelList = new ArrayList<>();
                    for (int i = 0; i < adapter.getItems().size(); i++) {
                        modelList.add(adapter.getItem(i));
                    }
                    Intent mIntent = new Intent();
                    String is = "";
                    if (modelList.size() > 0) {
                        is = "1";
                    } else {
                        is = "0";
                    }
//                    mIntent.putExtra("modelistdata", modelList.toString());
                    mIntent.putExtra("modelist", is);
                    setResult(RESULT_OK, mIntent);
                    EventManager.getInstance().post(new IMStringEvent(modelList, currentitemFragement));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void ChangeTypeItem(boolean isSell) {
        if (isSell) {
            boolean isHave = false;
            for (CityModel m : adapter.getItems()) {
                Logger.e("eeeee");
                if (m.getTypeid().equals("1")) {
                    Logger.e("getTypeid");
                    isHave = true;
                    model.setId("0");
                    model.setName("出售");
                }
            }
            if (!isHave) {
                model.setTypeid("1");
                model.setId("0");
                model.setName("出售");
                adapter.append(model);
            }
            adapter.notifyDataSetChanged();
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            sellTextView.setTextColor(getResources().getColor(R.color.white));
            BuyTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
        } else {
            boolean isHave = false;
            for (CityModel m : adapter.getItems()) {
                if (m.getTypeid().equals("1")) {
                    Logger.e("ddd");
                    isHave = true;
                    model.setId("1");
                    model.setName("求购");
                }
            }
            if (!isHave) {
                model.setTypeid("1");
                model.setId("1");
                model.setName("求购");
                adapter.append(model);
            }
            adapter.notifyDataSetChanged();
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            sellTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            BuyTextView.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter = new ActivityCityAdapter(this);
        adapter.setDialogListener(this);
        gview.setAdapter(adapter);
        cityList = new ArrayList<>();
        adapter.setRes(cityList);
        model = new CityModel();
        model.setTypeid("1");

//        Clearmodel=new CityModel();
//        Clearmodel.setTypeid("4");
//        model=new CityModel();
//        model.setTypeid("1");  // 供求
//        Clearmodel.setId("4");   //出售
//        Clearmodel.setName("清空");
//        adapter.append(Clearmodel);
//        adapter.notifyDataSetChanged();
        if(MainSelectDetailFragment.model!=null){
            for(int i=0;i<MainSelectDetailFragment.model.size();i++){
                if(MainSelectDetailFragment.model.get(i).getTypeid().equals("1")){
                    if(MainSelectDetailFragment.model.get(i).getId().equals("0")){
                        sellTextView.setBackgroundResource(R.drawable.activity_select_item_select);
                        BuyTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
                        sellTextView.setTextColor(getResources().getColor(R.color.white));
                        BuyTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));

                        model.setTypeid("1");
                        model.setId("0");
                        model.setName("出售");
                        adapter.append(model);
                    }else{
                        sellTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
                        BuyTextView.setBackgroundResource(R.drawable.activity_select_item_select);
                        sellTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                        BuyTextView.setTextColor(getResources().getColor(R.color.white));

                        model.setTypeid("1");
                        model.setId("1");
                        model.setName("求购");
                        adapter.append(model);
                    }
                }else{
                    adapter.append(MainSelectDetailFragment.model.get(i));
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {

        itemTextivew.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        sellTextView.setOnClickListener(this);
        BuyTextView.setOnClickListener(this);
        addressSelectLayout.setOnClickListener(this);
        typeSelectLayout.setOnClickListener(this);
        SureButtonView.setOnClickListener(this);
//        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Logger.e("adfad"+adapter.getItems().size());
//                if(adapter.getItems().size()>1){
//                    Logger.e("adwerwrwrfad"+adapter.getItems().size());
//                    if(adapter.getItem(position).getId().equals("4")){
//                        Logger.e("adwerwrwrwerwerwefad"+adapter.getItems().size());
//                        for(int i=0;i<adapter.getItems().size();i++){
//                            if(!adapter.getItem(i).getTypeid().equals("4")){
//                                adapter.removeByPosition(i);
//                            }
//                        }
//                    }
//                }
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    protected void initData() {
        if (getIntent().getSerializableExtra("flag") != null) {
            currentitemFragement = (int) getIntent().getSerializableExtra("flag");
        }
        if(getIntent().getSerializableExtra("data")!=null){
            Logger.e("erer---");
            List<CityModel> model=(List<CityModel>)getIntent().getSerializableExtra("data");
            adapter.setRes(model);
            adapter.notifyDataSetChanged();

        }
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000) {
            if (requestCode == typeSelect) {
                Log.d("date--------------------------", data.getSerializableExtra("variety_id").toString());
                variety_id = (String) data.getSerializableExtra("variety_id");
                String variety_type = (String) data.getSerializableExtra("variety_type");

                boolean isHave = false;
                for (CityModel m : adapter.getItems()) {
                    if (m.getTypeid().equals("3")) {
                        isHave = true;
                        m.setId(variety_id);
                        m.setName(variety_type);
                        adapter.notifyDataSetChanged();
                    }
                }
                if (!isHave) {
                    CityModel model = new CityModel();
                    model.setTypeid("3");
                    model.setName(variety_type);
                    model.setId(variety_id);
                    adapter.append(model);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        if (resultCode == 1000 && requestCode == citySelect) {
            Logger.e("date" + data);
            if (data != null) {
                String cityiD = data.getStringExtra("variety_id");
                String city = data.getStringExtra("variety_name");
                Logger.e("daaaaaaaaaaaa" + city + cityiD);
                boolean isHave = false;
                for (CityModel m : adapter.getItems()) {
                    if (m.getTypeid().equals("2")) {
                        isHave = true;
                        m.setId(cityiD);
                        m.setName(city);
                        adapter.notifyDataSetChanged();
                    }
                }
                if (!isHave) {
                    CityModel model = new CityModel();
                    model.setTypeid("2");
                    model.setName(city);
                    model.setId(cityiD);
                    adapter.append(model);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_item;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

    @Override
    public void onclickCountItem(View view, int positon) {
//        if(adapter.getItem(positon).getName().equals("清空")){
//            adapter.clearAll();
//        }
//        adapter.append(Clearmodel);
//        adapter.notifyDataSetChanged();
    }

}
