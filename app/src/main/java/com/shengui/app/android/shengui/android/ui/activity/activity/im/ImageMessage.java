package com.shengui.app.android.shengui.android.ui.activity.activity.im;


import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;

import java.io.Serializable;

/**
 * @author : yingmu on 14-12-31.
 * @email : yingmu@mogujie.com.
 */
public class ImageMessage extends MessageEntity implements Serializable {

    /**
     * 本地保存的path
     */
    private String path = "";
    /**
     * 图片的网络地址
     */
    private String url = "";

    public ImageMessage() {
    }

    /**
     * 消息拆分的时候需要
     */
    public ImageMessage(MessageEntity entity) {
        /**父类的id*/
        messageType = entity.getDisplayType();
        if(entity.getUser_id()==null){
            userId = entity.getFromId();
        }else{
            userId = Integer.parseInt(entity.getUser_id());
        }
        sessionId = entity.getSession_id();
        to_user_id=entity.getTo_user_id();
        user_id=entity.getUser_id();
        status=entity.getStatus()+"";
        content = entity.getContent();
        token=entity.getToken();
        messageType = entity.getDisplayType();
        statu = entity.getStatus();
        sendTime = entity.getCreated();
        locId =entity.getLocId();
        headPath=entity.getHeadPath();
    }

    public static ImageMessage parseFromNet(ImageMessage text, MessageEntity entity) {
        text.setContent(entity.getContent());
        text.setStatus(Constant.MSG_SUCCESS);
        text.setDisplayType(Constant.SHOW_IMAGE_TYPE);
        text.setId(entity.getId());
        text.setCreated(entity.getCreated());
        text.setFromId(Integer.parseInt(entity.getUser_id()));
        text.setTo_user_id(entity.getTo_user_id());
        text.setUser_id(entity.getUser_id());
        text.setSessionKey(entity.getSession_id());
        text.setSession_id(entity.getSession_id());
//        text.setSessionKey(entity.getSessionKey());
        text.headPath=entity.getHeadPath();
        return text;
    }

    public static ImageMessage parseFromSend(String text, String sessionId) {
        ImageMessage textMessage = new ImageMessage();
        textMessage.setContent(text);
        textMessage.setRead(1);
        textMessage.setFromId(UserPreference.getUid());
        textMessage.setDisplayType(Constant.SHOW_IMAGE_TYPE);
        textMessage.setStatus(Constant.MSG_SENDING);
//        textMessage.setFromId(UserPreference.getUid());
        textMessage.setCreated((int) (System.currentTimeMillis() / 1000));
        textMessage.setSessionKey(sessionId);
//        long id = new MessageDao().insertRaw(textMessage);
//        textMessage.setLocId((int) id);
        return textMessage;
    }


    /**
     * -----------------------set/get------------------------
     */
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
