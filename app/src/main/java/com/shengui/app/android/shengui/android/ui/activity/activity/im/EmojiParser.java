package com.shengui.app.android.shengui.android.ui.activity.activity.im;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmojiParser {
    private EmojiParser(Context context) {
        readMap(context);
    }

    private HashMap<List<Integer>, String> convertMap = new HashMap<List<Integer>, String>();
    private HashMap<String, ArrayList<String>> emoMap = new HashMap<String, ArrayList<String>>();
    private static EmojiParser mParser;

    public static EmojiParser getInstance(Context context) {
        if (mParser == null) {
            mParser = new EmojiParser(context);
        }
        return mParser;
    }

    public HashMap<String, ArrayList<String>> getEmoMap() {
        return emoMap;
    }

    public void readMap(Context context) {
        if (convertMap == null || convertMap.size() == 0) {
            convertMap = new HashMap<List<Integer>, String>();
            XmlPullParser xmlpull = null;
            String fromAttr = null;
            String key = null;
            ArrayList<String> emos = null;
            try {
                XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
                xmlpull = xppf.newPullParser();
                InputStream stream = context.getAssets().open("emoji.xml");
                xmlpull.setInput(stream, "UTF-8");
                int eventCode = xmlpull.getEventType();
                while (eventCode != XmlPullParser.END_DOCUMENT) {
                    switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT: {
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        if (xmlpull.getName().equals("key")) {
                            emos = new ArrayList<String>();
                            key = xmlpull.nextText();
                        }
                        if (xmlpull.getName().equals("e")) {
                            fromAttr = xmlpull.nextText();
                            emos.add(fromAttr);
                            List<Integer> fromCodePoints = new ArrayList<Integer>();
                            if (fromAttr.length() > 6) {
                                String[] froms = fromAttr.split("\\_");
                                for (String part : froms) {
                                    fromCodePoints.add(Integer.parseInt(part, 16));
                                }
                            } else {
                                fromCodePoints.add(Integer.parseInt(fromAttr, 16));
                            }
                            convertMap.put(fromCodePoints, fromAttr);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if (xmlpull.getName().equals("dict")) {
                            emoMap.put(key, emos);
                        }
                        break;
                    }
                    case XmlPullParser.END_DOCUMENT: {
                        break;
                    }
                    }
                    eventCode = xmlpull.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String parseEmoji(String input) {
        if (input == null || input.length() <= 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        int[] codePoints = toCodePointArray(input);
        List<Integer> key = null;
        for (int i = 0; i < codePoints.length; i++) {
            key = new ArrayList<Integer>();
            if (i + 1 < codePoints.length) {
                key.add(codePoints[i]);
                key.add(codePoints[i + 1]);
                if (convertMap.containsKey(key)) {
                    String value = convertMap.get(key);
                    if (value != null) {
                        result.append("[e]" + value + "[/e]");
                    }
                    i++;
                    continue;
                }
            }
            key.clear();
            key.add(codePoints[i]);
            if (convertMap.containsKey(key)) {
                String value = convertMap.get(key);
                if (value != null) {
                    result.append("[e]" + value + "[/e]");
                }
                continue;
            }
            result.append(EmojiDecoder.parseSimpleCodePoint(codePoints[i]));
        }
        return result.toString();
    }

    private int[] toCodePointArray(String str) {
        char[] ach = str.toCharArray();
        int len = ach.length;
        int[] acp = new int[Character.codePointCount(ach, 0, len)];
        int j = 0;
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(ach, i);
            acp[j++] = cp;
        }
        return acp;
    }

    // XXX: bad name
    public SpannableStringBuilder convertToHtml(CharSequence text, Context context) {
        if (text==null){
            return new SpannableStringBuilder();
        }
        SpannableStringBuilder sBuilder = new SpannableStringBuilder(text);
        int[] codePoints = toCodePointArray(text.toString());
        List<Integer> key = null;
        int strLen = 0;

        for (int i = 0; i < codePoints.length; i++) {
            key = new ArrayList<Integer>();
            if (i + 1 < codePoints.length) {
                key.add(codePoints[i]);
                key.add(codePoints[i + 1]);
                if (convertMap.containsKey(key)) {
                    int len = setImageSpanWithTwoCodePoint(context, strLen, convertMap.get(key), sBuilder, codePoints[i], codePoints[i + 1]);
                    strLen += len;
                    i = len == 0 ? i : i + 1;
                    continue;
                }
            }

            key.clear();
            key.add(codePoints[i]);
            if (convertMap.containsKey(key)) {
                strLen += setImageSpanWithOneCodePoint(context, convertMap.get(key), strLen, sBuilder, codePoints[i]);
                continue;
            }
            strLen += Character.charCount(codePoints[i]);
        }
        return sBuilder;
    }

    public String convertUnicode(String emo) {
        String[] emos = emo.split("_");
        if (emos.length < 2) {
            return new String(Character.toChars(Integer.parseInt(emo, 16)));
        }
        char[] char0 = Character.toChars(Integer.parseInt(emos[0], 16));
        char[] char1 = Character.toChars(Integer.parseInt(emos[1], 16));
        char[] emoji = new char[char0.length + char1.length];
        for (int i = 0; i < char0.length; i++) {
            emoji[i] = char0[i];
        }
        for (int i = char0.length; i < emoji.length; i++) {
            emoji[i] = char1[i - char0.length];
        }
        return new String(emoji);
    }

    private int setImageSpanWithOneCodePoint(Context context,
                                             String value,
                                             int strLen,
                                             SpannableStringBuilder sBuilder,
                                             int firstCodePoint) {
        int codeLen = 0;
        if (value != null) {
            int id = context.getResources().getIdentifier("emoji_" + value, "drawable", context.getPackageName());
            if (id != 0) {
                Drawable drawable = context.getResources().getDrawable(id);
                final int pixels = (int)(20. * context.getResources().getDisplayMetrics().density);
                drawable.setBounds(0, 0, pixels, pixels);
                ImageSpan span = new ImageSpan(drawable);
                codeLen = Character.charCount(firstCodePoint);
                sBuilder.setSpan(span, strLen, strLen + codeLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return codeLen;
    }

    private int setImageSpanWithTwoCodePoint(Context context,
                                             int strLen,
                                             String value,
                                             SpannableStringBuilder sBuilder,
                                             int firstCodePoint,
                                             int secondCodePoint) {
        int codeLen = 0;

        if (value != null) {
            int id = context.getResources().getIdentifier("emoji_" + value, "drawable", context.getPackageName());
            if (id != 0) {
                Drawable drawable = context.getResources().getDrawable(id);
                final int pixels = (int)(20. * context.getResources().getDisplayMetrics().density);
                drawable.setBounds(0, 0, pixels, pixels);
                ImageSpan span = new ImageSpan(drawable);
                codeLen = Character.charCount(firstCodePoint) + Character.charCount(firstCodePoint);
                sBuilder.setSpan(span, strLen, strLen + codeLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return codeLen;
    }
}
