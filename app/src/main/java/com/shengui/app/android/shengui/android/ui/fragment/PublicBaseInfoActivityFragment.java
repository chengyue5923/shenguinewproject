package com.shengui.app.android.shengui.android.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.dialog.BottomDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.configer.constants.IConstant;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by User on 2016/7/21.
 */
public class PublicBaseInfoActivityFragment extends Fragment implements AdapterView.OnItemClickListener, ViewNetCallBack,View.OnClickListener , BottomDialog.TransferValue{
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
    //    @Bind(R.id.myActivityList)
//    ListView myActivityList;
//    MyActivityListAdapter myActivityListAdapter;
//    private List<Object> objects = new ArrayList<>();
//    private EmptyLayout emptyLayout;
//    SwipeRefreshLayout swipeLayout;
    private String picPath = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_public_base_info_activity, container, false);
        ButterKnife.bind(this, view);
        initData();
//        myActivityListAdapter = new MyActivityListAdapter(getActivity());
//        myActivityList.setAdapter(myActivityListAdapter);
//        myActivityList.setFocusable(false);
//        emptyLayout = new EmptyLayout(getActivity(), myActivityList);
//        swipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        swipeLayout.setRefreshing(false);
////                    }
////                }, 5000);
//
//                initData();
//            }
//        });
//        swipeLayout.setColorSchemeResources(R.color.color_bule2, R.color.color_bule, R.color.color_bule2, R.color.color_bule3);
        return view;
    }

    private void initData() {
        changeImage.setOnClickListener(this);
        changeQuanming.setOnClickListener(this);
        changeaddress.setOnClickListener(this);
        saveTextView.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (objects.get(position) instanceof ActivityStatusModel)
//            return;
//        IntentTools.startActivityDetail(getActivity(), ((OrderActivityModel) objects.get(position)).getActivityId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
//        List<OrderActivityModel> list = (List<OrderActivityModel>) result;
//        swipeLayout.setRefreshing(false);
//        List<OrderActivityModel> tempList = new ArrayList<>();
//        emptyLayout.showSuccess(list.size() <= 0);
//        for (OrderActivityModel model : list) {
//            if (model.getStatus() != 1&&model.getStatus()!=0) {
//                tempList.add(model);
//            } else {
//                objects.add(model);
//            }
//        }
//        if (tempList.size()>0){
//            objects.add(new ActivityStatusModel());
//            objects.addAll(tempList);
//        }
//        myActivityListAdapter.setRes(objects);
    }

    @Override
    public void onConnectStart() {
//        emptyLayout.showLoading();
    }

    @Override
    public void onConnectEnd() {

    }

    @Override
    public void onFail(Exception e) {
//        emptyLayout.showError();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeImage:
                FragmentManager fm = getChildFragmentManager();
                BottomDialog bottomDialog = new BottomDialog(this, "", IConstant.PHOTOPICTURE);
                bottomDialog.show(fm, "fragment_dialog");
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IConstant.TAKE_PHOTO) {

            Glide.with(this).load(picPath).centerCrop().into(personInfoIv);

//            UserController.getInstance().changeAvarter(this, picPath);
//            UserController.getInstance().upLoadFile(new upLoadFile(), new File(picPath), HttpConfig.changeAvatar);
        } else if (resultCode == Activity.RESULT_OK && requestCode == IConstant.ALBUM_PHOTO) {
            if (null != data) {
                Uri photoUri = data.getData();
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    Logger.e("setPicPath" + picPath);
                    cursor.close();
                }
                Glide.with(this).load(picPath).centerCrop().into(personInfoIv);
//                UserController.getInstance().upLoadFile(new upLoadFile(), new File(picPath), HttpConfig.changeAvatar);
            }
        }
    }
    @Override
    public void transferValue(String value) {
        picPath = value;
    }
}
