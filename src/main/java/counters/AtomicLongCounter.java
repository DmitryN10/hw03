package counters;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongCounter implements Counter {
    private final AtomicLong count = new AtomicLong(0);

    @Override
    public synchronized long incrementAndGet() {
        return count.incrementAndGet();
    }
}
