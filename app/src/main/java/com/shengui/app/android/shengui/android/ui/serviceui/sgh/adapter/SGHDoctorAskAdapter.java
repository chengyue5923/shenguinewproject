package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.ScanImageActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.DoctorAskBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.ReceivePatientCaseDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/15.
 */


public class SGHDoctorAskAdapter extends RecyclerView.Adapter<SGHDoctorAskAdapter.ViewHolder> {

    List<DoctorAskBean.DataBean> data;
    Context context;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
         switch (msg.what){
             case  VIDEOCOVERS:
                 List obj = (List) msg.obj;
                 ImageView imageView = (ImageView) obj.get(0);
                 Bitmap bitmap = (Bitmap) obj.get(1);
                 imageView.setImageBitmap(bitmap);
                 break;
         }
        }
    };
    private final int VIDEOCOVERS= 1;


    public SGHDoctorAskAdapter(List<DoctorAskBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgh_item_doctor_ask, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final DoctorAskBean.DataBean dataBean = data.get(position);
        if (User.userType == 3 && dataBean.getIsAsk() == 2) {
            holder.tishimsg.setVisibility(View.VISIBLE);
        } else {
            holder.tishimsg.setVisibility(View.GONE);
        }

        if (dataBean.getContentType() == 1) {
            holder.image.setVisibility(View.GONE);
        } else if (dataBean.getContentType() == 3) {
            if (dataBean.getFiles() == null) {
                holder.image.setVisibility(View.GONE);
                holder.video.setVisibility(View.GONE);
            } else {
                holder.image.setVisibility(View.VISIBLE);
                holder.video.setVisibility(View.VISIBLE);
                videoCovers(Api.SGHBaseUrl + dataBean.getFiles().get(0),holder.imageImg);
            }
        } else if (dataBean.getContentType() == 2) {
            if (dataBean.getFiles() != null) {
                holder.video.setVisibility(View.GONE);
                holder.image.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(Api.baseUrl + dataBean.getFiles().get(0))
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                        .into(holder.imageImg);
            } else {
                holder.video.setVisibility(View.GONE);
                holder.image.setVisibility(View.GONE);
            }
        }else if (dataBean.getContentType() == 3){
            if (dataBean.getFiles() != null) {
                holder.image.setVisibility(View.GONE);
                holder.video.setVisibility(View.VISIBLE);
                videoCovers(Api.SGHBaseUrl + dataBean.getFiles().get(0),holder.imageImg);
            } else {
                holder.image.setVisibility(View.GONE);
                holder.video.setVisibility(View.GONE);
            }
        }
        holder.questionTime.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 1));

        if (dataBean.getStatus() <= 1) {
            holder.questionStatus.setImageResource(R.mipmap.icon_statue_wating);
        } else if (dataBean.getStatus() == 2) {
            holder.questionStatus.setImageResource(R.mipmap.icon_statue_already);
        } else if (dataBean.getStatus() == 3) {
            holder.questionStatus.setImageResource(R.mipmap.icon_statue_finish);
        }

        holder.uestionTitle.setText(dataBean.getTitle());

        final ArrayList<String> list = new ArrayList();

        if (dataBean.getFiles() != null) {

            for (int i = 0; i < dataBean.getFiles().size(); i++) {
                list.add(Api.SGHBaseUrl + dataBean.getFiles().get(i));
            }
        }
        holder.imageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScanImageActivity.class);
                intent.putStringArrayListExtra("images", list);
                intent.putExtra("index", 0);
                context.startActivity(intent);
            }
        });

        holder.contents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReceivePatientCaseDetailsActivity.class);
                intent.putExtra("inquiryId", dataBean.getId());
                context.startActivity(intent);
            }
        });
    }

//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    private Bitmap createVideoThumbnail(String url, int width, int height) {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
//        try {
//            if (Build.VERSION.SDK_INT >= 14) {
//                retriever.setDataSource(url, new HashMap<String, String>());
//            } else {
//                retriever.setDataSource(url);
//            }
//            bitmap = retriever.getFrameAtTime();
//        } catch (IllegalArgumentException ex) {
//            // Assume this is a corrupt video file
//        } catch (RuntimeException ex) {
//            // Assume this is a corrupt video file.
//        } finally {
//            try {
//                retriever.release();
//            } catch (RuntimeException ex) {
//                // Ignore failures while cleaning up.
//            }
//        }
//        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        }
//        return bitmap;
//    }

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tishimsg)
        TextView tishimsg;
        @Bind(R.id.image_img)
        ImageView imageImg;
        @Bind(R.id.video)
        ImageView video;
        @Bind(R.id.image)
        RelativeLayout image;
        @Bind(R.id.uestion_title)
        TextView uestionTitle;
        @Bind(R.id.question_time)
        TextView questionTime;
        @Bind(R.id.question_status)
        ImageView questionStatus;
        @Bind(R.id.content_layout)
        LinearLayout contentLayout;
        @Bind(R.id.content)
        RelativeLayout contents;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}



