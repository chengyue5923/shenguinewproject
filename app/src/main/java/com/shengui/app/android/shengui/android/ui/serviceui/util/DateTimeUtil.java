package com.shengui.app.android.shengui.android.ui.serviceui.util;

import android.util.Log;

/**
 * Created by idea on 2016/10/12.
 */
public class DateTimeUtil {

    public static String durationMillisToString(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long secs = seconds % 60;

        String secsStr = secs < 10 ? "0" + secs : "" + secs;
        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        return minutesStr + ":" + secsStr;
    }

//    01:43.96
    public static long parseTimeStrToLong(String timeStr) {
        long minMillis=0;
        long secs=0;
        long secMillis=0;
        long oddMillis=0;
        try {
            minMillis = Long.valueOf(timeStr.substring(0, 2)) * 60 * 1000;
             secs = Long.valueOf(timeStr.substring(timeStr.indexOf(";") + 1, timeStr.indexOf(".")));
            Log.e("test", "parseTimeStrToLong: " + secs);
            secMillis = secs * 1000;
            String odd = timeStr.substring(timeStr.indexOf(".") + 1);
             oddMillis = Long.valueOf(odd) * 10;
        }catch (Exception e){
            e.printStackTrace();
        }

        return minMillis+secMillis+oddMillis;
    }
}
