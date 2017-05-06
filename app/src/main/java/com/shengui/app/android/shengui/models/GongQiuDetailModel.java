package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/10.
 */

public class GongQiuDetailModel implements Serializable {

    String id;
    String user_id;
    String variety_id;
    String title;
    String contents;
    String imgs;
    String type;
    String lng;
    String lat;
    String location;
    String province;
    String city;
    String contact_user;
    String contact_mobile;
    String view_num;
    String status;
    String create_time;
    String variety;
    String is_favorite;
    String cover;
    String fav_type;
    String distance;
    LoginResultModel userinfo;
    String pub_time;


    String city_name;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    String avatar;



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LoginResultModel getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(LoginResultModel userinfo) {
        this.userinfo = userinfo;
    }

    VideoModel video_info;

    public VideoModel getVideo_info() {
        return video_info;
    }

    public void setVideo_info(VideoModel video_info) {
        this.video_info = video_info;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    String cover_original;

    List<ImageModel> images;

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    public String getFav_type() {
        return fav_type;
    }

    public void setFav_type(String fav_type) {
        this.fav_type = fav_type;
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

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    @Override
    public String toString() {
        return "GongQiuDetailModel{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", variety_id='" + variety_id + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", imgs='" + imgs + '\'' +
                ", type='" + type + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", location='" + location + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", contact_user='" + contact_user + '\'' +
                ", contact_mobile='" + contact_mobile + '\'' +
                ", view_num='" + view_num + '\'' +
                ", status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
