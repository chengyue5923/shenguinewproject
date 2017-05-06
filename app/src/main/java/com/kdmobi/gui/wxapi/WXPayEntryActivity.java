package com.kdmobi.gui.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shengui.app.android.shengui.configer.constants.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

		api = WXAPIFactory.createWXAPI(this, Constant.WXIDAPP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {
		if (baseResp.errCode == 0) {//支付成功

			Intent intent = new Intent();
			intent.setAction("weixinpay");
//			intent.setAction("weixingave");
//			intent.setAction("weixinsghgave");
//			intent.setAction("weixincasedetailgave");
			sendBroadcast(intent);

			Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			finish();
		}else if (baseResp.errCode == -1) {//支付失败
			Toast.makeText(getApplicationContext(), "支付失败",Toast.LENGTH_SHORT).show();
			finish();
		}else {//取消
			Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();

			Intent intentBack = new Intent();
			intentBack.setAction("deleteweixinpay");
//			intentBack.setAction("deletesghweixinpay");
//			intentBack.setAction("deleteweixincasedetailpay");
//			intentBack.setAction("test");
			sendBroadcast(intentBack);

			finish();
		}
	}

//	@Override
//	public void onReq(BaseReq req) {
//	}
//
//	@Override
//	public void onResp(BaseResp resp) {
//		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
//	}
}