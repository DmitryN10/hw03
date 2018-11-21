package counters;

public class SynchronizedCounter implements Counter {
    private long count = 0;

    @Override
    public synchronized long incrementAndGet() {
        count += 1;
        return count;
    }
}
