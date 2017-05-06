package com.shengui.app.android.shengui.android.ui.serviceui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.serviceui.serviceModle.SGUViewPageBean;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.VideoPlayActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.Api;
import com.shengui.app.android.shengui.android.ui.serviceui.util.JsonUitl;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ThreadUtil;
import com.shengui.app.android.shengui.android.ui.serviceui.util.ZoomOutPageTransformer;
import com.shengui.app.android.shengui.android.ui.serviceui.weigh.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SGUFragment extends BaseFragment {

    View view;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.sgu_top_rb1)
    RadioButton sguTopRb1;
    @Bind(R.id.sgu_top_rb2)
    RadioButton sguTopRb2;
    @Bind(R.id.sgu_top_rb3)
    RadioButton sguTopRb3;
    @Bind(R.id.sgu_top_rb4)
    RadioButton sguTopRb4;
    @Bind(R.id.sgu_top_rb5)
    RadioButton sguTopRb5;
    @Bind(R.id.sgu_top_rb6)
    RadioButton sguTopRb6;
    @Bind(R.id.rgSGUH)
    RadioGroup rgSGUH;
    @Bind(R.id.viewPagerContainer)
    RelativeLayout viewPagerContainer;

    List<View> views = new ArrayList<>();

    List<SGUViewPageBean.DataBean> data;

   Handler handler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           List<SGUViewPageBean.DataBean> dataBeen = (List<SGUViewPageBean.DataBean>) msg.obj;
           data = dataBeen;
           if (dataBeen!=null) {
               initViews(dataBeen);
           }
       }
   };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.sguh_fragment_service_sgh, container, false);
        ButterKnife.bind(this, view);

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                List<SGUViewPageBean.DataBean> dataBeen = JsonUitl.sGUspecial(getContext());
                Message message = handler.obtainMessage();
                message.obj = dataBeen;
                handler.sendMessage(message);
            }
        });

        return view;
    }

    private void initViews(List<SGUViewPageBean.DataBean> dataBeen) {
        viewpager.setOffscreenPageLimit(3);
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));

        for (int i = 0; i < dataBeen.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.sguh_viewpage, null, false);
            RoundImageView roundImageView = (RoundImageView) view.findViewById(R.id.iv_title_pic);
            Glide.with(getContext())
                    .load(Api.baseUrl+dataBeen.get(i).getPath())
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(roundImageView);
            TextView tvMemo = (TextView) view.findViewById(R.id.tv2);
            tvMemo.setText(dataBeen.get(i).getMemo());
            views.add(view);
        }

        MyViewPagerAdapter mAdapter = new MyViewPagerAdapter(views);

        viewpager.setAdapter(mAdapter);
        viewpager.setOffscreenPageLimit(mAdapter.getCount());

        topViewPageInit(views);
    }

    View[] viewPageView;

    private void topViewPageInit(List<View> list) {
//        viewPageView = new View[list.size()];

        switch (list.size()) {
            case 1:
                sguTopRb1.setVisibility(View.VISIBLE);
                sguTopRb2.setVisibility(View.GONE);
                sguTopRb3.setVisibility(View.GONE);
                sguTopRb4.setVisibility(View.GONE);
                sguTopRb5.setVisibility(View.GONE);
                sguTopRb6.setVisibility(View.GONE);
                break;
            case 2:
                sguTopRb1.setVisibility(View.VISIBLE);
                sguTopRb2.setVisibility(View.VISIBLE);
                sguTopRb3.setVisibility(View.GONE);
                sguTopRb4.setVisibility(View.GONE);
                sguTopRb5.setVisibility(View.GONE);
                sguTopRb6.setVisibility(View.GONE);
                break;
            case 3:
                sguTopRb1.setVisibility(View.VISIBLE);
                sguTopRb2.setVisibility(View.VISIBLE);
                sguTopRb3.setVisibility(View.VISIBLE);
                sguTopRb4.setVisibility(View.GONE);
                sguTopRb5.setVisibility(View.GONE);
                sguTopRb6.setVisibility(View.GONE);
                break;
            case 4:
                sguTopRb1.setVisibility(View.VISIBLE);
                sguTopRb2.setVisibility(View.VISIBLE);
                sguTopRb3.setVisibility(View.VISIBLE);
                sguTopRb4.setVisibility(View.VISIBLE);
                sguTopRb5.setVisibility(View.GONE);
                sguTopRb6.setVisibility(View.GONE);
                break;
            case 5:
                sguTopRb1.setVisibility(View.VISIBLE);
                sguTopRb2.setVisibility(View.VISIBLE);
                sguTopRb3.setVisibility(View.VISIBLE);
                sguTopRb4.setVisibility(View.VISIBLE);
                sguTopRb5.setVisibility(View.VISIBLE);
                sguTopRb6.setVisibility(View.GONE);
                break;
        }


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int i = position;
                switch (i) {
                    case 0:
                        sguTopRb1.setChecked(true);
                        break;
                    case 1:
                        sguTopRb2.setChecked(true);
                        break;
                    case 2:
                        sguTopRb3.setChecked(true);
                        break;
                    case 3:
                        sguTopRb4.setChecked(true);
                        break;
                    case 4:
                        sguTopRb5.setChecked(true);
                        break;
                    case 5:
                        sguTopRb6.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    /**
     * Viewpager数据适配器
     */
    class MyViewPagerAdapter extends PagerAdapter {

        //view复用
        private List<View> mViewCache;

        public MyViewPagerAdapter(List<View> list) {
            mViewCache = list;
        }

        @Override
        public int getCount() {
            return mViewCache.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mViewCache.get(position);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data!=null){
                        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
                        intent.putExtra("courseId",data.get(position).getObjectId());
                        startActivity(intent);
                    }
                }
            });
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewCache.get(position));
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            //这里做切换ViewPager时，底部RadioButton的操作
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    public void refresh() {

    }
}
