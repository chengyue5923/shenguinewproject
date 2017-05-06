package com.shengui.app.android.shengui.android.ui.serviceui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageRVAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/16.
 */

public class ServiceSearchFragment extends Fragment {


    View view;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_no_keyword)
    TextView searchNoKeyword;
    @Bind(R.id.search_no_result)
    LinearLayout searchNoResult;
    @Bind(R.id.search_keyword)
    TextView searchKeyword;
    @Bind(R.id.search_numb)
    TextView searchNumb;
    @Bind(R.id.search_other)
    TextView searchOther;
    @Bind(R.id.have_result)
    LinearLayout haveResult;

    String keyWord;

    private final int ASK = 1;

    private final int SEE = 3;

    Handler handler = new Handler() {
        private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
        private SGHHomePageRVAdapter sghHomePageRVAdapter;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ASK:
                    List<InquiryListBean.DataBean> inquiryDataBeen = (List<InquiryListBean.DataBean>) msg.obj;
                    if (inquiryDataBeen.size() == 0) {
                        searchNoResult.setVisibility(View.VISIBLE);
                        haveResult.setVisibility(View.GONE);
                        searchNoKeyword.setText("没有搜索到相关病例，请试试其他关键字");
                    } else {
                        searchNoResult.setVisibility(View.GONE);
                        haveResult.setVisibility(View.VISIBLE);
                        searchKeyword.setText("关键字“" + keyWord + "” ，共找到");
                        searchNumb.setText(inquiryDataBeen.size() + "");
                        searchOther.setText("个案例");
                        sghHomePageRVAdapter = new SGHHomePageRVAdapter(getContext(), inquiryDataBeen);
                        recyclerView.setAdapter(sghHomePageRVAdapter);
                        sghHomePageRVAdapter.notifyDataSetChanged();
                    }

                    break;
                case SEE:
                    List<VideoListViewBean.DataBean> videoDataBean = (List<VideoListViewBean.DataBean>) msg.obj;
                    if (videoDataBean.size() == 0) {
                        searchNoResult.setVisibility(View.VISIBLE);
                        haveResult.setVisibility(View.GONE);
                        searchNoKeyword.setText("没有搜索到相关课程，请试试其他关键字");
                    } else {
                        searchNoResult.setVisibility(View.GONE);
                        haveResult.setVisibility(View.VISIBLE);
                        searchKeyword.setText("关键字“" + keyWord + "” ，共找到");
                        searchNumb.setText(videoDataBean.size() + "");
                        searchOther.setText("个课程");
                        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getContext(), videoDataBean);
                        recyclerView.setAdapter(videoRecyclerViewAdapter);
                        videoRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.sgh_fragment_listview, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }

    public void dataInit(final String keyword) {

        keyWord = keyword;

        final String[] stringArray = getResources().getStringArray(R.array.sguh_search);
        Bundle arguments = getArguments();
        String tab = arguments.getString("tab");


        if (tab.equals(stringArray[0])) {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.searchInquiryList(getContext(), keyword);
                    Message message = handler.obtainMessage();
                    message.obj = dataBeen;
                    message.what = ASK;
                    handler.sendMessage(message);
                }
            });
        } else {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    List<VideoListViewBean.DataBean> dataBeen = JsonUitl.searchResult(getContext(), keyword);
                    Message message = handler.obtainMessage();
                    message.obj = dataBeen;
                    message.what = SEE;
                    handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
