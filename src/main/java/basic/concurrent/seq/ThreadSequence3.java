package basic.concurrent.seq;

/**
 * Created by vanki on 2019-03-26 10:10.
 */
public class ThreadSequence3 {

    private static final Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Thread1());
        Thread t2 = new Thread(new Thread2());
        Thread t3 = new Thread(new Thread3());
        t1.start();
        t2.start();
        t3.start();

//        Thread.sleep(100);
//        obj.notify();

//        Thread.sleep(5000);
    }

    static class Thread1 implements Runnable {

        @Override
        public void run() {
            try {
//                synchronized (obj) {
//                obj.wait();
                System.out.println("线程1 -- start");
                Thread.sleep(1000);
                System.out.println("线程1 -- end");

                synchronized (obj) {
                    obj.notify();
                }
//                }
            } catch (Exception e) {
            }
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();

                    System.out.println("线程2 -- start");
                    Thread.sleep(1000);
                    System.out.println("线程2 -- end");

                    obj.notify();
                }
            } catch (Exception e) {
            } finally {
//                notify();
            }
        }
    }

    static class Thread3 implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();

                    System.out.println("线程3 -- start");
                    Thread.sleep(1000);
                    System.out.println("线程3 -- end");

                    obj.notify();
                }
            } catch (Exception e) {
            }
        }
    }
}
