package com.shengui.app.android.shengui.android.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.android.Logger;
import com.bumptech.glide.Glide;
import com.kdmobi.gui.R;
import com.shengui.app.android.shengui.android.base.BaseFragment;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHDoctorActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgh.ui.SGHMyActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.sgu.ui.MyActivity;
import com.shengui.app.android.shengui.android.ui.serviceui.util.User;
import com.shengui.app.android.shengui.android.ui.utilsview.CircleImageView;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.controller.MineInfoController;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yanbo on 16-7-11.
 */
public class SgMineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.scanCode)
    ImageView scanCode;
    @Bind(R.id.noticeIv)
    ImageView noticeIv;
    @Bind(R.id.loginTextView)
    TextView loginTextView;
    @Bind(R.id.ReginTextView)
    TextView ReginTextView;
    @Bind(R.id.personInfoIv)
    CircleImageView personInfoIv;
    @Bind(R.id.nametextview)
    TextView nametextview;
    @Bind(R.id.famelView)
    ImageView famelView;
    @Bind(R.id.loginLayout)
    RelativeLayout loginLayout;
    @Bind(R.id.outLoginLayout)
    RelativeLayout outLoginLayout;
    @Bind(R.id.guimitext)
    TextView guimitext;
    @Bind(R.id.guimiText)
    TextView guimiText;
    @Bind(R.id.fanstext)
    TextView fanstext;
    @Bind(R.id.fansText)
    TextView fansText;
    @Bind(R.id.collecttext)
    TextView collecttext;
    @Bind(R.id.collectText)
    TextView collectText;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.stateview)
    View stateview;
    @Bind(R.id.setview)
    View setview;
    @Bind(R.id.adview)
    View adview;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    @Bind(R.id.guimiLayout)
    RelativeLayout guimiLayout;
    @Bind(R.id.fansLayout)
    RelativeLayout fansLayout;
    @Bind(R.id.collectLayout)
    RelativeLayout collectLayout;
    @Bind(R.id.gongLayout)
    RelativeLayout gongLayout;
    @Bind(R.id.manageLayout)
    RelativeLayout manageLayout;
    @Bind(R.id.settingLayout)
    RelativeLayout settingLayout;
    @Bind(R.id.adjustLayout)
    RelativeLayout adjustLayout;
    @Bind(R.id.noticeIm)
    ImageView noticeIm;
    @Bind(R.id.sghview)
    View sghview;
    @Bind(R.id.sghLayout)
    RelativeLayout sghLayout;
    @Bind(R.id.sguview)
    View sguview;
    @Bind(R.id.sguLayout)
    RelativeLayout sguLayout;

    public static SgMineFragment newInstance() {
        SgMineFragment squareFragmentV2 = new SgMineFragment();
        return squareFragmentV2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_select_page, container, false);
        ButterKnife.bind(this, view);
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            outLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
            nametextview.setText(UserPreference.getName());
            Glide.with(getActivity()).load(UserPreference.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
            if (UserPreference.getSex() == 1) {
                famelView.setImageDrawable(getResources().getDrawable(R.drawable.male));
            } else {
                famelView.setImageDrawable(getResources().getDrawable(R.drawable.women));
            }
            initNumber();
        } else {
            outLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }
        initEvent();
        if (UserPreference.getHAVENOTICE() != null && UserPreference.getHAVENOTICE().equals("1")) {
            noticeIm.setVisibility(View.VISIBLE);
        } else {
            noticeIm.setVisibility(View.GONE);
        }
        if (!EventManager.getInstance().isRegister(this)) {
            EventManager.getInstance().register(this);
        }
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IMStringEvent event) {
        Logger.e("currwent----------------------------" + event.getNotice());
        noticeIm.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void initNumber() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {

            MineInfoController.getInstance().get_my_fullinfo(new ViewNetCallBack() {
                @Override
                public void onConnectStart() {

                }

                @Override
                public void onConnectEnd() {

                }

                @Override
                public void onFail(Exception e) {

                }

                @Override
                public void onData(Serializable result, int flag, boolean fromNet, Object o, Map<String, Object> param) {

                    try {
                        JSONObject object = new JSONObject(o.toString());
                        if (object.getBoolean("status")) {

                            JSONObject ja = object.getJSONObject("data");

                            String follower_me_num = ja.getString("follower_me_num");
                            String my_follower_num = ja.getString("my_follower_num");
                            String favorite_num = ja.getString("favorite_num");

                            guimiText.setText(my_follower_num);
                            fansText.setText(follower_me_num);
                            collectText.setText(favorite_num);
                        }
                    } catch (Exception e) {
                    }
                }
            }, UserPreference.getTOKEN());
        } else {
            guimiText.setText("---");
            fansText.setText("---");
            collectText.setText("---");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.e("ererere-----------");
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            initNumber();
            Glide.with(getActivity()).load(UserPreference.getAvatar()).asBitmap().placeholder(R.drawable.default_image).into(personInfoIv);
            outLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
            nametextview.setText(UserPreference.getName());
            if (UserPreference.getSex() == 1) {
                famelView.setImageDrawable(getResources().getDrawable(R.drawable.male));
            } else {
                famelView.setImageDrawable(getResources().getDrawable(R.drawable.women));
            }
        } else {
            outLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            guimiText.setText("---");
            fansText.setText("---");
            collectText.setText("---");
        }
        if (UserPreference.getHAVENOTICE() != null && UserPreference.getHAVENOTICE().equals("1")) {
            noticeIm.setVisibility(View.VISIBLE);
        } else {
            noticeIm.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        collectLayout.setOnClickListener(this);
        scanCode.setOnClickListener(this);
        gongLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
        adjustLayout.setOnClickListener(this);
        guimiLayout.setOnClickListener(this);
        fansLayout.setOnClickListener(this);
        noticeIv.setOnClickListener(this);
        manageLayout.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
        ReginTextView.setOnClickListener(this);
        loginLayout.setOnClickListener(this);
        sghLayout.setOnClickListener(this);
        sguLayout.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventManager.getInstance().unregister(this);
    }

    public boolean isLogin() {
        if (UserPreference.getTOKEN() != null && UserPreference.getTOKEN().length() > 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collectLayout:
                if (isLogin()) {
                    IntentTools.startCollection(getActivity());
                } else {
                    IntentTools.startLogin(getActivity());
                }

                break;
            case R.id.scanCode:
                if (isLogin()) {
                    IntentTools.startScan(getActivity());

                } else {

                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.gongLayout:
                if (isLogin()) {
                    IntentTools.startMainGone(getActivity());

                } else {

                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.settingLayout:
                IntentTools.startSetting(getActivity());
                break;
            case R.id.adjustLayout:
                IntentTools.startAdjust(getActivity());
                break;
            case R.id.guimiLayout:
                if (isLogin()) {
                    IntentTools.startFansOrGuiMi(getActivity(), 0);

                } else {

                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.sghLayout:
                if (isLogin()) {
                    if (User.userType!=3) {
                        startActivity(new Intent(getContext(), SGHMyActivity.class));
                    }else {
                        startActivity(new Intent(getContext(), SGHDoctorActivity.class));
                    }
                } else {
                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.sguLayout:
                if (isLogin()) {
                        startActivity(new Intent(getContext(), MyActivity.class));
                } else {
                    IntentTools.startLogin(getActivity());
                }
                break;

            case R.id.fansLayout:
                if (isLogin()) {
                    IntentTools.startFansOrGuiMi(getActivity(), 1);

                } else {
                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.noticeIv:
                if (isLogin()) {
                    IntentTools.startNotice(getActivity());

                } else {
                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.manageLayout:
                if (isLogin()) {
                    IntentTools.startManageDongtai(getActivity());

                } else {

                    IntentTools.startLogin(getActivity());
                }
                break;
            case R.id.loginTextView:
                IntentTools.startLogin(getActivity());
                break;
            case R.id.ReginTextView:
                IntentTools.startRegister(getActivity());
                break;
            case R.id.loginLayout:
                IntentTools.startInfo(getActivity());
                break;
        }

    }


}
