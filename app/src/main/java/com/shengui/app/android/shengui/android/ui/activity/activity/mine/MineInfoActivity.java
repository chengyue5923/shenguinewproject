package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.BottomDialog;
import com.shengui.app.android.shengui.android.ui.dialog.InfoSexDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.constants.IConstant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.tencent.open.LocationApi;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineInfoActivity extends BaseActivity implements View.OnClickListener, BottomDialog.TransferValue ,InfoSexDialog.DialogSexListener{


    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.infoBack)
    TextView infoBack;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.getImage)
    TextView getImage;
    @Bind(R.id.infoImageLayout)
    RelativeLayout infoImageLayout;
    @Bind(R.id.nameTv)
    TextView nameTv;
    @Bind(R.id.nameEt)
    EditText nameEt;
    @Bind(R.id.changeName)
    TextView changeName;
    @Bind(R.id.infoLayout)
    RelativeLayout infoLayout;
    @Bind(R.id.nikiTv)
    TextView nikiTv;
    @Bind(R.id.nikiEt)
    EditText nikiEt;
    @Bind(R.id.changeNiki)
    TextView changeNiki;
    @Bind(R.id.infonmkeLayout)
    RelativeLayout infonmkeLayout;
    @Bind(R.id.sexTv)
    TextView sexTv;
    @Bind(R.id.sexEt)
    TextView sexEt;
    @Bind(R.id.changeSex)
    TextView changeSex;
    @Bind(R.id.sexLayout)
    RelativeLayout sexLayout;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.addressEt)
    TextView addressEt;
    @Bind(R.id.addressLayout)
    RelativeLayout addressLayout;
    @Bind(R.id.signTv)
    TextView signTv;
    @Bind(R.id.signEt)
    EditText signEt;
    @Bind(R.id.signNumber)
    TextView signNumber;
    @Bind(R.id.signLayout)
    RelativeLayout signLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    private String picPath = "";
    InfoSexDialog dialog;
    private static final int cityCode=10190;
    private Dialog loadingdialog;
    LocationModel model=new LocationModel();
    String Avater="";
    int  cityId;
    String cityName;
    int sex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.getImage:
                FragmentManager fm = getSupportFragmentManager();
                BottomDialog bottomDialog = new BottomDialog(this, "", IConstant.PHOTOPICTURE);
                bottomDialog.show(fm, "fragment_dialog");
                break;
            case R.id.sexLayout:
                PopUpDialog();
                break;
            case R.id.addressLayout:
                IntentTools.startCityList(this,cityCode);
                break;
            case R.id.statesText:
