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
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityMineGuiMiListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;

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
public class MineGuiMiNumberActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack {

    @Bind(R.id.confirmlistview)
    NoScrollListView confirmlistview;
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
//    ActivityQuanZiNumberListAdapter adapter;
    ActivityMineGuiMiListAdapter numberAdapter;
    @Bind(R.id.showmoreLayout)
    TextView showmoreLayout;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_number_activity, container, false);
        ButterKnife.bind(this, view);
        confirmlistview.setFocusable(false);
        initData();
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
        numberAdapter = new ActivityMineGuiMiListAdapter(getActivity());
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        confirmlistview.setAdapter(numberAdapter);
        return view;
    }

    private void initData() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            MineInfoController.getInstance().get_my_attension(this, 0, 50, UserPreference.getTOKEN());
        } else {
            ToastTool.show("您还没有登录");
        }
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
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.get_my_attension.getType()) {
            Logger.e("logget_my_attension" + result.toString());
            Logger.e("lCirclepproveogger-----ddddddddddddddddddddd-----" + o);
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject json = jsonObject.getJSONObject("data");

                int count = json.getInt("count");
                showmoreLayout.setText("共" + count + "个龟蜜");
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

                numberAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

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
}
