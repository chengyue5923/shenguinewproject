package com.base.platform.utils.android;

import android.util.Log;


public class Logger {

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        Logger.TAG = TAG;
    }

    protected static  String TAG = "fhzc";
    public static boolean isDebug = false;

    public static void initDebug(boolean isdebug) {

        isDebug   =  isdebug;
    }


    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, buildMessage(msg));
    }

    public static void v(String msg, Throwable thr) {

        if (isDebug)
            Log.v(TAG, buildMessage(msg), thr);

    }

    public static void d(String msg) {

        if (isDebug)
            Log.d(TAG, buildMessage(msg));

    }

    public static void d(String msg, Throwable thr) {

        if (isDebug)
            Log.d(TAG, buildMessage(msg), thr);
    }


    public static void i(String msg) {

        if (isDebug)
            Log.i(TAG, buildMessage(msg));
    }

    public static void i(String msg, Throwable thr) {

        if (isDebug)
            Log.i(TAG, buildMessage(msg), thr);
    }

    public static void e(String msg) {

        if (isDebug)
//            Log.e(TAG, msg);
            Log.e(TAG, buildMessage(msg));
    }

    public static void ee(String msg) {


        Log.e(TAG, buildMessage(msg));
    }


    public static void w(String msg) {

        Log.w(TAG, buildMessage(msg));
    }


    public static void w(String msg, Throwable thr) {

        Log.w(TAG, buildMessage(msg), thr);
    }


    public static void w(Throwable thr) {

        Log.w(TAG, buildMessage(""), thr);
    }


    public static void e(String msg, Throwable thr) {

        if (isDebug)
            Log.e(TAG, buildMessage(msg), thr);
    }


    protected static String buildMessage(String msg) {

        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        String stackTrace = new StringBuilder().append(caller.getFileName()).append(caller.getMethodName()).append(caller.getLineNumber()).append(msg).toString();
//        return stackTrace;
        return caller.toString() + msg;

    }

}
