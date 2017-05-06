package com.shengui.app.android.shengui.utils.net;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;


import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.utils.im.CommonUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 2016/7/14.
 */
public class CacheUtil {
    public static String getSavePath(int type) {
        String path;
        String floder = (type == Constant.FILE_SAVE_TYPE_IMAGE) ? "images"
                : "audio";
        if (CommonUtil.checkSDCard()) {
            path = Environment.getExternalStorageDirectory().toString()
                    + File.separator + "FHZC-IM" + File.separator + floder
                    + File.separator;

        } else {
            path = Environment.getDataDirectory().toString() + File.separator
                    + "FHZC-IM" + File.separator + floder + File.separator;
        }
        return path;
    }

    public static synchronized String getCacheDirectory(Context context) {
        String sRootDirectory;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sRootDirectory = Environment.getExternalStorageDirectory().toString() + "/Android/data/" + context.getPackageName();
        } else {
            sRootDirectory = context.getFilesDir().toString();
        }
        return sRootDirectory;
    }

    public static boolean isSdCardAvailuable() {

        boolean bRet = false;
        do {
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                break;
            }

            if (getSDFreeSize() < 5) {
                break;
            }

            bRet = true;
        } while (false);

        return bRet;
    }

    /**
     * @return
     * @Description 获取sdcard可用空间的大小
     */
    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    public static File createTmpFile(Context context) {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载

            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!pic.exists()) {
                pic.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(pic, fileName + ".jpg");
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            return tmpFile;
        }

    }
}
