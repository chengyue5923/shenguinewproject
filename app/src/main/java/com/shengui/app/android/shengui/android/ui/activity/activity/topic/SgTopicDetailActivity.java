package com.shengui.app.android.shengui.android.ui.activity.activity.topic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgTieZiDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushTieZiDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareRemovePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.fragment.HotGuiMiDetailFragment;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.models.TieZiDetailListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2016/12/29.
 */

public class SgTopicDetailActivity extends BaseActivity implements View.OnClickListener, ListView.OnItemClickListener, ShareOtherPopUpDialog.OnClickLintener,SharePopUpDialog.DialogShareListener {

    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityTieziListAdapter adapter;
    List<ProductModel> listDate;
    String topicId = "";
    SharePopUpDialog PopUpDialogs;
    @Bind(R.id.titleTv)
    TextView titleTv;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.topicTv)
    TextView topicTv;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private int positonDelete;
    String topictext;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        listview.setFocusable(false);
        listDate = new ArrayList<>();
        adapter = new ActivityTieziListAdapter(this);
        listview.setAdapter(adapter);
        PopUpDialogs = new SharePopUpDialog(this);
        PopUpDialogs.setDialogListener(this);
        regToWx();
    }

    @Override
    protected void initEvent() {
        topicTv.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        Buylayout.setOnClickListener(this);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
    }
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, W_APPID, true);
        api.registerApp(W_APPID);
    }
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.topicTv:
                IntentTools.startTopicList(this);
                break;
            case R.id.Buylayout:
