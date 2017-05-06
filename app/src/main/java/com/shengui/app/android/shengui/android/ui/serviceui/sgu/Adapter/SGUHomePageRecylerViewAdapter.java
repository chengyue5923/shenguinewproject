package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class SGUHomePageRecylerViewAdapter extends RecyclerView.Adapter<SGUHomePageRecylerViewAdapter.ViewHolder> {

    Context context;
    List<VideoListViewBean.DataBean> data;

    public SGUHomePageRecylerViewAdapter(Context context,  List<VideoListViewBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_hp_center_item, parent, false);//找到相应的item view，传递给viewHolder
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoListViewBean.DataBean dataBean = data.get(position);
        holder.rvItemImageView.setBackgroundResource(R.mipmap.video);
//        holder.rvItemTextView.setText("养龟十余载，产蛋不容易  110704");
        Glide.with(context)
                .load(Uri.parse(Api.baseUrl + dataBean.getCover()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.rvItemImageView);
        if (dataBean.getPrice()!=0){
            holder.freeTv.setBackgroundResource(R.color.main_color);
            holder.freeTv.setText("付费");
        }else {
            holder.freeTv.setBackgroundResource(R.color.black);
            holder.freeTv.setText("免费");
        }

        holder.rvItemTextView.setText(dataBean.getIntro());

        holder.rvItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("courseId",dataBean.getId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ImageView rvItemImageView;
        TextView rvItemTextView;
        TextView freeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            rvItemImageView = ((ImageView) itemView.findViewById(R.id.rvItemImageView));
            rvItemTextView = ((TextView) itemView.findViewById(R.id.rvItemTextView));
            freeTv = ((TextView) itemView.findViewById(R.id.freeTv));
        }
    }
}
