package com.shengui.app.android.shengui.android.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
public class BottomDialog extends DialogFragment implements View.OnClickListener {
    private TransferValue transferValue;
    private String picUrl;
    private int type;

    public BottomDialog() {
    }

    @SuppressLint("ValidFragment")
    public BottomDialog(TransferValue transferValue, String picUrl, int type) {
        this.transferValue = transferValue;
        this.picUrl = picUrl;
        this.type = type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleBottom);
        View view = View.inflate(getActivity(), R.layout.ht_dialog_bottom_popup, null);
        dialog.setContentView(view);
//        TextView textView = (TextView) view.findViewById(R.id.ht_lookUpPicture);
//        if (type == IConstant.PHOTOPICTURE) {
//            textView.setOnClickListener(this);
//        } else {
//            textView.setVisibility(View.GONE);
//        }
        view.findViewById(R.id.ht_dialog_cancel).setOnClickListener(this);
        view.findViewById(R.id.ht_takePhoto).setOnClickListener(this);
        view.findViewById(R.id.ht_album).setOnClickListener(this);
        return dialog;
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
                CommonUtils.albumPhoto(getActivity(), IConstant.ALBUM_PHOTO);
                dismiss();
                break;
            case R.id.ht_takePhoto:
                String picPath = CommonUtils.takePhoto(getActivity(), IConstant.TAKE_PHOTO);
                transferValue.transferValue(picPath);
                dismiss();
                break;
//            case R.id.ht_lookUpPicture:
//                IntentTools.startShowBigImagePage(getActivity(), picUrl);
//                dismiss();
//                break;
        }
    }

    public interface TransferValue {
        void transferValue(String value);
    }

}
