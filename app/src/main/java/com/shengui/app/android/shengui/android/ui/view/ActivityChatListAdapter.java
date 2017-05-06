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
import com.shengui.app.android.shengui.models.NoticeModel;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityChatListAdapter extends BasePlatAdapter<NoticeModel> {
    Context mContext;
    public ActivityChatListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        NoticeModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_chat_item, null);

            vh.titleTv=(TextView)itemView.findViewById(R.id.titleTv);
            vh.contentTv=(TextView)itemView.findViewById(R.id.contentTv);
            vh.timeTv=(TextView)itemView.findViewById(R.id.timeTv);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        if(!model.getRedirect_type().equals("")){
            switch (model.getRedirect_type()){
                case "0":
                    vh.personInfoIv.setImageResource(R.drawable.url_0_8_gonggao_image);
                    break;
                case "1":
                    vh.personInfoIv.setImageResource(R.drawable.tiezi_iamge_1);
                    break;
                case "2":
                    vh.personInfoIv.setImageResource(R.drawable.gongqiu_image_2);
                    break;
                case "3":
                    vh.personInfoIv.setImageResource(R.drawable.quanzi_iamge_4_3_image);
                    break;
                case "4":
                    vh.personInfoIv.setImageResource(R.drawable.quanzi_iamge_4_3_image);
                    break;
                case "5":
                    vh.personInfoIv.setImageResource(R.drawable.topic_6_5_image);
                    break;
                case "6":
                    vh.personInfoIv.setImageResource(R.drawable.topic_6_5_image);
                    break;
                case "7":
                    vh.personInfoIv.setImageResource(R.drawable.activity_7_image);
                    break;
                case "8":
                    vh.personInfoIv.setImageResource(R.drawable.url_0_8_gonggao_image);
                    break;
                case "9":
                    vh.personInfoIv.setImageResource(R.drawable.image_15_16_9_notice_image);
                    break;
                case "15":
                    vh.personInfoIv.setImageResource(R.drawable.image_15_16_9_notice_image);
                    break;
                case "16":
                    vh.personInfoIv.setImageResource(R.drawable.image_15_16_9_notice_image);
                    break;
            }
        }
        Logger.e("nitice"+model.getTitle());
        vh.titleTv.setText(model.getTitle());
        vh.contentTv.setText(model.getContent());
        vh.timeTv.setText(model.getC_time());
        return itemView;
    }
    class ViewHolder {
        TextView titleTv, contentTv,timeTv;
        CircleImageView personInfoIv;
    }
}
