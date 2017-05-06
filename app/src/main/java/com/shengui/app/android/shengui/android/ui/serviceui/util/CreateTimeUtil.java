package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Administrator on 2017/3/25.
 */

public class CreateTimeUtil {


    public String mDateStringFormat = "MM-dd";
    public String mDateTimeStringFormat = "MM-dd HH:mm";

    public static String time(int timeData, int type) {
        String temp = "";
        String timeStr = timeData+"";
        try {
            long now = System.currentTimeMillis() / 1000;
            long publish = Long.parseLong(timeStr);
            long diff = now - publish;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;


            if (type == 1) {
                Calendar cald = GregorianCalendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
                long lt = new Long(timeStr);
                Date datetime = new Date(lt * 1000);
                cald.setTime(datetime);
                temp = simpleDateFormat.format(cald.getTime());
                return temp;
            }

            Calendar cald = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            long lt = new Long(timeStr);
            Date date = new Date(lt * 1000);

            cald.setTime(date);

            if (days > 0) {
                temp = simpleDateFormat.format(cald.getTime());
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else {
                temp = minutes + "分钟前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;

    }


    public static String getStandardDate(String timeStr, int type) {
        String temp = "";
        try {
            long now = System.currentTimeMillis() / 1000;
            long publish = Long.parseLong(timeStr);
            long diff = now - publish;
            long months = diff / (60 * 60 * 24 * 30);
            long days = diff / (60 * 60 * 24);
            long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
            long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / 60;


            if (type == 1) {
                Calendar cald = GregorianCalendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
                Log.e("test", "getStandardDate: " + timeStr);
                long lt = new Long(timeStr);
                Date datetime = new Date(lt * 1000);
                cald.setTime(datetime);
                temp = simpleDateFormat.format(cald.getTime());
                return temp;
            }
            Log.e("test", "getStandardDate: " + timeStr);

            Calendar cald = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            long lt = new Long(timeStr);
            Date date = new Date(lt * 1000);

            cald.setTime(date);

            if (days > 0) {
                temp = simpleDateFormat.format(cald.getTime());
            } else if (hours > 0) {
                temp = hours + "小时前";
            } else {
                temp = minutes + "分钟前";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
