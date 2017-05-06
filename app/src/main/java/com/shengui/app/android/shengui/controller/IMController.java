package com.shengui.app.android.shengui.controller;

import android.app.Activity;
import android.util.Log;

import com.base.framwork.cach.preferce.PreferceManager;
import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.ContactModel;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMEvent;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageEntity;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.dao.ContactDao;
import com.shengui.app.android.shengui.dao.MessageDao;
import com.shengui.app.android.shengui.dao.SessionDao;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.MessageModel;
import com.shengui.app.android.shengui.models.PullMessageMapModel;
import com.shengui.app.android.shengui.models.SessionModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 2016/7/14.
 */
public class IMController {
    private static IMController instance;
    private ScheduledExecutorService mSyncScheduler;
    private ScheduledExecutorService mLocationSyncScheduler;

    public static IMController getInstance() {
        if (instance == null)
            instance = new IMController();
        return instance;
    }

    public void oneshotSync(final Activity context) {
        if (mSyncScheduler != null) {
            return;
        }
        if(mLocationSyncScheduler!=null){
            return;
        }
        mSyncScheduler = Executors.newSingleThreadScheduledExecutor();
        mLocationSyncScheduler=Executors.newSingleThreadScheduledExecutor();
        final Runnable syncRequest = new Runnable() {

            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            pull(Long.parseLong(PreferceManager.getInsance().getValueBYkey("version"+ UserPreference.getTOKEN())));
                            pullMessage(PreferceManager.getInsance().getValueBYkey("version"+UserPreference.getTOKEN()));
//                            updateLocation();
                        } catch (Exception e) {
                            pull(0);
                        }

                    }
                });
            }
        };
        final Runnable LocationRequest = new Runnable() {

            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            updateLocation();
                        } catch (Exception e) {

                        }

                    }
                });
            }
        };
        mSyncScheduler.scheduleAtFixedRate(syncRequest, 0, Constant.MESSSAGE_SYNCING_INTERVAL, TimeUnit.SECONDS);
        mLocationSyncScheduler.scheduleAtFixedRate(LocationRequest, 0, Constant.MESSSAGE_SLOCATION_INTERVAL, TimeUnit.MINUTES);
    }

    public void updateLocation(){
        MineInfoController.getInstance().loginlocation(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {

            }

            @Override
            public void onConnectEnd() {

            }

            @Override
            public void onFail(Exception e) {

            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

                Logger.e("updateLocation"+o.toString());
            }
        });

    }
    public void pullMessage(String mesage){
            try {
                ConnectTool.httpRequest(HttpConfig.ImgetDetailmessage, new MapBuilder<String, Object>()
                        .add("m_id",mesage)
                        .add("token",UserPreference.getTOKEN())
                        .getMap(), new PullCallBack(), MessageModel.class);
            } catch (Exception e) {
        }

    }



    /**神龟发布消息
     *content	string	是	聊天内容
     token	string	是	用户token
     to_user_id	int	是	发送消息对象id
     message_type	string	是	消息类型
     session_id	string	否	聊天组id，空则生成新会话
     * @param callBack
     */

    public void PushMessage(ViewNetCallBack callBack,String content,String token,int to_user_id,String message_type,String session_id,int duration) {
        try {
            ConnectTool.httpRequest(HttpConfig.Impub, new MapBuilder<String, Object>()
                    .add("content",content)
                    .add("to_user_id",to_user_id)
                    .add("message_type",message_type)
                    .add("session_id",session_id)
                    .add("token",token)
                    .add("duration",duration)
                    .getMap(), callBack, MessageEntity.class);
        } catch (Exception e) {
        }

    }
    /**神龟发布消息
     *content	string	是	聊天内容
     token	string	是	用户token
     to_user_id	int	是	发送消息对象id
     message_type	string	是	消息类型
     session_id	string	否	聊天组id，空则生成新会话
     * @param callBack
     */

    public void PushImageMessage(ViewNetCallBack callBack,String content,String token,int to_user_id,String message_type,String session_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.Impub, new MapBuilder<String, Object>()
                    .add("content",content)
                    .add("to_user_id",to_user_id)
                    .add("message_type",message_type)
                    .add("session_id",session_id)
                    .add("token",token)
                    .getMap(), callBack, MessageEntity.class);
        } catch (Exception e) {
        }

    }
    /**
     * 获取详细消息内容接口
     * token	string	是	用户token
     i d	int	是	消息id
     * @param callBack
     * @param token
     * @param id
     */
    public void getDetailmessage(ViewNetCallBack callBack,String token,int id) {
        try {
            ConnectTool.httpRequest(HttpConfig.ImgetDetailmessage, new MapBuilder<String, Object>()
                    .add("id",id)
                    .add("token",token)
                    .getMap(), callBack, MessageModel.class);
        } catch (Exception e) {
        }

    }


    /**token	string	是	用户token
     session_id	string	是	会话session_id
     *获取消息历史
     * @param callBack
     * @param token
     * @param session_id
     */
    public void getHistorymessage(ViewNetCallBack callBack,String token,int session_id) {
        try {
            ConnectTool.httpRequest(HttpConfig.Impubgethistory, new MapBuilder<String, Object>()
                    .add("session_id",session_id)
                    .add("token",token)
                    .getMap(), callBack, MessageModel.class);
        } catch (Exception e) {
        }

    }



    /**
     * 轮询
     *
     * @param version
     */
    public void pull(long version) {
        try {
            ConnectTool.httpRequest(
                    HttpConfig.yapull,
                    new MapBuilder<String, Object>()
                            .add("version", version)
                            .add("userId", UserPreference.getTOKEN())
                            .getMap(), new PullCallBack(), String.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 发送消息
     *
     * @param toUserId
     * @param text
     * @param type
     * @param duration
     */
    public void publish(ViewNetCallBack callBack, int toUserId, String text, String type, int duration) {
        try {
            ConnectTool.httpRequest(
                    HttpConfig.publish,
                    new MapBuilder<String, Object>()
                            .add("toUserId", toUserId)
                            .add("userId", UserPreference.getUid())
                            .add("text", text)
                            .add("type", type)
                            .add("duration", duration)
                            .getMap(), callBack, String.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 发送消息
     *
     * @param toUserId
     * @param text
     * @param type
     * @param duration
     */
    public void publishImage(ViewNetCallBack callBack, int toUserId, String text, String type, int duration, File file) {
        try {
            ConnectTool.httpRequest(
                    HttpConfig.publish,
                    new MapBuilder<String, Object>()
                            .add("toUserId", toUserId)
                            .add("userId", UserPreference.getUid())
                            .add("text", text)
                            .add("type", type)
                            .add("duration", duration)
                            .add("file", file)
                            .getMap(), callBack, MessageEntity.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }

    public void audioContent(ViewNetCallBack callBack, int id) {
        try {
            ConnectTool.httpRequest(
                    HttpConfig.audioContent,
                    new MapBuilder<String, Object>()
                            .add("messageId", id)
                            .getMap(), callBack, String.class);
        } catch (Exception e) {
            Logger.e(e.getLocalizedMessage(), e);
        }
    }

    private class PullCallBack implements ViewNetCallBack {
        @Override
        public void onConnectStart() {

        }

        @Override
        public void onConnectEnd() {

        }

        @Override
        public void onFail(Exception e) {

        }

        @Override
        public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
            Logger.e("logger-Serializable----"+result.toString());

            List<MessageModel> model = (List<MessageModel>) result;
            if(model.size()!=0){
                new Thread(new DataRunnable(model)).start();
            }
        }
    }

    class DataRunnable implements Runnable {
        List<MessageModel> model;

        public DataRunnable( List<MessageModel> model) {
            this.model = model;
        }
        @Override
        public void run() {
            MessageDao dao = new MessageDao();
            ContactDao contactDao = new ContactDao();
            SessionDao sessionDao = new SessionDao();
            Logger.e("saveValueBYkegetModely"+String.valueOf(model));
            if(model.size()!=0){
                PreferceManager.getInsance().saveValueBYkey("version"+UserPreference.getTOKEN(),String.valueOf(model.get(model.size()-1).getId()));
            }
            int messageCount = 0;
            messageCount =model.size();
            for (MessageModel messageModel : model) {
                if(!messageModel.getUser_id().equals(UserPreference.getUid()+"")){
                    //                for (MessageEntity messageEntity : messageModel.get()) {
                    MessageEntity messageEntity=new MessageEntity();
                    messageEntity.setSession_id(messageModel.getSession_id());
                    messageEntity.setId(Integer.parseInt(messageModel.getId()));
                    messageEntity.setUser_id(messageModel.getUser_id());
                    messageEntity.setUserId(Integer.parseInt(messageModel.getUser_id()));
                    messageEntity.setTo_user_id(messageModel.getTo_user_id());
                    messageEntity.setDisplayType(messageModel.getMessage_type());
                    messageEntity.setContent(messageModel.getContent());
                    messageEntity.setGetMessageTime(messageModel.getSend_time());
                    messageEntity.setRead(0);
                    messageEntity.setDuration(messageModel.getDuration());
                    messageEntity.setStatus(Constant.MSG_SUCCESS);
                    dao.insert(messageEntity);

                    List<SessionModel> lidtt=sessionDao.getAllSession();
                    boolean isNohave=true;
                       for(SessionModel see:lidtt){
                           Logger.e("wwwqqqq"+see.getFromId()+see.getUserId()+see.getSessionId());
                           Logger.e("falge"+(see.getUserId()==Integer.parseInt(messageModel.getUser_id())));
                           Logger.e("falgeeee"+(see.getFromId()==Integer.parseInt(messageModel.getTo_user_id())));
                           if(see.getUserId()==Integer.parseInt(messageModel.getUser_id())&&see.getFromId()==Integer.parseInt(messageModel.getTo_user_id())){
                               Logger.e("wwwqqqqwwwwww"+see.getFromId()+see.getUserId()+see.getSessionId());
                               isNohave=false;
                           }
                           if(see.getFromId()==Integer.parseInt(messageModel.getUser_id())&&see.getUserId()==Integer.parseInt(messageModel.getTo_user_id())){
                               Logger.e("wqwwwwww"+see.getFromId()+see.getUserId()+see.getSessionId());
                               isNohave=false;
                           }
                       }
                    if(isNohave){
                        SessionModel model1 = new SessionModel();
                        Logger.e("ffffff"+messageModel.getUser_id()+messageModel.getTo_user_id()+messageModel.getSession_id());
                        model1.setUserId(Integer.parseInt(messageModel.getUser_id()));
                        model1.setFromId(Integer.parseInt(messageModel.getTo_user_id()));
                        model1.setSessionId(messageModel.getSession_id());
                        model1.setUnReadCount(dao.getUnReadCountBySessionId(model1.getSessionId()));
                        sessionDao.updateSessionId(model1);
                    }

//                    }
//                    contactDao.update(contactModel);
//                }
                }

            }
            EventManager.getInstance().post(new IMEvent(messageCount));
        }

    }


}
