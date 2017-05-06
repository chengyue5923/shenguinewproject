package com.base.view.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.base.platform.utils.android.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * 带进度条的WebView
 *
 *
 */
public class ProgressWebView extends WebView {
    OnLoadEndLisener lisener;
    private ProgressBar progressbar;
    Context context;

    @SuppressWarnings("deprecation")
    @SuppressLint("SetJavaScriptEnabled")
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context  = context;
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                5, 0, 0));
        addView(progressbar);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new MyWebViewClient());

    }


    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView", "onPageStarted");
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse response = null;
            if (url.contains("logo")) {
                try {
                    InputStream localCopy = context.getAssets().open("replace_bg.png");
                    response = new WebResourceResponse("image/png", "UTF-8", localCopy);
                } catch (IOException e) {
                    Logger.e(e.getLocalizedMessage(), e);
                }
            }
            return response;
        }
    }



    @SuppressWarnings("deprecation")
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setLisener(OnLoadEndLisener lisener) {
        this.lisener = lisener;
    }

    public interface OnLoadEndLisener {
        void onFinish();
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
                if (lisener != null)
                    lisener.onFinish();
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }



    }


}