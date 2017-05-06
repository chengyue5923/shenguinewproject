package com.shengui.app.android.shengui.android.ui.utilsview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by g on 2016/10/20.
 */

public class GuiMiBannerView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private boolean isLoop=false;
    private ViewPager viewPager;
    private BannerAdapter adapter;
    private Handler handler;
    private LinearLayout points;
    private List<ImageView> tips;
    private ArrayList<String> dataList; //数据源
    private int pointsFocused;
    private int pointsUnFocused;
    private int defaultPic;
    private BannerOnClickListener bannerOnClickListener;
    private boolean isPositive = true; //是否正向循环
    private Runnable runnable;


    public GuiMiBannerView(Context context) {
        super(context);
        init(context);
    }

    public GuiMiBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GuiMiBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager = new ViewPager(context);
        viewPager.setLayoutParams(params);
        viewPager.addOnPageChangeListener(this);

        addView(viewPager);
        handler = new Handler();

        points = new LinearLayout(context);
        //设置points的LayoutParams
        LinearLayout.LayoutParams pLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        points.setLayoutParams(pLayoutParams);
        points.setOrientation(LinearLayout.HORIZONTAL);
        //这里很重要 设置  layout_gravity  相对于父布局的位置
         LayoutParams layoutParams = new LayoutParams(points.getLayoutParams());
        layoutParams.gravity= (Gravity.BOTTOM|Gravity.RIGHT);  //设置轮播点靠右下角展示
        layoutParams.bottomMargin=dip2px(context,10);
        layoutParams.rightMargin=dip2px(context,10);
        points.setLayoutParams(layoutParams);
        addView(points);

    }

    /**
     * 设置轮播点
     * @param number
     */
    private void setPoint(int number){

        for(int i=0; i<number; i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(dip2px(getContext(),10),dip2px(getContext(),10)));
            tips.add(imageView);
            if(i == 0){
                tips.get(i).setBackgroundResource(pointsFocused);
            }else{
                tips.get(i).setBackgroundResource(pointsUnFocused);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = dip2px(getContext(),2);//轮播点左右间距
            layoutParams.rightMargin = dip2px(getContext(),2);//轮播点左右间距
            points.addView(imageView, layoutParams);
        }



    }


    /**
     * 初始化数据
     * @param list  传入的图片数据，本地图片id，网络图片地址都可以
     * @param defaultPic 默认的图片，在加载失败或者图片尚未加载完全时显示默认图片
     * @param needPoint 是否需要指示点
     * @param pointsUnFocused  指示点的未选中状态图片，不需要指示点设为0即可
     * @param pointsFocused  指示点的选中状态图片，不需要指示点设为0即可
     * @param isLoop 图片是否可以无限循环滑动
     */
    public void init(ArrayList<String> list,int defaultPic ,boolean needPoint,int pointsUnFocused,int pointsFocused,boolean isLoop){
        if(list==null)return;
        if(dataList!=null)return;
        dataList =list;
        this.defaultPic=defaultPic;//默认图片
        this.isLoop=isLoop;//是否轮询

        adapter = new BannerAdapter(list);
        viewPager.setAdapter(adapter);


        //设置指示点
        if(list.size()<=1)needPoint=false;//如果只有一张图片就不显示指示点
        tips = new ArrayList<>();
        if(needPoint){
            this.pointsUnFocused=pointsUnFocused;
            this.pointsFocused=pointsFocused;
            setPoint(list.size());
        }
        setAutoLoop(2000);
    }

    /**
     * 设置自动循环时间，不设置就不会自动循环
     * @param loopTime
     */
    public void setAutoLoop(int loopTime){
        stop();
        autoLoop(loopTime);
    }


//设置banner点击监听
    public void setBannerOnClickListener(BannerOnClickListener bannerOnClickListener){
        this.bannerOnClickListener=bannerOnClickListener;
    }
//设置banner点击监听
    public interface BannerOnClickListener{
        void onClick(int position);
    }

    private void autoLoop(final int time) {

        runnable = new Runnable() {
            @Override
            public void run() {
                int item = viewPager.getCurrentItem();

                if (item >= adapter.getCount() - 1) {  //判断循环到哪里了
                    isPositive = false;
                } else if (item <= 0) {
                    isPositive = true;
                }
                if (isPositive) {
                    item++;
                } else if (!isPositive) {
                    item--;
                }
                viewPager.setCurrentItem(item);
                handler.postDelayed(this, time);
            }
        };
        handler.postDelayed(runnable,time);
    }

    /**
     * 移除掉handler
     */
    public void removeHandler(){
        handler.removeCallbacks(runnable);
        handler=null;
    }
    public void stop(){
        handler.removeCallbacks(runnable);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position=position%dataList.size();
        for(int i=0; i<tips.size(); i++){
            if(i == position){
                tips.get(i).setBackgroundResource(pointsFocused);
            }else{
                tips.get(i).setBackgroundResource(pointsUnFocused);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if(state==0){
            Logger.e("wwwwwwwwwwww"+state);
        }else if(state==1){
            Logger.e("Logger"+state);
            handler.removeCallbacks(runnable);
        }else{
            Logger.e("else"+state);
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable,2000); //手指抬起后2秒后继续循环
        }
    }
    private int downTime=0;//按下时间
    int upTime;

    class BannerAdapter extends PagerAdapter {
        private ArrayList<?> list;
        HashMap<Integer,ImageView> hashMap = new HashMap<>();

        public BannerAdapter(ArrayList<?> url) {
            list=url;
        }
        @Override
        public int getCount() {
            if(!isLoop)return list.size();//不循环

            if(list.size()==1){  //循环
                return 1;
            }else {
                return 500*list.size();
            }
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%dataList.size();
            ImageView imageView=null;
            if (hashMap.containsKey(position)){
                imageView = hashMap.get(position);
                container.removeView(imageView);
            }else{
                imageView = new ImageView(getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Glide.with(getContext()).load(list.get(position)).centerCrop().error(defaultPic).into(imageView);//Glide加载图片
                hashMap.put(position,imageView);
            }

            Logger.e("----------hasmap--------------"+hashMap.size());
//            imageView.setBackground(getResources().getDrawable(defaultPic));
            //此处用Glide加载图片，如果用的其他的，可以再这里更换
             container.addView(imageView);
            final int finalPosition = position;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                   bannerOnClickListener.onClick(finalPosition);//点击事件的回调
                }
            });
            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        handler.removeCallbacks(runnable);  //手指触碰banner条后，停止循环
                        downTime=(int) System.currentTimeMillis();
                        Logger.e("downTime---"+downTime);
                    }
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable,2000); //手指抬起后2秒后继续循环
                        upTime=(int) System.currentTimeMillis();
                        Logger.e("upTime---"+upTime);
                    }
                    if(event.getAction()==MotionEvent.ACTION_MOVE){
                        handler.removeCallbacks(runnable);
                        handler.postDelayed(runnable,2000); //手指抬起后2秒后继续循环
                    }
//                    handler.removeCallbacks(runnable);
//                    handler.postDelayed(runnable,2000); //手指抬起后2秒后继续循环
                    return false;
                }
            });
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    private   int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    private  int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

}
