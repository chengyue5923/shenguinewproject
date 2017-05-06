package com.shengui.app.android.shengui.android.ui.fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.dialog.ShareInvationPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareOtherPopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareRemovePopUpDialog;
import com.shengui.app.android.shengui.android.ui.dialog.ShareReportPopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.ex.NoDataListExpetion;
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

import org.json.JSONException;
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

public class HotGuiMiDetailFragment extends Fragment implements ViewNetCallBack , SharePopUpDialog.DialogShareListener,ShareOtherPopUpDialog.OnClickLintener {
    private RecyclerView mRecyclerView;
    SharePopUpDialog PopUpDialogs;
    private List<TieZiDetailModel> mDatas;
//    private EmptyLayout mEmptyLayout;
    private int firstNumber=0;
    private int size=10;
    private final String W_APPID= Constant.WXIDAPP_ID;
    private IWXAPI api;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        mEmptyLayout = new EmptyLayout(getActivity(), mRecyclerView);
        PopUpDialogs = new SharePopUpDialog(getActivity());
        PopUpDialogs.setDialogListener(this);
        mDatas = new ArrayList<TieZiDetailModel>();
        initdata();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount()-1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom =  recyclerView.getBottom()-recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition  = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if(lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount()-1 ){
//                    Toast.makeText(RecyclerActivity.this, "滑动到底了", Toast.LENGTH_SHORT).show();
                    Logger.e("aaaaaaaaaa------------");
                    firstNumber=firstNumber+size;
                    TieZiController.getInstance().getNewTieZiList(HotGuiMiDetailFragment.this,firstNumber,size,"hot");
                }
            }

        });
        regToWx();
        return view;
    }
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(getActivity(), W_APPID, true);
        api.registerApp(W_APPID);
    }
    private int deletepositon;
    private void initdata() {
        firstNumber=0;
        TieZiController.getInstance().getNewTieZiList(HotGuiMiDetailFragment.this,firstNumber,size,"hot");
    }

    @Override
    public void onConnectStart() {
    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {
        if (e instanceof NoDataListExpetion){
            emptyViewVisiable();
        }
        else if(firstNumber==0){
            emptyViewVisiable();
        }
    }
    private void showCreateEidtCieclrDialog(final  TieZiDetailModel model) {
        DialogFacory.getInstance().createAlertDialog(getActivity(), "创建板块", "发现您所在的圈子没有板块是否去创建板块", "创建", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IntentTools.startQuanziManage(getActivity(), Integer.parseInt(model.getCircle_id()));
            }
        }, null).show();
    }
    MyAdapter adapter;
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
//        mEmptyLayout.showSuccess(false);
        Logger.e("result"+result);
        if (flag == HttpConfig.getnewlist.getType()) {
            TieZiDetailListModel model = (TieZiDetailListModel) result;
            Logger.e("--------------" + model.getCount() + "" + model.getResult());
            mDatas=model.getResult();
            try {
                JSONObject js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    if(mDatas.size()!=0){
                        if(firstNumber==0){
                            mRecyclerView.removeAllViews();
                            adapter=new MyAdapter(mDatas);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else{
                            adapter.AppData(mDatas);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    if(firstNumber==0){
                        mRecyclerView.removeAllViews();
                        emptyViewVisiable();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            mRecyclerView.setAdapter(new  MyAdapter(mDatas));

        }
        if(flag==HttpConfig.setTop.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show(jsonObject.getString("data"));
//                    initdata();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        } if(flag==HttpConfig.setDigest.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show(jsonObject.getString("data"));
//                    initdata();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        } if(flag==HttpConfig.Favoriteadd.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("收藏成功");
//                    initdata();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }if(flag==HttpConfig.Favoritedel.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    ToastTool.show("取消收藏");
//                    initdata();
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }
        if(flag==HttpConfig.quanzhuDelete.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
//                    ToastTool.show("删除");
                    adapter.removewPosition(deletepositon);
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

            }
        }
        if(flag==HttpConfig.DeleteTieZi.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    adapter.removewPosition(deletepositon);
                }else{
                    ToastTool.show(jsonObject.getString("message"));
                }
            }catch (Exception e){

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
    @Override
    public void onclickShareItem(int flags, TieZiDetailModel model) {
            Logger.e("flagesss" + flags);
            if (flags == 0) {
//                ToastTool.show("已收藏");
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(model.getIs_favorite().equals("0")){
                        GuiMiController.getInstance().Favoriteadd(this,UserPreference.getTOKEN(),model.getId(),"post");
                    }else{
                        GuiMiController.getInstance().Favoritedel(this,UserPreference.getTOKEN(),model.getId(),"post");
                    }
                }else{

                    IntentTools.startLogin(getActivity());
                }
            } else if (flags == 1) {
                ShareOtherPopUpDialog(model);
            } else if (flags == 2) {
                ShareReportPopUpDialog(model);
            } else if (flags == 3) {
                if(model.getCircle_section()!=null&&model.getCircle_section().size()>0){
                    ShareRemovePopUpDialog(model);

                }else{
                    showCreateEidtCieclrDialog(model);
                    Logger.e("dfpop");
                }
            } else if (flags == 4) {
                if(model.getIs_digest().equals("0")){
                    TieZiController.getInstance().setDigest(this,model.getId(),"set");
                }else{
                    TieZiController.getInstance().setDigest(this,model.getId(),"cancel");
                }
//                ShareJinghuaPopUpDialog();
            } else if (flags == 5) {
                if(model.getIs_top().equals("0")){
                    TieZiController.getInstance().setTop(this,model.getId(),"set");
                }else{
                    TieZiController.getInstance().setTop(this,model.getId(),"cancel");
                }
//            ToastTool.show("已置顶");
            }else if(flags==6) {
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                    MineInfoController.getInstance().quanzhuDelete(this, UserPreference.getTOKEN(), model.getId());
                } else {
                   IntentTools.startLogin(getActivity());

                }
            }else if(flags==7){
                if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                TieZiController.getInstance().DeletTieeZi(this,model.getId(),UserPreference.getTOKEN());
                } else {
                    IntentTools.startLogin(getActivity());

                }
            }
    }

    public void  emptyViewVisiable(){
        mDatas = new ArrayList<>();
        mRecyclerView.setVisibility(View.VISIBLE);
        for (int i=0;i<20;i++){
            TieZiDetailModel m = new TieZiDetailModel();
            mDatas.add(m);
        }
        adapter=new HotGuiMiDetailFragment.MyAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            ImageLoader.getInstance().destroy();
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            ImageLoader.getInstance().destroy();
        }catch (Exception e){

        }
    }

    @Override
    public void OnClick(TieZiDetailModel mo) {
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl =Constant.PostShareUrl+mo.getId();
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
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
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<TieZiDetailModel> mDatas;

        //创建构造参数，用来接受数据集
        public MyAdapter(List<TieZiDetailModel> datas) {
            this.mDatas = datas;
            initImageLoader();
        }
        public void AppData(List<TieZiDetailModel> datas){
//            if(datas.size()!=0){
//                for(TieZiDetailModel M:datas){
//                    mDatas.add(M);
//                }
//            }
            mDatas.addAll(datas);
        }
        public void removewPosition(int position){
            mDatas.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
        public void initImageLoader() {
            // This configuration tuning is custom. You can tune every option, you
            // may tune some of them,
            // or you can create default configuration by
            // ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    getActivity().getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .writeDebugLogs() // Remove for release app
                    .build();
            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);
        }
        //创建ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //加载布局文件，记得传入parent而不是null
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_activity_tie_zi_item_line, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        //绑定ViewHolder
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final TieZiDetailModel mo=mDatas.get(position);
            if (StringTools.isNullOrEmpty(mo.getId())){
                holder.viewall.setVisibility(View.GONE);
                holder.driver.setVisibility(View.GONE);
                if (position==0){
                    holder.showTv.setVisibility(View.VISIBLE);
                    holder.showTv.setText("抱歉，没有符合的供求信息");
                }else {
                    holder.showTv.setVisibility(View.INVISIBLE);
                }


                return;
            }
            holder.viewall.setVisibility(View.VISIBLE);
            holder.driver.setVisibility(View.VISIBLE);
            holder.showTv.setVisibility(View.GONE);

            Glide.with(getActivity()).load(mo.getUserinfo().getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(holder.personInfoIv);
//            Glide.with(getActivity()).load(mo.getUserinfo().getAvatar()).centerCrop().into(holder.personInfoIv);
            holder.nameTiezaiText.setText(mo.getUserinfo().getName());
            if(StringTools.isNullOrEmpty(mo.getCity_name())){
                holder.addressTV.setVisibility(View.GONE);
                holder.addressTV.setText(mo.getCity_name());
            }else{
                holder.addressTV.setVisibility(View.VISIBLE);
                holder.addressTV.setText(mo.getCity_name());
            }
            holder.contentTv.setText(mo.getContent());
            if(!mo.getTopic_id().equals("0")){
                holder.topTextView.setVisibility(View.VISIBLE);
                holder.topTextView.setText("#"+mo.getTopic()+"#");
            }else{
                holder.topTextView.setVisibility(View.GONE);
            }
            if(StringTools.isNullOrEmpty(mo.getCircle())){
                holder.addressTextView.setVisibility(View.GONE);
            }else{
                holder.addressTextView.setVisibility(View.VISIBLE);
                holder.addressTextView.setText(mo.getCircle());
            }

            holder.niceTextView.setText(mo.getDig_num());
            holder.commendTextView.setText(mo.getComment_num());
            holder.timeTieZiTextView.setText(getStrTime(mo.getCreate_time()));
            if(mo.getIs_dig().equals("0")){
                holder.niceIv.setImageDrawable(getResources().getDrawable(R.drawable.tiezi_like));
            }else{
                holder.niceIv.setImageDrawable(getResources().getDrawable(R.drawable.like_image_bg));

            }
            holder.personInfoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startOtherDetail(getActivity(),mo.getUserinfo().getId());
                }
            });
            holder.imagesListView.setVisibility(View.GONE);
            holder.jc_video.setVisibility(View.GONE);
            if(mo.getImages()!=null&&mo.getImages().size()>0) {
                holder.imagesListView.setVisibility(View.VISIBLE);
                if (mo.getImages().size() == 0) {
                    holder.imagesListView.setVisibility(View.GONE);
                } else if (mo.getImages().size() == 1) {
                    holder.imagesListView.setVisibility(View.VISIBLE);
                    holder.image1.setVisibility(View.VISIBLE);
                    holder.imageView2.setVisibility(View.GONE);
                    holder.imageView3.setVisibility(View.GONE);

                    try {
                        Glide.with(getActivity()).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.image1);
                        holder.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();

                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,0);
                            }
                        });
                    } catch (Exception e) {
                        com.base.platform.utils.android.Logger.e("sd" + e.getMessage());
                    }

                } else if (mo.getImages().size() == 2) {
                    holder.imagesListView.setVisibility(View.VISIBLE);
                    holder.image1.setVisibility(View.VISIBLE);
                    holder.imageView2.setVisibility(View.VISIBLE);
                    holder.imageView3.setVisibility(View.GONE);
                    try {
                        Glide.with(getActivity()).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.image1);
                        Glide.with(getActivity()).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.imageView2);
                        holder.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();
                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,0);
                            }
                        });
                        holder.imageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();
                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,1);
                            }
                        });

                    } catch (Exception e) {
                        com.base.platform.utils.android.Logger.e("sd" + e.getMessage());
                    }
                } else {
                    holder.imagesListView.setVisibility(View.VISIBLE);
                    holder.image1.setVisibility(View.VISIBLE);
                    holder.imageView2.setVisibility(View.VISIBLE);
                    holder.imageView3.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(getActivity()).load(mo.getImages().get(0).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.image1);
                        Glide.with(getActivity()).load(mo.getImages().get(1).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.imageView2);
                        Glide.with(getActivity()).load(mo.getImages().get(2).getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.imageView3);
                        holder.image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();
                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,0);
                            }
                        });
                        holder.imageView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();
                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,1);
                            }
                        });
                        holder.imageView3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArrayList<String> picListImage = new ArrayList<>();
                                Logger.e("erer"+position+picListImage.size());
                                for(int i=0;i<mo.getImages().size();i++){
                                    picListImage.add(mo.getImages().get(i).getUrl_middle());
                                }
                                IntentTools.StartImageActivity(getActivity(),picListImage,2);
                            }
                        });

                    } catch (Exception e) {
                        com.base.platform.utils.android.Logger.e("sd" + e.getMessage());
                    }
                }
            }else{
                try{
                    if(mo.getVideo_info()!=null){
                        if(!mo.getVideo_info().getUrl().equals("")){
                            holder.jc_video.setVisibility(View.VISIBLE);
                            holder.jc_video.setUp(mo.getVideo_info().getUrl()
                                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"");
//                            Picasso.with(getActivity())
//                                    .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
//                                    .into( holder.jc_video.thumbImageView);
//                            holder.jc_video.thumbImageView.setImageBitmap(createVideoThumbnail(mo.getVideo_info().getUrl(),500,400));
                            new MyTask(mo.getVideo_info().getUrl(), holder.jc_video.thumbImageView).execute();
                            JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
                        }else{
                            holder.jc_video.setVisibility(View.GONE);
                        }
                    }
                }catch (Exception e){
                    Logger.e("e.get"+e.getMessage());
                }
            }
            if(mo.getImages().size()>3){
                holder.imageCount.setVisibility(View.VISIBLE);
                holder.imageCount.setText(mo.getImages().size()+"张");
            }else{
                holder.imageCount.setVisibility(View.GONE);
            }
            holder.niceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1) {
                        Logger.e("logger"+mo.getIs_dig());
                        if(mo.getIs_dig().equals("0")){
                            int niceNumber=Integer.parseInt(mo.getDig_num());
                            niceNumber++;
                            mo.setDig_num(niceNumber+"");
                            TieZiController.getInstance().setDig(HotGuiMiDetailFragment.this,mo.getId(),"set");
                        }else{
                            int niceNumber=Integer.parseInt(mo.getDig_num());
                            if(niceNumber>0){
                                niceNumber--;
                                mo.setDig_num(niceNumber+"");
                                TieZiController.getInstance().setDig(HotGuiMiDetailFragment.this,mo.getId(),"cancel");
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

            holder.contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
                }
            });
            //点击缩略图看大图
