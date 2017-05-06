package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.CityModel;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityCityAdapter extends BasePlatAdapter<CityModel> {
    Context mContext;
    public ActivityCityAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_city_view, null);
            vh.itemTextivew=(TextView) itemView.findViewById(R.id.itemTextivew);
            vh.deleteIm=(ImageView) itemView.findViewById(R.id.deleteIm);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        final CityModel model = getItem(position);
        if(model.getName().equals("清空")){
            vh.itemTextivew.setTextColor(mContext.getResources().getColor(R.color.white));
            vh.deleteIm.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vh.itemTextivew.setBackground(mContext.getResources().getDrawable(R.drawable.activity_select_item_select));
            }
        }else{
            vh.itemTextivew.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.deleteIm.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                vh.itemTextivew.setBackground(mContext.getResources().getDrawable(R.drawable.activity_border_text));
            }
        }

        vh.itemTextivew.setText(model.getName());

        vh.deleteIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    removeByPosition(position);
                    notifyDataSetChanged();
            }
        });
        vh.itemTextivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        void onclickCountItem(View view, int positon);
    }
    class ViewHolder {
        TextView itemTextivew;
        ImageView deleteIm;
    }
}
