package test;

import counters.AtomicLongCounter;
import counters.SemaphoreCounter;
import counters.SynchronizedCounter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static java.lang.Thread.sleep;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openjdk.jmh.annotations.Mode.Throughput;

public class CountersTest{
    private static final int THREAD_COUNT = 8;
    private static final long THREAD_TIME_MS = 2;
    private static final int WARMUP_ITER = 10;
    private static final int MEASUREMENT_ITER = 100;

    @State(Scope.Benchmark)
    public static class CounterPerfState {
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        AtomicLongCounter atomicLongCounter = new AtomicLongCounter();
        SemaphoreCounter semaphoreCounter = new SemaphoreCounter();
    }
    @Benchmark
    @BenchmarkMode(Throughput)
    @OutputTimeUnit(SECONDS)
    @Group("AtomicLongCounter")
    public void testAtomicLongCounter(CounterPerfState state) {
        state.atomicLongCounter.incrementAndGet();
        threadSleep();
    }

    @Benchmark
    @BenchmarkMode(Throughput)
    @OutputTimeUnit(SECONDS)
    @Group("SemaphoreCounter")
    public void testSemaphoreCounter(CounterPerfState state) {
        state.semaphoreCounter.incrementAndGet();
        threadSleep();
    }

    @Benchmark
    @BenchmarkMode(Throughput)
    @OutputTimeUnit(SECONDS)
    @Group("SynchronizedCounter")
    public void testSynchronizedCounter(CounterPerfState state) {
        state.synchronizedCounter.incrementAndGet();
        threadSleep();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(CountersTest.class.getName())
                .warmupIterations(WARMUP_ITER)
                .measurementIterations(MEASUREMENT_ITER)
                .forks(1)
                .threads(THREAD_COUNT)
                .resultFormat(ResultFormatType.JSON)
                .result("Threads"+THREAD_COUNT+"_W"+WARMUP_ITER+"_M"+MEASUREMENT_ITER)
                .build();
        new Runner(options).run();
    }

    private void threadSleep() {
        try {
            sleep(CountersTest.THREAD_TIME_MS);
        } catch (InterruptedException e) { }
    }
}
