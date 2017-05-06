package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.android.ui.utilsview.GridAdapter;
import com.shengui.app.android.shengui.android.ui.utilsview.MultiImageSelectorActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollGridView;
import com.shengui.app.android.shengui.android.ui.utilsview.PictureUtil;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/8/9.
 */
public class SgPushTieziDetailActivity extends BaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {

    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.pushTextView)
    TextView pushTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.mGridView)
    NoScrollGridView mGridView;
    @Bind(R.id.imageCount)
    TextView imageCount;
    @Bind(R.id.quanzilayout)
    RelativeLayout quanzilayout;
    @Bind(R.id.faceimage)
    ImageView faceimage;
    //    @Bind(R.id.scrollView)
//    ScrollView scrollView;

    @Bind(R.id.inputlayout)
    RelativeLayout inputlayout;
    @Bind(R.id.topicText)
    TextView topicText;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.rootlayout)
    RelativeLayout rootlayout;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.textViewName)
    TextView textViewName;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    private GridAdapter gridAdapter;
    public static ArrayList<String> bmp;
    private String type = "";
    private Dialog dialog;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private String imageUrl = "";
    private int CircleId;
    private String titleString = "";
    private String titleid = "";
    private static final int circleCode = 10002;

    LocationModel model = new LocationModel();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if(!UserPreference.getTOPICID().equals("")){
            CircleId =Integer.parseInt(UserPreference.getTOPICID());
        }
