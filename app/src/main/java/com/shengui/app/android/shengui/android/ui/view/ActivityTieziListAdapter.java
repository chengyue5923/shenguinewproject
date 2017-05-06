package com.shengui.app.android.shengui.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.MessageChatAdapter;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.fragment.FocusGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.Fragment1;
import com.shengui.app.android.shengui.android.ui.fragment.HotGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityTieziListAdapter extends BasePlatAdapter<TieZiDetailModel> {
    final Context mContext;
    public ActivityTieziListAdapter(Context context) {
        super(context);
        mContext=context;
        initImageLoader();
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        final TieZiDetailModel mo = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_tie_zi_item, null);
            vh.imagesListView=(RelativeLayout)itemView.findViewById(R.id.imagesListView);
            vh.shareText=(ImageView)itemView.findViewById(R.id.shareText);

            vh.image1=(ImageView)itemView.findViewById(R.id.image1);
            vh.imageView2=(ImageView)itemView.findViewById(R.id.imageView2);
            vh.imageView3=(ImageView)itemView.findViewById(R.id.imageView3);
            vh.CommonIv=(ImageView)itemView.findViewById(R.id.CommonIv);
            vh.niceIv=(ImageView)itemView.findViewById(R.id.niceIv);


            vh.nameTiezaiText=(TextView) itemView.findViewById(R.id.nameTiezaiText);
            vh.timeTieZiTextView=(TextView) itemView.findViewById(R.id.timeTieZiTextView);
            vh.addressTV=(TextView) itemView.findViewById(R.id.addressTV);
            vh.contentTv=(TextView) itemView.findViewById(R.id.contentTv);
            vh.imageCount=(TextView) itemView.findViewById(R.id.imageCount);
            vh.addressTextView=(TextView) itemView.findViewById(R.id.addressTextView);
            vh.topTextView=(TextView) itemView.findViewById(R.id.topTextView);
            vh.commendTextView=(TextView) itemView.findViewById(R.id.commendTextView);
            vh.niceTextView=(TextView) itemView.findViewById(R.id.niceTextView);

            vh.personInfoIv=(CircleImageView) itemView.findViewById(R.id.personInfoIv);
            vh.ringLayout=(LinearLayout) itemView.findViewById(R.id.ringLayout);
            vh.jc_video=(JCVideoPlayerStandard) itemView.findViewById(R.id.jc_video);

            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        vh.shareText.setVisibility(View.GONE);
        Glide.with(mContext).load(mo.getUserinfo().getAvatar()).centerCrop().into(vh.personInfoIv);
        vh.nameTiezaiText.setText(mo.getUserinfo().getName());
        vh.addressTV.setText(mo.getCity_name());
        vh.contentTv.setText(mo.getContent());
        if(!mo.getTopic_id().equals("0")){
            vh.topTextView.setVisibility(View.VISIBLE);
            vh.topTextView.setText("#"+mo.getTopic()+"#");
        }else{
            vh.topTextView.setVisibility(View.GONE);
        }
        vh.topTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTopicDetail(mContext,mo.getTopic_id(),mo.getTopic());
            }
        });
        vh.addressTextView.setText(mo.getCircle());
        vh.addressTextView.setOnClickListener(new CircleOnClickListener(mo));

        vh.niceTextView.setText(mo.getDig_num());
        vh.commendTextView.setText(mo.getComment_num());
        vh.timeTieZiTextView.setText(getStrTime(mo.getCreate_time()));
        if(mo.getIs_dig().equals("0")){
            vh.niceIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tiezi_like));
        }else{
            vh.niceIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_image_bg));

        }
        vh.contentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
        vh.ringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
        vh.niceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
        vh.imagesListView.setVisibility(View.GONE);
        vh.jc_video.setVisibility(View.GONE);

        //点击缩略图看大图
        vh.imagesListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
        Logger.e("----------erwrwr-------"+mo.getVideos());
        try{
        if(mo.getImages()!=null&&mo.getImages().size()>0) {
            vh.image1.setVisibility(View.VISIBLE);
            if (mo.getImages().size() == 0) {
                vh.imagesListView.setVisibility(View.GONE);
            } else if (mo.getImages().size() == 1) {
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.GONE);
                vh.imageView3.setVisibility(View.GONE);

                try {
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                } catch (Exception e) {
                    com.base.platform.utils.android.Logger.e("sd" + e.getMessage());
                }

            } else if (mo.getImages().size() == 2) {
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.VISIBLE);
                vh.imageView3.setVisibility(View.GONE);
                try {
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                    Glide.with(mContext).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);

                } catch (Exception e) {
                    com.base.platform.utils.android.Logger.e("sd" + e.getMessage());
                }
            } else {
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.VISIBLE);
                vh.imageView3.setVisibility(View.VISIBLE);
                try {
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                    Glide.with(mContext).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);
                    Glide.with(mContext).load(mo.getImages().get(2).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView3);

                } catch (Exception e) {
                    Logger.e("sd" + e.getMessage());
                }
            }
            if (mo.getImages().size() <= 3) {
                vh.imageCount.setVisibility(View.GONE);
            } else {
                vh.imageCount.setVisibility(View.VISIBLE);
                vh.imageCount.setText(mo.getImages().size() + "张");
            }

        }else if(mo.getVideo_info()!=null&&!mo.getVideo_info().getUrl().equals("")){
                    if(!mo.getVideo_info().getUrl().equals("")){
                        vh.jc_video.setVisibility(View.VISIBLE);
                        vh.jc_video.setUp(mo.getVideo_info().getUrl()
                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
                        new MyTask(mo.getVideo_info().getUrl(), vh.jc_video.thumbImageView).execute();
                        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                    }else{
                        vh.jc_video.setVisibility(View.GONE);
                    }
         }
        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }
        return itemView;
    }

    public class CircleOnClickListener implements View.OnClickListener{

        TieZiDetailModel listmodel;
        public CircleOnClickListener( TieZiDetailModel listmo){
            listmodel= listmo;
        }
        @Override
        public void onClick(View v) {
            if(listmodel!=null&&!listmodel.getCircle_id().equals("")){
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    GuiMiController.getInstance().CiecleContentDetail(new ViewNetCallBack() {
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
                            if (flag == HttpConfig.CircleDetail.getType()) {
                                Logger.e("logger" + result);
                                QuanziList QuanZiModel = (QuanziList) result;

                                //官方圈子
                                if (QuanZiModel.getIs_public().equals("1")) {
                                    IntentTools.startQuanziManageOffical(mContext, QuanZiModel);
                                    return;
                                }
                                if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                                    IntentTools.startQuanziDetailSelf(mContext, QuanZiModel);
                                    return;
                                }
                                if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                                    IntentTools.startquanziDetail(mContext, QuanZiModel);
                                    return;
                                }
                                if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                                    IntentTools.startQuanziDetailSelf(mContext, QuanZiModel);
                                    return;
                                }
                            }
                        }
                    }, Integer.parseInt(listmodel.getCircle_id()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(getActivity(), model.getRedirect_url());
                } else {
                    IntentTools.startLogin(mContext);
                }

            }

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
    //adapter
    private class MyTask extends AsyncTask<String, Integer, String> {
        private ImageView imageView;
        private String  url;

        private Bitmap bitmap;
        public MyTask(String  urls, ImageView imageViews){

            url=urls;
            imageView=imageViews;
        }
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {


            bitmap=createVideoThumbnail(url,200,160);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imageView.setImageBitmap(bitmap);
        }
    }
    class ViewHolder {
        RelativeLayout imagesListView;
        TextView nameTiezaiText, timeTieZiTextView,addressTV,contentTv,imageCount,addressTextView,topTextView,commendTextView,niceTextView;
        ImageView shareText,image1,imageView2,imageView3,CommonIv,niceIv;
        CircleImageView personInfoIv;
        LinearLayout ringLayout;
        JCVideoPlayerStandard jc_video;
    }

    public void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }



    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }
}
