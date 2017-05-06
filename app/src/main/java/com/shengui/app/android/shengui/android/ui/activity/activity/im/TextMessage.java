package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;

import java.io.Serializable;


/**
 * @author : yingmu on 14-12-31.
 * @email : yingmu@mogujie.com.
 */
public class TextMessage extends MessageEntity implements Serializable {

    public TextMessage(MessageEntity entity) {
        /**父类的id*/
        messageType = entity.getDisplayType();
        if(entity.getUser_id()==null){
            userId = entity.getFromId();
        }else{
            userId = Integer.parseInt(entity.getUser_id());
        }
        sessionId = entity.getSession_id();
        content = entity.getContent();
        statu = entity.getStatus();
        sendTime = entity.getCreated();
        locId = entity.getLocId();
        to_user_id=entity.getTo_user_id();
        user_id=entity.getUser_id();
        status=entity.getStatus()+"";
        is_read=entity.getIs_read();
        duration=entity.getDuration();
        token=entity.getToken();
        getMessageTime=entity.getMessageTime;
        headPath=entity.getHeadPath();
        headPersonId=entity.getHeadPersonId();
    }

    public TextMessage() {
    }

    public static TextMessage parseFromNet(TextMessage text, MessageEntity entity) {

        text.setStatus(Constant.MSG_SUCCESS);
        text.setDisplayType(Constant.SHOW_ORIGIN_TEXT_TYPE);
        text.setId(entity.getId());
        text.setCreated(entity.getCreated());
        text.setFromId(Integer.parseInt(entity.getUser_id()));
        text.setSessionKey(entity.getSession_id());
        text.setUser_id(entity.getUser_id());
        text.setTo_user_id(entity.getTo_user_id());
        text.setDuration(entity.getDuration());
        text.setFromId(Integer.parseInt(entity.getTo_user_id()));
        text.setGetMessageTime(entity.getGetMessageTime());
        text.setRead(1);
        text.headPersonId=entity.getHeadPersonId();
        text.headPath=entity.getHeadPath();
        text.setSession_id(entity.getSession_id());
        return text;
    }


    public static TextMessage parseFromSend(String text, String sessionId) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(text);
        textMessage.setRead(1);
        textMessage.setFromId(UserPreference.getUid());
        textMessage.setDisplayType(Constant.SHOW_ORIGIN_TEXT_TYPE);
        textMessage.setStatus(Constant.MSG_SENDING);
//        textMessage.setFromId(UserPreference.getUid());
        textMessage.setCreated((int) (System.currentTimeMillis() / 1000));
        textMessage.setSessionKey(sessionId);
//        long id = new MessageDao().insertRaw(textMessage);
//        textMessage.setLocId((int) id);
        return textMessage;
    }




}
