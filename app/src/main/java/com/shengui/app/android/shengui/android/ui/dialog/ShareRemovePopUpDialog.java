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
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareRemovePopUpDialog extends DialogFragment implements View.OnClickListener{

    private TextView collectionText,sharwTextView,cancelTextView,jubaoText;

    TieZiDetailModel model;
    Context context;
    public ShareRemovePopUpDialog(Context context, TieZiDetailModel mo){
        this.context=context;
        model=mo;
    }
    @SuppressLint("ValidFragment")
    public ShareRemovePopUpDialog(Context context){
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
        View view = View.inflate(getActivity(), R.layout.activity_bottom_share_remove_activity, null);
        cancelTextView=(TextView)view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        collectionText=(TextView)view.findViewById(R.id.collectionText);
        collectionText.setOnClickListener(this);
        sharwTextView=(TextView)view.findViewById(R.id.sharwTextView);
        sharwTextView.setOnClickListener(this);
        jubaoText=(TextView)view.findViewById(R.id.jubaoText);
        jubaoText.setOnClickListener(this);

        collectionText.setVisibility(View.GONE);
        sharwTextView.setVisibility(View.GONE);
        jubaoText.setVisibility(View.GONE);

        if(model.getCircle_section()!=null&&model.getCircle_section().size()>0){
            switch (model.getCircle_section().size()){
                case 1:
                    collectionText.setVisibility(View.VISIBLE);
                    collectionText.setText(model.getCircle_section().get(0).getTitle());
                    break;
                case 2:
                    collectionText.setVisibility(View.VISIBLE);
                    collectionText.setText(model.getCircle_section().get(0).getTitle());
                    sharwTextView.setVisibility(View.VISIBLE);
                    sharwTextView.setText(model.getCircle_section().get(1).getTitle());
                    break;
                case 3:
                    collectionText.setVisibility(View.VISIBLE);
                    collectionText.setText(model.getCircle_section().get(0).getTitle());
                    sharwTextView.setVisibility(View.VISIBLE);
                    sharwTextView.setText(model.getCircle_section().get(1).getTitle());
                    jubaoText.setVisibility(View.VISIBLE);
                    jubaoText.setText(model.getCircle_section().get(2).getTitle());
                    break;
            }
        }
        dialog.setContentView(view);
        return dialog;
    }

    public boolean haveToken(){
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            return true;
        }else{
            return false;
        }
    }
    public interface  Orefresh{
        void referesh();
    }
    Orefresh re;
    public void onRerresh( Orefresh r){
        re=r;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.collectionText:
                if(haveToken()){
                    TieZiController.getInstance().MoveToSection(new ViewNetCallBack() {
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

                            try {
                                JSONObject ja=new JSONObject(o.toString());
                                if(ja.getBoolean("status")){
                                    ToastTool.show("移动成功");
                                }else{
                                    ToastTool.show("移动失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },UserPreference.getTOKEN(),model.getId(),model.getCircle_section().get(0).getId());
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
            case R.id.sharwTextView:
                if(haveToken()){
                    TieZiController.getInstance().MoveToSection(new ViewNetCallBack() {
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

                            try {
                                JSONObject ja=new JSONObject(o.toString());
                                if(ja.getBoolean("status")){
                                    if(re!=null){}
                                    ToastTool.show("移动成功");
                                }else{
                                    ToastTool.show("移动失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },UserPreference.getTOKEN(),model.getId(),model.getCircle_section().get(1).getId());
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
            case R.id.jubaoText:
                if(haveToken()){
                    TieZiController.getInstance().MoveToSection(new ViewNetCallBack() {
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

                            try {
                                JSONObject ja=new JSONObject(o.toString());
                                if(ja.getBoolean("status")){
                                    ToastTool.show("移动成功");
                                }else{
                                    ToastTool.show("移动失败");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },UserPreference.getTOKEN(),model.getId(),model.getCircle_section().get(2).getId());
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
        }

    }
}
