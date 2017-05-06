package com.shengui.app.android.shengui.android.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdmobi.gui.R;


public class AttentionProductFragment extends Fragment {
    private static AttentionProductFragment instance=null;
    public static AttentionProductFragment newInstance() {
        if(instance==null){
            instance= new AttentionProductFragment();
        }
        return instance;
    }
    public AttentionProductFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        return view;
    }


}
