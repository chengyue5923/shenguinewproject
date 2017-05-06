package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/10/25.
 */
public class WebViewActivity extends BaseActivity {


    @Bind(R.id.webView1)
    WebView webview;
    @Bind(R.id.titleTv)
    TextView titleTv;
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.finashlayout)
    RelativeLayout finashlayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initEvent() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAppCacheEnabled(false);//是否使用缓存
        webSettings.setDomStorageEnabled(true);//DOM Storage
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                if(url.startsWith("http")||url.startsWith("https")){
                    return false;
                }
                else{
                    return  true;
                }
            }
        });
        finashlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void initData() {
        if (getIntent() != null) {
            String url = (String) getIntent().getSerializableExtra("Url");
            Logger.e("urkl" + url);
            webview.loadUrl(url);
            titleTv.setText((String)getIntent().getSerializableExtra("name"));
        }
//        webview.loadUrl("http://www.baidu.com/");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.webwiew;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

}
