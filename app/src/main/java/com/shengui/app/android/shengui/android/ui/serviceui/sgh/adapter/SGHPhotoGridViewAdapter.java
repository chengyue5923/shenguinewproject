package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.PutQuestionsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;


/**
 * Created by Administrator on 2017/4/5.
 */


public class SGHPhotoGridViewAdapter extends RecyclerView.Adapter<SGHPhotoGridViewAdapter.ViewHolder> {

    PutQuestionsActivity putQuestionsActivity;
    List<MediaBean> data;
    Context context;


    public SGHPhotoGridViewAdapter(List<MediaBean> data, Context context) {
        this.data = data;
        this.context = context;
        putQuestionsActivity = ((PutQuestionsActivity) context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgh_item_photo, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (data == null || position == data.size()) {

            holder.photo.setImageResource(R.mipmap.icon_addpic);
            holder.delete.setVisibility(View.GONE);

            holder.photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    putQuestionsActivity.photoCallBack(data);

                }
            });
        } else {
            if (position < data.size()) {

                Glide.with(context)
                        .load(data.get(position).getThumbnailBigPath())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                        .into(holder.photo);

                holder.delete.setVisibility(View.VISIBLE);

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.size() == 1) {
                            data = null;
                        } else {
                            data.remove(position);
                        }

                        notifyDataSetChanged();
                    }
                });
            }
        }


    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 1;
        } else if (data.size() < 9) {
            return data.size() + 1;
        } else {
            return data.size();
        }


    }


    static class ViewHolder  extends RecyclerView.ViewHolder{
        @Bind(R.id.photo)
        ImageView photo;
        @Bind(R.id.delete)
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

