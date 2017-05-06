package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.video.MainActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityBaseNumberTeamListAdapter extends BasePlatAdapter<AllTypeTorModel> {
    Context mContext;
    ActivityQuanZiNumberAddMoreListAdapter adapter;
    QuanziList CirCleModel;
    public ActivityBaseNumberTeamListAdapter(Context context,QuanziList model) {
        super(context);
        mContext=context;
        CirCleModel=model;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        adapter=new ActivityQuanZiNumberAddMoreListAdapter(mContext, CirCleModel);
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
        adapter.setRes(model.getModelList());
        return itemView;
    }
    class ViewHolder {
        TextView viewCharText;
        NoScrollListView confirmlistview;
    }
}
