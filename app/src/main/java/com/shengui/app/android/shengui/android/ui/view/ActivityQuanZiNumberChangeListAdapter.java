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
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityQuanZiNumberChangeListAdapter extends BasePlatAdapter<CircleMemberDetail> {
    Context mContext;
    public ActivityQuanZiNumberChangeListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
       final  ViewHolder vh;
        final CircleMemberDetail model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_quanzi_list_item, null);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.typeImage=(ImageView)itemView.findViewById(R.id.typeImage);
            vh.changeSelectImage=(ImageView)itemView.findViewById(R.id.changeSelectImage);
            vh.nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            vh.confirmText=(TextView)itemView.findViewById(R.id.confirmText);
            vh.addressTextView=(TextView)itemView.findViewById(R.id.addressTextView);


            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }


        vh.addressTextView.setText(model.getLocation());
        vh.nameTextView.setText(model.getName());

        if(model.getSex().equals("0")){
            vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.women));
        }else{
            vh.typeImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.male));
        }

        if(model.isflag()){
            vh.changeSelectImage.setBackgroundResource(R.drawable.quanzi_list_select_view);
        }else{
            vh.changeSelectImage.setBackgroundResource(R.drawable.quanzi_unselect_images);
        }
        Glide.with(mContext).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into( vh.personInfoIv);
        vh.changeSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setIsflag(!model.isflag());
                if(model.isflag()){
                    vh.changeSelectImage.setBackgroundResource(R.drawable.quanzi_list_select_view);
                }else{
                    vh.changeSelectImage.setBackgroundResource(R.drawable.quanzi_unselect_images);
                }
                Logger.e("positon---"+position);
                dialogListener.onItemConfirm(model);
            }
        });

        return itemView;
    }
    private ItemListener dialogListener;
    public void setDialogListener(ItemListener dialogListener) {
        this.dialogListener = dialogListener;
    }



    public interface ItemListener{
        void onItemConfirm(CircleMemberDetail p);
    }
    class ViewHolder {
        CircleImageView personInfoIv;
        ImageView typeImage,changeSelectImage;
        TextView confirmText,nameTextView,addressTextView;
    }
}
