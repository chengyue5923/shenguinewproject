package com.shengui.app.android.shengui.android.ui.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.image.ImagPagerUtil;
import com.shengui.app.android.shengui.android.ui.dialog.SharePopUpDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.DensityUtils;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityProductListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.GongQiuListModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
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
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class OtherDetailFragment extends Fragment implements ViewNetCallBack {
    String otherid;
    int firstNumber;
    private RecyclerView mRecyclerView;
    int size =5;
    private List<GongQiuDetailModel> mDatas;
    private boolean isFocus=false;
    TextView showTv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        PopUpDialogs.setDialogListener(this);
        showTv=(TextView)view.findViewById(R.id.showTv);

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
                    if(isFocus){
                        firstNumber=firstNumber+size;
                        MineInfoController.getInstance().get_my_supply_byUid(OtherDetailFragment.this, firstNumber, size, otherid);
                    }
                }
            }

        });
        return view;
    }

    public void setOtherId(String otherId) {
        otherid = otherId;
        Logger.e("dotherIdd------------------" + otherId);
    }
    public void setFocus(boolean flag){
        isFocus=flag;
    }

    public void refresh(){
        firstNumber=0;
        initdata();
    }
    private void initdata() {
        MineInfoController.getInstance().get_my_supply_byUid(OtherDetailFragment.this, firstNumber, size, otherid);
    }

    @Override
    public void onConnectStart() {

    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {

    }
    public void  emptyViewVisiable(){
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }
        for (int i=0;i<20;i++){
            GongQiuDetailModel m = new GongQiuDetailModel();
            mDatas.add(m);
        }
        adapter=new MyAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);
    }
   MyAdapter adapter;
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.get_my_supply.getType()) {

            Logger.e("-----------get_my_supply-------"+o.toString());
//            GongQiuListModel model = (GongQiuListModel) result;
            List<GongQiuDetailModel> model=new ArrayList<>();
            mDatas=new ArrayList<>();
            try {
                JSONObject ja=new JSONObject(o.toString());

                if(ja.getBoolean("status")){
                    JSONObject hadata=ja.getJSONObject("data");

                    JSONArray jsonArray=hadata.getJSONArray("result");


                    model= GsonTool.jsonToArrayEntity(jsonArray.toString(),GongQiuDetailModel.class);

                    Logger.e("dffffffffffffffffff------"+model.size());
                    for(GongQiuDetailModel mL:model){
                        Logger.e("mo"+mL.getId());
                    }
                    Logger.e("dffffffffffffffffff--dddddddd----"+isFocus);
                    if(isFocus){
                        mDatas=model;
                        showTv.setVisibility(View.GONE);
                    }else{
                        if(model.size()>3){
                            mDatas.add(model.get(0));
                            mDatas.add(model.get(1));
                            mDatas.add(model.get(2));
                            GongQiuDetailModel m = new GongQiuDetailModel();
                            m.setId("-1");
                            mDatas.add(m);
//                            showTv.setVisibility(View.VISIBLE);
//                            showTv.setText("未关注对方，最多查看3条");
                        }else{
                            mDatas=model;
                        }
                    }
                    Logger.e("dffffffffffffffffff--dddddddd----"+model.size()+mDatas.size());
                    if(mDatas.size()!=0){
                        if(firstNumber==0){
                            adapter=new MyAdapter(mDatas);
                            mRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else{
                            adapter.AppData(mDatas);
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        if(firstNumber==0){
                            emptyViewVisiable();
                        }
                    }
                }else{
                    if(firstNumber==0){
                        emptyViewVisiable();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<GongQiuDetailModel> mDatas;

        //创建构造参数，用来接受数据集
        public MyAdapter(List<GongQiuDetailModel> datas) {
            this.mDatas = datas;
            initImageLoader();
        }
        public void AppData(List<GongQiuDetailModel> datas){
            mDatas.addAll(datas);
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
                    .inflate(R.layout.view_activity_item_line, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        //绑定ViewHolder
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            final GongQiuDetailModel mo=mDatas.get(position);
            if (StringTools.isNullOrEmpty(mo.getId())){
                holder.rootlayout.setVisibility(View.GONE);
                holder.driver.setVisibility(View.GONE);
                if (position==0){
                    holder.showTv.setVisibility(View.VISIBLE);
                    holder.showTv.setText("抱歉，没有符合的供求信息");
                }else {
                    holder.showTv.setVisibility(View.INVISIBLE);
                }
                return;
            }
            if (mo.getId().equals("-1")){
                holder.showTv.setVisibility(View.VISIBLE);
                holder.viewall.setVisibility(View.GONE);
                holder.driver.setVisibility(View.GONE);
                return;
            }
            holder.showTv.setVisibility(View.GONE);
            holder.viewall.setVisibility(View.VISIBLE);
            holder.driver.setVisibility(View.VISIBLE);

            holder.productTitleNameText.setText(mo.getTitle());
            holder.detailTextView.setText(mo.getContents());
            holder.texttelTextView.setText(mo.getContact_mobile());
            if(StringTools.isNullOrEmpty(mo.getCity_name())){
                holder.textAddressText.setVisibility(View.GONE);
                holder.textAddressText.setText(mo.getCity_name());

            }else{
                holder.textAddressText.setVisibility(View.VISIBLE);
                holder.textAddressText.setText(mo.getCity_name());

            }
            if(mo.getType().equals("1")){
                holder.productBuyText.setText("出售");
                holder.productBuyText.setBackground(getResources().getDrawable(R.drawable.activity_quanzi_confirm));
            }else{
                holder.productBuyText.setText("求购");
                holder.productBuyText.setBackground(getResources().getDrawable(R.drawable.activity_product_title));
            }
            holder.priductTimeText.setText(getStrTime(mo.getCreate_time()));
            holder.productBuyTypeText.setText(mo.getVariety());
            holder.rootlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentTools.startOtherGongQiuDetail(getActivity(),mo.getId());
                }
            });
            if(mo.getUserinfo().getName().equals("")){
                holder.textView.setVisibility(View.GONE);
            }else{
                holder.textView.setVisibility(View.VISIBLE);
                holder.textView.setText(mo.getUserinfo().getName());
            }

//            if(!mo.getCover().equals("")){
//                Picasso.with(getActivity()).load(mo.getCover()).resize(DensityUtils.dp2px(getActivity(),100), DensityUtils.dp2px(getActivity(),80))
//                        .into(holder.productImageView);
//            }else{
            if(StringTools.isNullOrEmpty(mo.getCover())){
                new MyTask(mo.getVideo_info().getUrl(), holder.productImageView).execute();
                holder.videoImageView.setVisibility(View.VISIBLE);
            }else{
                Glide.with(getActivity()).load(mo.getCover()).asBitmap().placeholder(R.drawable.default_pictures).into(holder.productImageView);
                holder.videoImageView.setVisibility(View.GONE);
            }
//            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
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
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView priductTimeText, productTitleNameText,productBuyText,productBuyTypeText,detailTextView,textView,texttelTextView,textAddressText,deleteItem,showTv;
        ImageView productImageView;
        RelativeLayout rootlayout;
        View viewall,driver;
        ImageView videoImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            videoImageView=(ImageView)itemView.findViewById(R.id.videoImageView);
            rootlayout=(RelativeLayout)itemView.findViewById(R.id.rootlayout);
            priductTimeText = (TextView)itemView.findViewById(R.id.priductTimeText);
            productTitleNameText = (TextView)itemView.findViewById(R.id.productTitleNameText);
            showTv = (TextView)itemView.findViewById(R.id.showTv);
            productBuyText = (TextView)itemView.findViewById(R.id.productBuyText);
            productBuyTypeText = (TextView)itemView.findViewById(R.id.productBuyTypeText);
            detailTextView = (TextView)itemView.findViewById(R.id.detailTextView);
            textView = (TextView)itemView.findViewById(R.id.textView);
            texttelTextView = (TextView)itemView.findViewById(R.id.texttelTextView);
            textAddressText = (TextView)itemView.findViewById(R.id.textAddressText);
            deleteItem = (TextView)itemView.findViewById(R.id.deleteItem);
            productImageView = (ImageView)itemView.findViewById(R.id.productImageView);
            viewall = itemView.findViewById(R.id.rootlayout);
            driver = itemView.findViewById(R.id.driver);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
