package com.shengui.app.android.shengui.android.ui.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.video.RecordVideoActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.video.RingProgressBar;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/7/12.
 */
@SuppressLint("ValidFragment")
public class SgActivityPushTieZiDialog extends DialogFragment implements View.OnClickListener {

    @Bind(R.id.ht_takePhoto)
    TextView htTakePhoto;
    @Bind(R.id.ht_album)
    TextView htAlbum;
    @Bind(R.id.ht_dialog_cancel)
    TextView htDialogCancel;
    private Context context;
    ImageView cancenlImage;
    /**
     * 是否有足够的剩余空间
     */
    private boolean haveEnoughSpace = false;
    private RingProgressBar ringProgressBar;
    RelativeLayout wentiLayout, gongqiuLayout, tieziLayout, videoLayout;

    @SuppressLint("ValidFragment")
    public SgActivityPushTieZiDialog(Context context) {
        this.context = context;

    }
    String title="";
    String topicId;
    String circleTitle;
    String circleId;

    @SuppressLint("ValidFragment")
    public SgActivityPushTieZiDialog(Context context,String titlew,String topoc) {
        this.context = context;
        this.title=titlew;
        topicId=topoc;
    }@SuppressLint("ValidFragment")
    public SgActivityPushTieZiDialog(Context context,String circletitle,String circleId,String circle) {
        this.context = context;
        this.circleTitle=circletitle;
        this.circleId=circleId;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WindowManager.LayoutParams localLayoutParams = getDialog().getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialogStyleBottom);
        View view = View.inflate(getActivity(), R.layout.activity_main_push_bottom, null);
        ButterKnife.bind(this, view);
        htTakePhoto=(TextView)view.findViewById(R.id.ht_takePhoto);
        htTakePhoto.setOnClickListener(this);
        htAlbum=(TextView)view.findViewById(R.id.ht_album);
        htAlbum.setOnClickListener(this);
        htDialogCancel=(TextView)view.findViewById(R.id.ht_dialog_cancel);
        htDialogCancel.setOnClickListener(this);


        dialog.setContentView(view);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ht_dialog_cancel:
                dismiss();
                break;
            case R.id.ht_album:
                statrtVideo();
                dismiss();
                break;
            case R.id.ht_takePhoto:
                if(StringTools.isNullOrEmpty(title)){
                    IntentTools.startPushTiezi(getActivity(),circleTitle,circleId,"circle");
                }else{
                    IntentTools.startPushTiezi(getActivity(),title,topicId,"");
                }
                dismiss();
                break;

        }

    }

    public void statrtVideo(){
        long freeSpace = getFreeSpace();
        haveEnoughSpace = !(freeSpace < 5242880);//TODO 检测剩余空间，限制是5M
        if (haveEnoughSpace) {
            checkCameraPermission();
        } else {
            Toast.makeText(getActivity(), "剩余空间不够充足，请清理一下再试一次", Toast.LENGTH_SHORT).show();
        }

    }
    //视频录制需要的权限(相机，录音，外部存储)
    private String[] VIDEO_PERMISSION = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> NO_VIDEO_PERMISSION = new ArrayList<String>();

    /**
     * 检测摄像头权限，具备相关权限才能继续
     */
    private void checkCameraPermission() {
        NO_VIDEO_PERMISSION.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < VIDEO_PERMISSION.length; i++) {
                if (ActivityCompat.checkSelfPermission(getActivity(), VIDEO_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                    NO_VIDEO_PERMISSION.add(VIDEO_PERMISSION[i]);
                }
            }
            if (NO_VIDEO_PERMISSION.size() == 0) {
                Intent intent = new Intent(getActivity(), RecordVideoActivity.class);
                if(StringTools.isNullOrEmpty(title)){
                    intent.putExtra("circletitle",circleTitle);
                    intent.putExtra("circleid",circleId);
                }else{
                    intent.putExtra("topic",title);
                    intent.putExtra("topicid",topicId);
                }
                startActivity(intent);
                dismiss();
            } else {
                ActivityCompat.requestPermissions(getActivity(), NO_VIDEO_PERMISSION.toArray(new String[NO_VIDEO_PERMISSION.size()]), REQUEST_CAMERA);
            }
            Logger.e("sssssssssssss"+NO_VIDEO_PERMISSION);
        } else {
            Logger.e("sssss");
            Intent intent = new Intent(getActivity(), RecordVideoActivity.class);
            if(StringTools.isNullOrEmpty(title)){
                intent.putExtra("circletitle",circleTitle);
                intent.putExtra("circleid",circleId);
            }else{
                intent.putExtra("topic",title);
                intent.putExtra("topicid",topicId);
            }
            startActivity(intent);
            dismiss();
        }
    }
    private static final int REQUEST_CAMERA = 0;
    /**
     * 获得可用存储空间
     *
     * @return 可用存储空间（单位b）
     */
    public long getFreeSpace() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;//区块的大小
        long totalBlocks;//区块总数
        long availableBlocks;//可用区块的数量
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }
        //String totalSpace = Formatter.formatFileSize(MyApplication.getInstance().getApplicationContext(), blockSize * totalBlocks);
        //String availableSpace = Formatter.formatFileSize(MyApplication.getInstance().getApplicationContext(), blockSize * availableBlocks);
        //Log.e(LOG_TAG, "totalSpace：" + totalSpace + "...availableSpace：" + availableSpace);
        Log.e("sos-sos", "totalSpace：" + blockSize * totalBlocks + "...availableSpace：" + blockSize * availableBlocks);
        return blockSize * availableBlocks;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
