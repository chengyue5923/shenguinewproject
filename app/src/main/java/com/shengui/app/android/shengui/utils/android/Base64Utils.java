package com.shengui.app.android.shengui.utils.android;

import android.util.Base64;

/**
 * 项目名称：FunSchool
 * 类描述：Base64工具类
 * 创建人：Administrator
 * 创建时间：2016/7/20 14:32
 * 修改人：Administrator
 * 修改时间：2016/7/20 14:32
 * 修改备注：
 */
public class Base64Utils {
    private Base64Utils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /** Base64加密*/
    public static String encodeBASE64(String s) {
        if (s == null) return null;
        return new String(Base64.encode(s.getBytes(), Base64.NO_WRAP));
    }

    /** Base64解密*/
    public static String decodeBASE64(String s) {
        if (s == null) return null;
        return new String(Base64.decode(s.getBytes(), Base64.DEFAULT));
    }
}
