package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.kdmobi.gui.R;

import java.util.ArrayList;

public class EmojiDetailGridViewAdapter extends BaseAdapter {
    private ArrayList<String> mEmojiList;
    private Context mContext;
    
    public EmojiDetailGridViewAdapter(Context context, ArrayList<String> emojiList) {
        mContext = context;
        mEmojiList = emojiList;
    }
    
    @Override
    public int getCount() {
        return mEmojiList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmojiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.emoji_detail_perview, null);
        }
        bindView(convertView, position);
        return convertView;
    }   
    
    private void bindView(View convertView, int position) {
        ImageView emojiView = (ImageView)convertView.findViewById(R.id.show_per_emoji_view);
        if (position < 20) {
            int resId = mContext.getResources().getIdentifier("emoji_" + getItem(position), "drawable", mContext.getPackageName());
            if (resId != 0) {
                emojiView.setImageResource(resId);
            } else {
                emojiView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.emoji_backgrond_pressed_transparent));
            }
        } else {
            emojiView.setImageResource(Integer.parseInt(getItem(position).toString()));
        }
    }
}
