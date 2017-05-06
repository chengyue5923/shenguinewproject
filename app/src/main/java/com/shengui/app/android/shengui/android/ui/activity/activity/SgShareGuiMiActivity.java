package com.shengui.app.android.shengui.android.ui.activity.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.framwork.utils.GsonTool;
import com.base.platform.utils.android.Logger;
import com.base.platform.utils.android.ToastTool;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.NoScrollListView;
import com.shengui.app.android.shengui.android.ui.utilsview.ScrollViewExtend;
import com.shengui.app.android.shengui.android.ui.view.ActivityShareGuiMiListAdapter;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.models.AllTypeTorModel;
import com.shengui.app.android.shengui.models.CircleMemberDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/2/28.
 */

public class SgShareGuiMiActivity extends BaseActivity implements View.OnClickListener,ActivityShareGuiMiListAdapter.OnShareListener {
    @Bind(R.id.cancelTextView)
    ImageView cancelTextView;
    @Bind(R.id.InavateText)
    TextView InavateText;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    NoScrollListView listview;
    @Bind(R.id.scrollView)
    ScrollViewExtend scrollView;
    //    @Bind(R.id.swipe_container)
//    SwipeRefreshLayout swipeContainer;
    ActivityShareGuiMiListAdapter numberAdapter;
    @Bind(R.id.textNumber)
    TextView textNumber;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;

    private String target_type;
    private String target_id;
    private String title;
    private String content;

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cancelTextView:
                finish();
                break;
        }

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        if(getIntent().getSerializableExtra("target_type")!=null){
            target_type=(String)getIntent().getSerializableExtra("target_type");
            target_id=(String)getIntent().getSerializableExtra("target_id");
            title=(String)getIntent().getSerializableExtra("title");
            content=(String)getIntent().getSerializableExtra("content");
        }
        numberAdapter = new ActivityShareGuiMiListAdapter(this);
        listview.setAdapter(numberAdapter);
        numberAdapter.setOnlitener(this);
    }

    @Override
    protected void initEvent() {
        cancelTextView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            MineInfoController.getInstance().get_my_attension(this, 0, 50, UserPreference.getTOKEN());
        } else {

            ToastTool.show("您还没有登录");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guimi_list_activity;
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {
        if (flag == HttpConfig.get_my_attension.getType()) {
            Logger.e("logget_my_attension" + result.toString());
            Logger.e("lCirclepproveogger-----ddddddddddddddddddddd-----" + o);
            try {
                JSONObject jsonObject = new JSONObject(o.toString());
                JSONObject json = jsonObject.getJSONObject("data");
                JSONObject data = json.getJSONObject("result");
                String number = json.getString("count");
                textNumber.setText("共有"+number+"位龟蜜好友");
                Iterator<String> keys = data.keys();
                List<AllTypeTorModel> modallist = new ArrayList<>();
                while (keys.hasNext()) {
                    AllTypeTorModel modelsd = new AllTypeTorModel();
                    List<CircleMemberDetail> listdate = new ArrayList<>();
                    JSONArray ja = data.getJSONArray(keys.next().toString());
//                    for(int i=0;i<ja.length();i++){
                    List<CircleMemberDetail> resultf = (List<CircleMemberDetail>) GsonTool.jsonToArrayEntity(ja.toString(), CircleMemberDetail.class);
                    for (CircleMemberDetail ds : resultf) {
                        listdate.add(ds);
                        modelsd.setCharac(ds.getName_first());
                    }
//                    }
                    modelsd.setModelList(listdate);
                    modallist.add(modelsd);
                }
                numberAdapter.setRes(modallist);
            } catch (Exception e) {
                Logger.e("exception" + e.getMessage());
            }

        }
        if(flag==HttpConfig.set_user_notice.getType()){

            try {
                JSONObject js=new JSONObject(o.toString());
                if(js.getBoolean("status")){
                    ToastTool.show("分享成功");
                    finish();
                }else{
                    ToastTool.show("分享失败");
                    finish();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onListener(CircleMemberDetail model) {

        Logger.e("model"+model.getId());
//    public void set_user_notice(ViewNetCallBack callBack, String  user_id, String  target_type, String  target_id, String  title, String  token, String  content) {
        if(target_id.equals("")){
            MineInfoController.getInstance().set_user_notice(this,model.getId(),target_type,model.getId(),title,UserPreference.getTOKEN(),content);
        }else{
            MineInfoController.getInstance().set_user_notice(this,model.getId(),target_type,target_id,title,UserPreference.getTOKEN(),content);

        }

    }
}
