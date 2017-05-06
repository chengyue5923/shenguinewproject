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
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class SearchGuiMiListAdapter extends BasePlatAdapter<LoginResultModel> {
    Context mContext;
    public SearchGuiMiListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        LoginResultModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_searchitem, null);
            vh.QuanZiNameText=(TextView)itemView.findViewById(R.id.QuanZiNameText);
            vh.textDetail=(TextView)itemView.findViewById(R.id.textDetail);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.QuanzitypeText=(ImageView)itemView.findViewById(R.id.QuanzitypeText);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        try{
            Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(vh.personInfoIv);
            vh.QuanZiNameText.setText(model.getName());
            if(model.getSex()==0){
                vh.QuanzitypeText.setImageDrawable(mContext.getResources().getDrawable(R.drawable.women));
            }else{
                vh.QuanzitypeText.setImageDrawable(mContext.getResources().getDrawable(R.drawable.male));
            }
            vh.textDetail.setText(model.getSignature());
        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }


        return itemView;
    }
    class ViewHolder {
        TextView QuanZiNameText,textDetail;
        CircleImageView personInfoIv;
        ImageView QuanzitypeText;
    }
}
