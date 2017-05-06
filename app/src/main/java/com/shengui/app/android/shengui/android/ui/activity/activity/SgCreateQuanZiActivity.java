package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.BottomDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SgCreatePushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.configer.constants.IConstant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/26.
 */

public class SgCreateQuanZiActivity extends BaseActivity implements View.OnClickListener, BottomDialog.TransferValue {
    @Bind(R.id.backImageView)
    ImageView backImageView;
    @Bind(R.id.NameText)
    TextView NameText;
    @Bind(R.id.topLayout)
    TextView topLayout;
    @Bind(R.id.imagename)
    TextView imagename;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.changeImage)
    TextView changeImage;
    @Bind(R.id.QuanZiname)
    TextView QuanZiname;
    @Bind(R.id.changeQuanming)
    TextView changeQuanming;
    @Bind(R.id.Addressname)
    TextView Addressname;
    @Bind(R.id.changeaddress)
    TextView changeaddress;
    @Bind(R.id.typename)
    TextView typename;
    @Bind(R.id.activity_sele)
    TextView activitySele;
    @Bind(R.id.testname)
    TextView testname;
    @Bind(R.id.quanzhuTest)
    TextView quanzhuTest;
    @Bind(R.id.noneTest)
    TextView noneTest;
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.createButton)
    TextView createButton;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.quanzhuImageTest)
    ImageView quanzhuImageTest;
    @Bind(R.id.NoneImageTest)
    ImageView NoneImageTest;
    @Bind(R.id.createName)
    EditText createName;


    private static final int backflage = 121;
    @Bind(R.id.plateOne)
    TextView plateOne;
    @Bind(R.id.plateTwo)
    TextView plateTwo;
    @Bind(R.id.plateThree)
    TextView plateThree;
    @Bind(R.id.addressLocation)
    TextView addressLocation;
    private String picPath = "";
    private String testType = "圈主审核";
    private Dialog dialog;
    String plate = "";


    LocationModel model=new LocationModel();
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImageView:
                finish();
                break;
            case R.id.changeImage:
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(SgCreateQuanZiActivity.this, Manifest.permission.CAMERA)) {
                    //提示用户开户权限
                    String[] perms = {"android.permission.CAMERA"};
                    ActivityCompat.requestPermissions(SgCreateQuanZiActivity.this, perms, 10001);
                    return;
                }
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                        checkSelfPermission(SgCreateQuanZiActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    //提示用户开户权限
                    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                    ActivityCompat.requestPermissions(SgCreateQuanZiActivity.this, perms, 10002);
                    return;
                }
                FragmentManager fm = getSupportFragmentManager();
                BottomDialog bottomDialog = new BottomDialog(this, "", IConstant.PHOTOPICTURE);
                bottomDialog.show(fm, "fragment_dialog");
                break;

            case R.id.quanzhuTest:
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                break;
            case R.id.noneTest:
                testType = "无需审核";
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                break;
            case R.id.quanzhuImageTest:
                testType = "圈主审核";
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                break;
            case R.id.NoneImageTest:
                testType = "无需审核";
                NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
                quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
                break;
            case R.id.createButton:
                String createNamestr = createName.getText().toString();
                String titleEtStr = titleEt.getText().toString();
