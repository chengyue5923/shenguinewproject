package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.adapter.SGHHomePageRVAdapter;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.InquiryListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SGHSearchActivity extends SGUHBaseActivity {


    @Bind(R.id.search_init)
    EditText searchInit;
    @Bind(R.id.header_search)
    LinearLayout headerSearch;
    @Bind(R.id.search_back)
    LinearLayout searchBack;
    @Bind(R.id.search_text)
    TextView searchText;
    @Bind(R.id.search_no_keyword)
    TextView searchNoKeyword;
    @Bind(R.id.search_text1)
    TextView searchText1;
    @Bind(R.id.search_no_result)
    LinearLayout searchNoResult;
    @Bind(R.id.search_keyword)
    TextView searchKeyword;
    @Bind(R.id.search_numb)
    TextView searchNumb;
    @Bind(R.id.search_other)
    TextView searchOther;
    @Bind(R.id.search_recycler_view)
    RecyclerView searchRecyclerView;
    @Bind(R.id.search_have_data)
    LinearLayout searchHaveData;
    private InputMethodManager methodManager;
    SGHHomePageRVAdapter rvadapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<InquiryListBean.DataBean> dataBeen = (List<InquiryListBean.DataBean>) msg.obj;
            if (dataBeen.size() != 0) {
                searchNoResult.setVisibility(View.GONE);
                searchHaveData.setVisibility(View.VISIBLE);
                searchKeyword.setText("关键字" + s + " ,共找到");
                searchNumb.setText(dataBeen.size() + "");
                rvadapter = new SGHHomePageRVAdapter(SGHSearchActivity.this, dataBeen);
                searchRecyclerView.setAdapter(rvadapter);
            } else {
                searchNoResult.setVisibility(View.VISIBLE);
                searchHaveData.setVisibility(View.GONE);
                searchNoKeyword.setText(s);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_search);
        ButterKnife.bind(this);

        searchInit.setFocusable(true);

        searchInit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    searchKeyWordResult();
                    hideInput();
                    return true;
                }
                return false;
            }
        });

        searchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow(((Activity) SGHSearchActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private String s = "";

    private void searchKeyWordResult() {
        s = searchInit.getText().toString();

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(SGHSearchActivity.this, LinearLayoutManager.VERTICAL, false));

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                ThreadUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<InquiryListBean.DataBean> dataBeen = SGHJsonUtil.searchInquiryList(SGHSearchActivity.this, s);//获取全部消息
                        Message message = handler.obtainMessage();
                        message.obj = dataBeen;
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }
}
