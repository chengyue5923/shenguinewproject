package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/24.
 */

public class ImageModel implements Serializable {


    String id;
    String img_1;
    String redirect_type;
    String redirect_url;
    String url_original;
    String url_big;
    String url_middle;
    String url_small;
    String city_id;
    String city_name;
    String name;
    String content;
    String img;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getUrl_original() {
        return url_original;
    }

    public void setUrl_original(String url_original) {
        this.url_original = url_original;
    }

    public String getUrl_big() {
        return url_big;
    }

    public void setUrl_big(String url_big) {
        this.url_big = url_big;
    }

    public String getUrl_middle() {
        return url_middle;
    }

    public void setUrl_middle(String url_middle) {
        this.url_middle = url_middle;
    }

    public String getUrl_small() {
        return url_small;
    }

    public void setUrl_small(String url_small) {
        this.url_small = url_small;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg_1() {
        return img_1;
    }

    public void setImg_1(String img_1) {
        this.img_1 = img_1;
    }

    public String getRedirect_type() {
        return redirect_type;
    }

    public void setRedirect_type(String redirect_type) {
        this.redirect_type = redirect_type;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}
