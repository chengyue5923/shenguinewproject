package com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.SGUHBaseActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/18.
 */

public class OfficialCaseDetailActivity extends SGUHBaseActivity {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.webView)
    WebView webView;
    private String officialId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sgh_webview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        officialId = intent.getStringExtra("officialId");

        init(Api.baseUrl+"/api/inquiry/showinto.do?id="+officialId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init(String webUrl) {

        //WebView加载web资源
        webView.loadUrl(webUrl);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开

        WebSettings webSettings = webView.getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
        webView.requestFocusFromTouch();

        webSettings.setJavaScriptEnabled(true);  //支持js

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
