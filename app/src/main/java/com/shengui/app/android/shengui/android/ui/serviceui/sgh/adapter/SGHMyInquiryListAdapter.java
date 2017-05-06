package com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.ScanImageActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment.FragmentMyInquiryListView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UserQuestionListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.CaseDetailsActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHMyActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.CreateTimeUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.DialogFacory;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;

/**
 * Created by Administrator on 2017/4/7.
 */


public class SGHMyInquiryListAdapter extends RecyclerView.Adapter<SGHMyInquiryListAdapter.ViewHolder> {

    private final SGHMyActivity activity;
    private final Dialog runDialog;
    List<UserQuestionListBean.DataBean> data;
    FragmentMyInquiryListView fragmentMyInquiryListView;
    Context context;

    private TextView gaveDialogConfirm;
    private RadioGroup gaveRG;
    private ImageView dialogBack;
    private TextView teacherName;
    private ImageView teacherImage;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private EditText editText;

    private String price;

    private String inquiryId;


    private AlertDialog dialog;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLOSEINQUIRYID:
                    boolean b = (boolean) msg.obj;
                    if (b) {
                        Toast.makeText(context, "取消问诊成功", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "取消问诊失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case WEIXINGAVE:

                    WeiXinPayDean weiXinPayDean = (WeiXinPayDean) msg.obj;
                    WeiXinPayDean.DataBean wxData = weiXinPayDean.getData();
                    if (weiXinPayDean.getStatus() == 1) {
                        IWXAPI api = WXAPIFactory.createWXAPI(context, Constant.WXIDAPP_ID);
                        api.registerApp(Constant.WXIDAPP_ID);
                        PayReq payReq = new PayReq();
                        payReq.appId = Constant.WXIDAPP_ID;
                        payReq.partnerId = wxData.getPartnerid();
                        payReq.prepayId = wxData.getPrepayid();
                        payReq.packageValue = wxData.getPackageX();
                        payReq.nonceStr = wxData.getNoncestr();
                        ;
                        payReq.timeStamp = String.valueOf(wxData.getTimestamp());
                        payReq.sign = wxData.getSign();
                        api.sendReq(payReq);
                    } else {
                        Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                        runDialog.dismiss();
                    }

                    break;
                case WEIXINCHECK:
                    boolean obj = (boolean) msg.obj;
                    runDialog.dismiss();
                    if (obj) {
                        fragmentMyInquiryListView.ThreadQuestinData();
                    } else {
                        Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case  VIDEOCOVERS:
                    List o = (List) msg.obj;
                    ImageView imageView = (ImageView) o.get(0);
                    Bitmap bitmap = (Bitmap) o.get(1);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    private final int CLOSEINQUIRYID = 9;
    private final int WEIXINGAVE = 1;
    private final int WEIXINCHECK = 2;
    private final int VIDEOCOVERS= 3;
    private VideoReceiver videoReceiver;


    public SGHMyInquiryListAdapter(List<UserQuestionListBean.DataBean> data, Context context, FragmentMyInquiryListView fragmentMyInquiryListView) {
        this.data = data;
        this.context = context;
        activity = ((SGHMyActivity) context);
        diaglogCreate();
        registerReceiveInit();
        this.fragmentMyInquiryListView = fragmentMyInquiryListView;
        runDialog = DialogFacory.getInstance().createRunDialog(context, "正在提交。。");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.sgh_item_my_question, parent, false);
        return new ViewHolder(inflate);
    }

    public class VideoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("test", "onReceive: "+action);
            if (action.equals("weixinpay")) {
                Log.e("test", "onReceive: "+action);
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        Boolean aBoolean = JsonUitl.weiXinCheck(context);
                        Message message = handler.obtainMessage();
                        message.obj = aBoolean;
                        message.what = WEIXINCHECK;
                        handler.sendMessage(message);
                    }
                });
            }else if (action.equals("deleteweixinpay")){
                runDialog.dismiss();
                dialog.dismiss();
            }
        }
    }

    private void registerReceiveInit() {
        // 注册广播接收
        videoReceiver = new VideoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("weixinpay");
        filter.addAction("deleteweixinpay");//只有持有相同的action的接受者才能接收此广播
        context.registerReceiver(videoReceiver, filter);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final UserQuestionListBean.DataBean dataBean = data.get(position);
        dialogControl(dataBean);

        holder.myQuestionTime.setText(CreateTimeUtil.time(dataBean.getCreateTime(), 1));

        inquiryId = dataBean.getId();

        if (dataBean.getContentType() == 1) {
            holder.myImage.setVisibility(View.GONE);
        } else if (dataBean.getContentType() == 2) {
            if (dataBean.getFiles() != null) {
                holder.myImage.setVisibility(View.VISIBLE);
                holder.myVideo.setVisibility(View.GONE);
                Glide.with(context)
                        .load(Api.baseUrl + dataBean.getFiles().get(0))
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
                        .into(holder.myImageImg);
            } else {
                holder.myImage.setVisibility(View.GONE);
                holder.myVideo.setVisibility(View.GONE);
            }
        } else {
            if (dataBean.getFiles() != null) {
                holder.myImage.setVisibility(View.VISIBLE);
                holder.myVideo.setVisibility(View.VISIBLE);
                videoCovers(Api.SGHBaseUrl + dataBean.getFiles().get(0),holder.myImageImg);
//                holder.myImageImg.setImageBitmap(createVideoThumbnail(Api.SGHBaseUrl + dataBean.getFiles().get(0), 192, 108));
            } else {
                holder.myImage.setVisibility(View.GONE);
                holder.myVideo.setVisibility(View.GONE);
            }
        }

        if (dataBean.getStatus() <= 1) {
            holder.myQuestionStatus.setImageResource(R.mipmap.icon_statue_wating);
            if (dataBean.getStatus() == 0) {
                holder.myCancel.setVisibility(View.VISIBLE);
                holder.myWait.setVisibility(View.GONE);
                holder.myPay.setVisibility(View.GONE);
                holder.myAlready.setVisibility(View.GONE);

                holder.myDoctorFace.setVisibility(View.GONE);
                holder.myDoctorName.setText("医生接诊中，请耐心等候...");
            } else {
                holder.myWait.setVisibility(View.VISIBLE);
                holder.myCancel.setVisibility(View.GONE);
                holder.myPay.setVisibility(View.GONE);
                holder.myAlready.setVisibility(View.GONE);

                holder.myDoctorFace.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(Api.baseUrl + dataBean.getDoctorFace())
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
//                        .error(R.mipmap.timg) //失败图片
                        .into(holder.myDoctorFace);

                holder.myDoctorName.setText(dataBean.getDoctorName() + "已接受问诊");
            }
        } else if (dataBean.getStatus() == 2) {
            holder.myQuestionStatus.setImageResource(R.mipmap.icon_statue_already);
            holder.myCancel.setVisibility(View.GONE);
            holder.myWait.setVisibility(View.GONE);
            holder.myAlready.setVisibility(View.VISIBLE);
            holder.myPay.setVisibility(View.GONE);

            holder.myDoctorFace.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Api.baseUrl + dataBean.getDoctorFace())
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
//                    .error(R.mipmap.timg) //失败图片
                    .into(holder.myDoctorFace);

            holder.myDoctorName.setText(dataBean.getDoctorName() + "已解答问题");

        } else if (dataBean.getStatus() == 3) {
            holder.myQuestionStatus.setImageResource(R.mipmap.icon_statue_finish);
            holder.myCancel.setVisibility(View.GONE);
            holder.myWait.setVisibility(View.GONE);
            holder.myAlready.setVisibility(View.GONE);
            if (dataBean.getPrice() == 0) {
                holder.myPay.setVisibility(View.GONE);
            } else {
                holder.myPay.setVisibility(View.VISIBLE);
                holder.myPrice.setText("¥" + (((double) dataBean.getPrice()))/100);
            }

            holder.myDoctorFace.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(Api.baseUrl + dataBean.getDoctorFace())
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.logo) //占位符 也就是加载中的图片，可放个gif
//                    .error(R.mipmap.timg) //失败图片
                    .into(holder.myDoctorFace);

            holder.myDoctorName.setText(dataBean.getDoctorName() + "已解答问题");

        } else {
            holder.myQuestionStatus.setImageResource(R.mipmap.icon_statue_finish);
            holder.myCancel.setVisibility(View.GONE);
            holder.myWait.setVisibility(View.GONE);
            holder.myAlready.setVisibility(View.GONE);
            holder.myPay.setVisibility(View.GONE);

            holder.myDoctorFace.setVisibility(View.GONE);

            holder.myDoctorName.setText("已关闭");


        }
        holder.myQuestionTitle.setText(dataBean.getTitle());

        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaseDetailsActivity.class);
                intent.putExtra("inquiryId", dataBean.getId());
                context.startActivity(intent);
            }
        });
        holder.myWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaseDetailsActivity.class);
                intent.putExtra("inquiryId", dataBean.getId());
                context.startActivity(intent);
            }
        });

        holder.myCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeInquiry(dataBean, position);
            }
        });
        holder.myAlreadyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.reportInput(dataBean.getId(), dataBean, position);
            }
        });
        holder.myAlreadyPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        if (dataBean.getFiles() != null) {
            final ArrayList<String> list = new ArrayList();

            for (int i = 0; i < dataBean.getFiles().size(); i++) {
                list.add(Api.SGHBaseUrl + dataBean.getFiles().get(i));
            }
            holder.myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScanImageActivity.class);
                    intent.putStringArrayListExtra("images", list);
                    intent.putExtra("index", 0);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void videoCovers(final String url, final ImageView imageImg) {
        final List list = new ArrayList();
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(url, new HashMap<String, String>());
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(5 * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
                list.add(imageImg);
                list.add(bitmap);
                Message message = handler.obtainMessage();
                message.obj = list;
                message.what = VIDEOCOVERS;
                handler.sendMessage(message);
            }
        });
    }

    private void dialogControl(UserQuestionListBean.DataBean dataBean) {


        teacherName.setText(dataBean.getDoctorName());
        Glide.with(context)
                .load(Uri.parse(Api.baseUrl + dataBean.getDoctorFace()))
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(teacherImage);

        gaveRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.gave_one:
                        price = "1";
                        editText.clearFocus();
                        break;
                    case R.id.gave_two:
                        price = "5";
                        editText.clearFocus();
                        break;
                    case R.id.gave_three:
                        price = "10";
                        editText.clearFocus();
                        break;
                }
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("5")) {
                    price = "5";
                    radioButton2.setChecked(true);
                } else if (s.toString().equals("1")) {
                    price = "1";
                    radioButton1.setChecked(true);
                } else if (s.toString().equals("10")) {
                    price = "10";
                    radioButton3.setChecked(true);
                } else {
                    price = editText.getText().toString();
                    gaveRG.clearCheck();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        gaveDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDialog.show();
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        WeiXinPayDean weiXinPayDean = SGHJsonUtil.weixinGave(context, inquiryId, price);
                        Message message = handler.obtainMessage();
                        message.what = WEIXINGAVE;
                        message.obj = weiXinPayDean;
                        handler.sendMessage(message);

                    }
                });
            }
        });


    }

    private void diaglogCreate() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.sgu_dialog_gave, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.testDlg);
        dialog = builder
                .setView(contentView)
                .create();

        teacherImage = (ImageView) contentView.findViewById(R.id.gave_teacher_face);
        teacherName = (TextView) contentView.findViewById(R.id.gave_teacher_name);
        dialogBack = (ImageView) contentView.findViewById(R.id.gave_dialog_back);
        editText = (EditText) contentView.findViewById(R.id.gave_price_edit);
        gaveRG = (RadioGroup) contentView.findViewById(R.id.gave_rg);
        gaveDialogConfirm = (TextView) contentView.findViewById(R.id.gave_dialog_confirm);
        radioButton1 = (RadioButton) contentView.findViewById(R.id.gave_one);
        radioButton2 = (RadioButton) contentView.findViewById(R.id.gave_two);
        radioButton3 = (RadioButton) contentView.findViewById(R.id.gave_three);
        price = "5";
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

    private void closeInquiry(final UserQuestionListBean.DataBean dataBean, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("取消问诊")
                .setMessage("是否确认取消问诊？")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThreadUtil.execute(new Runnable() {
                            @Override
                            public void run() {
//                        inquiryId：问诊ID
                                FormBody formBody = new FormBody.Builder()//创建表单构造器
                                        .add("inquiryId", dataBean.getId())//添加表单参数
                                        .build();//生成简易表单型RequestBody
                                Boolean aBoolean = SGHJsonUtil.closeInquiry(context, formBody);
                                data.get(position).setStatus(9);
                                Message message = handler.obtainMessage();
                                message.what = CLOSEINQUIRYID;
                                message.obj = aBoolean;
                                handler.sendMessage(message);
                            }
                        });
                    }
                })
                .create().show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.my_image_img)
        ImageView myImageImg;
        @Bind(R.id.my_video)
        ImageView myVideo;
        @Bind(R.id.my_image)
        RelativeLayout myImage;
        @Bind(R.id.my_question_title)
        TextView myQuestionTitle;
        @Bind(R.id.my_question_time)
        TextView myQuestionTime;
        @Bind(R.id.my_question_status)
        ImageView myQuestionStatus;
        @Bind(R.id.content_layout)
        LinearLayout contentLayout;
        @Bind(R.id.my_doctor_name)
        TextView myDoctorName;
        @Bind(R.id.my_wait)
        TextView myWait;
        @Bind(R.id.my_already_report)
        ImageView myAlreadyReport;
        @Bind(R.id.my_already_pay)
        ImageView myAlreadyPay;
        @Bind(R.id.my_already)
        LinearLayout myAlready;
        @Bind(R.id.my_cancel)
        ImageView myCancel;
        @Bind(R.id.my_pay)
        LinearLayout myPay;
        @Bind(R.id.my_doctor_face)
        CircleImageView myDoctorFace;
        @Bind(R.id.sgh_item_my_question)
        LinearLayout sghItemMyQuestion;
        @Bind(R.id.price)
        TextView myPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


