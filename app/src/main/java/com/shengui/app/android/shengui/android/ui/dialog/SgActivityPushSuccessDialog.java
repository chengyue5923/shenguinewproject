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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class SgActivityPushSuccessDialog extends DialogFragment implements View.OnClickListener {

    private Context context;
    @SuppressLint("ValidFragment")
    public SgActivityPushSuccessDialog(Context context) {
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleSuccess);
        View view = View.inflate(getActivity(), R.layout.activity_push_success_activity, null);
        dialog.setContentView(view);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
