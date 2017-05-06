package com.base.platform.utils.android;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 */
public class JsonTool {

    /**
     * 从json 格式中解析 int值
     *
     * @param json json字符串
     * @param key  需要的key
     * @return 返回int值
     */
    public static int getInt(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);

            return jo.getInt(key);
        } catch (Exception e) {
            return 0 ;
        }
        }
    public static String getString(JSONObject json, String key) {
        Object value = getValue(json, key, "");
        return value instanceof String ? (String) value : "";
    }
    public static Object getValue(JSONObject json, String key, Object defaultValue) {
        Object value = null;
        try {
            value = json.get(key);
        } catch (Exception e) {
        }
        return value != null ? value : defaultValue;
    }
    public static String getString(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);
            return jo.getString(key);
        } catch (Exception e) {
//            Logger.e(e.getMessage(), e);
            return "";
        }

    }

    public static JSONArray getJsonArray(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);
            return jo.getJSONArray(key);
        } catch (Exception e) {
//            Logger.e(e.getMessage(), e);
            return null;
        }

    }

    public static boolean getBoolean(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);

            return jo.getBoolean(key);
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean hasKey(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);

            return jo.has(key);
        } catch (Exception e) {
            return false;
        }
    }

    public static JSONObject getJson(String json, String key) {
        try {
            JSONObject jo = new JSONObject(json);

            return jo.getJSONObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是不是json 数组
     *
     * @param json
     * @return
     */
    public static boolean isArray(String json) {
        Logger.e("---json-" + json);
        if (json.startsWith("[")) {
            Logger.e("---json-是数组" );
            return true;
        } else if (json.startsWith("\t[")) {
            Logger.e("---json-是数组" );
            return true;
        }
        Logger.e("---json-不是数组" );
        return false;
    }

}
