package bbejeck.guava.chapter5;

/**
 * User: Bill Bejeck
 * Date: 4/14/13
 * Time: 2:34 PM
 */
public class TraditionalLockSample {

    private static final Object lock = new Object();
    private volatile boolean condition;

    public void synchronizedOperation() {
        synchronized (lock) {
            while (condition) {
                try {
                    System.out.println("Waiting to do work");
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("ok to do work now");
        }

    }

    public void otherSynchronizedOperation() {
        synchronized (lock) {
            System.out.println("Doing some work that resets state");
            condition = false;
            lock.notify();
        }
    }
}
