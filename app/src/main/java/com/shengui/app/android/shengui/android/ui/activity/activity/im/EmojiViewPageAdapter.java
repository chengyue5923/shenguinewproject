package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.kdmobi.gui.R;

import java.util.ArrayList;

public class EmojiViewPageAdapter extends PagerAdapter {
    static final String TAG = EmojiViewPageAdapter.class.getSimpleName();
    private static final int PER_PAGE_ROW_NUMBER = 3;
    private ArrayList<ArrayList<String>> mEmojiList;
    private Context mContext;
    private int mPerRowEmojiNumber;
    private EmojiSelectedDelegate mDelegate;
    public EmojiViewPageAdapter(Context context, ArrayList<String> emojiList, int perRowEmojiNumber) {
        mContext = context;
        mPerRowEmojiNumber = perRowEmojiNumber;
        initEmojiList(emojiList);
    }

    public void setEmojiSelectDelegate(EmojiSelectedDelegate delegate) {
        mDelegate = delegate;
    }

    private void initEmojiList(ArrayList<String> emojiList) {
        mEmojiList = new ArrayList<ArrayList<String>>();
        ArrayList<String> childList = null;
        for (int i = 0; i < emojiList.size(); i++) {
            if (childList == null) {
                childList = new ArrayList<String>();
            } else if (childList.size() == mPerRowEmojiNumber * PER_PAGE_ROW_NUMBER - 1) {
                //child list add delete id
                childList.add(String.valueOf(R.drawable.emoji_delete_button));
                mEmojiList.add(childList);
                childList = new ArrayList<String>();
            }
            childList.add(emojiList.get(i));
        }

        for (int i = childList.size(); i < mPerRowEmojiNumber * PER_PAGE_ROW_NUMBER - 1; i++) {
            childList.add(String.valueOf(-1));
        }
        childList.add(String.valueOf(R.drawable.emoji_delete_button));
        mEmojiList.add(childList);
    }

    public int getPageViewSize() {
        return mEmojiList.size();
    }

    @Override
    public int getCount() {
        return mEmojiList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (View) arg1;
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.emoji_detail_grid_view, null);
        GroupGridView gridView = (GroupGridView) convertView.findViewById(R.id.show_detail_emoji_grid_view);
        gridView.setNumColumns(mPerRowEmojiNumber);
        final EmojiDetailGridViewAdapter adapter = new EmojiDetailGridViewAdapter(mContext, mEmojiList.get(arg1));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (mDelegate != null) {
                    if (arg2 >= mPerRowEmojiNumber * PER_PAGE_ROW_NUMBER - 1) {
                        mDelegate.clickPerEmoji(adapter.getItem(arg2).toString(), true);
                    } else {
                        mDelegate.clickPerEmoji(adapter.getItem(arg2).toString(), false);
                    }
                }
            }
        });

        ((ViewPager) arg0).addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    public interface EmojiSelectedDelegate {
        void clickPerEmoji(String emoji, boolean isDeleteButton);
    }

}
