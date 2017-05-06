package com.base.platform.utils.java;



import com.base.platform.utils.android.Logger;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {

    /**
     * 处理字符串 中的null字符和 null的字符串
     *
     * @param str 需要处理的字符串
     * @return 已经处理的字符串
     */
    public static String toTrim(String str) {
        if (str == null) {
            return "";
        }
        if (str.trim().equalsIgnoreCase("null"))
            return "";
        return str.trim();
    }

    public static String captureName(String name) {
        //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }

    /**
     * @param txt
     * @return
     */
    public static String htmlEncode(String txt) {
        if (txt != null) {
            txt = replace(txt, "&", "&amp;");
            txt = replace(txt, "&amp;amp;", "&amp;");
            txt = replace(txt, "&amp;quot;", "&quot;");
            txt = replace(txt, "\"", "&quot;");
            txt = replace(txt, "&amp;lt;", "&lt;");
            txt = replace(txt, "<", "&lt;");
            txt = replace(txt, "&amp;gt;", "&gt;");
            txt = replace(txt, ">", "&gt;");
            txt = replace(txt, "&amp;nbsp;", "&nbsp;");
            txt = replace(txt, " ", "&nbsp;");
        }
        return txt;
    }

    /**
     * html格式的解码
     *
     * @param txt 传入的字符串
     * @return
     */
    public static String unHtmlEncode(String txt) {
        if (txt != null) {
            txt = replace(txt, "&amp;", "&");
            txt = replace(txt, "&quot;", "\"");
            txt = replace(txt, "&lt;", "<");
            txt = replace(txt, "&gt;", ">");
            txt = replace(txt, "&nbsp;", " ");
        }
        return txt;
    }

    /**
     * 字符串中 处理字符将其中一些字符替换
     *
     * @param str    需要处理的字符串
     * @param substr
     * @param restr
     * @return
     */
    public static String replace(String str, String substr, String restr) {
        if (str == null)
            return "";
        String[] tmp = split(str, substr, false);
        String returnstr = null;
        if (tmp.length != 0) {
            returnstr = tmp[0];
            for (int i = 0; i < tmp.length - 1; i++) {
                returnstr = dealNull(returnstr) + restr + tmp[(i + 1)];
            }
        }
        return dealNull(returnstr);
    }

    /**
     * @param str 需要处理的字符串 如果字符串为空那么返回""
     * @return 处理过的字符串
     */
    private static String dealNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 如果字符串为空那么返回""，不为空则返回其trim之后的字符串
     *
     * @param str 需要处理的字符串
     * @return 处理过的字符串
     */
    public static String notNullTrim(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * @param source  需要分割的资源串
     * @param div     需要分割的字符
     * @param include 是否包含字符
     * @return 分割的字符数组
     */
    public static String[] split(String source, String div, boolean include) {

        StringTokenizer tokens = new StringTokenizer(source, div, include);
        String[] result = new String[tokens.countTokens()];
        int i = 0;
        while (tokens.hasMoreTokens()) {
            result[i++] = tokens.nextToken();
        }
        return result;

    }


    /**
     * @param source 输入字符串
     * @return 是否是空字符串 如果空的话返回true
     */
    public static boolean isNullOrEmpty(String source) {
        if (source == null) {
            return true;
        }
        if (source.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static String formatBankCardNumber(String cardCode) {
        StringBuffer butter = new StringBuffer(cardCode);
        int index = 0;
        while (index < butter.length()) {
            if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                butter.insert(index, ' ');
            }
            index++;
        }


        return butter.toString();

    }

    /**
     * 判断是否是邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {

        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher matcher = p.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobile(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = p.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符串是否能转成int类型
     *
     * @param source 输入的需要转换的资源
     * @return 是否能转成int类型
     */
    public static boolean isIntent(String source) {
        boolean bool = true;
        try {
            Integer.parseInt(source);
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
        }
        return bool;

    }


    /**
     * 判断字符串是否能转成 浮点类型
     *
     * @param source 输入的字符串
     * @return 是否能转成 浮点类型
     */
    public static boolean isFloat(String source) {
        boolean bool = true;
        try {
            Float.parseFloat(source);
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
        }
        return bool;

    }


    public static boolean isInteger(String  source){
        boolean bool = true;
        try {
            Integer.parseInt(source);
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
        }
        return bool;
    }
    /**
     * 判断字符串是否能转成 double类型
     *
     * @param source 输入的字符串
     * @return 是否能转成 double类型
     */
    public static boolean isDouble(String source) {
        boolean bool = true;
        try {
            Double.parseDouble(source);
        } catch (Exception e) {
            // TODO: handle exception
            bool = false;
        }
        return bool;

    }

    /**
     * 字符串逆序
     *
     * @param reslut 需要逆序的字符串
     * @return 返回 逆序的字符串
     */
    public static String reverse(String reslut) {
        StringBuffer sb = new StringBuffer(reslut);
        sb.reverse();
        return sb.toString();
    }

    /**
     * 判断字符是否是汉字
     *
     * @param c 字符
     * @return 是否是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 判断字符是否是全角
     *
     * @param s 字符
     * @return 是否是全角
     */
    public static boolean checkWideChar(String s) {
        try {
            if (s == null || s.equals(""))
                return false;
            byte abyte0[] = s.getBytes("EUC-JP");
            for (int i = 0; i < abyte0.length; i++) {
                byte byte0 = abyte0[i];
                if (byte0 >= 0 && byte0 <= 127 || byte0 == -114)
                    if (byte0 == 63) {
                        for (int j = 0; j < s.length(); j++) {
                            char c = s.charAt(j);
                            if (c == '?')
                                return false;
                        }

                    } else {
                        return false;
                    }
            }

            return true;
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            return false;
        }
    }


    /**
     * 汉语文字 全角 半角 转换
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String backNotNullString(String value) {

        Logger.e(value);
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }


    public  static String removeMoreSpace(String res){
        if (isNullOrEmpty(res)){
            return "";
        }
        String test = res.replaceAll("\\s{1,}", " ");
        return test;
    }

    public  static  void main(String[] st){
        String str="abc      def      (black    )";
        System.out.println(removeMoreSpace(str));
    }

    public static boolean isEqual(String a, String b) {
        return a.equals(b);
    }
}
