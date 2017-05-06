package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
public class SgPushQuestionDetailActivity extends BaseActivity implements View.OnClickListener, View.OnLayoutChangeListener {
    @Bind(R.id.titleEt)
    EditTextMultiLine titleEt;
    @Bind(R.id.mGridView)
    GridView mGridView;
    @Bind(R.id.imageCount)
    TextView imageCount;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.pushTextView)
    TextView pushTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.quanzilayout)
    RelativeLayout quanzilayout;
    @Bind(R.id.headlayout)
    RelativeLayout headlayout;
    @Bind(R.id.rootlayout)
    RelativeLayout rootlayout;
    @Bind(R.id.facebookibre)
    ImageView facebookibre;
    @Bind(R.id.topicTv)
    TextView topicTv;
    @Bind(R.id.addressTv)
    TextView addressTv;
    private GridAdapter gridAdapter;
    public static ArrayList<String> bmp;
    private String type = "";
    private Dialog dialog;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private String imageUrl = "";
    private static final int circleCode = 10004;
    private int CircleId;
    private String titleString = "";
    private String titleid = "";

    LocationModel model=new LocationModel();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        addressTv.setText(UserPreference.getUsualCityName());
        model.setId(UserPreference.getUsualCityId());
        model.setName(UserPreference.getUsualCityName());
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
    }

    @Override
    protected void initEvent() {
        addressTv.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == bmp.size()) {
                    IntentTools.openImageChooseActivity(SgPushQuestionDetailActivity.this, bmp);
                }
            }
        });
        headlayout.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        pushTextView.setOnClickListener(this);
        quanzilayout.setOnClickListener(this);
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
    protected int getLayoutId() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1230 && resultCode == 1000) {
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
        if (requestCode == 1214) {
            if (resultCode == 1010) {
                Log.e("mods-------------------", data.getStringExtra("topic") + data.getStringExtra("result"));
                titleString = data.getStringExtra("topic");
                titleid = data.getStringExtra("result");
                topicTv.setText(titleString);
            }
        }
        if (requestCode == circleCode) {
            if (resultCode == 1010) {
                Log.e("modelsssss", data.getStringExtra("modelist"));
                CircleId = Integer.parseInt((String) data.getStringExtra("modelist"));
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
                    PushQuestion();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (flag == HttpConfig.pushTie.getType()) {
            try {
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

    private void PushQuestion() {
        String strtitleEt = titleEt.getText().toString();
        if (strtitleEt.equals("")) {
            ToastTool.show("请填乌龟详情");
            return;
        }
        if (CircleId == 0) {
            ToastTool.show("请选择圈子");
            return;
        }
        if (model.getName().equals("")) {
            ToastTool.show("请选择城市");
            return;
        }
        PushController.getInstance().PushTieZi(this, CircleId, strtitleEt, imageUrl, "2", "", titleid,model.getId(),model.getName());
    }


    //发布成功弹窗
    public void PopUpDialog() {   //弹框
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
                } else {
                    File[] fillist;
                    fillist = new File[bmp.size()];
                    for (int i = 0; i < bmp.size(); i++) {
                        fillist[i] = new File(bmp.get(i));
                    }
                    PushController.getInstance().upLoadFile(this, fillist, HttpConfig.UploadImage);
                }
                break;
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.quanzilayout:
                IntentTools.startquanzilist(this, circleCode);
                break;
            case R.id.headlayout:
                IntentTools.startCreateTop(this, 1214);
                break;
            case R.id.addressTv:
                IntentTools.startCityList(this,1230);
                break;
//            case R.id.feedbackTextView:
//                if (StringTools.isNullOrEmpty(titleEt.getText().toString())){
//                    ToastTool.show("请输入反馈内容");
//                    return;
//                }
//                List<NameValuePair> httpParams = new ArrayList<>();
//                httpParams.add(new BasicNameValuePair("type", type));
//                httpParams.add(new BasicNameValuePair("content", titleEt.getText().toString()));
//                httpParams.add(new BasicNameValuePair("mobile", editText.getText().toString()));
//                HttpConfigBean config = HttpManager.getInstance().getConifgById(HttpConfig.pushtiezi);
//                new UploadAnswerImageTask(httpParams).execute(bmp,
//                        UrlRes.getInstance().getUrl() + config.getActioin());
//                break;
//        }
        }


//    public class UploadAnswerImageTask extends AsyncTask<Object, Void, Integer> {
//        JSONObject mJson;
//        ResponseHandler mResponseHandler;
//        List<NameValuePair> mHttpParams;
//
//        public UploadAnswerImageTask(List<NameValuePair> params) {
//
//            mHttpParams = params;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            if (null != dialog && !dialog.isShowing()) {
//                dialog.show();
//            }
//        }
//
//        @Override
//        protected Integer doInBackground(Object... params) {
//            try {
//                ArrayList<File> imgFiles = new ArrayList<File>();
//                if (params[0] instanceof String) {
//                    File f = new File((String) params[0]);
//                    if (f.exists()) {
//                        imgFiles.add(f);
//                    }
//                } else if (params[0] instanceof List) {
//                    List<String> imgFns = (List<String>) params[0];
//                    for (String fn : imgFns) {
//                        File f = new File(fn);
//                        if (f.exists()) {
//                            imgFiles.add(f);
//                        }
//                    }
//                }
//
//
//                final String url = (String) params[1];
//                PostMethod method = new PostMethod(url);
//                method.addRequestHeader("ACCEPT", "application/json");
//
//                if (!StringTools.isNullOrEmpty(PreferceManager.getInsance().getValueBYkey(Constans.jseesion))) {
//                    String session = PreferceManager.getInsance().getValueBYkey(Constans.jseesion);
//                    method.addRequestHeader("Cookie", "JSESSIONID=" + session);
//                    com.base.platform.utils.android.Logger.e("session = " + session);
//                }
//
//
//                HttpClient client = new HttpClient();
//                client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
//                Part[] parts = new Part[mHttpParams.size() + imgFiles.size()];
//
//                for (int i = 0; i < mHttpParams.size(); ++i) {
//                    NameValuePair pair = mHttpParams.get(i);
//                    parts[i] = new StringPart(pair.getName(), pair.getValue(), "UTF-8");
//                }
//
//                for (int i = 0; i < imgFiles.size(); ++i) {
//                    parts[mHttpParams.size() + i] = new FilePart("payload", "jpg", imgFiles.get(i), "image/jpeg", null);
//                }
//
//                method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));
//                client.executeMethod(method);
//                String responseString = method.getResponseBodyAsString();
//                method.releaseConnection();
//
//                if (responseString != null) {
//                    mJson = new JSONObject(responseString);
//                    return method.getStatusCode();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return HttpStatus.SC_BAD_REQUEST;
//        }
//
//        @Override
//        protected void onPostExecute(Integer statusCode) {
//            if (null != dialog && dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            if (statusCode == HttpStatus.SC_OK && mJson != null) {
//                ToastTool.show("反馈成功");
//                finish();
//            } else if (mJson != null) {
//                try {
//                    ToastTool.show(mJson.getString("message"));
//                } catch (Exception e) {
//                }
//
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rootlayout.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            headlayout.setVisibility(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            headlayout.setVisibility(View.GONE);
        }
    }

}
