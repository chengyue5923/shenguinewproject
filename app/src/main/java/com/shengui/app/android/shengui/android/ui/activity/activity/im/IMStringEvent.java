package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import com.shengui.app.android.shengui.models.CityModel;

import java.util.List;

/**
 * Created by User on 2016/7/31.
 */
public class IMStringEvent {
    List<CityModel> count;

    int flag;
    public IMStringEvent(List<CityModel> count,int flag) {
        this.count = count;
        flag=flag;
    }
    String path;

    int notice=0;
    public IMStringEvent(int noticewe) {
        this.notice = noticewe;
        notice++;
    }
    public IMStringEvent(boolean noticewe) {
        setIsfresh(noticewe);
    }
    boolean isfresh=false;

    public boolean getIsfresh() {
        return isfresh;
    }

    public void setIsfresh(boolean isfresh) {
        this.isfresh = isfresh;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    String cityid;
    String cityname;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public IMStringEvent(String  cityid,String cityname) {
        this.count = count;
        this.cityid = cityid;
        this.cityname = cityname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public IMStringEvent(String  path) {
        this.path = path;
    }
    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<CityModel> getCount() {
        return count;
    }

    public void setCount(List<CityModel> count) {
        this.count = count;
    }
}
