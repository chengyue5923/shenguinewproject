package com.shengui.app.android.shengui.android.ui.activity.activity.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.MD5Util;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载 apk的service
 */
public class UpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int progress;//当前进度
    private int lastRate = 0;
    private String apkUrl = "";
    private String saveFileName = Constant.APK_DOWNLOAD_PATH + File.separator + "shenguiTemple.apk";
    NotificationCompat.Builder notificationBuilder;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(BaseApplication.getInstance().getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("正在下载")
                .setTicker("开始下载")
                .setProgress(100, 0, true)
                .setAutoCancel(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            apkUrl = intent.getStringExtra("url");
            if (!StringTools.isNullOrEmpty(apkUrl)) {
                if (!new File(Constant.APK_DOWNLOAD_PATH).exists()) {
                    new File(Constant.APK_DOWNLOAD_PATH).mkdirs();
                }
                saveFileName = Constant.APK_DOWNLOAD_PATH + File.separator + MD5Util.string2MD5(apkUrl) + ".apk";
                Logger.e("-------apkUrl----local---" + saveFileName);
                new DowLoadThread().start();
            }
        } catch (Exception E) {
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void install() {
        File f = new File(saveFileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(f),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }


    private class DowLoadThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                com.base.platform.utils.android.Logger.e("----apkUrl----" + length);
                InputStream is = conn.getInputStream();
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[8192];
                while (true) {
                    int numread = is.read(buf);

                    count += numread;
                    progress = (int) (((float) count / length) * 100);

                    // 更新进度
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;
                    if (progress >= lastRate + 1) {
                        if (progress % 2 == 1) {
                            mHandler.sendMessage(msg);
                            lastRate = progress;

                        }
                    }
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(0);
                        // 下载完了，cancelled也要设置
                        com.base.platform.utils.android.Logger.e("下载完毕");
                        break;
                    }
                    fos.write(buf, 0, numread);
                }
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
               Logger.e(e.getLocalizedMessage(), e);

            }

        }
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 下载完毕
                    // 取消通知
                    mNotificationManager.cancel(0);
                    install();
                    break;
                case 1:
                    int rate = msg.arg1;
                    if (rate < 100) {
                        com.base.platform.utils.android.Logger.e("rate-" + rate);
                        notificationBuilder.setProgress(100, rate, true);
                    }
                    mNotificationManager.notify(0 /* ID of notification */,
                            notificationBuilder.build());
                    break;
            }
        }
    };
    private NotificationManager mNotificationManager;


}
