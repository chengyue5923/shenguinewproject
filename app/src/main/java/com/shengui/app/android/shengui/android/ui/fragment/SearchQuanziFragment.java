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
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityQuanziListItemAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

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
public class SearchQuanziFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack, ScrollViewExtend.ObservableScrollViewCallbacks {
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
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    String key = "";
    int firstNumber = 0;
    int size = 10;
    @Bind(R.id.nodataTv)
    TextView nodataTv;
    @Bind(R.id.nearlistview)
    NoScrollListView nearlistview;
    @Bind(R.id.notdataLayout)
    LinearLayout notdataLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_quanzi_activity, container, false);
        ButterKnife.bind(this, view);
        listview.setFocusable(false);
        Logger.e("key-onCreateView--" + key);
        scrollView.setCallbacks(this);
        initData();
        return view;
    }

    public void UpdateData(String key) {
        this.key = key;
        Logger.e("key-onCreateView--" + key);
        MineInfoController.getInstance().searchindex(this, key, "1", firstNumber, size);
    }

    private void initData() {
        adapter = new ActivityQuanziListItemAdapter(getActivity(), false);
        adapter.setTag(false);
        listview.setAdapter(adapter);
        final List<QuanziList> modellist = new ArrayList<>();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    GuiMiController.getInstance().CiecleContentDetail(SearchQuanziFragment.this, Integer.parseInt(adapter.getItem(position).getId()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                } else {
                    IntentTools.startLogin(getActivity());
                }
//                IntentTools.startQuanziDetail(getActivity(),adapter.getItem(position));
//                Logger.e("isin---------------------"+adapter.getItem(position).getIs_in());
//                if (Integer.parseInt(adapter.getItem(position).getUser_id()) == UserPreference.getUid()) {
//                    Logger.e("key-onCreateView--"+adapter.getItem(position).getUser_id());
//                    IntentTools.startQuanziManage(getActivity(), Integer.parseInt(adapter.getItem(position).getId()));
//                    return;
//                }
//                if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 0) {
//                    Logger.e("isin--"+adapter.getItem(position).getIs_in());
//                    IntentTools.startquanziDetail(getActivity(), adapter.getItem(position));
//                    return;
//                }
//                if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 1) {
//                    Logger.e("isin-adapter-"+adapter.getItem(position).getIs_in());
//                    IntentTools.startQuanziDetailSelf(getActivity(), adapter.getItem(position));
//                    return;
//                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Logger.e("isin---------------------"+adapter.getItem(position).getIs_in());
//        if (Integer.parseInt(adapter.getItem(position).getUser_id()) == UserPreference.getUid()) {
//            Logger.e("key-onCreateView--"+adapter.getItem(position).getUser_id());
//            IntentTools.startQuanziManage(getActivity(), Integer.parseInt(adapter.getItem(position).getId()));
//            return;
//        }
//        if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 0) {
//            Logger.e("isin--"+adapter.getItem(position).getIs_in());
//            IntentTools.startquanziDetail(getActivity(), adapter.getItem(position));
//            return;
//        }
//        if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 1) {
//            Logger.e("isin-adapter-"+adapter.getItem(position).getIs_in());
//            IntentTools.startQuanziDetailSelf(getActivity(), adapter.getItem(position));
//            return;
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("key-onResume--" + key);
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        Logger.e("key-onResume--" + result);
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

            //官方圈子
            if (QuanZiModel.getIs_public().equals("1")) {
                IntentTools.startQuanziManageOffical(getActivity(), QuanZiModel);
                return;
            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(getActivity(), Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(getActivity(), QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                IntentTools.startquanziDetail(getActivity(), QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                IntentTools.startQuanziDetailSelf(getActivity(), QuanZiModel);
                return;
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            if (jsonObject.getBoolean("status")) {
                nodataTv.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                List<QuanziList> modellist = (List<QuanziList>) result;
                Logger.e("HotActivityFragmentmodl-----" + modellist.size());
//            adapter.setRes(modellist);
                if (firstNumber == 0) {
                    if(modellist.size()>0){
                        adapter.clearAll();
                        adapter.setRes(modellist);
                    }else{
                        nodataTv.setVisibility(View.VISIBLE);
                        ToastTool.show("暂无更多数据");
                    }
                } else {
                    adapter.append(modellist);
                }
            } else {
                nodataTv.setVisibility(View.VISIBLE);
                ToastTool.show("暂无更多数据");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try{
//            adapter.clearAll();
//            adapter.notifyDataSetChanged();
//            adapter.setRes((List<QuanziList>)result);
//        }catch (Exception e){
//            Logger.e("dd"+e.getMessage());
//        }
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
        Logger.e("ddddddbottom" + firstNumber);
        firstNumber = firstNumber + size;
        MineInfoController.getInstance().searchindex(this, key, "1", firstNumber, size);
    }
}
