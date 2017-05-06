package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.TeachersTeamBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.TeacherDetailActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/17.
 */

public class TeachersRecyclerViewAdapter extends RecyclerView.Adapter<TeachersRecyclerViewAdapter.ViewHolder> {

    List<TeachersTeamBean.DataBean> data;
    Context context;

    public TeachersRecyclerViewAdapter(List data, Context context) {

        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_teacher, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (data.size() != 0) {

            if (data.size() == 1) {
                holder.haveBean.setVisibility(View.VISIBLE);
                holder.teacherNoData.setVisibility(View.VISIBLE);
                holder.noDataTv.setText("");
            } else {
                holder.haveBean.setVisibility(View.VISIBLE);
                holder.teacherNoData.setVisibility(View.GONE);
            }


            final TeachersTeamBean.DataBean dataBean = data.get(position);

            Glide.with(context)
                    .load(Uri.parse(Api.baseUrl + dataBean.getAvatar()))
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.teacherFace);

            holder.teacherName.setText(dataBean.getName());

            holder.teacherMemo.setText(dataBean.getSignature());

            holder.teacherCourseCount.setText(dataBean.getCourseCount() + "");

            holder.teacherViewsCount.setText(dataBean.getViewsCount() + "");

            holder.haveBean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeacherDetailActivity.class);
                    intent.putExtra("teacherId", dataBean.getId());
                    context.startActivity(intent);
                }
            });
        } else {

            holder.haveBean.setVisibility(View.GONE);
            holder.teacherNoData.setVisibility(View.VISIBLE);
            holder.noDataTv.setText("抱歉，没有数据");
        }

    }


    @Override
    public int getItemCount() {

        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.teacher_face)
        RoundedImageView teacherFace;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.teacher_memo)
        TextView teacherMemo;
        @Bind(R.id.teacher_courseCount)
        TextView teacherCourseCount;
        @Bind(R.id.view)
        View view;
        @Bind(R.id.teacher_viewsCount)
        TextView teacherViewsCount;
        @Bind(R.id.have_bean)
        LinearLayout haveBean;
        @Bind(R.id.no_data_tv)
        TextView noDataTv;
        @Bind(R.id.teacher_no_data)
        LinearLayout teacherNoData;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
