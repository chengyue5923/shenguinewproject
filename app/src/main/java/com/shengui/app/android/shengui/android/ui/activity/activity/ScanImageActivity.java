package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.ListUtil;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.codescan.decoding.Intents;
import com.shengui.app.android.shengui.android.ui.dialog.BottomSaveDialog;
import com.shengui.app.android.shengui.utils.im.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.kdmobi.gui.R.color.context;


/**
 * Created by yanbo on 2016/12/6.
 */
public class ScanImageActivity extends BaseActivity implements BottomSaveDialog.SaveOnClick {
    @Bind(R.id.indexTv)
    TextView indexTv;
    @Bind(R.id.imagePager)
    ViewPager imagePager;
    List<String> images = null;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        imagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (!ListUtil.isNullOrEmpty(images))
                    indexTv.setText("(" + (position + 1) + "/" + images.size() + ")");
                imagePager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        try{
            images = getIntent().getStringArrayListExtra("images");
            int index = getIntent().getIntExtra("index", 0);
            if (ListUtil.isNullOrEmpty(images))
                return;
            indexTv.setText("(" + (index + 1) + "/" + images.size() + ")");
            ImagePagerAdapter adapter = new ImagePagerAdapter(images);
            imagePager.setAdapter(adapter);
            imagePager.setCurrentItem(index);
        }catch (Exception e){

        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_image;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

    @Override
    public void onCLick() {
        File sd = Environment.getExternalStorageDirectory();
        boolean can_write = sd.canWrite();
        Logger.e("erer"+can_write);
        new SaveImage(images.get(position)).execute();
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
                file = new File(sdcard + "/Download/" + new Date().getTime()+position+".png");
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
            new AlertDialog.Builder(ScanImageActivity.this)
                    .setTitle("提示")
                    .setMessage(result)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
    class ImagePagerAdapter extends PagerAdapter {
        List<String> images = new ArrayList<String>();
        List<View> list = new ArrayList<View>();

        public ImagePagerAdapter(List<String> images) {
            this.images = images;
        }

        @Override
        public int getCount() {
            return ListUtil.isNullOrEmpty(images) ? 0 : images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView imageView = new PhotoView(ScanImageActivity.this);
            imageView.enable();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  finish();
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialogOnclick(position);
                    return false;
                }
            });

//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ImageLoader.getInstance(ScanImageActivity.this).displayImage(images.get(position), imageView);
            Glide.with(ScanImageActivity.this).load(images.get(position)).asBitmap().fitCenter().placeholder(R.drawable.default_pictures).into(imageView);
            container.addView(imageView);
            list.add(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            try {
                container.removeView(list.get(position));
            } catch (Exception e) {
            }


        }
    }
    int position;
    //成功
    public void dialogOnclick(int pis) {

        position=pis;
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.
                checkSelfPermission(ScanImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //提示用户开户权限
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            ActivityCompat.requestPermissions(ScanImageActivity.this, perms, 10002);
            return;
        }



        BottomSaveDialog PopUpDialogs = new BottomSaveDialog(this);
        PopUpDialogs.setOnBottmeOnClick(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	
    	//Toast.makeText(this, "back "+keyCode, Toast.LENGTH_LONG).show();
    	if(keyCode==4){
    		Logger.e("action back");
    		
    		finish();
    		return false;
    	}
    	
    	
    	return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	Logger.e("-----------onBackPressed----------");
    	super.onBackPressed();
    }
}
