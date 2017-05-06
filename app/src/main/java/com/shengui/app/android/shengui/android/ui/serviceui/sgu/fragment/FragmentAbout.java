package com.shengui.app.android.shengui.android.ui.serviceui.sgu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;

/**
 * Created by Administrator on 2017/4/17.
 */

public class FragmentAbout extends BaseFragment {

    View view;
    private WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sguh_webview, container, false);
        webView = (WebView) view.findViewById(R.id.webView);

        init();
        return view;
    }

    private void init() {

        //WebView加载web资源
        webView.loadUrl(Api.baseUrl + "/api/course/getAbout.do");
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
