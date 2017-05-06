package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.shengui.app.android.shengui.utils.im.CommonUtil;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityGuiMiNumberListAdapter extends BasePlatAdapter<CircleMemberDetail> {
    Context mContext;
    public ActivityGuiMiNumberListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        final CircleMemberDetail model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_guimi_member_list_item, null);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.typeImage=(ImageView)itemView.findViewById(R.id.typeImage);
            vh.nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            vh.confirmText=(TextView)itemView.findViewById(R.id.confirmText);
            vh.addressTextView=(TextView)itemView.findViewById(R.id.addressTextView);
            vh.phoneIv=(ImageView)itemView.findViewById(R.id.phoneIv);
            vh.otherinfo=(RelativeLayout)itemView.findViewById(R.id.otherinfo);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        try{
            vh.confirmText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!model.getId().equals(UserPreference.getUid())){
                        Logger.e("avaster--------------------"+model.getAvatar());
                        IntentTools.startChat(mContext,Integer.parseInt(model.getId()),model.getName(),model.getAvatar());
                    }
                }
            });
            vh.addressTextView.setText(model.getLocation());
            vh.nameTextView.setText(model.getName());
            if(model.getSex()!=null&&model.getSex().equals("0")){
                vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.women));
            }else{
                vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.male));
            }
            Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(vh.personInfoIv);

        }catch (Exception e){
            Logger.e("exception"+e.getMessage());
        }
        vh.otherinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startOtherDetail(mContext,Integer.parseInt(model.getId()));
            }
        });
        vh.personInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startOtherDetail(mContext,Integer.parseInt(model.getId()));
            }
        });
        vh.phoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.call(mContext, model.getMobile());
            }
        });

        return itemView;
    }
    class ViewHolder {
        CircleImageView personInfoIv;
        ImageView typeImage,phoneIv;
        RelativeLayout otherinfo;
        TextView confirmText,nameTextView,addressTextView;
    }
}
