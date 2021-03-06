package counters;

import java.util.concurrent.Semaphore;

public class SemaphoreCounter implements Counter{
    private Semaphore sem = new Semaphore(1);
    private long count = 0;

    @Override
    public long incrementAndGet() {
        try {
            sem.acquire();
            count += 1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sem.release();
        }
        return count;
    }
}

