package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.im.ImageLoader;


/**
 * @author : yingmu on 15-1-9.
 * @email : yingmu@mogujie.com.
 */
public class ImageRenderView extends BaseMsgRenderView {


    // 上层必须实现的接口
    private ImageLoadListener imageLoadListener;


    /**
     * 可点击的view
     */
    private View messageLayout;
    /**
     * 图片消息体
     */
    private BubbleImageView messageImage;

    private ImageView user_portrait;
    RequestManager glideRequest;
    private Context mContext;

    public ImageRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public static ImageRenderView inflater(Context context, ViewGroup viewGroup, boolean isMine) {
        int resource = isMine ? R.layout.im_mine_image_message_item : R.layout.im_other_image_message_item;
        ImageRenderView imageRenderView = (ImageRenderView) LayoutInflater.from(context).inflate(resource, viewGroup, false);
        imageRenderView.setMine(isMine);
        imageRenderView.setParentView(viewGroup);
        return imageRenderView;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        messageLayout = findViewById(R.id.message_layout);
        messageImage = (BubbleImageView) findViewById(R.id.message_image);
        user_portrait=(ImageView)findViewById(R.id.user_portrait);
    }

    /**
     *
     * */

    /**
     * 控件赋值
     *
     * @param messageEntity
     * @param userEntity    对于mine。 图片send_success 就是成功了直接取地址
     *                      对于sending  就是正在上传
     *                      <p/>
     *                      对于other，消息一定是success，接受成功额
     *                      2. 然后分析loadStatus 判断消息的展示状态
     */
    @Override
    public void render(final MessageEntity messageEntity, final ContactModel userEntity, Context ctx) {
        super.render(messageEntity, userEntity, ctx);
        Logger.e("erer"+messageEntity.getContent());
        if(messageEntity.getFromId()!= UserPreference.getUid()){
            ImageLoader.getInstance(ctx, R.drawable.im_message_image_default).displayImage(messageEntity.getContent(),messageImage);
//            Glide.with(ctx).load(messageEntity.getContent()).placeholder(R.drawable.im_message_image_default).into(messageImage);
        }else{
            Logger.e("erwwwwwwwwwer"+messageEntity.getContent());
            if (messageEntity.getStatus()== Constant.MSG_SUCCESS){
                Logger.e("erwww----wwwer"+messageEntity.getContent());
//                Glide.with(ctx).load(messageEntity.getContent()).placeholder(R.drawable.im_message_image_default).into(messageImage);
                ImageLoader.getInstance(ctx, R.drawable.im_message_image_default).displayImage(messageEntity.getContent(),messageImage);
            }
             else
//                Glide.with(ctx).load(messageEntity.getContent()).placeholder(R.drawable.im_message_image_default).into(user_portrait);
                ImageLoader.getInstance(ctx, R.drawable.im_message_image_default).loadLocalImage(messageEntity.getContent(),messageImage);
        }



        if(isMine()){
            glideRequest = Glide.with(ctx);
            glideRequest.load(UserPreference.getAvatar()).transform(new GlideCircleTransform(ctx)).into(user_portrait);
//            Glide.with(ctx).load(UserPreference.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(user_portrait);
        }else{

            try{
                Logger.e("messageEntity"+messageEntity.getFromId());
                if(messageEntity.getHeadPath()==null||messageEntity.getHeadPath().equals("")){
                    Glide.with(ctx).load(messageEntity.getHeadPath()).asBitmap().placeholder(R.drawable.default_image).into(user_portrait);
                }else{
                    glideRequest = Glide.with(ctx);
                    glideRequest.load(messageEntity.getHeadPath()).transform(new GlideCircleTransform(ctx)).into(user_portrait);
                }
            }catch (Exception e){
                Logger.e("excere"+e.getMessage());
            }

//            Glide.with(ctx).load(messageEntity.getHeadPath()).asBitmap().placeholder(R.drawable.default_image).into(user_portrait);
        }

        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FullScreenImageActivity.class);
                intent.putExtra("url", messageEntity.getContent());
                mContext.startActivity(intent);
            }
        });

    }

    public static Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
        System.out.println("图片是否变成圆形模式了+++++++++++++");
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        System.out.println("pixels+++++++" + String.valueOf(ratio));

        return output;

    }
    public interface ImageLoadListener {
        public void onLoadComplete(String path);

        // 应该把exception 返回结构放进去
        public void onLoadFailed();

    }

    public void setImageLoadListener(ImageLoadListener imageLoadListener) {
        this.imageLoadListener = imageLoadListener;
    }

    /**---------------------图片下载相关、以及事件回调 end-----------------------------------*/


    /**
     * ----------------------set/get------------------------------------
     */
    public View getMessageLayout() {
        return messageLayout;
    }

    public ImageView getMessageImage() {
        return messageImage;
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
