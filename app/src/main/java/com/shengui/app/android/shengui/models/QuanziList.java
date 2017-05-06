package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/10.
 */

public class QuanziList implements Serializable {

    String id;
    String user_id;
    String avatar;
    String title;
    String lng;
    String lat;
    String city_id;
    String city_name;
    String desc;
    String member_audit;
    String member_num;
    String post_num;
    String is_organize;
    String is_open;
    String create_time;
    String update_time;
    String is_in;
    List<SectionModel> section;
    String is_public;
    String apply;
    String is_attention;

    public String getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(String is_attention) {
        this.is_attention = is_attention;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    List<TagModel> tag;


    public List<TagModel> getTag() {
        return tag;
    }

    public void setTag(List<TagModel> tag) {
        this.tag = tag;
    }

    public List<SectionModel> getSection() {
        return section;
    }

    public void setSection(List<SectionModel> section) {
        this.section = section;
    }

    public String getIs_in() {
        return is_in;
    }

    public void setIs_in(String is_in) {
        this.is_in = is_in;
    }

    boolean isCheck=false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMember_audit() {
        return member_audit;
    }

    public void setMember_audit(String member_audit) {
        this.member_audit = member_audit;
    }

    public String getMember_num() {
        return member_num;
    }

    public void setMember_num(String member_num) {
        this.member_num = member_num;
    }

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getIs_organize() {
        return is_organize;
    }

    public void setIs_organize(String is_organize) {
        this.is_organize = is_organize;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
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

    @Override
    public String toString() {
        return "QuanziList{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city_name='" + city_name + '\'' +
                ", desc='" + desc + '\'' +
                ", member_audit='" + member_audit + '\'' +
                ", member_num='" + member_num + '\'' +
                ", post_num='" + post_num + '\'' +
                ", is_organize='" + is_organize + '\'' +
                ", is_open='" + is_open + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", is_in='" + is_in + '\'' +
                ", section=" + section +
                ", is_public='" + is_public + '\'' +
                ", apply='" + apply + '\'' +
                ", is_attention='" + is_attention + '\'' +
                ", tag=" + tag +
                ", isCheck=" + isCheck +
                '}';
    }
}
