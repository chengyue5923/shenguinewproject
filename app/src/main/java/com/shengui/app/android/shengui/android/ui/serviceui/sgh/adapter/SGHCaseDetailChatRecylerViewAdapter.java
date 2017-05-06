package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.MediaPlayUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/4/7.
 */

public class SGHCaseDetailChatRecylerViewAdapter extends RecyclerView.Adapter<SGHCaseDetailChatRecylerViewAdapter.ViewHolder> {

    List<InquiryMsgBean.DataBean> data;
    Context context;
    String doctor;

    // 语音相关
    private ScaleAnimation mScaleBigAnimation;
    private ScaleAnimation mScaleLittleAnimation;
    private String mSoundData = "";//语音数据
    private String dataPath;
    private boolean isStop = true;  // 录音是否结束的标志 超过两分钟停止
    private boolean isCanceled = false; // 是否取消录音
    private float downY;
    private MediaRecorder mRecorder;
    private MediaPlayUtil mMediaPlayUtil;
    private String mVoiceData;
    private AnimationDrawable mImageAnim;
    MediaPlayer mediaPlayer;


    public SGHCaseDetailChatRecylerViewAdapter(List<InquiryMsgBean.DataBean> data, Context context, String doctor) {
        this.data = data;
        this.context = context;
        this.doctor = doctor;
        mMediaPlayUtil = MediaPlayUtil.getInstance();
//        mediaPlayer = new MediaPlayer();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgh_item_msg, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final InquiryMsgBean.DataBean dataBean = data.get(position);

        if (User.Login){
            if (!User.userId.equals(dataBean.getUserId())&&!User.userId.equals(doctor)){
                if (doctor.equals(dataBean.getUserId())){
                    leftInit(holder,dataBean);
                }else {
                    rightInit(holder,dataBean);
                }
            }else {
                if (!User.userId.equals(dataBean.getUserId())) {
                    leftInit(holder,dataBean);
                }else {
                    rightInit(holder,dataBean);
                }
            }
        }else {
            if (doctor.equals(dataBean.getUserId())){
                leftInit(holder,dataBean);
            }else {
                rightInit(holder,dataBean);
            }
        }


        holder.rightVoiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayUtil.isPlaying()) {
                    mMediaPlayUtil.stop();
                    mImageAnim.stop();
                    holder.rightIvVoiceImage.setVisibility(View.VISIBLE);
                    holder.rightIvVoiceImageAnim.setVisibility(View.GONE);
                } else {
                    startAnim(holder.rightIvVoiceImageAnim, holder.rightIvVoiceImage,dataBean.getMedia());
                    mMediaPlayUtil.play(Api.baseUrl+dataBean.getMedia());
                }
            }
        });


        holder.leftVoiceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayUtil.isPlaying()) {
                    mMediaPlayUtil.stop();
                    mImageAnim.stop();
                    isStop = true;
                    holder.leftIvVoiceImage.setVisibility(View.VISIBLE);
                    holder.leftIvVoiceImageAnim.setVisibility(View.GONE);
                } else if (isStop){
                    startAnim(holder.leftIvVoiceImageAnim, holder.leftIvVoiceImage,dataBean.getMedia());
                    mMediaPlayUtil.play(Api.baseUrl+dataBean.getMedia());
                }
            }
        });


    }

    private void rightInit(ViewHolder holder, InquiryMsgBean.DataBean dataBean) {

        if (dataBean.getUserId().equals(doctor)){
//            holder.rightQuestion.setVisibility(View.GONE);
        }
        holder.left.setVisibility(View.GONE);
        holder.right.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(Api.baseUrl + dataBean.getUserFace())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                .error(R.mipmap.logo) //失败图片
                .into(holder.rightFace);

        if (dataBean.getContentType() == 1) {
            holder.rightMsgText.setVisibility(View.VISIBLE);
            holder.rightMsgImg.setVisibility(View.GONE);
            holder.rightMsgVideo.setVisibility(View.GONE);
            holder.rightVoiceLayout.setVisibility(View.GONE);
            holder.rightMsgText.setText(dataBean.getContent());
        } else if (dataBean.getContentType() == 2) {
            holder.rightMsgText.setVisibility(View.GONE);
            holder.rightMsgImg.setVisibility(View.VISIBLE);
            holder.rightMsgVideo.setVisibility(View.GONE);
            holder.rightVoiceLayout.setVisibility(View.GONE);
            Glide.with(context)
                    .load(Api.baseUrl + dataBean.getMedia())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                    .error(R.mipmap.logo) //失败图片
                    .into(holder.rightMsgImg);
        } else if (dataBean.getContentType() == 3) {
            holder.rightMsgText.setVisibility(View.GONE);
            holder.rightMsgImg.setVisibility(View.GONE);
            holder.rightMsgVideo.setVisibility(View.VISIBLE);
            holder.rightVoiceLayout.setVisibility(View.GONE);
            holder.rightMsgVideo.setUp(Api.baseUrl + dataBean.getMedia(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            holder.rightMsgVideo.thumbImageView.setImageBitmap(createVideoThumbnail(dataBean.getMedia(), 500, 400));
        } else if (dataBean.getContentType() == 4) {
            holder.rightMsgText.setVisibility(View.GONE);
            holder.rightMsgImg.setVisibility(View.GONE);
            holder.rightMsgVideo.setVisibility(View.GONE);
            holder.rightVoiceLayout.setVisibility(View.VISIBLE);
            holder.rightTvVoiceLen.setText(dataBean.getMediaTime()+"" + '"');
        }
    }

    private void leftInit(ViewHolder holder, InquiryMsgBean.DataBean dataBean) {
        holder.left.setVisibility(View.VISIBLE);
        holder.right.setVisibility(View.GONE);

        Glide.with(context)
                .load(Api.baseUrl + dataBean.getUserFace())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                .error(R.mipmap.logo) //失败图片
                .into(holder.leftFace);
        if (dataBean.getContentType() == 1) {
            holder.leftMsgText.setVisibility(View.VISIBLE);
            holder.leftMsgImg.setVisibility(View.GONE);
            holder.leftMsgVideo.setVisibility(View.GONE);
            holder.leftVoiceLayout.setVisibility(View.GONE);
            holder.leftMsgText.setText(dataBean.getContent());
        } else if (dataBean.getContentType() == 2) {
            holder.leftMsgText.setVisibility(View.GONE);
            holder.leftMsgImg.setVisibility(View.VISIBLE);
            holder.leftMsgVideo.setVisibility(View.GONE);
            holder.leftVoiceLayout.setVisibility(View.GONE);
            Glide.with(context)
                    .load(Api.baseUrl + dataBean.getMedia())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                    .error(R.mipmap.logo) //失败图片
                    .into(holder.leftMsgImg);
        } else if (dataBean.getContentType() == 3) {
            holder.leftMsgText.setVisibility(View.GONE);
            holder.leftMsgImg.setVisibility(View.GONE);
            holder.leftMsgVideo.setVisibility(View.VISIBLE);
            holder.leftVoiceLayout.setVisibility(View.GONE);
            holder.leftMsgVideo.setUp(Api.baseUrl + dataBean.getMedia(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        } else if (dataBean.getContentType() == 4) {
            holder.leftMsgText.setVisibility(View.GONE);
            holder.leftMsgImg.setVisibility(View.GONE);
            holder.leftMsgVideo.setVisibility(View.GONE);
            holder.leftVoiceLayout.setVisibility(View.VISIBLE);
            holder.leftTvVoiceLen.setText(dataBean.getMediaTime()+"" + '"');
        }
    }

    /**
     * 语音播放效果
     */
    public void startAnim(final ImageView mIvVoiceAnim, final ImageView mIvVoice,String path) {

        mImageAnim = (AnimationDrawable) mIvVoiceAnim.getBackground();
        mIvVoiceAnim.setVisibility(View.VISIBLE);
        mIvVoice.setVisibility(View.GONE);
        mImageAnim.start();

        mMediaPlayUtil.setPlayOnCompleteListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mIvVoice.setVisibility(View.VISIBLE);
                mIvVoiceAnim.setVisibility(View.GONE);
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.left_face)
        CircleImageView leftFace;
        @Bind(R.id.left_msg_text)
        TextView leftMsgText;
        @Bind(R.id.left_msg_img)
        ImageView leftMsgImg;
        @Bind(R.id.left_msg_video)
        JCVideoPlayerStandard leftMsgVideo;
        @Bind(R.id.left_tv_voice_len)
        TextView leftTvVoiceLen;
        @Bind(R.id.left_iv_voice_image)
        ImageView leftIvVoiceImage;
        @Bind(R.id.left_iv_voice_image_anim)
        ImageView leftIvVoiceImageAnim;
        @Bind(R.id.left_voice_layout)
        RelativeLayout leftVoiceLayout;
        @Bind(R.id.left_msg_ll)
        LinearLayout leftMsgLl;
        @Bind(R.id.left)
        RelativeLayout left;
        @Bind(R.id.right_face)
        CircleImageView rightFace;
//        @Bind(R.id.right_question)
//        TextView rightQuestion;
        @Bind(R.id.right_msg_text)
        TextView rightMsgText;
        @Bind(R.id.right_msg)
        LinearLayout rightMsg;
        @Bind(R.id.right_msg_img)
        ImageView rightMsgImg;
        @Bind(R.id.right_msg_video)
        JCVideoPlayerStandard rightMsgVideo;
        @Bind(R.id.right_tv_voice_len)
        TextView rightTvVoiceLen;
        @Bind(R.id.right_iv_voice_image)
        ImageView rightIvVoiceImage;
        @Bind(R.id.right_iv_voice_image_anim)
        ImageView rightIvVoiceImageAnim;
        @Bind(R.id.right_voice_layout)
        RelativeLayout rightVoiceLayout;
        @Bind(R.id.right_msg_ll)
        LinearLayout rightMsgLl;
        @Bind(R.id.right)
        RelativeLayout right;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


