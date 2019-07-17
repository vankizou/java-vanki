package test;

import java.util.concurrent.TimeUnit;

/**
 * @author vanki
 * @date 2019-07-08 08:34
 */
public class ThreadTest implements Runnable {

    public static void main(String[] args) {
        new Thread(new ThreadTest()).start();
        new Thread(new ThreadTest()).start();
    }

    @Override
    public void run() {
        synchronized (this.getClass()) {
            System.out.println("start.....");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("end......");
        }
    }
}
