package basic;

import java.util.WeakHashMap;

/**
 * Created by vanki on 2019-03-26 11:34.
 */
public class WeakHashMapDemo {

    public static void main(String[] args) throws InterruptedException {

        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();

        WeakHashMap<Object, Integer> map = new WeakHashMap<>();

        map.put(o1, 1);
        map.put(o2, 2);
        map.put(o3, 3);

        System.out.println(map);
        System.gc();
        System.out.println(map);

        o1 = null;

        System.gc();
        System.out.println(map);
    }
}