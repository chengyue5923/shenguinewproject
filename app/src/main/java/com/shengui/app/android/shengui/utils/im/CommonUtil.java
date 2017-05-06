package com.shengui.app.android.shengui.utils.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;

import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.shengui.app.android.shengui.configer.enums.UrlRes;
import com.shengui.app.android.shengui.db.UserPreference;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 2016/7/14.
 */
public class CommonUtil {

    /**
     * @return
     * @Description 判断存储卡是否存在
     */
    public static boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }

        return false;
    }

    /**
     * @param text
     * @return
     * @Description 判断是否是url
     */
    public static String matchUrl(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        Pattern p = Pattern.compile(
                "[http]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @param savePath   文件保存路径
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        File file = new File(savePath);
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /****
     * 是否大于kiakat系统(4.4)
     *
     * @return
     */
    public static boolean isUpperKK() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isBelowVersion(final int v) {
        return Build.VERSION.SDK_INT < v;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Create a color integer value with specified alpha.
     * This may be useful to change alpha value of background color.
     *
     * @param alpha     alpha value from 0.0f to 1.0f.
     * @param baseColor base color. alpha value will be ignored.
     * @return a color with alpha made from base color
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static synchronized String getWebStorageDirectory(Context context) {
        return context.getApplicationInfo().dataDir + "/databases/";
    }

    /**
     * 隐藏手机号码中间四位
     *
     * @param mobile
     * @return
     */
    public static String getPhoneNum(String mobile) {
        if (StringTools.isNullOrEmpty(mobile)) {
            return "";
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    /**
     * 隐藏身份证号生日
     *
     * @param id
     * @return
     */
    public static String getIDNum(String id) {
        if (StringTools.isNullOrEmpty(id)) {
            return "";
        }
        return id.substring(0,3) + "***************";
    }


    /**
     * 判断是否为密码
     */
    public static boolean isPassword(String password) {
        if (StringTools.isNullOrEmpty(password)) {
            ToastTool.show("请输入密码");
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastTool.show("请输入6-20位密码");
            return false;
        }
        return true;

    }

    /**
     * 判断是否为密码
     */
    public static boolean isPasswordEqul(String password, String comfirmPsw) {
        if (!StringTools.isEqual(password, comfirmPsw)) {
            ToastTool.show("两次密码输入不一致");
            return false;
        }
        return true;

    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, String phoneNum) {
        try {
            Intent intent = new Intent();
            //设置要拨打的号码
            intent.setData(Uri.parse("tel:" + phoneNum));
            //设置动作,拨号 动作
            intent.setAction(intent.ACTION_CALL);
            //跳转到拨号界面
            context.startActivity(intent);
        } catch (Exception e) {
        }

    }

    /**
     * 判断消息是否为分享
     *
     * @param content
     * @return
     */
    public static boolean isShareText(String content) {
        return content.startsWith("share$_$rights$_$") || content.startsWith("share$_$activity$_$") || content.startsWith("share$_$report$_$") || content.startsWith("share$_$product$_$");

    }

    /**
     * double 保留两位小数
     */
    public static String keepTwoDecimalPlaces(double privce) {
        if (privce != 0) {
            String keepTwoDecimalPlaces = "";
            keepTwoDecimalPlaces = new DecimalFormat("######0.00")
                    .format(privce);
            return keepTwoDecimalPlaces;
        } else {
            return "0.00";
        }
    }

    public static Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            if (imageUrl.contains("/Public/Uploads/ueditor")&&!imageUrl.contains("http")) {
                myFileUrl = new URL(UrlRes.getInstance().getPictureUrl() + imageUrl);
            } else {
                myFileUrl = new URL(imageUrl);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);

            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);

            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }


    public static void getNetIp() {
        try{
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    String ip = GetNetIp();

                }
            });
            executor.awaitTermination(10, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @Title: GetNetIp
     * @Description:
     * @param @return
     * @return String
     * @throws
     */
    public static String GetNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
            infoUrl = new URL("http://ip168.com/");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");

                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                httpConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ipLine;
    }

    /**
     * 获取本地IP
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                Enumeration<InetAddress> enIp = ni.getInetAddresses();
                while (enIp.hasMoreElements()) {
                    InetAddress inet = enIp.nextElement();
                    if (!inet.isLoopbackAddress()
                            && (inet instanceof Inet4Address)) {
                        return inet.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "0";
    }

//    public static InetAddress getWifiIp() {
//        Context myContext = BaseApplication.getInstance().getApplicationContext();
//        if (myContext == null) {
//            throw new NullPointerException("Global context is null");
//        }
//        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
//        if (isWifiEnabled()) {
//            int ipAsInt = wifiMgr.getConnectionInfo().getIpAddress();
//            if (ipAsInt == 0) {
//                return null;
//            } else {
//                return Util.intToInet(ipAsInt);
//            }
//        } else {
//            return null;
//        }
//    }
//
//
//    　　// 取得wifi的ip地址
//            　　InetAddress address = FTPServerService.getWifiIp();
//    　　address.getHostAddress();

    public static boolean isWifiEnabled() {
        Context myContext = BaseApplication.getInstance().getApplicationContext();
        if (myContext == null) {
            throw new NullPointerException("Global context is null");
        }
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) myContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }

    public static String getWifiName(){
        WifiManager wifiMgr = (WifiManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        int wifiState = wifiMgr.getWifiState();
        WifiInfo info = wifiMgr.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        return wifiId;
    }


    /**
     * 统计点击次数
     */
    public static void clickCount(Context mContext,String eventId){
        HashMap<String,String> map = new HashMap<>();
        map.put("trigger", UserPreference.getROLE());
        MobclickAgent.onEvent(mContext, eventId, map);
    }


    /**
     * 换算万
     */

    public static String keepTwoDecimal(double youIn){
        double l = 10000;
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(youIn / l) + "万";
    }

}
