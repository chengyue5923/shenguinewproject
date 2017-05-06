package com.shengui.app.android.shengui.utils.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.base.platform.utils.android.Logger;
import com.base.platform.utils.java.StringTools;
import com.shengui.app.android.shengui.android.ui.activity.activity.BigImageActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.MainTabActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.ScanImageActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgBindMobileActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgContactsListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgCreateQuanZiActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgEditTextImageViewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgGuiQuanDetailActivityNewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgGuiQuanManagerDetailActivityNewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgGuiQuanManagerOfficalActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgHotQuanZiListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgOtherProductDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPlateDivideActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgProductDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushCitySelecterActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushGongQiuDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushQuestionDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushTieziDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushTypeSelecterActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushVideoPushActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgPushVideoRecorderActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgQuanZiContentBackActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgQuanZiContentDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgQuanZiContentIntroDuceDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgQuanziListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgSelectItemActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgShareGuiMiActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgTextViewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgTieZiDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.WebViewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.WelcomeGuideActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.activity.SgXianXiaActivitySingUpActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.activity.SgXianXiaavtivityDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.activity.SgXianxiaActivityListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.activity.SgXianxiaActivityOrderListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.codescan.CaptureActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.ChatActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgForgetPassActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgLoginActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.SgRegisterActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.login.StartLoginActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgChangeQuanZhuActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgJoinQuanziDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgPublicJoinQuanziDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanZiManagerMemberActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanZiManagerMemberNewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanziManageAvtivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineAddressManageActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineAddressManageEditActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineAddressManageListActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineAdjustBackActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineAgreementActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineChangePassWordActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineChangePhoneNumberActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineChangePhoneNumberStepTwoActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineCodeActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineCollectionActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineDongtaiActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineGongQiuActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineGuiMiOrFansActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineInfoActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineNoticeActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineOtherInfoNewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineSettingActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.SgCityByProvinceActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.SgProvinceActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.search.SearchProductActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.search.SgSearchActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.sign.SgSignMainActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgCreateTopicDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgTopicDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgTopicListActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.FullScreenImageActivity;
import com.shengui.app.android.shengui.android.ui.utilsview.MultiImageSelectorActivity;
import com.shengui.app.android.shengui.models.AddressModel;
import com.shengui.app.android.shengui.models.QuanziList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/7/11.
 */
public class IntentTools {

    /**
     *首页
     * @param context
     */
    public static void startMain(Context context) {
        Intent intent = new Intent(context, MainTabActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    /**
     *首页
     * @param context
     */
    public static void startOrderPage(Context context) {
        Intent intent = new Intent(context, WelcomeGuideActivity.class);
        context.startActivity(intent);
    }
    /**
     *首页
     * @param context
     */
    public static void startMain(Context context,String flag) {
        Intent intent = new Intent(context, MainTabActivity.class);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }
    /**
     *StartLoginActivity
     * @param context
     */
    public static void StartLoginActivity(Context context) {
        Intent intent = new Intent(context, StartLoginActivity.class);
        context.startActivity(intent);
    }
    /**
     *StartLoginActivity
     * @param context
     */
    public static void StartImageActivity(Context context,ArrayList<String> images,int index) {
        Intent intent = new Intent(context, ScanImageActivity.class);
        intent.putStringArrayListExtra("images",images);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }
    /**
     *siliao
     * @param context
     */
    public static void startChat(Context context,int userid,String name,String headpath) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("fromUserId",userid);
        intent.putExtra("name",name);
        intent.putExtra("headpath",headpath);
        context.startActivity(intent);
    }
    /**
     *siliao
     * @param context
     */
    public static void startChat(Context context,int session) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("fromUserId",session);
        context.startActivity(intent);
    }
    /**
     * 忘记密码
     * @param context
     */
    public static void startForgetPass(Context context) {
        Intent intent = new Intent(context, SgForgetPassActivity.class);
        context.startActivity(intent);
    }
    /**
     * 大图
     * @param context
     */
    public static void startBigImage(Context context,String url) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    /**

     * 品种选择
     * @param context
     */
    public static void startSelectType(Activity context,int code) {
        Intent intent = new Intent(context, SgPushTypeSelecterActivity.class);
        context.startActivityForResult(intent,code);
    }
    /**
     * 帖子详情
     * @param context
     */
    public static void startTieZiDetail(Activity context,String id) {
        Intent intent = new Intent(context, SgTieZiDetailActivity.class);
        intent.putExtra("pageid",id);
        context.startActivity(intent);
    }
    /**
     * 帖子详情
     * @param context
     */
    public static void startTieZiDetail(Context context,String id) {
        Intent intent = new Intent(context, SgTieZiDetailActivity.class);
        intent.putExtra("pageid",id);
        context.startActivity(intent);
    }
    /**
     *登录
     * @param context
     */
    public static void startLogin(Context context) {
        Intent intent = new Intent(context, SgLoginActivity.class);
        context.startActivity(intent);
    }
    /**
     *注册
     * @param context
     */
    public static void startRegister(Context context) {
        Intent intent = new Intent(context, SgRegisterActivity.class);
        context.startActivity(intent);
    }

