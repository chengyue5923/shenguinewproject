package com.shengui.app.android.shengui.models;

import java.io.Serializable;

/**
 * Created by yanbo on 16-7-15.
 */
public class SessionModel implements Serializable {
    String sessionId;
    int unReadCount;
    int fromId;
    int userId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
}