//            holder.imagesListView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), picList);
//                    imagPagerUtil.setContentText(content);
//                    imagPagerUtil.show();
//                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
//                }
//            });
            holder.shareText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletepositon=position;
                    SharePopUpDialog(mo);
                }
            });
            holder.contentTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
                }
            });

            holder.ringLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
                }
            });
            holder.bottomLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletepositon=position;
                    SharePopUpDialog(mo);
                }
            });

            holder.titleReayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
                }
            });


            holder.timeRelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startTieZiDetail(getActivity(),mo.getId());
                }
            });
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }


    }
    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout imagesListView,bottomLayout,titleReayout,timeRelayout;
        TextView nameTiezaiText, timeTieZiTextView,addressTV,contentTv,imageCount,addressTextView,topTextView,commendTextView,niceTextView,showTv;
        ImageView shareText,image1,imageView2,imageView3,CommonIv,niceIv;
        CircleImageView personInfoIv;
        LinearLayout ringLayout;
        JCVideoPlayerStandard jc_video;
        View viewall,driver;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleReayout=(RelativeLayout)itemView.findViewById(R.id.titleReayout);
            timeRelayout=(RelativeLayout)itemView.findViewById(R.id.timeRelayout);

            bottomLayout=(RelativeLayout)itemView.findViewById(R.id.bottomLayout);
            jc_video=(JCVideoPlayerStandard)itemView.findViewById(R.id.jc_video);
            ringLayout=(LinearLayout)itemView.findViewById(R.id.ringLayout);
            nameTiezaiText = (TextView)itemView.findViewById(R.id.nameTiezaiText);
            timeTieZiTextView = (TextView)itemView.findViewById(R.id.timeTieZiTextView);
            addressTV = (TextView)itemView.findViewById(R.id.addressTV);
            contentTv = (TextView)itemView.findViewById(R.id.contentTv);
            imageCount = (TextView)itemView.findViewById(R.id.imageCount);
            addressTextView = (TextView)itemView.findViewById(R.id.addressTextView);
            topTextView = (TextView)itemView.findViewById(R.id.topTextView);
            commendTextView = (TextView)itemView.findViewById(R.id.commendTextView);
            niceTextView = (TextView)itemView.findViewById(R.id.niceTextView);
            imagesListView = (RelativeLayout)itemView.findViewById(R.id.imagesListView);

            shareText = (ImageView)itemView.findViewById(R.id.shareText);
            image1 = (ImageView)itemView.findViewById(R.id.image1);
            imageView2 = (ImageView)itemView.findViewById(R.id.imageView2);
            CommonIv = (ImageView)itemView.findViewById(R.id.CommonIv);
            imageView3 = (ImageView)itemView.findViewById(R.id.imageView3);
            niceIv = (ImageView)itemView.findViewById(R.id.niceIv);
            personInfoIv = (CircleImageView)itemView.findViewById(R.id.personInfoIv);
            viewall = itemView.findViewById(R.id.viewall);
            driver = itemView.findViewById(R.id.driver);
            showTv = (TextView)itemView.findViewById(R.id.showTv);
        }
    }
    //分享举报收藏弹窗
    public void SharePopUpDialog(TieZiDetailModel m) {   //弹框
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.setModel(m);
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //分享弹窗
    public void ShareOtherPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareOtherPopUpDialog PopUpDialogs = new ShareOtherPopUpDialog(getActivity(),model);
        PopUpDialogs.listener(this);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //举报弹窗
    public void ShareReportPopUpDialog(TieZiDetailModel model) {   //弹框
        ShareReportPopUpDialog PopUpDialogs = new ShareReportPopUpDialog(getActivity(),new QuanziList(),model.getId());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //移动弹窗
    public void ShareRemovePopUpDialog(TieZiDetailModel model) {   //弹框
        ShareRemovePopUpDialog PopUpDialogs = new ShareRemovePopUpDialog(getActivity(),model);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "SharePopUpDialog");
    }

    //精华弹窗
    public void ShareJinghuaPopUpDialog() {   //弹框
        ShareInvationPopUpDialog PopUpDialogs = new ShareInvationPopUpDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
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
    public void refresh(){
        Log.e("---","initdata----");
        initdata();
    }
}
