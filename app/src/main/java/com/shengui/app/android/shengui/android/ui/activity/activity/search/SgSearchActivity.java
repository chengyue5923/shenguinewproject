package com.shengui.app.android.shengui.android.ui.activity.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.MultipleTextViewGroup;
import com.shengui.app.android.shengui.android.ui.view.MainSexangleAdapter;
import com.shengui.app.android.shengui.android.ui.view.SearchPagerAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.models.QuanziList;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/30.
 */

public class SgSearchActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener,MultipleTextViewGroup.OnMultipleTVItemClickListener  {
    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.edittext)
    EditText mEditTextMsg;
    @Bind(R.id.relayout)
    RelativeLayout relayout;
    @Bind(R.id.searchquanzi)
    TextView searchquanzi;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.searchqumizi)
    TextView searchqumizi;
    @Bind(R.id.micentent)
    TextView micentent;
    @Bind(R.id.seartiezi)
    TextView seartiezi;
    @Bind(R.id.tiecontent)
    TextView tiecontent;
    @Bind(R.id.seatLayout)
    LinearLayout seatLayout;
    @Bind(R.id.Nonelayout)
    LinearLayout Nonelayout;
    @Bind(R.id.quanLayout)
    RelativeLayout quanLayout;
    @Bind(R.id.miLayout)
    RelativeLayout miLayout;
    @Bind(R.id.tielayout)
    RelativeLayout tielayout;

    private final static int Quanzi = 0;
    private final static int GuiMi = 1;
    private final static int Tiezi = 2;

    public int defaultflage=0;

    @Bind(R.id.searchResultlayout)
    LinearLayout searchResultlayout;
    @Bind(R.id.linesd)
    View linesd;
    @Bind(R.id.hotlayout)
    TextView hotlayout;
    @Bind(R.id.hotlineView)
    View hotlineView;
    @Bind(R.id.originzationLayout)
    TextView originzationLayout;
    @Bind(R.id.origiazationlineView)
    View origiazationlineView;
    @Bind(R.id.allLayout)
    TextView allLayout;
    @Bind(R.id.alllineView)
    View alllineView;
    @Bind(R.id.myTabLayout)
    LinearLayout myTabLayout;
    @Bind(R.id.topLayout)
    RelativeLayout topLayout;
    @Bind(R.id.line)
    View line;
    @Bind(R.id.my_pager)
    ViewPager myPager;

    String key = "";
    MainSexangleAdapter adapter;
    SearchPagerAdapter Searchadapter;
    @Bind(R.id.main_rl)
    MultipleTextViewGroup mainRl;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SendTextButton:
                finish();
                break;
            case R.id.tielayout:
                SearchDate(Tiezi);
                break;
            case R.id.miLayout:
                SearchDate(GuiMi);
                break;
            case R.id.quanLayout:
                SearchDate(Quanzi);
                break;
            case R.id.hotlayout:
                setCurrentTab(0);
                break;
            case R.id.originzationLayout:
                setCurrentTab(1);
                break;
            case R.id.allLayout:
                setCurrentTab(2);
                break;

        }
    }

    private void setCurrentTab(int index) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activityMain.getWindowToken(), 0);
