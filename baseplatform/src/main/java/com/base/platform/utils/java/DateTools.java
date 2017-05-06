package com.base.platform.utils.java;


import com.base.platform.utils.android.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 通用工具
 * 日期时间工具
 *
 * @version 1.0 以下所有变量长度未作说明的则由java定义决定,默认情况下，返回null或-1为失败
 */
public class DateTools {
    public static final String DATE_FORMAT_STYLE_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_STYLE_2 = "yyyy-MM-dd HH-mm-ss";
    public static final String DATE_FORMAT_STYLE_3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_STYLE_4 = "MM-dd";
    public static final String DATE_FORMAT_STYLE_5 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_STYLE_6 = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_FORMAT_STYLE_7 = "dd";
    public static final String DATE_FORMAT_STYLE_8 = "HH:mm:ss";
    public static final String DATE_FORMAT_STYLE_9 = "yyyy/MM/dd";

    public static final String DATE_FORMAT_STYLE_10 = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_STYLE_11 = "yyyyMMdd";
    public static final String DATE_FORMAT_STYLE_12 = "HHmmss";
    public static final String DATE_FORMAT_STYLE_13 = "yyyy";

    public static final String DATE_FORMAT_STYLE_14 = "MM";

    public static final String DATE_FORMAT_STYLE_15 = "HH";
    public static final String DATE_FORMAT_STYLE_16 = "yyyy.MM.dd";

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private static final DateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");


    public static String getTime(String Usertimer){
        String time="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try{
            d=sdf.parse(Usertimer);
            long l=d.getTime();
            time=String.valueOf(l);
//            time=str.substring(0,10);

        }catch (Exception e){
            Logger.e("exceptiopnm"+e.getMessage());
        }

