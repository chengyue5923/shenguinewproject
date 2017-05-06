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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.utilsview.DensityUtils;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.GongQiuController;
import com.shengui.app.android.shengui.controller.TieZiController;
import com.shengui.app.android.shengui.models.CityModel;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.GongQiuListModel;
import com.shengui.app.android.shengui.models.TieZiDetailModel;
import com.shengui.app.android.shengui.utils.android.EmptyLayout;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSelectDetailFragment extends Fragment implements ViewNetCallBack {
    private RecyclerView mRecyclerView;
    private TextView showTv;
    private List<GongQiuDetailModel> mDatas;
    private int firstNumber=0;
    private int size=10;
    private boolean canload=true;
    private String cityid="";
    private String typeid="";
    private String GongQiu="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        showTv = (TextView) view.findViewById(R.id.showTv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
                    if(canload){
                        firstNumber=firstNumber+size;
                        GongQiuController.getInstance().getGongQiuList(MainSelectDetailFragment.this,typeid,cityid,GongQiu,firstNumber,size);
                    }

                }
            }


        });
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        return view;
    }
    public void refresh(){
        if(model!=null){
            model.clear();
        }
        cityid="";
        typeid="";
        GongQiu="";
        initdata();
    }
    public void initdata() {

        firstNumber=0;
        canload=true;
        GongQiuController.getInstance().getGongQiuList(this,typeid,cityid,GongQiu,firstNumber,size);
    }
    public static List<CityModel> model;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IMStringEvent event) {
        Logger.e("currwent----------------------------"+event.getFlag());
        if(model!=null){
            model.clear();
        }
        List<CityModel> modelList=event.getCount();
        model=modelList;
        GongQiu="";
        cityid="";
        typeid="";
        for(CityModel m:modelList){
            Logger.e("addddddMainSelectDetailFragmentddd"+m.getName()+m.getId()+m.getTypeid());
            // 0 出售 1 求购
            if(m.getTypeid().equals("1")){
                if(m.getId().equals("0")){
                    GongQiu="1";
                }else{
                    GongQiu="2";
                }
            }else if(m.getTypeid().equals("2")){
                cityid=m.getId();
            }else if(m.getTypeid().equals("3")){
                typeid=m.getId();
            }
        }
        initdata();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
        if(ImageLoader.getInstance()!=null){
//            ImageLoader.getInstance().destroy();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConnectStart() {
    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {
        if(firstNumber==0){
            emptyViewVisiable();
        }
    }
    MyAdapter adapter;
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        try {
        JSONObject jsonObject=new JSONObject(o.toString());
        if (jsonObject.getBoolean("status")) {
            mRecyclerView.setVisibility(View.VISIBLE);
            showTv.setVisibility(View.GONE);
            GongQiuListModel  model=(GongQiuListModel)result;
            mDatas=model.getResult();
            Logger.e("moMainSelectDetailFragmentdel"+model.getResult()+model.getResult());
            Logger.e("jsonObject-------o.toString()------"+o.toString());
                    if(mDatas.size()!=0){
                    if(firstNumber==0){
                        adapter=new MyAdapter(mDatas);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        adapter.AppData(mDatas);
                        adapter.notifyDataSetChanged();
                    }
                    if(model.getResult().size()<10){
                        canload=false;
                    }
                }
        }else {
            if(firstNumber==0){
                mRecyclerView.setVisibility(View.VISIBLE);
                showTv.setVisibility(View.VISIBLE);
                emptyViewVisiable();
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void  emptyViewVisiable(){
        if (mDatas==null){
            mDatas = new ArrayList<>();
        }else{
            mDatas.clear();
        }
        for (int i=0;i<20;i++){
            GongQiuDetailModel m = new GongQiuDetailModel();
            mDatas.add(m);
        }
        adapter=new MyAdapter(mDatas);
        mRecyclerView.setAdapter(adapter);
    }
    //adapter

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<GongQiuDetailModel> mDatas;
        //创建构造参数，用来接受数据集
        public MyAdapter(List<GongQiuDetailModel> datas) {
            hashMap.clear();
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
            holder.rootlayout.setVisibility(View.VISIBLE);
            holder.driver.setVisibility(View.VISIBLE);
            holder.showTv.setVisibility(View.GONE);


            holder.productTitleNameText.setText(mo.getTitle());
            holder.detailTextView.setText(mo.getContents());
            holder.texttelTextView.setText(mo.getContact_mobile());
            if(StringTools.isNullOrEmpty(mo.getCity_name())){
                holder.textAddressText.setVisibility(View.GONE);
                holder.textAddressText.setText(mo.getCity_name());
            }else{
                holder.textAddressText.setVisibility(View.VISIBLE);
                holder.textAddressText.setText(mo.getCity_name());
                holder.textAddressText.setTextColor(getResources().getColor(R.color.viewfinder_laser));
            }

            if(mo.getType().equals("1")){
                holder.productBuyText.setText("出售");
                    holder.productBuyText.setBackground(getActivity().getResources().getDrawable(R.drawable.activity_product_sale_title));
            }else{
                holder.productBuyText.setText("求购");
                holder.productBuyText.setBackground(getActivity().getResources().getDrawable(R.drawable.activity_product_title));
            }
            holder.priductTimeText.setText(mo.getPub_time());
            holder.productBuyTypeText.setText(mo.getVariety());
            holder.rootlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("model"+mo.getId());
                    IntentTools.startGongQiuDetail(getActivity(),mo.getId());
                }
            });
            if(mo.getContact_user().equals("")){
                holder.textView.setText("以龟会友");
            }else{
                holder.textView.setText(mo.getContact_user());
            }
            if(StringTools.isNullOrEmpty(mo.getCover())){
//                holder.productImageView.setImageBitmap(createVideoThumbnail(mo.getVideo_info().getUrl(),200,160));
                new MyTask(mo.getVideo_info().getUrl(), holder.productImageView).execute();
                holder.videoImageView.setVisibility(View.VISIBLE);
            }else{
                Glide.with(getActivity()).load(mo.getCover()).asBitmap().override(400,320).placeholder(R.drawable.default_pictures).into(holder.productImageView);
                holder.videoImageView.setVisibility(View.GONE);
            }
        }
        @Override
        public int getItemCount() {
            return mDatas.size();
        }


    }
    HashMap<String,Bitmap> hashMap =  new HashMap<>();
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


            bitmap=createVideoThumbnail(url,400,320);
            hashMap.put(url,bitmap);

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
        View driver;
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
            driver = itemView.findViewById(R.id.driver);
        }
    }
}
