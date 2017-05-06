package com.shengui.app.android.shengui.android.ui.activity.activity.im;


public class MessageEntity implements java.io.Serializable {
    protected String sessionId;
    protected int id;
    protected int userId;
    protected int read = 1; //0:未读 1：已读

    protected String content;

    protected String messageType;
    protected int statu;
    protected String duration;
    protected long sendTime;
    protected int locId;

    String is_read;
    String mid;
    String status;

    int  headPersonId;

    public int getHeadPersonId() {
        return headPersonId;
    }

    public void setHeadPersonId(int headPersonId) {
        this.headPersonId = headPersonId;
    }

    protected String headPath;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String message_type;
    String to_user_id;
    String token;
    String session_id;
    String user_id;
    String send_time;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }
    // KEEP FIELDS - put your custom fields here


    // KEEP FIELDS END

    public MessageEntity() {
    }

    public int getUserId() {
        return userId;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageEntity(int id, int fromId, String sessionKey, String content, String msgType, int created) {
        this.userId = fromId;
        this.sessionId = sessionKey;
        this.content = content;
        this.messageType = msgType;
        this.id = id;

        this.sendTime = created;

    }

    public int getFromId() {
        return userId;
    }

    public void setFromId(int fromId) {
        this.userId = fromId;
    }


    /**
     * Not-null value.
     */
    public String getSessionKey() {
        return sessionId;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setSessionKey(String sessionKey) {
        this.sessionId = sessionKey;
    }

    /**
     * Not-null value.
     */
    public String getContent() {
        return content;
    }

    /**
     * Not-null value; ensure this value is available before it is saved to the database.
     */
    public void setContent(String content) {
        this.content = content;
    }


    public String getDisplayType() {
        return messageType;
    }

    public void setDisplayType(String displayType) {
        this.messageType = displayType;
    }

    public int getStatus() {
        return statu;
    }

    public void setStatus(int status) {
        this.statu = status;
    }

    public int getCreated() {
        if ((sendTime + "").length() > 11) {
            return (int) (sendTime / 1000);
        } else {
            return (int) sendTime;
        }

    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "sessionId='" + sessionId + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                ", read=" + read +
                ", content='" + content + '\'' +
                ", messageType='" + messageType + '\'' +
                ", statu=" + statu +
                ", duration='" + duration + '\'' +
                ", sendTime=" + sendTime +
                ", locId=" + locId +
                ", is_read='" + is_read + '\'' +
                ", mid='" + mid + '\'' +
                ", status='" + status + '\'' +
                ", headPersonId=" + headPersonId +
                ", headPath='" + headPath + '\'' +
                ", message_type='" + message_type + '\'' +
                ", to_user_id='" + to_user_id + '\'' +
                ", token='" + token + '\'' +
                ", session_id='" + session_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", send_time='" + send_time + '\'' +
                ", getMessageTime='" + getMessageTime + '\'' +
                '}';
    }

    String getMessageTime;

    public String getGetMessageTime() {
        return getMessageTime;
    }

    public void setGetMessageTime(String getMessageTime) {
        this.getMessageTime = getMessageTime;
    }

    public void setCreated(int created) {
        this.sendTime = created;
    }


}