//                IntentTools.startPushTiezi(this,titleTv.getText().toString());
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    UserPreference.setTOPICID(topicId);
                    UserPreference.setTOPICCONTENT(titleTv.getText().toString());
                    PopUpDialog((String) getIntent().getSerializableExtra("title"), topicId);
                } else {
                    IntentTools.startLogin(this);
                }

                break;
        }
    }

    //发布弹框
    public void PopUpDialog(String title, String topicId) {
        SgActivityPushTieZiDialog PopUpDialogs = new SgActivityPushTieZiDialog(this, title, topicId);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    protected void initData() {

        if (getIntent().getSerializableExtra("topic") != null) {
            topicId = (String) getIntent().getSerializableExtra("topic");
            if (getIntent().getSerializableExtra("title") != null) {
                topictext=(String) getIntent().getSerializableExtra("title");
                titleTv.setText((String) getIntent().getSerializableExtra("title"));
            }
            GuiMiController.getInstance().TopicTieZiList(this, topicId, 0, 20);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_detail_item_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        swipeLayout.setRefreshing(false);
        if (flag == HttpConfig.TopicTieZiList.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    TieZiDetailListModel model = (TieZiDetailListModel) result;
                    adapter.setRes(model.getResult());
                    if(StringTools.isNullOrEmpty(titleTv.getText().toString())){
                        titleTv.setText(model.getResult().get(0).getTopic()+"");
                    }
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }

        }
        if (flag == HttpConfig.setTop.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show(jsonObject.getString("data"));
//                    initData();
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
        if (flag == HttpConfig.setDigest.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show(jsonObject.getString("data"));
//                    initData();
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
        if (flag == HttpConfig.Favoriteadd.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show("收藏成功");
//                    initData();
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
        if (flag == HttpConfig.Favoritedel.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
                    ToastTool.show("取消收藏");
//                    initData();
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
        if (flag == HttpConfig.quanzhuDelete.getType()) {
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                if (jsonObject.getBoolean("status")) {
//                    ToastTool.show("删除");
                    adapter.removeByPosition(positonDelete);
                } else {
                    ToastTool.show(jsonObject.getString("message"));
                }
            } catch (Exception e) {

            }
        }
        if(flag==HttpConfig.DeleteTieZi.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    adapter.removeByPosition(positonDelete);
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

            //官方圈子
            if (QuanZiModel.getIs_public().equals("1")) {
                IntentTools.startQuanziManageOffical(SgTopicDetailActivity.this, QuanZiModel);
                return;
            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(SgTopicDetailActivity.this, Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(SgTopicDetailActivity.this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                IntentTools.startquanziDetail(SgTopicDetailActivity.this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                IntentTools.startQuanziDetailSelf(SgTopicDetailActivity.this, QuanZiModel);
                return;
            }
        }
    }

    @Override
    public void onclickShareItem(int flags, TieZiDetailModel model) {
        Logger.e("flagesss" + flags);
        if (flags == 0) {
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                if (model.getIs_favorite().equals("0")) {
                    GuiMiController.getInstance().Favoriteadd(this, UserPreference.getTOKEN(), model.getId(), "post");
                } else {
                    GuiMiController.getInstance().Favoritedel(this, UserPreference.getTOKEN(), model.getId(), "post");
                }
            } else {
                IntentTools.startLogin(this);
            }
        } else if (flags == 1) {
            ShareOtherPopUpDialog(model);
        } else if (flags == 2) {
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                ShareReportPopUpDialog(model);
            }else{
                IntentTools.startLogin(this);
            }
//            ShareReportPopUpDialog();
        } else if (flags == 3) {
//            ShareRemovePopUpDialog();
            if(model.getCircle_section()!=null&&model.getCircle_section().size()>0){
                ShareRemovePopUpDialog(model);
            }else{
                Logger.e("model");
                showCreateEidtCieclrDialog(model);
            }
        } else if (flags == 4) {
            if (model.getIs_digest().equals("0")) {
                TieZiController.getInstance().setDigest(this, model.getId(), "set");
            } else {
                TieZiController.getInstance().setDigest(this, model.getId(), "cancel");
            }
//                ShareJinghuaPopUpDialog();
        } else if (flags == 5) {
            if (model.getIs_top().equals("0")) {
                TieZiController.getInstance().setTop(this, model.getId(), "set");
            } else {
                TieZiController.getInstance().setTop(this, model.getId(), "cancel");
            }
//            ToastTool.show("已置顶");
        } else if (flags == 6) {
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                MineInfoController.getInstance().quanzhuDelete(this, UserPreference.getTOKEN(), model.getId());
            } else {
               IntentTools.startLogin(this);
            }
        }else if(flags==7){
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                TieZiController.getInstance().DeletTieeZi(this,model.getId(),UserPreference.getTOKEN());
            }else{
                IntentTools.startLogin(this);
            }
        }
    }
    private void showCreateEidtCieclrDialog(final  TieZiDetailModel model) {
        DialogFacory.getInstance().createAlertDialog(this, "创建板块", "发现您所在的圈子没有板块是否去创建板块", "创建", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IntentTools.startQuanziManage(SgTopicDetailActivity.this, Integer.parseInt(model.getCircle_id()));
            }
        }, null).show();
    }
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    @Override
    public void OnClick(TieZiDetailModel mo) {
        if (!api.isWXAppInstalled()) {
            ToastTool.show("您还未安装微信");
            return;
        }

        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = Constant.PostShareUrl+mo.getId();
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title =mo.getCity_name();
//        msg.description ="神龟网帖子分享";
        if(mo.getCircle().length()>14){
            msg.title =mo.getCircle().substring(0,14)+"...";
        }else{
            msg.title =mo.getCircle();
        }
        if(mo.getContent().length()>14){
            msg.description =mo.getContent().substring(0,14)+"...";
        }else{
            msg.description =mo.getContent();
        }


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;

        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = SendMessageToWX.Req.WXSceneSession ;

        api.sendReq(req);
    }


    public class ActivityTieziListAdapter extends BasePlatAdapter<TieZiDetailModel> {
        final Context mContext;

        public ActivityTieziListAdapter(Context context) {
            super(context);
            mContext = context;
            initImageLoader();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View itemView, ViewGroup parent) {
            ViewHolder vh;
            final TieZiDetailModel mo = getItem(position);
            if (itemView == null) {
                vh = new ViewHolder();
                itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_tie_zi_item_line, null);
                vh.imagesListView = (RelativeLayout) itemView.findViewById(R.id.imagesListView);
                vh.shareText = (ImageView) itemView.findViewById(R.id.shareText);
                vh.ringLayout = (LinearLayout) itemView.findViewById(R.id.ringLayout);
                vh.nameTiezaiText = (TextView) itemView.findViewById(R.id.nameTiezaiText);
                vh.timeTieZiTextView = (TextView) itemView.findViewById(R.id.timeTieZiTextView);
                vh.addressTV = (TextView) itemView.findViewById(R.id.addressTV);
                vh.contentTv = (TextView) itemView.findViewById(R.id.contentTv);
                vh.imageCount = (TextView) itemView.findViewById(R.id.imageCount);
                vh.addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
                vh.topTextView = (TextView) itemView.findViewById(R.id.topTextView);
                vh.commendTextView = (TextView) itemView.findViewById(R.id.commendTextView);
                vh.niceTextView = (TextView) itemView.findViewById(R.id.niceTextView);
                vh.image1 = (ImageView) itemView.findViewById(R.id.image1);
                vh.imageView2 = (ImageView) itemView.findViewById(R.id.imageView2);
                vh.CommonIv = (ImageView) itemView.findViewById(R.id.CommonIv);
                vh.imageView3 = (ImageView) itemView.findViewById(R.id.imageView3);
                vh.niceIv = (ImageView) itemView.findViewById(R.id.niceIv);
                vh.personInfoIv = (CircleImageView) itemView.findViewById(R.id.personInfoIv);
                vh.personLayout = (LinearLayout) itemView.findViewById(R.id.personLayout);

                vh.jc_video = (JCVideoPlayerStandard) itemView.findViewById(R.id.jc_video);
                itemView.setTag(vh);
            } else {
                vh = (ViewHolder) itemView.getTag();
            }
            if (StringTools.isNullOrEmpty(mo.getUserinfo().getAvatar())) {
                Glide.with(mContext).load(R.drawable.default_image).centerCrop().into(vh.personInfoIv);
            } else {
                Glide.with(mContext).load(mo.getUserinfo().getAvatar()).centerCrop().into(vh.personInfoIv);
            }

            vh.nameTiezaiText.setText(mo.getUserinfo().getName());
            vh.addressTV.setText(mo.getCity_name());
            vh.contentTv.setText(mo.getContent());
            if (mo.getTopic().equals("")) {
                vh.topTextView.setText(topictext );
            } else {
                vh.topTextView.setText(mo.getTopic());
            }
            vh.topTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTopicDetail(SgTopicDetailActivity.this,mo.getTopic_id(),mo.getTopic());
                }
            });
            vh.addressTextView.setText(mo.getCircle());
            vh.addressTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(SgTopicDetailActivity.this, Integer.parseInt(mo.getCircle_id()), UserPreference.getUid());
