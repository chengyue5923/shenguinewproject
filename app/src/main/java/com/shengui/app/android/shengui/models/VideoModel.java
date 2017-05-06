package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/23.
 */

public class VideoModel implements Serializable {

    String url;
    String cover;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
