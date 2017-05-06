package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.ui.utilsview.GuideViewPagerAdapter;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.models.ImageModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 欢迎页
 * 
 * @author wwj_748
 * 
 */
public class WelcomeGuideActivity extends Activity implements OnClickListener {

	private ViewPager vp;
	private GuideViewPagerAdapter adapter;
	private List<View> views;
	private Button startBtn;

	// 引导页图片资源
	private static final int[] pics = { R.layout.guid_view1,
			R.layout.guid_view2, R.layout.guid_view3, R.layout.guid_view4 };

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;
	List<ImageModel> modelsd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		MineInfoController.getInstance().get_install_boot_map(new ViewNetCallBack() {
			@Override
			public void onConnectStart() {

			}

			@Override
			public void onConnectEnd() {

			}

			@Override
			public void onFail(Exception e) {
				IntentTools.startMain(WelcomeGuideActivity.this);
				finish();
			}

			@Override
			public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
				try{
					Logger.e("data"+o.toString());
					JSONObject object = new JSONObject(o.toString());
					if (object.getBoolean("status")) {
						JSONArray ja=object.getJSONArray("data");
						modelsd = GsonTool.jsonToArrayEntity(ja.toString(),ImageModel.class);
					}
				}catch (Exception w){
					Logger.e("exece"+w.getMessage());
				}

			}
		});
	}
	public void initdata(){
		views = new ArrayList<View>();

		// 初始化引导页视图列表
		for (int i = 0; i < modelsd.size(); i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.guid_view1, null);
			ImageView imageView=(ImageView)findViewById(R.id.imageView);
			Glide.with(this).load(modelsd.get(i).getImg_1()).asBitmap().placeholder(R.drawable.default_pictures).into(imageView);
			if (i == modelsd.size() - 1) {
				startBtn.setVisibility(View.VISIBLE);
				startBtn = (Button) view.findViewById(R.id.btn_login);
				startBtn.setTag("enter");
				startBtn.setOnClickListener(this);
			}
			views.add(view);
		}

		vp = (ViewPager) findViewById(R.id.vp_guide);
		// 初始化adapter
		adapter = new GuideViewPagerAdapter(views);
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(new PageChangeListener());
		initDots();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 如果切换到后台，就设置下次不进入功能引导页
//		SpUtils.putBoolean(WelcomeGuideActivity.this, AppConstants.FIRST_OPEN, true);
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		dots = new ImageView[ modelsd.size()];

		// 循环取得小点图片
		for (int i = 0; i <  modelsd.size(); i++) {
			// 得到一个LinearLayout下面的每一个子元素
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(false);// 都设为灰色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态

	}

	/**
	 * 设置当前view
	 * 
	 * @param position
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		vp.setCurrentItem(position);
	}

	/**
	 * 设置当前指示点
	 * 
	 * @param position
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > pics.length || currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);
		dots[currentIndex].setEnabled(false);
		currentIndex = position;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag().equals("enter")) {
			enterMainActivity();
			return;
		}
		
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
	
	
	private void enterMainActivity() {

		IntentTools.startMain(WelcomeGuideActivity.this);
		finish();
	}

	private class PageChangeListener implements OnPageChangeListener {
		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int position) {
			// arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

		}

		// 当前页面被滑动时调用
		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			// arg0 :当前页面，及你点击滑动的页面
			// arg1:当前页面偏移的百分比
			// arg2:当前页面偏移的像素位置

		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int position) {
			// 设置底部小点选中状态
			setCurDot(position);
		}

	}
}
