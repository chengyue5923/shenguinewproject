package com.shengui.app.android.shengui.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/11.
 */

public class ActivityModel implements Serializable {

    String id;
    String cat_id;
    String title;
    String short_desc;
    String content;
    String  stime;
    String etime;
    String price;
    String sort_order;
    String cover;
    String status;
    String create_time;
    String update_time;
    String extend;
    List<ImageModel> focus;
    List<String> slider;

    public String getApply_num() {
        return apply_num;
    }

    public void setApply_num(String apply_num) {
        this.apply_num = apply_num;
    }

    String apply_num;

    String apply;

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public List<String> getSlider() {
        return slider;
    }

    public void setSlider(List<String> slider) {
        this.slider = slider;
    }

    public List<ImageModel> getFocus() {
        return focus;
    }

    public void setFocus(List<ImageModel> focus) {
        this.focus = focus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "ActivityModel{" +
                "id='" + id + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", title='" + title + '\'' +
                ", short_desc='" + short_desc + '\'' +
                ", content='" + content + '\'' +
                ", stime=" + stime +
                ", etime=" + etime +
                ", price='" + price + '\'' +
                ", sort_order='" + sort_order + '\'' +
                ", cover='" + cover + '\'' +
                ", status='" + status + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", extend='" + extend + '\'' +
                '}';
    }
}
