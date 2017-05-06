package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PersonBean;

/**
 * Created by wanggenlin on 17-3-28.
 */
public class CityAdapter extends BasePlatAdapter<PersonBean>{
    public CityAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        PersonBean person = getItem(position);
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewholder.tv_tag = (TextView) convertView
                    .findViewById(R.id.tv_lv_item_tag);
            viewholder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_lv_item_name);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
//        try {
        Logger.e("---getFirstPinYin--"+person.getFirstPinYin());
            int  selection = person.getFirstPinYin().charAt(0);
            int positionForSelection = getPositionForSelection(selection);
        Logger.e("---getFirstPinYin-1-"+selection);
        Logger.e("---getFirstPinYin-2-"+positionForSelection);
            if (position == positionForSelection) {
                if (!StringTools.isNullOrEmpty(person.getFirstPinYin())){
                    viewholder.tv_tag.setVisibility(View.VISIBLE);
                    viewholder.tv_tag.setText(person.getFirstPinYin());
                }else {
                    viewholder.tv_tag.setVisibility(View.GONE);
                }
            } else {
                viewholder.tv_tag.setVisibility(View.GONE);

            }
//        }catch (Exception e){
//
//        }

        viewholder.tv_name.setText(person.getName());
        return convertView;
    }

    public int getPositionForSelection(int selection) {
        for (int i = 0; i < getCount(); i++) {
            String Fpinyin = getItem(i).getFirstPinYin();
            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;

    }

    class ViewHolder {
        TextView tv_tag;
        TextView tv_name;
    }
}
