package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityContactlistAdapter;
import com.shengui.app.android.shengui.models.ContactPhoneModel;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/28.
 */

public class SgContactsListActivity extends BaseActivity  implements ActivityContactlistAdapter.ShareOnClickListener{
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.InavateText)
    TextView InavateText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;



    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;


//    /**联系人名称**/
//    private ArrayList<String> mContactsName = new ArrayList<String>();
//
//    /**联系人头像**/
//    private ArrayList<String> mContactsNumber = new ArrayList<String>();
//
//    /**联系人头像**/
//    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

    List<ContactPhoneModel> modellist;
    ActivityContactlistAdapter adapter;
    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }
    //权限
    private String[] VIDEO_PERMISSION = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS};
    private List<String> NO_VIDEO_PERMISSION = new ArrayList<String>();
    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String text="";
    @Override
    protected void initData() {
        if(getIntent().getSerializableExtra("test")!=null){
            text=(String)getIntent().getSerializableExtra("test");
        }
        Logger.e("ssssssssssssstext------"+text);

        modellist=new ArrayList<>();
        adapter=new ActivityContactlistAdapter(this);
        adapter.setOnClickListener(this);
        listview.setAdapter(adapter);
        NO_VIDEO_PERMISSION.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < VIDEO_PERMISSION.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, VIDEO_PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                    NO_VIDEO_PERMISSION.add(VIDEO_PERMISSION[i]);
                }
            }
            if (NO_VIDEO_PERMISSION.size() != 0) {
                ActivityCompat.requestPermissions(this, NO_VIDEO_PERMISSION.toArray(new String[NO_VIDEO_PERMISSION.size()]), 1210);
            }
            Logger.e("sssssssssssss"+NO_VIDEO_PERMISSION);
        }
        Logger.e("sssssssseeeeeeeeeeeeeeeeeesssss");
        /**得到手机通讯录联系人信息**/
        getPhoneContacts();
        adapter.setRes(modellist);
    }
    /**得到手机通讯录联系人信息**/
    private void getPhoneContacts() {
        ContentResolver resolver =getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);


        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                ContactPhoneModel mo=new ContactPhoneModel();
                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);

                //得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                //得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                //得到联系人头像Bitamp
                Bitmap contactPhoto = null;

                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.quanzi_person_image);
                }
                mo.setmContactsName(contactName);
                mo.setmContactsNumber(phoneNumber);
                mo.setmContactsPhonto(contactPhoto);
//                mContactsName.add(contactName);
//                mContactsNumber.add(phoneNumber);
//                mContactsPhonto.add(contactPhoto);
                modellist.add(mo);
            }
            phoneCursor.close();
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_list_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }
    //创建一个SmsManager对象
    SmsManager sManager;
    @Override
    public void OnShateItem(ContactPhoneModel model) {
        Logger.e("model"+model.toString());
//        创建一个PendingIntent对象
//        PendingIntent pi=PendingIntent.getActivity(
//                this,0, new Intent(),0);
//        //获取SmsManager
//        sManager= SmsManager.getDefault();
//        //发送短信
//        sManager.sendTextMessage(model.getmContactsNumber(), null, text, pi, null);

        Uri uri = Uri.parse("smsto:" + model.getmContactsNumber());
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        sendIntent.putExtra("sms_body", text);
        startActivityForResult(sendIntent, 1002 );
    }


}
