package com.shengui.app.android.shengui.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgProductDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgQuanZiContentIntroDuceDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgTextViewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.SgTieZiDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.WebViewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.activity.SgXianXiaavtivityDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.ChatActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.im.IMStringEvent;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgJoinQuanziDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.manage.SgQuanziManageAvtivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineNoticeActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.mine.MineOtherInfoNewActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.sign.SgSignMainActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgTopicDetailActivity;
import com.shengui.app.android.shengui.android.ui.activity.activity.topic.SgTopicListActivity;
import com.shengui.app.android.shengui.controller.EventManager;
import com.shengui.app.android.shengui.db.UserPreference;
import com.shengui.app.android.shengui.utils.android.IntentTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Logger.e(TAG+ "[MyReceiver] 接收Registration Id : " + regId);
			UserPreference.setCBid(regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Logger.e(TAG+ "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			UserPreference.setHAVENOTICE("1");
//        	processCustomMessage(context, bundle);

			EventManager.getInstance().post(new IMStringEvent(1));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			UserPreference.setHAVENOTICE("1");
			Logger.e(TAG+ "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Logger.e(TAG+" -----------------------[MyReceiver] 用户点击打开了通知");
//
			Logger.e(TAG+ "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
			Logger.e("sdfffffff"+getNoName(bundle));
			String s = bundle.getString(JPushInterface.EXTRA_EXTRA);
			try {

				JSONObject obj = new JSONObject(s);
				Logger.e(TAG+" -----------------------obj"+obj.toString());
//				ive(MyReceiver.java:53)JPush -----------------------obj{"redirect_type":1,"redirect_url":3}
				int redirect_type=obj.getInt("redirect_type");
				String redirect_url=obj.getString("redirect_url");
				Logger.e(TAG+" --------------redirect_type--redirect_url-------obj"+redirect_type);
				Logger.e(TAG+" ----------------redirect_url-------obj"+redirect_url);
				switch (redirect_type){
					case  0:
						   Intent i = new Intent(context, WebViewActivity.class);
        					i.putExtras(bundle);
						    i.putExtra("Url",redirect_url);
        					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        					context.startActivity(i);
//						IntentTools.startWebViewActivity(context,redirect_url);
						break;
					case 1:
						Intent iTIE = new Intent(context, SgTieZiDetailActivity.class);
						iTIE.putExtras(bundle);
						iTIE.putExtra("pageid",redirect_url);
						iTIE.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(iTIE);
//						IntentTools.startTieZiDetail(context,redirect_url);
						break;
					case 2:
						Intent iGONGQIU = new Intent(context, SgProductDetailActivity.class);
						iGONGQIU.putExtras(bundle);
						iGONGQIU.putExtra("ids",redirect_url);
						iGONGQIU.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(iGONGQIU);
//						IntentTools.startGongQiuDetail(context,redirect_url);
						break;
					case 3:
						Intent iqUANZI = new Intent(context, SgQuanziManageAvtivity.class);
						iqUANZI.putExtras(bundle);
						iqUANZI.putExtra("CircleId",Integer.parseInt(redirect_url));
						iqUANZI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(iqUANZI);
//						IntentTools.startQuanziDetailById(context,redirect_url);
						break;
					case 4:
						Intent iqUANZIdETA = new Intent(context, SgQuanZiContentIntroDuceDetailActivity.class);
						iqUANZIdETA.putExtras(bundle);
						iqUANZIdETA.putExtra("modelsid",redirect_url);
						iqUANZIdETA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(iqUANZIdETA);

//						IntentTools.startQuanziDetailById(context,redirect_url);
						break;
					case 5:
						Intent itopic = new Intent(context, SgTopicListActivity.class);
						itopic.putExtras(bundle);
						itopic.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(itopic);

//						Intent intent = new Intent(context, SgTopicListActivity.class);
						context.startActivity(intent);
//						IntentTools.startTopicList(context);
						break;
					case 6:
						Intent itopicdETA = new Intent(context, SgTopicDetailActivity.class);
						itopicdETA.putExtras(bundle);
						itopicdETA.putExtra("topic",redirect_url);
						itopicdETA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(itopicdETA);

//						Intent intent = new Intent(context, SgTopicDetailActivity.class);
//						intent.putExtra("topic",topicId);
//						context.startActivity(intent);
//						IntentTools.startTopicDetail(context,redirect_url);
						break;
					case 7:
						Intent ActivityModel = new Intent(context, SgXianXiaavtivityDetailActivity.class);
						ActivityModel.putExtras(bundle);
						ActivityModel.putExtra("ActivityModel",redirect_url);
						ActivityModel.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(ActivityModel);

//						Intent intent = new Intent(context, SgXianXiaavtivityDetailActivity.class);
//						intent.putExtra("ActivityModel",model);
//						context.startActivity(intent);
//						IntentTools.startDetail(context, redirect_url);
						break;
					case 8:
						Intent TextViewA = new Intent(context, SgTextViewActivity.class);
						TextViewA.putExtras(bundle);
						TextViewA.putExtra("text",redirect_url);
						TextViewA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(TextViewA);
//						Intent intent = new Intent(context, SgTextViewActivity.class);
//						intent.putExtra("text",text);
//						context.startActivity(intent);
//						IntentTools.startTextView(context,redirect_url);
						break;
					case 9:
						Intent  MineOther = new Intent(context, MineOtherInfoNewActivity.class);
						MineOther.putExtras(bundle);
						MineOther.putExtra("otherinfo",Integer.parseInt(redirect_url));
						MineOther.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(MineOther);
//						public static void startOtherDetail(Context context,int otherinfo) {
//						Intent intent = new Intent(context, MineOtherInfoNewActivity.class);
//						intent.putExtra("otherinfo",otherinfo);
//						context.startActivity(intent);
//						IntentTools.startOtherDetail(context,Integer.parseInt(redirect_url));
						break;
					case 15:
						Intent   ChatA = new Intent(context, ChatActivity.class);
						ChatA.putExtras(bundle);
						ChatA.putExtra("fromUserId",Integer.parseInt(redirect_url));
						ChatA.putExtra("name",getNoName(bundle));
						ChatA.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity( ChatA);
//						public static void startChat(Context context,String session) {
//						Intent intent = new Intent(context, ChatActivity.class);
//						intent.putExtra("session",session);
//						context.startActivity(intent);
//						IntentTools.startChat(context,redirect_url);
						break;
					case 16:
						Intent   SgSignM = new Intent(context, SgSignMainActivity.class);
						SgSignM.putExtras(bundle);
						SgSignM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity( SgSignM);
//						Intent intent = new Intent(context, SgSignMainActivity.class);
//						context.startActivity(intent);
//						IntentTools.startsign(context);
						break;
					case 17:
						Intent   Main = new Intent(context, MineNoticeActivity.class);
						Main.putExtras(bundle);
						Main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity( Main);
//						IntentTools.startMain(MineNoticeActivity.this,"flag");
						break;
					case 18:
						Intent   MainManage = new Intent(context, SgJoinQuanziDetailActivity.class);
						MainManage.putExtras(bundle);
						MainManage.putExtra("CircleIdApply",Integer.parseInt(redirect_url));
						MainManage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity( MainManage);
//						IntentTools.startQuanziManage(MineNoticeActivity.this,Integer.parseInt(adapter.getItem(position).getRedirect_url()));
						break;
				}
			}catch(JSONException e){

				Logger.e("excepiptn"+e.getMessage());
			}
//        	//打开自定义的Activity
//        	Intent i = new Intent(context, TestActivity.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Logger.e(TAG+ "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Logger.e(TAG+ "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
			Logger.e(TAG+ "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", 121313value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", 233424234value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Logger.e(TAG+"This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: ---uuu[" +
								myKey + " - ---" +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG+"Get mess--age extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:------erteertert--" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	public String getNoName(Bundle bundle){
		StringBuilder sb = new StringBuilder();
		String svalew="";
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", 121313value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", 233424234value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Logger.e(TAG + "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it = json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: ---uuu[" +
								myKey + " - ---" + json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Logger.e(TAG + "Get mess--age extra JSON error!");
				}

			} else if (key.equals(JPushInterface.EXTRA_ALERT)) {
				svalew = bundle.getString(key);
			}
		}
		String [] temp = null;
		temp = svalew.split(" ");
		for(int i=0;i<temp.length;i++){
			Logger.e("sdsdsd"+temp[i]);
		}
		return temp[0].toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
	}
}
