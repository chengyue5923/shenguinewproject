package com.shengui.app.android.shengui.android.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.view.ActivityTieziListAdapter;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
public class SharePopUpDialog extends DialogFragment implements View.OnClickListener{

    private TextView collectionText,sharwTextView,cancelTextView,jubaoText,zhidingText,settingText,removeText,deleteText,removeDeleteText;

    TieZiDetailModel mo;
    Context context;
    View linetop,linedig,linemove,linedelete,linecollect;
    public SharePopUpDialog(){

    }
    @SuppressLint("ValidFragment")
    public SharePopUpDialog(Context context){
        this.context=context;
        mo=new TieZiDetailModel();
    }
    @SuppressLint("ValidFragment")
    public SharePopUpDialog(Context context, TieZiDetailModel model){
        this.context=context;
        mo=model;
    }
    public void setModel(TieZiDetailModel model){
        Logger.e("mode--------------l"+model.toString());
        mo=model;
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
        View view = View.inflate(getActivity(), R.layout.activity_bottom_share_activity, null);

        linetop=(View)view.findViewById(R.id.linetop);
        linedig=(View)view.findViewById(R.id.linedig);
        linemove=(View)view.findViewById(R.id.linemove);
        linedelete=(View)view.findViewById(R.id.linedelete);

        linecollect=(View)view.findViewById(R.id.linecollect);

        cancelTextView=(TextView)view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);

        removeDeleteText=(TextView)view.findViewById(R.id.removeDeleteText);
        removeDeleteText.setOnClickListener(this);

        collectionText=(TextView)view.findViewById(R.id.collectionText);
        collectionText.setOnClickListener(this);
        if(mo.getIs_favorite().equals("0")){
            collectionText.setText("收藏");
        }else{
            collectionText.setText("取消收藏");
        }
        sharwTextView=(TextView)view.findViewById(R.id.sharwTextView);
        sharwTextView.setOnClickListener(this);
        jubaoText=(TextView)view.findViewById(R.id.jubaoText);
        jubaoText.setOnClickListener(this);
        zhidingText=(TextView)view.findViewById(R.id.zhidingText);
        if(mo.getIs_top().equals("0")){
            zhidingText.setText("置顶");
        }else{
            zhidingText.setText("取消置顶");
        }
        zhidingText.setOnClickListener(this);
        settingText=(TextView)view.findViewById(R.id.settingText);
        if(mo.getIs_digest().equals("0")){
            settingText.setText("设为精华");
        }else{
            settingText.setText("取消设为精华");
        }
        settingText.setOnClickListener(this);
        removeText=(TextView)view.findViewById(R.id.removeText);
        removeText.setOnClickListener(this);

        deleteText=(TextView)view.findViewById(R.id.deleteText);
        deleteText.setOnClickListener(this);

        linetop.setVisibility(View.GONE);
        linedig.setVisibility(View.GONE);
        linemove.setVisibility(View.GONE);
        linedelete.setVisibility(View.GONE);
        linecollect.setVisibility(View.GONE);
        zhidingText.setVisibility(View.GONE);
        settingText.setVisibility(View.GONE);
        removeText.setVisibility(View.GONE);
        deleteText.setVisibility(View.GONE);
        removeDeleteText.setVisibility(View.GONE);
        try{
            Logger.e("uid"+mo.getCircle_detail().getUser_id()+"------------"+UserPreference.getUid());
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1&&mo.getCircle_detail()!=null){
                if(UserPreference.getUid()== Integer.parseInt(mo.getCircle_detail().getUser_id())){
                    removeDeleteText.setVisibility(View.VISIBLE);
                    zhidingText.setVisibility(View.VISIBLE);
                    settingText.setVisibility(View.VISIBLE);
                    removeText.setVisibility(View.VISIBLE);
                    linetop.setVisibility(View.VISIBLE);
                    linedig.setVisibility(View.VISIBLE);
                    linemove.setVisibility(View.VISIBLE);
                    linedelete.setVisibility(View.VISIBLE);
                    linecollect.setVisibility(View.VISIBLE);
                }else{
                    removeDeleteText.setVisibility(View.GONE);
                    zhidingText.setVisibility(View.GONE);
                    settingText.setVisibility(View.GONE);
                    removeText.setVisibility(View.GONE);
                    linetop.setVisibility(View.GONE);
                    linedig.setVisibility(View.GONE);
                    linemove.setVisibility(View.GONE);
                    linedelete.setVisibility(View.GONE);
                    linecollect.setVisibility(View.GONE);
                }
                if(UserPreference.getUid()== Integer.parseInt(mo.getUser_id())){
                    deleteText.setVisibility(View.VISIBLE);
                    linecollect.setVisibility(View.VISIBLE);
                }
            }else {
                linetop.setVisibility(View.GONE);
                linedig.setVisibility(View.GONE);
                linemove.setVisibility(View.GONE);
                linedelete.setVisibility(View.GONE);
                linecollect.setVisibility(View.GONE);
                removeDeleteText.setVisibility(View.GONE);
                deleteText.setVisibility(View.GONE);
                zhidingText.setVisibility(View.GONE);
                settingText.setVisibility(View.GONE);
                removeText.setVisibility(View.GONE);
            }

        }catch (Exception e){
            Logger.e("exception"+e.getMessage());
        }

        dialog.setContentView(view);
        return dialog;
    }
    private DialogShareListener dialogListener;
    public void setDialogListener(DialogShareListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    public interface DialogShareListener{
        void onclickShareItem(int flags,TieZiDetailModel model);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.collectionText:
                dialogListener.onclickShareItem(0,mo);
                dismiss();
                break;
            case R.id.sharwTextView:
                dialogListener.onclickShareItem(1,mo);
                dismiss();
                break;
            case R.id.jubaoText:
                dialogListener.onclickShareItem(2,mo);
                dismiss();
                break;
            case R.id.removeText:
                dialogListener.onclickShareItem(3,mo);
                dismiss();
                break;
            case R.id.settingText:
                dialogListener.onclickShareItem(4,mo);
                dismiss();
                break;
            case R.id.zhidingText:
                dialogListener.onclickShareItem(5,mo);
                dismiss();
                break;
            case R.id.deleteText:  //自己删除
                dialogListener.onclickShareItem(7,mo);
//                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
//
//                    MineInfoController.getInstance().quanzhuDelete(new ViewNetCallBack() {
//                        @Override
//                        public void onConnectStart() {
//
//                        }
//
//                        @Override
//                        public void onConnectEnd() {
//
//                        }
//
//                        @Override
//                        public void onFail(Exception e) {
//
//                        }
//
//                        @Override
//                        public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
//
//                            try {
//                                JSONObject js=new JSONObject(o.toString());
//                                if(js.getBoolean("status")){
//
//                                    ToastTool.show("删除成功");
//                                }else{
//                                    ToastTool.show("删除失败");
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, UserPreference.getTOKEN(), mo.getId());
//                }
                dismiss();
                break;
            case R.id.removeDeleteText:
                dialogListener.onclickShareItem(6,mo);
                dismiss();
                break;
        }

    }
}
