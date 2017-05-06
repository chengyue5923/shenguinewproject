package com.base.framwork.file.download.image;

import android.widget.ImageView;

import com.base.framwork.file.download.image.lisener.ImDLisener;

/**
 * ImageFactroy
 */
public class ImageLoaderFactroy {


    static  ImageLoaderFactroy instance;

    public ImageLoaderFactroy() {
    }

    public static ImageLoaderFactroy getInstance() {
        if (instance==null){
            instance = new ImageLoaderFactroy();
        }
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
        ImageLoaderManager.getInstance().init(subImage,nullImage,failImage,round);
    }


    /**
     * 实例化 loader  下载前的 imageid 下载失败的imageid   方脚
     * @param subImage  下载见 需要的图片
     * @param failImage 失败
     */
    public void init(int  subImage,int failImage){
        ImageLoaderManager.getInstance().init(subImage,failImage);
    }


    /**
     *
     * @param iv
     * @param lisener
     */
    public void disPlay(String url,ImageView iv,ImDLisener lisener){
      ImageLoaderManager.getInstance().disPlayImage(url,lisener,iv);
    }



    }
