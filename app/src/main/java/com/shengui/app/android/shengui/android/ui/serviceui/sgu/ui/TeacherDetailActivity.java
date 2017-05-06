package com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHSearchActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoRecyclerViewAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeacherDataBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TeacherDetailActivity extends SGUHBaseActivity {


    @Bind(R.id.teacher_search_init)
    TextView teacherSearchInit;
    @Bind(R.id.teacher_header_search)
    LinearLayout teacherHeaderSearch;
    @Bind(R.id.teacher_search_back)
    LinearLayout teacherSearchBack;
    @Bind(R.id.teacher_face)
    CircleImageView teacherFace;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.teacher_memo)
    TextView teacherMemo;
    @Bind(R.id.teacher_courseCount)
    TextView teacherCourseCount;
    @Bind(R.id.teacher_viewsCount)
    TextView teacherViewsCount;
    @Bind(R.id.teacher_experience)
    TextView teacherExperience;
    @Bind(R.id.video_numb)
    TextView videoNumb;
    @Bind(R.id.teacherDetailRecylerView)
    RecyclerView teacherDetailRecylerView;
    @Bind(R.id.activity_teacher_detail)
    LinearLayout activityTeacherDetail;
    private String teacherId;

    private final int TEACHERDATA = 1;
    private final int COURSEDATA = 2;

    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TEACHERDATA:
                    TeacherDataBean.DataBean teacherData = (TeacherDataBean.DataBean) msg.obj;
                    teacherName.setText(teacherData.getTruename());
                    Glide.with(TeacherDetailActivity.this)
                            .load(Api.SGHBaseUrl + teacherData.getAvatar())
                            .skipMemoryCache(false)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(teacherFace);

                    teacherMemo.setText(teacherData.getSignature());
                    teacherCourseCount.setText(teacherData.getCourseCount() + "");
                    teacherViewsCount.setText(teacherData.getViewsCount() + "");
                    teacherExperience.setText(teacherData.getExtend());
                    courseInit(teacherData.getTruename());
                    break;

                case COURSEDATA:
                    List<VideoListViewBean.DataBean> obj = (List<VideoListViewBean.DataBean>) msg.obj;
                    videoNumb.setText("相关视频（" + obj.size() + ")");
                    videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(TeacherDetailActivity.this, obj);
                    teacherDetailRecylerView.setAdapter(videoRecyclerViewAdapter);
                    break;

            }
        }
    };
    private InputMethodManager methodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgu_activity_teacher_detail);
        ButterKnife.bind(this);


        teacherDetailRecylerView.setLayoutManager(new LinearLayoutManager(TeacherDetailActivity.this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        teacherId = intent.getStringExtra("teacherId");

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                TeacherDataBean.DataBean dataBean = JsonUitl.teacherDetail(TeacherDetailActivity.this, teacherId);
                Message message = handler.obtainMessage();
                message.what = TEACHERDATA;
                message.obj = dataBean;
                handler.sendMessage(message);
            }
        });

        teacherSearchInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherDetailActivity.this,SearchActivity.class));
            }
        });

        teacherSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow((TeacherDetailActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void courseInit(final String keyword) {

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoListViewBean.DataBean> dataBeen = JsonUitl.searchResult(TeacherDetailActivity.this, keyword);
                Message message = handler.obtainMessage();
                message.what = COURSEDATA;
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });
    }
}
