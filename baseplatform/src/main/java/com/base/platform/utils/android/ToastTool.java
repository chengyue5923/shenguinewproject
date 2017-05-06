package com.base.platform.utils.android;


import android.widget.Toast;

import com.base.platform.android.application.BaseApplication;


/**
 * 关于toast的工具类
 *
 * @author linxi
 */
public class ToastTool {
    /**
     * 测试环境下的 toas 提示
     *
     * @param toast 提示内容
     */
    public static boolean showDev=false;
    public static void showDev(String toast) {
        if (BaseApplication.getInstance() == null) {
            return;
        }

        if (!showDev) {
            return;
        }

        showSystem(toast);

    }

    private static Toast getToast(String content) {
        Toast toast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), content, Toast.LENGTH_SHORT);
        return toast;

    }

    /**
     * 测试环境下的 toast 提示
     *
     * @param toastResId 提示内容id
     */
    public static void showDev(int toastResId) {
        if (!showDev) {
            return;
        }
        showSystem(toastResId);

    }

    /**
     * 提示内容
     *
     * @param toast 内容
     */
    public static void show(String toast) {

        showSystem(toast);

    }

    /**
     * 提示内容
     *
     * @param resId 内容id
     */

    public static void show(int resId) {
        showSystem(resId);
    }


    private static void showSystem(String toast) {
        getToast(toast).show();
    }

    private static void showSystem(int toastId) {
        getToast(BaseApplication.getInstance().getApplicationContext().getResources().getString(toastId)).show();
    }

}
