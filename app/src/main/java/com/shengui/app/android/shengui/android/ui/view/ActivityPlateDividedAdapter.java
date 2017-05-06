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
public class ActivityPlateDividedAdapter extends BasePlatAdapter<String> {
    Context mContext;
    public ActivityPlateDividedAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_divided_view, null);
            vh.itemTextivew=(TextView) itemView.findViewById(R.id.itemTextivew);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        final String model = getItem(position);
        vh.itemTextivew.setText(model);
        vh.itemTextivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                model.setFlags(!model.isFlags());
//                if(model.isFlags()){
//                    vh.itemTextivew.setTextColor(mContext.getResources().getColor(R.color.shenguiappcolor));
//                    vh.itemTextivew.setBackgroundResource(R.drawable.activity_plate_divided_select_item);
//                }else{
//                    vh.itemTextivew.setTextColor(mContext.getResources().getColor(R.color.platedivietextitem));
//                    vh.itemTextivew.setBackgroundResource(R.drawable.activity_plate_divided_unselect_item);
//                }
                countListener.onclickCountItem(v,position);
            }
        });
        return itemView;
    }
    private GridListener countListener;
    public void setDialogListener(GridListener dialogListener) {
        this.countListener = dialogListener;
    }


    public interface GridListener{
        void onclickCountItem(View view,int positon );
    }
    class ViewHolder {
        TextView itemTextivew;
    }
}
