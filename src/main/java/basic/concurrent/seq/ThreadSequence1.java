package basic.concurrent.seq;

/**
 * 使用 join()
 *
 * Created by vanki on 2019-03-26 09:45.
 */
public class ThreadSequence1 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Thread1());
        Thread t2 = new Thread(new Thread2());
        Thread t3 = new Thread(new Thread3());
        t1.start();
        t1.join();  // 阻塞等待，内部其实调用 Object.wait() 方法

        t2.start();
        t2.join();

        t3.start();
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

