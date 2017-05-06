package com.base.platform.utils.java;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * list 操作工具类
 */
public class ListUtil {

    public static <T> boolean isNullOrEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNullOrEmpty(T[] ts) {
        if (ts == null || ts.length == 0) {
            return true;
        }
        return false;
    }


    public static int getInt(Map<String, String> map, String key) {

        String value = map.get(key).toString();
        try {
            return Integer.parseInt(value);

        } catch (Exception e) {
            return 0;
        }
    }
    public   static   void  removeDuplicate(List list)   {
        HashSet h  =   new  HashSet(list);
        list.clear();
        list.addAll(h);
        System.out.println(list);
    }


}
