package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.SearchGuiMiListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class SearchGuiMiFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack , ScrollViewExtend.ObservableScrollViewCallbacks{
    @Bind(R.id.addressQuanNmae)
    TextView addressQuanNmae;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    SearchGuiMiListAdapter adapter;
    SearchGuiMiListAdapter Nearadapter;
    String key = "";
    int firstNumber = 0;
    int size = 10;
    @Bind(R.id.nearlistview)
    NoScrollListView nearlistview;
    @Bind(R.id.notdataLayout)
    LinearLayout notdataLayout;

    public boolean isNear=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_quanzi_activity, container, false);
        ButterKnife.bind(this, view);
        listview.setFocusable(false);
        scrollView.setCallbacks(this);
        Logger.e("key-onCreateView--" + key);
        initData();
        return view;
    }

    public void UpdateData(String key) {
        this.key = key;
        Logger.e("key-onCreateView--" + key);
        MineInfoController.getInstance().searchindexGuiMI(this, key, "2", firstNumber, size);
    }

    private void initData() {
        adapter = new SearchGuiMiListAdapter(getActivity());
        listview.setAdapter(adapter);

        Nearadapter = new SearchGuiMiListAdapter(getActivity());
        nearlistview.setAdapter(Nearadapter);
        List<ProductModel> modellist = new ArrayList<>();

        nearlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startOtherDetail(getActivity(),Nearadapter.getItem(position).getId());
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startOtherDetail(getActivity(),adapter.getItem(position).getId());
            }
        });
//
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentTools.startOtherDetail(getActivity(), Integer.parseInt(adapter.getItem(position).getId() + ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(flag== HttpConfig.SearchIndex.getType()){
            try {
                Logger.e("dataa"+o.toString());
                JSONObject  ja=new JSONObject(o.toString());
                if(ja.getBoolean("status")){
                    isNear=false;
                    List<LoginResultModel> model=(List<LoginResultModel>) result;
                    Logger.e("dataa"+model.size());
                    listview.setVisibility(View.VISIBLE);
                    notdataLayout.setVisibility(View.GONE);
                    if(firstNumber==0){
                        if(model.size()>0){
                            adapter.clearAll();
                            adapter.setRes(model);
                        }else{
                            MineInfoController.getInstance().searchnearby(this, firstNumber, size);
                            notdataLayout.setVisibility(View.VISIBLE);
                            listview.setVisibility(View.GONE);
                        }
                    }else{
                        adapter.append(model);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    MineInfoController.getInstance().searchnearby(this, firstNumber, size);
                    notdataLayout.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(flag==HttpConfig.searchnearby.getType()){
            Logger.e("dataa"+o.toString());
            isNear=true;
            try {
                JSONObject js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    JSONObject ja=js.getJSONObject("data");
                    JSONArray jresult=ja.getJSONArray("result");

                    List<LoginResultModel> model= GsonTool.jsonToArrayEntity(jresult.toString(),LoginResultModel.class);

                    Logger.e("datmodelaa"+model.toString());
                    Nearadapter.setRes(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Logger.e("dfff"+e.getMessage());
            }

        }

    }

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
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        if(!isNear){
            Logger.e("ddddddbottom" + firstNumber);
            firstNumber = firstNumber + size;
            MineInfoController.getInstance().searchindexGuiMI(this, key, "2", firstNumber, size);
        }

    }
}
