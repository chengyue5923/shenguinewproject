package com.shengui.app.android.shengui.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/16.
 */

public class imagesModel implements Serializable {
    String url_original;
    String url_big;
    String url_middle;
    String url_small;

    Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
}