//        mEditTextMsg.setInputType(InputType.TYPE_NULL); //关闭软键盘
        ChangeView(index);
        myPager.setCurrentItem(index);
    }

    public void ChangeView(int flags) {
        switch (flags) {
            case 0:
                hotlayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 1:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));
                originzationLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                allLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 2:
                hotlayout.setTextColor(getResources().getColor(R.color.titlecolor));
                hotlineView.setBackgroundColor(getResources().getColor(R.color.white));

                originzationLayout.setTextColor(getResources().getColor(R.color.titlecolor));
                origiazationlineView.setBackgroundColor(getResources().getColor(R.color.white));
                allLayout.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                alllineView.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                break;
        }

    }

    public void SearchDate(int date) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activityMain.getWindowToken(), 0);
        Logger.e("date" + date + mEditTextMsg.getText());
        Searchadapter.setKey(date, mEditTextMsg.getText().toString());
        setCurrentTab(date);
        seatLayout.setVisibility(View.GONE);
        Nonelayout.setVisibility(View.GONE);
        searchResultlayout.setVisibility(View.VISIBLE);
        Searchadapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        MineInfoController.getInstance().search_hot_search(this);
        myPager.setOffscreenPageLimit(4);
        String[] strings = getResources().getStringArray(R.array.mine_search);
        myPager.setOnPageChangeListener(this);
        Searchadapter = new SearchPagerAdapter(getSupportFragmentManager(), strings);
        myPager.setAdapter(Searchadapter);
        mainRl.setOnMultipleTVItemClickListener(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }
    @Override
    protected void initEvent() {
        SendTextButton.setOnClickListener(this);
        hotlayout.setOnClickListener(this);
        originzationLayout.setOnClickListener(this);
        allLayout.setOnClickListener(this);
        tielayout.setOnClickListener(this);
        miLayout.setOnClickListener(this);
        quanLayout.setOnClickListener(this);
        mEditTextMsg.addTextChangedListener(new EditChangedListener());
        mEditTextMsg.setOnEditorActionListener(editorActionListener);
//        mEditTextMsg.setFocusable(true);//设置输入框可聚集
//        mEditTextMsg.setFocusableInTouchMode(true);//设置触摸聚焦
//        mEditTextMsg.requestFocus();//请求焦点
//        mEditTextMsg.findFocus();//获取焦点
    }
    public EditText.OnEditorActionListener editorActionListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                SearchDate(defaultflage);
            }
            return true;
        }
    };
    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_activity;
    }
    List<ImageModel> modellist;
    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        Logger.e("ddf" + o.toString());
        if (flag == HttpConfig.CircleDetail.getType()) {
            Logger.e("logger" + result);
            QuanziList QuanZiModel = (QuanziList) result;

            //官方圈子
            if (QuanZiModel.getIs_public().equals("1")) {
                IntentTools.startQuanziManageOffical(this, QuanZiModel);
                return;
            }
//            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
//                IntentTools.startQuanziManage(this, Integer.parseInt(QuanZiModel.getId()));
//                return;
//            }
            if (Integer.parseInt(QuanZiModel.getUser_id()) == UserPreference.getUid()) {
                IntentTools.startQuanziDetailSelf(this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 0) {
                IntentTools.startquanziDetail(this, QuanZiModel);
                return;
            }
            if (Integer.parseInt(QuanZiModel.getIs_in()) == 1) {
                IntentTools.startQuanziDetailSelf(this, QuanZiModel);
                return;
            }
        }
        if (flag == HttpConfig.search_hot_search.getType()) {
            mainRl.removeAllViews();
          try {
              JSONObject ja=new JSONObject(o.toString());
              if(ja.getBoolean("status")){
                  JSONArray jsonArray=ja.getJSONArray("data");
                  modellist = GsonTool.jsonToArrayEntity(jsonArray.toString(),ImageModel.class);
                  Logger.e("model"+modellist);
                  List<String> dataList = new ArrayList<String>();
                  for(ImageModel da:modellist){
                      dataList.add("#"+da.getName()+"#");
                  }
                  mainRl.setTextViews(dataList);
              }else{
                  ToastTool.show(ja.getString("message"));
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentTab(position);
        defaultflage=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onMultipleTVItemClick(View view, int position) {
//        mEditTextMsg.setText(modellist.get(position).getName());

        SkipActivity(modellist.get(position));
    }

    //首页轮播图和四张菱形图的跳转
    public void SkipActivity(ImageModel model) {
        if (model != null) {
            switch (model.getRedirect_type()) {
                case "0":
                    if(!StringTools.isNullOrEmpty( model.getRedirect_url())){
                        IntentTools.startWebViewActivity(this, model.getRedirect_url(),model.getName());
                    }
                    break;
                case "1":
                    IntentTools.startTieZiDetail(this, model.getRedirect_url());
                    break;
                case "2":
                    IntentTools.startGongQiuDetail(this, model.getRedirect_url());
                    break;
                case "3":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                    IntentTools.startQuanziDetailById(this, model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "4":
                    if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
                        GuiMiController.getInstance().CiecleContentDetail(this, Integer.parseInt(model.getRedirect_url()), UserPreference.getUid());
//                    IntentTools.startQuanziDetailById(this, model.getRedirect_url());
                    } else {
                        GuiMiController.getInstance().CiecleContentQuanziDetail(this, Integer.parseInt(model.getRedirect_url()));
                    }
                    break;
                case "5":
                    IntentTools.startTopicList(this);
                    break;
                case "6":
                    IntentTools.startTopicDetail(this, model.getRedirect_url());
                    break;
                case "7":
                    IntentTools.startDetail(this, model.getRedirect_url());
                    break;
                case "8":
                    IntentTools.startEditTextView(this, model.getRedirect_url());
                    break;
                default:
                    break;
            }
        }
    }
    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 20;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Nonelayout.setVisibility(View.GONE);
            seatLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            content.setText(s);
            micentent.setText(s);
            tiecontent.setText(s);
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
//            editStart = mEditTextMsg.getSelectionStart();
//            editEnd = mEditTextMsg.getSelectionEnd();
//            if (temp.length() > charMaxNum) {
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
//            }

        }
    }
}
