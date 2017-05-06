package com.shengui.app.android.shengui.android.ui.utilsview;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Spanned;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by fw on 16/11/5.
 */

public class HtmlHandler extends Handler {
    public static final int UPDATE_TEXTVIEW = 1;


    WeakReference<Activity> mActivityReference;



    public HtmlHandler(Activity activity) {
        mActivityReference= new WeakReference<Activity>(activity);
    }

    public static  HtmlHandler getInstance(Activity activity){
        return new HtmlHandler(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        final Activity activity = mActivityReference.get();
        if (activity != null) {
            if(msg.what == UPDATE_TEXTVIEW){
                TextView textView = (TextView)activity.findViewById(msg.arg1);
                textView.setText((Spanned)msg.obj);
            }
        }
    }

}
