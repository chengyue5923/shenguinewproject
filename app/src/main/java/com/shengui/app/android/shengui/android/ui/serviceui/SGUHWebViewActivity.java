package com.shengui.app.android.shengui.android.ui.serviceui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SGUHWebViewActivity extends AppCompatActivity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sguh_webview);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        init(url);
//        //设置WebView属性，能够执行Javascript脚本
//        webView.getSettings().setJavaScriptEnabled(true);
//        //加载需要显示的网页
//        webView.loadUrl(url);
//        //设置Web视图
//        setContentView(webView);
    }

//
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
