package com.shengui.app.android.shengui.android.ui.activity.activity.image;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.kdmobi.gui.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mine on 2015/9/24.
 */
public class ImagPagerUtil {
    private List<String> mPicList;
    private Context mActivity;
    private Dialog dialog;
    private LazyViewPager mViewPager;
    private LinearLayout mLL_progress;
    private TextView tv_loadingmsg;
    private int screenWidth;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private TextView tv_img_current_index;
    private TextView tv_img_count;
    private TextView tv_content,saveTv;

    Context context;
    public ImagPagerUtil(Context activity, List<String> mPicList) {
        this.mPicList = mPicList;
        this.context=activity;
        this.mActivity = activity;
        imageLoader = ImageLoader.getInstance();
        setOptions();
        init();
    }

    public ImagPagerUtil(Activity activity, String[] picarr) {
        mPicList = new ArrayList<>();
        for (int i = 0; i < picarr.length; i++) {
            mPicList.add(picarr[i]);
        }
        this.mActivity = activity;
        imageLoader = ImageLoader.getInstance();
        setOptions();
        init();
    }

    /**
     * 设置图片下方的文字
     * @param str
     */
    public void setContentText(String str) {
        if (!TextUtils.isEmpty(str)) {
            tv_content.setText(str);
        }
    }

    public void show() {
        dialog.show();
    }

    private void init() {
        dialog = new Dialog(mActivity, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(mActivity, R.layout.view_dialogpager_img, null);
        mViewPager = getView(contentView, R.id.view_pager);
        mLL_progress = getView(contentView, R.id.vdi_ll_progress);
        tv_loadingmsg = getView(contentView, R.id.tv_loadingmsg);
        tv_img_current_index = getView(contentView, R.id.tv_img_current_index);
        tv_img_count = getView(contentView, R.id.tv_img_count);
        tv_content = getView(contentView, R.id.tv_content);
        dialog.setContentView(contentView);
        saveTv=getView(contentView,R.id.saveTv);
        tv_img_count.setText(mPicList.size() + "");
        tv_img_current_index.setText("1");

        int size = mPicList.size();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        ZoomImageView imageView = new ZoomImageView(mActivity);
        imageView.measure(0, 0);

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveImage(mPicList.get(positions)).execute(); // Android 4.0以后要使用线程来访问网络
            }
        });
//        android获取屏幕的高度和宽度用到WindowManager这个类，两种方法：

        WindowManager wm = (WindowManager) mActivity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display =wm.getDefaultDisplay();
        screenWidth = display.getWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(screenWidth, display.getHeight());
        imageView.setLayoutParams(marginLayoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {//如果不需要点击图片关闭的需求，可以去掉这个点击事件
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Logger.e("ddddddddddffffffffffffffff");
////                new SaveImage(mPicList.get(positions)).execute(); // Android 4.0以后要使用线程来访问网络
//                return false;
//            }
//        });
        for (int i = 0; i < size; i++) {
            imageViews.add(imageView);
        }
        initViewPager(imageViews);
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
                file = new File(sdcard + "/Download/" + new Date().getTime() + ext);
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
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            new AlertDialog.Builder(context)
                    .setTitle("提示")
                    .setMessage(result)
                    .setPositiveButton("确定",null)
                    .show();
        }
    }
    private int positions=0;
    private void initViewPager(ArrayList<ImageView> list) {
        mViewPager.setOnPageChangeListener(new LazyViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tv_img_current_index.setText("" + (position + 1));
                positions=position;
            }
        });

        MyImagPagerAdapter myImagPagerAdapter = new MyImagPagerAdapter(list);
        mViewPager.setAdapter(myImagPagerAdapter);
    }

    class MyImagPagerAdapter extends PagerAdapter {
        ArrayList<ImageView> mList;

        public MyImagPagerAdapter(ArrayList<ImageView> mList) {
            this.mList = mList;
        }

        @Override
        public Object instantiateItem(ViewGroup container,final int position) {
            ImageView imageView = mList.get(position);
            showPic(imageView, mPicList.get(position));

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            if (null == mList || mList.size() <= 0) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    protected void setOptions() {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    private void showPic(ImageView imageView, String url) {
        imageView.setImageBitmap(null);

        mLL_progress.setVisibility(View.VISIBLE);
        imageLoader.displayImage(url, imageView, options, animateFirstListener, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {
                float temp = (float) i / i1;
                int progress = (int) (temp * 100);
                if (null != tv_loadingmsg) {
                    tv_loadingmsg.setText(progress + "%");
                }
            }
        });

        dialog.show();
    }

    private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            mLL_progress.setVisibility(View.GONE);
            tv_loadingmsg.setText("");
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(loadedImage);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("ImagPageUtil", "Could not cast View to concrete class \n" + ex.getMessage());
            throw ex;
        }
    }
}
