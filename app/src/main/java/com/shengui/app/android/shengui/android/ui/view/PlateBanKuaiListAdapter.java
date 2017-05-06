package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.models.ProductModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class PlateBanKuaiListAdapter extends BasePlatAdapter<ProductModel> {
    Context mContext;
    public PlateBanKuaiListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        ProductModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_bankuai_item, null);

            vh.deleteItenms=(TextView)itemView.findViewById(R.id.deleteItenms);
            vh.images=(TextView)itemView.findViewById(R.id.images);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.deleteItenms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getItems().remove(position);
                notifyDataSetChanged();
                countListener.onclickCountItem(getCount());
            }
        });

        return itemView;
    }
    private CountListener countListener;
    public void setDialogListener(CountListener dialogListener) {
        this.countListener = dialogListener;
    }


    public interface CountListener{
        void onclickCountItem(int flags);
    }
    class ViewHolder {
        TextView images,deleteItenms;
    }
}
