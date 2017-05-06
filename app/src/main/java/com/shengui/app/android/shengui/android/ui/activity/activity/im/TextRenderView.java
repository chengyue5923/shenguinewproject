package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.im.CommonUtil;


/**
 * @author : yingmu on 15-1-9.
 * @email : yingmu@mogujie.com.
 * <p/>
 * 样式根据mine 与other不同可以分成两个
 */
public class TextRenderView extends BaseMsgRenderView {
    /**
     * 文字消息体
     */
    private TextView messageContent;
    private ImageView user_portrait;
    RequestManager glideRequest;
    public static TextRenderView inflater(Context context, ViewGroup viewGroup, boolean isMine) {
        int resource = isMine ? R.layout.im_mine_text_message_item : R.layout.im_other_text_message_item;
        TextRenderView textRenderView = (TextRenderView) LayoutInflater.from(context).inflate(resource, viewGroup, false);
        textRenderView.setMine(isMine);
        textRenderView.setParentView(viewGroup);
        return textRenderView;
    }

    public TextRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        messageContent = (TextView) findViewById(R.id.message_content);
        user_portrait=(ImageView)findViewById(R.id.user_portrait);
    }

    /**
     * 控件赋值
     *
     * @param messageEntity
     * @param userEntity
     */
    @Override
    public void render(MessageEntity messageEntity, ContactModel userEntity, final Context context) {
        super.render(messageEntity, userEntity, context);
        TextMessage textMessage = (TextMessage) messageEntity;
        // 按钮的长按也是上层设定的
        // url 路径可以设定 跳转哦哦
        String content = textMessage.getContent();
        Logger.e("ffffffffffff------------------------"+messageEntity.getHeadPath());
        if(isMine()){
            glideRequest = Glide.with(context);
            glideRequest.load(UserPreference.getAvatar()).transform(new GlideCircleTransform(context)).into(user_portrait);
        }else{
            try{
                Logger.e("messageEntity"+messageEntity.getFromId());
                glideRequest = Glide.with(context);

                if(messageEntity.getHeadPath()==null||messageEntity.getHeadPath().equals("")){
                    Glide.with(context).load(messageEntity.getHeadPath()).asBitmap().placeholder(R.drawable.default_image).into(user_portrait);

                    Logger.e("excer44444444e");

//                    MineInfoController.getInstance().get_my_fullinfoById(this, messageEntity.getFromId() + "", UserPreference.getTOKEN());

                }else{
                    glideRequest.load(messageEntity.getHeadPath()).transform(new GlideCircleTransform(context)).into(user_portrait);
                }
            }catch (Exception e){
                Logger.e("excere"+e.getMessage());
            }
//            Glide.with(ctx).load(messageEntity.getHeadPath()).asBitmap().placeholder(R.drawable.default_image).into(user_portrait);
        }
            messageContent.setText(EmojiParser.getInstance(getContext()).convertToHtml(content, context)); // 所以上层还是处理好之后再给我 Emoparser 处理之后的

        extractUrl2Link(messageContent);

    }


    private static final String SCHEMA = "com.mogujie.tt://message_private_url";
    private static final String PARAM_UID = "uid";
    private String urlRegex = "((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+(?:(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])|(?:biz|b[abdefghijmnorstvwyz])|(?:cat|com|coop|c[acdfghiklmnoruvxyz])|d[ejkmoz]|(?:edu|e[cegrstu])|f[ijkmor]|(?:gov|g[abdefghilmnpqrstuwy])|h[kmnrtu]|(?:info|int|i[delmnoqrst])|(?:jobs|j[emop])|k[eghimnrwyz]|l[abcikrstuvy]|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])|(?:name|net|n[acefgilopruz])|(?:org|om)|(?:pro|p[aefghklmnrstwy])|qa|r[eouw]|s[abcdeghijklmnortuvyz]|(?:tel|travel|t[cdfghjklmnoprtvwz])|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw]))|(?:(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])))(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?(?:\\b|$)";

    private void extractUrl2Link(TextView v) {
        java.util.regex.Pattern wikiWordMatcher = java.util.regex.Pattern.compile(urlRegex);
        String mentionsScheme = String.format("%s/?%s=", SCHEMA, PARAM_UID);
        Linkify.addLinks(v, wikiWordMatcher, mentionsScheme);
    }

    @Override
    public void msgFailure(MessageEntity messageEntity) {
        super.msgFailure(messageEntity);
    }

    /**
     * ----------------set/get---------------------------------
     */
    public TextView getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(TextView messageContent) {
        this.messageContent = messageContent;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public ViewGroup getParentView() {
        return parentView;
    }

    public void setParentView(ViewGroup parentView) {
        this.parentView = parentView;
    }
}
