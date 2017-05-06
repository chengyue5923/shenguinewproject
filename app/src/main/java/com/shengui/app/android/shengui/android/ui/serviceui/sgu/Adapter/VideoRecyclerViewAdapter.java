package com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoListViewBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2017/3/17.
 */

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    List<VideoListViewBean.DataBean> data;
    int type = -1;

    private TextView butDelete;
    private TextView btnCollect;
    private TextView btnShape;
    private TextView btnCancel;

    private static String courseId = "";
    private static int posi = -1;

    private Dialog dialog;

    private TextView collectShape;
    private TextView delectCollect;

    public VideoRecyclerViewAdapter(Context context, List data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
    }

    public VideoRecyclerViewAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sgu_item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (data != null) {

            final VideoListViewBean.DataBean dataBean = data.get(position);

            if (type == -1) {
                holder.videoChange.setVisibility(View.GONE);

            } else {
                holder.videoChange.setVisibility(View.VISIBLE);
                if (type == 0) {
                    holder.videoChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            courseId = dataBean.getId();
                            posi = position;
                            showMyCollectDialog();
                        }
                    });
                } else {
                    holder.videoChange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            courseId = dataBean.getId();
                            posi = position;
                            showMyViewsDialog();
                        }
                    });
                }

            }

            holder.haveBean.setVisibility(View.VISIBLE);
            holder.videoNoData.setVisibility(View.GONE);


            Glide.with(context)
                    .load(Uri.parse(Api.baseUrl + dataBean.getCover()))
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.videoImageView);

            holder.voideTitle.setText(dataBean.getName());

            holder.teacherName.setText(dataBean.getTeacherName());

            holder.watchNumb.setText(dataBean.getViews() + "");

            if (dataBean.getPrice() == 0) {
                holder.freeLL.setBackgroundResource(R.color.black);
                holder.freeTv.setText("免费");
            } else {
                holder.freeLL.setBackgroundResource(R.color.main_color);
                holder.freeTv.setText("付费");
            }

            String time = CreateTimeUtil.time(dataBean.getPublishTime(), 1);

            holder.videoTime.setText(time);

            holder.haveBean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VideoPlayActivity.class);
                    intent.putExtra("teacherName", dataBean.getTeacherName());
                    intent.putExtra("teacherFace", dataBean.getTeacherFace());
                    intent.putExtra("courseId", dataBean.getId());
                    context.startActivity(intent);
                }
            });

        } else {

            holder.haveBean.setVisibility(View.GONE);
            holder.videoNoData.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        int i = 1;
        if (data != null) {
            i = data.size();
        }
        return i;
    }


    public void showMyCollectDialog() {

              //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgu_dialog_bottm, null, false);
        //初始化控件
        delectCollect = (TextView) inflate.findViewById(R.id.btn_delete_collect);

        delectCollect.setOnClickListener(this);


        diaLogShow(inflate);
    }

    public void showMyViewsDialog() {

        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgu_dialog_bottm_views_buys, null, false);
        //初始化控件
        butDelete = (TextView) inflate.findViewById(R.id.btn_delete);
        btnCollect = (TextView) inflate.findViewById(R.id.btn_collect);

        btnCollect.setOnClickListener(this);
        butDelete.setOnClickListener(this);


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
    public void onClick(View view) {
        if (type == 0) {
            switch (view.getId()) {
                case R.id.btn_delete_collect:
                    Toast.makeText(context, "删除收藏", Toast.LENGTH_SHORT).show();
                    data.remove(posi);
                    notifyDataSetChanged();
                    final FormBody formBody = new FormBody.Builder()
                            .add("courseId", courseId)
                            .build();
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            JsonUitl.collectVideo(context,formBody,2);
                        }
                    });
                    break;
//                case R.id.btn_collect_shape:
//                    Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
//                    break;
            }
        } else {
            switch (view.getId()) {
                case R.id.btn_delete:
                    if (type == 1) {
                        Toast.makeText(context, "删除观看视频", Toast.LENGTH_SHORT).show();
                        data.remove(posi);
                        notifyDataSetChanged();
                        final FormBody formBody = new FormBody.Builder()
                                .add("courseId", courseId)
                                .build();
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                JsonUitl.delVideoView(context, formBody);
                            }
                        });

                    } else {
                        Toast.makeText(context, "删除购买视频", Toast.LENGTH_SHORT).show();
                        data.remove(posi);
                        notifyDataSetChanged();
                        final FormBody formBody = new FormBody.Builder()
                                .add("courseId", courseId)
                                .build();
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
                                JsonUitl.delVideoOrder(context, formBody);
                            }
                        });
                    }

                    break;
                case R.id.btn_collect:
                    Toast.makeText(context, "收藏该条视频", Toast.LENGTH_SHORT).show();
                    final FormBody formBody = new FormBody.Builder()
                            .add("courseId", courseId)
                            .build();
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            JsonUitl.collectVideo(context,formBody,1);
                        }
                    });
                    break;

            }
        }
        dialog.dismiss();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.video_image_view)
        ImageView videoImageView;
        @Bind(R.id.freeTv)
        TextView freeTv;
        @Bind(R.id.freeLL)
        LinearLayout freeLL;
        @Bind(R.id.voide_title)
        TextView voideTitle;
        @Bind(R.id.video_change)
        LinearLayout videoChange;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.watch_numb)
        TextView watchNumb;
        @Bind(R.id.numbLL)
        LinearLayout numbLL;
        @Bind(R.id.video_time)
        TextView videoTime;
        @Bind(R.id.have_bean)
        LinearLayout haveBean;
        @Bind(R.id.video_no_data)
        LinearLayout videoNoData;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
