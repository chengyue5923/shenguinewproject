package com.shengui.app.android.shengui.utils.im;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;


/**
 * Created by yanbo on 2016/7/19.
 */
public class CommonToolBar extends RelativeLayout implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView title;
    private ImageView imageView;
    private BaseActivity mActivity;
    private View bottomLine;
    private TextView rightText,finishTextView,toolbar_title_left;
    private TextView messageRedImage;
    private ClickRightListener clickRightListener;
    private ClickLeftListener clickLeftListener;

    public void setClickLeftListener(ClickLeftListener clickLeftListener) {
        this.clickRightListener = clickRightListener;
    }

    public interface ClickLeftListener {
        void OnClickLeft(View view);
    }


    public void setClickRightListener(ClickRightListener clickRightListener) {
        this.clickRightListener = clickRightListener;
    }

    public interface ClickRightListener {
        void OnClickRight(View view);
    }

    public CommonToolBar(Context context) {
        this(context, null);

    }

    public CommonToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (BaseActivity) context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_toolbar, this);
        bottomLine = (View) view.findViewById(R.id.bottomLine);
        bottomLine.setVisibility(View.VISIBLE);
        toolbar_title_left=(TextView)view.findViewById(R.id.toolbar_title_left);
        toolbar_title_left.setOnClickListener(this);
        toolbar_title_left.setVisibility(GONE);
        finishTextView=(TextView)view.findViewById(R.id.finishTextView);
        finishTextView.setVisibility(View.GONE);
        finishTextView.setOnClickListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        title = (TextView) view.findViewById(R.id.toolbar_title);
        rightText = (TextView) view.findViewById(R.id.toolbar_right_text);
        messageRedImage = (TextView) view.findViewById(R.id.messageRedImage);
        imageView = (ImageView) view.findViewById(R.id.toolbar_right_image);
        TypedArray a = mActivity.obtainStyledAttributes(attrs, R.styleable.toolBar);
        toolbar.setTitle("");
        title.setText(a.getString(R.styleable.toolBar_mToolBar_title));
        title.setTextColor(getResources().getColor(a.getResourceId(R.styleable.toolBar_mToolBar_titleColor, R.color.common_title_text)));

        toolbar.setBackgroundColor(getResources().getColor(a.getResourceId(R.styleable.toolBar_mToolBar_background, R.color.common_title)));
        mActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        if (a.getBoolean(R.styleable.toolBar_mToolBar_showBack, true)) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(getResources().getDrawable(a.getResourceId(R.styleable.toolBar_mToolBar_backIv, R.drawable.back_default)));
        }
        if (a.getBoolean(R.styleable.toolBar_mToolBar_showRight, false)) {
            imageView.setVisibility(VISIBLE);
            imageView.setImageResource(a.getResourceId(R.styleable.toolBar_mToolBar_rightImage, android.R.color.transparent));
        }
        if (a.getBoolean(R.styleable.toolBar_mToolBar_showRightText, false)) {
            rightText.setVisibility(VISIBLE);
            rightText.setText(a.getString(R.styleable.toolBar_mToolBar_rightText));
        }

        imageView.setOnClickListener(this);
        rightText.setOnClickListener(this);
        a.recycle();
    }

    public void setTitle(String titleText) {
        title.setText(titleText);

    }

    public String  getRightText(){
        return rightText.getText().toString();
    }
    public void  setRightText(String text){
         rightText.setText(text);
    }
    public void setTitleTextColor(int ResId) {
        title.setTextColor(getResources().getColor(ResId));
    }

    public void setRightImage(int resId) {
        imageView.setImageResource(resId);

    }

    public ImageView getRightView() {
        return imageView;
    }

    public void setBackImage(int resId) {
        toolbar.setNavigationIcon(getResources().getDrawable(resId));
    }
    public void setBackRoot(){
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void showLeft(){
        toolbar_title_left.setVisibility(VISIBLE);
        finishTextView.setTextColor(getResources().getColor(R.color.shenguiappcolor));
    }
    public void setBottomLineVisible(int Visible) {

        bottomLine.setVisibility(Visible);

    }

    public void dismissLine() {
        bottomLine.setVisibility(View.GONE);
    }

    public ImageView getRightImageView() {
        return imageView;
    }


    public boolean isCollect() {

        return null != imageView.getTag() && imageView.getTag().equals("true");
    }

    public void showFinish(){
        finishTextView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        messageRedImage.setVisibility(View.GONE);
    }
    public void setRedImage(int count) {
        messageRedImage.setVisibility(count>0?VISIBLE:GONE);
//        messageRedImage.setText(String.valueOf(count));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_title_left:
                if(clickLeftListener!=null){
                    clickLeftListener.OnClickLeft(v);
                }
                break;
            default:
                if (clickRightListener != null) {
                    clickRightListener.OnClickRight(v);
                }
        }
    }
}
