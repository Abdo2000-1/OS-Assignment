package hospital;

import java.util.concurrent.TimeUnit;

// Custom semaphore required by the assignment.
public class Semaphore {
    private int permits;

    public Semaphore(int initialPermits) {
        this.permits = initialPermits;
    }

    public synchronized void acquire() throws InterruptedException {
        // TODO: wait until a permit is available, then acquire it.
        throw new UnsupportedOperationException("TODO");
    }

    public synchronized boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO: acquire within the given timeout; return false if time runs out.
        throw new UnsupportedOperationException("TODO");
    }

    public synchronized void release() {
        // TODO: return a permit and wake a waiting thread.
        throw new UnsupportedOperationException("TODO");
    }
}