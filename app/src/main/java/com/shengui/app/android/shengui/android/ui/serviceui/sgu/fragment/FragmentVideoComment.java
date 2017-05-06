package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoCommentAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/22.
 */

public class FragmentVideoComment extends BaseFragment {

    View view;

    List<VideoCommentBean.DataBeanX> bean = null;

    public VideoCommentAdapter videoCommentAdapter;
    @Bind(R.id.video_comment_recyler_view)
    public RecyclerView videoCommentRecylerView;
    private String courseId;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<VideoCommentBean.DataBeanX> beanXes = (List<VideoCommentBean.DataBeanX>) msg.obj;
            bean = beanXes;
            videoCommentAdapter = new VideoCommentAdapter(getContext(), beanXes);
            if (videoCommentAdapter!=null && videoCommentRecylerView!=null) {
                videoCommentRecylerView.setAdapter(videoCommentAdapter);
            }
            videoCommentAdapter.notifyDataSetChanged();
        }
    };

    public void addComment(VideoCommentBean.DataBeanX data, int place) {
        if (bean != null) {
            int count = 0;
            for (int i = 0; i < bean.size(); i++) {
                if (bean.get(i).getTop() == 1) {
                    count++;
                } else {
                    break;
                }
            }
                bean.add(count, data);
            videoCommentAdapter = new VideoCommentAdapter(getContext(), bean);
            videoCommentRecylerView.setAdapter(videoCommentAdapter);
            videoCommentAdapter.notifyDataSetChanged();

        }
    }

    public void changeComment(VideoCommentBean.DataBeanX dataAllComments, int place) {
        if (bean!=null){
            bean.set(place,dataAllComments);
        }
        int countComments = bean.get(place).getCountComments();
        bean.get(place).setCountComments(countComments+1);
        videoCommentAdapter = new VideoCommentAdapter(getContext(), bean);
        videoCommentRecylerView.setAdapter(videoCommentAdapter);
        videoCommentAdapter.notifyDataSetChanged();
        videoCommentRecylerView.smoothScrollToPosition(place);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sgu_fragment_video_comment, container, false);
        ButterKnife.bind(this, view);

        videoCommentRecylerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Bundle arguments = getArguments();
        courseId = arguments.getString("courseId");
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {
            videoCommentAdapter = new VideoCommentAdapter(getContext(), null);
            videoCommentRecylerView.setAdapter(videoCommentAdapter);
            videoCommentAdapter.notifyDataSetChanged();
        } else {
            ThreadData();
        }
        return view;
    }

    private void ThreadData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoCommentBean.DataBeanX> dataBeanXes = JsonUitl.videoComment(getContext(), courseId);
                Message message = handler.obtainMessage();
                message.obj = dataBeanXes;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    public void reflash(){
        ThreadData();
    }

}
