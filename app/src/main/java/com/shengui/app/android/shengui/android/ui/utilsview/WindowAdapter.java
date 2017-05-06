package com.shengui.app.android.shengui.android.ui.utilsview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by g on 2016/10/21.
 */

public class WindowAdapter {
    /**
     * 根据图片比例设置控件的宽高，避免图片拉伸
     * @param context
     * @param view 需要设置的控件
     * @param w  图片的宽
     * @param h  图片的高
     */
    public static void setHeight(Context context, View view,int w,int h){
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width=widthPixels;
        params.height=widthPixels*h/w;
    }
}
