package com.shengui.app.android.shengui.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.shengui.app.android.shengui.android.ui.view.ActivityTieziListAdapter;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.TieZiDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class SearchTieZiFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack, ScrollViewExtend.ObservableScrollViewCallbacks {
    @Bind(R.id.addressQuanNmae)
    TextView addressQuanNmae;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    ActivityTieziListAdapter adapter;
    String key = "";
    int firstNumber = 0;
    int size = 10;
    @Bind(R.id.searchNoDate)
    TextView searchNoDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_tiezi_activity, container, false);
        ButterKnife.bind(this, view);
        listview.setFocusable(false);
        initData();
        scrollView.setCallbacks(this);
        Logger.e("key-onCreateView--" + key);
        return view;
    }

    public void UpdateData(String key) {
        this.key = key;
        Logger.e("key-onCreateView--" + key);
        MineInfoController.getInstance().searchindexTieZi(this, key, "3", firstNumber, size);
    }

    private void initData() {
        adapter = new ActivityTieziListAdapter(getActivity());
        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(this);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        //当文本的measureheight 等于scroll滚动的长度+scroll的height
                        if (scrollView.getChildAt(0).getMeasuredHeight() <= scrollView.getScrollY() + scrollView.getHeight()) {
                            Logger.e("ddddddbottom" + firstNumber);
                            firstNumber = firstNumber + size;
                            MineInfoController.getInstance().searchindexTieZi(SearchTieZiFragment.this, key, "3", firstNumber, size);
                        } else {

                        }
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        IntentTools.startTieZiDetail(getActivity(),adapter.getItem(position).getId());
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

        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            if (jsonObject.getBoolean("status")) {
                searchNoDate.setVisibility(View.GONE);
                List<TieZiDetailModel> modellist = (List<TieZiDetailModel>) result;
                Logger.e("HotActivityFragmentmodl-----" + modellist.size());
//            adapter.setRes(modellist);
                if (firstNumber == 0) {
                    if(modellist.size()>0){
                        adapter.clearAll();
                        adapter.setRes(modellist);
                    }else{
                        searchNoDate.setVisibility(View.VISIBLE);
                        ToastTool.show("暂无更多数据");
                    }
                } else {
                    adapter.append(modellist);
                }
                adapter.notifyDataSetChanged();
            } else {
                searchNoDate.setVisibility(View.VISIBLE);
                ToastTool.show("暂无更多数据");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        adapter.clearAll();
//        adapter.notifyDataSetChanged();
//        adapter.setRes((List<TieZiDetailModel>)result);
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
//        Logger.e("ddddddbottom" + firstNumber);
//        firstNumber = firstNumber + size;
//        MineInfoController.getInstance().searchindexTieZi(this,key,"3",firstNumber,size);
    }
}
