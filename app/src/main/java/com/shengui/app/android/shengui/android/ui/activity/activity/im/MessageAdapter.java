package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.MessageType;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.DateUtil;
import com.shengui.app.android.shengui.utils.im.CommonUtil;

import java.util.ArrayList;

/**
 * @author : yingmu on 15-1-8.
 * @email : yingmu@mogujie.com.
 */
public class MessageAdapter extends BaseAdapter {
    private ArrayList<Object> msgObjectList = new ArrayList<>();
    private Context ctx;

    private String Headpath;
    private int PathPersonId;

    public int getPathPersonId() {
        return PathPersonId;
    }

    public void setPathPersonId(int pathPersonId) {
        PathPersonId = pathPersonId;
    }

    public String getHeadpath() {
        return Headpath;
    }

    public void setHeadpath(String headpath) {
        Headpath = headpath;
    }


    /**
     * 依赖整体session状态的
     */


    public MessageAdapter(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * ----------------------添加历史消息-----------------
     */
    public void addItem(final MessageEntity msg) {
        Logger.e("message"+PathPersonId+msg.toString());
        msg.setHeadPath(Headpath);
        msg.setHeadPersonId(PathPersonId);
        int nextTime = msg.getCreated();
        if (getCount() > 0) {
            Object object = msgObjectList.get(getCount() - 1);
            if (object instanceof MessageEntity) {
                long preTime = ((MessageEntity) object).getCreated();
                boolean needTime = DateUtil.needDisplayTime(preTime, nextTime);
                if (needTime) {
                    int in = nextTime;
                    msgObjectList.add(in);
                }
            }
        } else {
            int in = msg.getCreated();
            msgObjectList.add(in);
        }
        if (msg.getDisplayType().equals(Constant.SHOW_ORIGIN_TEXT_TYPE)) {
            msgObjectList.add(new TextMessage(msg));
        }
        if (msg.getDisplayType().equals(Constant.SHOW_AUDIO_TYPE)) {
            msgObjectList.add(AudioMessage.parse(msg));
        }
        if (msg.getDisplayType().equals(Constant.SHOW_IMAGE_TYPE)) {
            msgObjectList.add(new ImageMessage(msg));
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if (null == msgObjectList) {
            return 0;
        } else {
            return msgObjectList.size();
        }
    }

    @Override
    public int getViewTypeCount() {
        return MessageType.values().length;
    }


    @Override
    public int getItemViewType(int position) {
        try {
            /**默认是失败类型*/
            MessageType type = MessageType.MESSAGE_TYPE_INVALID;
            Object obj = msgObjectList.get(position);
            if (obj instanceof Integer) {
                type = MessageType.MESSAGE_TYPE_TIME_TITLE;
            } else if (obj instanceof MessageEntity) {
                MessageEntity info = (MessageEntity) obj;
                Logger.e("infoinfo.getFromId()==="+info.toString());
                Logger.e("infoin==="+info.getUserId()+info.getFromId());
                boolean isMine = info.getFromId() == UserPreference.getUid();
                Logger.e("infoinfo.UserPreference.getUid()()==="+UserPreference.getUid());
                switch (info.getDisplayType()) {
                    case Constant.SHOW_AUDIO_TYPE:
                        type = isMine ? MessageType.MESSAGE_TYPE_MINE_AUDIO
                                : MessageType.MESSAGE_TYPE_OTHER_AUDIO;
                        break;
                    case Constant.SHOW_IMAGE_TYPE:

                        type = isMine ? MessageType.MESSAGE_TYPE_MINE_IMAGE
                                : MessageType.MESSAGE_TYPE_OTHER_IMAGE;
                        break;
                    case Constant.SHOW_ORIGIN_TEXT_TYPE:
                        if (CommonUtil.isShareText(info.getContent())) {
                            type = isMine ? MessageType.MESSAGE_TYPE_MINE_SHARE
                                    : MessageType.MESSAGE_TYPE_OTHER_SHARE;
                        } else {
                            type = isMine ? MessageType.MESSAGE_TYPE_MINE_TEXT
                                    : MessageType.MESSAGE_TYPE_OTHER_TEXT;
                        }
                        break;
                    default:
                        break;
                }
            }
            return type.ordinal();
        } catch (Exception e) {
            return MessageType.MESSAGE_TYPE_INVALID.ordinal();
        }
    }

    @Override
    public Object getItem(int position) {
        if (position >= getCount() || position < 0) {
            return null;
        }
        return msgObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 时间气泡的渲染展示
     */
    private View timeBubbleRender(int position, View convertView, ViewGroup parent) {
        TimeRenderView timeRenderView;
        Integer timeBubble = (Integer) msgObjectList.get(position);
        if (null == convertView) {
            timeRenderView = TimeRenderView.inflater(ctx, parent);
        } else {
            // 不用再使用tag 标签了
            timeRenderView = (TimeRenderView) convertView;
        }
        timeRenderView.setTime(timeBubble);
        return timeRenderView;
    }


    public void updateItemState(MessageEntity messageEntity, boolean success) {
        for (Object o : msgObjectList) {
            Logger.e("fgfgfgf"+o.toString()+success);
            if (!(o instanceof Integer)) {
                Logger.e("fgfgfgfssss"+((MessageEntity)o).getContent()+"-yu-"+messageEntity.getContent());
                Logger.e("sssddddddds"+((MessageEntity)o).getContent()+"--"+messageEntity.getContent());
                if (((MessageEntity)o).getContent().equals(messageEntity.getContent())) {
                    ((MessageEntity)o).setStatus(success ? Constant.MSG_SUCCESS : Constant.MSG_FAILURE);
//                    ((MessageEntity) o).setId(success ? messageEntity.getId() : 0);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    /**
     * 1.头像事件
     * mine:事件 other事件
     * 图片的状态  消息收到，没收到，图片展示成功，没有成功
     * 触发图片的事件  【长按】
     * <p/>
     * 图片消息类型的render
     *
     * @param position
     * @param convertView
     * @param parent
     * @param isMine
     * @return
     */
    private View imageMsgRender(final int position, View convertView, final ViewGroup parent, final boolean isMine) {
        ImageRenderView imageRenderView;
        final ImageMessage imageMessage = (ImageMessage) msgObjectList.get(position);
        ContactModel userEntity = new ContactModel();
        if (null == convertView) {
            imageRenderView = ImageRenderView.inflater(ctx, parent, isMine);
        } else {
            imageRenderView = (ImageRenderView) convertView;
        }
        imageRenderView.render(imageMessage, userEntity, ctx);
        return imageRenderView;
    }

    /**
     * 语音的路径，判断收发的状态
     * 展现的状态
     * 播放动画相关
     * 获取语音的读取状态/
     * 语音长按事件
     *
     * @param position
     * @param convertView
     * @param parent
     * @param isMine
     * @return
     */
    private View audioMsgRender(final int position, View convertView, final ViewGroup parent, final boolean isMine) {
        AudioRenderView audioRenderView;
        final AudioMessage audioMessage = (AudioMessage) msgObjectList.get(position);
        ContactModel entity = new ContactModel();
        if (null == convertView) {
            audioRenderView = AudioRenderView.inflater(ctx, parent, isMine);
        } else {
            audioRenderView = (AudioRenderView) convertView;
        }
        audioRenderView.setBtnImageListener(new AudioRenderView.BtnImageListener() {
            @Override
            public void onClickUnread() {

            }

            @Override
            public void onClickReaded() {
            }
        });
        audioRenderView.render(audioMessage, entity, ctx);
        return audioRenderView;
    }


    /**
     * text类型的: 1. 设定内容Emoparser
     * 2. 点击事件  单击跳转、 双击方法、长按pop menu
     * 点击头像的事件 跳转
     *
     * @param position
     * @param convertView
     * @param viewGroup
     * @param isMine
     * @return
     */
    private View textMsgRender(final int position, View convertView, final ViewGroup viewGroup, final boolean isMine) {
        TextRenderView textRenderView;
        final TextMessage textMessage = (TextMessage) msgObjectList.get(position);
        ContactModel userEntity = new ContactModel();

        if (null == convertView) {
            textRenderView = TextRenderView.inflater(ctx, viewGroup, isMine); //new TextRenderView(ctx,viewGroup,isMine);
        } else {
            textRenderView = (TextRenderView) convertView;
        }
        textRenderView.render(textMessage, userEntity, ctx);
        return textRenderView;
    }

    /**
     * text类型的: 1. 设定内容Emoparser
     * 2. 点击事件  单击跳转、 双击方法、长按pop menu
     * 点击头像的事件 跳转
     *
     * @param position
     * @param convertView
     * @return
     */
//    private View shareMsgRender(final int position, View convertView, final ViewGroup viewGroup, final boolean isMine) {
//        ShareRenderView textRenderView;
//        final TextMessage textMessage = (TextMessage) msgObjectList.get(position);
//
//
//        if (null == convertView) {
//            textRenderView = ShareRenderView.inflater(ctx, viewGroup, isMine); //new TextRenderView(ctx,viewGroup,isMine);
//        } else {
//            textRenderView = (ShareRenderView) convertView;
//        }
//        textRenderView.render(textMessage, ctx);
//        return textRenderView;
//    }
//

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int typeIndex = getItemViewType(position);
        MessageType renderType = MessageType.values()[typeIndex];
        // 改用map的形式
        switch (renderType) {
            case MESSAGE_TYPE_INVALID:
                // 直接返回
                break;
            case MESSAGE_TYPE_TIME_TITLE:
                convertView = timeBubbleRender(position, convertView, parent);
                break;
            case MESSAGE_TYPE_MINE_AUDIO:
                convertView = audioMsgRender(position, convertView, parent, true);
                break;
            case MESSAGE_TYPE_OTHER_AUDIO:
                convertView = audioMsgRender(position, convertView, parent, false);
                break;
            case MESSAGE_TYPE_MINE_IMAGE:
                convertView = imageMsgRender(position, convertView, parent, true);
                break;
            case MESSAGE_TYPE_OTHER_IMAGE:
                convertView = imageMsgRender(position, convertView, parent, false);
                break;
            case MESSAGE_TYPE_MINE_TEXT:
                convertView = textMsgRender(position, convertView, parent, true);
                break;
            case MESSAGE_TYPE_OTHER_TEXT:
                convertView = textMsgRender(position, convertView, parent, false);
                break;
//            case MESSAGE_TYPE_MINE_SHARE:
//                convertView = shareMsgRender(position, convertView, parent, true);
//                break;
//            case MESSAGE_TYPE_OTHER_SHARE:
//                convertView = shareMsgRender(position, convertView, parent, false);
//                break;
        }
        return convertView;

    }


}
