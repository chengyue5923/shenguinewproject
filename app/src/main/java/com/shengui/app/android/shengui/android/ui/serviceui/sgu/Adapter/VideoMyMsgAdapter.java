package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.MyMsgBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2017/4/17.
 */


public class VideoMyMsgAdapter extends RecyclerView.Adapter<VideoMyMsgAdapter.ViewHolder>{

    Context context;
    List<MyMsgBean.DataBean> data;


    private TextView btnDel;
    private TextView btnAsk;



    private Dialog dialog;

    public VideoMyMsgAdapter(Context context, List<MyMsgBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_my_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyMsgBean.DataBean dataBean = data.get(position);
        Glide.with(context)//頭像
                .load(Uri.parse(Api.baseUrl + dataBean.getUserFace()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.peopleFace);



        holder.commentName.setText(dataBean.getUserName());
        holder.commentTime.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 2));

        holder.commentMemo.setText("回复您：" + dataBean.getContent());

        holder.countName.setText(dataBean.getCountName());

        holder.itemMyMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyViewsDialog(position,dataBean.getId());
            }
        });


    }


    public void showMyViewsDialog(final int position, final String commentId) {

        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgu_dialog_bottom_my_msg, null, false);
        //初始化控件
        btnAsk = (TextView) inflate.findViewById(R.id.btn_ask);
        btnDel = (TextView) inflate.findViewById(R.id.btn_del);

        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra("courseId",data.get(position).getCourseId());
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                final FormBody formBody = new FormBody.Builder()
                        .add("commentsId", commentId)
                        .build();
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        JsonUitl.delComments(context,formBody);
                    }
                });
                dialog.dismiss();
               notifyDataSetChanged();
            }
        });

        diaLogShow(inflate);
    }

    private void diaLogShow(View inflate) {
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }



    @Override
    public int getItemCount() {

        return data.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.people_face)
        CircleImageView peopleFace;
        @Bind(R.id.comment_name)
        TextView commentName;
        @Bind(R.id.comment_time)
        TextView commentTime;
        @Bind(R.id.comment_memo)
        TextView commentMemo;
        @Bind(R.id.countName)
        TextView countName;
        @Bind(R.id.item_my_msg)
        LinearLayout itemMyMsg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

