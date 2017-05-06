package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.ScanImageActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/2.
 */

public class SGHCaseDetailImageGirdAdapter extends RecyclerView.Adapter<SGHCaseDetailImageGirdAdapter.ViewHolder> {

    List<String> data;
    Context context;
    ArrayList<String> images = new ArrayList<>();

    public SGHCaseDetailImageGirdAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
        if (data!=null) {
            for (int i = 0; i < data.size(); i++) {
                images.add(Api.baseUrl + data.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgh_item_image, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context)
                .load(Api.baseUrl + data.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
//                .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
//                .error(R.mipmap.timg) //失败图片
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScanImageActivity.class);
                intent.putStringArrayListExtra("images", images);
                intent.putExtra("index",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.image)
        ImageView image;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
