package com.shengui.app.android.shengui.android.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.dialog.SgActivityPushDialog;
import com.shengui.app.android.shengui.android.ui.utilsview.ImagePagerView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityProductListAdapter;
import com.shengui.app.android.shengui.models.GongQiuDetailModel;
import com.shengui.app.android.shengui.models.ProductModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 16-7-11.
 */
public class SelectFragment extends Fragment implements ScrollViewExtend.ObservableScrollViewCallbacks, View.OnClickListener {


    @Bind(R.id.newTextView)
    TextView newTextView;
    @Bind(R.id.localTextView)
    TextView localTextView;
    @Bind(R.id.AttentionTextView)
    TextView AttentionTextView;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.coordinator_layout)
    RelativeLayout coordinatorLayout;
    @Bind(R.id.hindnewTextView)
    TextView hindnewTextView;
    @Bind(R.id.hindlocalTextView)
    TextView hindlocalTextView;
    @Bind(R.id.hindAttentionTextView)
    TextView hindAttentionTextView;
    @Bind(R.id.hindLayout)
    LinearLayout hindLayout;
    @Bind(R.id.tabLayout)
    RelativeLayout tabLayout;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.Buylayout)
    RelativeLayout Buylayout;
    @Bind(R.id.newBottomLine)
    View newBottomLine;
    @Bind(R.id.newlocalLine)
    View newlocalLine;
    @Bind(R.id.newattentionLine)
    View newattentionLine;
    @Bind(R.id.newhindBottomLine)
    View newhindBottomLine;
    @Bind(R.id.newhindlocalLine)
    View newhindlocalLine;
    @Bind(R.id.newhindattentionLine)
    View newhindattentionLine;
    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.selectItemView)
    ImageView selectItemView;
    @Bind(R.id.pager)
    ImagePagerView pager;
    @Bind(R.id.hindselectItemView)
    ImageView hindselectItemView;
    private List<String> mADParseArray;  //轮播图集合
    private int mParallaxImageHeight;

    public static SelectFragment newInstance() {
        SelectFragment squareFragmentV2 = new SelectFragment();
        return squareFragmentV2;
    }

    private static final int newItem = 0;
    private static final int localItem = 1;
    private static final int attentionItem = 2;

    List<GongQiuDetailModel> listDate;
    ActivityProductListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mine_page, container,
                false);
        ButterKnife.bind(this, view);
        scrollView.setCallbacks(this);
        listview.setFocusable(false);
        initview();
        initpage();
        initEvent();
        return view;
    }

    private void initEvent() {
        Buylayout.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentTools.startProductDetail(getActivity());
            }
        });
        SendTextButton.setOnClickListener(this);
        newTextView.setOnClickListener(this);
        localTextView.setOnClickListener(this);
        AttentionTextView.setOnClickListener(this);
        hindnewTextView.setOnClickListener(this);
        hindlocalTextView.setOnClickListener(this);
        hindselectItemView.setOnClickListener(this);
        hindAttentionTextView.setOnClickListener(this);
        searchlayout.setOnClickListener(this);
        selectItemView.setOnClickListener(this);
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
//                        selectedRefresh.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
//                        selectedRefresh.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        pager.setOnItemClickLisener(new ImagePagerView.OnItemClickLisener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.e("posito"+position);
            }
        });
        List<String> paths=new ArrayList<>();
        paths.add("http://m.easyto.com/m/zhulifuwu_banner.jpg");
        paths.add("http://m.easyto.com/m/japan/images/banner_3y_new.jpg");
        paths.add("http://m.easyto.com/m/japan/images/banner_5y_new.jpg");
        pager.initData(paths);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newTextView:
                ChangeItem(newItem);
                break;
            case R.id.localTextView:
                ChangeItem(localItem);
                break;
            case R.id.AttentionTextView:
                ChangeItem(attentionItem);
                break;
            case R.id.hindnewTextView:
                ChangeItem(newItem);
                break;
            case R.id.hindlocalTextView:
                ChangeItem(localItem);
                break;
            case R.id.hindAttentionTextView:
                ChangeItem(attentionItem);
                break;
            case R.id.searchlayout:
                IntentTools.startSearchDate(getActivity());
                break;
            case R.id.selectItemView:
                IntentTools.startSelectItem(getActivity());
                break;
            case R.id.SendTextButton:
                PopUpDialog();
                break;
            case R.id.Buylayout:
                IntentTools.startPushGongQiu(getActivity());
                break;
            case R.id.hindselectItemView:
                IntentTools.startSelectItem(getActivity());
                break;
        }

    }

    private void ChangeItem(int flags) {
        switch (flags) {
            case newItem:
                newTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                localTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                AttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.white));

                hindnewTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case localItem:
                newTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                localTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                AttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindnewTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case attentionItem:
                newTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                localTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                AttentionTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newattentionLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                hindnewTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindBottomLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindlocalTextView.setTextColor(getResources().getColor(R.color.textcolor_center));
                newhindlocalLine.setBackgroundColor(getResources().getColor(R.color.white));
                hindAttentionTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
                newhindattentionLine.setBackgroundColor(getResources().getColor(R.color.shenguiappcolor));
                break;
        }
    }

    private void initpage() {
        listDate = new ArrayList<>();
        adapter = new ActivityProductListAdapter(getActivity());
        listview.setAdapter(adapter);

        for (int i = 0; i < 10; i++) {
            listDate.add(new GongQiuDetailModel());
        }
        adapter.setRes(listDate);
    }

    private void initview() {
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.twentyhundredthreedp);
        mADParseArray = new ArrayList<String>();
        mADParseArray
                .add("http://m.easyto.com/m/zhulifuwu_banner.jpg");
        mADParseArray
                .add("http://m.easyto.com/m/japan/images/banner_3y_new.jpg");
        mADParseArray
                .add("http://m.easyto.com/m/japan/images/banner_5y_new.jpg");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onScrollChanged(int scrollY) {
        if (scrollY >= mParallaxImageHeight) {
            hindLayout.setVisibility(View.VISIBLE);
            Buylayout.setVisibility(View.VISIBLE);
        } else {
            hindLayout.setVisibility(View.GONE);
            Buylayout.setVisibility(View.GONE);
        }
    }
    public void PopUpDialog() {   //弹框
        SgActivityPushDialog PopUpDialogs = new SgActivityPushDialog(getActivity());
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        PopUpDialogs.show(fragmentTransaction, "ActivityNoticeDialog");
    }
    @Override
    public void onScrollBottom() {
    }
}
