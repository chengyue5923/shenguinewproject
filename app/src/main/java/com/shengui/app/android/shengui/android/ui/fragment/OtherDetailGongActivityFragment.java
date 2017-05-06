package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityQuanziListItemAdapter;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class OtherDetailGongActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack {
    //    @Bind(R.id.myActivityList)
//    ListView myActivityList;
//    MyActivityListAdapter myActivityListAdapter;
//    private List<Object> objects = new ArrayList<>();
//    private EmptyLayout emptyLayout;
//    SwipeRefreshLayout swipeLayout;
    ActivityQuanziListItemAdapter adapter;
    @Bind(R.id.addressQuanNmae)
    TextView addressQuanNmae;
    @Bind(R.id.listview)
    NoScrollListView listview;
//    @Bind(R.id.scrollView)
//    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_other_detail_activity, container, false);
        ButterKnife.bind(this, view);
        listview.setFocusable(false);
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
        adapter=new ActivityQuanziListItemAdapter(getActivity());
        listview.setAdapter(adapter);
        final List<QuanziList> modellist=new ArrayList<>();

        for(int i=0;i<10;i++){
            modellist.add(new QuanziList());
        }
        adapter.setRes(modellist);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startQuanziDetail(getActivity(),modellist.get(position));
            }
        });
//        objects.clear();
//        ActivityController.getInstance().myOrderActivity(this, UserPreference.getRoleId());
//        myActivityList.setOnItemClickListener(this);
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
        initData();
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
