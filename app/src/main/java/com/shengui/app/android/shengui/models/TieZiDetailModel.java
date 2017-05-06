package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yanbo on 2016/7/28.
 */
public class TieZiDetailModel implements Serializable {
    String id;
    String user_id;
    String circle_id;
    String topic_id="";
    String section_id="";
    String content="";
    String imgs="";
    String videos="";
    String lng="";
    String lat="";
    String city_id="";
    String city_name="";

    String type="";
    String is_open="";
    String is_digest="";
    String is_top="";
    String comment_num="";
    String last_comment_time="";
    String create_time="";
    String dig_num="";
    String is_dig="";
    String is_favorite="";
    String is_attention="";
    String is_mixed;
    String mix_content;

    public String getIs_mixed() {
        return is_mixed;
    }

    public void setIs_mixed(String is_mixed) {
        this.is_mixed = is_mixed;
    }

    public String getMix_content() {
        return mix_content;
    }

    public void setMix_content(String mix_content) {
        this.mix_content = mix_content;
    }

    QuanziList circle_detail;

    List<TagModel> tag;

    public List<TagModel> getTag() {
        return tag;
    }

    public void setTag(List<TagModel> tag) {
        this.tag = tag;
    }

    public QuanziList getCircle_detail() {
        return circle_detail;
    }

    public void setCircle_detail(QuanziList circle_detail) {
        this.circle_detail = circle_detail;
    }

    List<SectionModel> circle_section;

    public List<SectionModel> getCircle_section() {
        return circle_section;
    }

    public void setCircle_section(List<SectionModel> circle_section) {
        this.circle_section = circle_section;
    }

    VideoModel video_info;

    LoginResultModel userinfo;

    public LoginResultModel getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(LoginResultModel userinfo) {
        this.userinfo = userinfo;
    }

    public VideoModel getVideo_info() {
        return video_info;
    }

    public void setVideo_info(VideoModel video_info) {
        this.video_info = video_info;
    }

    public String getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(String is_attention) {
        this.is_attention = is_attention;
    }

    List<imagesModel> images;

    public List<imagesModel> getImages() {
        return images;
    }

    public void setImages(List<imagesModel> images) {
        this.images = images;
    }

    public String getIs_dig() {
        return is_dig;
    }

    public void setIs_dig(String is_dig) {
        this.is_dig = is_dig;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getDig_num() {
        return dig_num;
    }

    public void setDig_num(String dig_num) {
        this.dig_num = dig_num;
    }

    String update_time="";
    String circle;
    String section="";
    String topic="";

    @Override
    public String toString() {
        return "TieZiDetailModel{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", circle_id='" + circle_id + '\'' +
                ", topic_id='" + topic_id + '\'' +
                ", section_id='" + section_id + '\'' +
                ", content='" + content + '\'' +
                ", imgs='" + imgs + '\'' +
                ", videos='" + videos + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", city_id='" + city_id + '\'' +
                ", city_name='" + city_name + '\'' +
                ", type='" + type + '\'' +
                ", is_open='" + is_open + '\'' +
                ", is_digest='" + is_digest + '\'' +
                ", is_top='" + is_top + '\'' +
                ", comment_num='" + comment_num + '\'' +
                ", last_comment_time='" + last_comment_time + '\'' +
                ", create_time='" + create_time + '\'' +
                ", dig_num='" + dig_num + '\'' +
                ", is_dig='" + is_dig + '\'' +
                ", is_favorite='" + is_favorite + '\'' +
                ", is_attention='" + is_attention + '\'' +
                ", circle_detail=" + circle_detail +
                ", tag=" + tag +
                ", circle_section=" + circle_section +
                ", video_info=" + video_info +
                ", userinfo=" + userinfo +
                ", images=" + images +
                ", update_time='" + update_time + '\'' +
                ", circle='" + circle + '\'' +
                ", section='" + section + '\'' +
                ", topic='" + topic + '\'' +
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

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getIs_digest() {
        return is_digest;
    }

    public void setIs_digest(String is_digest) {
        this.is_digest = is_digest;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getLast_comment_time() {
        return last_comment_time;
    }

    public void setLast_comment_time(String last_comment_time) {
        this.last_comment_time = last_comment_time;
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

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
