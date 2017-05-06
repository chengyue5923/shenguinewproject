package com.shengui.app.android.shengui.android.ui.activity.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.LoginResultModel;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/1/4.
 */

public class MineAdjustBackActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.statesText)
    TextView statesText;
    @Bind(R.id.cancelTextView)
    TextView cancelTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.backContent)
    EditText backContent;
    @Bind(R.id.numberTv)
    TextView numberTv;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelTextView:
                finish();
                break;
            case R.id.statesText:
//                ToastTool.show("发送成功");
//                finish();
                String backContentStr=backContent.getText().toString();

//                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(StringTools.isNullOrEmpty(backContentStr)){
                        ToastTool.show("意见反馈内容不能为空");
                        break;
                    }
                    MineInfoController.getInstance().feedback(this,UserPreference.getTOKEN(),backContentStr);
//                }else{
//                    ToastTool.show("您还没有登录");
//                }
                break;
        }
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
        statesText.setOnClickListener(this);
        backContent.addTextChangedListener(new EditChangedListener());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_adjust_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        try {
            JSONObject object = new JSONObject(o.toString());
            if (object.getBoolean("status")) {
                ToastTool.show("反馈成功");
                finish();
            } else {
                ToastTool.show(object.getString("message"));
            }

        } catch (Exception e) {
            ToastTool.show("反馈失败");
        }

    }
    class EditChangedListener implements TextWatcher {
        String TAG = "editTextView";
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 360;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "输入文本之前的状态");
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");
            numberTv.setText("还能输入" + (charMaxNum - s.length()) + "字");

        }

        @Override
        public void afterTextChanged(Editable s) {
//                Log.i(TAG, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            if (s.length() > charMaxNum) {
                ToastTool.show("你输入的字数已经超过了限制");
//                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//                s.delete(editStart - 1, editEnd);
//                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
            }

        }
    }

}
