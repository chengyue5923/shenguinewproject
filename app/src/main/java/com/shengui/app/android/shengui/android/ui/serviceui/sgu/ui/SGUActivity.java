package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.SGUHWebViewActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.CaseDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.DoctorDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.SGUHomePageRecylerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.SGUHomePageViewPageAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentAbout;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentListView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.ListIndexImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.utilsview.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10.
 */

public class SGUActivity extends SGUHBaseActivity {


    @Bind(R.id.header_imageView)
    ImageView headerImageView;
    @Bind(R.id.header_search)
    LinearLayout headerSearch;
    @Bind(R.id.header_my)
    LinearLayout headerMy;
    @Bind(R.id.header)
    LinearLayout header;
    @Bind(R.id.sgu_bannerView)
    BannerView sguBannerView;
    @Bind(R.id.all_video_tv)
    TextView allVideoTv;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.all_about_video)
    RelativeLayout allAboutVideo;
    @Bind(R.id.recyclerview_horizontal)
    RecyclerView recyclerviewHorizontal;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.include_tablayout)
    TabLayout includeTablayout;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    @Bind(R.id.include_vp)
    ViewPager includeVp;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private List<String> imageUrlList;
    private List recylerViewData;
    private View[] viewPageView;
    private SGUHomePageViewPageAdapter sguHomePageViewPageAdapter;
    private SGUHomePageRecylerViewAdapter sguHomePageRecylerViewAdapter;

    private final int REFLASH = 1;

    List<Fragment> fragments;

    private final int SUSPEND = 0;
    private final int STOP = -1;
    private final int RUNNING = 1;

    private int status = 1;
    private final int LISTINDEXIMAGE = 1;
    private final int LISTTUI = 2;
    ArrayList<String> imageList = new ArrayList<>();
    List<ListIndexImageBean.DataBean> imageData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LISTINDEXIMAGE:
                    final List<ListIndexImageBean.DataBean> dataBeen = ((List<ListIndexImageBean.DataBean>) msg.obj);
                    imageData = dataBeen;
                    for (int i = 0; i < dataBeen.size(); i++) {
                        ListIndexImageBean.DataBean dataBean = dataBeen.get(i);
                        imageList.add(Api.baseUrl + dataBean.getPath());
                    }
                    if (dataBeen.size() > 0) {
                        sguBannerView.init(imageList, R.drawable.default_pictures, true, R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused, true);
                    }
                    break;
                case LISTTUI:
                    List<VideoListViewBean.DataBean> list = (List<VideoListViewBean.DataBean>) msg.obj;
                    recylerViewInit(list);
                    break;
            }
        }
    };
    private FragmentListView fragmentVideo;
    private FragmentListView fragmentTeacher;
    private FragmentAbout fragmentAbout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgu_acitivty_sgu);
        ButterKnife.bind(this);

        sguListIndexImage();

        tablayoutViewPageInit();

        onClickListen();

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoListViewBean.DataBean> dataBeen = JsonUitl.videoListViewTui(SGUActivity.this);
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                message.what = LISTTUI;
                handler.sendMessage(message);
            }
        });

    }

    private void sguListIndexImage() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<ListIndexImageBean.DataBean> dataBeen = JsonUitl.listIndexImage(SGUActivity.this);

                Message message = handler.obtainMessage();
                message.what = LISTINDEXIMAGE;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }

    private void onClickListen() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tablayoutViewPageInit();

                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });

        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeContainer.setEnabled(true);
                } else {
                    swipeContainer.setEnabled(false);
                }
            }
        });

        sguBannerView.setBannerOnClickListener(new BannerView.BannerOnClickListener() {
            @Override
            public void onClick(int position) {
                if (imageData!=null){
                    switch (imageData.get(position).getType()){
                        case "1":
                            Intent intent1 = new Intent(SGUActivity.this, TeacherDetailActivity.class);
                            intent1.putExtra("teacherId",imageData.get(position).getHost());
                            startActivity(intent1);
                            break;
                        case "2":
                            Intent intent2 = new Intent(SGUActivity.this, DoctorDetailsActivity.class);
                            intent2.putExtra("doctorId",imageData.get(position).getHost());
                            startActivity(intent2);
                            break;
                        case "3":
                            Intent intent3 = new Intent(SGUActivity.this, CaseDetailsActivity.class);
                            intent3.putExtra("inquiryId",imageData.get(position).getHost());
                            startActivity(intent3);
                            break;
                        case "4":
                            Intent intent4 = new Intent(SGUActivity.this, VideoPlayActivity.class);
                            intent4.putExtra("courseId",imageData.get(position).getHost());
                            startActivity(intent4);
                            break;
                        case "9":
                            Intent intent5 = new Intent(SGUActivity.this, SGUHWebViewActivity.class);
                            intent5.putExtra("URL",imageData.get(position).getHost());
                            startActivity(intent5);
                            break;
                    }
                }
            }
        });

        headerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SGUActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        allVideoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SGUActivity.this, VideoClassifyActivity.class);
                startActivity(intent);
            }
        });

        headerMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.Login) {
                    startActivity(new Intent(SGUActivity.this, MyActivity.class));
                }else {
                    startActivity(new Intent(SGUActivity.this, SgLoginActivity.class));
                }
            }
        });

        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void recylerViewInit( List<VideoListViewBean.DataBean> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewHorizontal.setLayoutManager(linearLayoutManager);
        sguHomePageRecylerViewAdapter = new SGUHomePageRecylerViewAdapter(this, list);
        if (sguHomePageRecylerViewAdapter != null) {
            recyclerviewHorizontal.setAdapter(sguHomePageRecylerViewAdapter);
        }
    }


    private void tablayoutViewPageInit() {

        final String[] stringArray = getResources().getStringArray(R.array.sgu_homepage_tab);

        fragments = new ArrayList<>();
        final List<String> stringList = new ArrayList<>();

        fragmentVideo = new FragmentListView();
        Bundle bundle0 = new Bundle();
        bundle0.putString("tab", stringArray[0]);
        stringList.add(stringArray[0]);
        fragmentVideo.setArguments(bundle0);
        fragments.add(fragmentVideo);

        fragmentTeacher = new FragmentListView();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[1]);
        stringList.add(stringArray[1]);
        fragmentTeacher.setArguments(bundle1);
        fragments.add(fragmentTeacher);

        fragmentAbout = new FragmentAbout();

        fragments.add(fragmentAbout);


        includeVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return stringArray[position];

            }
        });

        includeTablayout.setupWithViewPager(includeVp);

    }

    @Override
    protected void onResume() {
        sguBannerView.setAutoLoop(2000);
        super.onResume();
    }

    @Override
    public void onPause() {
        sguBannerView.stop();
        swipeContainer.setRefreshing(false);
        super.onPause();
    }


}
