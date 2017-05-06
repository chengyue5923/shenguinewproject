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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareInvatiFriendsOtherPopUpDialog extends DialogFragment implements View.OnClickListener{
    private static final String APPID = Constant.QQ_LOGIN_FLAG;
    private TextView weixinTv,guimiTv,cancelTextView,qqTv,contactTv;
    QuanziList quanziModel;
    Context context;
    private LinearLayout weixinlayout,guimiLayout,qqlayout,contactlayout;
    private Tencent mTencent;
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    public ShareInvatiFriendsOtherPopUpDialog(Context context, QuanziList model){
        quanziModel=model;
        this.context=context;
    }
    @SuppressLint("ValidFragment")
    public ShareInvatiFriendsOtherPopUpDialog(Context context){
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
        weixinTv.setOnClickListener(this);
        guimiTv=(TextView)view.findViewById(R.id.guimiTv);
        guimiTv.setOnClickListener(this);
        qqTv=(TextView)view.findViewById(R.id.qqTv);
        qqTv.setOnClickListener(this);
        contactTv=(TextView)view.findViewById(R.id.contactTv);
        contactTv.setOnClickListener(this);
        dialog.setContentView(view);
        regToWx();
        return dialog;
    }
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(context, W_APPID, true);
        api.registerApp(W_APPID);
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
        if(quanziModel!=null){
            Logger.e("ert"+Constant.QuanziInvateUrl+quanziModel.getId());
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE,quanziModel.getTitle());
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, quanziModel.getDesc());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, Constant.QuanziInvateUrl+quanziModel.getId());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,quanziModel.getAvatar());
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.weixinTv:
                dismiss();
                break;
            case R.id.guimiTv:
//                IntentTools.startGuimiList(getActivity(),"您的好友邀请您加入圈子\""+quanziModel.getTitle()+"\",点击查看圈子详情"+Constant.QuanziInvateUrl+quanziModel.getId());
                dismiss();
                break;
            case R.id.qqTv:
                qq(false);
                dismiss();
                break;
            case R.id.contactTv:
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友邀请您加入他们在神龟网的圈子！神龟网APP下载地址下载地址："+Constant.QuanziInvateUrl+quanziModel.getId());
                dismiss();
                break;
            case R.id.weixinLayout:
                if (!api.isWXAppInstalled()) {
                    ToastTool.show("您还未安装微信");
                    return;
                }

                //创建一个WXWebPageObject对象，用于封装要发送的Url
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl =Constant.QuanziInvateUrl+quanziModel.getId();
                //创建一个WXMediaMessage对象
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title =quanziModel.getTitle();
                msg.description = "[神龟网]提醒您：好友邀请您加入他们在神龟网的圈子";

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
                req.message = msg;

                //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
                req.scene =SendMessageToWX.Req.WXSceneSession;

                api.sendReq(req);
                dismiss();
                break;
            case R.id.guimiLayout:
//                public static void startGuimiList(Context context,String target_type,String target_id,String title,String content){
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startGuimiList(getActivity(),"3",quanziModel.getId(),quanziModel.getTitle(),quanziModel.getDesc());

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
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友邀请您加入他们在神龟网的圈子！神龟网APP下载地址下载地址："+Constant.QuanziInvateUrl+quanziModel.getId());
                dismiss();
                break;
            default:
                if (!api.isWXAppInstalled()) {
                    ToastTool.show("您还未安装微信");
                    return;
                }

                //创建一个WXWebPageObject对象，用于封装要发送的Url
                WXWebpageObject webpagewe = new WXWebpageObject();
                webpagewe.webpageUrl =Constant.QuanziInvateUrl+quanziModel.getId();
                //创建一个WXMediaMessage对象
                WXMediaMessage msgw = new WXMediaMessage(webpagewe);
                msgw.title =quanziModel.getTitle();
                msgw.description = "[神龟网]提醒您：好友邀请您加入他们在神龟网的圈子";

                SendMessageToWX.Req reqw = new SendMessageToWX.Req();
                reqw.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
                reqw.message = msgw;

                //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
                reqw.scene =SendMessageToWX.Req.WXSceneSession;

                api.sendReq(reqw);
                dismiss();
                break;
        }

    }
}
