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
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHDoctorAskAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorAskBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.EndlessRecyclerOnScrollListener;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15.
 */

public class FragmentDoctorAskItemList extends BaseFragment {

    View view;
    @Bind(R.id.fragment_RecyclerView)
    RecyclerView fragmentRecyclerView;
    @Bind(R.id.delete_h)
    TextView deleteH;

    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;

    List<DoctorAskBean.DataBean> data1 = new ArrayList<>();
    List<DoctorAskBean.DataBean> data2 = new ArrayList<>();
    List<DoctorAskBean.DataBean> data3 = new ArrayList<>();
    List<DoctorAskBean.DataBean> data4 = new ArrayList<>();
    List<DoctorAskBean.DataBean> data5 = new ArrayList<>();

    Handler handler = new Handler() {
        private SGHDoctorAskAdapter sghDoctorAskAdapter;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ONE:
                    List<DoctorAskBean.DataBean> dataBeen1 = (List<DoctorAskBean.DataBean>) msg.obj;
                    data1.addAll(dataBeen1);
                    if (p == 1) {
                        sghDoctorAskAdapter = new SGHDoctorAskAdapter(dataBeen1, getContext());
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghDoctorAskAdapter);
                        }
                    } else {
                        sghDoctorAskAdapter.notifyDataSetChanged();
                    }
                    break;
                case TWO:
                    List<DoctorAskBean.DataBean> dataBeen2 = (List<DoctorAskBean.DataBean>) msg.obj;
                    data2.addAll(dataBeen2);
                    if (p == 1) {
                        sghDoctorAskAdapter = new SGHDoctorAskAdapter(dataBeen2, getContext());
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghDoctorAskAdapter);
                        }
                    } else {
                        sghDoctorAskAdapter.notifyDataSetChanged();
                    }
                    break;
                case THREE:
                    List<DoctorAskBean.DataBean> dataBeen3 = (List<DoctorAskBean.DataBean>) msg.obj;
                    data3.addAll(dataBeen3);
                    if (p == 1) {
                        sghDoctorAskAdapter = new SGHDoctorAskAdapter(dataBeen3, getContext());
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghDoctorAskAdapter);
                        }
                    } else {
                        sghDoctorAskAdapter.notifyDataSetChanged();
                    }
                    break;
                case FOUR:
                    List<DoctorAskBean.DataBean> dataBeen4 = (List<DoctorAskBean.DataBean>) msg.obj;
                    data4.addAll(dataBeen4);
                    if (p == 1) {
                        sghDoctorAskAdapter = new SGHDoctorAskAdapter(dataBeen4, getContext());
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghDoctorAskAdapter);
                        }
                    } else {
                        sghDoctorAskAdapter.notifyDataSetChanged();
                    }

                    break;
                case FIVE:
                    List<DoctorAskBean.DataBean> dataBeen5 = (List<DoctorAskBean.DataBean>) msg.obj;
                    data5.addAll(dataBeen5);
                    if (p == 1) {
                        sghDoctorAskAdapter = new SGHDoctorAskAdapter(dataBeen5, getContext());
                        if (fragmentRecyclerView != null) {
                            fragmentRecyclerView.setAdapter(sghDoctorAskAdapter);
                        }
                    } else {
                        sghDoctorAskAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    private String tab;
    private int p = 1;
    private LinearLayoutManager layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listview, container, false);
        ButterKnife.bind(this, view);

        layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        fragmentRecyclerView.setLayoutManager(layout);
        final String[] stringArray = getResources().getStringArray(R.array.sgh_doctor_ask);
        Bundle arguments = getArguments();
        tab = arguments.getString("tab");

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
            td1();
        } else if (tab.equals(stringArray[1])) {
            td2();
        } else if (tab.equals(stringArray[2])) {
            td3();
        } else if (tab.equals(stringArray[3])) {
            td4();
        } else if (tab.equals(stringArray[4])) {
            td5();
        }
    }

    public void reflash(String[] stringArray) {
        p = 1;
        data1.clear();
        data2.clear();
        data3.clear();
        data4.clear();
        data5.clear();
        dataInit(stringArray);
    }

    public void dataInit(String[] stringArray) {
        Log.e("test", "onCreateView: " + tab);
        if (tab.equals(stringArray[0])) {
            td1();

        } else if (tab.equals(stringArray[1])) {
            td2();
        }
        if (tab.equals(stringArray[2])) {
            td3();
        }
        if (tab.equals(stringArray[3])) {
            td4();
        }
        if (tab.equals(stringArray[4])) {
            td5();
        }
    }

    private void td5() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorAskBean.DataBean> dataBeen = SGHJsonUtil.DoctorReceiveList(getContext(), 5, p);
                Message message = handler.obtainMessage();
                message.what = FIVE;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    private void td4() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorAskBean.DataBean> dataBeen = SGHJsonUtil.DoctorReceiveList(getContext(), 4, p);
                Message message = handler.obtainMessage();
                message.what = FOUR;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    private void td3() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorAskBean.DataBean> dataBeen = SGHJsonUtil.DoctorReceiveList(getContext(), 3, p);
                Message message = handler.obtainMessage();
                message.what = THREE;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    private void td2() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorAskBean.DataBean> dataBeen = SGHJsonUtil.DoctorReceiveList(getContext(), 2, p);
                Message message = handler.obtainMessage();
                message.what = TWO;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    private void td1() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<DoctorAskBean.DataBean> dataBeen = SGHJsonUtil.DoctorReceiveList(getContext(), 1, p);
                Message message = handler.obtainMessage();
                message.what = ONE;
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
