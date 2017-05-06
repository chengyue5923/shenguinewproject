package com.shengui.app.android.shengui.android.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.base.view.view.dialog.factory.DialogFacory;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.dialog.BottomDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.constants.IConstant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.CityModel;
import com.shengui.app.android.shengui.models.ImageUploadModel;
import com.shengui.app.android.shengui.models.LocationModel;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class BaseInfoActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack,View.OnClickListener , BottomDialog.TransferValue{
    @Bind(R.id.imagename)
    TextView imagename;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.changeImage)
    TextView changeImage;
    @Bind(R.id.QuanZiname)
    TextView QuanZiname;
    @Bind(R.id.changeQuanming)
    TextView changeQuanming;
    @Bind(R.id.Addressname)
    TextView Addressname;
    @Bind(R.id.changeaddress)
    TextView changeaddress;
    @Bind(R.id.saveTextView)
    TextView saveTextView;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.nameEt)
    EditText nameEt;
    @Bind(R.id.cirtEt)
    TextView cirtEt;
    private String picPath = "";

    LocationModel locationModel=new LocationModel();
    private QuanziList model;
    public static BaseInfoActivityFragment newInstance(QuanziList model) {
        BaseInfoActivityFragment customerInfoFragment = new BaseInfoActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        customerInfoFragment.setArguments(bundle);
        return customerInfoFragment;
    }
    private Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_base_info_activity, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        model = (QuanziList) bundle.getSerializable("model");

        try{
            Glide.with(this).load(model.getAvatar()).asBitmap().placeholder(R.drawable.default_pictures).into(personInfoIv);
        }catch (Exception e){
           Logger.e("sd"+e.getMessage());
        }
        picPath=model.getAvatar();
        nameEt.setText(model.getTitle());
        cirtEt.setText(model.getCity_name());
        locationModel.setId(model.getCity_id());
        locationModel.setName(model.getCity_name());
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        initData();
        return view;
    }
    private void initData() {
        changeImage.setOnClickListener(this);
        changeQuanming.setOnClickListener(this);
        changeaddress.setOnClickListener(this);
        saveTextView.setOnClickListener(this);
        dialog = DialogFacory.getInstance().createRunDialog(getActivity(), "请稍后");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IMStringEvent event) {
        Logger.e("currwent------wwwwwwwwww-----------BaseInfoActivityFragment-----------"+event.getCityid());
            Logger.e("BaseInfoActivityFragment"+event.getCityid()+event.getCityname());
            cirtEt.setText(event.getCityname());
            locationModel.setId(event.getCityid());
            locationModel.setName(event.getCityname());

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().unregister(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if(flag== HttpConfig.EditCircle.getType()){
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("修改成功");
                    getActivity().finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                ToastTool.show("修改失败");
            }
        }
            if (flag == HttpConfig.UploadImage.getType()) {
                try {

                    JSONObject object = new JSONObject(o.toString());
                    if (!object.getBoolean("status")) {
                        ToastTool.show(object.getString("message"));
                    } else {
                        List<ImageUploadModel> modelist = (List<ImageUploadModel>) result;
                        Log.e("timage----", modelist.get(0).getImg_id());
                        ToastTool.show("图片上传成功");
//                        picPath = modelist.get(0).getUrl_short();
                        GuiMiController.getInstance().EditCircle(this,UserPreference.getTOKEN(),Integer.parseInt(model.getId()), modelist.get(0).getUrl_short(),nameEt.getText().toString(),UserPreference.getLat(),UserPreference.getLng(),locationModel.getId(),locationModel.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeImage:
                FragmentManager fm = getChildFragmentManager();
                BottomDialog bottomDialog = new BottomDialog(this, "", IConstant.PHOTOPICTURE);
                bottomDialog.show(fm, "fragment_dialog");
                break;
            case R.id.saveTextView:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(!StringTools.isNullOrEmpty(nameEt.getText().toString())){
                        if(picPath.contains("http")){
                            GuiMiController.getInstance().EditCircle(this,UserPreference.getTOKEN(),Integer.parseInt(model.getId()),picPath,nameEt.getText().toString(),UserPreference.getLat(),UserPreference.getLng(),locationModel.getId(),locationModel.getName());
                        }else{
                            UploadImage(picPath);
                        }
                    }else{
                        ToastTool.show("圈名不能为空");
                    }
                }else{
                    ToastTool.show("您还没有登录");
                }
                break;
            case R.id.changeaddress:
                IntentTools.startCityList(getActivity(), 1010);
                break;
        }
    }
    public void UploadImage(String path) {
        Log.e("photnooa------------", path);
        File[] fillist;
        fillist = new File[1];
        fillist[0] = new File(path);
        PushController.getInstance().upLoadFile(new ViewNetCallBack() {
            @Override
            public void onConnectStart() {
                if (null != dialog && !dialog.isShowing()) {
                    dialog.show();
                }
            }

            @Override
            public void onConnectEnd() {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFail(Exception e) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
                try {
                    JSONObject object = new JSONObject(o.toString());
                    if (!object.getBoolean("status")) {
                        ToastTool.show(object.getString("message"));
                    } else {
                        List<ImageUploadModel> modelist = (List<ImageUploadModel>) result;
                        Log.e("timage----", modelist.get(0).getImg_id());
                        ToastTool.show("图片上传成功");
                        picPath = modelist.get(0).getUrl_short();
                        GuiMiController.getInstance().EditCircle(new backlistener(),UserPreference.getTOKEN(),Integer.parseInt(model.getId()), modelist.get(0).getUrl_short(),nameEt.getText().toString(),UserPreference.getLat(),UserPreference.getLng(),locationModel.getId(),locationModel.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, fillist, HttpConfig.UploadImage);
    }
    class  backlistener implements ViewNetCallBack {

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
                Logger.e("dafa=-----------------------"+o.toString());
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
                    ToastTool.show("修改成功");
                    getActivity().finish();
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                ToastTool.show("修改失败");
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Logger.e("setPicdatadatadatadataPath" + data.getSerializableExtra("picPath"));
//        String value=(String)data.getSerializableExtra("picPath");
//        String img_path =(String)data.getSerializableExtra("picPath");
//        Bitmap bmp= BitmapFactory.decodeFile(img_path);
//        personInfoIv.setImageBitmap(bmp);
//        Glide.with(BaseInfoActivityFragment.this).load((String)data.getSerializableExtra("picPath")).centerCrop().into(personInfoIv);

    }

    @Override
    public void transferValue(String value) {
        Logger.e("setPicvaluevaluevaluevaluevaluevaluevaluePath" + value);
        picPath = value;
    }

    public void refreshImage(String picPath){
        Logger.e("-imge---------------------"+picPath);
        this.picPath = picPath;
        Glide.with(this).load(picPath).centerCrop().into(personInfoIv);
//        personInfoIv.setImageBitmap(BitmapFactory.decodeFile(picPath));
    }
}