//                ShareRemovePopUpDialog();
                if (createNamestr.equals("")) {
                    ToastTool.show("请填写圈名");
                    return;
                }
                if (titleEtStr.equals("")) {
                    ToastTool.show("介绍不能为空");
                    return;
                }
                int test = 0;
                if (testType.equals("圈主审核")) {
                    test = 0;
                } else {
                    test = 1;
                }
                if(model.getName()==null&&model.getName().equals("")){
                    ToastTool.show("请选择所在城市");
                    return;
                }
                if (picPath.equals("")) {
                    CreateQuanzi();
                } else {
                    UploadImage(picPath);
                }

                break;
            case R.id.activity_sele:
                IntentTools.startSelectBankuai(this, backflage);
                break;
            case R.id.changeaddress:
                IntentTools.startCityList(this, 1212);
                break;
        }

    }

    private void CreateQuanzi() {

        String createNamestr = createName.getText().toString();
        String titleEtStr = titleEt.getText().toString();
        if (createNamestr.equals("")) {
            ToastTool.show("请填写圈名");
            return;
        }
        if (titleEtStr.equals("")) {
            ToastTool.show("介绍不能为空");
            return;
        }
        int test = 0;
        if (testType.equals("圈主审核")) {
            test = 0;
        } else {
            test = 1;
        }
        if(model.getName()==null&&model.getName().equals("")){
            ToastTool.show("请选择所在城市");
            return;
        }
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            GuiMiController.getInstance().GetlistQuanzi(this, createNamestr, test, plate, picPath, titleEtStr,model.getId(),model.getName());
        } else {
            ToastTool.show("您还没有登录");
        }

    }

    public void UploadImage(String path) {
        Log.e("photnooa------------", path);
        File[] fillist;
        fillist = new File[1];
        fillist[0] = new File(path);
        PushController.getInstance().upLoadFile(this, fillist, HttpConfig.UploadImage);
    }


    //创建
    public void ShareRemovePopUpDialog() {   //弹框
        final SgCreatePushSuccessDialog PopUpDialogs = new SgCreatePushSuccessDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    PopUpDialogs.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == backflage && resultCode == resultCode) {
            if(data!=null){
                String date = data.getStringExtra("modelist");
                Logger.e("dateresult" + date);
                plate = date;
                try {

                    Logger.e("date" + GsonTool.jsonToStringArrayEntity(date));
                    List<String> jsonArray = GsonTool.jsonToStringArrayEntity(date);
                    if (jsonArray.size() > 0) {
                        switch (jsonArray.size()) {
                            case 1:
                                JSONObject ja = new JSONObject(jsonArray.get(0));
                                plateOne.setText(ja.getString("title"));
                                plateOne.setVisibility(View.VISIBLE);
                                plateTwo.setVisibility(View.GONE);
                                plateThree.setVisibility(View.GONE);
                                break;
                            case 2:
                                JSONObject jaas = new JSONObject(jsonArray.get(0));
                                plateOne.setText(jaas.getString("title"));
                                plateOne.setVisibility(View.VISIBLE);
                                JSONObject jams = new JSONObject(jsonArray.get(1));
                                plateTwo.setText(jams.getString("title"));
                                plateTwo.setVisibility(View.VISIBLE);
                                plateThree.setVisibility(View.GONE);
                                break;
                            case 3:
                                JSONObject jaasc = new JSONObject(jsonArray.get(0));
                                plateOne.setText(jaasc.getString("title"));
                                plateOne.setVisibility(View.VISIBLE);
                                JSONObject jamsv = new JSONObject(jsonArray.get(1));
                                plateTwo.setText(jamsv.getString("title"));
                                plateTwo.setVisibility(View.VISIBLE);
                                JSONObject jamsvd = new JSONObject(jsonArray.get(2));
                                plateThree.setText(jamsvd.getString("title"));
                                plateThree.setVisibility(View.VISIBLE);
                                break;
                        }
                    }else{
                        plateOne.setVisibility(View.GONE);
                        plateTwo.setVisibility(View.GONE);
                        plateThree.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IConstant.TAKE_PHOTO) {

            Glide.with(this).load(picPath).centerCrop().into(personInfoIv);

//            UserController.getInstance().changeAvarter(this, picPath);
//            UserController.getInstance().upLoadFile(new upLoadFile(), new File(picPath), HttpConfig.changeAvatar);
        } else if (resultCode == Activity.RESULT_OK && requestCode == IConstant.ALBUM_PHOTO) {
            if (null != data) {
//                Uri photoUri = data.getData();
                Uri photoUri = geturi(data);
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
//                UserController.getInstance().upLoadFile(new upLoadFile(), new File(picPath), HttpConfig.changeAvatar);
            }
        }
        if (requestCode == 1212 && resultCode == 1000) {
            Logger.e("date" + data);
            if (data != null) {
                String cityiD = data.getStringExtra("variety_id");
                String city = data.getStringExtra("variety_name");
                Logger.e("daaaaaaaaaaaa" + city + cityiD);
                addressLocation.setText(city);
                UserPreference.setUsualCityId(cityiD);
                UserPreference.setUsualCityName(city);
                model.setId(cityiD);
                model.setName(city);
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
    protected void initView() {

        ButterKnife.bind(this);
        addressLocation.setText(UserPreference.getUsualCityName());
        quanzhuImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_list_select_view));
        NoneImageTest.setImageDrawable(getResources().getDrawable(R.drawable.quanzi_unselect_images));
    }

    @Override
    protected void initEvent() {
        NoneImageTest.setOnClickListener(this);
        quanzhuImageTest.setOnClickListener(this);
        noneTest.setOnClickListener(this);
        quanzhuTest.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        changeImage.setOnClickListener(this);
        createButton.setOnClickListener(this);
        activitySele.setOnClickListener(this);
        changeaddress.setOnClickListener(this);
    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onConnectEnd() {
        super.onConnectEnd();
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        ToastTool.show("创建失败");
    }

    @Override
    protected void initData() {
        model.setId(UserPreference.getUsualCityId());
        model.setName(UserPreference.getUsualCityName());
        dialog = DialogFacory.getInstance().createRunDialog(this, "请稍后");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_quanzi_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        try {
            if (flag == HttpConfig.UploadImage.getType()) {
                try {

                    JSONObject object = new JSONObject(o.toString());
                    if (!object.getBoolean("status")) {
                        ToastTool.show(object.getString("message"));
                    } else {
                        List<ImageUploadModel> modelist = (List<ImageUploadModel>) result;
                        Log.e("timage----", modelist.get(0).getImg_id());
                        ToastTool.show("图片上传成功");
                        picPath = modelist.get(0).getUrl_short();
                        CreateQuanzi();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (flag == HttpConfig.CreateQuanZi.getType()) {
                try {
                    JSONObject object = new JSONObject(o.toString());
                    if (object.getBoolean("status")) {
                        ToastTool.show("创建成功");
                        int CirclrID = object.getInt("data");
                        Logger.e("daerr" + CirclrID);
                        GuiMiController.getInstance().CiecleContentDetail(this, CirclrID, UserPreference.getUid());
//                        finish();
//                        int CirclrID = object.getInt("data");
//                        Logger.e("daerr" + CirclrID);
//                        IntentTools.startQuanziManage(this, CirclrID);
                    } else {
                        ToastTool.show(object.getString("message"));
                    }

                } catch (Exception e) {
                    ToastTool.show("创建失败");
                }
            }
            if (flag == HttpConfig.CircleDetail.getType()) {
                Logger.e("logger" + result);
                QuanziList QuanZiModel = (QuanziList) result;
                IntentTools.startQuanziDetailSelf(this, QuanZiModel);
            }
        } catch (Exception e) {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
            }
            Logger.e("exception" + e.getMessage());
        }

    }

    @Override
    public void transferValue(String value) {
        picPath = value;
    }


}
