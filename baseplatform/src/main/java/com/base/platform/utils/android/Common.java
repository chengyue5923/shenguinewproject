package com.base.platform.utils.android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.java.StringTools;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 公用工具
 */
public class Common {

    public static View.OnFocusChangeListener onFocusAutoClearHintListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText textView = (EditText) v;
            String hint;
            if (hasFocus && !StringTools.isNullOrEmpty(((EditText) v).getText().toString())) {
                hint = textView.getHint().toString();
                textView.setTag(hint);
                textView.setHint("");
            } else {
                hint = textView.getTag().toString();
                textView.setHint(hint);
            }
        }
    };
    public static View.OnTouchListener onTouchClearHintListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            EditText textView = (EditText) v;
            textView.setHint("");
            return false;
        }


    };

    public static void call(Context context, String num) {
        Intent intent = new Intent();
//系统默认的action，用来打开默认的电话界面
        intent.setAction(Intent.ACTION_CALL);
//需要拨打的号码
        intent.setData(Uri.parse("tel:" + num));
        context.startActivity(intent);

    }

    public static void sendSMS(String smsBody, Context context, String num)

    {

        Uri smsToUri = Uri.parse("smsto:" + num);

        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

        intent.putExtra("sms_body", smsBody);

        context.startActivity(intent);

    }

    public static void email(Context context, String emailAdress) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("plain/text");
        String[] emailReciver = new String[]{emailAdress};


//设置邮件默认地址
        email.putExtra(Intent.EXTRA_EMAIL, emailReciver);
//设置邮件默认标题
        email.putExtra(Intent.EXTRA_SUBJECT, "");
//设置要默认发送的内容
        email.putExtra(Intent.EXTRA_TEXT, "");
//调用系统的邮件系统
        email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(email, "请选择邮件发送软件"));

    }

    public static String takePhoto1(Context context, int requestCode) {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2);
        //下面这句指定调用相机拍照后的照片存储的路径  //--图片的名称设定  用户的uuid和当前时间的拼出来的字符串
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "zmtCach"
                + File.separator + "camera" + ".jpg";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(filePath)));
        ((Activity) context).startActivityForResult(intent, requestCode);
        return filePath;
    }

    public static String getCameraPath() {
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "zmtCach"
                + File.separator + "camera" + ".jpg";
        return filePath;
    }

    public static String ambluePhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(
                "android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        //下面这句指定调用相机拍照后的照片存储的路径  //--图片的名称设定  用户的uuid和当前时间的拼出来的字符串
        String filePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "zmtCach" + File.separator + System.currentTimeMillis() + ".jpg";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                .fromFile(new File(filePath)));
        activity.startActivityForResult(intent, requestCode);
        return filePath;
    }

    /**
     * 获取 屏幕的尺寸
     *
     * @param c 相应的activity
     * @return 屏幕的尺寸
     */
    public static DisplayMetrics screenSize(Activity c) {
        DisplayMetrics dm = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * @param c
     * @return 屏幕的宽
     */
    public static int screenWidth(Activity c) {
        return screenSize(c).widthPixels;
    }

    /**
     * @param c
     * @return 屏幕的高
     */
    public static int screenHeight(Activity c) {
        return screenSize(c).heightPixels;
    }

    /**
     * @param context
     * @param packageName 包名
     * @return 是否安装这个包名的 应用
     */
    public static boolean installPackageCheck(Context context,
                                              String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            return info.versionCode >= 1 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public static void startPhotoZoom(Context context, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }



    public static void noty(String content, int id,int rImageId,int app_name) {
        NotificationManager nm = (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification(rImageId, content, System.currentTimeMillis());
        n.flags = Notification.FLAG_AUTO_CANCEL;
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                BaseApplication.getInstance().getInstance(),
                app_name,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT);

//        n.setLatestEventInfo(
//                BaseApplication.getInstance().getInstance(),
//                content,
//                "",
//                contentIntent);

        nm.notify(id, n);

    }

    public static void cancleNoticeByid(int id) {
        NotificationManager nm = (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }

    public static void cancleNoticeByIdAfterTime(final int id) {
        final NotificationManager nm = (NotificationManager) BaseApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nm.cancel(id);
            }
        }, 1000);
    }

    public static String getManifestVisionCode(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
//            int versionCode = context.getPackageManager()
//                    .getPackageInfo(pkName, 0).versionCode;
            return versionName + "";
        } catch (Exception e) {
        }
        return "1.0.0";
    }

    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public static boolean isNetworkAvailable() {
        Context context = BaseApplication.getInstance().getApplicationContext().getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /****
     * 判断文件是否为本地文件
     *
     * @param filePath
     * @return
     */
    public static boolean isLocalPath(String filePath) {
        if (StringTools.isNullOrEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (filePath.toLowerCase().contains("/sdcard")
                || filePath.toLowerCase().contains("/FH_IMG")
                || filePath.toLowerCase().contains("/storage/emulated")
                || file.exists()) {
            file = null;
            return true;
        }
        file = null;
        return false;
    }

    public static void showSoftinput(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    }


}
