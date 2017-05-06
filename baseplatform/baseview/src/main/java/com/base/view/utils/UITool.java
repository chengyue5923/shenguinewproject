package com.base.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class UITool {
    /**
     * 从viewGroup中获取 view
     *
     * @param mContext
     * @param res
     * @param parent
     * @return
     */
    public static View getView(Context mContext, int res, ViewGroup parent) {
        View contentView = LayoutInflater.from(mContext).inflate(res, parent);
        return contentView;
    }


    /**
     * 控制组件的显示和隐藏
     *
     * @param view   组件
     * @param isShow 是否显示
     */
    public static void setViewVisiable(View view, boolean isShow) {
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    /**
     * 判断listview 是不是空的
     * @param adapter
     * @return
     */
    public static boolean checkListViewIsNull(BaseAdapter adapter){
        if (adapter==null){
            return true;
        }
        return adapter.getCount()==0;
    }

    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static <T extends View> T  r2v(View view, int resId) {
        return (T) view.findViewById(resId);
    }




    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



}
