package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoMyMsgAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.MyMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/3/27.
 */

public class FragmentMyVideoListView extends BaseFragment {

    View view;

    int myVideoType = 0;


    private final int MYCOLLECTVIDEO = 0;
    private final int MYVIEWSVIDEO = 1;
    private final int MYBUYSVIDEO = 2;
    private final int MYMSG = 3;

    private int p = 1;

    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentRecyclerView;
    @Bind(R.id.delete_h)
    TextView deleteH;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter = null;
    private VideoMyMsgAdapter videoMyMsgAdapter = null;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what < 3) {
                List<VideoListViewBean.DataBean> dataBeen = (List<VideoListViewBean.DataBean>) msg.obj;
                Log.e("test", "handleMessage: "+dataBeen.size());
                if (dataBeen.size() > 0) {
                    videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), dataBeen, myVideoType);
                    fragmentRecyclerView.setAdapter(videoRecyclerViewAdapter);
                    videoRecyclerViewAdapter.notifyDataSetChanged();
                    if (msg.what == 1) {
                        deleteH.setVisibility(View.VISIBLE);
                        deleteH.setText("清空观看历史");
                    } else if (msg.what == 2) {
                        deleteH.setVisibility(View.VISIBLE);
                        deleteH.setText("清空购买记录");
                    } else {
                        deleteH.setVisibility(View.GONE);
                    }
                } else {
                    deleteH.setVisibility(View.GONE);
                }
            } else if (msg.what==3){
                List<MyMsgBean.DataBean> myMsgBean = (List<MyMsgBean.DataBean>) msg.obj;
                if (myMsgBean.size() > 0) {
                    videoMyMsgAdapter = new VideoMyMsgAdapter(getContext(), myMsgBean);
                    fragmentRecyclerView.setAdapter(videoMyMsgAdapter);
                    videoMyMsgAdapter.notifyDataSetChanged();
                } else {
                    deleteH.setVisibility(View.VISIBLE);
                    deleteH.setText("暂无消息");
                }
            }else {
                Boolean obj = (Boolean) msg.obj;
                if (obj){
                    reflash();
                }else {
                    Toast.makeText(getContext(), "清空失败，请稍后再试..", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
    private final int DELALLVIEW = 4;
    private final int DELALLORDER = 5;
    private String[] stringArray;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        fragmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        stringArray = getResources().getStringArray(R.array.sgu_my);

        dataInit(stringArray);

        deleteH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test", "onClick: " + myVideoType);
                if (myVideoType == 1) {
                    Log.e("test", "onClick: " + myVideoType);
                    videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), null, myVideoType);
                    fragmentRecyclerView.setAdapter(videoRecyclerViewAdapter);
                    videoRecyclerViewAdapter.notifyDataSetChanged();
                    deleteH.setVisibility(View.GONE);
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            Boolean aBoolean = JsonUitl.delAllVideoView(getContext());
                            Message message = handler.obtainMessage();
                            message.what = DELALLVIEW;
                            message.obj = aBoolean;
                            handler.sendMessage(message);
                        }
                    });
                } else if (myVideoType == 2) {
                    Log.e("test", "onClick: " + myVideoType);
                    videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), null, myVideoType);
                    fragmentRecyclerView.setAdapter(videoRecyclerViewAdapter);
                    videoRecyclerViewAdapter.notifyDataSetChanged();
                    deleteH.setVisibility(View.GONE);
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            boolean b = JsonUitl.delAllVideoOrder(getContext());
                            Message message = handler.obtainMessage();
                            message.what = DELALLORDER;
                            message.obj = b;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });

        return view;
    }

    public void reflash() {
        if (videoMyMsgAdapter != null && videoRecyclerViewAdapter != null) {
            ThreadData1();
            ThreadData2();
        }
    }

    private void dataInit(String[] stringArray) {
        Bundle arguments = getArguments();
        String tab = arguments.getString("tab");

        for (int i = 0; i < stringArray.length; i++) {
            if (tab.equals(stringArray[i])) {
                myVideoType = i;
            }
        }

        if (myVideoType != 3) {
            ThreadData1();
        } else {
            ThreadData2();
        }
    }

    private void ThreadData2() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<MyMsgBean.DataBean> dataBeen = JsonUitl.myMsgData(getContext(), p);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = myVideoType;
                handler.sendMessage(message);
            }
        });
    }

    private void ThreadData1() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoListViewBean.DataBean> dataBeen = JsonUitl.myViewsData(getContext(), myVideoType, p);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = myVideoType;
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
