package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityHotListAdapter extends BasePlatAdapter<QuanziList> {
    Context mContext;
    public ActivityHotListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        QuanziList model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_hot_item, null);
            vh.images=(CircleImageView)itemView.findViewById(R.id.images);
            vh.titile=(TextView)itemView.findViewById(R.id.titile);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        try{
            vh.titile.setText(model.getTitle());
            if(model.getAvatar().equals("")){
                Glide.with(mContext).load(mContext.getResources().getDrawable(R.drawable.default_image_av)).asBitmap().placeholder(R.drawable.default_pictures).into(vh.images);
            }else{
                Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.images);
            }

        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }

        return itemView;
    }
    class ViewHolder {
        TextView titile;
        CircleImageView images;
    }
}
