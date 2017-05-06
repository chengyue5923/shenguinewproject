package com.shengui.app.android.shengui.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.view.ActivityProductListAdapter;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewProductFragment extends Fragment {
    private static NewProductFragment instance = null;


    List<GongQiuDetailModel> messageModles;
    @Bind(R.id.activityList)
    ListView activityList;
    @Bind(R.id.swipe_container)
    LinearLayout swipeContainer;

    ActivityProductListAdapter adapter;
    public static NewProductFragment newInstance() {
        if (instance == null) {
            instance = new NewProductFragment();
        }
        return instance;
    }

    public NewProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        messageModles=new ArrayList<>();
        adapter=new ActivityProductListAdapter(getActivity());
        activityList.setAdapter(adapter);
        for(int i=0;i<10;i++){
            messageModles.add(new GongQiuDetailModel());
        }
        adapter.setRes(messageModles);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
