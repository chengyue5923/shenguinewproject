package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by bangping on 2016/8/14.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.LeaveMessageHolder> {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<ProductModel> messageModleList;
    private ProductModel messageModle;
    private ListView messageList;

    public ProductListAdapter(Context mContext) {
        this.mContext = mContext;
        messageModleList = new ArrayList<ProductModel>();
        layoutInflater = LayoutInflater.from(mContext);
    }

    public void setChangeData(List<ProductModel> messageModleList) {
        this.messageModleList.clear();
        this.messageModleList.addAll(messageModleList);
        notifyDataSetChanged();
    }

    @Override
    public LeaveMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.view_activity_item, null);
        return new LeaveMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaveMessageHolder holder, final int position) {
        messageModle = (ProductModel) messageModleList.get(position);
//        holder.coursename.setText(messageModle.getUser_info().getName());
//        holder.courselang.setText(messageModle.getC_time());
//        holder.commentContext.setText(messageModle.getContents());
//        holder.commentnum.setText(messageModle.getReview_num());
//        holder.reviewLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return messageModleList.size();
    }


    class LeaveMessageHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.productImageView)
        ImageView productImageView;
        @Bind(R.id.priductTimeText)
        TextView priductTimeText;
        @Bind(R.id.productTitleNameText)
        TextView productTitleNameText;
        @Bind(R.id.productBuyText)
        TextView productBuyText;
        @Bind(R.id.productBuyTypeText)
        TextView productBuyTypeText;
        @Bind(R.id.detailTextView)
        TextView detailTextView;
        @Bind(R.id.textView)
        TextView textView;
        @Bind(R.id.texttelTextView)
        TextView texttelTextView;
        @Bind(R.id.textAddressText)
        TextView textAddressText;
        View itemView;
        public LeaveMessageHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
