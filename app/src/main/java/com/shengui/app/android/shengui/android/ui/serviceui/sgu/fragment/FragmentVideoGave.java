package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.Adapter.VideoGaveAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.VideoGaveBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.model.WeiXinPayDean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.utilsview.DialogFacory;
import com.shengui.app.android.shengui.configer.constants.Constant;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/24.
 */

public class FragmentVideoGave extends BaseFragment {

    View view;


    EditText editText;
    @Bind(R.id.video_gave_recycler_view)
    RecyclerView videoGaveRecyclerView;
    @Bind(R.id.video_gave_support)
    ImageView videoGaveSupport;

    private TextView gaveDialogConfirm;
    private RadioGroup gaveRG;
    private ImageView dialogBack;
    private TextView teacherName;
    private ImageView teacherImage;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;

    String price;


    private VideoGaveAdapter videoGaveAdapter;

    private AlertDialog dialog;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GAVADATA:
                    List<VideoGaveBean.DataBean> dataBeen = (List<VideoGaveBean.DataBean>) msg.obj;
                    videoGaveAdapter = new VideoGaveAdapter(getContext(), dataBeen,name);
                    if (videoGaveAdapter!=null &&videoGaveRecyclerView!=null) {
                        videoGaveRecyclerView.setAdapter(videoGaveAdapter);
                    }
                    videoGaveAdapter.notifyDataSetChanged();
                    break;

                case WEIXINGAVE:
                    WeiXinPayDean weiXinPayDean = (WeiXinPayDean) msg.obj;
                    WeiXinPayDean.DataBean wxData = weiXinPayDean.getData();
                    if (weiXinPayDean.getStatus() == 1) {
                        IWXAPI api = WXAPIFactory.createWXAPI(getContext(), Constant.WXIDAPP_ID);
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
                        Toast.makeText(getContext(), "下单失败，请检查输入金额格式或稍后再试。。。", Toast.LENGTH_SHORT).show();
                        runDialog.dismiss();
                    }
                    break;
                case WEIXINCHECK:
                    boolean obj = (boolean) msg.obj;
                    runDialog.dismiss();
                    dialog.dismiss();
                    if (obj) {
                        ThreadData();
                    } else {
                        Toast.makeText(getContext(), "下单失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };
    private String name;
    private String face;
    private String courseId;
    private final int GAVADATA = 1;
    private final int WEIXINGAVE = 2;
    private final int WEIXINCHECK = 3;
    private VideoReceiver videoReceiver;
    private Dialog runDialog;

    public class VideoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("test", "onReceive: " + action);
            if (action.equals("weixinpay")) {
                    Log.e("test", "onReceive: " + action);
                    ThreadUtil.execute(new Runnable() {
                        @Override
                        public void run() {
                            Boolean aBoolean = JsonUitl.weiXinCheck(getContext());
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
        getActivity().registerReceiver(videoReceiver, filter);
    }

    public void unRegister(){
        if (videoReceiver!=null ){
            getActivity().unregisterReceiver(videoReceiver);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sgu_fragment_video_gave, container, false);
        ButterKnife.bind(this, view);

        runDialog = DialogFacory.getInstance().createRunDialog(getContext(), "正在提交。。");

        registerReceiveInit();

        Bundle arguments = getArguments();
        name = arguments.getString("teacherName");
        face = arguments.getString("teacherFace");
        courseId = arguments.getString("courseId");
        recycleViewInit();

        dialogControl();

        return view;
    }

    private void dialogControl() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.sgu_dialog_gave, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.testDlg);
        dialog = builder
                .setView(contentView)
                .create();

        videoGaveSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

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

        teacherName.setText(name);
        Glide.with(getContext())
                .load(Uri.parse(Api.baseUrl + face))
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
//                        FormBody formBody = new FormBody.Builder()//创建表单构造器
//                                .add("courseId", courseId)//添加表单参数
//                                .add("price", String.valueOf(price))
//                                .build();//生成简易表单型RequestBody
//
//                        boolean b = JsonUitl.videoGavePost(getContext(), formBody);

                        WeiXinPayDean weiXinPayDean = JsonUitl.weixinGave(getContext(), courseId, price);

                        Message message = handler.obtainMessage();
                        message.what = WEIXINGAVE;
                        message.obj = weiXinPayDean;
                        handler.sendMessage(message);
                    }
                });
            }
        });


    }

    private void recycleViewInit() {
        videoGaveRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        String[] stringArray = getResources().getStringArray(R.array.sgu_video_tab);
        Bundle arguments = getArguments();
        String tab = arguments.getString("tab");

        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (tab.equals(stringArray[1])) {

            if (mNetworkInfo == null) {
                videoGaveAdapter = new VideoGaveAdapter(getContext(), null, name);

                videoGaveRecyclerView.setAdapter(videoGaveAdapter);
                videoGaveAdapter.notifyDataSetChanged();

            } else {
                ThreadData();
            }

        }
    }

    private void ThreadData() {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<VideoGaveBean.DataBean> gaveBeen = JsonUitl.videoGave(getContext(), courseId);
                Message message = handler.obtainMessage();
                message.what = GAVADATA;
                message.obj = gaveBeen;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }


    public void reflash() {
        ThreadData();
    }
}
