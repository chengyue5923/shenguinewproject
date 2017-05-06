package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

;

/**
 * Created by Administrator on 2017/3/15.
 */

public class FragmentVideoClassify extends BaseFragment {


    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentListview;
    @Bind(R.id.delete_h)
    TextView deleteH;
    private View view;


    private static final int VIDEO_LISVIEW_ALL = 0;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;

    private int position;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            List<VideoListViewBean.DataBean> dataBeen = (List<VideoListViewBean.DataBean>) msg.obj;
            videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), dataBeen, -1);
            if (videoRecyclerViewAdapter != null) {
                fragmentListview.setAdapter(videoRecyclerViewAdapter);
                videoRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        fragmentListview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        String[] stringArray = getResources().getStringArray(R.array.sgu_video_classify);
        Bundle arguments = getArguments();
        String tab = arguments.getString("tab");


        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        for (int i = 0; i < stringArray.length; i++) {
            if (tab.equals(stringArray[i])) {
                position = i;
                break;
            }
        }


        if (mNetworkInfo == null) {
            videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), null);
            fragmentListview.setAdapter(videoRecyclerViewAdapter);

        } else {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    List<VideoListViewBean.DataBean> videoListView;
                    if (position == 0) {
                        videoListView = JsonUitl.videoListViewAll(getContext());
                    } else {
                        videoListView = JsonUitl.videoListViewClassify(getContext(), position);
                    }
                    Message message = handler.obtainMessage();
                    message.obj = videoListView;
                    message.what = VIDEO_LISVIEW_ALL;
                    handler.sendMessage(message);

                }
            });
        }


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
