package counters;

/**
 * Created by gzge on 11/19/16.
 */
public class SynchronizedCounter {
    private long count = 0;

    public synchronized void increment() {
        count += 1;
    }

    public synchronized long incrementAndGet() {
        count += 1;
        return count;
    }

    public synchronized long get() {
        return count;
    }
}
