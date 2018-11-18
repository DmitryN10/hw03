package counters;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gzge on 11/19/16.
 */
public class AtomicLongCounter {
    AtomicLong count = new AtomicLong(0);

    public void increment() {
        count.incrementAndGet();
    }

    public synchronized long incrementAndGet() {
        return count.incrementAndGet();
    }

    public long get() {
        return count.get();
    }
}
