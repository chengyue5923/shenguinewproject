package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.ScanImageActivity;
import com.shengui.app.android.shengui.utils.im.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/8/4.
 */
public class FullScreenImageActivity extends Activity implements View.OnTouchListener {
    @Bind(R.id.image)
    ImageView image;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File sd = Environment.getExternalStorageDirectory();
                boolean can_write = sd.canWrite();
                Logger.e("erer"+can_write);
                new SaveImage(url).execute();

                return false;
            }
        });
//        ImageLoader.getInstance(this, R.drawable.default_error).displayImage(url, image);
        Glide.with(this).load(url).asBitmap().fitCenter().placeholder(R.drawable.default_pictures).into(image);
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
                file = new File(sdcard + "/Download/" + new Date().getTime()+".png");
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
            new AlertDialog.Builder(FullScreenImageActivity.this)
                    .setTitle("提示")
                    .setMessage(result)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return true;
    }

}
