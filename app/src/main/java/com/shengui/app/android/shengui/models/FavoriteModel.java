package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/12.
 */

public class FavoriteModel implements Serializable {
    String id;
    String user_id;
    String variety_id;
    String title;
    String contents;
    String imgs;
    String type;
    String lng;
    String lat;
    String city_name;
    String province;
    String city_idl;
    String contact_user;
    String contact_mobile;
    String view_num;
    String status;
    String cover;
    String cover_original;
    String create_time;
    String update_time;
    String fav_type;
    String content;

    QuanziList circle_detail;

    public QuanziList getCircle_detail() {
        return circle_detail;
    }

    public void setCircle_detail(QuanziList circle_detail) {
        this.circle_detail = circle_detail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFav_type() {
        return fav_type;
    }

    public void setFav_type(String fav_type) {
        this.fav_type = fav_type;
    }

    @Override
    public String toString() {
        return "FavoriteModel{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", variety_id='" + variety_id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", imgs='" + imgs + '\'' +
                ", type='" + type + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", city_name='" + city_name + '\'' +
                ", province='" + province + '\'' +
                ", city_idl='" + city_idl + '\'' +
                ", contact_user='" + contact_user + '\'' +
                ", contact_mobile='" + contact_mobile + '\'' +
                ", view_num='" + view_num + '\'' +
                ", status='" + status + '\'' +
                ", cover='" + cover + '\'' +
                ", cover_original='" + cover_original + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVariety_id() {
        return variety_id;
    }

    public void setVariety_id(String variety_id) {
        this.variety_id = variety_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity_idl() {
        return city_idl;
    }

    public void setCity_idl(String city_idl) {
        this.city_idl = city_idl;
    }

    public String getContact_user() {
        return contact_user;
    }

    public void setContact_user(String contact_user) {
        this.contact_user = contact_user;
    }

    public String getContact_mobile() {
        return contact_mobile;
    }

    public void setContact_mobile(String contact_mobile) {
        this.contact_mobile = contact_mobile;
    }

    public String getView_num() {
        return view_num;
    }

    public void setView_num(String view_num) {
        this.view_num = view_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover_original() {
        return cover_original;
    }

    public void setCover_original(String cover_original) {
        this.cover_original = cover_original;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