//                        IntentTools.startQuanziDetailById(SgTopicDetailActivity.this, model.getRedirect_url());
                    } else {
                        IntentTools.startLogin(SgTopicDetailActivity.this);
                    }
                }
            });
            vh.niceTextView.setText(mo.getDig_num());
            vh.commendTextView.setText(mo.getComment_num());
            vh.timeTieZiTextView.setText(getStrTime(mo.getCreate_time()));
            if (mo.getIs_dig().equals("0")) {
                vh.niceIv.setImageDrawable(getResources().getDrawable(R.drawable.tiezi_like));
            } else {
                vh.niceIv.setImageDrawable(getResources().getDrawable(R.drawable.like_image_bg));

            }
            if (mo.getTag() != null && mo.getTag().size() > 0) {
                for (int i = 0; i < mo.getTag().size(); i++) {
                    RelativeLayout reservedLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.activity_tag_activity, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    reservedLayout.setLayoutParams(layoutParams);
                    TextView QuanzitypeText = (TextView) reservedLayout.findViewById(R.id.QuanzitypeText);
                    if (mo.getTag().get(i).getName().equals("官方")) {
                        QuanzitypeText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_guanfang));
                    }
                    if (mo.getTag().get(i).getName().equals("名人")) {
                        QuanzitypeText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_mingren));
                    }
                    QuanzitypeText.setText(mo.getTag().get(i).getName());
                    vh.personLayout.addView(reservedLayout);
                }
            }
            vh.imagesListView.setVisibility(View.GONE);
            vh.jc_video.setVisibility(View.GONE);
            if (mo.getImages() != null && mo.getImages().size() > 0) {
                if (mo.getImages().size() == 0) {
                    vh.imagesListView.setVisibility(View.GONE);
                } else if (mo.getImages().size() == 1) {
                    vh.imagesListView.setVisibility(View.VISIBLE);
                    vh.image1.setVisibility(View.VISIBLE);
                    vh.imageView2.setVisibility(View.GONE);
                    vh.imageView3.setVisibility(View.GONE);

                    try {
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                        vh.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();

                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,0);
                            }
                        });
                    } catch (Exception e) {
                        Logger.e("sd" + e.getMessage());
                    }

                } else if (mo.getImages().size() == 2) {
                    vh.imagesListView.setVisibility(View.VISIBLE);
                    vh.image1.setVisibility(View.VISIBLE);
                    vh.imageView2.setVisibility(View.VISIBLE);
                    vh.imageView3.setVisibility(View.GONE);
                    try {
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);
                        vh.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();

                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,0);
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
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,1);
                            }
                        });
                    } catch (Exception e) {
                        Logger.e("sd" + e.getMessage());
                    }
                } else {
                    vh.imagesListView.setVisibility(View.VISIBLE);
                    vh.image1.setVisibility(View.VISIBLE);
                    vh.imageView2.setVisibility(View.VISIBLE);
                    vh.imageView3.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.image1);
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView2);
                        Glide.with(SgTopicDetailActivity.this).load(mo.getImages().get(2).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.imageView3);
                        vh.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();

                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,0);
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
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,1);
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
                                IntentTools.StartImageActivity(SgTopicDetailActivity.this,picListImage,2);
                            }
                        });

                    } catch (Exception e) {
                        Logger.e("sd" + e.getMessage());
                    }
                }
                vh.imageCount.setText(mo.getImages().size() + "张");
            } else {
                if (mo.getVideo_info() != null) {
                    if (!mo.getVideo_info().getUrl().equals("")) {
                        vh.jc_video.setVisibility(View.VISIBLE);
                        vh.jc_video.setUp(mo.getVideo_info().getUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
//                        if ((mo.getVideo_info().getCover().equals(""))) {
//                            Picasso.with(mContext).load(R.drawable.default_pictures).into(vh.jc_video.thumbImageView);
//                        } else {
//                            Picasso.with(mContext).load(mo.getVideo_info().getCover()).into(vh.jc_video.thumbImageView);
//                        }
//                            Picasso.with(this)
//                                    .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
//                                    .into(jcVideo.thumbImageView);
//                        vh.jc_video.thumbImageView.setImageBitmap(createVideoThumbnail(mo.getVideo_info().getUrl(),500,400));
                        new MyTask(mo.getVideo_info().getUrl(),  vh.jc_video.thumbImageView).execute();
                        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                    } else {
                        vh.jc_video.setVisibility(View.GONE);
                    }
                }


            }

            vh.niceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        if (mo.getIs_dig().equals("0")) {
                            int niceNumber = Integer.parseInt(mo.getDig_num());
                            niceNumber++;
                            mo.setDig_num(niceNumber + "");
                            TieZiController.getInstance().setDig(SgTopicDetailActivity.this, mo.getId(), "set");
                        } else {
                            int niceNumber = Integer.parseInt(mo.getDig_num());
                            if (niceNumber > 0) {
                                niceNumber--;
                                mo.setDig_num(niceNumber + "");
                                TieZiController.getInstance().setDig(SgTopicDetailActivity.this, mo.getId(), "cancel");
                            }

                        }
                        mo.setIs_dig(mo.getIs_dig().equals("0") ? "1" : "0");
                        notifyDataSetChanged();
                    } else {
                        IntentTools.startLogin(mContext);

                    }
                }
            });

            vh.contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(SgTopicDetailActivity.this, mo.getId());
                }
            });
            vh.ringLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(SgTopicDetailActivity.this, mo.getId());
                }
            });
            final List<String> picList = new ArrayList<>();
            picList.clear();
            for (int i = 0; i < mo.getImages().size(); i++) {
                picList.add(mo.getImages().get(i).getUrl_middle());
            }
            final String content = mo.getContent();
            vh.ringLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(SgTopicDetailActivity.this, mo.getId());
                }
            });
            //点击缩略图看大图
