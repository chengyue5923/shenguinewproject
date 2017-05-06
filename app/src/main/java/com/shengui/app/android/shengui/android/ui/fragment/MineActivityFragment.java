package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanZiListModel;
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
public class MineActivityFragment extends Fragment implements AdapterView.OnItemClickListener,ViewNetCallBack, ScrollViewExtend.ObservableScrollViewCallbacks {
    @Bind(R.id.addressQuanNmae)
    TextView addressQuanNmae;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    ActivityQuanziListItemAdapter adapter;
    @Bind(R.id.noticeTv)
    TextView noticeTv;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private int firstnumber = 0;
    private int size = 10;
    private String city_id="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_detail_activity, container, false);
        ButterKnife.bind(this, view);
        listview.setFocusable(false);
        listview.setOnItemClickListener(this);
        noticeTv.setVisibility(View.GONE);
        scrollView.setCallbacks(this);
        adapter = new ActivityQuanziListItemAdapter(getActivity(), false);
        listview.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        noticeTv.setVisibility(View.GONE);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE:{
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:{
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        //当文本的measureheight 等于scroll滚动的长度+scroll的height
                        if(scrollView.getChildAt(0).getMeasuredHeight()<=scrollView.getScrollY()+scrollView.getHeight()){
                            Logger.e("ddddddbottom" + firstnumber);
                            firstnumber = firstnumber + size;
                            PushController.getInstance().GetlistQuanzi(MineActivityFragment.this, firstnumber, size, "my_jion",city_id);
                        }else{

                        }
                        break;
                    }
                }
                return false;
            }
        });
        return view;
    }

    private void initData() {
        firstnumber = 0;
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            PushController.getInstance().GetlistQuanzi(this, firstnumber, size, "my_jion",city_id);
        }else{
            ToastTool.show("您还没有登录");
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        IntentTools.startQuanziManage(getActivity(), Integer.parseInt(adapter.getItem(position).getId()));
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            Logger.e("dataUid"+adapter.getItem(position).getUser_id()+"-------------"+UserPreference.getUid()+adapter.getItem(position).getIs_public());
            //官方圈子
            if(adapter.getItem(position).getIs_public().equals("1")){
                IntentTools.startQuanziManageOffical(getActivity(), adapter.getItem(position));
                return;
            }
//            if (Integer.parseInt(adapter.getItem(position).getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(getActivity(), Integer.parseInt(adapter.getItem(position).getId()));
//                return;
//            }
            if (Integer.parseInt(adapter.getItem(position).getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(getActivity(), adapter.getItem(position));
                return;
            }
            if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 0) {
                IntentTools.startquanziDetail(getActivity(), adapter.getItem(position));
                return;
            }
            if (Integer.parseInt(adapter.getItem(position).getIs_in()) == 1) {
                IntentTools.startQuanziDetailSelf(getActivity(), adapter.getItem(position));
                return;
            }
        }else{
            if(adapter.getItem(position).getIs_public().equals("1")){
                IntentTools.startQuanziManageOffical(getActivity(), adapter.getItem(position));
                return;
            }
            IntentTools.startquanziDetail(getActivity(), adapter.getItem(position));
        }

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
//        if (flag == HttpConfig.joinQuanZi.getType()) {
//            QuanZiListModel model = (QuanZiListModel) result;
//            modellist = model.getResult();
//            adapter.setRes(modellist);
//            Logger.e("MineActivityFragmentmodlll" + model.getCount());
//        }
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.joinQuanZi.getType()) {
            try {
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    listview.setVisibility(View.VISIBLE);
                    QuanZiListModel model = (QuanZiListModel) result;
                    List<QuanziList>    modellist = model.getResult();
                    Logger.e("HotActivityFragmentmodlll" + model.getCount());
                    if (firstnumber == 0) {
                        adapter.clearAll();
                        adapter.setRes(modellist);
                    } else {
                        adapter.append(modellist);
                    }
                }else{
                    if (firstnumber == 0) {
                        listview.setVisibility(View.GONE);
                        noticeTv.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
//        Logger.e("ddddddbottom" + firstnumber);
//        firstnumber = firstnumber + size;
//        PushController.getInstance().GetlistQuanzi(this, firstnumber, size, "my_jion",city_id);
    }
}
