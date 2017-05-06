package com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment;

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

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageRVAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHMyInquiryListAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UserQuestionListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.EndlessRecyclerOnScrollListener;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/7.
 */
public class FragmentMyInquiryListView extends BaseFragment {

    View view;


    public SGHMyInquiryListAdapter sghMyInquiryListAdapter;
    public SGHHomePageRVAdapter sghMyFavRVAdapter;
    List<UserQuestionListBean.DataBean> data1 = new ArrayList<>();
    List<InquiryListBean.DataBean> data2 = new ArrayList<>();


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MYQUSETION:
                    List<UserQuestionListBean.DataBean> dataBeen = (List<UserQuestionListBean.DataBean>) msg.obj;
                    data1.addAll(dataBeen);
                    if (p==1){
                        sghMyInquiryListAdapter = new SGHMyInquiryListAdapter(dataBeen, getContext(), FragmentMyInquiryListView.this);
                        if (fragmentRecyclerView!=null){
                            fragmentRecyclerView.setAdapter(sghMyInquiryListAdapter);
                        }
                    }else {
                        sghMyInquiryListAdapter.notifyDataSetChanged();
                    }

                    break;
                case MYCOLLECT:
                    List<InquiryListBean.DataBean> been = (List<InquiryListBean.DataBean>) msg.obj;
                    data2.addAll(been);
                    if (p==1){
                        sghMyFavRVAdapter = new SGHHomePageRVAdapter(getContext(), been);
                        if (fragmentRecyclerView!=null) {
                            fragmentRecyclerView.setAdapter(sghMyFavRVAdapter);
                        }
                    }else {
                        sghMyFavRVAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    private final int MYQUSETION = 1;
    private final int MYCOLLECT = 2;
    private final int WEIXINCHECK = 3;
    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentRecyclerView;
    @Bind(R.id.delete_h)
    TextView deleteH;
    private LinearLayoutManager layout;
    private String tab;
    private int p = 1;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        fragmentRecyclerView.setLayoutManager(layout);

        final String[] stringArray = getResources().getStringArray(R.array.sgh_my);
        dataInit(stringArray);

        fragmentRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.e("test", "onLoadMore: " + currentPage);
                loadMore(stringArray);
            }
        });
        return view;
    }

    private void loadMore(String[] stringArray) {
        p++;
        if (tab.equals(stringArray[0])) {
            ThreadQuestinData();
        } else {
            ThreadCollectData();
        }
    }

    private void dataInit(String[] stringArray) {
        Bundle arguments = getArguments();
        tab = arguments.getString("tab");

        if (tab.equals(stringArray[0])) {
            ThreadQuestinData();
        } else {
            ThreadCollectData();
        }
    }

    public void ThreadCollectData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.myFavList(getContext(), p);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = MYCOLLECT;
                handler.sendMessage(message);

            }
        });
    }

    public void ThreadQuestinData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<UserQuestionListBean.DataBean> dataBeen = SGHJsonUtil.userQuestionList(getContext(), p);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = MYQUSETION;
                handler.sendMessage(message);
            }
        });
    }

    public void reFlash(String[] stringArray) {
        p=1;
        data1.clear();
        data2.clear();
       dataInit(stringArray);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }


}
