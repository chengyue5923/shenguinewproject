package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.AddressModel;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityAddressListAdapter extends BasePlatAdapter<AddressModel> {
    Context mContext;
    public ActivityAddressListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        AddressModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_address_list_view, null);
            vh.nameTv=(TextView)itemView.findViewById(R.id.nameTv);
            vh.phoneTv=(TextView)itemView.findViewById(R.id.phoneTv);
            vh.addressTv=(TextView)itemView.findViewById(R.id.addressTv);
            vh.UsualaddressTv=(TextView)itemView.findViewById(R.id.UsualaddressTv);
            vh.deleteTv=(TextView)itemView.findViewById(R.id.deleteTv);
            vh.changeTv=(TextView)itemView.findViewById(R.id.changeTv);
            vh.addressSelectIv=(ImageView)itemView.findViewById(R.id.addressSelectIv);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        try{
            vh.nameTv.setText(model.getName());
            vh.phoneTv.setText(model.getMobile());
            vh.addressTv.setText(model.getProvince()+model.getCity()+model.getAddress());
            if(model.getIs_usual()!=null&&model.getIs_usual().equals("1")){
                vh.addressSelectIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quanzi_list_select_view));
                vh.UsualaddressTv.setTextColor(mContext.getResources().getColor(R.color.shenguiappcolor));
            }else{
                vh.addressSelectIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.quanzi_unselect_images));
                vh.UsualaddressTv.setTextColor(mContext.getResources().getColor(R.color.titlecolor));
            }
            vh.deleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.deleteAddress(v,position);
                }
            });
            vh.changeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.changeAddress(v,position);
                }
            });
            vh.UsualaddressTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setDefault(v,position);
                }
            });
        }catch (Exception e){
            Logger.e("exception"+e.getMessage());
        }

        return itemView;
    }
    AddressOnClickListener listener;
    public  void  setOnclickListener(AddressOnClickListener listeners){
        this.listener=listeners;
    }

    public interface  AddressOnClickListener {
        void deleteAddress(View view,int position);
        void changeAddress(View view,int position);
        void setDefault(View view,int position);
    }
    class ViewHolder {
        TextView nameTv, phoneTv,addressTv,UsualaddressTv,deleteTv,changeTv;
        ImageView addressSelectIv;
    }
}
