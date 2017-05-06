package com.shengui.app.android.shengui.android.ui.activity.activity.topic;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityTopAdjustListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.TopicController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.TopicListModel;
import com.shengui.app.android.shengui.models.TopicModel;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2016/12/29.
 */

public class SgCreateTopicDetailActivity extends BaseActivity implements View.OnClickListener, ListView.OnItemClickListener {
    @Bind(R.id.SendTextButton)
    TextView SendTextButton;
    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.searchlayout)
    RelativeLayout searchlayout;
    @Bind(R.id.Memberlistview)
    NoScrollListView Memberlistview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    ActivityTopAdjustListAdapter adjustListAdapter;
    @Bind(R.id.textExitview)
    TextView textExitview;
    @Bind(R.id.changtypeLayout)
    LinearLayout changtypeLayout;
    List<TopicModel> list;
    private int resultCode = 1010;
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        changtypeLayout.setVisibility(View.GONE);
        adjustListAdapter = new ActivityTopAdjustListAdapter(this);
        Memberlistview.setAdapter(adjustListAdapter);


//        TopicController.getInstance().GetlistTopic(SgCreateTopicDetailActivity.this,0,10);
          list = new ArrayList<>();
    }
    @Override
    protected void initData() {
        if(getIntent()!=null){
            if(!StringTools.isNullOrEmpty((String)getIntent().getSerializableExtra("crea"))){
                TopicController.getInstance().recommend_topic(this,"");
            }else{
                TopicController.getInstance().recommend_topic(this,UserPreference.getTOKEN());
            }
        }else{
            TopicController.getInstance().recommend_topic(this,UserPreference.getTOKEN());
        }
    }
    @Override
    protected void initEvent() {
        editText.addTextChangedListener(new EditChangedListener());
        SendTextButton.setOnClickListener(this);
        textExitview.setOnClickListener(this);
        Memberlistview.setOnItemClickListener(this);

        Memberlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                IntentTools.startTopicDetail(SgCreateTopicDetailActivity.this, (String)adjustListAdapter.getItem(position).getId(),(String)adjustListAdapter.getItem(position).getTitle());
                Log.e("logger",adjustListAdapter.getItem(position).toString());
                Intent mIntent=new Intent();
                mIntent.putExtra("result",adjustListAdapter.getItem(position).getId());
                mIntent.putExtra("topic",adjustListAdapter.getItem(position).getTitle());
                setResult(resultCode, mIntent);
                finish();
            }
        });
        editText.setOnEditorActionListener(editorActionListener);
    }
    public EditText.OnEditorActionListener editorActionListener = new EditText.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE)
            {
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(textExitview.equals("")){
                        ToastTool.show("请输入话题，话题不能为空");
                    }else{
                        TopicController.getInstance().addTopic(SgCreateTopicDetailActivity.this,textExitview.getText().toString());
                    }
                }else{
                    ToastTool.show("您还没有登录");
                }
            }
            return true;
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SendTextButton:
                finish();
                break;
            case R.id.textExitview:
                if(UserPreference.getTOKEN()!=null&&UserPreference.getTOKEN().length()>1){
                    if(textExitview.equals("")){
                        ToastTool.show("请输入话题，话题不能为空");
                    }else{
                        TopicController.getInstance().addTopic(SgCreateTopicDetailActivity.this,textExitview.getText().toString());
                    }
                }else{
                    ToastTool.show("您还没有登录");
                }

                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        finish();
    }

    class EditChangedListener implements TextWatcher {
        String TAG="editTextView";
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 15;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "输入文本之前的状态");
            temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");
//            mTvAvailableCharNum.setText("还能输入" + (charMaxNum - s.length()) + "字符");

        }

        @Override
        public void afterTextChanged(Editable s) {
//                Log.i(TAG, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            editStart = editText.getSelectionStart();
            editEnd = editText.getSelectionEnd();
            if(s.equals("")){
                changtypeLayout.setVisibility(View.GONE);
            }else{
                changtypeLayout.setVisibility(View.VISIBLE);
                textExitview.setText("#"+s+"#");
            }
            Logger.e("edfddf"+s.length()+charMaxNum);
            TopicController.getInstance().GetlistTopic(SgCreateTopicDetailActivity.this,0,10,s.toString());
            if (s.length() > charMaxNum) {
                ToastTool.show("你输入的字数已经超过了限制");
//                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
//                mEditTextMsg.setText(s);
//                mEditTextMsg.setSelection(tempSelection);
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_topic_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if(flag== HttpConfig.addTopicList.getType()){
          try{
              JSONObject jsonObject=new JSONObject(o.toString());
              if(jsonObject.getBoolean("status")){
                  ToastTool.show("创建成功");
//                  Intent mIntent=new Intent();
//                  mIntent.putExtra("result",jsonObject.getString("data"));
//                  mIntent.putExtra("topic",textExitview.getText().toString());
//                  setResult(resultCode, mIntent);
//                  finish();

                  IntentTools.startTopicDetail(this, (String)jsonObject.getString("data"),textExitview.getText().toString());
              }
              else{
                  ToastTool.show(jsonObject.getString("message"));
              }

          }catch (Exception e){
              Logger.e("eeeeeeee"+e.getMessage());
          }
        }
        if(flag== HttpConfig.recommend_topic.getType()){
            try{
                JSONObject jsonObject=new JSONObject(o.toString());
                if(jsonObject.getBoolean("status")){
                    TopicListModel model=(TopicListModel)result;
                    Log.e("topic",model.getCount()+"");
                    adjustListAdapter.removeAll();
                    adjustListAdapter.setRes(model.getResult());
                }
                else{
                }

            }catch (Exception e){
                Logger.e("eeeeeeee"+e.getMessage());
            }
        }
    }

}
