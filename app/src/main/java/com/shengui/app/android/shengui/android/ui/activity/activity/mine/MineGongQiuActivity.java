package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushGongQiuDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GongQiuController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.GongQiuListModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

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

/**
 * Created by admin on 2017/1/4.
 */

public class MineGongQiuActivity extends BaseActivity implements View.OnClickListener ,ScrollViewExtend.ObservableScrollViewCallbacks{
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.showmoreLayout)
    TextView showmoreLayout;
    @Bind(R.id.sentmoreLayout)
    TextView sentmoreLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    List<ProductModel> listDate;
    ActivityProductListAdapter adapter;
    @Bind(R.id.gongqiueimage)
    ImageView gongqiueimage;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    private int firstnumber=0;
    private int size=8;

    boolean canload=true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.statesText:
                if (statesText.getText().equals("管理")) {
                    statesText.setText("完成");
                    adapter.SetShowDelete(true);
                    adapter.notifyDataSetChanged();
                } else {
                    statesText.setText("管理");
                    adapter.SetShowDelete(false);
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.Buylayout:
//                IntentTools.startPushGongQiu(this);
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    PopUpDialog();
                }else{
                    ToastTool.show("您还没有登录");
                }

                break;
            case R.id.cancelTextView:
                finish();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        listDate = new ArrayList<>();
        adapter = new ActivityProductListAdapter(this);
        listview.setAdapter(adapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor, R.color.shenguiappcolor);
        scrollView.setCallbacks(this);
    }

    @Override
    protected void initEvent() {
        Buylayout.setOnClickListener(this);
        statesText.setOnClickListener(this);
        cancelTextView.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startGongQiuDetail(MineGongQiuActivity.this,adapter.getItem(position).getId());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(UserPreference.getISPOSTFINISHPOSR()!=null&&UserPreference.getISPOSTFINISHPOSR().length()>1){
            initData();
            UserPreference.setISPOSTFINISHPOSR("");
        }
    }
    @Override
    protected void initData() {
        canload=true;
        showmoreLayout.setVisibility(View.GONE);
        firstnumber=0;
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            MineInfoController.getInstance().get_my_supply(this, firstnumber, size, UserPreference.getTOKEN());
        } else {
            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_gongqiu_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.get_my_supply.getType()) {
            swipeLayout.setRefreshing(false);
            try {
                JSONObject js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    GongQiuListModel model = (GongQiuListModel) result;
                    Log.e("date----", model.getCount() + "");
                    if(firstnumber==0&&model.getResult().size()==0){
                        showmoreLayout.setVisibility(View.VISIBLE);
                    }else{
                        if(firstnumber==0){
                            adapter.clearAll();
                            adapter.setRes(model.getResult());
                        }else{
                            adapter.append(model.getResult());
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onScrollChanged(int scrollY) {

    }
    public class ActivityProductListAdapter extends BasePlatAdapter<GongQiuDetailModel> {
        Context mContext;
        boolean showdelete=false;
        HashMap<Integer,Object> hashMapIndex = new HashMap();
        public ActivityProductListAdapter(Context context) {
            super(context);
            mContext=context;
        }

        public void SetShowDelete(boolean is){
            showdelete=is;
        }
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View itemView, ViewGroup parent) {
            ViewHolder vh;
            final GongQiuDetailModel model = getItem(position);
            if (itemView == null) {
                vh = new ViewHolder();
                itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_item, null);
                vh.priductTimeText = (TextView)itemView.findViewById(R.id.priductTimeText);
                vh.productTitleNameText = (TextView)itemView.findViewById(R.id.productTitleNameText);
                vh.productBuyText = (TextView)itemView.findViewById(R.id.productBuyText);
                vh.productBuyTypeText = (TextView)itemView.findViewById(R.id.productBuyTypeText);
                vh.detailTextView = (TextView)itemView.findViewById(R.id.detailTextView);
                vh.textView = (TextView)itemView.findViewById(R.id.textView);
                vh.texttelTextView = (TextView)itemView.findViewById(R.id.texttelTextView);
                vh.textAddressText = (TextView)itemView.findViewById(R.id.textAddressText);
                vh.deleteItem = (TextView)itemView.findViewById(R.id.deleteItem);
                vh.productImageView = (ImageView)itemView.findViewById(R.id.productImageView);
                vh.videoImageView = (ImageView)itemView.findViewById(R.id.videoImageView);
                if(StringTools.isNullOrEmpty(model.getCover())){
                        new MyTask(model.getVideo_info().getUrl(),  vh.productImageView).execute();
                    hashMapIndex.put(position,"");
                    vh.videoImageView.setVisibility(View.VISIBLE);
//            vh.productImageView.setImageBitmap(createVideoThumbnail(model.getVideo_info().getUrl(),200,160));
                }else{
                    vh.videoImageView.setVisibility(View.GONE);
                    Glide.with(mContext).load(model.getCover()).asBitmap().placeholder(R.drawable.default_image).into(vh.productImageView);
                }

                itemView.setTag(vh);
            } else {
                vh = (ViewHolder) itemView.getTag();
            }
            if(!showdelete){
                if(StringTools.isNullOrEmpty(model.getCity_name())){
                    vh.textAddressText.setVisibility(View.GONE);
                }else{
                    vh.textAddressText.setVisibility(View.VISIBLE);
                    vh.textAddressText.setText(model.getCity_name());
                }
            }


            if(showdelete){
                vh.deleteItem.setVisibility(View.VISIBLE);
                vh.textAddressText.setVisibility(View.GONE);
            }else{
                vh.deleteItem.setVisibility(View.GONE);
                vh.textAddressText.setVisibility(View.VISIBLE);
            }


            vh.productTitleNameText.setText(model.getTitle());
            try{

                if(model.getCreate_time()!=null){
                    vh.priductTimeText.setText(getStrTime(model.getCreate_time()));
                }
                if(model.getType().equals("1")){
                    vh.productBuyText.setText("出售");
                    vh.productBuyText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_quanzi_confirm));
                }else{
                    vh.productBuyText.setText("求购");
                    vh.productBuyText.setBackground(mContext.getResources().getDrawable(R.drawable.activity_product_title));
                }
                vh.detailTextView.setText(model.getContents());
                vh.texttelTextView.setText(model.getContact_mobile());
                vh.productBuyTypeText.setText(model.getVariety());
                vh.textView.setText(model.getUserinfo().getName());
                vh.deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GongQiuController.getInstance().DeleteGongQiu(new ViewNetCallBack() {
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
                                        ToastTool.show("删除供求成功");
                                        getItems().remove(position);
                                        notifyDataSetChanged();
                                    } else {
                                        ToastTool.show(object.getString("message"));
                                    }

                                } catch (Exception e) {
                                    ToastTool.show("删除供求失败");
                                }
                            }
                        },model.getId(),UserPreference.getTOKEN());


                    }
                });
            }catch (Exception e){
                Logger.e("eee"+e.getMessage());
            }


            return itemView;
        }
        HashMap<String,Bitmap> hashMap =  new HashMap<>();
        private class MyTask extends AsyncTask<String, Integer, String> {
            private ImageView imageView;
            private String url;

            private Bitmap bitmap;

            public MyTask(String urls, ImageView imageViews) {

                url = urls;
                imageView = imageViews;
            }

            //onPreExecute方法用于在执行后台任务前做一些UI操作
            @Override
            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(String... params) {


                    bitmap = createVideoThumbnail(url, 200, 160);
                    hashMap.put(url,bitmap);

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Logger.e("--imageView----"+imageView);
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
        public  String getStrTime(String cc_time) {
            String re_StrTime = null;

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));

            return re_StrTime;

        }
        class ViewHolder {
            TextView priductTimeText, productTitleNameText,productBuyText,productBuyTypeText,detailTextView,textView,texttelTextView,textAddressText,deleteItem;
            ImageView productImageView,videoImageView;

        }
    }
    public void PopUpDialog() {   //弹框
        SgActivityPushGongQiuDialog PopUpDialogs = new SgActivityPushGongQiuDialog(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom"+canload+firstnumber);
            firstnumber=firstnumber+size;
            MineInfoController.getInstance().get_my_supply(this, firstnumber, size, UserPreference.getTOKEN());
        }



}
