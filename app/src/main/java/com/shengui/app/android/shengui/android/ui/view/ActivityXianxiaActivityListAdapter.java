package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.DensityUtils;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityXianxiaActivityListAdapter extends BasePlatAdapter<ActivityModel> {
    Context mContext;
    public ActivityXianxiaActivityListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ActivityModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_xianxia_activity_item, null);
            vh.avName=(TextView)itemView.findViewById(R.id.avName);
            vh.numberText=(TextView)itemView.findViewById(R.id.numberText);
            vh.signNumber=(TextView)itemView.findViewById(R.id.signNumber);
            vh.singState=(TextView)itemView.findViewById(R.id.singState);
            vh.detailImage=(ImageView)itemView.findViewById(R.id.detailImage);
            vh.signImage=(ImageView)itemView.findViewById(R.id.signImage);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }


        try{
            String endtime=model.getEtime()+"000";
            long nowtime=new Date().getTime();
            if(nowtime>Long.parseLong(endtime)){
//                vh.singState.setText("报名结束");

                vh.signImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.activity_over_image));
            }else{
//                vh.singState.setText("报名中");
                vh.signImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.activity_stil_image));
            }
            vh.numberText.setText(model.getPrice()+"元/人");
        }catch (Exception e){
            Logger.e("exception"+e.getMessage());
        }
        if(StringTools.isNullOrEmpty((model.getCover()))){
            Picasso.with(mContext).load(R.drawable.default_pictures).resize( context.getResources().getDisplayMetrics().widthPixels,DensityUtils.dp2px(mContext,350))
                    .into(vh.detailImage);
        }else {
            Picasso.with(mContext).load(model.getCover()).resize( context.getResources().getDisplayMetrics().widthPixels,DensityUtils.dp2px(mContext,350))
                    .into(vh.detailImage);
        }
//        Glide.with(mContext).load(model.getCover()).asBitmap().placeholder(R.drawable.default_pictures).into( vh.detailImage);
        vh.avName.setText(model.getTitle());
        vh.signNumber.setText("已报名"+model.getApply_num()+"人");
        return itemView;
    }
    class ViewHolder {
        TextView avName, numberText,signNumber,singState;
        ImageView detailImage,signImage;
    }
}