    /**
     *动态管理
     * @param context
     */
    public static void startManageDongtai(Context context) {
        Intent intent = new Intent(context, MineDongtaiActivity.class);
        context.startActivity(intent);
    }
    /**
     *消息
     * @param context
     */
    public static void startNotice(Context context) {
        Intent intent = new Intent(context, MineNoticeActivity.class);
        context.startActivity(intent);
    }
    /**
     *他人详情
     * @param context
     */
    public static void startOtherDetail(Context context,int otherinfo) {
        Intent intent = new Intent(context, MineOtherInfoNewActivity.class);
        intent.putExtra("otherinfo",otherinfo);
        context.startActivity(intent);
    }
    /**
     *粉丝 龟蜜
     * @param context
     */
    public static void startFansOrGuiMi(Context context,int flags) {
        Intent intent = new Intent(context, MineGuiMiOrFansActivity.class);
        intent.putExtra("flags",flags);
        context.startActivity(intent);
    }
    /**
     *手机号更改
     * @param context
     */
    public static void startChangePhone(Context context) {
        Intent intent = new Intent(context, MineChangePhoneNumberActivity.class);
        context.startActivity(intent);
    }
    /**
     *用户协议
     * @param context
     */
    public static void startAgreement(Context context) {
        Intent intent = new Intent(context, MineAgreementActivity.class);
        context.startActivity(intent);
    }

    /**
     *意见反馈
     * @param context
     */
    public static void startAdjust(Context context) {
        Intent intent = new Intent(context, MineAdjustBackActivity.class);
        context.startActivity(intent);
    }

    /**
     *修改密码
     * @param context
     */
    public static void startCode(Context context) {
        Intent intent = new Intent(context, MineChangePassWordActivity.class);
        context.startActivity(intent);
    }
    /**
     *个人资料
     * @param context
     */
    public static void startInfo(Context context) {
        Intent intent = new Intent(context, MineInfoActivity.class);
        context.startActivity(intent);
    }
    /**
     *收货地址
     * @param context
     */
    public static void startDefaultAddress(Context context) {
        Intent intent = new Intent(context, MineAddressManageActivity.class);
        context.startActivity(intent);
    }
    /**
     *我的供求
     * @param context
     */
    public static void startMainGone(Context context) {
        Intent intent = new Intent(context, MineGongQiuActivity.class);
        context.startActivity(intent);
    }
    /**
     *我的设置
     * @param context
     */
    public static void startSetting(Context context) {
        Intent intent = new Intent(context, MineSettingActivity.class);
        context.startActivity(intent);
    }
    /**
     *我的地址
     * @param context
     */
    public static void startAddress(Context context) {
        Intent intent = new Intent(context, MineAddressManageListActivity.class);
        context.startActivity(intent);
    }
    /**
     *扫码
     * @param context
     */
    public static void startScan(Context context) {
        Intent intent = new Intent(context, MineCodeActivity.class);
        context.startActivity(intent);
    }
    /**
     *收藏
     * @param context
     */
    public static void startCollection(Context context) {
        Intent intent = new Intent(context, MineCollectionActivity.class);
        context.startActivity(intent);
    }
    /**
     *首页搜索
     * @param context
     */
    public static void startSearchDate(Context context) {
        Intent intent = new Intent(context, SearchProductActivity.class);
        context.startActivity(intent);
    }
    /**
     * 详情大图界面
     *
     * @param context
     */

