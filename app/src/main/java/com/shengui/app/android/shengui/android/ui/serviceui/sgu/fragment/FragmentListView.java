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
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.AboutRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.TeachersRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeachersTeamBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/15.
 */

public class FragmentListView extends BaseFragment {


    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentListview;
    @Bind(R.id.delete_h)
    TextView deleteH;
    private View view;


    private static final int VIDEO_LISVIEW_ALL = 0;
    private static final int TEACHERS_TEAM = 1;
    private static final int ABOUT_MY = 2;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;

    private TeachersRecyclerViewAdapter teachersRecyclerViewAdapter;

    AboutRecyclerViewAdapter aboutRecyclerViewAdapter;


    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case VIDEO_LISVIEW_ALL:
                    List<VideoListViewBean.DataBean> videoDataBeen = (List<VideoListViewBean.DataBean>) msg.obj;

                    videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), videoDataBeen);
                    if (videoRecyclerViewAdapter!=null && fragmentListview!=null) {
                        fragmentListview.setAdapter(videoRecyclerViewAdapter);
                        videoRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;

                case TEACHERS_TEAM:
                    List<TeachersTeamBean.DataBean> teachersDataBean = (List<TeachersTeamBean.DataBean>) msg.obj;

                    if (teachersDataBean.size()<=4){
                        deleteH.setVisibility(View.VISIBLE);
                        deleteH.setText("       ");
                    }else {
                        deleteH.setVisibility(View.GONE);
                    }
                    teachersRecyclerViewAdapter = new TeachersRecyclerViewAdapter(teachersDataBean, getContext());

                    fragmentListview.setAdapter(teachersRecyclerViewAdapter);

                    teachersRecyclerViewAdapter.notifyDataSetChanged();

                    break;

                case ABOUT_MY:

                    aboutRecyclerViewAdapter = new AboutRecyclerViewAdapter(getContext());

                    fragmentListview.setAdapter(aboutRecyclerViewAdapter);

            }

        }

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        fragmentListview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        String[] stringArray = getResources().getStringArray(R.array.sgu_homepage_tab);
        Bundle arguments = getArguments();
        String tab = arguments.getString("tab");

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();


        if (tab.equals(stringArray[0])) {

            if (mNetworkInfo == null) {
                videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), null);
                fragmentListview.setAdapter(videoRecyclerViewAdapter);

            } else {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<VideoListViewBean.DataBean> videoListViewAll = JsonUitl.videoListViewAll(getContext());
                        Message message = handler.obtainMessage();
                        message.obj = videoListViewAll;
                        message.what = VIDEO_LISVIEW_ALL;
                        handler.sendMessage(message);

                    }
                });
            }
        } else if (tab.equals(stringArray[1])) {

            if (mNetworkInfo == null) {
                teachersRecyclerViewAdapter = new TeachersRecyclerViewAdapter(null, getContext());
                fragmentListview.setAdapter(teachersRecyclerViewAdapter);

            } else {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<TeachersTeamBean.DataBean> teachersTeam = JsonUitl.teachersTeam(getContext());
                        Message message = handler.obtainMessage();
                        message.obj = teachersTeam;
                        message.what = TEACHERS_TEAM;
                        handler.sendMessage(message);
                    }
                });
            }
        } else if (tab.equals(stringArray[2])) {

            Message message = handler.obtainMessage();
            message.what = ABOUT_MY;
            handler.sendMessage(message);
        }


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }
}
