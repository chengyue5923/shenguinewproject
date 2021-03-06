package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;


/**
 * @author : yingmu on 15-1-9.
 * @email : yingmu@mogujie.com.
 */
public abstract class BaseMsgRenderView extends RelativeLayout {
    /** 头像*/
    protected ImageView portrait;
    /** 消息状态*/
    protected ImageView messageFailed;
    protected ProgressBar loadingProgressBar;
    protected TextView name;

    /**渲染的消息实体*/
    protected MessageEntity messageEntity;
    protected ViewGroup parentView;
    protected  boolean isMine;

    protected BaseMsgRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 渲染之后做的事情 子类会调用到这个地方嘛?
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        portrait = (ImageView) findViewById(R.id.user_portrait);
        messageFailed = (ImageView) findViewById(R.id.message_state_failed);
        loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        name = (TextView) findViewById(R.id.name);
    }



    // 消息失败绑定事件 三种不同的弹窗
    // image的load状态就是 sending状态的一个子状态
    public  void msgSendinging(final MessageEntity messageEntity){
        messageFailed.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    public void msgFailure(final MessageEntity messageEntity){
        messageFailed.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    public void msgSuccess(final MessageEntity messageEntity){
        messageFailed.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    public void msgStatusError(final MessageEntity messageEntity){
        messageFailed.setVisibility(View.GONE);
        loadingProgressBar.setVisibility(View.GONE);
    }

    /**控件赋值*/
    public void render(final MessageEntity entity, ContactModel userEntity, final Context ctx){
        this.messageEntity = entity;
        if(userEntity == null){
            // 没有找到对应的用户信息 todo
            // 请求用户信息 设定默认头像、默认姓名、
            userEntity=new ContactModel();
            userEntity.setName("未知");
        }

        String avatar = userEntity.getAvatar();
        int msgStatus = messageEntity.getStatus();

//        //头像设置
//        portrait.setDefaultImageRes(R.drawable.im_default_user_portrait_corner);
//        portrait.setCorner(5);
//        portrait.setImageUrl(avatar);
        // 设定姓名 应该消息都是有的
       if(!isMine){
           name.setText(userEntity.getName());
           name.setVisibility(View.VISIBLE);
       }


        /**头像的跳转事件暂时放在这里， todo 业务结合紧密，但是应该不会改了*/
        final int userId = userEntity.getUid();
        portrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                IMUIHelper.openUserProfileActivity(getContext(),userId);
            }
        });
        /**头像事件 end*/

        // 设定三种信息的弹窗类型
        // 判定消息的状态 成功还是失败  todo 具体实现放在子类中
        switch (msgStatus){
            case Constant.MSG_FAILURE:
                msgFailure(messageEntity);
                break;
            case Constant.MSG_SUCCESS:
                msgSuccess(messageEntity);
                break;
            case Constant.MSG_SENDING:
                msgSendinging(messageEntity);
                break;
            default:
                msgStatusError(messageEntity);
        }

        // 如果消息还是处于loading 状态
        // 判断是否是image类型  image类型的才有 loading，其他的都GONE
        // todo 上面这些由子类做
        // 消息总体的类型有两种
        // 展示类型有四种(图片、文字、语音、混排)
    }

   /**-------------------------set/get--------------------------*/
    public ImageView getPortrait() {
        return portrait;
    }

    public ImageView getMessageFailed() {
        return messageFailed;
    }


    public ProgressBar getLoadingProgressBar() {
        return loadingProgressBar;
    }

    public TextView getName() {
        return name;
    }

}
