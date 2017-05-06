package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityDetailListAdapter extends BasePlatAdapter<ProductModel> {
    Context mContext;
    public ActivityDetailListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ProductModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_detail_item, null);

            vh.avtivityDetail=(ImageView)itemView.findViewById(R.id.avtivityDetail);
            vh.priductTimeText=(TextView)itemView.findViewById(R.id.priductTimeText);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        return itemView;
    }
    class ViewHolder {
        TextView priductTimeText;
        ImageView avtivityDetail;
    }
}
