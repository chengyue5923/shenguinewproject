package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageRVAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorDetailBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DoctorDetailsActivity extends SGUHBaseActivity {


    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.doctor_header)
    LinearLayout doctorHeader;
    @Bind(R.id.doctor_face)
    CircleImageView doctorFace;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.doctor_sign)
    TextView doctorSign;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.doctor_select_tv1)
    TextView doctorSelectTv1;
    @Bind(R.id.doctor_select_v1)
    View doctorSelectV1;
    @Bind(R.id.doctor_select_tv2)
    TextView doctorSelectTv2;
    @Bind(R.id.doctor_select_v2)
    View doctorSelectV2;
    @Bind(R.id.appbatlayout)
    AppBarLayout appbatlayout;
    @Bind(R.id.doctor_experience)
    TextView doctorExperience;
    @Bind(R.id.doctor_Inquiry)
    RecyclerView doctorInquiry;
    @Bind(R.id.no_inquiry)
    TextView noInquiry;
    @Bind(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;
    private String doctorId;

    private SGHHomePageRVAdapter sghHomePageRVAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOCTORDETAIL:
                    DoctorDetailBean.DataBean dataBean = (DoctorDetailBean.DataBean) msg.obj;
                    Glide.with(DoctorDetailsActivity.this)
                            .load(Api.baseUrl + dataBean.getAvatar())
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(doctorFace);
                    teacherName.setText(dataBean.getTruename());
                    doctorSign.setText(dataBean.getSignature());
                    doctorExperience.setText(dataBean.getExtend());
                    break;
                case DOCTORINQUIRY:
                    List<InquiryListBean.DataBean> inquiryDataBeen = (List<InquiryListBean.DataBean>) msg.obj;
                    Log.e("test", "handleMessage: " + inquiryDataBeen.size());
                    doctorSelectTv2.setText("诊断病例（" + inquiryDataBeen.size() + "）");
                    sghHomePageRVAdapter = new SGHHomePageRVAdapter(DoctorDetailsActivity.this, inquiryDataBeen);
                    doctorInquiry.setAdapter(sghHomePageRVAdapter);
                    sghHomePageRVAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private final int DOCTORDETAIL = 1;
    private final int DOCTORINQUIRY = 2;
    private LinearLayoutManager layoutManager;
    private int height = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_doctor_details);
        ButterKnife.bind(this);


        ViewTreeObserver viewTreeObserver = doctorExperience.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                doctorExperience.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                height = doctorExperience.getHeight();
                Log.e("test", "onGlobalLayout: " + height);
            }
        });

        nestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Rect scrollRect = new Rect();
                nestedScrollview.getHitRect(scrollRect);
                //子控件在可视范围内（至少有一个像素在可视范围内）
                if (doctorExperience.getLocalVisibleRect(scrollRect)) {
                    doctorSelectV1.setBackgroundResource(R.color.main_color);
                    doctorSelectTv1.setTextColor(getResources().getColor(R.color.main_color));
                    doctorSelectV2.setBackgroundResource(R.color.comment_color_2);
                    doctorSelectTv2.setTextColor(getResources().getColor(R.color.comment_color_2));
                } else {
                    ////子控件完全不在可视范围内
                    doctorSelectV2.setBackgroundResource(R.color.main_color);
                    doctorSelectTv2.setTextColor(getResources().getColor(R.color.main_color));
                    doctorSelectV1.setBackgroundResource(R.color.comment_color_2);
                    doctorSelectTv1.setTextColor(getResources().getColor(R.color.comment_color_2));
                }
            }
        });

        layoutManager = new LinearLayoutManager(DoctorDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
        doctorInquiry.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        doctorId = intent.getStringExtra("doctorId");

        if (doctorId != null) {
            dataInit(doctorId, 1);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dataInit(final String doctorId, final int p) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                DoctorDetailBean.DataBean dataBean = SGHJsonUtil.DoctorDetail(DoctorDetailsActivity.this, doctorId);
                Message message = handler.obtainMessage();
                message.what = DOCTORDETAIL;
                message.obj = dataBean;
                handler.sendMessage(message);
            }
        });

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.doctorInquiry(DoctorDetailsActivity.this, doctorId, p);
                Message message = handler.obtainMessage();
                message.what = DOCTORINQUIRY;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }
}
