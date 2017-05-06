package com.base.platform.utils.android;

import android.content.Context;

/**
 * Created by Administrator on 2015/4/2.
 */
public class StaticContext {

    static StaticContext instance;

    public StaticContext() {

    }

    public static StaticContext getInstance() {
        if (instance==null){
            instance = new StaticContext();
        }
        return instance;
    }

    private  Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public  void setCurrentContext(Context con){
            context = con;
    }













}