//        textViewName.setText(UserPreference.getTOPICCONTENT());
        model.setId(UserPreference.getCityID());
        model.setName(UserPreference.getCityName());
        addressTv.setText(UserPreference.getCityName());
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        if(getIntent().getSerializableExtra("topic")!=null){
            topicText.setText((String)getIntent().getSerializableExtra("topic"));
            titleid=(String)getIntent().getSerializableExtra("topicid");
            Logger.e("topicId"+(String)getIntent().getSerializableExtra("topic")+titleid);
        }
        if(getIntent().getSerializableExtra("circletitle")!=null){
            String ciecleTitle=(String)getIntent().getSerializableExtra("circletitle");
            String ciecleid=(String)getIntent().getSerializableExtra("circleid");
            textViewName.setText(ciecleTitle);
            CircleId=Integer.parseInt(ciecleid);
        }
    }

    @Override
    protected void initEvent() {
//        addressTv.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == bmp.size()) {
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(SgPushTieziDetailActivity.this, Manifest.permission.CAMERA)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.CAMERA"};
                        ActivityCompat.requestPermissions(SgPushTieziDetailActivity.this, perms, 10001);
                        return;
                    }
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                            checkSelfPermission(SgPushTieziDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(SgPushTieziDetailActivity.this, perms, 10002);
                        return;
                    }
                    IntentTools.openImageChooseActivity(SgPushTieziDetailActivity.this, bmp);
                }
            }
        });
        inputlayout.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        pushTextView.setOnClickListener(this);
        quanzilayout.setOnClickListener(this);
    }

    public void deleteImage(String path) {
        if (bmp.contains(path)) bmp.remove(path);
        imageCount.setText(bmp.size() + "/9");
        gridAdapter.setBitmaps(bmp);
    }

    @Override
    protected void initData() {
//        titleid = UserPreference.getTOPICID();
//        topicText.setText(UserPreference.getTOPICCONTENT());
        type = getIntent().getStringExtra("type");
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        bmp = new ArrayList<String>();
        gridAdapter = new GridAdapter(this, bmp, 9);
        mGridView.setAdapter(gridAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback_detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1015 && resultCode == 1000) {
            Logger.e("date" + data);
            if (data != null) {
                String cityiD = data.getStringExtra("variety_id");
                String city = data.getStringExtra("variety_name");
                Logger.e("daaaaaaaaaaaa" + city + cityiD);
                addressTv.setText(city);
                UserPreference.setUsualCityId(cityiD);
                UserPreference.setUsualCityName(city);
                model.setId(cityiD);
                model.setName(city);
            }
        }
        if (requestCode == 1212) {
            if (resultCode == 1010) {
                Log.e("mods-------------------", data.getStringExtra("topic") + data.getStringExtra("result"));
                titleString = data.getStringExtra("topic");
                titleid = data.getStringExtra("result");
                topicText.setText(titleString);
            }
        }
        if (requestCode == circleCode) {
            if (resultCode == 1010) {
                Log.e("modelsssss", data.getStringExtra("modelist"));
                CircleId = Integer.parseInt((String) data.getStringExtra("modelist"));
                textViewName.setText((String) data.getStringExtra("sname"));

            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MultiImageSelectorActivity.REQUEST_CODE:
                    List<String> selectImages = data.getStringArrayListExtra("select_result");
                    bmp.clear();
                    bmp.addAll(selectImages);
                    imageCount.setText(bmp.size() + "/9张");
                    gridAdapter.setBitmaps(bmp);
                    break;

            }
        }
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.UploadImage.getType()) {
            try {
//
                JSONObject object = new JSONObject(o.toString());
                if (!object.getBoolean("status")) {
                    ToastTool.show(object.getString("message"));
                } else {
                    List<ImageUploadModel> modelist = (List<ImageUploadModel>) result;
                    for (ImageUploadModel m : modelist) {
                        Logger.e("model" + m.getUrl());
                        imageUrl = imageUrl + m.getImg_id() + ",";
                    }
                    imageUrl.substring(0, imageUrl.length() - 1);
                    ToastTool.show("图片上传成功");
                    PushTieZi();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (flag == HttpConfig.pushTie.getType()) {
            try {
//
                JSONObject object = new JSONObject(o.toString());
                Log.e("dffdf", object.toString());
                if (!object.getBoolean("status")) {
                    ToastTool.show(object.getString("message"));
                } else {
                    PopUpDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


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

    private void PushTieZi() {
        String strtitleEt = titleEt.getText().toString();
        if (strtitleEt.equals("")) {
            ToastTool.show("请填写帖子详情");
            return;
        }
        if (textViewName.getText().equals("")) {
            ToastTool.show("请选择圈子");
            return;
        }
//        if (model.getName().equals("")) {
//            ToastTool.show("请选择圈子");
//            return;
//        }
        PushController.getInstance().PushTieZi(this, CircleId, strtitleEt, imageUrl, "0", "", titleid, model.getId(), model.getName());
    }


    //发布成功弹窗
    public void PopUpDialog() {   //弹框
        UserPreference.setISFINISHPOSR("112");
        final SgActivityPushSuccessDialog PopUpDialogs = new SgActivityPushSuccessDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
//        PopUpDialogs.dismiss();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    PopUpDialogs.dismiss();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.pushTextView:
//                PopUpDialog();
                if (bmp.size() == 0) {
                    //没有图片
                    ToastTool.show("请选择图片");
                    break;
                }
                String strtitleEt = titleEt.getText().toString();
                if (strtitleEt.equals("")) {
                    ToastTool.show("请填写帖子详情");
                    break;
                }
                if (textViewName.getText().equals("")) {
                    ToastTool.show("请选择圈子");
                    break;
                }
                else {
//                    File[] fillist;
//                    fillist = new File[bmp.size()];
//                    for (int i = 0; i < bmp.size(); i++) {
//                        fillist[i] = new File(bmp.get(i));
//                    }
//                    PushController.getInstance().upLoadFile(this, fillist, HttpConfig.UploadImage);
                    new ImageBitmapAsy(bmp).execute();
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.quanzilayout:
                IntentTools.startquanzilist(this, circleCode);
                break;
            case R.id.inputlayout:
                IntentTools.startCreateTop(this, 1212);
                break;
            case R.id.addressTv:
                IntentTools.startCityList(this, 1015);
                break;
        }

    }

    public class ImageBitmapAsy extends AsyncTask {
        private File[] fill;
        private List<String> Stringlist;
        public ImageBitmapAsy(List<String> string){
            Stringlist=string;
            fill = new File[bmp.size()];
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (null != dialog && !dialog.isShowing()) {
                Logger.e("erer---onPreExecute--");
                dialog.show();
            }
        }

        @Override
        protected File[] doInBackground(Object[] params) {
            for(int i=0;i<Stringlist.size();i++){
                Logger.e("erer-Stringlist.size()----"+Stringlist.size());
//                updatelist.add(PictureUtil.bitmapToString(Stringlist.get(i)));
                fill[i] = PictureUtil.bitmapToStringFile(Stringlist.get(i));
            }
            return fill;
        }
        @Override
        protected void onPostExecute(Object o) {
            Logger.e("erer---onPostExecute--");
            PushController.getInstance().upLoadFile(SgPushTieziDetailActivity.this, fill, HttpConfig.UploadImage);
            super.onPostExecute(o);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        rootlayout.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            inputlayout.setVisibility(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            inputlayout.setVisibility(View.GONE);
        }
    }

}
