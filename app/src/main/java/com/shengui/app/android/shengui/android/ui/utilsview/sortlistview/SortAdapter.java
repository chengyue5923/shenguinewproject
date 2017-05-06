package com.shengui.app.android.shengui.android.ui.utilsview.sortlistview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kdmobi.gui.R;

public class SortAdapter extends BaseAdapter {
	private Context context;
	private List<PersonBean> persons;
	private LayoutInflater inflater;

	public SortAdapter(Context context, List<PersonBean> persons) {
		this.context = context;
		this.persons = persons;
		this.inflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewholder = null;
		PersonBean person = persons.get(position);
		if (convertView == null) {
			viewholder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item, null);
			viewholder.tv_tag = (TextView) convertView
					.findViewById(R.id.tv_lv_item_tag);
			viewholder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_lv_item_name);
			convertView.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) convertView.getTag();
		}
		int selection = person.getFirstPinYin().charAt(0);
		int positionForSelection = getPositionForSelection(selection);
		if (position == positionForSelection) {
			viewholder.tv_tag.setVisibility(View.VISIBLE);
			viewholder.tv_tag.setText(person.getFirstPinYin());
		} else {
			viewholder.tv_tag.setVisibility(View.GONE);

		}
		viewholder.tv_name.setText(person.getName());
		return convertView;
	}

	public int getPositionForSelection(int selection) {
		for (int i = 0; i < persons.size(); i++) {
			String Fpinyin = persons.get(i).getFirstPinYin();
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
