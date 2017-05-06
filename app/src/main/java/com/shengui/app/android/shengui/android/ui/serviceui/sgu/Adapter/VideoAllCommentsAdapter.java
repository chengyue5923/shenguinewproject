package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.AllcommentsBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/29.
 */

public class VideoAllCommentsAdapter extends RecyclerView.Adapter<VideoAllCommentsAdapter.ViewHolder> {

    Context context;
    List<AllcommentsBean.DataBean> dataBean;

    public VideoAllCommentsAdapter(Context context, List<AllcommentsBean.DataBean> dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_comments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.allComments.setVisibility(View.GONE);
        if (dataBean == null) {
            holder.videoCommentNoData.setVisibility(View.VISIBLE);
            holder.videoCommentsHaveBean.setVisibility(View.GONE);
        } else {

            final AllcommentsBean.DataBean data = dataBean.get(position);
            holder.videoCommentNoData.setVisibility(View.GONE);
            holder.videoCommentsHaveBean.setVisibility(View.VISIBLE);


            holder.commentReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoPlayActivity activity = (VideoPlayActivity) VideoAllCommentsAdapter.this.context;
                    activity.allCommentVisible(dataBean, position);
                    if (!data.getUserId().equals(User.userId)) {
                        activity.allCommentVisible(dataBean, position);
                    } else {
                        activity.toastPrompt();
                    }
                }
            });

            Glide.with(context)// 頭像
                    .load(Uri.parse(Api.baseUrl + data.getUserImage()))
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.peopleFace);

            holder.commentName.setText(data.getUserRealName());
            if (!data.getUserId().equals(data.getReceiver())) {
                holder.commentMemo.setText("回复:@" + data.getReceiverRealName() + " " + data.getContent());
            } else {
                holder.commentMemo.setText(data.getContent());
            }

            holder.commentTime.setText(CreateTimeUtil.time(data.getCreateTime(), 1));


        }

    }


    private SpannableStringBuilder getSpannableString(AllcommentsBean.DataBean commentData, String s) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(s);

        styledText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.comment_color_1)), 0, commentData.getReceiverRealName().length() + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.comment_color_2)), commentData.getReceiverRealName().length() + 4, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return styledText;
    }


    @Override
    public int getItemCount() {
        int i = 1;
        if (dataBean != null) {
            i = dataBean.size();
        }
        return i;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
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

