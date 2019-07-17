package test;

/**
 * Created by vanki on 2018-12-13 09:39.
 */
public class VolatileTest implements Runnable {
    volatile int v = 0;

    public static void main(String[] args) throws InterruptedException {
        VolatileTest t = new VolatileTest();

        Thread thread = new Thread(t);
        thread.setName("test");
        thread.start();

        t.t2();
        System.out.println("main：" + t.v);
    }

    synchronized void t1() throws InterruptedException {
        v = 1;
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("t1 = " + v);
    }

    synchronized void t2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " t2 --------- start");
        v = 2;
        Thread.sleep(250);
        System.out.println(Thread.currentThread().getName() + " t2 --------- end");
    }

    @Override
    public void run() {
        try {
            t1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
