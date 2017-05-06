package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.custom.vg.list.CustomAdapter;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.ProductModel;

import java.util.List;

public class MainSexangleAdapter extends CustomAdapter {
	
	private List<ProductModel> list;
	private Context con;
	private LayoutInflater inflater;

	public MainSexangleAdapter(Context context, List<ProductModel> list) {
		this.con = context;
		this.list = list;
		inflater = LayoutInflater.from(con);
	}

	public MainSexangleAdapter(Context context) {
		this.con = context;
		inflater = LayoutInflater.from(con);
	}
	public void setData( List<ProductModel> list){
		this.list = list;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_sexangle_item_style, null);
			vh.tv = (TextView) convertView.findViewById(R.id.adapter_text);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}

		ProductModel str = list.get(position);
		Logger.e("dddfffffffffff"+str.getTitle());
		vh.tv.setText(str.getName());
		return convertView;
	}
	
	public class ViewHolder{
		public TextView tv;
		private ImageView ImageViewRight;
	}

}
