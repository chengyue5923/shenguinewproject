package com.shengui.app.android.shengui.android.ui.utilsview;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.WindowManager;

import com.shengui.app.android.shengui.utils.im.CommonUtil;


/**
 * Created by fw on 16/11/5.
 */

public class HtmlImgGetter implements Html.ImageGetter {
    protected Activity activity;


    public HtmlImgGetter(Activity activity){
        this.activity = activity;

    }



    @Override
    public Drawable getDrawable(String source) {
        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Drawable drawable = CommonUtil.getImageFromNetwork(source);
//        if(drawable.getIntrinsicWidth()*3>width){
//            drawable.setBounds(0, 0, width, drawable.getIntrinsicHeight() * 3);
//        }else{
//            drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*3, drawable.getIntrinsicHeight() * 3);
//        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()*2, drawable.getIntrinsicHeight() * 2);
        return drawable;
    }
}
