package com.shengui.app.android.shengui.android.ui.utilsview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;


import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.fragment.MultiImageSelectorFragment;
import com.shengui.app.android.shengui.utils.im.CommonToolBar;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 多图选择
 */
public class MultiImageSelectorActivity extends BaseActivity implements MultiImageSelectorFragment.Callback, CommonToolBar.ClickRightListener {

    public static final int REQUEST_CODE = 902;
    public static final int REQUEST_CODE_SINGLE = 903;
    public final static String IMAGES = "images";

    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";

    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    protected ArrayList<String> resultList = new ArrayList<String>();

    protected int mDefaultCount;
    @Bind(R.id.chooseImageToolbar)
    CommonToolBar chooseImageToolbar;
    @Bind(R.id.image_grid)
    FrameLayout imageGrid;


    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

    }

    @Override
    protected void initEvent() {
        chooseImageToolbar.setClickRightListener(this);
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_multimage_selector;
    }


    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {

        if (!resultList.contains(path)) {
            resultList.add(path);
        }
//        // 有图片之后，改变按钮状态
//        if (resultList.size() > 0) {
//            mSubmitButton.setText("完成");
//            if (!mSubmitButton.isEnabled()) {
//                mSubmitButton.setEnabled(true);
//            }
//        }
    }

    @Override
    public void onImageUnselected(String path) {
//        mSubmitButton.setEnabled(true);
        if (resultList.contains(path)) {
            resultList.remove(path);

        }

    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    public void back(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void OnClickRight(View view) {
        if (resultList != null && resultList.size() > 0) {
            // 返回已选择的图片数据
            Intent data = new Intent();
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            this.setResult(RESULT_OK, data);
            finish();
        }
    }

}
