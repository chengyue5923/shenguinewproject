package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityMineGuiMiListAdapter extends BasePlatAdapter<AllTypeTorModel> {
    Context mContext;
    ActivityGuiMiNumberListAdapter adapter;
    public ActivityMineGuiMiListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        adapter=new ActivityGuiMiNumberListAdapter(mContext);
        AllTypeTorModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_base_quanzi_number_list_item, null);
            vh.confirmlistview=(NoScrollListView)itemView.findViewById(R.id.confirmlistview);
            vh.viewCharText=(TextView)itemView.findViewById(R.id.viewCharText);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.viewCharText.setText(model.getCharac());
        vh.confirmlistview.setAdapter(adapter);
        if(model.getModelList()!=null&&model.getModelList().size()>0){
            adapter.setRes(model.getModelList());
        }
        return itemView;
    }
    class ViewHolder {
        TextView viewCharText;
        NoScrollListView confirmlistview;
    }
}
