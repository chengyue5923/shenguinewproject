package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/11.
 */

public class TopicModel implements Serializable {

    String id;
    String user_id;
    String title;
    String member_num;
    String post_num;
    String last_post_id;
    String is_open;
    String create_time;
    String update_time;

    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", member_num='" + member_num + '\'' +
                ", post_num='" + post_num + '\'' +
                ", last_post_id='" + last_post_id + '\'' +
                ", is_open='" + is_open + '\'' +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLast_post_id() {
        return last_post_id;
    }

    public void setLast_post_id(String last_post_id) {
        this.last_post_id = last_post_id;
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
}
