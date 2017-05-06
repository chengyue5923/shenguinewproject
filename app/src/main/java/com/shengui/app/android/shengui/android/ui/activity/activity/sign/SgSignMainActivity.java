package com.shengui.app.android.shengui.android.ui.activity.activity.sign;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.DateTools;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.fragment.SignDialogFragment;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.view.ActivitySignListAdapter;
import com.shengui.app.android.shengui.android.ui.view.SignAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.models.SignEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class SgSignMainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.orderText)
    TextView orderText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.signstates)
    TextView signstates;
    @Bind(R.id.QuanZiNameText)
    TextView QuanZiNameText;
    @Bind(R.id.topLauout)
    RelativeLayout topLauout;
    @Bind(R.id.NumberTextView)
    TextView NumberTextView;
    @Bind(R.id.titlenameLayout)
    RelativeLayout titlenameLayout;
    @Bind(R.id.signtextViewaa)
    TextView signtextViewaa;
    @Bind(R.id.giftText)
    TextView giftText;
    @Bind(R.id.giftText3)
    TextView giftText3;
    @Bind(R.id.giftText2)
    TextView giftText2;
    @Bind(R.id.activity_main_tv_main_day)
    TextView tvSignDay;
    @Bind(R.id.activity_main_tv_score)
    TextView tvScore;
    @Bind(R.id.activity_main_tv_year)
    TextView tvYear;
    @Bind(R.id.activity_main_tv_month)
    TextView tvMonth;
    @Bind(R.id.activity_main_ll_date)
    LinearLayout activityMainLlDate;
    @Bind(R.id.activity_main_cv)
    SignView signView;
    @Bind(R.id.views)
    View views;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.giftOneDay)
    TextView giftOneDay;
    @Bind(R.id.giftTwoDay)
    TextView giftTwoDay;
    @Bind(R.id.Day3)
    TextView Day3;
    private List<SignEntity> data;
    ActivitySignListAdapter adapter;
    int singNumbwr=0;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        onReady();
        listview.setFocusable(false);
        adapter = new ActivitySignListAdapter(this);
//        List<ProductModel> list=new ArrayList<>();
//        adapter .setRes(list);
        listview.setAdapter(adapter);

        QuanZiNameText.setText(UserPreference.getName());
        Glide.with(this).load(UserPreference.getAvatar()).centerCrop().into(personInfoIv);

    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        signstates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signstates.getText().equals("未签到")) {
                    if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                        signToday();
                    }else{
                        ToastTool.show("您还没有登录");
                    }

                } else {
                    ToastTool.show("您今天已签到");
                }
            }
        });
        if (signView != null) {
            signView.setOnTodayClickListener(onTodayClickListener);
        }
    }

    private void onReady() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        tvSignDay.setText(Html.fromHtml(String.format(getString(R.string.you_have_sign), "#999999", "#1B89CD", 3)));
        tvScore.setText(String.valueOf(3015));
        tvYear.setText(String.valueOf(calendar.get(Calendar.YEAR)) + "年");
        tvMonth.setText(getResources().getStringArray(R.array.month_array)[month]);

//        Calendar calendarToday = Calendar.getInstance();
//        int dayOfMonthToday = calendarToday.get(Calendar.DAY_OF_MONTH);

//        data = new ArrayList<>();
//        Random ran = new Random();
//        for (int i = 0; i < 30; i++) {
//            SignEntity signEntity = new SignEntity();
//            if (dayOfMonthToday == i + 1)
//                signEntity.setDayType(2);
//            //0  签到    1  未签到
////                signEntity.setDayType((ran.nextInt(1000) % 2 == 0) ? 0 : 1);
//            if(i==8){
//                signEntity.setDayType(1);
//            }
//            data.add(signEntity);
//        }
//        SignAdapter signAdapter = new SignAdapter(data);
//        signView.setAdapter(signAdapter);
    }

    private void onSign() {
        signstates.setText("已签到");
        FragmentManager fragmentManager = getSupportFragmentManager();
        SignDialogFragment signDialogFragment = SignDialogFragment.newInstance(15);
        signDialogFragment.setOnConfirmListener(onConfirmListener);
        signDialogFragment.show(fragmentManager, SignDialogFragment.TAG);
    }

    private void signToday() {
        if (signstates.getText().equals("未签到")) {
            if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                MineInfoController.getInstance().SignUser(this, UserPreference.getTOKEN());
            }
        } else {
            ToastTool.show("您今天已签到");
        }
