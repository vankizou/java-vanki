package basic.concurrent;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * wait + notify 实现生产者消费者
 *
 * Created by vanki on 2019-06-18 10:29.
 */
public class WaitNotifyDemo {
    static final LinkedList<Integer> queue = new LinkedList<>();
    static int count = 0;

    public static void main(String[] args) {

        new Thread(new Consumer()).start();

        new Thread(new Producer()).start();

    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (queue) {
                    while (true) {
                        if (queue.isEmpty()) {
                            queue.wait();
                        }
                        System.out.println("接收到数据：" + queue.poll());
                        TimeUnit.SECONDS.sleep(1);

                        queue.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }

    }

    private static class Producer implements Runnable {

        @Override
        public void run() {
            try {

                synchronized (queue) {
                    while (true) {
                        if (queue.size() > 10) {
                            queue.wait();
                        }
                        queue.add(count++);
                        System.out.println("生产数据：" + count);
                        queue.notify();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
