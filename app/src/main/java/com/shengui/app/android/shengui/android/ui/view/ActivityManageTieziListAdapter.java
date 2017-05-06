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
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.fragment.FocusGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.fragment.Fragment1;
import com.shengui.app.android.shengui.android.ui.fragment.NewGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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
public class ActivityManageTieziListAdapter extends BasePlatAdapter<TieZiDetailModel> {
    final Context mContext;
    public ActivityManageTieziListAdapter(Context context) {
        super(context);
        mContext=context;
        initImageLoader();
    }
    boolean ismanage=false;
    public void SetManage(boolean isman){
        ismanage=isman;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
        final TieZiDetailModel mo = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_manage_tie_zi_item, null);
            vh.personInfoIv = (CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.imagesListView=(RelativeLayout)itemView.findViewById(R.id.imagesListView);
            vh.shareText=(ImageView)itemView.findViewById(R.id.shareText);
            vh.deleteText=(TextView)itemView.findViewById(R.id.deleteText);

            vh.nameTiezaiText=(TextView)itemView.findViewById(R.id.nameTiezaiText);
            vh.timeTieZiTextView=(TextView)itemView.findViewById(R.id.timeTieZiTextView);
            vh.addressTextview=(TextView)itemView.findViewById(R.id.addressTextview);

            vh.contentID=(TextView)itemView.findViewById(R.id.contentID);
            vh.commendTextView=(TextView)itemView.findViewById(R.id.commendTextView);
            vh.niceTextView=(TextView)itemView.findViewById(R.id.niceTextView);

            vh.numbeTV=(TextView)itemView.findViewById(R.id.numbeTV);
            vh.topTextView=(TextView)itemView.findViewById(R.id.topTextView);

            vh.CommonIv=(ImageView)itemView.findViewById(R.id.CommonIv);
            vh.niceIv=(ImageView)itemView.findViewById(R.id.niceIv);

            vh.image1=(ImageView)itemView.findViewById(R.id.image1);
            vh.imageView2=(ImageView)itemView.findViewById(R.id.imageView2);
            vh.imageView3=(ImageView)itemView.findViewById(R.id.imageView3);

            vh.ringLayout=(LinearLayout) itemView.findViewById(R.id.ringLayout);
            vh.leftLayout=(LinearLayout)itemView.findViewById(R.id.leftLayout);
            vh.DetailLayout=(LinearLayout)itemView.findViewById(R.id.DetailLayout);
            vh.jc_video=(JCVideoPlayerStandard)itemView.findViewById(R.id.jc_video);
           vh.addressTextViewimage=(TextView)itemView.findViewById(R.id.addressTextViewimage);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }


//        final List<String> picList = new ArrayList<>();
//        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-001.jpg");
//        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-002.jpg");
//        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-003.jpg");
//        picList.add("http://img.ivsky.com/img/bizhi/pre/201601/27/february_2016-004.jpg");
//        picList.add("http://img.ivsky.com/img/tupian/pre/201511/16/chongwugou.jpg");
//        final String content ="用户分享测试测试分享图片";
//
        if(ismanage){
            vh.deleteText.setVisibility(View.VISIBLE);
            vh.shareText.setVisibility(View.GONE);
        }else{
            vh.deleteText.setVisibility(View.GONE);
            vh.shareText.setVisibility(View.VISIBLE);
        }
        vh.deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TieZiController.getInstance().DeletTieeZi(new ViewNetCallBack() {
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
                        try {
                            JSONObject object = new JSONObject(o.toString());
                            if (object.getBoolean("status")) {
                                ToastTool.show("删除帖子成功");
                                getItems().remove(position);
                                notifyDataSetChanged();
                            } else {
                                ToastTool.show(object.getString("message"));
                            }
                        } catch (Exception e) {
                            ToastTool.show("登录失败");
                        }
                    }
                },mo.getId(),UserPreference.getTOKEN());
            }
        });
        vh.personInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startOtherDetail(mContext,mo.getUserinfo().getId());
            }
        });
        Glide.with(mContext).load(mo.getUserinfo().getAvatar()).centerCrop().into(vh.personInfoIv);
        vh.nameTiezaiText.setText(mo.getUserinfo().getName());
        vh.addressTextview.setText(mo.getCity_name());
        vh.addressTextViewimage.setText(mo.getCircle());
        vh.contentID.setText(mo.getContent());
        if(StringTools.isNullOrEmpty(mo.getTopic())){
            vh.topTextView.setVisibility(View.GONE);
            vh.topTextView.setText("#"+mo.getTopic()+"#");
        }else{
            vh.topTextView.setVisibility(View.VISIBLE);
            vh.topTextView.setText(mo.getTopic());
        }

        vh.addressTextViewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    }, Integer.parseInt(mo.getCircle_id()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(mContext, model.getRedirect_url());
                } else {
                    IntentTools.startLogin(mContext);
                }
            }
        });
        vh.topTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTopicDetail(mContext,mo.getTopic_id(),mo.getTopic());
            }
        });

        vh.niceTextView.setText(mo.getDig_num());
        vh.commendTextView.setText(mo.getComment_num());
        vh.timeTieZiTextView.setText(getStrTime(mo.getCreate_time()));
        if(mo.getIs_dig().equals("0")){
            vh.niceIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tiezi_like));
        }else{
            vh.niceIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_image_bg));

        }
        vh.imagesListView.setVisibility(View.GONE);
        vh.jc_video.setVisibility(View.GONE);

        if(mo.getImages()!=null&&mo.getImages().size()>0) {
            vh.imagesListView.setVisibility(View.VISIBLE);

            if(mo.getImages().size()==0){
                vh.imagesListView.setVisibility(View.GONE);
            }else if(mo.getImages().size()==1){
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.GONE);
                vh.imageView3.setVisibility(View.GONE);

                try{
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                    vh.image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();

                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,0);
                        }
                    });
                }catch (Exception e){
                    Logger.e("sd"+e.getMessage());
                }

            }else if(mo.getImages().size()==2){
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.VISIBLE);
                vh.imageView3.setVisibility(View.GONE);
                try{
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                    Glide.with(mContext).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);
                    vh.image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();
                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,0);
                        }
                    });
                    vh.imageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();
                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,1);
                        }
                    });

                }catch (Exception e){
                    com.base.platform.utils.android.Logger.e("sd"+e.getMessage());
                }
            }else{
                vh.imagesListView.setVisibility(View.VISIBLE);
                vh.image1.setVisibility(View.VISIBLE);
                vh.imageView2.setVisibility(View.VISIBLE);
                vh.imageView3.setVisibility(View.VISIBLE);
                try{
                    Glide.with(mContext).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                    Glide.with(mContext).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);
                    Glide.with(mContext).load(mo.getImages().get(2).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView3);
                    vh.image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();
                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,0);
                        }
                    });
                    vh.imageView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();
                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,1);
                        }
                    });
                    vh.imageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> picListImage = new ArrayList<>();
                            Logger.e("erer"+position+picListImage.size());
                            for(int i=0;i<mo.getImages().size();i++){
                                picListImage.add(mo.getImages().get(i).getUrl_middle());
                            }
                            IntentTools.StartImageActivity(mContext,picListImage,2);
                        }
                    });
                }catch (Exception e){
                    Logger.e("sd"+e.getMessage());
                }
            }
            if(mo.getImages().size()<=3){
                vh.numbeTV.setVisibility(View.GONE);
            }else{
                vh.numbeTV.setVisibility(View.VISIBLE);
                vh.numbeTV.setText(mo.getImages().size()+"张");
            }

        }else{
            try{
                if(mo.getVideo_info()!=null){
                    if(!mo.getVideo_info().getUrl().equals("")){
                        vh.jc_video.setVisibility(View.VISIBLE);
                        vh.jc_video.setUp(mo.getVideo_info().getUrl()
                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
//                        if((mo.getVideo_info().getCover().equals(""))){
//                            Picasso.with(mContext)
//                                    .load(R.drawable.default_pictures)
//                                    .into(vh.jc_video.thumbImageView);
//                        }else{
//                            Picasso.with(mContext)
//                                    .load(mo.getVideo_info().getCover())
//                                    .into(vh.jc_video.thumbImageView);
//                        }
//                        Picasso.with(mContext)
//                                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
//                                .into( vh.jc_video.thumbImageView);
//                        vh.jc_video.thumbImageView.setImageBitmap(createVideoThumbnail(mo.getVideo_info().getUrl(),500,400));
                        new MyTask(mo.getVideo_info().getUrl(),vh.jc_video.thumbImageView).execute();
                        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                    }else{
                        vh.jc_video.setVisibility(View.GONE);
                    }
                }
            }catch (Exception e){
                Logger.e("ee"+e.getMessage());
            }
        }


        vh.leftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                    Logger.e("logger"+mo.getIs_dig());
                    if(mo.getIs_dig().equals("0")){
                        int niceNumber=Integer.parseInt(mo.getDig_num());
                        niceNumber++;
                        mo.setDig_num(niceNumber+"");
                        TieZiController.getInstance().setDig(new ViewNetCallBack() {
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

                            }
                        }, mo.getId(), "set");
//                        dialogListener.onClickNice(mo,v,position);
                    }else{
                        int niceNumber=Integer.parseInt(mo.getDig_num());
                        if(niceNumber>0){
                            niceNumber--;
                            mo.setDig_num(niceNumber+"");
                            TieZiController.getInstance().setDig(new ViewNetCallBack() {
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

                                }
                            }, mo.getId(), "cancel");
//                            dialogListener.onCancelClickNice(mo,v,position);
                        }
                    }
                    mo.setIs_dig(mo.getIs_dig().equals("0")?"1":"0");
                    notifyDataSetChanged();
                }else{
                    ToastTool.show("您还没有登录");
                }
            }
        });

        final List<String> picList = new ArrayList<>();
        picList.clear();
        for(int i=0;i<mo.getImages().size();i++){
            picList.add(mo.getImages().get(i).getUrl_middle());
        }
        final String content =mo.getContent();

        vh.DetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
