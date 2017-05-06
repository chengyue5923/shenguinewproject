package com.base.view.view.dialog.factory;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.base.view.view.dialog.RunProgressDialog;


/**
 * 对话框
 */
public class DialogFacory {

    public static DialogFacory getInstance() {
        return SingleClearCach.instance;
    }

    /**
     * 显示 dilog
     */
    public Dialog createRunDialog(Context context, String msg) {

        RunProgressDialog progressDialog = RunProgressDialog.createDialog(context);

        progressDialog.setMessage(msg);

        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }


    static class SingleClearCach {
        static DialogFacory instance = new DialogFacory();
    }

    public Dialog createAlertDialog(Context context, String title, String message, String confirmMessage, DialogInterface.OnClickListener listener,DialogInterface.OnClickListener naListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirmMessage,
                        listener)
                .setNegativeButton("取消",naListener==null?
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                dialog.dismiss();
                            }
                        }:naListener);
        return alert.create();
    }


}
