package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.SGUHWebViewActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment.FragmentSGHHomePage;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.SGUHomePageViewPageAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.ListIndexImageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.TeacherDetailActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.utilsview.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SGHActivity extends SGUHBaseActivity implements View.OnClickListener {


    @Bind(R.id.header_imageView)
    ImageView headerImageView;
    @Bind(R.id.header_search)
    LinearLayout headerSearch;
    @Bind(R.id.header_my)
    LinearLayout headerMy;
    @Bind(R.id.header)
    LinearLayout header;
    @Bind(R.id.sgh_bannerView)
    BannerView sghBannerView;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.sgh_tablayout)
    TabLayout sghTablayout;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    @Bind(R.id.sgh_vp)
    ViewPager sghVp;
    @Bind(R.id.announce)
    ImageView announce;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.header_back)
    LinearLayout headerBack;
    private List<String> imageUrlList;
    private View[] viewPageView;

    SGUHomePageViewPageAdapter sguHomePageViewPageAdapter;
    private List<Fragment> fragments;

    private final int SUSPEND = 0;
    private final int STOP = -1;
    private final int RUNNING = 1;
    private int status = 1;


    private Dialog dialog;
    private TextView putPhotoTv;
    private TextView putVideoTv;
    private final int ISPOSTINQUIRY = 1;
    private final int LISTINDEXIMAGE = 2;

    ArrayList<String> imageList = new ArrayList<>();
    List<ListIndexImageBean.DataBean> imageData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ISPOSTINQUIRY:
                    Boolean aBoolean = (Boolean) msg.obj;
                    if (aBoolean) {
                        showPutDialog();
                    } else {
                        Toast.makeText(SGHActivity.this, "您还有未完成的提问未处理", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case LISTINDEXIMAGE:
                    final List<ListIndexImageBean.DataBean> dataBeen = ((List<ListIndexImageBean.DataBean>) msg.obj);
                    imageData = dataBeen;
                    for (int i = 0; i < dataBeen.size(); i++) {
                        ListIndexImageBean.DataBean dataBean = dataBeen.get(i);
                        imageList.add(Api.baseUrl + dataBean.getPath());
                    }
                    if (dataBeen.size() > 0) {
                        sghBannerView.init(imageList, R.drawable.default_pictures, true, R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused, true);
                        sghBannerView.setAutoLoop(2000);
                    } else {
                        sghBannerView.init(null, R.drawable.default_pictures, true, R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused, false);

                    }
                    break;
            }
        }
    };

    private FragmentSGHHomePage fragmentSGHHomePage1;
    private FragmentSGHHomePage fragmentSGHHomePage2;
    private FragmentSGHHomePage fragmentSGHHomePage3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_sgh);
        ButterKnife.bind(this);

        sguListIndexImage();

        tablayoutViewPageInit();

        viewOnClick();

    }

    private void viewOnClick() {
        headerMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.userType == 3) {
                    startActivity(new Intent(SGHActivity.this, SGHDoctorActivity.class));
                } else if (User.userType == -1) {
                    startActivity(new Intent(SGHActivity.this, SgLoginActivity.class));
                } else {
                    startActivity(new Intent(SGHActivity.this, SGHMyActivity.class));
                }
            }
        });

        headerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SGHActivity.this,SGHSearchActivity.class));
            }
        });

        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                final String[] stringArray = getResources().getStringArray(R.array.sgh_homepage);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (User.userType==3) {
                            fragmentSGHHomePage1.reflash(stringArray);
                            fragmentSGHHomePage2.reflash(stringArray);
                            fragmentSGHHomePage3.reflash(stringArray);
                        }else {
                            fragmentSGHHomePage2.reflash(stringArray);
                            fragmentSGHHomePage3.reflash(stringArray);
                        }
                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });


        TypedArray actionbarSizeTypedArray = this.obtainStyledAttributes(new int[]{
                android.R.attr.actionBarSize
        });

        final float h = actionbarSizeTypedArray.getDimension(0, 0);

        appbatlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeContainer.setEnabled(true);

                    announce.setVisibility(View.GONE);
                } else {
                    if (h - appBarLayout.getHeight() == verticalOffset) {
                        swipeContainer.setEnabled(false);
                        if (User.userType == 3) {
                            announce.setVisibility(View.GONE);
                        } else {
                            announce.setVisibility(View.VISIBLE);
                        }
                    } else {
                        swipeContainer.setEnabled(false);
                        announce.setVisibility(View.GONE);
                    }

                }
            }
        });

        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.userType == -1) {
                    startActivity(new Intent(SGHActivity.this, SgLoginActivity.class));
                } else {
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            Boolean postInquiry = SGHJsonUtil.isPostInquiry(SGHActivity.this);
                            Message message = handler.obtainMessage();
                            message.what = ISPOSTINQUIRY;
                            message.obj = postInquiry;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });

        sghBannerView.setBannerOnClickListener(new BannerView.BannerOnClickListener() {
            @Override
            public void onClick(int position) {
                if (imageData != null) {
                    switch (imageData.get(position).getType()) {
                        case "1":
                            Intent intent1 = new Intent(SGHActivity.this, TeacherDetailActivity.class);
                            intent1.putExtra("teacherId", imageData.get(position).getHost());
                            startActivity(intent1);
                            break;
                        case "2":
                            Intent intent2 = new Intent(SGHActivity.this, DoctorDetailsActivity.class);
                            intent2.putExtra("doctorId", imageData.get(position).getHost());
                            startActivity(intent2);
                            break;
                        case "3":
                            Intent intent3 = new Intent(SGHActivity.this, CaseDetailsActivity.class);
                            intent3.putExtra("inquiryId", imageData.get(position).getHost());
                            startActivity(intent3);
                            break;
                        case "4":
                            Intent intent4 = new Intent(SGHActivity.this, VideoPlayActivity.class);
                            intent4.putExtra("courseId", imageData.get(position).getHost());
                            startActivity(intent4);
                            break;
                        case "9":
                            Intent intent5 = new Intent(SGHActivity.this, SGUHWebViewActivity.class);
                            intent5.putExtra("URL", imageData.get(position).getHost());
                            startActivity(intent5);
                            break;
                    }
                }
            }
        });
    }

    private void sguListIndexImage() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<ListIndexImageBean.DataBean> dataBeen = SGHJsonUtil.listIndexImage(SGHActivity.this);
                Message message = handler.obtainMessage();
                message.what = LISTINDEXIMAGE;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }


    public void showPutDialog() {

        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.sgh_dialog_put_phote_video, null, false);
        //初始化控件
        putPhotoTv = (TextView) inflate.findViewById(R.id.btn_put_photo);
        putVideoTv = (TextView) inflate.findViewById(R.id.btn_put_video);
        putPhotoTv.setOnClickListener(this);
        putVideoTv.setOnClickListener(this);

        diaLogShow(inflate);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_put_photo:
                startActivity(new Intent(this, PutQuestionsActivity.class));
                break;
            case R.id.btn_put_video:
                startActivity(new Intent(this, SGHRecordVideoActivity.class));
                break;
        }
        dialog.dismiss();
        ;
    }

    private void diaLogShow(View inflate) {
        dialog = new Dialog(SGHActivity.this, R.style.ActionSheetDialogStyle);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }


    private void tablayoutViewPageInit() {

        final String[] stringArray = getResources().getStringArray(R.array.sgh_homepage);

        fragments = new ArrayList<>();


        fragmentSGHHomePage1 = new FragmentSGHHomePage();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[0]);
        fragmentSGHHomePage1.setArguments(bundle1);

        fragmentSGHHomePage2 = new FragmentSGHHomePage();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tab", stringArray[1]);
        fragmentSGHHomePage2.setArguments(bundle2);

        fragmentSGHHomePage3 = new FragmentSGHHomePage();
        Bundle bundle3 = new Bundle();
        bundle3.putString("tab", stringArray[2]);
        fragmentSGHHomePage3.setArguments(bundle3);

        if (User.userType != 3) {
            fragments.add(fragmentSGHHomePage2);
            fragments.add(fragmentSGHHomePage3);
        } else {
            fragments.add(fragmentSGHHomePage1);
            fragments.add(fragmentSGHHomePage2);
            fragments.add(fragmentSGHHomePage3);
        }


        sghVp.setOffscreenPageLimit(2);

        sghVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        sghTablayout.setupWithViewPager(sghVp);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }

    @Override
    protected void onResume() {
        if (imageList.size() > 0) {
            sghBannerView.setAutoLoop(2000);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        sghBannerView.stop();
        super.onPause();
    }
}
