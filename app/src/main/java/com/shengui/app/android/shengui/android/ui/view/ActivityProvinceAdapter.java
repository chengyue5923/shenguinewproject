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
import com.shengui.app.android.shengui.models.ProvinceModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityProvinceAdapter extends BasePlatAdapter<ProvinceModel> {
    Context mContext;
    public ActivityProvinceAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ProvinceModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_address_list_province_view, null);
            vh.nameTv=(TextView)itemView.findViewById(R.id.nameTv);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        vh.nameTv.setText(model.getName());
        return itemView;
    }
    class ViewHolder {
        TextView nameTv;
    }
}
