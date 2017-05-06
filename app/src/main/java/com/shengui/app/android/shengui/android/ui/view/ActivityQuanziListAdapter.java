package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityQuanziListAdapter extends BasePlatAdapter<QuanziList> {
    Context mContext;
    public ActivityQuanziListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final QuanziList model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_quanzi_item_item, null);
            vh.nextDetailLayout = (ImageView)itemView.findViewById(R.id.nextDetailLayout);
            vh.personInfoIv = (CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.QuanZiNameText = (TextView)itemView.findViewById(R.id.QuanZiNameText);
            vh.QuanzitypeText = (TextView)itemView.findViewById(R.id.QuanzitypeText);
            vh.NumberTextView = (TextView)itemView.findViewById(R.id.NumberTextView);
            vh.tiezaiNumberText = (TextView)itemView.findViewById(R.id.tiezaiNumberText);
            vh.addressQuanziText = (TextView)itemView.findViewById(R.id.addressQuanziText);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.QuanZiNameText.setText(model.getTitle());
        vh.tiezaiNumberText.setText(model.getPost_num());
        vh.addressQuanziText.setText(model.getCity_name());
        vh.NumberTextView.setText(model.getMember_num());
        if(model.isCheck()){
            vh.nextDetailLayout.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quanzi_list_select_view));
        }else{
            vh.nextDetailLayout.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quanzi_unselect_images));
        }
        vh.nextDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vh.nextDetailLayout.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quanzi_list_select_view));
                for(QuanziList mo:getItems()){
                    mo.setCheck(false);
                }
                model.setCheck(!model.isCheck());
                notifyDataSetChanged();
            }
        });
        Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(vh.personInfoIv);
        return itemView;
    }
    class ViewHolder {
        TextView QuanZiNameText,QuanzitypeText,NumberTextView,tiezaiNumberText,addressQuanziText;
        ImageView nextDetailLayout;
        CircleImageView personInfoIv;
    }
}
