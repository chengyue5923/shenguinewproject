package com.shengui.app.android.shengui.android.ui.utilsview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 展示图片的容日
 */
public class ImageBigTumbPagerView extends RelativeLayout implements Handler.Callback ,ViewPager.OnPageChangeListener {

    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar


    public static int getImageCount() {
        return IMAGE_COUNT;
    }

    public static void setImageCount(int imageCount) {
        IMAGE_COUNT = imageCount;
    }

    //轮播图图片数量
    private static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    protected final static int TIME_INTERVAL = 3;
    //自动轮播启用开关
    protected  static boolean isAutoPlay = true;
    OnItemClickLisener onItemClickLisener;
    //自定义轮播图的资源
    protected String[] imageUrls;
    //放轮播图片的ImageView 的list
    protected List<ImageView> imageViewsList;
    //放圆点的View的list
    protected List<View> dotViewsList;
    protected ViewPager viewPager;
    //当前轮播页
    public int currentItem = 0;
    //定时任务/
    protected ScheduledExecutorService scheduledExecutorService;
    protected Context context;
    //Handler
    protected Handler handler;
    protected int layoutId ;

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId() {
        this.layoutId = R.layout.view_image_shower;
    }

    public ImageBigTumbPagerView(Context context) {
        this(context, null);
    }

    public ImageBigTumbPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageBigTumbPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        handler = new Handler(this);
    }

    surrentItemListener listener;
    public void setOnCurrentLister( surrentItemListener li){
        this.listener=li;
    }
    public  interface surrentItemListener{
        void OnFinishListener(int itemcount);
        void FirstAndFinish();
    }
    public void initData(List<String> paths,boolean isaudioplay) {
        imageUrls = new String[paths.size()];
        paths.toArray(imageUrls);
        initData();
        setLayoutId();
        initUI(context);
        isAutoPlay=isaudioplay;
        if (isaudioplay) {
            startPlay();
        }
        isNotFirstTime=isaudioplay;
    }

    public void initData(List<String> paths) {
        imageUrls = new String[paths.size()];
        paths.toArray(imageUrls);
        initData();
        setLayoutId();
        initUI(context);
        isAutoPlay=true;
        if (isAutoPlay) {
            startPlay();
        }
        isNotFirstTime=true;
    }

    public void initData(String[] paths) {
        this.imageUrls = paths;

        initData();
        setLayoutId();
        initUI(context);
        isAutoPlay=true;
        if (isAutoPlay) {
            startPlay();
        }
        isNotFirstTime=true;
    }

    /**
     * 开始轮播图切换
     */
    public void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, TIME_INTERVAL, TimeUnit.SECONDS);
    }
    public void stopService(){
        if (scheduledExecutorService==null)
            return;
        scheduledExecutorService.shutdown();
        handler.removeCallbacksAndMessages(null); // 参数为null时会清除所有消息。
    }

    /**
     * 停止轮播图切换
     */
    public void stopPlay() {
        if(scheduledExecutorService==null){
            return;
        }
        scheduledExecutorService.shutdownNow();
    }

    /**
     * 初始化相关Data
     */
    private void initData() {
        imageViewsList = new ArrayList<>();
        dotViewsList = new ArrayList<>();

        // 一步任务获取图片
    }


    /**
     * 初始化Views等UI
     */
    protected void initUI(Context context) {
        if (imageUrls == null || imageUrls.length == 0)
            return;
        LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(context);

            view.setOnClickListener(new OnclickLis(i));
            view.setTag(R.id.tag_position, imageUrls[i]);
            if (i == 0)//给一个默认图
//                view.setBackgroundResource(R.drawable.black);
                view.setBackgroundResource(R.color.gray);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(this);

    }

    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;
    }

    /**
     * 销毁ImageView资源，回收内存
     */
    public void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }


    private int downTime=0;//按下时间
    //按下的XY坐标
    private int downX=0;
    private int downY=0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:
                downX= (int) ev.getX();
                downY= (int) ev.getY();
                downTime= (int) System.currentTimeMillis();
                Logger.e("we----ACTION_DOWN-----");
//                //停止轮播
//                isRunning=false;
//                handler.removeMessages(NEXT);
                stopPlay();
                break;

            case MotionEvent.ACTION_UP:
                int upX= (int) ev.getX();
                int upY= (int) ev.getY();
                int disX=Math.abs(upX - downX);
                int disY=Math.abs(upY - downY);
                int upTime=(int) System.currentTimeMillis();
//                if(upTime-downTime<500 && disX-disY<5 ){
//                    if(onItemClickListener!=null){
//                        onItemClickListener.onItemClick(getCurrentItem()%imageUrls.length);//当前位置就是显示的条目
//                    }
//                }
                Logger.e("we----ACTION_UP-----");
                //开启轮播
//                startRoll();
                startPlay();
                break;

            case MotionEvent.ACTION_CANCEL:
//                startRoll();
                startPlay();
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean handleMessage(Message msg) {
        viewPager.setCurrentItem(currentItem);
        return false;
    }

    public interface OnItemClickLisener {
        void onItemClick(View view, int position);
    }

    protected class OnclickLis implements OnClickListener {
        int index;

        public OnclickLis(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickLisener != null) {
                onItemClickLisener.onItemClick(v, index);
            }
        }
    }

    /**
     * 填充ViewPager的页面适配器
     */


    private boolean ischange=false;
    public void setAdjudtCanver(boolean fl){
        ischange=fl;
    }
    protected class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewsList.get(position);
            if(ischange){
                imageView.setAdjustViewBounds(true);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            ImageLoaderManager.getInstance().disPlayImage(imageView.getTag() + "", null, imageView);
            Glide.with(context)
                    .load(imageView.getTag(R.id.tag_position))
                    .error(R.drawable.default_pictures)
                    .into(imageView);
//            Glide.with(context).load(imageView.getTag(R.id.tag_position)).centerCrop().into(imageView);
//            imageView.setImageResource(R.drawable.test_pager_big);
            container.addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(ViewGroup arg0) {

        }

        @Override
        public void finishUpdate(ViewGroup arg0) {

        }

    }

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */



        boolean isNotFirstTime=true;

        boolean autoPlay = false;

        @Override
        public synchronized void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    autoPlay = false;
                    break;
                case 2:// 界面切换中
                    autoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !autoPlay) {
                        if(isNotFirstTime){
                            viewPager.setCurrentItem(0);
                        }else{
                            setOnFinish();
                        }
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !autoPlay&&isNotFirstTime) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

    public void setOnFinish(){
        if(listener!=null){
            if(currentItem==imageUrls.length-1){
                Logger.e("rtrtr-------------------");
                stopPlay();
                stopService();
                handler.removeCallbacksAndMessages(null); // 参数为null时会清除所有消息。
                listener.FirstAndFinish();
            }
        }
    }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public synchronized void onPageSelected(int pos) {

            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == pos) {
                    (dotViewsList.get(pos)).setBackgroundResource(R.drawable.detailselect);
                } else {
                    (dotViewsList.get(i)).setBackgroundResource(R.drawable.detailunselect);
                }
            }
                if(listener!=null){
                    if(currentItem==imageUrls.length-1){
                        Logger.e("rtrtr-------------------");
                        stopPlay();
                        stopService();
                        handler.removeCallbacksAndMessages(null); // 参数为null时会清除所有消息。
                        listener.OnFinishListener(currentItem);
                    }
                }
        }


    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }


}



