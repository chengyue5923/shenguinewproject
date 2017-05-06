package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.EditTextMultiLine;
import com.shengui.app.android.shengui.android.ui.utilsview.GridAdapter;
import com.shengui.app.android.shengui.android.ui.utilsview.MultiImageSelectorActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.PictureUtil;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
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
public class SgPushGongQiuDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.mGridView)
    GridView mGridView;
    @Bind(R.id.imageCount)
    TextView imageCount;
    @Bind(R.id.feedbackTextView)
    TextView feedbackTextView;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.pushTextView)
    TextView pushTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.typeSelectLayout)
    RelativeLayout typeSelectLayout;
    @Bind(R.id.sellTextView)
    TextView sellTextView;
    @Bind(R.id.BuyTextView)
    TextView BuyTextView;
    @Bind(R.id.titleTextVisew)
    EditText titleTextVisew;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.selectImageTv)
    TextView selectImageTv;
    private GridAdapter gridAdapter;
    public static ArrayList<String> bmp;
    private String type = "";
    private Dialog dialog;
    private final static int typeSelect = 1001;
    private String variety_id = "";  //圈子
    private int Selletype = 1;   //供求
    private String imageUrl = "";

    LocationModel model = new LocationModel();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        model.setId(UserPreference.getCityID());
        model.setName(UserPreference.getCityName());
        addressTv.setText(UserPreference.getCityName());
    }

    @Override
    protected void initEvent() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bmp.size()) {
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(SgPushGongQiuDetailActivity.this, Manifest.permission.CAMERA)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.CAMERA"};
                        ActivityCompat.requestPermissions(SgPushGongQiuDetailActivity.this, perms, 10001);
                        return;
                    }
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                            checkSelfPermission(SgPushGongQiuDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(SgPushGongQiuDetailActivity.this, perms, 10002);
                        return;
                    }
                    IntentTools.openImageChooseGongqiuActivity(SgPushGongQiuDetailActivity.this, bmp);
                }
            }
        });
//        addressTv.setOnClickListener(this);
        feedbackTextView.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        pushTextView.setOnClickListener(this);
        sellTextView.setOnClickListener(this);
        BuyTextView.setOnClickListener(this);
        typeSelectLayout.setOnClickListener(this);
    }

    public void deleteImage(String path) {
        if (bmp.contains(path))
            bmp.remove(path);
        imageCount.setText(bmp.size() + "/9");
        gridAdapter.setBitmaps(bmp);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        bmp = new ArrayList<String>();
        gridAdapter = new GridAdapter(this, bmp, 9);
        mGridView.setAdapter(gridAdapter);
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
    protected int getLayoutId() {
        return R.layout.activity_feedback_gongqiu_detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1010 && resultCode == 1000) {
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
        if (resultCode == 1000) {
            if (requestCode == typeSelect) {
                Logger.e("date" + data.getSerializableExtra("variety_id"));
                variety_id = (String) data.getSerializableExtra("variety_id");
                selectImageTv.setText((String) data.getSerializableExtra("variety_type"));
            }
        }
    }


    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.pushGongQiu.getType()) {
            try {
                Logger.e("date" + result + "-----" + o);
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    PopUpDialog();
                } else {
                    ToastTool.show(object.getString("message"));
                }

            } catch (Exception e) {
                ToastTool.show("提交失败");
            }

        }
        if (flag == HttpConfig.UploadImage.getType()) {
            try {

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
//                    ToastTool.show("图片上传成功");-
//                    PushController.getInstance().PushGongQiu(this ,variety_id,titleTextVisew.getText().toString(),titleEt.getText().toString(),imageUrl,Selletype);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                PushController.getInstance().PushGongQiu(this, variety_id, titleTextVisew.getText().toString(), titleEt.getText().toString(), imageUrl, Selletype, model.getId(), model.getName(), "", "");
            }
        }

    }

    private void ChangeTypeItem(boolean isSell) {
        if (isSell) {
            Selletype = 1;
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            sellTextView.setTextColor(getResources().getColor(R.color.white));
            BuyTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
        } else {
            Selletype = 2;
            sellTextView.setBackgroundResource(R.drawable.activity_select_item_unselect);
            BuyTextView.setBackgroundResource(R.drawable.activity_select_item_select);
            sellTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            BuyTextView.setTextColor(getResources().getColor(R.color.white));
        }
    }

    //发布成功弹窗
    public void PopUpDialog() {   //弹框
        UserPreference.setISPOSTFINISHPOSR("234");
        EventManager.getInstance().post(new IMStringEvent(true));
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
                PushGongQiu();
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.sellTextView:
                ChangeTypeItem(true);
                break;
            case R.id.BuyTextView:
                ChangeTypeItem(false);
                break;
            case R.id.typeSelectLayout:
                IntentTools.startSelectType(this, typeSelect);
                break;
            case R.id.addressTv:
                IntentTools.startCityList(this, 1010,"ispub");
                break;
        }
    }

    private void PushGongQiu() {

        String titleTextVisewStr = titleTextVisew.getText().toString();
        String titleEtStr = titleEt.getText().toString();
        if (variety_id.equals("")) {
            ToastTool.show("请选择品种");
            return;
        }
        if (titleTextVisewStr.equals("")) {
            ToastTool.show("请填写标题");
            return;
        }
        if (titleEtStr.equals("")) {
            ToastTool.show("请填写内容");
            return;
        }

//        if (model.getId().equals("")) {
//            ToastTool.show("请选择城市");
//            return;
//        }
        if (bmp.size() == 0) {
            ToastTool.show("请选择图片");
        } else {
//            File[] fillist;
//            fillist = new File[bmp.size()];
//            for (int i = 0; i < bmp.size(); i++) {
//                fillist[i] = new File(bmp.get(i));
//            }
//            PushController.getInstance().upLoadFile(this, fillist, HttpConfig.UploadImage);
            new ImageBitmapAsy(bmp).execute();
        }

        return;
    }


    public class ImageBitmapAsy extends AsyncTask{
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
            PushController.getInstance().upLoadFile(SgPushGongQiuDetailActivity.this, fill, HttpConfig.UploadImage);
            super.onPostExecute(o);
        }

    }



    class LoadFile implements ViewNetCallBack {

        @Override
        public void onConnectStart() {
            dialog.show();
        }

        @Override
        public void onConnectEnd() {
            dialog.dismiss();
//            PushController.getInstance().PushGongQiu(this ,variety_id,titleTextVisew.getText().toString(),titleEt.getText().toString(),imageUrl,Selletype);
        }

        @Override
        public void onFail(Exception e) {
//            dialog.dismiss();
        }

        @Override
        public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
            try {

                JSONObject object = new JSONObject(o.toString());
                if (!object.getBoolean("status")) {
                    ToastTool.show(object.getString("message"));
                } else {
                    List<ImageUploadModel> modelist = (List<ImageUploadModel>) result;
                    for (ImageUploadModel m : modelist) {
                        Logger.e("model" + m.getUrl());
                        imageUrl = m.getImg_id() + ",";
                    }
                    ToastTool.show("图片上传成功");
//                    PushController.getInstance().PushGongQiu(this ,variety_id,titleTextVisew.getText().toString(),titleEt.getText().toString(),imageUrl,Selletype);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                PushController.getInstance().PushGongQiu(this, variety_id, titleTextVisew.getText().toString(), titleEt.getText().toString(), imageUrl, Selletype);
            }
        }
    }
}
