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
import com.shengui.app.android.shengui.models.TopicModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityTopAdjustListAdapter extends BasePlatAdapter<TopicModel> {
    Context mContext;
    public ActivityTopAdjustListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    public void removeAll(){
        for(int i=0;i<getItems().size();i++){
            removeByPosition(i);
        }
    }
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        TopicModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_adjust_top_item, null);

            vh.title=(TextView)itemView.findViewById(R.id.title);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        vh.title.setText(model.getTitle());
        return itemView;
    }
    class ViewHolder {
        TextView title;
    }
}
