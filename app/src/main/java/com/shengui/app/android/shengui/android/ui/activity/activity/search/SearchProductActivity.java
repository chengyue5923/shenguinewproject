package com.shengui.app.android.shengui.android.ui.activity.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgCreateTopicDetailActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityProductListAdapter;
import com.shengui.app.android.shengui.android.ui.view.ActivitySearchListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.ActivityController;
import com.shengui.app.android.shengui.controller.GongQiuController;
import com.shengui.app.android.shengui.controller.GuiMiController;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.controller.TopicController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.GongQiuListModel;
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
 * Created by admin on 2017/1/3.
 */

public class SearchProductActivity extends BaseActivity implements View.OnClickListener,ScrollViewExtend.ObservableScrollViewCallbacks  {
    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.edittext)
    EditText edittext;
    @Bind(R.id.relayout)
    RelativeLayout relayout;
    @Bind(R.id.searchNoDate)
    RelativeLayout searchNoDate;
    @Bind(R.id.gview)
    GridView gview;
    @Bind(R.id.Nonelayout)
    LinearLayout Nonelayout;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.searchDatelayout)
    LinearLayout searchDatelayout;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivitySearchListAdapter searchListAdapter;
    ActivityProductListAdapter adapter;
    List<GongQiuDetailModel> listDate;
    @Bind(R.id.searchTv)
    TextView searchTv;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.allsearchTv)
    TextView allsearchTv;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    private List<GongQiuDetailModel> mDatas;
    private int firstNumber = 0;
    private int size = 10;
    private boolean canload = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.searchlayout:
                edittext.setFocusable(true);
                break;
            case R.id.SendTextButton:
                finish();
                break;
            case R.id.searchTv:
                gview.setVisibility(View.GONE);
                allsearchTv.setVisibility(View.GONE);
                searchDatelayout.setVisibility(View.VISIBLE);
                search();
                break;
        }
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        scrollView.setCallbacks(this);
        MineInfoController.getInstance().supply_hot_search(this);
    }

    @Override
    protected void initEvent() {
        searchlayout.setOnClickListener(this);
        searchTv.setOnClickListener(this);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                edittext.setText(searchListAdapter.getItem(position).getName());
//                gview.setVisibility(View.GONE);
//                allsearchTv.setVisibility(View.GONE);
//                searchDatelayout.setVisibility(View.VISIBLE);
//                IntentTools.startOtherDetail(this,searchListAdapter.get);
                SkipActivity(searchListAdapter.getItem(position));
            }
        });
        SendTextButton.setOnClickListener(this);
        edittext.setOnEditorActionListener(editorActionListener);
    }

    //首页轮播图和四张菱形图的跳转
    public void SkipActivity(ImageModel model) {
        if (model != null) {
            switch (model.getRedirect_type()) {
                case "0":
                    if(!StringTools.isNullOrEmpty(model.getRedirect_url())){
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
    public EditText.OnEditorActionListener editorActionListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                gview.setVisibility(View.GONE);
                allsearchTv.setVisibility(View.GONE);
                searchDatelayout.setVisibility(View.VISIBLE);
                search();
//                edittext.setFocusable(false);
            }
            return true;
        }
    };
    public void search() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        GongQiuController.getInstance().getGongQiuList(this, "", "", 0, firstNumber, size, edittext.getText().toString());
    }

    @Override
    protected void initData() {
        searchListAdapter = new ActivitySearchListAdapter(this);
        gview.setAdapter(searchListAdapter);
        listDate = new ArrayList<>();
        adapter = new ActivityProductListAdapter(this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startGongQiuDetail(SearchProductActivity.this,adapter.getItem(position).getId());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_product_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
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
        if (flag == HttpConfig.supply_hot_search.getType()) {
            Logger.e("dss" + result);
            searchListAdapter.setRes((List<ImageModel>) result);
        }
        try {
            JSONObject jsonObject = new JSONObject(o.toString());
            if (jsonObject.getBoolean("status")) {
                JSONObject ja=jsonObject.getJSONObject("data");
                JSONArray jsonArray =ja.getJSONArray("result");
                List<GongQiuDetailModel> mDatas= GsonTool.jsonToArrayEntity(jsonArray.toString(),GongQiuDetailModel.class);
                if (mDatas.size() != 0) {
                    if (firstNumber == 0) {
                        adapter.clearAll();
                        adapter.notifyDataSetChanged();
                        adapter.setRes(mDatas);
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.append(mDatas);
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                adapter.clearAll();
                adapter.notifyDataSetChanged();
                ToastTool.show(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollChanged(int scrollY) {

    }

    @Override
    public void onScrollBottom() {
        Logger.e("ddddddbottom" + firstNumber);
        firstNumber = firstNumber + size;
        GongQiuController.getInstance().getGongQiuList(this, "", "", 0, firstNumber, size, edittext.getText().toString());
    }
}
