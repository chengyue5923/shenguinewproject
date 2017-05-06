package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
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
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoGaveBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */

public class VideoGaveAdapter extends RecyclerView.Adapter<VideoGaveAdapter.ViewHolder> {

    Context context;
    List<VideoGaveBean.DataBean> data;
    String name;

    public VideoGaveAdapter(Context context, List<VideoGaveBean.DataBean> data, String name) {
        this.context = context;
        this.data = data;
        this.name = name;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_video_gave, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data.size()==0) {
            holder.gaveNoData.setVisibility(View.VISIBLE);
            holder.gaveHaveBean.setVisibility(View.GONE);
        } else {
            holder.gaveNoData.setVisibility(View.GONE);
            holder.gaveHaveBean.setVisibility(View.VISIBLE);

            VideoGaveBean.DataBean dataBean = data.get(position);

            Glide.with(context)//頭像
                    .load(Uri.parse(Api.baseUrl + dataBean.getUserImage()))
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.gaverFace);

            holder.gaverName.setText(dataBean.getUserName());
            holder.gavePrice.setText("¥" + (((double) dataBean.getPrice()))/100);
            holder.gaveTo.setText("给"+name+"打赏了：");
            holder.gaveTime.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 1));
        }

    }


    @Override
    public int getItemCount() {
        int i = 1;
        if (data != null) {
            i = data.size();
        }
        return i;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.gaver_face)
        CircleImageView gaverFace;
        @Bind(R.id.gaver_name)
        TextView gaverName;
        @Bind(R.id.gave_to)
        TextView gaveTo;
        @Bind(R.id.gave_price)
        TextView gavePrice;
        @Bind(R.id.gave_time)
        TextView gaveTime;
        @Bind(R.id.gave_have_bean)
        LinearLayout gaveHaveBean;
        @Bind(R.id.gave_no_data)
        LinearLayout gaveNoData;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
