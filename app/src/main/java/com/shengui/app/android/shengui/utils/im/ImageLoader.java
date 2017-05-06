package com.shengui.app.android.shengui.utils.im;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.enums.UrlRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.Executors;

/**
 * 图片缓存类
 *
 * @author linxi
 *         异步加载图片
 */
public class ImageLoader {


    Context context;

    int stub_id;
    static ImageLoader instance;

    public static ImageLoader getInstance(Context context, int iconDefaultResId) {
        if (instance == null) {
            return new ImageLoader(context, iconDefaultResId);
        }
        return instance;
    }

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            return new ImageLoader(context, 0);
        }
        return instance;
    }

    public ImageLoader(Context context) {
        this(context, 0);
    }

    public ImageLoader(Context context, int iconDefaultResId) {
        // 默认图片
        this.context = context;
        stub_id = iconDefaultResId;


    }

    public void displayImage(String url, ImageView imageView) {
        if (StringTools.isNullOrEmpty(url))
            return;
        Glide.with(context)
                .load(getUrl(url))
                .centerCrop()
                .placeholder(stub_id)
                .into(imageView);

    }

    public void displayHtmlImage(String url, ImageView imageView) {
        if (StringTools.isNullOrEmpty(url))
            return;
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(stub_id)
                .into(imageView);

    }

    private String getUrl(String url) {
        if (url.startsWith("http")) {
            return url;
        }
        return UrlRes.getInstance().getPictureUrl() + url;
    }


    public void loadLocalImage(String path, ImageView imageView) {

        if (TextUtils.isEmpty(path)
                || null == imageView) {
            return;
        }

        Glide.with(context)
                .load(path)
                .centerCrop()
                .placeholder(R.drawable.default_error)
                .into(imageView);
    }


    public void loadAvatar(final String url, ImageView imageView) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "fhzc";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        final String suffix = url.substring(url.length()-4,url.length());
        final File avatars = new File(appDir, url.substring(url.indexOf("image/")+6));
        try {
            Logger.e("fuwen" + avatars.getAbsolutePath());
            if (!avatars.exists()) {
                displayImage(url, imageView);
                final FileOutputStream fos = new FileOutputStream(avatars);
                Executors.newFixedThreadPool(2).execute(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        try {
                            bitmap = Glide.with(context)
                                    .load(url)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get();
                            if (bitmap != null) {
                                Bitmap.CompressFormat format =  suffix.equals("jpg")?Bitmap.CompressFormat.JPEG:Bitmap.CompressFormat.PNG;
                                bitmap.compress(format, 100, fos);
                                fos.flush();
                                if (fos != null) {
                                    fos.close();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

//            if (bitmap != null) {
//                try {
//                    fos = new FileOutputStream(avatars);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                    fos.flush();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }finally {
//                    try {
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            } else {
                Glide.with(context).load(avatars).centerCrop().placeholder(stub_id).into(imageView);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                Uri.fromFile(new File(currentFile.getPath()))));
    }
}




