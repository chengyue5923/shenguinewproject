package com.shengui.app.android.shengui.android.ui.serviceui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment.FragmentListView;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/16.
 */

public class SGUHSearchActivity extends SGUHBaseActivity {


    @Bind(R.id.search_init)
    EditText searchInit;
    @Bind(R.id.header_search)
    LinearLayout headerSearch;
    @Bind(R.id.search_back)
    LinearLayout searchBack;
    @Bind(R.id.sguh_search_tab)
    TabLayout sguhSearchTab;
    @Bind(R.id.sguh_search_vp)
    ViewPager sguhSearchVp;
    private List<Fragment> fragments;
    private InputMethodManager methodManager;

    private ServiceSearchFragment inquiryfFragmentListView;
    private ServiceSearchFragment videoFragmentListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sguh_activity_search);
        ButterKnife.bind(this);

        tablayoutViewPageInit();

        searchInit.setFocusable(true);

        searchInit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                    String s = searchInit.getText().toString();
                    inquiryfFragmentListView.dataInit(s);
                    videoFragmentListView.dataInit(s);
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

    private void tablayoutViewPageInit() {

        final String[] stringArray = getResources().getStringArray(R.array.sguh_search);

        fragments = new ArrayList<>();

        inquiryfFragmentListView = new ServiceSearchFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tab", stringArray[0]);
        inquiryfFragmentListView.setArguments(bundle1);
        fragments.add(inquiryfFragmentListView);

        videoFragmentListView = new ServiceSearchFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("tab", stringArray[1]);
        videoFragmentListView.setArguments(bundle2);
        fragments.add(videoFragmentListView);


        sguhSearchVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

        sguhSearchTab.setupWithViewPager(sguhSearchVp);

    }

    private void hideInput() {
        methodManager = ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE));
        methodManager.hideSoftInputFromWindow(((Activity) SGUHSearchActivity.this).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
