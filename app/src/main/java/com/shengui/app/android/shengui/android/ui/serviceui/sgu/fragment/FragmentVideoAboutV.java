package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FragmentVideoAboutV extends BaseFragment {


    View view;
    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentRecyclerView;
    @Bind(R.id.delete_h)
    TextView deleteH;


    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;


    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            List<VideoListViewBean.DataBean> dataBeen = (List<VideoListViewBean.DataBean>) msg.obj;

            videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), dataBeen);
            if (fragmentRecyclerView!=null) {
                fragmentRecyclerView.setAdapter(videoRecyclerViewAdapter);
            }

        }
    };
    private String name;
    private String face;
    private String courseId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        name = arguments.getString("teacherName");
        face = arguments.getString("teacherFace");
        courseId = arguments.getString("courseId");

        aboutNameV();
        return view;


    }

    private void aboutNameV() {


        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoListViewBean.DataBean> dataBeen = JsonUitl.searchResult(getContext(), name);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