//        data.get(signView.getDayOfMonthToday() - 1).setDayType(SignView.DayType.SIGNED.getValue());
//        signView.notifyDataSetChanged();
//
//        int score = Integer.valueOf((String) tvScore.getText());
//        tvScore.setText(String.valueOf(score + 15));
    }
    String year;
    String month;
    @Override
    protected void initData() {

        year = DateTools.dateFormatterOfDateTimeForNow(DateTools.DATE_FORMAT_STYLE_13);
        month = DateTools.dateFormatterOfDateTimeForNow(DateTools.DATE_FORMAT_STYLE_14);

        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            MineInfoController.getInstance().SingListInfo(this, year + month, UserPreference.getTOKEN());
            MineInfoController.getInstance().SingGiftInfo(this, year + month, UserPreference.getTOKEN());
            MineInfoController.getInstance().GiftListInfo(this, year + month, UserPreference.getTOKEN());
        } else {
            ToastTool.show("您还没有登录");
        }

    }

    private SignView.OnTodayClickListener onTodayClickListener = new SignView.OnTodayClickListener() {
        @Override
        public void onTodayClick() {
//            onSign();
            if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                signToday();
            }else{
                ToastTool.show("您还没有登录");
            }

        }
    };

    private SignDialogFragment.OnConfirmListener onConfirmListener = new SignDialogFragment.OnConfirmListener() {
        @Override
        public void onConfirm() {
            signToday();
        }
    };
    boolean isSign = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.SignUser.getType()) {
            try {
                JSONObject object = new JSONObject(o.toString());
                if (object.getBoolean("status")) {
//                    data.get(signView.getDayOfMonthToday() - 1).setDayType(SignView.DayType.SIGNED.getValue());
                    onSign();
                    data.get(signView.getDayOfMonthToday() - 1).setDayType(SignView.DayType.SIGNED.getValue());
                    signView.notifyDataSetChanged();
                    signstates.setText("已签到");
                    MineInfoController.getInstance().SingListInfo(this, year + month, UserPreference.getTOKEN());
                } else {
                    ToastTool.show(object.getString("message"));
                }
            } catch (Exception e) {
                ToastTool.show("签到失败");
            }
        }
        /**
         * 签到规则
         *
         */
        if (flag == HttpConfig.SingGiftInfo.getType()) {
            Logger.e("dataddd" + o.toString());
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Logger.e("json---" + jsonArray.get(i).toString());
                    JSONObject ja = (JSONObject) jsonArray.get(i);
                    String jaDay = ja.getString("days");
                    JSONObject JaGift = ja.getJSONObject("gift");
                    String jagitname = JaGift.getString("name");
                    Logger.e("dddd-------" + jaDay + JaGift + jagitname);
                    if (i == 0) {
                        giftText.setText(jagitname);
                        giftOneDay.setText(jaDay+"天");
                    }
                    if(i==1){
                        giftText3.setText(jagitname);
                        giftTwoDay.setText(jaDay+"天");
                    }
                    if(i==2){
                        giftText2.setText(jagitname);
                        Day3.setText(jaDay+"天");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /**
         * 自然月份
         */
        if (flag == HttpConfig.SingListInfo.getType()) {

            Logger.e("result" + o.toString());
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject js = jsonObject.getJSONObject("data");
                signstates.setVisibility(View.VISIBLE);
                isSign = js.getBoolean("is_sign");
                Logger.e("is_sign" + isSign);
                if (isSign) {
                    signstates.setText("已签到");
                } else {
                    signstates.setText("未签到");
                }
                List<Integer> time = new ArrayList<>();
                if (js.getJSONArray("sign_data") != null) {
                    JSONArray jsond = js.getJSONArray("sign_data");
                    Logger.e("jsdonsss" + jsond);
                    for (int i = 0; i < jsond.length(); i++) {
                        Integer strDate = (Integer) jsond.get(i);
                        Logger.e("Stringadate" + strDate);
                        String date = String.valueOf(strDate);
                        String timeItem = date.substring(date.length() - 2, date.length());

                        Logger.e("timeItem" + timeItem);

                        Integer intStr = Integer.parseInt(timeItem);
                        Logger.e("intStr" + intStr);
                        time.add(intStr);
                    }
                    Logger.e("time" + time);
                }

//                /**
//                 * 已签到状态，时间已过
//                 */
//                SIGNED(0),
//                        /**
//                         * 未签到状态，时间已过
//                         */
//                        UNSIGNED(1),
//                        /**
//                         * 等待状态，即当日还未签到
//                         */
//                        WAITING(2),
//                /**

                Calendar calendarToday = Calendar.getInstance();
                int dayOfMonthToday = calendarToday.get(Calendar.DAY_OF_MONTH);

                data = new ArrayList<>();
//                for (int i = 0; i < 30; i++) {
//                    SignEntity signEntity = new SignEntity();
//                    if(time.size()==0){
//                        signEntity.setDayType(1);
//                    }else{
//                        for (Integer t : time) {
//                          Logger.e("ddddd"+ i+"------"+t);
//                          if (t == i + 1) {
//                              Logger.e("ddddwwwwwwwwd"+ i+"------"+t);
//                              signEntity.setDayType(0);
//                              continue;
//                          } else {
//                              Logger.e("dddddddddddddddsssssssssd"+ i+"------"+t);
//                              signEntity.setDayType(1);
//                          }
//                      }
//                    }
//                    if (dayOfMonthToday == i + 1&&!isSign)
//                        signEntity.setDayType(2);
//                    data.add(signEntity);
//                }

                for (int i = 1; i <= 31; i++) {
                    SignEntity signEntity = new SignEntity();
                    if(time.size()==0){
                        signEntity.setDayType(1);
                    }else{
                        for (Integer t : time) {
                            if (t == i) {
                                signEntity.setDayType(0);
                                singNumbwr++;
                                break;
                            } else {
                                signEntity.setDayType(1);
                            }
                        }
                    }
                    if (dayOfMonthToday == i&&!isSign)
                        signEntity.setDayType(2);
                    data.add(signEntity);
                }

                SignAdapter signAdapter = new SignAdapter(data);
                signView.setAdapter(signAdapter);
                String rangk=js.getString("rank");
                String sign_count=js.getString("sign_count");
//                int ran=new Random().nextInt(5);
                NumberTextView.setText("签到"+sign_count+"天,今日排名第"+rangk+"名");
            } catch (JSONException e) {
                Logger.e("exception-------" + e.getMessage());
            }
        }
        if (flag == HttpConfig.GiftListInfo.getType()) {
            JSONObject jsonObject = null;
            try {
                List<ProductModel> list = new ArrayList<>();
                jsonObject = new JSONObject(o.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    ProductModel model = new ProductModel();

                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    JSONObject user_info = jsonObject1.getJSONObject("user_info");
                    String name = user_info.getString("name");

                    JSONObject runtime_data_array = jsonObject1.getJSONObject("runtime_data_array");
                    String gift = runtime_data_array.getString("name");

                    model.setName(name);
                    model.setGift(gift);
                    list.add(model);
                }

                adapter.setRes(list);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
        }
    }
}
