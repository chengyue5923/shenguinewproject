package com.shengui.app.android.shengui.controller;

import com.base.framwork.net.lisener.ViewNetCallBack;
import com.base.platform.utils.java.MapBuilder;
import com.shengui.app.android.shengui.configer.enums.HttpConfig;
import com.shengui.app.android.shengui.models.LoginResultModel;
import com.shengui.app.android.shengui.utils.net.ConnectTool;

/**
 * 登录注册
 * Created by yanbo on 2016/7/22.
 */
public class LoginController {
    private static LoginController instance;


    public static LoginController getInstance() {
        if (instance == null)
            instance = new LoginController();
        return instance;
    }

    public void register(ViewNetCallBack callBack, String mobile, String identityNum, String phoneNum, String verifyCode) {
        try {
            ConnectTool.httpRequest(HttpConfig.register, new MapBuilder<String, Object>()
                    .add("mobile", mobile)
                    .add("pwd", identityNum)
                    .add("re_pwd", phoneNum)
                    .add("code", verifyCode)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }

    }

    public void loginUser(ViewNetCallBack callBack, String username, String password) {
        try {
            ConnectTool.httpRequest(HttpConfig.login, new MapBuilder<String, Object>()
                    .add("mobile", username)
                    .add("pwd", password)
                    .getMap(), callBack, LoginResultModel.class);
        } catch (Exception e) {
        }

    }
//
//    public void setPsw(ViewNetCallBack callBack, int identity, String identityNum, String password, String confirmPwd) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.setPsw, new MapBuilder<String, Object>()
//                    .add("identity", identity)
//                    .add("identityNum", identityNum)
//                    .add("password", password)
//                    .add("confirmPwd", confirmPwd)
//                    .getMap(), callBack, LoginResultModel.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    public void index(ViewNetCallBack callBack) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.index, new MapBuilder<String, Object>()
//
//                    .getMap(), callBack, IndexMapModel.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 私行服务
//     *
//     * @param callBack
//     */
//    public void myBank(ViewNetCallBack callBack) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.myBank, new MapBuilder<String, Object>()
//
//                    .getMap(), callBack, IndexMapModel.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 验证密码
//     *
//     * @param callBack
//     */
//    public void preModify(ViewNetCallBack callBack, String password) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.preModify, new MapBuilder<String, Object>()
//                    .add("password", password)
//                    .add("userId", UserPreference.getUid())
//                    .getMap(), callBack, String.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 重置密码
//     *
//     * @param callBack
//     */
//    public void resetPsw(ViewNetCallBack callBack, String newPassword, String confirmPwd) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.resetPsw, new MapBuilder<String, Object>()
//                    .add("newPwd", newPassword)
//                    .add("confirmPwd", confirmPwd)
//                    .add("userId", UserPreference.getUid())
//                    .getMap(), callBack, String.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 忘记密码
//     *
//     * @param callBack
//     */
//    public void forgetPsw(ViewNetCallBack callBack, String mobile, String newPassword, String confirmPwd) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.ForgetPass, new MapBuilder<String, Object>()
//                    .add("newPwd", newPassword)
//                    .add("mobile", mobile)
//                    .add("confirmPwd", confirmPwd)
//                    .getMap(), callBack, String.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 验证用户名是否存在
//     *
//     * @param callBack
//     */
//    public void loginExist(ViewNetCallBack callBack, String login) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.loginExist, new MapBuilder<String, Object>()
//                    .add("login", login)
//                    .getMap(), callBack, LoginExistModel.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 修改登录名
//     *
//     * @param callBack
//     */
//    public void modLogin(ViewNetCallBack callBack, String login) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.modLogin, new MapBuilder<String, Object>()
//                    .add("login", login)
//                    .getMap(), callBack, Boolean.class);
//        } catch (Exception e) {
//        }
//
//    }
//
//    /**
//     * 获取验证码
//     *
//     * @param callBack
//     */
//
//    public void getSms(ViewNetCallBack callBack, String mobile, String name) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.getSms, new MapBuilder<String, Object>()
//                    .add("mobile", mobile)
//                    .add("login", name)
//                    .getMap(), callBack, Boolean.class);
//        } catch (Exception e) {
//        }
//
//    }
//    public void getSms(ViewNetCallBack callBack, String mobile) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.getSms, new MapBuilder<String, Object>()
//                    .add("mobile", mobile)
//                    .getMap(), callBack, Boolean.class);
//        } catch (Exception e) {
//        }
//
//    }
//    public void getSmsWithoutCheck(ViewNetCallBack callBack, String mobile) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.getSmsWithoutCheck, new MapBuilder<String, Object>()
//                    .add("mobile", mobile)
//                    .getMap(), callBack, Boolean.class);
//        } catch (Exception e) {
//        }
//
//    }
//    /**
//     * 获取验证码
//     *
//     * @param callBack
//     */
//
//    public void bindDevice(ViewNetCallBack callBack, String token) {
//        try {
//            ConnectTool.httpRequest(HttpConfig.bindDevice, new MapBuilder<String, Object>()
//                    .add("token", token)
//                    .add("allowPush", 1)
//                    .add("deviceType", "android")
//                    .getMap(), callBack, String.class);
//        } catch (Exception e) {
//        }
//
//    }

}
