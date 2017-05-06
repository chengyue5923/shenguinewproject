package com.base.view.view.dialog;

import android.app.Dialog;

import com.base.platform.utils.android.StaticContext;
import com.base.platform.utils.java.MD5Util;
import com.base.view.view.dialog.factory.DialogFacory;

import java.util.HashMap;

/**
 * 当前dialog的容器
 */
public class CurrentDialogContainer {
    static CurrentDialogContainer instance;

    public CurrentDialogContainer() {
        init();

    }
    private HashMap<String,Dialog> dList;
    public static CurrentDialogContainer getInstance() {
        if (instance==null){
            instance = new CurrentDialogContainer();
        }
        return instance;
    }
    public void clear(){
        dList.clear();
    }
    public void init(){
        dList =  new HashMap<String, Dialog>();
    }



    public Dialog createDialog(String key){
        try {
            dList.clear();
            Dialog dialog= DialogFacory.getInstance().createRunDialog(StaticContext.getInstance().getContext(),"");
            dList.put(MD5Util.string2MD5(key),dialog);
            return dialog;
        }catch (Exception e){
            return null;
        }

    }

    public Dialog getDisMisDialogByKey(String key){
        return dList.get(MD5Util.string2MD5(key));
//        return null;
    }
}
