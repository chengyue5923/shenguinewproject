package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.dialog.BottomSaveDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SgGetLoginSuccessDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2015/12/16.
 */
public class BigImageActivity extends BaseActivity implements BottomSaveDialog.SaveOnClick {

    ImageView images;
    @Bind(R.id.imagessdd)
    ImageView imagessdd;
    TextView savetv;
    String Url;
    @Override
    protected void initView() {
        View view = View.inflate(this, R.layout.ac_load, null);
//        setContentView(R.layout.ac_load);
        images = (ImageView) view.findViewById(R.id.imagessdd);
        savetv=(TextView)view.findViewById(R.id.savetv);
        if (getIntent() != null) {
             Url = (String) getIntent().getSerializableExtra("url");
            try {
                Glide.with(this).load(Url).asBitmap().placeholder(R.drawable.default_pictures).into(images);
            } catch (Exception e) {
                Logger.e("sd" + e.getMessage());
            }
        }
        setContentView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }
        images.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                if (PackageManager.PERMISSION_GRANTED != ContextCompat.
//                        checkSelfPermission(BigImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    //提示用户开户权限
//                    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
//                    ActivityCompat.requestPermissions(BigImageActivity.this, perms, 10002);
                    dialogOnclick();
//                }
//                new SaveImage(Url).execute(); // Android 4.0以后要使用线程来访问网络
                return false;
            }
        });

    }
    //成功
    public void dialogOnclick() {

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                checkSelfPermission(BigImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //提示用户开户权限
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            ActivityCompat.requestPermissions(BigImageActivity.this, perms, 10002);
            return;
        }
        BottomSaveDialog PopUpDialogs = new BottomSaveDialog(this);
        PopUpDialogs.setOnBottmeOnClick(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    @Override
    public void onCLick() {
        new SaveImage(Url).execute(); // Android 4.0以后要使用线程来访问网络
    }

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     *
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        String imgurl;
        public SaveImage(String url){
            imgurl=url;
        }
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = imgurl.lastIndexOf(".");
                String ext = imgurl.substring(idx);
                file = new File(sdcard + "/Download/" + new Date().getTime() + ".png");
                InputStream inputStream = null;
                URL url = new URL(imgurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            new AlertDialog.Builder(BigImageActivity.this)
                    .setTitle("提示")
                    .setMessage(result)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
    @Override
    protected void initEvent() {
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        savetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveImage(Url).execute(); // Android 4.0以后要使用线程来访问网络
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_load;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }


}
