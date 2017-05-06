package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityBaseNumberTeamListAdapter;
import com.shengui.app.android.shengui.android.ui.view.ActivityQuanZiNumberListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.CircleMemberListModel;
import com.shengui.app.android.shengui.models.QuanziList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class BaseNumberActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack,ScrollViewExtend.ObservableScrollViewCallbacks  {
    @Bind(R.id.confirmlistview)
    NoScrollListView confirmlistview;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private QuanziList model;
    ActivityQuanZiNumberListAdapter adapter;
    ActivityBaseNumberTeamListAdapter numberAdapter;
    private int firstnumber=0;
    private int size=10;
    public static BaseNumberActivityFragment newInstance(QuanziList model) {
        BaseNumberActivityFragment customerInfoFragment = new BaseNumberActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        customerInfoFragment.setArguments(bundle);
        return customerInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_base_number_activity, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        model = (QuanziList) bundle.getSerializable("model");
        Logger.e("loggereeeeeee" + model.getId());
        initData();
        scrollView.setCallbacks(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        return view;
    }

    private void initData() {
        firstnumber=0;
        GuiMiController.getInstance().CirclenoapproveList(this, Integer.parseInt(model.getId()), 0, 20);
        GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(model.getId()), firstnumber, size);

        adapter = new ActivityQuanZiNumberListAdapter(getActivity(), model);
        confirmlistview.setAdapter(adapter);
        numberAdapter = new ActivityBaseNumberTeamListAdapter(getActivity(), model);

        Memberlistview.setAdapter(numberAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (objects.get(position) instanceof ActivityStatusModel)
//            return;
//        IntentTools.startActivityDetail(getActivity(), ((OrderActivityModel) objects.get(position)).getActivityId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.Circlepprove.getType()) {
            Logger.e("lCirclepproveogger----------" + o);
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject json = jsonObject.getJSONObject("data");
                JSONObject data = json.getJSONObject("result");
                Iterator<String> keys = data.keys();
                List<AllTypeTorModel> modallist = new ArrayList<>();
                while (keys.hasNext()) {
                    AllTypeTorModel modelsd = new AllTypeTorModel();
                    List<CircleMemberDetail> listdate = new ArrayList<>();
                    JSONArray ja = data.getJSONArray(keys.next().toString());
//                    for (int i = 0; i < ja.length(); i++) {
                        List<CircleMemberDetail> resultf = (List<CircleMemberDetail>) GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                        for (CircleMemberDetail ds : resultf) {
                            listdate.add(ds);
                            modelsd.setCharac(ds.getName_first());
                        }
//                    }
                    modelsd.setModelList(listdate);
                    modallist.add(modelsd);
                }
                if(firstnumber==0){
                    numberAdapter.clearAll();
                    numberAdapter.setRes(modallist);
                }else{
                    numberAdapter.append(modallist);
                }
//                numberAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }

        if (flag == HttpConfig.Circlenoapprove.getType()) {
            List<CircleMemberDetail> model = (List<CircleMemberDetail>) result;
            Logger.e("Circlenoapprovelogger" + model.size());
            adapter.setRes(model);
        }
    }

    @Override
    public void onConnectStart() {
//        emptyLayout.showLoading();
    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {
//        emptyLayout.showError();
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+firstnumber);
        firstnumber=firstnumber+size;
        GuiMiController.getInstance().CircleMemberList(this, Integer.parseInt(model.getId()), firstnumber, size);
    }
}
