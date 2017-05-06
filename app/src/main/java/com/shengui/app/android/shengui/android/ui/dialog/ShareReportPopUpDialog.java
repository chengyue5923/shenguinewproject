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

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.Serializable;
import java.util.Map;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareReportPopUpDialog extends DialogFragment implements View.OnClickListener,ViewNetCallBack{

    private TextView collectionText,sharwTextView,cancelTextView,jubaoText;
    Context context;
    QuanziList model;
    String postId;
    public ShareReportPopUpDialog(Context context,QuanziList mo,String post){
        model=mo;
        postId =post;
        this.context=context;
    }
    @SuppressLint("ValidFragment")
    public ShareReportPopUpDialog(Context context){
        this.context=context;
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
        View view = View.inflate(getActivity(), R.layout.activity_bottom_share_report_activity, null);
        cancelTextView=(TextView)view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        collectionText=(TextView)view.findViewById(R.id.collectionText);
        collectionText.setOnClickListener(this);
        sharwTextView=(TextView)view.findViewById(R.id.sharwTextView);
        sharwTextView.setOnClickListener(this);
        jubaoText=(TextView)view.findViewById(R.id.jubaoText);
        jubaoText.setOnClickListener(this);
        dialog.setContentView(view);
        return dialog;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.collectionText:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(!postId.equals("")){
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),collectionText.getText().toString(),postId,"1");
                    }else{
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),collectionText.getText().toString(),model.getId(),"2");
                    }
                    ToastTool.show("举报成功");
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
            case R.id.sharwTextView:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(!postId.equals("")){
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),sharwTextView.getText().toString(),postId,"1");
                    }else{
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),sharwTextView.getText().toString(),model.getId(),"2");
                    }
                    ToastTool.show("举报成功");
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
            case R.id.jubaoText:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(!postId.equals("")){
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),jubaoText.getText().toString(),postId,"1");

                    }else{
                        MineInfoController.getInstance().expose_post(ShareReportPopUpDialog.this,UserPreference.getTOKEN(),jubaoText.getText().toString(),model.getId(),"2");

                    }
                    ToastTool.show("举报成功");
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {

    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }
}
