package test;

import java.lang.reflect.Field;

/**
 * @author vanki
 * @date 2019-07-07 10:34
 */
public class StringTest {
    public static void main(String[] args) throws Exception {
        String b = new String("aa");
        String bc = b.intern();
        String a = "aa";

        System.out.println(a == b);
        System.out.println(a == bc);

        Field vf = String.class.getDeclaredField("value");
        vf.setAccessible(true);
        char[] c = (char[]) vf.get(b);
        c[1] = '_';

        System.out.println(a == b);

        System.out.println(a);
        System.out.println(b);

        StringTest st = new StringTest();
        System.out.println("xxx" + StringTest.class.getMethod("a").invoke(st));
    }

    public void a() {
        System.out.println("--------");
    }
}
