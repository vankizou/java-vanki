package basic.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * java 模拟死锁
 */
public class DeadLockTest implements Runnable {
    // 死锁时两把锁在互相抢夺资源，但都互不相让
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private boolean flag = true;

    public static void main(String[] args) {
        DeadLockTest test = new DeadLockTest();

        for (int i = 0; i < 2; i++) {
            new Thread(test).start();
        }

        System.out.println("======== Hello World!!!");

    }

    @Override
    public void run() {
        if (flag) {
            flag = false;

            lock1.lock();

            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                ;
            }*/

            System.out.println("flag = true，获取lock1锁，等待lock2释放锁");

            lock2.lock();
            System.out.println("flag = true，获取lock2锁");
            lock2.unlock();

            lock1.unlock();
        } else {
            flag = true;

            lock2.lock();

            System.out.println("flag = false，获取lock2锁，等待lock1释放锁");

            lock1.lock();
            System.out.println("flag = false，获取lock1锁");
            lock1.unlock();

            lock2.unlock();
        }
    }
}
