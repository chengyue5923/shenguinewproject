package com.shengui.app.android.shengui.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.DateTools;
import com.base.platform.utils.java.StringTools;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgProductDetailActivity;
import com.shengui.app.android.shengui.configer.enums.UrlRes;
import com.shengui.app.android.shengui.controller.GongQiuController;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.ProductModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanbo on 2016/7/20.
 */
public class ActivityProductListAdapter extends BasePlatAdapter<GongQiuDetailModel> {
    Context mContext;
    boolean showdelete=false;
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
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }
        if(showdelete){
            vh.deleteItem.setVisibility(View.VISIBLE);
            vh.textAddressText.setVisibility(View.GONE);
        }else{
            vh.deleteItem.setVisibility(View.GONE);
            vh.textAddressText.setVisibility(View.VISIBLE);
        }

        if(StringTools.isNullOrEmpty(model.getCover())){
            new  MyTask(model.getVideo_info().getUrl(),  vh.productImageView).execute();
            vh.videoImageView.setVisibility(View.VISIBLE);
//            vh.productImageView.setImageBitmap(createVideoThumbnail(model.getVideo_info().getUrl(),200,160));
        }else{
            vh.videoImageView.setVisibility(View.GONE);
            Glide.with(mContext).load(model.getCover()).asBitmap().placeholder(R.drawable.default_pictures).into(vh.productImageView);
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
            if(StringTools.isNullOrEmpty(model.getVariety())){
                vh.productBuyTypeText.setVisibility(View.GONE);
            }else{
                vh.productBuyTypeText.setVisibility(View.VISIBLE);
                vh.productBuyTypeText.setText(model.getVariety());
            }

            vh.textView.setText(model.getContact_user());
            if(StringTools.isNullOrEmpty(model.getCity_name())){
                vh.textAddressText.setVisibility(View.GONE);
            }else{
                vh.textAddressText.setVisibility(View.VISIBLE);
                vh.textAddressText.setText(model.getCity_name());
            }

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }
    class ViewHolder {
        TextView priductTimeText, productTitleNameText,productBuyText,productBuyTypeText,detailTextView,textView,texttelTextView,textAddressText,deleteItem;
        ImageView productImageView,videoImageView;
    }
}
