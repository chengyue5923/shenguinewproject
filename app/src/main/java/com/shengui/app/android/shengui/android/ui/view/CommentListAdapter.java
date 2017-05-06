package com.shengui.app.android.shengui.android.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.view.adapter.BasePlatAdapter;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.models.CommentModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yanbo on 2016/7/20.
 */
public class CommentListAdapter extends BasePlatAdapter<CommentModel> {
    Context mContext;
    public CommentListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        ViewHolder vh;
       final  CommentModel model = getItem(position);
        if (itemView == null) {
            vh = new ViewHolder();
            itemView = LayoutInflater.from(mContext).inflate(R.layout.view_activity_comment_item, null);

            vh.nameTiezaiText=(TextView)itemView.findViewById(R.id.nameTiezaiText);
            vh.focusText=(TextView)itemView.findViewById(R.id.focusText);
            vh.timeTieZiTextView=(TextView)itemView.findViewById(R.id.timeTieZiTextView);
            vh.contentTextView=(TextView)itemView.findViewById(R.id.contentTextView);
            vh.niceTextView=(TextView)itemView.findViewById(R.id.niceTextView);
            vh.commentView=(TextView)itemView.findViewById(R.id.commentView);
            vh.personInfoIv=(CircleImageView)itemView.findViewById(R.id.personInfoIv);
            vh.niceTv=(ImageView)itemView.findViewById(R.id.niceTv);
            vh.commonTv=(ImageView)itemView.findViewById(R.id.commonTv);

            vh.titleLayout=(LinearLayout) itemView.findViewById(R.id.titleLayout);
            itemView.setTag(vh);
        } else {
            vh = (ViewHolder) itemView.getTag();
        }

        vh.niceTextView.setText(model.getDig_num());
        vh.commentView.setText(model.getReview_num());
        vh.timeTieZiTextView.setText(getStrTime(model.getCreate_time()));
        vh.nameTiezaiText.setText(model.getUserinfo().getName());
        vh.contentTextView.setText(model.getContents());

//        if(model.get)
        if(model.getIs_dig().equals("0")){
            vh.niceTv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tiezi_like));
        }else{
            vh.niceTv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.like_image_bg));
        }
        try{
            Glide.with(mContext).load(model.getUserinfo().getAvatar()).asBitmap().placeholder(R.drawable.default_image).into( vh.personInfoIv);
        }catch (Exception e){
            Logger.e("sd"+e.getMessage());
        }
        vh.personInfoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentTools.startOtherDetail(mContext,model.getUserinfo().getId());
            }
        });
        vh.focusText.setText("回复"+model.getReview_user().getName());
        //点击评论内容
        vh.contentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickContent(v,position);
            }
        });
        vh.timeTieZiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickContent(v,position);
            }
        });
        vh.titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnClickContent(v,position);
            }
        });

        //店家评论图标
        vh.commonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnclickCommoment(v,position);
            }
        });

        //点击品论点赞
        vh.niceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnclickNice(v,position);
            }
        });


        return itemView;
    }

    CommomentOnclickListener listener;

    public void setListener(CommomentOnclickListener listener) {
        this.listener = listener;
    }

    public interface CommomentOnclickListener{
       void  OnclickNice(View v,int position);
        void OnclickCommoment(View v,int position);
        void OnClickContent(View v,int position);
    }

    class ViewHolder {
        LinearLayout titleLayout;
        TextView nameTiezaiText, focusText,timeTieZiTextView,contentTextView,niceTextView,commentView;
        ImageView niceTv,commonTv;
        CircleImageView personInfoIv;

    }
    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }

}
