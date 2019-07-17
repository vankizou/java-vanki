package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vanki on 2017/8/11.
 */
public class T2 {
    public static <T> T getDataByMapKey(Map<String, String> confMap, String key, Class<?> c) {
        String val = confMap.get(key);
        System.out.println(c.getName());

        if (c.getClass().getName().contains("String")) {
            return (T) val;
        } else if ("Integer".endsWith(c.getClass().getName())) {
            return (T) val;
        } else if ("Long".endsWith(c.getClass().getName())) {
            return (T) val;
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> mp = new HashMap<>();
        mp.put("aaa", "111");
        mp.put("bbb", "222");
        mp.put("ccc", "333");


        Class<String> a = getDataByMapKey(mp, "aaa", String.class);
        System.out.println(a);

        System.exit(0);
    }

    public static <T> T getDataByMapKey2(Map<String, String> confMap, String key, T c) {
        String value = confMap.get(key);

        if (value == null) return null;

        return (T) value;
    }
}
