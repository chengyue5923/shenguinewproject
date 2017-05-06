package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgGuiQuanDetailActivityNewActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityQuanziListItemAdapter extends BasePlatAdapter<QuanziList> {
    Context mContext;
    boolean showSelect=true;
    private RelativeLayout reservedLayout;
    public ActivityQuanziListItemAdapter(Context context) {
        super(context);
        mContext=context;
    }
    public ActivityQuanziListItemAdapter(Context context,boolean show) {
        super(context);
        mContext=context;
        showSelect=show;
    }
    public void setTag(boolean flag){
        haveTag=flag;
    }
    private boolean haveTag=true;
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        QuanziList model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_quanzi_item_item, null);
            vh.addressQuanziText=(TextView)itemView.findViewById(R.id.addressQuanziText);
            vh.QuanZiNameText=(TextView)itemView.findViewById(R.id.QuanZiNameText);
            vh.QuanzitypeText=(TextView)itemView.findViewById(R.id.QuanzitypeText);
            vh.NumberTextView=(TextView)itemView.findViewById(R.id.NumberTextView);
            vh.tiezaiNumberText=(TextView)itemView.findViewById(R.id.tiezaiNumberText);
            vh.textDetail=(TextView)itemView.findViewById(R.id.textDetail);
            vh.nextDetailLayout=(ImageView)itemView.findViewById(R.id.nextDetailLayout);
            vh.personLayout=(LinearLayout)itemView.findViewById(R.id.personLayout);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        if(showSelect){
            vh.nextDetailLayout.setVisibility(View.VISIBLE);
        }else{
            vh.nextDetailLayout.setVisibility(View.VISIBLE);
            vh.nextDetailLayout.setBackground(mContext.getResources().getDrawable(R.drawable.detail_next_img));
        }
        vh.personLayout.removeAllViews();

        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            if(Integer.parseInt(model.getUser_id())==UserPreference.getUid()){
                RelativeLayout reservedLayout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.activity_tag_activity, null);
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                reservedLayout.setLayoutParams(layoutParams);
                TextView QuanzitypeText=(TextView)reservedLayout.findViewById(R.id.QuanzitypeText);
                QuanzitypeText.setText("自创");
                vh.personLayout.addView(reservedLayout);
            }
        }
//        if(model.getIs_public().equals("1")){
//            RelativeLayout reservedLayout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.activity_tag_activity, null);
//            LinearLayout.LayoutParams layoutParams =
//                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            reservedLayout.setLayoutParams(layoutParams);
//            TextView QuanzitypeText=(TextView)reservedLayout.findViewById(R.id.QuanzitypeText);
//            QuanzitypeText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_guanfang));
//            QuanzitypeText.setText("官方");
//            vh.personLayout.addView(reservedLayout);
//
//
//        }
        if(haveTag){
            if(model.getTag()!=null&&model.getTag().size()>0){
                for(int i=0;i<model.getTag().size();i++){
                    RelativeLayout reservedLayout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.activity_tag_activity, null);
                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    reservedLayout.setLayoutParams(layoutParams);
                    TextView QuanzitypeText=(TextView)reservedLayout.findViewById(R.id.QuanzitypeText);
                    if(model.getTag().get(i).getName().equals("官方")){
                        QuanzitypeText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_office));
                    }
//                    if(model.getTag().get(i).getName().equals("名人")){
//                        QuanzitypeText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_confirm));
//                    }
                    QuanzitypeText.setText(model.getTag().get(i).getName());
                    vh.personLayout.addView(reservedLayout);
                }
            }
        }
        vh.NumberTextView.setText(model.getMember_num());
        vh.QuanZiNameText.setText(model.getTitle());
        vh.tiezaiNumberText.setText(model.getPost_num());
        vh.addressQuanziText.setText(model.getCity_name());
        vh.textDetail.setText(model.getDesc());
        try{

            Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image_av).into(vh.personInfoIv);
        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }
        return itemView;
    }
    class ViewHolder {
        CircleImageView personInfoIv;
        LinearLayout personLayout;
        ImageView nextDetailLayout;
        TextView QuanZiNameText,QuanzitypeText,NumberTextView,tiezaiNumberText,addressQuanziText,textDetail;

    }
}
