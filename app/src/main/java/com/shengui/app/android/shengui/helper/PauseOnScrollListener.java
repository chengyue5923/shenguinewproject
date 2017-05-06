package com.shengui.app.android.shengui.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

/**
 * 滑动监听
 * Created by HW on 2015/8/28.
 */
public class PauseOnScrollListener implements AbsListView.OnScrollListener {

    private final static int LOAD_TYPE_CONTEXT = 1;
    private final static int LOAD_TYPE_ACTIVITY = 2;
    private final static int LOAD_TYPE_FRAGMENT_ACTIVITY = 3;
    private final static int LOAD_TYPE_FRAGMENT = 4;

    private int LOAD_TYPE = LOAD_TYPE_CONTEXT;

    private WeakReference<Context> mContext;
    private WeakReference<Activity> mActivity;
    private WeakReference<FragmentActivity> mFragmentActivity;
    private WeakReference<Fragment> mFragment;

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final AbsListView.OnScrollListener externalListener;

    public PauseOnScrollListener(Context mContext, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener externalListener) {
        this.mContext = new WeakReference<Context>(mContext);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = externalListener;
        LOAD_TYPE = LOAD_TYPE_CONTEXT;
    }

    public PauseOnScrollListener(Context mContext, boolean pauseOnScroll, boolean pauseOnFling) {
        this.mContext = new WeakReference<Context>(mContext);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = null;
        LOAD_TYPE = LOAD_TYPE_CONTEXT;
    }

    public PauseOnScrollListener(Activity activity, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener externalListener) {
        this.mActivity = new WeakReference<Activity>(activity);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = externalListener;
        LOAD_TYPE = LOAD_TYPE_ACTIVITY;
    }

    public PauseOnScrollListener(Activity activity, boolean pauseOnScroll, boolean pauseOnFling) {
        this.mActivity = new WeakReference<Activity>(activity);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = null;
        LOAD_TYPE = LOAD_TYPE_ACTIVITY;
    }

    public PauseOnScrollListener(FragmentActivity fragmentActivity, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener externalListener) {
        this.mFragmentActivity = new WeakReference<FragmentActivity>(fragmentActivity);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = externalListener;
        LOAD_TYPE = LOAD_TYPE_FRAGMENT_ACTIVITY;
    }

    public PauseOnScrollListener(FragmentActivity fragmentActivity, boolean pauseOnScroll, boolean pauseOnFling) {
        this.mFragmentActivity = new WeakReference<FragmentActivity>(fragmentActivity);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = null;
        LOAD_TYPE = LOAD_TYPE_FRAGMENT_ACTIVITY;
    }

    public PauseOnScrollListener(Fragment fragment, boolean pauseOnScroll, boolean pauseOnFling, AbsListView.OnScrollListener externalListener) {
        this.mFragment = new WeakReference<Fragment>(fragment);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = externalListener;
        LOAD_TYPE = LOAD_TYPE_FRAGMENT;
    }

    public PauseOnScrollListener(Fragment fragment, boolean pauseOnScroll, boolean pauseOnFling) {
        this.mFragment = new WeakReference<Fragment>(fragment);
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = null;
        LOAD_TYPE = LOAD_TYPE_FRAGMENT;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // TODO 恢复下载
                try {
                    resumeOnScroll();
                } catch (Exception e) {
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                if (pauseOnScroll) {
                    // TODO 暂停下载
                    try {
                        pauseOnScroll();
                    } catch (Exception e) {
                    }
                }
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                if (pauseOnFling) {
                    // TODO 暂停下载
                    try {
                        pauseOnScroll();
                    } catch (Exception e) {
                    }
                }
                break;
        }

        if (externalListener != null) {
            externalListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO
        if (externalListener != null) {
            externalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    /****
     * 暂停线程下载
     */
    private void pauseOnScroll(){
        switch (LOAD_TYPE){
            case LOAD_TYPE_CONTEXT:
                if(null!=mContext.get()){
                    Glide.with(mContext.get()).pauseRequests();
                }
                break;
            case LOAD_TYPE_ACTIVITY:
                if(null!=mActivity.get()){
                    Glide.with(mActivity.get()).pauseRequests();
                }
                break;
            case LOAD_TYPE_FRAGMENT_ACTIVITY:
                if(null!=mFragmentActivity.get()){
                    Glide.with(mFragmentActivity.get()).pauseRequests();
                }
                break;
            case LOAD_TYPE_FRAGMENT:
                if(null!=mFragment.get()){
                    Glide.with(mFragment.get()).pauseRequests();
                }
                break;
        }
    }


    /****
     * 恢复线程下载
     */
    private void resumeOnScroll(){
        switch (LOAD_TYPE){
            case LOAD_TYPE_CONTEXT:
                if(null!=mContext.get()){
                    Glide.with(mContext.get()).resumeRequests();
                }
                break;
            case LOAD_TYPE_ACTIVITY:
                if(null!=mActivity.get()){
                    Glide.with(mActivity.get()).resumeRequests();
                }
                break;
            case LOAD_TYPE_FRAGMENT_ACTIVITY:
                if(null!=mFragmentActivity.get()){
                    Glide.with(mFragmentActivity.get()).resumeRequests();
                }
                break;
            case LOAD_TYPE_FRAGMENT:
                if(null!=mFragment.get()){
                    Glide.with(mFragment.get()).resumeRequests();
                }
                break;
        }
    }
}
