package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.util.Log;

import com.base.platform.android.application.BaseApplication;
import com.base.platform.utils.android.JsonTool;
import com.kdmobi.gui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class EmojiDecoder {
    private static final String TAG = "emoji";
    
    private static JSONObject emojiUnifiedSoftbank;
    private static JSONObject emojiSoftbankNameCh;
    
    static {
        emojiUnifiedSoftbank = createJsonObject(R.raw.emoji_unified_softbank);
        emojiSoftbankNameCh = createJsonObject(R.raw.emoji_softbank_name_ch);
    }
    
    private static JSONObject createJsonObject(int id) {
        Context ctx = BaseApplication.getInstance().getApplicationContext();
        InputStream is = ctx.getResources().openRawResource(id);
        byte[] data;
        String jsonString = null;
        try {
            data = new byte[is.available()];
            is.read(data);
            jsonString = new String(data);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "init emoji dictionary error");
        }
        
        JSONObject result = null;
        try {
            result = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String decode(final String text) {
        StringBuffer sb = null;
        final int length = text.length();
        for (int offset = 0; offset < length; ) {
           int codepoint = text.codePointAt(offset);        
           if (!isUnifiedEncoded(codepoint) && !isSoftbankEncoded(codepoint)) {
               if (sb != null) {
                   sb.appendCodePoint(codepoint);
               }
           }
           else {
               if (sb == null) {
                   sb = new StringBuffer(length * 4);
                   if (offset > 0) {
                       sb.append(text.substring(0, offset));
                   }
               }
               
               String emojiSoftbank = isUnifiedEncoded(codepoint) ?
                                      JsonTool.getString(emojiUnifiedSoftbank, String.valueOf(codepoint)) :
                                      String.valueOf(codepoint);
               sb.append(getEmojiName(emojiSoftbank));
           }
           offset += Character.charCount(codepoint);
        }
        return sb == null ? text : sb.toString();
   }
    
    public static String parseSimpleCodePoint(int codepoint) {
        StringBuffer sb = new StringBuffer();
        if (!isUnifiedEncoded(codepoint) && !isSoftbankEncoded(codepoint)) {
            if (sb != null) {
                sb.appendCodePoint(codepoint);
            }
        } else {
            String emojiSoftbank = isUnifiedEncoded(codepoint) ?
                    JsonTool.getString(emojiUnifiedSoftbank, String.valueOf(codepoint)) :
                    String.valueOf(codepoint);
            String emojiName = JsonTool.getString(emojiSoftbankNameCh,emojiSoftbank);
            if (emojiName != null) {
                sb.append(String.format("<%s>", emojiName));
            } else {
                sb.append(String.format("<%s>", ".."));
            }
        }
        return sb.toString();
    }
    
    private static String getEmojiName(String emojiSoftbank) {
        String emojiName = JsonTool.getString(emojiSoftbankNameCh, emojiSoftbank);
        return emojiName != null ? String.format("[%s]", emojiName) : "..";
    }

    private static boolean isSoftbankEncoded(int codepoint) {
        return codepoint > 0xE000 && codepoint < 0xE5FF;
    }

    private static boolean isUnifiedEncoded(int codepoint) {
        return (codepoint >= 0x1f300 && codepoint <= 0x1f64f) ||
               (codepoint >= 0x1f680 && codepoint <= 0x1f6ff);
    }
}
