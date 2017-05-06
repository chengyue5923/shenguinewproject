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
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageDoctorRvAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageRVAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorNoList;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.EndlessRecyclerOnScrollListener;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/30.
 */

public class FragmentSGHHomePage extends BaseFragment {

    View view = null;

    private final int USERDATA = 0;//问诊中心
    private final int NEWDATA = 1;//最新案例
    private final int OFFICALDATA = 2;//官方案例

    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentRecyclerView;
    @Bind(R.id.delete_h)
    TextView deleteH;

    private SGHHomePageRVAdapter sghHomePageRVAdapter;
    private SGHHomePageDoctorRvAdapter sghHomePageDoctorRvAdapter;
    private int p = 1;

    List<DoctorNoList.DataBean> dataBeen0 = new ArrayList<>();
    List<InquiryListBean.DataBean> dataBeen1 = new ArrayList<>();
    List<InquiryListBean.DataBean> dataBeen2 = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USERDATA:
                    List<DoctorNoList.DataBean> noListDataBeen = (List<DoctorNoList.DataBean>) msg.obj;
                    dataBeen0.addAll(noListDataBeen);
                    if (p == 1) {
                        sghHomePageDoctorRvAdapter = new SGHHomePageDoctorRvAdapter(getContext(), dataBeen0);
                        if (sghHomePageDoctorRvAdapter != null) {
                            fragmentRecyclerView.setAdapter(sghHomePageDoctorRvAdapter);
                        }
                    } else {
                        sghHomePageDoctorRvAdapter.notifyDataSetChanged();
                    }


                    break;
                case NEWDATA:
                    List<InquiryListBean.DataBean> dataBeen = (List<InquiryListBean.DataBean>) msg.obj;
                    dataBeen1.addAll(dataBeen);
                    if (p == 1) {
                        sghHomePageRVAdapter = new SGHHomePageRVAdapter(getContext(), dataBeen1);
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghHomePageRVAdapter);
                        }
                    } else {
                        sghHomePageRVAdapter.notifyDataSetChanged();
                    }


                    break;
                case OFFICALDATA:
                    List<InquiryListBean.DataBean> data = (List<InquiryListBean.DataBean>) msg.obj;
                    List<InquiryListBean.DataBean> officalData = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getType() == 2) {
                            officalData.add(data.get(i));
                        }
                    }
                    dataBeen2.addAll(officalData);
                    if (p == 1) {
                        sghHomePageRVAdapter = new SGHHomePageRVAdapter(getContext(), dataBeen2);
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghHomePageRVAdapter);
                        }
                    } else {
                        sghHomePageRVAdapter.notifyDataSetChanged();
                    }

//                    sghHomePageRVAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private LinearLayoutManager layout;
    private String tab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        fragmentRecyclerView.setLayoutManager(layout);

        final String[] stringArray = getResources().getStringArray(R.array.sgh_homepage);
        dataInit(stringArray);
        fragmentRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.e("test", "onLoadMore: "+currentPage );
                loadMore(stringArray);
            }
        });
        return view;
    }

    private void loadMore(String[] stringArray) {
        p++;
        if (tab.equals(stringArray[1])) {
            ThreadData1();
        } else if (tab.equals(stringArray[2])) {
            ThreadData2();
        } else if (tab.equals(stringArray[0])) {
            ThreadData0();
        }
    }

    private void dataInit(String[] stringArray) {
        Bundle arguments = getArguments();
        tab = arguments.getString("tab");
        if (tab.equals(stringArray[0])) {//USERDATA,医生问诊中心
            ThreadData0();

        } else if (tab.equals(stringArray[1])) {
            ThreadData1();

        } else if (tab.equals(stringArray[2])) {
            ThreadData2();
        }
    }

    private void ThreadData2() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.allInquiryList(getContext(), 2, p);//获取案例消息
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = OFFICALDATA;//最新消息
                handler.sendMessage(message);
            }
        });
    }

    private void ThreadData1() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.allInquiryList(getContext(), 0, p);//获取全部消息
                        Message message = handler.obtainMessage();
                        message.obj = dataBeen;
                        message.what = NEWDATA;//最新消息
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }

    private void ThreadData0() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorNoList.DataBean> dataBeen = SGHJsonUtil.noList(getContext(), p);//获取全部消息
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = USERDATA;//最新消息
                handler.sendMessage(message);
            }
        });
    }

    public void reflash(final String[] stringArray) {
        p = 1;
        dataBeen1.clear();
        dataBeen2.clear();
        dataBeen0.clear();
//        fragmentRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layout) {
//            @Override
//            public void onLoadMore(int currentPage) {
//                Log.e("test", "onLoadMore: "+currentPage );
//                loadMore(stringArray);
//            }
//        });
        dataInit(stringArray);
//        p = 1;
//        ThreadData1();
//        ThreadData2();
//        ThreadData0();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