        return time;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }
    /**
     * 获得当前时间并且以String类型返回。
     *
     * @return 返回的时间格式yyyyMMddHHmmss。
     */
    public static String getDate() {
        return dateFormatterOfDateTimeForNow("yyyyMMddHHmmss");
    }

    /**
     * 获得当前时间。时间格式根据fmt进行格式化
     *
     * @param fmt 日期格式
     * @return 返回的时间格式根据fmt格式生成
     */
    public static String dateFormatterOfDateTimeForNow(String fmt) {
        Date myDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
        return sDateformat.format(myDate).toString();
    }
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }

    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    public static String dateFormatterOfDateTime(String fmt, long time) {
        Date myDate = new Date(time);
        SimpleDateFormat sDateformat = new SimpleDateFormat(fmt);
        return sDateformat.format(myDate).toString();
    }
    public static String timestamp2Date(String str_num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new Date(toLong(str_num)));
            return date;
    }
    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }
    public static Calendar getCal(String strdate, String fmt) {
        Calendar cal = null;
        try {
            if ((strdate != null) && (fmt != null)) {
                TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

                SimpleDateFormat nowDate = new SimpleDateFormat(fmt);
                Date d = nowDate.parse(strdate, new ParsePosition(0));
                if (d != null) {
                    cal = Calendar.getInstance();
                    cal.clear();
                    cal.setTime(d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return cal;
    }

    /**
     * 计算 当前日期是本年的第几周
     *
     * @param strdate 计算日期
     * @param fmt     日期格式
     * @return strdate说在年份中有几周，如果strdate的格式为yyyy则默认为当年的1月1日
     */
    public static int getWeekOfYear(String strdate, String fmt) {
        int ret = -1;
        try {
            if ((strdate != null) && (fmt != null)) {

                Calendar cal = getCal(strdate, fmt);
                if (cal != null) {

                    ret = cal.get(3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 计算给定日期所在周的全部日期
     *
     * @param strdate 指定日期
     * @param oldfmt  自定日期格式
     * @param newfmt  输出格式
     * @return 结果数组形式输出所在周的日期。指定日期的格式为yyyy或yyyyMM将计算出当年第一周或当月第一周的日期
     */
    public static String[] getWeekDay(String strdate, String oldfmt, String newfmt) {
        String[] weekday = new String[7];
        try {
            if ((strdate != null) && (oldfmt != null) && (newfmt != null)) {
                Calendar cal = getCal(strdate, oldfmt);
                if (cal != null) {
                    int dayOfWeek = cal.get(7);

                    dayOfWeek = -dayOfWeek + 2;
                    if (dayOfWeek > 0) {
                        dayOfWeek = -6;
                    }
                    cal.add(5, dayOfWeek);
                    SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
                    weekday[0] = sdf.format(cal.getTime());
                    for (int i = 1; i < 7; i++) {
                        cal.add(5, 1);
                        weekday[i] = sdf.format(cal.getTime());
                    }
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            iobe.printStackTrace();
        }
        return weekday;
    }

    /**
     * 计算给定周内的全部日期
     *
     * @param year   年
     * @param week   给定第几周
     * @param newfmt 返回值格式
     * @return 结果数组形式输出所在周的日期。
     */
    public static String[] getWeekDate(String year, int week, String newfmt) {
        String[] jweekday = new String[7];
        try {
            if ((year != null) && (year.length() == 4) && (week > 0)
                    && (newfmt != null)) {
                Calendar cal = getCal(year + "0101", "yyyyMMdd");
                if (cal != null) {
                    week--;
                    cal.add(5, week * 7 - cal.get(7) + 2);
                    SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
                    jweekday[0] = sdf.format(cal.getTime());
                    for (int i = 1; i < 7; i++) {
                        cal.add(5, 1);
                        jweekday[i] = sdf.format(cal.getTime());
                    }
                }
            }
        } catch (IndexOutOfBoundsException iobe) {
            iobe.printStackTrace();
        }
        return jweekday;
    }

    /**
     * 计算指定日期是星期几
     *
     * @param strdate 指定日期
     * @param oldfmt  指定日期格式
     * @param fmt     输出格式
     * @return 返回结果为fmt+结果。
     */
    public static String getDayOfWeek(String strdate, String oldfmt, String fmt) {
        String sWeek = null;
        try {
            if ((strdate != null) && (oldfmt != null) && (fmt != null)) {
                Calendar cal = getCal(strdate, oldfmt);
                if (cal != null) {
                    int iWeek = cal.get(7);
                    sWeek = fmt + (iWeek - 1 == 0 ? 7 : iWeek - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sWeek;
    }

    /**
     * 计算指定年共有多少周
     *
     * @param year 指定年 格式yyyy
     * @return 返回周数
     */
    public static int getWeekNum(String year) {
        int weeknum = -1;
        try {
            if (year != null) {
                Calendar cal = getCal(year + "1231", "yyyyMMdd");
                if (cal != null) {
                    if (cal.get(3) == 1)
                        cal.add(5, -7);
                    weeknum = cal.get(3);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeknum;
    }

    /**
     * 计算两个给定的时间之差
     *
     * @param startdate 开始日期
     * @param enddate   结束日期
     * @param fmt       日期格式
     * @param refmt     返回值格式   ms毫秒 s秒 m分 h小时 d天
     * @return 返回值
     */
    public static String cntTimeDifference(String startdate, String enddate,
                                           String fmt, String refmt) {
        String ret = null;
        try {
            if ((startdate != null) && (enddate != null) && (fmt != null)
                    && (refmt != null)) {
                Date scal = getCal(startdate, fmt).getTime();
                Date ecal = getCal(enddate, fmt).getTime();
                if ((scal == null) || (ecal == null)) {
                    return null;
                } else {
                    long diffMillis = ecal.getTime() - scal.getTime();

                    long diffSecs = diffMillis / 1000L;

                    long diffMins = diffMillis / 60000L;

                    long diffHours = diffMillis / 3600000L;

                    long diffDays = diffMillis / 86400000L;

                    if (refmt.equals("ms"))
                        ret = Long.toString(diffMillis);
                    else if (refmt.equals("s"))
                        ret = Long.toString(diffSecs);
                    else if (refmt.equals("m"))
                        ret = Long.toString(diffMins);
                    else if (refmt.equals("h"))
                        ret = Long.toString(diffHours);
                    else if (refmt.equals("d"))
                        ret = Long.toString(diffDays);
                    else
                        ret = Long.toString(diffHours);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 计算指定日期经过多少小时后的日期
     *
     * @param deftime  自定日期
     * @param oldfmt   日期格式
     * @param timediff 小时为单位
     * @param newfmt   返回值的格式
     * @return 返回日期，timediff >0向前计算，timediff<0向后计算
     */
    public static String getBeforeTime(String deftime, String oldfmt, int timediff,
                                       String newfmt) {
        String rq = null;
        try {
            if ((deftime != null) && (!deftime.equals(""))) {
                Calendar cal = getCal(deftime, oldfmt);
                if (cal != null) {
                    cal.add(12, -timediff * 60);
                    SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
                    rq = sdf.format(cal.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rq;
    }

    /**
     * 计算指定日期经过多少月后的日期
     *
     * @param deftime  自定日期
     * @param oldfmt   日期格式
     * @param timediff 月为单位
     * @param newfmt   返回值的格式
     * @return 返回日期，timediff >0向前计算，timediff<0向后计算
     */
    public static String getBeforeTimeByM(String deftime, String oldfmt, int timediff,
                                          String newfmt) {
        String rq = null;
        try {
            if ((deftime != null) && (!deftime.equals(""))) {
                Calendar cal = getCal(deftime, oldfmt);
                if (cal != null) {
                    cal.add(2, -timediff);
                    SimpleDateFormat sdf = new SimpleDateFormat(newfmt);
                    rq = sdf.format(cal.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rq;

    }

    /**
     * 日期格式化
     *
     * @param mydate 日期
     * @param oldfmt 旧格式
     * @param newfmt 新格式
     * @return 返回值的格式根据newfmt根式返回
     */
    public static String dataFormatterOfDateTime(String mydate, String oldfmt, String newfmt) {
        String restr = null;
        try {
            if ((mydate != null) && (oldfmt != null) && (newfmt != null)) {
                SimpleDateFormat newDate = new SimpleDateFormat(newfmt);
                Calendar cal = getCal(mydate, oldfmt);
                if (cal != null) {
                    restr = newDate.format(cal.getTime());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restr;

    }

    /**
     * @param yyyy 指定多少年
     * @param MM   指定 当年的多少月
     * @return 返回 数组  index0 是这个 1 是年+月+这个月的天数
     */
    public static String[] getMonthDay(String yyyy, String MM) {
        if (yyyy == null || MM == null || yyyy.equals("") || MM.equals("")) {
            return null;
        }
        int year = Integer.parseInt(yyyy);
        int month = Integer.parseInt(MM);
        String[] rvalue = new String[2];

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            rvalue[0] = "31";
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            rvalue[0] = "30";
        } else if (month == 2) {
            if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                rvalue[0] = "29";
            } else {
                rvalue[0] = "28";
            }
        } else {
            return null;
        }
        rvalue[1] = yyyy + ((month < 10) ? "0" + month : "" + month) + rvalue[0];

        return rvalue;
    }

    public static String formatDateWithSecondSince1970(String fomat, long date1970) {
        Date myDate = new Date(date1970);
        SimpleDateFormat sDateformat = new SimpleDateFormat(fomat);
        return sDateformat.format(myDate).toString();
    }
    public static String formatDateWithSecondSince1970(String fomat, long date1970,boolean flag) {
        Date myDate = new Date(date1970);
//        SimpleDateFormat sDateformat = new SimpleDateFormat(fomat);
        int  year=myDate.getYear();
        int  month=myDate.getMonth();
        int  day=myDate.getDay();
        return year+"."+month+"."+day;
    }

    public static String dateIntervalOfDescribe(String time, String fromat) {
        String string = cntTimeDifference(time, dateFormatterOfDateTimeForNow(fromat), fromat, "d");
        int n = Integer.parseInt(string);
        int m = n / 30;
        if (1 < Math.abs(n) && Math.abs(n) < 2) {
            if (n > 0) {
                return "前一天";
            } else {
                return "后一天";
            }
        }
        if (n == 0) {
            return "今天";
        }
        if (2 <= Math.abs(n) && Math.abs(n) <= 30) {
            if (n > 0) {
                return "上个月";
            } else {
                return "下个月";
            }
        }
        if (Math.abs(n) > 30 && Math.abs(n) <= 365) {
            if (n > 0) {
                return m + "个月前";
            } else {
                return m + "个月后";
            }
        }
        if (Math.abs(n) > 365) {
            if (n > 0) {
                return "1年前";
            } else {
                return "1年后";
            }
        }
        return "";
    }

    public static String getNewDate(String oldType) {
        String strDate = oldType;
        String pat1 = "yyyy-MM-dd HH:mm";
        String pat2 = "MM月dd日 HH:mm";
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1); // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2); // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(strDate); // 将给定的字符串中的日期提取出来
        } catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace(); // 打印异常信息
        }
        return sdf2.format(d);
    }

    public static String getNewDateFormat(String oldType) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT_STYLE_5); // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(oldType); // 将给定的字符串中的日期提取出来

        } catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace(); // 打印异常信息
        }
        return friendlyTime(d.getTime());
    }

    public static String friendlyTime(long timeInMillis) {
        Date time = new Date(timeInMillis);

        if (time == null)
            return "Unknown";

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        String curDate = format3.format(cal.getTime());
        String paramDate = format3.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - timeInMillis) / 3600000);
            if (hour == 0)
                if ((cal.getTimeInMillis() - timeInMillis) / 60000 < 1) {
                    Logger.e(cal.getTimeInMillis() - timeInMillis + "===========");
                    Logger.e((cal.getTimeInMillis() - timeInMillis) / 60000 + "===========");
                    ftime = "刚刚";
                } else {
                    ftime = String.valueOf((cal.getTimeInMillis() - timeInMillis) / 60000) + "分钟前";
                }
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = timeInMillis / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - timeInMillis) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - timeInMillis) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = simpleDateFormat.format(time);
        }
        return ftime;
    }

    public static int friendlyDayTime(long timeInMillis) {
        Date time = new Date(timeInMillis);

        if (time == null)
            return 0;

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        String curDate = format3.format(cal.getTime());
        String paramDate = format3.format(time);
        if (curDate.equals(paramDate)) {
            return 0;
        }

        long lt = timeInMillis / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int)(lt - ct);

        return days;
    }

    public static int todayDifferDay(long timeInMillis) {
        Date time = new Date(timeInMillis);

        if (time == null)
            return 0;

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        String curDate = format3.format(cal.getTime());
        String paramDate = format3.format(time);
        if (curDate.equals(paramDate)) {
            return 0;
        }

        long lt = timeInMillis / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);

        return days;
    }

    public static int differDayTime(long day1,long day2) {
        Date time1 = new Date(day1);
        Date time2 = new Date(day2);

        if (day1 == 0||day1==0)
            return 0;




        String date1 = format3.format(time1);
        String date2 = format3.format(time2);
        if (date1.equals(date2)) {
            return 0;
        }
        long lt = day1 / 86400000;
        long ct = day2 / 86400000;
        int days = (int) Math.abs(ct - lt);

        return days;
    }

    // 将字符串转为时间戳
    public static long getTime(String time, String fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        Date d;
        try {
            d = sdf.parse(time);
            long l = d.getTime();
            return l;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String formatPhotoDate(String path){
        File file = new File(path);
        if(file.exists()){
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }
    public static String formatPhotoDate(long time){
        return timeFormat(time, "yyyy-MM-dd");
    }
    public static String timeFormat(long timeMillis, String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }
}