//            vh.imagesListView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    IntentTools.startTieZiDetail(SgTopicDetailActivity.this, mo.getId());
//                    ImagPagerUtil imagPagerUtil = new ImagPagerUtil(SgTopicDetailActivity.this, picList);
//                    imagPagerUtil.setContentText(content);
//                    imagPagerUtil.show();
//                }
//            });
            vh.shareText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positonDelete=position;
                    SharePopUpDialog(mo);
                }
            });
            return itemView;
        }

        class ViewHolder {
            RelativeLayout imagesListView;
            TextView nameTiezaiText, timeTieZiTextView, addressTV, contentTv, imageCount, addressTextView, topTextView, commendTextView, niceTextView;
            ImageView shareText, image1, imageView2, imageView3, CommonIv, niceIv;
            CircleImageView personInfoIv;
            LinearLayout ringLayout, personLayout;
            JCVideoPlayerStandard jc_video;
        }

        public void initImageLoader() {
            // This configuration tuning is custom. You can tune every option, you
            // may tune some of them,
            // or you can create default configuration by
            // ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove for release app
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);
        }
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
    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }

    //分享举报收藏弹窗
    public void SharePopUpDialog(TieZiDetailModel m) {   //弹框
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.setModel(m);
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //分享弹窗
    public void ShareOtherPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(this,model);
        PopUpDialogs.listener(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(this,new QuanziList(),model.getId());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //移动弹窗
    public void ShareRemovePopUpDialog(TieZiDetailModel model) {   //弹框
        ShareRemovePopUpDialog PopUpDialogs = new ShareRemovePopUpDialog(this,model);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //精华弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvationPopUpDialog PopUpDialogs = new ShareInvationPopUpDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
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
