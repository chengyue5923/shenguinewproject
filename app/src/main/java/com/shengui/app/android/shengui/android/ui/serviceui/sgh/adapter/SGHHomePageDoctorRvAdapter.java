package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorNoList;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHDoctorActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2017/4/11.
 */


public class SGHHomePageDoctorRvAdapter extends RecyclerView.Adapter<SGHHomePageDoctorRvAdapter.ViewHolder> {

    Context context;
    List<DoctorNoList.DataBean> data;

    private final int DOCRECEIVE = 1;
    private final int VIDEOCOVERS= 2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOCRECEIVE:
                    Boolean obj = (Boolean) msg.obj;
                    if (obj) {
                        context.startActivity(new Intent(context, SGHDoctorActivity.class));
                    } else {
                        Toast.makeText(context, "接诊失败，请刷新后再试！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case  VIDEOCOVERS:
                    List o = (List) msg.obj;
                    ImageView imageView = (ImageView) o.get(0);
                    Bitmap bitmap = (Bitmap) o.get(1);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    private SGHListImageGirdAdapter sghListImageGirdAdapter;

    public SGHHomePageDoctorRvAdapter(Context context, List<DoctorNoList.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgh_item_doctor, null, false);//找到相应的item view，传递给viewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DoctorNoList.DataBean dataBean = data.get(position);
        holder.userName.setText(dataBean.getUserName());
        Glide.with(context)
                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getUserFace()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userFace);
        holder.time.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 2));

        if (dataBean.getContentType() == 1) {
            holder.userImage.setVisibility(View.GONE);
            holder.userVideo.setVisibility(View.GONE);
        } else if (dataBean.getContentType() == 2) {
            if (dataBean.getFiles() != null) {
                holder.userImage.setVisibility(View.VISIBLE);
                holder.userVideo.setVisibility(View.GONE);

                switch (dataBean.getFiles().size()) {
                    case 1:
                        holder.userImage.setVisibility(View.VISIBLE);
                        holder.userImg1.setVisibility(View.VISIBLE);
                        holder.userImg2.setVisibility(View.GONE);
                        holder.userImg3.setVisibility(View.GONE);
                        holder.userImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg1);
                        break;
                    case 2:
                        holder.userImage.setVisibility(View.VISIBLE);
                        holder.userImg1.setVisibility(View.VISIBLE);
                        holder.userImg2.setVisibility(View.VISIBLE);
                        holder.userImg3.setVisibility(View.GONE);
                        holder.userImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg2);
                        break;
                    case 3:
                        holder.userImage.setVisibility(View.VISIBLE);
                        holder.userImg1.setVisibility(View.VISIBLE);
                        holder.userImg2.setVisibility(View.VISIBLE);
                        holder.userImg3.setVisibility(View.VISIBLE);
                        holder.userImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg2);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(2)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg3);
                        break;
                    default:
                        holder.userImage.setVisibility(View.VISIBLE);
                        holder.userImg1.setVisibility(View.VISIBLE);
                        holder.userImg2.setVisibility(View.VISIBLE);
                        holder.userImg3.setVisibility(View.VISIBLE);
                        holder.userImageCount.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg2);
                        Glide.with(context)
                                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getFiles().get(2)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.userImg3);
                        holder.userImageCount.setText(dataBean.getFiles().size() + "张");
                        break;
                }
            } else {
                holder.userImage.setVisibility(View.VISIBLE);
                holder.userVideo.setVisibility(View.GONE);
            }

        } else {
            if (dataBean.getFiles() != null) {
                holder.userImage.setVisibility(View.GONE);
                holder.userVideo.setVisibility(View.VISIBLE);
                holder.userVideoPlay.setUp(Api.baseUrl + dataBean.getFiles().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                videoCovers(Api.baseUrl + dataBean.getFiles().get(0),holder.userVideoPlay.thumbImageView);
            } else {
                holder.userImage.setVisibility(View.GONE);
                holder.userVideo.setVisibility(View.GONE);
            }
        }
        holder.userTitle.setText(dataBean.getTitle());
        holder.imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateReceiveDialog(dataBean);

            }
        });
    }

    private void showCreateReceiveDialog(final DoctorNoList.DataBean dataBean) {
        DialogFacory.getInstance().createAlertDialog(context, "接诊提示", "是否确定接受此问诊？", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        FormBody formBody = new FormBody.Builder()
                                .add("inquiryId", dataBean.getId())
                                .build();
                        Boolean aBoolean = SGHJsonUtil.DoctorReceive(context, formBody);
                        Message message = handler.obtainMessage();
                        message.what = DOCRECEIVE;
                        message.obj = aBoolean;
                        handler.sendMessage(message);
                    }
                });
            }
        }, null).show();
    }

    private void videoCovers(final String url, final ImageView imageImg) {
        final List list = new ArrayList();
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(5 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                list.add(imageImg);
                list.add(bitmap);
                Message message = handler.obtainMessage();
                message.obj = list;
                message.what = VIDEOCOVERS;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.user_face)
        CircleImageView userFace;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.user_title)
        TextView userTitle;
        @Bind(R.id.imageView6)
        ImageView imageView6;
        @Bind(R.id.user_img1)
        ImageView userImg1;
        @Bind(R.id.user_img2)
        ImageView userImg2;
        @Bind(R.id.user_img3)
        ImageView userImg3;
        @Bind(R.id.user_image_count)
        TextView userImageCount;
        @Bind(R.id.user_image)
        RelativeLayout userImage;
        @Bind(R.id.user_video_play)
        JCVideoPlayerStandard userVideoPlay;
        @Bind(R.id.user_video)
        LinearLayout userVideo;
        @Bind(R.id.userQuestion)
        LinearLayout userQuestion;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

