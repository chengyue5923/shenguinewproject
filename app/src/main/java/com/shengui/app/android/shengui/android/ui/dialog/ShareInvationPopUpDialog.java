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
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.models.QuanziList;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareInvationPopUpDialog extends DialogFragment implements View.OnClickListener{

    private TextView collectionText,sharwTextView,cancelTextView,jubaoText;

    QuanziList model;
    String postId;
    Context context;
    public ShareInvationPopUpDialog(Context context,QuanziList mo,String post){
        model=mo;
        postId =post;
        context=context;
    }
    public ShareInvationPopUpDialog(Context context){
        context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        localLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleBottom);
        View view = View.inflate(getActivity(), R.layout.activity_bottom_share_invation__activity, null);
        cancelTextView=(TextView)view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        dialog.setContentView(view);
        return dialog;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.cancelTextView:
                dismiss();
                break;
        }

    }
}
