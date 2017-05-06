package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;

import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.PutQuestionsActivity;
import com.shengui.app.android.shengui.utils.im.ImageLoader;

import java.util.List;


public class SGHGridAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater; // 视图容器
    private List<String> bitmaps;
    private int count = 9;

    public SGHGridAdapter(Context context, List<String> bitmaps) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.bitmaps = bitmaps;
    }

    public SGHGridAdapter(Context context, List<String> bitmaps, int count) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.bitmaps = bitmaps;
        this.count = count;
    }

    public void setBitmaps(List<String> bitmaps) {
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (count == bitmaps.size()) ? bitmaps.size() : bitmaps.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null == bitmaps ? null : bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_choose_image_item,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
            holder.delImage = (ImageView) convertView.findViewById(R.id.item_grid_image_del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (!(parent.getChildCount() == position)) {
            holder.image.setImageResource(R.drawable.icon_addpic_unfocused);
            return convertView;
        }
        if (position == bitmaps.size()) {
//			holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_addpic_unfocused));
            holder.image.setImageResource(R.drawable.icon_addpic_unfocused);
//            BaseApplication.getInstance().displayDrawable(holder.image,R.drawable.icon_addpic_unfocused);
            holder.delImage.setVisibility(View.GONE);
            if (position == count) {
                holder.image.setVisibility(View.INVISIBLE);
            }

        } else {
//			if(position == parent.getChildCount()){
            final String bitmapPath = bitmaps.get(position);

//					BaseApplication.getInstance().displayImage(holder.image,Constants.URL_PERFIX+bitmapPath);
            ImageLoader.getInstance(context, R.drawable.default_error).loadLocalImage(bitmapPath, holder.image);
            holder.delImage.setVisibility(View.VISIBLE);
            holder.delImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (context instanceof PutQuestionsActivity) {
                        DialogFacory.getInstance().createAlertDialog(context, "提示", "您确认删除图片吗?", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((PutQuestionsActivity) context).deleteImage(bitmapPath);
                            }
                        },null).show();
                    }
                }
            });


//			}
        }
        return convertView;
    }

    public class ViewHolder {
        public ImageView image, delImage;
    }

}
