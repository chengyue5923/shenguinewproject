package com.shengui.app.android.shengui.android.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.CommonUtils;
import com.shengui.app.android.shengui.configer.constants.IConstant;


/**
 * Created by lenovo on 2015/8/5.
 */
@SuppressLint("ValidFragment")
public class BottomSaveDialog extends DialogFragment implements View.OnClickListener {
    public BottomSaveDialog() {
    }
    public Context context;
    public BottomSaveDialog(Context xc) {
        context=xc;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleBottom);
        View view = View.inflate(getActivity(), R.layout.ht_dialog_bottom_save_popup, null);
        dialog.setContentView(view);
        view.findViewById(R.id.ht_album).setOnClickListener(this);
        view.findViewById(R.id.ht_dialog_cancel).setOnClickListener(this);
        return dialog;
    }

    public interface  SaveOnClick{
        void onCLick();
    }
    private SaveOnClick click;
    public void setOnBottmeOnClick(SaveOnClick c){
        click=c;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ht_dialog_cancel:
                dismiss();
                break;
            case R.id.ht_album:
                if(click!=null){
                    click.onCLick();
                }
                dismiss();
                break;
        }
    }
}
