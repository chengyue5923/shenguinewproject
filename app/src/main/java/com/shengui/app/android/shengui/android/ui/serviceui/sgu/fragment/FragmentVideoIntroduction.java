package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeacherDataBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.TeacherDetailActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FragmentVideoIntroduction extends BaseFragment {


    @Bind(R.id.video_intro)
    TextView videoIntro;
    @Bind(R.id.teacher_face)
    RoundedImageView teacherFace;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.teacher_memo)
    TextView teacherMemo;
    @Bind(R.id.teacher_courseCount)
    TextView teacherCourseCount;
    @Bind(R.id.teacher_viewsCount)
    TextView teacherViewsCount;
    @Bind(R.id.teacher_intro)
    TextView teacherIntro;

    View view;
    @Bind(R.id.teacher_More)

    ImageView teacherMore;
    private String courseId;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TeacherDataBean.DataBean dataBean = (TeacherDataBean.DataBean) msg.obj;

            if (dataBean != null) {
                Glide.with(getContext())
                        .load(Api.baseUrl + dataBean.getAvatar())
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(teacherFace);

                teacherName.setText(dataBean.getTruename());
                teacherMemo.setText(dataBean.getSignature());
                teacherCourseCount.setText(dataBean.getCourseCount() + "");
                teacherViewsCount.setText(dataBean.getViewsCount() + "");
                teacherIntro.setText(dataBean.getExtend());

            }
        }
    };
    private String teacherId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sgu_fragment_video_introduction, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        teacherId = arguments.getString("teacherId");
        String intro = arguments.getString("intro");

        videoIntro.setText(intro);
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                TeacherDataBean.DataBean dataBean = JsonUitl.UserData(getContext(), teacherId, 2);
                Message message = handler.obtainMessage();
                message.obj = dataBean;
                handler.sendMessage(message);
            }
        });

        teacherMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TeacherDetailActivity.class);
                intent.putExtra("teacherId",teacherId);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}

