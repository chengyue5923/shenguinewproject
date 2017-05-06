package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/23.
 */

public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.ViewHolder> {

    Context context;
    List<VideoCommentBean.DataBeanX> dataBeanXes;
    VideoPlayActivity activityContext;
    public VideoCommentCallRecylerViewAdapter videoCommentCallRecylerViewAdapter;



    public VideoCommentAdapter(Context context, List<VideoCommentBean.DataBeanX> dataBeanXes) {
        this.context = context;
        this.dataBeanXes = dataBeanXes;
        activityContext = (VideoPlayActivity) VideoCommentAdapter.this.context;
        for (int i = 0; i < dataBeanXes.size(); i++) {
            if (dataBeanXes.get(i).getCountComments() > 0) {
                dataBeanXes.get(i).setTag(true);
            }
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_comments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.setIsRecyclable(false); 设置不复用,不建议使用
        if (dataBeanXes.size() == 0) {
            holder.videoCommentNoData.setVisibility(View.VISIBLE);
            holder.videoCommentsHaveBean.setVisibility(View.GONE);
        } else {

            holder.videoCommentNoData.setVisibility(View.GONE);
            holder.videoCommentsHaveBean.setVisibility(View.VISIBLE);

            final VideoCommentBean.DataBeanX dataBeanX = dataBeanXes.get(position);

            Log.e("test", "onBindViewHolder: " + dataBeanX.getCountComments());

            if (dataBeanX.getTop() == 1) {
                holder.videoCommentTop.setVisibility(View.VISIBLE);
            } else {
                holder.videoCommentTop.setVisibility(View.GONE);
            }

            Glide.with(context)// 頭像
                    .load(Uri.parse(Api.baseUrl + dataBeanX.getUserImage()))
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.peopleFace);
            holder.commentName.setText(dataBeanX.getUserName());
            holder.commentMemo.setText(dataBeanX.getContent());
            holder.commentTime.setText(CreateTimeUtil.time(dataBeanX.getCreateTime(), 1));

            int countComments = dataBeanX.getCountComments();

            if (dataBeanXes.get(position).getTag()) {
                holder.commentCallRecylerView.setVisibility(View.VISIBLE);
            } else {
                holder.commentCallRecylerView.setVisibility(View.GONE);
            }

            if (countComments > 0) {
                holder.commentCallRecylerView.setVisibility(View.VISIBLE);

                holder.commentCallRecylerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                List<VideoCommentBean.DataBeanX.DataBean> data = dataBeanX.getData();

                videoCommentCallRecylerViewAdapter = new VideoCommentCallRecylerViewAdapter(context, dataBeanX, position);

                holder.commentCallRecylerView.setAdapter(videoCommentCallRecylerViewAdapter);

                videoCommentCallRecylerViewAdapter.notifyDataSetChanged();
            }

            if (countComments > 3) {
                holder.allComments.setVisibility(View.VISIBLE);
                holder.allComments.setText("查看全部" + countComments + "条评论");
            } else {
                holder.allComments.setVisibility(View.GONE);
            }

            holder.allComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityContext.allCommentControl(dataBeanX.getId(), dataBeanX);
                }
            });

            holder.commentReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityContext.inputVisible(dataBeanX, true, position, -1);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        int i = 1;
        if (dataBeanXes != null) {
            i = dataBeanXes.size();
        }
        return i;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        @Bind(R.id.people_face)
        CircleImageView peopleFace;
        @Bind(R.id.comment_name)
        TextView commentName;
        @Bind(R.id.video_comment_top)
        TextView videoCommentTop;
        @Bind(R.id.comment_memo)
        TextView commentMemo;
        @Bind(R.id.comment_time)
        TextView commentTime;
        @Bind(R.id.comment_reply)
        TextView commentReply;
        @Bind(R.id.comment_call_recyler_view)
        RecyclerView commentCallRecylerView;
        @Bind(R.id.all_comments)
        TextView allComments;
        @Bind(R.id.video_comments_have_bean)
        LinearLayout videoCommentsHaveBean;
        @Bind(R.id.video_comment_no_data)
        LinearLayout videoCommentNoData;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
