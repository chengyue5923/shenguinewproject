package com.shengui.app.android.shengui.android.ui.utilsview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


import com.shengui.app.android.shengui.utils.im.CommonUtil;

import java.util.HashMap;
import java.util.UUID;

/**
 * NOTE: should add keep in proguard to make js functions workable.
 *
 * @author cai
 */
public class DJXWebView extends WebView {
    private static final String TAG = "DJXWebView";
    // this object is used to implement some internal functions, not ready for using by web developers
    private static final String kTrickyAlertStringPrefixForEvaluateJS = "djx_kTrickyAlertStringPrefixForEvaluateJS_80DF64A5_757E_4B70_8AAC_2797EF8A77C7";
    private HashMap<String, ValueCallback<String>> mCallbacks = new HashMap<String, ValueCallback<String>>();
    private static int kTrickyAlertStringPrefixLen = 0;

    public DJXWebView(Context context) {
        super(context);
        sharedInit();
    }

    public DJXWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sharedInit();
    }

    public DJXWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        sharedInit();
    }

    protected void sharedInit() {
    }


    public static class DJXWebChromeClient extends WebChromeClient {
        private DJXWebView mDJXWebView = null;

        public DJXWebChromeClient(DJXWebView djxWebView) {
            mDJXWebView = djxWebView;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            boolean handled = false;
            if (mDJXWebView != null
                    && kTrickyAlertStringPrefixLen > 0
                    && !TextUtils.isEmpty(message)
                    && message.startsWith(kTrickyAlertStringPrefixForEvaluateJS)
                    && message.length() >= kTrickyAlertStringPrefixLen) {
                String callID = message.substring(kTrickyAlertStringPrefixForEvaluateJS.length(), kTrickyAlertStringPrefixLen);
                final String typedValueStr = message.substring(kTrickyAlertStringPrefixLen);
                final String typeStr = typedValueStr.substring(0, 1);
                // right now, we don't distinguish undefined and null from js
                final String valueStr = typeStr.equalsIgnoreCase("V") ? typedValueStr.substring(1) : null;
                final ValueCallback<String> callback = mDJXWebView.mCallbacks.remove(callID);
                if (callback != null) {
                    // we don't know how long this callback will take, so don't call it directly from here to avoid block web page.
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onReceiveValue(valueStr);
                        }
                    });
                }

                result.confirm();
                handled = true;
            }
            return handled;
        }
    }

    ;

    /*
     * Google adds evaluateJavaScript function at API 19, so we implement it for all API version.
     * Should enable javascript before you use this function.
     * NOTE: script should be a js expression, rather than a sentence. Such as "1 + 2" rather than "return 1 + 2;"
     * if js result value is undefined or null, callback argument string will be null rather than "undefined" or "null".
     */
    @SuppressLint("NewApi")
    public void djxEvaluateJavascript(String script, final ValueCallback<String> resultCallback) {
        final String wrapValueCode = "(function(x){return (typeof(x) == 'undefined') ? 'U' : (x == null ? 'N' : 'V'+x.toString());})("
                + script
                + ")";
        if (CommonUtil.isBelowVersion(Build.VERSION_CODES.KITKAT)) {
            String callID = UUID.randomUUID().toString();
            if (kTrickyAlertStringPrefixLen == 0) {
                kTrickyAlertStringPrefixLen = kTrickyAlertStringPrefixForEvaluateJS.length() + callID.length();
            }
            mCallbacks.put(callID, resultCallback);
            loadUrl("javascript:alert('"
                    + kTrickyAlertStringPrefixForEvaluateJS
                    + "' + '"
                    + callID
                    + "' + "
                    + wrapValueCode
                    + ")");
        } else {
            evaluateJavascript(wrapValueCode, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    String payload = null;
                    if (value != null && value.length() >= 2) {
                        // [NOTE] this is because evaluateJavascript API will return value with quotes if JS result type is string.
                        final String strValue = value.substring(1, value.length() - 1).replace("\\\"", "\"");
                        final String typeStr = strValue.substring(0, 1);
                        payload = typeStr.equalsIgnoreCase("V") ? strValue.substring(1) : null;
                    }
                    resultCallback.onReceiveValue(payload);
                }
            });
        }
    }
}
