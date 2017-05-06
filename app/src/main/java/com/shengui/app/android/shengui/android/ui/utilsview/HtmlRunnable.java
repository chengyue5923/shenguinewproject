package com.shengui.app.android.shengui.android.ui.utilsview;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;


/**
 * Created by fw on 16/11/5.
 */

public class HtmlRunnable implements Runnable {

    private Handler handler;
    private String str;
    protected Activity activity;
    private int textViewId;
    public HtmlRunnable(Handler handler, String str, Activity activity, int textViewId){
        this.str = str;
        this.handler = handler;
        this.activity = activity;
        message = Message.obtain();
        this.textViewId = textViewId;
    }

    protected Message message;
    @Override
    public void run() {

        Spanned spanned = Html.fromHtml(str, new HtmlImgGetter(activity), null);
        message.what = HtmlHandler.UPDATE_TEXTVIEW;
        message.arg1 = textViewId;
        message.obj = spanned;
        handler.sendMessage(message);
    }
}
