package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.view.view.dialog.factory.DialogFacory;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushGongQiuDetailActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushSuccessDialog;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHGridAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UploadBean;

import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.utilsview.MultiImageSelectorActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollGridView;
import com.shengui.app.android.shengui.android.ui.utilsview.PictureUtil;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import okhttp3.FormBody;


public class PutQuestionsActivity extends SGUHBaseActivity {


    @Bind(R.id.back)
    TextView back;
    @Bind(R.id.top_publish)
    TextView topPublish;
    @Bind(R.id.put_question_header)
    RelativeLayout putQuestionHeader;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.wordNumb)
    TextView wordNumb;
    @Bind(R.id.mGridView)
    NoScrollGridView mGridView;
    @Bind(R.id.addressTv)
    TextView addressTv;
    @Bind(R.id.imageCount)
    TextView imageCount;
    @Bind(R.id.bottom_publish)
    TextView bottomPublish;
    @Bind(R.id.sgh_put_questions_activity)
    LinearLayout sghPutQuestionsActivity;
//    private SGHPhotoGridViewAdapter sghPhotoGridViewAdapter;

    private SGHGridAdapter gridAdapter;
    public static ArrayList<String> bmp;
    private String type = "";
    private Dialog dialog;
    private int screenHeight = 0;
    private int keyHeight = 0;
    private String imageUrl = "";
    private int CircleId;
    private String titleString = "";
    private String titleid = "";


    List<MediaBean> l = new ArrayList<>();
    ;
    List<String> images = new ArrayList<>();

    private final int IMAGEUPLOAD = 1;
    private final int SAVEPOESTION=2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMAGEUPLOAD:
                    final List<UploadBean> uploadBeanList = (List<UploadBean>) msg.obj;
                    SaveQuestion(uploadBeanList, 2);
                    break;
                case SAVEPOESTION:
                    boolean b = (boolean) msg.obj;
                    if (b){
                        PopUpDialog();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(PutQuestionsActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };

    private void SaveQuestion(final List<UploadBean> uploadBeanList, final int type) {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (type == 1) {
                    FormBody formBody = new FormBody.Builder()//创建表单构造器
                            .add("intro", editText.getText().toString())//添加表单参数
                            .add("contentType", String.valueOf(type))
                            .build();//生成简易表单型RequestBody

                    SGHJsonUtil.SaveQuestion(PutQuestionsActivity.this, formBody);
                } else {
                    String files = "";
                    for (int i = 0; i < uploadBeanList.size(); i++) {
                        String data = uploadBeanList.get(i).getData();
                        Log.e("test", "run: "+data);
                        if (i == 0) {
                            files = data;
                        } else {
                            files = files + "," + data;
                        }
                    }
                    FormBody formBody = new FormBody.Builder()//创建表单构造器
                            .add("intro", editText.getText().toString())//添加表单参数
                            .add("files", files)
                            .add("contentType", String.valueOf(type))
                            .build();//生成简易表单型RequestBody
                    Boolean aBoolean = SGHJsonUtil.SaveQuestion(PutQuestionsActivity.this, formBody);
                    Message message = handler.obtainMessage();
                    message.what = SAVEPOESTION;
                    message.obj = aBoolean;
                    handler.sendMessage(message);

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_put_questions);
        ButterKnife.bind(this);


        addressTv.setText(UserPreference.getCityName());
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;
        dialog = DialogFacory.getInstance().createRunDialog(this, "正在提交。。");
        bmp = new ArrayList<String>();
        gridAdapter = new SGHGridAdapter(this, bmp, 9);
        mGridView.setAdapter(gridAdapter);

        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                wordNumb.setText(length + "字");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        bottomPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });

        topPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == bmp.size()) {
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(PutQuestionsActivity.this, Manifest.permission.CAMERA)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.CAMERA"};
                        ActivityCompat.requestPermissions(PutQuestionsActivity.this, perms, 10001);
                        return;
                    }
                    if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                            checkSelfPermission(PutQuestionsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //提示用户开户权限
                        String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                        ActivityCompat.requestPermissions(PutQuestionsActivity.this, perms, 10002);
                        return;
                    }
                    IntentTools.openImageChooseActivity(PutQuestionsActivity.this, bmp);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void publish() {
        if (editText.getText().length() == 0) {
            Toast.makeText(PutQuestionsActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else if (bmp.size() == 0) {
            dialog.show();
            SaveQuestion(null, 1);
        } else {
            new ImageBitmapAsy(bmp).execute();

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
//            PushController.getInstance().upLoadFile(PutQuestionsActivity.this, fill, HttpConfig.UploadImage);
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    List<UploadBean> uploadBeen = SGHJsonUtil.uploadImg(PutQuestionsActivity.this, fill);
                    Log.e("test", "run: " + bmp.size());
                    Message message = handler.obtainMessage();
                    message.what = IMAGEUPLOAD;
                    message.obj = uploadBeen;
                    handler.sendMessage(message);
                }
            });
            super.onPostExecute(o);
        }

    }


    public void deleteImage(String path) {
        if (bmp.contains(path)) bmp.remove(path);
        imageCount.setText(bmp.size() + "/9");
        gridAdapter.setBitmaps(bmp);
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
                    Intent intent = new Intent(PutQuestionsActivity.this,SGHMyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }
}
