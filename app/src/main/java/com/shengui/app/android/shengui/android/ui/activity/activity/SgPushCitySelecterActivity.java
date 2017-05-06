package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PersonBean;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PinyinComparator;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.PinyinUtils;
import com.shengui.app.android.shengui.android.ui.utilsview.sortlistview.SideBar;
import com.shengui.app.android.shengui.android.ui.view.CityAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.PushController;
import com.shengui.app.android.shengui.db.UserPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by admin on 2017/1/9.
 */

public class SgPushCitySelecterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.dialog)
    TextView dialog;
    @Bind(R.id.sidebar)
    SideBar sidebar;
    @Bind(R.id.sureTextView)
    TextView sureTextView;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.isroot)
    LinearLayout isroot;
    @Bind(R.id.hottypeLayout)
    LinearLayout hottypeLayout;
    private CityAdapter sortadapter;
    private List<PersonBean> data;
    List<PersonBean> datads;
    List<PersonBean> listmodel;
    private int resultCode = 1000;
//    private EmptyLayout mEmptyLayout;
//    private RelativeLayout reservedLayout;
    @Override
    public void onClick(View v) {

    }

    private List<PersonBean> getData(String[] data) {
        List<PersonBean> listarray = new ArrayList<PersonBean>();
        for (int i = 0; i < data.length; i++) {
            String pinyin = PinyinUtils.getPingYin(data[i]);
            String Fpinyin = pinyin.substring(0, 1).toUpperCase();

            PersonBean person = new PersonBean();
            person.setName(data[i]);
            person.setPinYin(pinyin);
            // 正则表达式，判断首字母是否是英文字母
            if (Fpinyin.matches("[A-Z]")) {
                person.setFirstPinYin(Fpinyin);
            } else {
                person.setFirstPinYin("#");
            }

            listarray.add(person);
        }
        return listarray;

    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);

        listview.setFocusable(false);
        sortadapter = new CityAdapter(this);
        listview.setAdapter(sortadapter);
        sidebar.setTextView(dialog);
        // 设置字母导航触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                try {
                    int position = sortadapter.getPositionForSelection(s.charAt(0));
                    if (position != -1) {
                        listview.setSelection(position);
                    }
                }catch (Exception  e){
                }
            }
        });

    }

    @Override
    public void onConnectStart() {
        super.onConnectStart();
//        mEmptyLayout.showLoading();
    }

    @Override
    public void onFail(Exception e) {
        super.onFail(e);
//        mEmptyLayout.showError();
    }

    @Override
    protected void initEvent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.e("positon" + position + all.get(position).getName());
                String variety_id = "";
                String variety_city = "";
                for (PersonBean bean : datads) {
                    if (bean.getName().equals(all.get(position).getName())) {
                        variety_id = bean.getId();
                        variety_city = bean.getName();
                    }
                }
                Logger.e("bean" + variety_id);
                Intent mIntent = new Intent();
                mIntent.putExtra("variety_id", variety_id);
                mIntent.putExtra("variety_name", variety_city);
                UserPreference.setUsualCityName(variety_city);
                UserPreference.setUsualCityId(variety_id);
                setResult(resultCode, mIntent);

                EventManager.getInstance().post(new IMStringEvent(variety_id,variety_city));
                finish();
            }
        });
    }
    List<PersonBean> all = new ArrayList<>();
    @Override
    protected void initData() {

        if(getIntent().getSerializableExtra("ispub")!=null){
            PushController.getInstance().GetCityType(this,"1");
        }
        PushController.getInstance().GetCityType(this,"0");

        sureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.type_city_select_activity_main;
    }


    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.CitySelecter.getType()) {
            all.clear();
            try {
                hottypeLayout.removeAllViews();
                JSONObject object=new JSONObject(o.toString());
                JSONObject data=object.getJSONObject("data");
                JSONArray hotList=data.getJSONArray("hot");
                Logger.e("houtlist"+hotList);
                listmodel = GsonTool.jsonToArrayEntity(hotList.toString(),PersonBean.class);
                all.addAll(listmodel);


            } catch (JSONException e) {
                e.printStackTrace();
            }



//            mEmptyLayout.showSuccess(false);
            datads = (List<PersonBean>) result;
            String[] list = new String[datads.size()];
            for (int i = 0; i < datads.size(); i++) {
                list[i] = datads.get(i).getName();
            }
            data = getData(list);
            Collections.sort(data, new PinyinComparator());
            all.addAll(data);
            sortadapter.setRes(all);




        }
    }
    class OnHotItemOnListener implements View.OnClickListener{

        int position;
        public OnHotItemOnListener(int pos ) {
            position=pos;
        }

        @Override
        public void onClick(View v) {
            Logger.e("positon" + position + data.get(position).getName());
            String variety_id = "";
            String variety_city = "";
            for (PersonBean bean : listmodel) {
                if (bean.getName().equals(listmodel.get(position).getName())) {
                    variety_id = bean.getId();
                    variety_city = bean.getName();
                }
            }
            Logger.e("bean" + variety_id);
            Intent mIntent = new Intent();
            mIntent.putExtra("variety_id", variety_id);
            mIntent.putExtra("variety_name", variety_city);
            UserPreference.setUsualCityName(variety_city);
            UserPreference.setUsualCityId(variety_id);
            setResult(resultCode, mIntent);
            EventManager.getInstance().post(new IMStringEvent(variety_id,variety_city));
            finish();
        }
    }
}
