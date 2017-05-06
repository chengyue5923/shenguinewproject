package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/26.
 */

public class SgTextViewActivity extends BaseActivity {


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
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void initView() {
        ButterKnife.bind(this);

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_textview_article_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

        if (flag == HttpConfig.get_article.getType()) {

            try {
                JSONObject ja = new JSONObject(o.toString());

                if (ja.getBoolean("status")) {

                    JSONObject jadata = ja.getJSONObject("data");

                    String content = jadata.getString("content");
                    String title = jadata.getString("title");


                    titleTv.setText(title);

                    skipTv.setText(Html.fromHtml(content));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
