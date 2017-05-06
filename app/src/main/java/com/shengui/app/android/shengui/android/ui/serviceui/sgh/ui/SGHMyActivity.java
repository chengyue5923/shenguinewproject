package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.base.ShenGuiApplication;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.fragment.FragmentMyInquiryListView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.TucaoCallBackBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.modle.UserQuestionListBean;
import com.shengui.app.android.shengui.android.ui.serviceui.util.SGHJsonUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.FormBody;

public class SGHMyActivity extends SGUHBaseActivity {


    @Bind(R.id.sgh_my_tab)
    TabLayout sghMyTab;
    @Bind(R.id.sgh_my_vp)
    ViewPager sghMyVp;
    @Bind(R.id.my_input_back)
    ImageView myInputBack;
    @Bind(R.id.editTextInput)
    EditText editTextInput;
    @Bind(R.id.my_input_tv)
    TextView myInputTv;
    @Bind(R.id.my_input_text)
    LinearLayout myInputText;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private List<Fragment> fragments;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TUCAOCALLBACK:
                    TucaoCallBackBean tucaoCallBackBean = (TucaoCallBackBean) msg.obj;
                    if (tucaoCallBackBean.isaBoolean()) {
                        Toast.makeText(SGHMyActivity.this, "吐槽成功", Toast.LENGTH_SHORT).show();
                        UserQuestionListBean.DataBean dataBean = tucaoCallBackBean.getDataBean();
                        dataBean.setStatus(9);
                        fragmentMyInquiry.sghMyInquiryListAdapter.notifyItemChanged(tucaoCallBackBean.getPosition(), dataBean);
                    } else {
                        Toast.makeText(SGHMyActivity.this, "吐槽失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    private InputMethodManager methodManager;
    private final int TUCAOCALLBACK = 1;
    private FragmentMyInquiryListView fragmentMyInquiry;
    FragmentMyInquiryListView fragmentMyFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_activity_my);
        ButterKnife.bind(this);

        final String[] stringArray = getResources().getStringArray(R.array.sgh_my);

        tabLayoutVpInit(stringArray);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final String[] stringArray = getResources().getStringArray(R.array.sgh_my);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyInquiry.reFlash(stringArray);

                        fragmentMyFav.reFlash(stringArray);

                        swipeContainer.setRefreshing(false);//标记刷新结束
                    }
                }, 1000);
            }
        });
    }

    private void tabLayoutVpInit(final String[] stringArray) {
        fragments = new ArrayList<>();

        fragmentMyInquiry = new FragmentMyInquiryListView();
        Bundle bundle0 = new Bundle();
        bundle0.putString("tab", stringArray[0]);
        fragmentMyInquiry.setArguments(bundle0);
        fragments.add(fragmentMyInquiry);

        fragmentMyFav = new FragmentMyInquiryListView();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[1]);
        fragmentMyFav.setArguments(bundle1);
        fragments.add(fragmentMyFav);


        sghMyVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return stringArray[position];

            }
        });

        sghMyTab.setupWithViewPager(sghMyVp);
    }


    public void reportInput(String inquiryId, UserQuestionListBean.DataBean dataBean, int position) {
        myInputText.setVisibility(View.VISIBLE);

        inputControl(inquiryId, dataBean, position);
        editTextInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) editTextInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

    public void inputControl(final String inquiryId, final UserQuestionListBean.DataBean dataBean, final int position) {

        myInputBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextInput.setHint("");
                editTextInput.setText("");
                hideInput();
                myInputText.setVisibility(View.GONE);
            }
        });

        editTextInput.addTextChangedListener(new TextWatcher() {//监听输入内容变化，改变发表按钮颜色
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    myInputTv.setBackgroundResource(R.drawable.video_inout_tv_nature_shape);
                } else {
                    myInputTv.setBackgroundResource(R.drawable.video_inout_tv_select_shape);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {//软键盘发送按钮点击监听
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEND)) {
                    inputInit(inquiryId, dataBean, position);
                    return true;
                }
                return false;
            }
        });

        myInputTv.setOnClickListener(new View.OnClickListener() {//点击发送监听
            @Override
            public void onClick(View v) {
                inputInit(inquiryId, dataBean, position);
            }
        });
    }

    private void inputInit(final String inquiryId, final UserQuestionListBean.DataBean dataBean, final int position) {
        final String content = editTextInput.getText().toString();
        String trim = content.trim();
        if (trim.equals("")) {
            hideInput();
            Toast.makeText(SGHMyActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else {
            ThreadUtil.execute(new Runnable() {
                @Override
                public void run() {
                    FormBody formBody = new FormBody.Builder()//创建表单构造器
                            .add("inquiryId", inquiryId)//添加表单参数
                            .add("content", content)
                            .build();//生成简易表单型RequestBody
                    Boolean ab = SGHJsonUtil.tucaoSave(SGHMyActivity.this, formBody);
                    TucaoCallBackBean tucaoCallBackBean = new TucaoCallBackBean(dataBean, position, ab);
                    Message message = handler.obtainMessage();
                    message.obj = tucaoCallBackBean;
                    message.what = TUCAOCALLBACK;
                    handler.sendMessage(message);
                }
            });
            editTextInput.setText("");
            editTextInput.setHint("");
            myInputText.setVisibility(View.GONE);
            hideInput();
        }
    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow((SGHMyActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShenGuiApplication.getInstance().clearAcCach();
    }
}
