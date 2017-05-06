package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/2/9.
 */

public class CommentModel implements Serializable {

    String id;
    String post_id;
    String user_id;
    String review_user_id;
    String contents;
    String is_open;
    String dig_num;
    String review_num;
    String source;
    String os;
    String os_version;
    String sort_order;
    String create_time;
    String update_time;

    String is_dig;
    LoginResultModel userinfo;
    LoginResultModel review_user;
    public String getIs_dig() {
        return is_dig;
    }

    public void setIs_dig(String is_dig) {
        this.is_dig = is_dig;
    }

    public LoginResultModel getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(LoginResultModel userinfo) {
        this.userinfo = userinfo;
    }

    public LoginResultModel getReview_user() {
        return review_user;
    }

    public void setReview_user(LoginResultModel review_user) {
        this.review_user = review_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReview_user_id() {
        return review_user_id;
    }

    public void setReview_user_id(String review_user_id) {
        this.review_user_id = review_user_id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getDig_num() {
        return dig_num;
    }

    public void setDig_num(String dig_num) {
        this.dig_num = dig_num;
    }

    public String getReview_num() {
        return review_num;
    }

    public void setReview_num(String review_num) {
        this.review_num = review_num;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
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
