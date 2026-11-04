package hospital;
import java.util.concurrent.TimeUnit;

public class Semaphore {
    private int permits;

    public Semaphore(int initialPermits) {
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();
        }
        permits--;
    }

    public synchronized boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        long millis = unit.toMillis(timeout);
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (permits <= 0) {long remaining = millis - elapsedTime;
            if (remaining <= 0) {
                return false; 
            }
            wait(remaining);
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        permits--;
        return true;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }

}