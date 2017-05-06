package com.base.platform.utils.java;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 关于hasmap 转换成url
 */
public class MapUtils {
    public static String map2UrlParams(String url, Map<String, Object> map) {

        StringBuilder sb = new StringBuilder();
        sb.append(url);

        if (map != null && map.size() > 0) {
//            sb.append('?');
            if (!url.endsWith("?")){
                sb.append('&');//修改为&,前面已经传过参数
            }

            for (Map.Entry<String, ?> entry : map.entrySet()) {

                try {
                    if (entry.getValue() != null) {
                        sb.append(entry.getKey())
                                .append('=')
                                .append(URLEncoder.encode(entry.getValue()
                                        .toString(), "UTF-8")).append('&');
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String getVlaue(String key, Map<String, String> map) {
        if (map.get(key) == null) {
            return "";
        }
        return map.get(key);
    }
}
