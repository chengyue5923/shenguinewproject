package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityBaseNumberChangeTeamListAdapter extends BasePlatAdapter<AllTypeTorModel> {
    Context mContext;
    ActivityQuanZiNumberChangeListAdapter adapter;
    public ActivityBaseNumberChangeTeamListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        adapter=new ActivityQuanZiNumberChangeListAdapter(mContext);
        final AllTypeTorModel model = getItem(position);
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
        adapter.setDialogListener(new ActivityQuanZiNumberChangeListAdapter.ItemListener() {
            @Override
            public void onItemConfirm(CircleMemberDetail p) {
                Logger.e("poitoin-----"+p);
                ChangeState(p);
                dialogListener.onItemConfirm(p,position);
            }
        });
        return itemView;
    }
    private BaseItemListener dialogListener;
    public void setDialogListener(BaseItemListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void ChangeState(CircleMemberDetail m){
        for(AllTypeTorModel model:getItems()){
            for(CircleMemberDetail ms:model.getModelList()){
                if(ms.getId().equals(m.getId())){
                    ms.setIsflag(true);
                }else{
                    ms.setIsflag(false);
                }
            }
        }
        notifyDataSetChanged();
    }
    public interface BaseItemListener{
        void onItemConfirm(CircleMemberDetail p,int po);
    }
    class ViewHolder {
        TextView viewCharText;
        NoScrollListView confirmlistview;
    }
}
