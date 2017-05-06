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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareOtherPopUpDialog extends DialogFragment implements View.OnClickListener{
    private static final String APPID = Constant.QQ_LOGIN_FLAG;
    private TextView weixinTv,guimiTv,cancelTextView,qqTv,contactTv;
    private LinearLayout weixinlayout,guimiLayout,qqlayout,contactlayout;
    TieZiDetailModel mod;
    QuanziList quanziList;
    Context context;

    private Tencent mTencent;
    public ShareOtherPopUpDialog(Context context,TieZiDetailModel model){
        mod=model;
        this.context=context;
    }
    public ShareOtherPopUpDialog(Context context,QuanziList model){
        quanziList=model;
        this.context=context;
    }
    @SuppressLint("ValidFragment")
    public ShareOtherPopUpDialog(Context context){
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
        View view = View.inflate(getActivity(), R.layout.activity_bottom_share_other__activity, null);
        regToQQ();
        weixinlayout=(LinearLayout)view.findViewById(R.id.weixinlayout);
        weixinlayout.setOnClickListener(this);
        guimiLayout=(LinearLayout)view.findViewById(R.id.guimiLayout);
        guimiLayout.setOnClickListener(this);
        qqlayout=(LinearLayout)view.findViewById(R.id.qqlayout);
        qqlayout.setOnClickListener(this);
        contactlayout=(LinearLayout)view.findViewById(R.id.contactlayout);
        contactlayout.setOnClickListener(this);


        cancelTextView=(TextView)view.findViewById(R.id.cancelTextView);
        cancelTextView.setOnClickListener(this);
        weixinTv=(TextView)view.findViewById(R.id.weixinTv);
//        weixinTv.setOnClickListener(this);
        guimiTv=(TextView)view.findViewById(R.id.guimiTv);
        guimiTv.setOnClickListener(this);
        qqTv=(TextView)view.findViewById(R.id.qqTv);
        qqTv.setOnClickListener(this);
        contactTv=(TextView)view.findViewById(R.id.contactTv);
        contactTv.setOnClickListener(this);
        dialog.setContentView(view);
        return dialog;
    }
    private class ShareListener implements IUiListener {

        @Override
        public void onCancel() {
            Logger.e("分享取消"+ 0);
        }

        @Override
        public void onComplete(Object arg0) {
            Logger.e("分享成功"+ 0);
        }

        @Override
        public void onError(UiError arg0) {
            Logger.e("分享出错"+ 0);
        }

    }
    private void qq(boolean flag) {
        if (mTencent.isSessionValid() && mTencent.getOpenId() == null) {
            Logger.e("您还未安装QQ"+ 0);
        }
        final Bundle params = new Bundle();
        if(mod!=null){
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "神龟网");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mod.getContent());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constant.PostShareUrl+mod.getId());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://www.baidu.com");
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "神龟网");
            if(flag){
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            }
        }else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, quanziList.getTitle());
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  quanziList.getDesc());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.baidu.com");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, Constant.QuanziInvateUrl+quanziList.getId());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "神龟网");
            if(flag){
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            }
        }

        mTencent.shareToQQ(getActivity(), params, new ShareListener());
    }
    private void regToQQ() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(APPID, getActivity());
    }
    public  interface  OnClickLintener{
        void OnClick(TieZiDetailModel mo);
    }
    OnClickLintener li;
    public void listener( OnClickLintener l){
        li=l;
    }
    @Override
    public void onClick(View view) {
        Logger.e("werwrwerwerw--------"+view.getId());
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
//            case R.id.weixinTv:
//                dismiss();
//                break;
            case R.id.guimiTv:
//                IntentTools.startGuimiList(getActivity(),"您的好友分享了一条供求信息，请查看！"+Constant.PostShareUrl+mod.getId());
                dismiss();
                break;
            case R.id.qqTv:
                qq(false);
                dismiss();
                break;
            case R.id.contactTv:
                IntentTools.startContactList(getActivity(),"test");
                dismiss();
                break;
            case R.id.weixinLayout:
//                weiChat(0);
                Logger.e("eretet----------------");
                if(li!=null){
                        li.OnClick(mod);
                }
                dismiss();
                break;
//        public static void startGuimiList(Context context,String target_type,String target_id,String title,String content){
            case R.id.guimiLayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startGuimiList(getActivity(),"1",mod.getId(),mod.getTopic(),mod.getContent());
                }else{
                    IntentTools.startLogin(getActivity());
                }
                dismiss();
                break;
            case R.id.qqlayout:
                qq(false);
                dismiss();
                break;
            case R.id.contactlayout:
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友分享给您一条帖子信息，请查看！神龟网APP下载地址："+Constant.PostShareUrl+mod.getId());
                dismiss();
                break;
            default:
                Logger.e("eretet------werwr----------");
                if(li!=null){
                    li.OnClick(mod);
                }
                dismiss();
                break;

        }

    }
}
