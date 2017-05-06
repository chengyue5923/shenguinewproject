package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.CaseDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.OfficialCaseDetailActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SGHHomePageRVAdapter extends RecyclerView.Adapter<SGHHomePageRVAdapter.ViewHolder> {

    Context context;
    List<InquiryListBean.DataBean> data;
    private final int VIDEOCOVERS= 1;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VIDEOCOVERS:
                    List obj = (List) msg.obj;
                    ImageView imageView = (ImageView) obj.get(0);
                    Bitmap bitmap = (Bitmap) obj.get(1);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };


    public SGHHomePageRVAdapter(Context context, List<InquiryListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgh_item, null, false);//找到相应的item view，传递给viewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InquiryListBean.DataBean dataBean = data.get(position);

        Log.e("test", "userQuestionInit: "+dataBean.getDoctorName());

        if (dataBean.getType() == 1) {//如果数据是用户问诊
            userQuestionInit(holder, dataBean);
        } else if (dataBean.getType() == 2) {//数据时官方案例
            officialExampleInit(holder, dataBean);
        }
    }

    private void officialExampleInit(ViewHolder holder, final InquiryListBean.DataBean dataBean) {
        holder.officialExample.setVisibility(View.VISIBLE);//显示官方案例部分
        holder.userQuestion.setVisibility(View.GONE);//隐藏用户部分

        holder.officialIntro.setText(dataBean.getTitle());
        holder.officialViewsCounts.setText(dataBean.getViews() + "次");

        if (dataBean.getContentType() == 1) {//判斷類型是否為純文字
            holder.officialImage.setVisibility(View.GONE);
            holder.officialVideo.setVisibility(View.GONE);

        } else if (dataBean.getContentType() == 2) {//判断类型是否為圖片

            if (dataBean.getFiles() != null) {
                holder.officialImage.setVisibility(View.VISIBLE);
                holder.officialVideo.setVisibility(View.GONE);
                switch (dataBean.getFiles().size()) {
                    case 1:
                        holder.officialImage.setVisibility(View.VISIBLE);
                        holder.officialImg1.setVisibility(View.VISIBLE);
                        holder.officialImg2.setVisibility(View.GONE);
                        holder.officialImg3.setVisibility(View.GONE);
                        holder.officialImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg1);
                        break;
                    case 2:
                        holder.officialImage.setVisibility(View.VISIBLE);
                        holder.officialImg1.setVisibility(View.VISIBLE);
                        holder.officialImg2.setVisibility(View.VISIBLE);
                        holder.officialImg3.setVisibility(View.GONE);
                        holder.officialImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg2);
                        break;
                    case 3:
                        holder.officialImage.setVisibility(View.VISIBLE);
                        holder.officialImg1.setVisibility(View.VISIBLE);
                        holder.officialImg2.setVisibility(View.VISIBLE);
                        holder.officialImg3.setVisibility(View.VISIBLE);
                        holder.officialImageCount.setVisibility(View.GONE);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg2);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(2)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg3);
                        break;

                    default:
                        holder.officialImage.setVisibility(View.VISIBLE);
                        holder.officialImg1.setVisibility(View.VISIBLE);
                        holder.officialImg2.setVisibility(View.VISIBLE);
                        holder.officialImg3.setVisibility(View.VISIBLE);
                        holder.officialImageCount.setVisibility(View.VISIBLE);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(0)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg1);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(1)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg2);
                        Glide.with(context)
                                .load(Uri.parse(Api.baseUrl + dataBean.getFiles().get(2)))
                                .skipMemoryCache(false)
                                .thumbnail(0.4f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(holder.officialImg3);
                        holder.officialImageCount.setText(dataBean.getFiles().size() + "张");
                        break;
                }
            } else {
                holder.officialImage.setVisibility(View.GONE);
                holder.officialVideo.setVisibility(View.GONE);
            }
        } else if (dataBean.getContentType() == 3) { //判斷類型是否為視頻
            if (dataBean.getFiles() != null) {
                holder.officialImage.setVisibility(View.GONE);
                holder.officialVideo.setVisibility(View.VISIBLE);
                holder.video.setUp(Api.baseUrl + dataBean.getFiles().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
//                holder.video.thumbImageView.setImageBitmap(createVideoThumbnail(Api.baseUrl + dataBean.getFiles().get(0), 192, 108));
                videoCovers(Api.SGHBaseUrl + dataBean.getFiles().get(0),holder.video.thumbImageView);
            } else {
                holder.officialImage.setVisibility(View.GONE);
                holder.officialVideo.setVisibility(View.GONE);
            }
        }

        holder.officialTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OfficialCaseDetailActivity.class);
                intent.putExtra("officialId",dataBean.getId());
                context.startActivity(intent);
            }
        });


        if (dataBean.getFiles() != null) {
            final ArrayList<String> list = new ArrayList();

            for (int i = 0; i < dataBean.getFiles().size(); i++) {
                list.add(Api.SGHBaseUrl + dataBean.getFiles().get(i));
            }

            holder.officialImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 0);
                    context.startActivity(intent);
                }
            });
            holder.officialImg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 1);
                    context.startActivity(intent);
                }
            });

            holder.officialImg3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 1);
                    context.startActivity(intent);
                }
            });
        }
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

    private void userQuestionInit(final ViewHolder holder, final InquiryListBean.DataBean dataBean) {
        holder.officialExample.setVisibility(View.GONE);//隐藏官方案例部分
        holder.userQuestion.setVisibility(View.VISIBLE);//显示用户部分
        holder.userIntro.setText(dataBean.getTitle());//显示文字



        if (dataBean.getContentType() == 1) {//判斷類型是否為純文字
            holder.userImage.setVisibility(View.GONE);
            holder.userVideo.setVisibility(View.GONE);

        } else if (dataBean.getContentType() == 2) {//判断类型是否為圖片
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
                holder.userImage.setVisibility(View.GONE);
                holder.userVideo.setVisibility(View.GONE);
            }
        } else if (dataBean.getContentType() == 3) { //判斷類型是否為視頻

            if (dataBean.getFiles() != null) {
                holder.userImage.setVisibility(View.GONE);
                holder.userVideo.setVisibility(View.VISIBLE);
                holder.userVideoPlay.setUp(Api.baseUrl + dataBean.getFiles().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                videoCovers(Api.SGHBaseUrl + dataBean.getFiles().get(0),holder.userVideoPlay.thumbImageView);
            } else {
                holder.userImage.setVisibility(View.GONE);
                holder.userVideo.setVisibility(View.GONE);
            }

        }

        holder.userName.setText(dataBean.getUserName());//設置用戶信息
        Glide.with(context)
                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getUserImage()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userFace);

        holder.doctorName.setText(dataBean.getDoctorName() + "已完美解决问题");//設置醫生信息
        Glide.with(context)
                .load(Uri.parse(Api.SGHBaseUrl + dataBean.getDoctorImage()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.logo)
                .into(holder.doctorFace);

        holder.userTime.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 1));
        holder.userViewsCounts.setText(dataBean.getViews() + "次");

        holder.clickSeeAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaseDetailsActivity.class);
                intent.putExtra("inquiryId", dataBean.getId());
                intent.putExtra("userName", dataBean.getUserName());
                intent.putExtra("userImage", dataBean.getUserImage());
                intent.putExtra("doctor", dataBean.getDoctor());
                context.startActivity(intent);
            }
        });

        if (dataBean.getFiles() != null) {
            final ArrayList<String> list = new ArrayList();

            for (int i = 0; i < dataBean.getFiles().size(); i++) {
                list.add(Api.SGHBaseUrl + dataBean.getFiles().get(i));
            }

            holder.userImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 0);
                    context.startActivity(intent);
                }
            });
            holder.userImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 1);
                    context.startActivity(intent);
                }
            });

            holder.userImg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 1);
                    context.startActivity(intent);
                }
            });
        }

    } private void videoCovers(final String url, final ImageView imageImg) {
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
        @Bind(R.id.user_face)
        CircleImageView userFace;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.user_time)
        TextView userTime;
        @Bind(R.id.user_views_counts)
        TextView userViewsCounts;
        @Bind(R.id.user_intro)
        TextView userIntro;
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
        @Bind(R.id.doctor_name)
        TextView doctorName;
        @Bind(R.id.click_see_answer)
        TextView clickSeeAnswer;
        @Bind(R.id.doctor_face)
        CircleImageView doctorFace;
        @Bind(R.id.userQuestion)
        LinearLayout userQuestion;
        @Bind(R.id.official_intro)
        TextView officialIntro;
        @Bind(R.id.official_title)
        LinearLayout officialTitle;
        @Bind(R.id.official_img1)
        ImageView officialImg1;
        @Bind(R.id.official_img2)
        ImageView officialImg2;
        @Bind(R.id.official_img3)
        ImageView officialImg3;
        @Bind(R.id.official_image_count)
        TextView officialImageCount;
        @Bind(R.id.official_image)
        RelativeLayout officialImage;
        @Bind(R.id.video)
        JCVideoPlayerStandard video;
        @Bind(R.id.official_video)
        LinearLayout officialVideo;
        @Bind(R.id.official_time)
        TextView officialTime;
        @Bind(R.id.official_views_counts)
        TextView officialViewsCounts;
        @Bind(R.id.official_example)
        LinearLayout officialExample;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


