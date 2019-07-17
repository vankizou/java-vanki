package test;

/**
 * @author vanki
 * @date 2019-07-08 11:33
 */
public class ThreadLocalTest implements Runnable {
    private static final ThreadLocal<ThreadLocalData> tl = ThreadLocal.withInitial(() -> new ThreadLocalData());
    private static final ThreadLocal<ThreadLocalData> tl2 = new ThreadLocal<ThreadLocalData>(){
        @Override
        protected ThreadLocalData initialValue() {
            return new ThreadLocalData();
        }
    };

    public static void main(String[] args) {
        ThreadLocalTest t = new ThreadLocalTest();
        new Thread(t).start();
        new Thread(t).start();
    }

    @Override
    public void run() {
        System.out.println(tl.get().getName() + " / " + tl2.get().getName());
    }
}


class ThreadLocalData {
    private String name = "haha";

    public String getName() {
        return name + " - " + Thread.currentThread().getName();
    }
}