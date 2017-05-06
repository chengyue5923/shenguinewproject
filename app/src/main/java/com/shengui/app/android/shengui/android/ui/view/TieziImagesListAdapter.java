package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgProductDetailActivity;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.imagesModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;
import com.squareup.picasso.Picasso;

import org.apache.commons.httpclient.util.HttpURLConnection;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by yanbo on 2016/7/20.
 */
public class TieziImagesListAdapter extends BasePlatAdapter<imagesModel> {
    Context mContext;
    public TieziImagesListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        final ViewHolder vh;
        final imagesModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_tiezi_list_item, null);
            vh.images=(ImageView)itemView.findViewById(R.id.images);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        vh.images.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IntentTools.startBigImage(mContext,model.getUrl_middle());

                ArrayList<String> mo=new ArrayList<String>();
                for(int i=0;i<getItems().size();i++){

                    mo.add(getItem(i).getUrl_middle());
                }

                IntentTools.StartImageActivity(mContext,mo,position);
            }
        });
        try{
            Logger.e("ererr-----------------"+model.getUrl_middle());
//            Glide.with(mContext).load(model.getUrl_middle()).asBitmap().override(500,700).placeholder(R.drawable.default_pictures).into(vh.images);
            Glide.with(mContext).load(model.getUrl_middle()).asBitmap().override(500,700).placeholder(R.drawable.default_pictures).into(vh.images);

//            Glide.with(mContext).load(model.getUrl_middle()).asBitmap().placeholder(R.drawable.default_pictures)
//                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            int imageHeight = resource.getHeight();
//                            int imagewidth=resource.getWidth();
//
//                            ViewGroup.LayoutParams para = vh.images.getLayoutParams();
//                            para.width = ViewGroup.LayoutParams.MATCH_PARENT;
//                            para.height= (para.width/imagewidth)*imageHeight;
////                            para.height = imageHeight;
//                            vh.images.setLayoutParams(para);
//
//                            Logger.e("ererr-------vh.images.setLayoutParams(para);----------"+model.getUrl_middle());
//                            Glide.with(context)
//                                    .load(model.getUrl_middle())
//                                    .asBitmap()
//                                    .placeholder(R.drawable.default_pictures)
//                                    .centerCrop()
//                                    .into(vh.images);
//                        }
//                    });
        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }
        return itemView;
    }
    class ViewHolder {
        ImageView images;
    }
}
