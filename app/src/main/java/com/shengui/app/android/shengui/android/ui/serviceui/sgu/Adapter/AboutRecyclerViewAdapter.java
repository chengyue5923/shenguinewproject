package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kdmobi.gui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/17.
 */

public class AboutRecyclerViewAdapter extends RecyclerView.Adapter<AboutRecyclerViewAdapter.ViewHolder> {


    Context context;

    public AboutRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sgu_about_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {

        return 1;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.about_tv)
        TextView aboutTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