//                public void edituserinfo(ViewNetCallBack callBack,String registration_id,String token,String name,String truename,String sex,String avatar,String signature) {
                if(StringTools.isNullOrEmpty(picPath)){
                    ChangeInfo();
                }else{
                    UploadImage(picPath);
                }
                break;
        }
    }
    public void ChangeInfo(String picPath){
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            String  nameEtStr=nameEt.getText().toString();
            String nikiEtStr=nikiEt.getText().toString();
            String StrsignEt=signEt.getText().toString();
            int sex=0;
            if(sexEt.getText().toString().equals("女")){
                sex=0;
            }else{
                sex=1;
            }
            if(nameEtStr.equals("")){
                ToastTool.show("请填写您的昵称");
                return;
            }
            if(nikiEtStr.equals("")){
                ToastTool.show("请填写您的真实名字");
                return;
            }
            MineInfoController.getInstance().edituserinfo(this,UserPreference.getTOKEN(),nameEtStr,nikiEtStr,sex+"",picPath,StrsignEt);
        }else{
            ToastTool.show("您还没有登录");
        }
    }
    public void ChangeInfo(){
        if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
            String  nameEtStr=nameEt.getText().toString();
            String nikiEtStr=nikiEt.getText().toString();
            String StrsignEt=signEt.getText().toString();
            int sex=0;
            if(sexEt.getText().toString().equals("女")){
                sex=0;
            }else{
                sex=1;
            }
            if(nameEtStr.equals("")){
                ToastTool.show("请填写您的昵称");
                return;
            }
            if(nikiEtStr.equals("")){
                ToastTool.show("请填写您的真实名字");
                return;
            }
            MineInfoController.getInstance().edituserinfo(this,UserPreference.getTOKEN(),nameEtStr,nikiEtStr,sex+"",StrsignEt);
        }else{
            ToastTool.show("您还没有登录");
        }
    }
     public void UploadImage(String path) {
            if (!new File(path).exists())
                return;
            Logger.e("photnooa------------"+ path);
            File[] fillist;
            fillist = new File[1];
            fillist[0] = new File(path);
            PushController.getInstance().upLoadFile(new ViewNetCallBack() {
                @Override
                public void onConnectStart() {
                    if (null != loadingdialog && !loadingdialog.isShowing()) {
                        loadingdialog.show();
                    }
                }

                @Override
                public void onConnectEnd() {
                    if (null != loadingdialog && loadingdialog.isShowing()) {
                        loadingdialog.dismiss();
                    }
                }

                @Override
                public void onFail(Exception e) {
                        if (null != loadingdialog && loadingdialog.isShowing()) {
                            loadingdialog.dismiss();
                        }
                }

                @Override
                public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                    Logger.e("上传成功"+result);
                    List<ImageUploadModel> modelist=(List<ImageUploadModel>)result;

                      Logger.e("上传成功路径--------"+modelist.get(0).getUrl_short());
    //                publishConversationPhotoMessage(modelist.get(0).getThumb());
                    ChangeInfo(modelist.get(0).getUrl_short());
                }
            }, fillist, HttpConfig.UploadImage);
        }




    @Override
    protected void initView() {
        ButterKnife.bind(this);
        dialog = new InfoSexDialog(this);
        addressEt.setText(UserPreference.getUsualCityName());
        model.setId(UserPreference.getUsualCityId());
        model.setId(UserPreference.getUsualCityName());
        signEt.setText(UserPreference.getSIGNTURE());
        Glide.with(this).load(UserPreference.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(target);
        nameEt.setText(UserPreference.getName());
        nikiEt.setText(UserPreference.getLoginName());
        if(UserPreference.getSex()==1) {
            sexEt.setText("男");
        }else {
            sexEt.setText("女");
        }
        loadingdialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        if (null != loadingdialog && !loadingdialog.isShowing()) {
//            loadingdialog.show();
//        }
    }

    @Override
    public void onConnectEnd() {
        super.onConnectEnd();
//        if (null != loadingdialog && loadingdialog.isShowing()) {
//            loadingdialog.dismiss();
//        }
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
//        if (null != loadingdialog && loadingdialog.isShowing()) {
//            loadingdialog.dismiss();
//        }
        ToastTool.show("编辑失败");
    }
    //性别选择
    public void PopUpDialog() {   //弹框
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        dialog.show(fragmentTransaction, "InfoSexDialog");
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            personInfoIv.setImageBitmap(bitmap);
        }
    };
    @Override
    protected void initEvent() {
        statesText.setOnClickListener(this);
        dialog.setDialogListener(this);
        signEt.addTextChangedListener(new EditChangedListener());
        cancelTextView.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        getImage.setOnClickListener(this);
        sexLayout.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IConstant.TAKE_PHOTO) {

            Glide.with(this).load(picPath).centerCrop().into(personInfoIv);

        } else if (resultCode == Activity.RESULT_OK && requestCode == IConstant.ALBUM_PHOTO) {
            if (null != data) {
//                Uri photoUri = data.getData();
                Uri     photoUri = geturi(data);//解决方案
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    Logger.e("setPicPath" + picPath);
                    cursor.close();
                }
                Glide.with(this).load(picPath).centerCrop().into(personInfoIv);
            }
        }
        if(resultCode ==1000 && requestCode == cityCode){
            Logger.e("date"+data);
            if(data!=null){
                String cityiD=data.getStringExtra("variety_id");
                String city=data.getStringExtra("variety_name");
                Logger.e("daaaaaaaaaaaa"+city+cityiD);
                UserPreference.setUsualCityName(city);
                UserPreference.setUsualCityId(cityiD);
                model.setId(cityiD);
                model.setName(city);
                addressEt.setText(city);
            }
        }
    }
    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
    @Override
    public void onclickShareItem(String flags) {
        if(flags.equals("男")){
            UserPreference.setSex(1);
        }else{
            UserPreference.setSex(0);
        }
        sexEt.setText(flags);
    }

    class EditChangedListener implements TextWatcher {
        String TAG = "editTextView";
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 26;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "输入文本之前的状态");
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");
            signNumber.setText("还能输入" + (charMaxNum - s.length()) + "字");

        }
        private SimpleTarget target = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                personInfoIv.setImageBitmap(bitmap);
            }
        };
        @Override
        public void afterTextChanged(Editable s) {
//                Log.i(TAG, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            if (s.length() > charMaxNum) {
                ToastTool.show("你输入的字数已经超过了限制");
//                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
            }

        }
    }

    ;

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_info_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if(HttpConfig.edituserinfo.getType()==flag){
            Logger.e("dddddddd"+o.toString());

            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("编辑成功");
                    LoginResultModel model=(LoginResultModel)result;
                    UserPreference.setUser(model);
                    finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("编辑失败");
            }

        }
    }

    @Override
    public void transferValue(String value) {
        Logger.e("dddddddd"+value);
        Avater=value;
        picPath = value;
    }
}
