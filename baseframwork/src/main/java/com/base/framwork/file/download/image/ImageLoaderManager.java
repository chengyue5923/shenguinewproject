package com.base.framwork.file.download.image;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.base.framwork.file.download.image.lisener.ImDLisener;
import com.base.platform.android.application.BaseApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 *imageLoader
 */
public class ImageLoaderManager {

    static ImageLoaderManager instance;
    ImageLoader imageLoader ;

    public ImageLoaderManager() {
       ;
    }
    DisplayImageOptions options;
    /**
     * 获取 图片下载的单例
     * @return
     */
    public static ImageLoaderManager getInstance() {
        if (instance ==null)
            instance = new ImageLoaderManager();
        return instance;
    }


    /**
     * 实例化 loader
     * @param subImage  下载见 需要的图片
     * @param nullImage  没有图片
     * @param failImage 失败
     * @param round  圆角
     */
    public void init(int  subImage,int nullImage,int failImage,int round){
        options = new DisplayImageOptions.Builder()
                .showStubImage(subImage)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(nullImage)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(failImage)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(round))  // 设置成圆角图片
                .build();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(BaseApplication.getInstance().getApplicationContext()));

    }

    /**
     * 实例化 loader  下载前的 imageid 下载失败的imageid   方脚
     * @param subImage  下载见 需要的图片
     * @param failImage 失败
     */
    public void init(int  subImage,int failImage){
        options = new DisplayImageOptions.Builder()
                .showStubImage(subImage)          // 设置图片下载期间显示的图片
                .showImageOnFail(failImage)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(BaseApplication.getInstance().getApplicationContext()));

    }

    /**
     * 实例化 loader  下载前的 imageid 下载失败的imageid   方脚

     */
    public void init(){
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(BaseApplication.getInstance().getApplicationContext()));

    }

    /**
     *
     * @param url
     * @param lisener
     * @param imageView
     */
    public void  disPlayImage(String url,ImDLisener lisener,ImageView imageView){
     ImageLoader.getInstance().displayImage(url,imageView,options,new ImageLisener(lisener),new ImageProgressLisener(lisener));
    }



    private class ImageLisener implements ImageLoadingListener{
        ImDLisener lisener;

        private ImageLisener(ImDLisener lisener) {
            this.lisener = lisener;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            if (lisener==null){
                return;
            }
            lisener.startDown();
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (lisener==null){
                return;
            }
            lisener.fail();
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            FadeInBitmapDisplayer.animate(view, 500);
                        if (lisener==null){
                return;
            }
            lisener.complete();
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }

    private class ImageProgressLisener implements ImageLoadingProgressListener{
        ImDLisener lisener;

        private ImageProgressLisener(ImDLisener lisener) {
            this.lisener = lisener;
        }
        @Override
        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            if (lisener==null){
                return;
            }
            lisener.progress(current,total);
        }

    }





}
