package com.shengui.app.android.shengui.android.ui.view;

import com.shengui.app.android.shengui.android.ui.activity.activity.sign.SignView;

/**
 * 签到日历控件数据适配器
 * Created by E.M on 2016/4/20.
 */
public abstract class CalendarAdapter {
    public abstract SignView.DayType getType(int dayOfMonth);
}