//        //点击缩略图看大图
//        vh.imagesListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mContext, picList);
//                imagPagerUtil.setContentText(content);
//                imagPagerUtil.show();
//            }
//        });
        vh.shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharePopUpDialog(mo);
                Logger.e("aass");
                dialogListener.PopDialog(mo,v,position);
            }
        });
//
        vh.ringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startTieZiDetail(mContext,mo.getId());
            }
        });
//        //点击缩略图看大图
//        vh.imagesListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                IntentTools.startTieZiDetail(mContext,mo.getId());
//                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mContext, picList);
//                imagPagerUtil.setContentText(content);
//                imagPagerUtil.show();
//            }
//        });
        return itemView;
    }
    private DialogListener dialogListener;
    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }
    public interface DialogListener{
        void onClickNice(TieZiDetailModel model,View v,int position);
        void onCancelClickNice(TieZiDetailModel model,View v,int position);
        void CommentLayout(TieZiDetailModel model,View v,int position);
        void PopDialog(TieZiDetailModel model,View v,int position);
    }
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
        LinearLayout DetailLayout;
        CircleImageView personInfoIv;
        JCVideoPlayerStandard jc_video;
        TextView nameTiezaiText,deleteText,timeTieZiTextView,addressTextview,contentID,commendTextView,niceTextView,numbeTV,topTextView,addressTextViewimage;
        ImageView CommonIv,shareText,niceIv,image1,imageView2,imageView3;
        RelativeLayout imagesListView;
        LinearLayout ringLayout,leftLayout;
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
