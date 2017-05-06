package com.base.view.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.widget.TextView;

import com.base.view.R;


/**
 * 自定义 progressdialog
 */
public class RunProgressDialog extends Dialog {

    static Animation operatingAnim = null;
    private static RunProgressDialog customProgressDialog = null;
    private Context context = null;

    public RunProgressDialog(Context context) {
        super(context);
        this.context = context;

    }


    public RunProgressDialog(Context context, int theme) {
        super(context, theme);

    }


    public static RunProgressDialog createDialog(Context context) {
        customProgressDialog = new RunProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.view_load_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return customProgressDialog;

    }


    public void onWindowFocusChanged(boolean hasFocus) {


        if (customProgressDialog == null) {
            return;

        }


    }


    /**
     * [Summary]
     * <p/>
     * setTitile 标题
     *
     * @param strTitle
     * @return
     */

    public RunProgressDialog setTitile(String strTitle) {

        return customProgressDialog;

    }


    /**
     * [Summary]
     * <p/>
     * setMessage 提示内容
     *
     * @param strMessage
     * @return
     */

    public RunProgressDialog setMessage(String strMessage) {

        TextView tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);


        if (tvMsg != null) {

            tvMsg.setText(strMessage);

        }


        return customProgressDialog;

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing())
            super.dismiss();
    }


}
