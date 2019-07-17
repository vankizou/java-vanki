package basic.concurrent.seq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用单线程池
 *
 * Created by vanki on 2019-03-26 10:10.
 */
public class ThreadSequence2 {
    private static final ExecutorService pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) {
        pool.submit(new Thread1());
        pool.submit(new Thread2());
        pool.submit(new Thread3());

        pool.shutdown();
    }

    static class Thread1 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("线程1 -- start");
                Thread.sleep(1000);
                System.out.println("线程1 -- end");
            } catch (Exception e) {
            }
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("线程2 -- start");
                Thread.sleep(1000);
                System.out.println("线程2 -- end");
            } catch (Exception e) {
            }
        }
    }

    static class Thread3 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("线程3 -- start");
                Thread.sleep(1000);
                System.out.println("线程3 -- end");
            } catch (Exception e) {
            }
        }
    }
}
