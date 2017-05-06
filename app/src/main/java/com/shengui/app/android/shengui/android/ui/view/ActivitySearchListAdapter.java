package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivitySearchListAdapter extends BasePlatAdapter<ImageModel> {
    Context mContext;
    public ActivitySearchListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ImageModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_list_view, null);

            vh.adapter_text=(TextView)itemView.findViewById(R.id.adapter_text);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.adapter_text.setText(model.getName());
        return itemView;
    }
    class ViewHolder {
        TextView adapter_text;
    }
}
