package com.shengui.app.android.shengui.android.ui.serviceui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.assembly.VpSwipeRefreshLayout;
import com.shengui.app.android.shengui.android.ui.serviceui.adapter.ServiceAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.BaiduWeatherBean;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.ReminderBean;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.ServiceImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.servicedialog.ServiceDialogFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.CaseDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.DoctorDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.SGUActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.TeacherDetailActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.utilsview.BannerView;
import com.shengui.app.android.shengui.db.UserPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/9.
 */

public class ServiceFragment extends BaseFragment {

    View view;
    @Bind(R.id.find_adress)
    TextView findAdress;
    @Bind(R.id.header_search)
    LinearLayout headerSearch;
    @Bind(R.id.find_service)
    TextView findService;
    @Bind(R.id.header_my)
    LinearLayout headerMy;
    @Bind(R.id.sguh_bannerView)
    BannerView sguhBannerView;
    @Bind(R.id.imageView7)
    ImageView imageView7;
    @Bind(R.id.tab_main)
    TabLayout tabMain;
    @Bind(R.id.vp_main)
    ViewPager vpMain;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.go_sgh)
    LinearLayout goSgh;
    @Bind(R.id.go_sgu)
    LinearLayout goSgu;
    @Bind(R.id.weather_tv)
    TextView weatherTv;
    @Bind(R.id.weather_img)
    ImageView weatherImg;
    @Bind(R.id.wenxingtishi)
    TextView wenxingtishi;

    private ServiceAdapter serviceAdapter;


    public static ServiceFragment newInstance() {
        ServiceFragment squareFragmentV2 = new ServiceFragment();
        return squareFragmentV2;
    }

    private final int BAIDUWEATHER = 1;
    private final int REMINDER = 2;
    private final int IMAGEDATA = 3;

    List<ServiceImageBean.DataBean> imageData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BAIDUWEATHER:
                    List<BaiduWeatherBean.ResultsBean> resultsBeen = (List<BaiduWeatherBean.ResultsBean>) msg.obj;
                    if (resultsBeen != null) {
                        weatherTv.setText(resultsBeen.get(0).getWeather_data().get(0).getTemperature());
                        Glide.with(getContext())
                                .load(resultsBeen.get(0).getWeather_data().get(0).getDayPictureUrl())
                                .skipMemoryCache(false)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(weatherImg);
                    }
                    break;

                case REMINDER:
                    ReminderBean reminderBean = (ReminderBean) msg.obj;
                    if (reminderBean!=null){
                        wenxingtishi.setText(reminderBean.getData());
                    }
                    break;
                case IMAGEDATA:
                    List<ServiceImageBean.DataBean> imageDataBean = (List<ServiceImageBean.DataBean>) msg.obj;
                    imageData=imageDataBean;
                    ArrayList<String> paths = new ArrayList<>();
                    if (imageDataBean.size()>0){
                    for (int i = 0; i < imageDataBean.size(); i++) {
                        paths.add(Api.baseUrl+imageDataBean.get(i).getPath());
                    }
                    sguhBannerView.init(paths,R.drawable.default_pictures,true,R.mipmap.page_indicator_focused,R.mipmap.page_indicator_unfocused,true);
                }
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sguh_activity_main, container, false);
        ButterKnife.bind(this, view);

        initView();

        dataThread();

        viewOnClick();

        return view;
    }

    private void viewOnClick() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataThread();
                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });

        goSgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SGHActivity.class));
            }
        });
        goSgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SGUActivity.class));
            }
        });

        headerMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });

        headerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SGUHSearchActivity.class));
            }
        });

        sguhBannerView.setBannerOnClickListener(new BannerView.BannerOnClickListener() {
            @Override
            public void onClick(int position) {
                if (imageData!=null){
                    switch (imageData.get(position).getType()){
                        case "1":
                            Intent intent1 = new Intent(getContext(), TeacherDetailActivity.class);
                            intent1.putExtra("teacherId",imageData.get(position).getHost());
                            startActivity(intent1);
                            break;
                        case "2":
                            Intent intent2 = new Intent(getContext(), DoctorDetailsActivity.class);
                            intent2.putExtra("doctorId",imageData.get(position).getHost());
                            startActivity(intent2);
                            break;
                        case "3":
                            Intent intent3 = new Intent(getContext(), CaseDetailsActivity.class);
                            intent3.putExtra("inquiryId",imageData.get(position).getHost());
                            startActivity(intent3);
                            break;
                        case "4":
                            Intent intent4 = new Intent(getContext(), VideoPlayActivity.class);
                            intent4.putExtra("courseId",imageData.get(position).getHost());
                            startActivity(intent4);
                            break;
                        case "9":
                            Intent intent5 = new Intent(getContext(), SGUHWebViewActivity.class);
                            intent5.putExtra("URL",imageData.get(position).getHost());
                            startActivity(intent5);
                            break;
                    }
                }
            }
        });
    }

    private void dataThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonUitl.userStatus(getActivity());
            }
        }).start();

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<BaiduWeatherBean.ResultsBean> resultsBeen = JsonUitl.baiduWeather(getContext());
                Message message = handler.obtainMessage();
                message.what = BAIDUWEATHER;
                message.obj = resultsBeen;
                handler.sendMessage(message);
            }
        });

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                ReminderBean reminder = JsonUitl.reminder(getContext());
                Message message = handler.obtainMessage();
                message.what = REMINDER;
                message.obj = reminder;
                handler.sendMessage(message);
            }
        });

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<ServiceImageBean.DataBean> imageDataBeen = JsonUitl.serviceImage(getContext());
                Message message = handler.obtainMessage();
                message.what = IMAGEDATA;
                message.obj = imageDataBeen;
                handler.sendMessage(message);
            }
        });
    }


    public void showDialog(View view) {
        ServiceDialogFragment serviceDialog = new ServiceDialogFragment();
        serviceDialog.show(getFragmentManager(), "");
    }

    List<Fragment> fragments;

    private void initView() {

        findAdress.setText(UserPreference.getCityName());

        final String[] strings = getResources().getStringArray(R.array.sguh_service);

        serviceAdapter = new ServiceAdapter(getChildFragmentManager(), strings);

        vpMain.setAdapter(serviceAdapter);

//        vpMain.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager绑定在一起，使双方各自的改变都能直接影响另一方，解放了开发人员对双方变动事件的监听
        tabMain.setupWithViewPager(vpMain);

        for (int i = 0; i < serviceAdapter.getCount(); i++) {
            TabLayout.Tab tab = tabMain.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.sguh_service_select_tab);//给每一个tab设置view
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);//第一个tab被选中
            }
            if (i == 0) {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("课程专题");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.VISIBLE);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));

                View iVs = (View) tab.getCustomView().findViewById(R.id.rightlineView);
                iVs.setVisibility(View.GONE);
            } else if (i == 1) {
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setText("案例专题");//设置tab上的文字
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.GONE);
                View iVs = (View) tab.getCustomView().findViewById(R.id.rightlineView);
                iVs.setVisibility(View.GONE);
            }

        }
        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(true);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.VISIBLE);
                iV.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                vpMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.TaGoneTv).setSelected(false);
                TextView textViewtitle = (TextView) tab.getCustomView().findViewById(R.id.TaGoneTv);
                textViewtitle.setTextColor(getResources().getColor(R.color.titlecolor));
                View iV = (View) tab.getCustomView().findViewById(R.id.goneView);
                iV.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.e("hidden ====       xxxxxxxxx"+hidden);
        if(hidden){
            //TODO now visible to user
            sguhBannerView.stop();
        } else {
            //TODO now invisible to user
            sguhBannerView.setAutoLoop(2000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
