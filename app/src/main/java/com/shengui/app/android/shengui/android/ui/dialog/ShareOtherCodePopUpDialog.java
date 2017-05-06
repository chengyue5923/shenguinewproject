package com.shengui.app.android.shengui.android.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareOtherCodePopUpDialog extends DialogFragment implements View.OnClickListener{
    private static final String APPID = Constant.QQ_LOGIN_FLAG;
    private TextView weixinTv,guimiTv,cancelTextView,qqTv,contactTv;
    private LinearLayout weixinlayout,guimiLayout,qqlayout,contactlayout;
    Context context;
    private Tencent mTencent;
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    @SuppressLint("ValidFragment")
    public ShareOtherCodePopUpDialog(Context context){
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
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "龟蜜圈");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "二维码分享");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, UserPreference.getQCODE());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,UserPreference.getQCODE());
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "神龟网");
            if(flag){
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
            }

        mTencent.shareToQQ(getActivity(), params, new ShareListener());
    }
    private void regToQQ() {
        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(APPID, getActivity());
    }

    OnClickListern ll;
    public void setOnListern( OnClickListern l){
        ll=l;
    }
    public interface  OnClickListern{
        void Onlisten();
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.weixinTv:

//                String imagepathw=UserPreference.getQCODE();
//                WXImageObject imageObjectw=new WXImageObject();
//                imageObjectw.setImagePath(imagepathw);
//                WXMediaMessage msgw = new WXMediaMessage(imageObjectw);
//                msgw.title ="二维码分享";
//                msgw.description ="[神龟网]提醒您：好友分享给您一张二维码，请查看！";
//
//                SendMessageToWX.Req reqq = new SendMessageToWX.Req();
//                reqq.transaction = "img"+String.valueOf(System.currentTimeMillis());
//                reqq.message = msgw;
//                reqq.scene = SendMessageToWX.Req.WXSceneSession;
//                api.sendReq(reqq);

                if(ll!=null){
                    ll.Onlisten();
                }
                dismiss();
                break;
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
            case R.id.weixinlayout:

//                //创建一个WXWebPageObject对象，用于封装要发送的Url
//                WXWebpageObject webpage = new WXWebpageObject();
//                webpage.webpageUrl =Constant.QuanziInvateUrl+quanziModel.getId();
//                //创建一个WXMediaMessage对象
//                WXMediaMessage msg = new WXMediaMessage(webpage);
//                msg.title =quanziModel.getTitle();
//                msg.description = quanziModel.getDesc();
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
//                req.message = msg;
//
//                //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
//                req.scene =SendMessageToWX.Req.WXSceneSession;
//
//                api.sendReq(req);

//
//                String imagepath=UserPreference.getQCODE();
//                WXImageObject imageObject=new WXImageObject();
//                imageObject.setImagePath(imagepath);
//                WXMediaMessage msg = new WXMediaMessage(imageObject);
//                msg.title ="二维码分享";
//                msg.description ="[神龟网]提醒您：好友分享给您一张二维码，请查看！";
//
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = "img"+String.valueOf(System.currentTimeMillis());
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession;
//                api.sendReq(req);



                if(ll!=null){
                    ll.Onlisten();
                }
                dismiss();
                break;
//        public static void startGuimiList(Context context,String target_type,String target_id,String title,String content){
            case R.id.guimiLayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startGuimiList(getActivity(),"9","","二维码分享","你的好友分享给你一个二维码");

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
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友分享给您一张二维码，请查看！神龟网APP下载地址："+UserPreference.getQCODE());
                dismiss();
                break;
        }

    }
}
