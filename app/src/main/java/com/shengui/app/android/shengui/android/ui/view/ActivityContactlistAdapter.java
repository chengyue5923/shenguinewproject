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
import com.shengui.app.android.shengui.models.ContactPhoneModel;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityContactlistAdapter extends BasePlatAdapter<ContactPhoneModel> {
    Context mContext;
    public ActivityContactlistAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final ContactPhoneModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_contact_quanzi_item_item, null);
            vh.nextDetailLayout = (TextView)itemView.findViewById(R.id.nextDetailLayout);
            vh.QuanZiNameText = (TextView)itemView.findViewById(R.id.QuanZiNameText);
            vh.NumberTextView = (TextView)itemView.findViewById(R.id.NumberTextView);
            vh.personInfoIv = (CircleImageView)itemView.findViewById(R.id.personInfoIv);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.QuanZiNameText.setText(model.getmContactsName());
        vh.NumberTextView.setText(model.getmContactsNumber());
        vh.personInfoIv.setImageBitmap(model.getmContactsPhonto());
        vh.nextDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnShateItem(model);
            }
        });
        return itemView;
    }
    ShareOnClickListener listener;
    public void setOnClickListener(ShareOnClickListener lr){
        listener=lr;
    }
    public interface ShareOnClickListener{
        void OnShateItem(ContactPhoneModel model);
    }

    class ViewHolder {
        TextView nextDetailLayout,QuanZiNameText,NumberTextView;
        CircleImageView personInfoIv;
    }
}
