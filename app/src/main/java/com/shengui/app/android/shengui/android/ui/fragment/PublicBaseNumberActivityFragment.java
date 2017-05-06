package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityBaseNumberTeamListAdapter;
import com.shengui.app.android.shengui.android.ui.view.ActivityQuanZiNumberListAdapter;
import com.shengui.app.android.shengui.models.ProductModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class PublicBaseNumberActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack {
    @Bind(R.id.confirmlistview)
    NoScrollListView confirmlistview;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    //    @Bind(R.id.myActivityList)
//    ListView myActivityList;
//    MyActivityListAdapter myActivityListAdapter;
//    private List<Object> objects = new ArrayList<>();
//    private EmptyLayout emptyLayout;
//    SwipeRefreshLayout swipeLayout;
    ActivityQuanZiNumberListAdapter adapter;
    ActivityBaseNumberTeamListAdapter numberAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_public_base_number_activity, container, false);
        ButterKnife.bind(this, view);
        initData();
        confirmlistview.setFocusable(false);
        Memberlistview.setFocusable(false);
//        myActivityListAdapter = new MyActivityListAdapter(getActivity());
//        myActivityList.setAdapter(myActivityListAdapter);
//        myActivityList.setFocusable(false);
//        emptyLayout = new EmptyLayout(getActivity(), myActivityList);
//        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        swipeLayout.setRefreshing(false);
////                    }
////                }, 5000);
//
//                initData();
//            }
//        });
//        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);
        return view;
    }

    private void initData() {
//        adapter=new ActivityQuanZiNumberListAdapter(getActivity());
//        confirmlistview.setAdapter(adapter);
//        List<ProductModel> modelList=new ArrayList<>();
//        for(int i=0;i<5;i++){
//            modelList.add(new ProductModel());
//        }
//        adapter.setRes(modelList);
//        numberAdapter =new ActivityBaseNumberTeamListAdapter(getActivity());
//
//        Memberlistview.setAdapter(numberAdapter);
//        numberAdapter.setRes(modelList);
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
//        List<OrderActivityModel> list = (List<OrderActivityModel>) result;
//        swipeLayout.setRefreshing(false);
//        List<OrderActivityModel> tempList = new ArrayList<>();
//        emptyLayout.showSuccess(list.size() <= 0);
//        for (OrderActivityModel model : list) {
//            if (model.getStatus() != 1&&model.getStatus()!=0) {
//                tempList.add(model);
//            } else {
//                objects.add(model);
//            }
//        }
//        if (tempList.size()>0){
//            objects.add(new ActivityStatusModel());
//            objects.addAll(tempList);
//        }
//        myActivityListAdapter.setRes(objects);
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
}
