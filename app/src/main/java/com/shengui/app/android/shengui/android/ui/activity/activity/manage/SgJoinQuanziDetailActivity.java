package com.shengui.app.android.shengui.android.ui.activity.activity.manage;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.dialog.BottomDialog;
import com.shengui.app.android.shengui.android.ui.view.HotMinePagerAdapter;
import com.shengui.app.android.shengui.android.ui.view.QuanZiBasePagerAdapter;
import com.shengui.app.android.shengui.configer.constants.IConstant;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.QuanziList;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/27.
 */

public class SgJoinQuanziDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener , BottomDialog.TransferValue{
    @Bind(R.id.backImageview)
    ImageView backImageview;
    @Bind(R.id.hotlayout)
    TextView hotlayout;
    @Bind(R.id.hotlineView)
    View hotlineView;
    @Bind(R.id.originzationLayout)
    TextView originzationLayout;
    @Bind(R.id.origiazationlineView)
    View origiazationlineView;
    @Bind(R.id.myTabLayout)
    LinearLayout myTabLayout;
    @Bind(R.id.topLayout)
    RelativeLayout topLayout;
    @Bind(R.id.my_pager)
    ViewPager myPager;
    QuanZiBasePagerAdapter adapter;
    private QuanziList model;
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.hotlayout:
                ChangeLayoutTab(0);
              break;
            case R.id.originzationLayout:
                ChangeLayoutTab(1);
                break;
            case R.id.backImageview:
                finish();
                break;

        }
    }

    private void ChangeLayoutTab(int b) {
        if(b==0){
            myPager.setCurrentItem(0);
            hotlayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            hotlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
            originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
            origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
        }else{
            originzationLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
            origiazationlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
            hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
            hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
            myPager.setCurrentItem(1);
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        myPager.setOffscreenPageLimit(4);
        myPager.setOnPageChangeListener(this);
    }

    @Override
    protected void initEvent() {
        hotlayout.setOnClickListener(this);
        originzationLayout.setOnClickListener(this);
        backImageview.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        if(getIntent().getSerializableExtra("circlrId")!=null){
            model=(QuanziList)getIntent().getSerializableExtra("circlrId");
            String[] strings = getResources().getStringArray(R.array.mine_base);
            adapter = new QuanZiBasePagerAdapter(getSupportFragmentManager(), strings,model);
            myPager.setAdapter(adapter);
        }
        if(getIntent().getSerializableExtra("CircleIdApply")!=null){
            int id=(int)getIntent().getSerializableExtra("CircleIdApply");
            GuiMiController.getInstance().CiecleContentDetail(this, id, UserPreference.getUid());
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_join_quanzi_detail_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);

            try {
                model = (QuanziList) result;
                String[] strings = getResources().getStringArray(R.array.mine_base);
                adapter = new QuanZiBasePagerAdapter(getSupportFragmentManager(), strings,model);
                myPager.setAdapter(adapter);
                myPager.setCurrentItem(1);
            } catch (Exception e) {
                Logger.e("sd" + e.getMessage());
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            ChangeLayoutTab(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IConstant.TAKE_PHOTO) {

//            Glide.with(this).load(picPath).centerCrop().into(personInfoIv);

        } else if (resultCode == Activity.RESULT_OK && requestCode == IConstant.ALBUM_PHOTO) {
            if (null != data) {
//                Uri photoUri = data.getData();
                Uri     photoUri = geturi(data);//解决方案
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = this.getContentResolver().query(photoUri, pojo, null, null, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    picPath = cursor.getString(columnIndex);
                    Logger.e("setPicPath" + picPath);

                    adapter.refreshImage(picPath);
                    cursor.close();
                }
//                Glide.with(this).load(picPath).centerCrop().into(personInfoIv);
            }
        }
        Intent intent=new Intent();
        intent.putExtra("picPath",picPath);
        adapter.getItem(0).onActivityResult(requestCode, resultCode, intent);
    }
    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }
    String picPath;
    @Override
    public void transferValue(String value) {
        Logger.e("setPicvaluevaluevaluevaluevaluevaluevaluePath" + value);
        picPath = value;
    }
//    @Override
//    public void transferValue(String value) {
//        Logger.e("setPicvaluevaluevaluevaluevaluevaluevaluePath" + value);
//        picPath = value;
//    }
}
