package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoCommentBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.NoRecylerView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/29.
 */


public class VideoCommentCallRecylerViewAdapter extends NoRecylerView.Adapter<VideoCommentCallRecylerViewAdapter.ViewHolder> {

    Context context;
    VideoCommentBean.DataBeanX data;
    private VideoPlayActivity activityContext;
    int place;

    public VideoCommentCallRecylerViewAdapter(Context context, VideoCommentBean.DataBeanX data, int place) {
        this.context = context;
        this.data = data;
        this.place = place;
        activityContext = ((VideoPlayActivity) context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_comment_call, null);//c参数设置
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final VideoCommentBean.DataBeanX.DataBean dataBean = data.getData().get(position);
        SpannableStringBuilder styledText = null;
        int len = 0;
        if (dataBean.getUserId().equals(dataBean.getReceiver())) {
            len = dataBean.getUserName().length() + 2;
            String s = dataBean.getUserName() + ":" + " " + dataBean.getContent();
            styledText = getSpannableString(s, len);//一个textview设置两种样式
        } else {
            len = dataBean.getUserName().length() + dataBean.getReceiverName().length() + 2;
            String s = dataBean.getUserName() + ":@" + dataBean.getReceiverName() + " " + dataBean.getContent();
            styledText = getSpannableString(s, len);//一个textview设置两种样式
        }
        holder.commentCallRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dataBean.getUserId().equals(User.userId)) {
                    activityContext.inputVisible(data, true, place, position);
                } else {
                    activityContext.toastPrompt();
                }
            }
        });

        holder.commentCall.setText(styledText);
    }


    @Override
    public int getItemCount() {
        if (data.getData().size() > 3) {
            return 3;
        } else {
            return data.getData().size();
        }
    }


    private SpannableStringBuilder getSpannableString(String s, int len) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(s);

        styledText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.comment_color_1)), 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.comment_color_2)), len, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return styledText;
    }

    public void addComments(VideoCommentBean.DataBeanX.DataBean dataAllComments) {
        data.getData().add(0, dataAllComments);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.comment_call)
        TextView commentCall;
        @Bind(R.id.comment_call_rl)
        RelativeLayout commentCallRl;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

