package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.Logger;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.HtmlHandler;
import com.shengui.app.android.shengui.android.ui.utilsview.HtmlRunnable;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.configer.enums.UrlRes;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.EditTextModel;
import com.shengui.app.android.shengui.utils.android.DensityUtil;
import com.shengui.app.android.shengui.utils.android.ScreenUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

/**
 * Created by admin on 2017/2/26.
 */

public class SgEditTextImageViewActivity extends BaseActivity {


    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.skipTv)
    TextView skipTv;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.titleTv)
    TextView titleTv;
    @Bind(R.id.finalfLayout)
    RelativeLayout finalfLayout;
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    public static Handler htmlHandler;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fixedThreadPool.shutdown();
        htmlHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finalfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        if (getIntent().getSerializableExtra("text") != null) {
            webView.setVisibility(View.VISIBLE);
            skipTv.setVisibility(View.GONE);
            String text = (String) getIntent().getSerializableExtra("text");
            MineInfoController.getInstance().get_article(this, Integer.parseInt(text));
//            skipTv.setText(Html.fromHtml(text));
        }
        if (getIntent().getSerializableExtra("textcontent") != null) {
            webView.setVisibility(View.GONE);
            skipTv.setVisibility(View.VISIBLE);
            skipTv.setText(Html.fromHtml((String) getIntent().getSerializableExtra("textcontent")));
            titleTv.setText("关于神龟");
        }
        htmlHandler = new HtmlHandler(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.requestFocus();
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_textview_article_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.get_article.getType()) {

            try {
//                JSONObject ja = new JSONObject(o.toString());
//
//                if (ja.getBoolean("status")) {
//
//                    JSONObject jadata = ja.getJSONObject("data");
//
//                    String content = jadata.getString("content");
//                    String title = jadata.getString("title");
//                    Logger.e("we--"+title+content);



                EditTextModel model = (EditTextModel) result;
                Logger.e("we-----" + model.getTitle());


                Float px= DensityUtil.px2dip(this, ScreenUtils.getScreenWidth(this))*(float)0.9;

                String str="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        "<head>  \n" +
                        "<style>\n" +
                        "body img{width:"+px+"px!important;margin:0px 0px!important;height:auto!important}\n" +
                        "\n" +
                        "</style>\n" +
                        "</head>\n" +
                        "<body>";

                String end="\n" +
                        "</body>\n" +
                        "</html> ";

                titleTv.setText(model.getTitle());
                String content=str+model.getContent()+end;
                Logger.e("html-----"+content);
                webView.loadDataWithBaseURL(  "http://api.gui66.com/",content, "text/html", "utf-8", null);

//                fixedThreadPool.execute(new HtmlRunnable(htmlHandler, model.getContent(), this, R.id.skipTv));
//                    skipTv.setText(Html.fromHtml(content));
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
