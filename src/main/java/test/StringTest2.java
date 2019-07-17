package test;

/**
 * @author vanki
 * @date 2019-07-07 10:43
 */
public class StringTest2 {
    public static void main(String[] args) throws InterruptedException {
//        String s = new String("str");
//        String s2 = s.intern();
//        String s3 = "str";
//
//        System.out.println(s == s2);
//        System.out.println(s == s3);
//        System.out.println(s2 == s3);

//        String s = "str";
//        String s2 = s.intern();
//        String s3 = new String("str");
//        String s4 = s3.intern();
//
//        System.out.println(s == s2);
//        System.out.println(s == s3);
//        System.out.println(s == s4);
//        System.out.println(s2 == s3);
//        System.out.println(s2 == s4);
//        System.out.println(s3 == s4);

        Object lock = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("A start then wait...");
                        lock.wait();
                        System.out.println("A end wait...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(10);

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        System.out.println("B start then wait...");
                        lock.notify();
                        System.out.println("B end wait...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
