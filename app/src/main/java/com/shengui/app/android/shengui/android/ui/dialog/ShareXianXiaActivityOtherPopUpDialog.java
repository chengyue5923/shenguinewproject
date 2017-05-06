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
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ActivityModel;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


/**
 *
 *
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class ShareXianXiaActivityOtherPopUpDialog extends DialogFragment implements View.OnClickListener{
    private static final String APPID = Constant.QQ_LOGIN_FLAG;
    private TextView weixinTv,guimiTv,cancelTextView,qqTv,contactTv;
    ActivityModel ActivityModel;
    Context context;
    private LinearLayout weixinlayout,guimiLayout,qqlayout,contactlayout;
    private Tencent mTencent;
    public ShareXianXiaActivityOtherPopUpDialog(Context context, ActivityModel model){
        ActivityModel=model;
        this.context=context;
    }
    @SuppressLint("ValidFragment")
    public ShareXianXiaActivityOtherPopUpDialog(Context context){
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
        if(ActivityModel!=null){
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, ActivityModel.getTitle());
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, ActivityModel.getShort_desc());
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  Constant.ActivityShareUrl+ActivityModel.getId());
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ActivityModel.getCover());
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
    public interface  Listener{
        void onListener();
    }
    Listener li;
    public void setOnListener(Listener l){
        li=l;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelTextView:
                dismiss();
                break;
            case R.id.weixinTv:
                if(li!=null){
                    li.onListener();
                }
                dismiss();
                break;
            case R.id.guimiTv:
//                IntentTools.startGuimiList(getActivity(),"您的好友分享了一条活动信息，请查看！"+Constant.ActivityShareUrl+ActivityModel.getId());
                dismiss();
                break;
            case R.id.qqTv:
                qq(false);
                dismiss();
                break;
            case R.id.contactTv:
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友分享给您一条活动信息，请查看！神龟网APP下载地址："+Constant.ActivityShareUrl+ActivityModel.getId());
                dismiss();
                break;
            case R.id.weixinlayout:
                if(li!=null){
                    li.onListener();
                }
                dismiss();
                break;
//        public static void startGuimiList(Context context,String target_type,String target_id,String title,String content){
            case R.id.guimiLayout:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    IntentTools.startGuimiList(getActivity(),"7",ActivityModel.getId(),ActivityModel.getTitle(),ActivityModel.getContent());

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
                IntentTools.startContactList(getActivity(),"[神龟网]提醒您：好友分享给您一条活动信息，请查看！神龟网APP下载地址："+Constant.ActivityShareUrl+ActivityModel.getId());
                dismiss();
                break;
        }

    }
}