    public static void startScreenFullActivity(Context context,String url) {
        Intent intent = new Intent(context, FullScreenImageActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
    /**
     *
     *话题广场
     * @param context
     */
    public static void startTopicList(Context context) {
        Intent intent = new Intent(context, SgTopicListActivity.class);
        context.startActivity(intent);
    }
    /**
     *圈子帖子搜索
     * @param context
     */
    public static void startSearchList(Context context) {
        Intent intent = new Intent(context, SgSearchActivity.class);
        context.startActivity(intent);
    }
    /**
     *签到
     * @param context
     */
    public static void startsign(Context context) {
        Intent intent = new Intent(context, SgSignMainActivity.class);
        context.startActivity(intent);
    }
    /**
     *线下活动
     * @param context
     */
    public static void startActivityList(Context context) {
        Intent intent = new Intent(context, SgXianxiaActivityListActivity.class);
        context.startActivity(intent);
    }
    /**
     *线下活动详情
     * @param context
     */
    public static void startActivityDetail(Context context) {
        Intent intent = new Intent(context, SgXianxiaActivityOrderListActivity.class);
        context.startActivity(intent);
    }
    /**
     *活动详情
     * @param context
     */
    public static void startDetail(Context context, String  model) {
        Intent intent = new Intent(context, SgXianXiaavtivityDetailActivity.class);
        intent.putExtra("ActivityModel",model);
        context.startActivity(intent);
    }
    /**
     *活动详情
     * @param context
     */
    public static void startDetail(Context context, String  model,String is) {
        Intent intent = new Intent(context, SgXianXiaavtivityDetailActivity.class);
        intent.putExtra("ActivityModel",model);
        intent.putExtra("is",is);
        context.startActivity(intent);
    }
    /**
     *话题详情
     * @param context
     */
    public static void startTopicDetail(Context context,String topicId) {
        Intent intent = new Intent(context, SgTopicDetailActivity.class);
        intent.putExtra("topic",topicId);
        context.startActivity(intent);
    }
    /**
     *话题详情
     * @param context
     */
    public static void startTopicDetail(Context context,String topicId,String title) {
        Intent intent = new Intent(context, SgTopicDetailActivity.class);
        intent.putExtra("topic",topicId);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    /**
     *创建话题
     * @param context
     */
    public static void startCreateTop(Activity context,int flage) {
        Intent intent = new Intent(context, SgCreateTopicDetailActivity.class);
        intent.putExtra("crea","");
        context.startActivityForResult(intent,flage);
    }
    /**
     *创建话题
     * @param context
     */
    public static void startCreateTop(Activity context,int flage,String crea) {
        Intent intent = new Intent(context, SgCreateTopicDetailActivity.class);
        intent.putExtra("crea",crea);
        context.startActivityForResult(intent,flage);
    }
    /**
     *q圈子成员
     * @param context
     */
    public static void startMember(Context context,String  mo) {
        Intent intent = new Intent(context, SgQuanZiManagerMemberActivity.class);
        intent.putExtra("circleid",mo);
        context.startActivity(intent);
    }
    /**
     *q圈子成员
     * @param context
     */
    public static void startNewMember(Context context,QuanziList mo) {
        Intent intent = new Intent(context, SgQuanZiManagerMemberNewActivity.class);
        intent.putExtra("mode",mo);
        context.startActivity(intent);
    }
    /**
     *供求详情
     * @param context
     */
    public static void startGongQiuDetail(Context context,String id) {
        Intent intent = new Intent(context, SgProductDetailActivity.class);
        intent.putExtra("ids",id);
        context.startActivity(intent);
    }
    /**
     *供求详情
     * @param context
     */
    public static void startOtherGongQiuDetail(Context context,String id) {
        Intent intent = new Intent(context, SgOtherProductDetailActivity.class);
        intent.putExtra("ids",id);
        context.startActivity(intent);
    }
    /**
     *圈子管理
     * @param context
     */
    public static void startJoinManage(Context context,QuanziList circlrId) {
        Intent intent = new Intent(context, SgJoinQuanziDetailActivity.class);
        intent.putExtra("circlrId",circlrId);
        context.startActivity(intent);
    }
    /**
     *圈子管理
     * @param context
     */
    public static void startQuanzhuJoinManage(Context context,QuanziList circlrId) {
        Intent intent = new Intent(context, SgGuiQuanManagerDetailActivityNewActivity.class);
        intent.putExtra("model",circlrId);
        context.startActivity(intent);
    }
    /**
     *F     访问圈子
     * @param context
     */
    public static void startPublicManage(Context context,QuanziList mo) {
        Intent intent = new Intent(context, SgPublicJoinQuanziDetailActivity.class);
        intent.putExtra("models",mo);
        context.startActivity(intent);
    }
    /**
     *圈子转圈主
     * @param context
     */
    public static void startChangeManager(Activity context,QuanziList model,int flag) {
        Intent intent = new Intent(context, SgChangeQuanZhuActivity.class);
        intent.putExtra("QuanziList",model);
        context.startActivityForResult(intent,flag);
    }

    /**
     *
     * @param context
     */
    public static void startSelectItem(Activity context,int code) {
        Intent intent = new Intent(context, SgSelectItemActivity.class);
        context.startActivityForResult(intent,code);
    }
    /**
     *
     * @param context
     */
    public static void startSelectItem(Context context) {
        Intent intent = new Intent(context, SgSelectItemActivity.class);
        context.startActivity(intent);
    }
    /**
     * 产品详情
     * @param context
     */
    public static void startProductDetail(Context context){
        Intent intent = new Intent(context, SgProductDetailActivity.class);
        context.startActivity(intent);
    }
    /**
     * 选择圈子
     * @param context
     */
    public static void startquanzilist(Activity context,int code){
        Intent intent = new Intent(context, SgQuanziListActivity.class);
        context.startActivityForResult(intent,code);
    }
    /**
     * 选择圈子详情
     * @param context
     */
    public static void startquanziDetail(Context context,QuanziList mo){
        Intent intent = new Intent(context, SgQuanZiContentDetailActivity.class);
        intent.putExtra("models",mo);
        context.startActivity(intent);
    }
    /**
     * 选择圈子详情
     * @param context
     */
    public static void startquanziDetail(Context context,QuanziList mo,boolean flag){
        Intent intent = new Intent(context, SgQuanZiContentDetailActivity.class);
        intent.putExtra("models",mo);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }
    /**
     * 选择圈子详情
     * @param context
     */
    public static void startquanziDetail(Context context){
        Intent intent = new Intent(context, SgQuanZiContentDetailActivity.class);
        context.startActivity(intent);
    }
    /**
     * t图片选择发帖子
     * @param context
     */
    public static void startPushTiezi(Context context){
        Intent intent = new Intent(context, SgPushTieziDetailActivity.class);
        context.startActivity(intent);
    }
    /**
     * t图片选择发帖子
     * @param context
     */
    public static void startPushTiezi(Context context,String topic,String topicid,String circle){
        Intent intent = new Intent(context, SgPushTieziDetailActivity.class);
        Logger.e("clecltltltl----------------------"+circle);
        if(StringTools.isNullOrEmpty(circle)){
            intent.putExtra("topic",topic);
            intent.putExtra("topicid",topicid);
        }else{
            intent.putExtra("circletitle",topic);
            intent.putExtra("circleid",topicid);
        }
        context.startActivity(intent);
    }
    /**
     * 圈子列表
     * @param context
     */
    public static void startQuanziList(Context context){
        Intent intent = new Intent(context, SgHotQuanZiListActivity.class);
        context.startActivity(intent);
    }
    /**
     * 文章
     * @param context
     */
    public static void startTextView(Context context,String text){
        Intent intent = new Intent(context, SgTextViewActivity.class);
        intent.putExtra("text",text);
        context.startActivity(intent);
    }
    /**
     * 文章
     * @param context
     */
    public static void startEditTextView(Context context,String text){
        Intent intent = new Intent(context, SgEditTextImageViewActivity.class);
        intent.putExtra("text",text);
        context.startActivity(intent);
    }
    /**
     * 文章
     * @param context
     */
    public static void startTextDecorView(Context context,String text){
        Intent intent = new Intent(context, SgTextViewActivity.class);
        intent.putExtra("textcontent",text);
        context.startActivity(intent);
    }
    /**
     * 创建圈子
     * @param context
     */
    public static void startCreateQuanzi(Context context){
        Intent intent = new Intent(context, SgCreateQuanZiActivity.class);
        context.startActivity(intent);
    }
    /**
     * 圈子详情
     * @param context
     */
    public static void startQuanziDetail(Context context, QuanziList model){
//        if(Integer.parseInt(model.getUser_id())==UserPreference.getID()){
//            Intent intent = new Intent(context, SgQuanziManageAvtivity.class);
//            intent.putExtra("model",model);
//            context.startActivity(intent);
//        }else{
            Intent intent = new Intent(context, SgGuiQuanDetailActivityNewActivity.class);
            intent.putExtra("model",model);
            context.startActivity(intent);
//        }
    }
    /**
     * 圈子详情
     * @param context
     */
    public static void startQuanziDetailSelf(Context context, QuanziList model){
            Intent intent = new Intent(context, SgGuiQuanDetailActivityNewActivity.class);
            intent.putExtra("model",model);
            context.startActivity(intent);
    }
    /**
     * 圈子详情
     * @param context
     */
    public static void startQuanziDetailById(Context context, String  id){
        Intent intent = new Intent(context, SgQuanZiContentIntroDuceDetailActivity.class);
        intent.putExtra("modelsid",id);
        context.startActivity(intent);
    }
    /**
     * 圈子详情退出
     * @param context
     */
    public static void startQuanziBackDetailSelf(Activity context, QuanziList model,int glaf){
        Intent intent = new Intent(context, SgQuanZiContentBackActivity.class);
        intent.putExtra("model",model);
        context.startActivityForResult(intent,glaf);
    }
    /**
     * 圈子管理
     * @param context
     */
    public static void startQuanziManage(Activity context,int  CircleId,int flage){
        Intent intent = new Intent(context, SgQuanziManageAvtivity.class);
        intent.putExtra("CircleId",CircleId);
        context.startActivityForResult(intent,flage);
    }
    /**
     * 圈子管理
     * @param context
     */
    public static void startQuanziManage(Activity context,int  CircleId){
        Intent intent = new Intent(context, SgQuanziManageAvtivity.class);
        intent.putExtra("CircleId",CircleId);
        context.startActivity(intent);
    }
    /**
     * 圈子管理
     * @param context
     */
    public static void startQuanziManageOffical(Context context,QuanziList  CircleId){
        Intent intent = new Intent(context, SgGuiQuanManagerOfficalActivity.class);
        intent.putExtra("model",CircleId);
        context.startActivity(intent);
    }
    /**
     * 圈子管理
     * @param context
     */
    public static void startQuanziManageApply(Context context,int  CircleId){
        Intent intent = new Intent(context, SgJoinQuanziDetailActivity.class);
        intent.putExtra("CircleIdApply",CircleId);
        context.startActivity(intent);
    }
    /**
     * 圈子管理
     * @param context
     */
    public static void startQuanziManage(Context context){
        Intent intent = new Intent(context, SgQuanziManageAvtivity.class);
        context.startActivity(intent);
    }
    /**
     * t录制视频
     * @param context
     */
    public static void startPushVideo(Context context){
        Intent intent = new Intent(context, SgPushVideoRecorderActivity.class);
        context.startActivity(intent);
    }
    /**
     * 视频发布
     * @param context
     */
    public static void startPushVideoGongqiu(Context context,File path){
        Intent intent = new Intent(context, SgPushVideoPushActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }
    /**
     * 通讯录
     * @param context
     */
    public static void startContactList(Context context,String text){
        Intent intent = new Intent(context, SgContactsListActivity.class);
        intent.putExtra("test",text);
        context.startActivity(intent);
    }  /**
     * 龟蜜
     * @param context
     */
    public static void startGuimiList(Context context,String target_type,String target_id,String title,String content){
        Intent intent = new Intent(context, SgShareGuiMiActivity.class);
        intent.putExtra("target_type",target_type);
        intent.putExtra("target_id",target_id);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startCityList(Activity context,int CityCode){
        Intent intent = new Intent(context, SgPushCitySelecterActivity.class);
        context.startActivityForResult(intent,CityCode);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startCityList(Activity context,int CityCode,boolean flag){
        Intent intent = new Intent(context, SgPushCitySelecterActivity.class);
        intent.putExtra("flag",flag);
        context.startActivityForResult(intent,CityCode);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startCityList(Activity context,int CityCode,String  flag){
        Intent intent = new Intent(context, SgPushCitySelecterActivity.class);
        intent.putExtra("ispub",flag);
        context.startActivityForResult(intent,CityCode);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startWebViewActivity(Activity context,String Url,String  name){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("Url",Url);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }

    /**
     * 修改手机号
     * @param context
     */
    public static void startPhoneList(Activity context,String phone,String code,int flag){
        Intent intent = new Intent(context, MineChangePhoneNumberStepTwoActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("code",code);
        context.startActivityForResult(intent,flag);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startChangeAddrelessDetailList(Activity context,AddressModel CityCode){
        Intent intent = new Intent(context, MineAddressManageEditActivity.class);
        intent.putExtra("CityCode",CityCode);
        context.startActivity(intent);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startCityByProvinceList(Activity context,String id,int code){
        Intent intent = new Intent(context, SgCityByProvinceActivity.class);
        intent.putExtra("pathid",id);
        context.startActivityForResult(intent,code);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startProvinceList(Activity context,int code){
//        Intent intent = new Intent(context, SgPushCitySelecterActivity.class);
        Intent intent = new Intent(context,SgProvinceActivity.class);

        context.startActivityForResult(intent,code);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startBindMobile(Activity context,String Openid,String flag){
        Intent intent = new Intent(context, SgBindMobileActivity.class);
        intent.putExtra("Openid",Openid);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }
    /**
     * Chengshi
     * @param context
     */
    public static void startScan(Activity context,int CityCode){
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivityForResult(intent,CityCode);
    }
    /**
     * t图片供求
     * @param context
     */
    public static void startPushGongQiu(Context context){
        Intent intent = new Intent(context, SgPushGongQiuDetailActivity.class);
        context.startActivity(intent);
    } /**
     * t图片提问
     * @param context
     */
    /**
     * 板块选择
     * @param context
     */
    public static void startSelectBankuai(Activity context){
        Intent intent = new Intent(context, SgPlateDivideActivity.class);
        context.startActivity(intent);
    }
    /**
     * 板块选择
     * @param context
     */
    public static void startSelectBankuai(Activity context,int flage){
        Intent intent = new Intent(context, SgPlateDivideActivity.class);
        context.startActivityForResult(intent,flage);
    }
    /**
     * 板块选择
     * @param context
     */
    public static void startSelectBankuai(Activity context,int flage,QuanziList model){
        Intent intent = new Intent(context, SgPlateDivideActivity.class);
        intent.putExtra("QuanziList",model);
        context.startActivityForResult(intent,flage);
    } /**
     * t图片提问
     * @param context
     */
    public static void startPushQuestion(Context context){
        Intent intent = new Intent(context, SgPushQuestionDetailActivity.class);
        context.startActivity(intent);
    }
    /****
     * 打开选择图片的页面
     *
     * @param activity
     * @param mSelectPath
     */
    public static void openImageChooseActivity(Activity activity, ArrayList<String> mSelectPath) {
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,9);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        activity.startActivityForResult(intent, MultiImageSelectorActivity.REQUEST_CODE);
    }
    /****
     * 打开选择图片的页面
     *
     * @param activity
     * @param mSelectPath
     */
    public static void openImageChooseGongqiuActivity(Activity activity, ArrayList<String> mSelectPath) {
        Intent intent = new Intent(activity, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,9);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        activity.startActivityForResult(intent, MultiImageSelectorActivity.REQUEST_CODE);
    }

    public static void startSingUpActivity(Activity context,String activityId) {
        Intent intent = new Intent(context, SgXianXiaActivitySingUpActivity.class);
        intent.putExtra("activityId",activityId);
        context.startActivity(intent);
    }
}
