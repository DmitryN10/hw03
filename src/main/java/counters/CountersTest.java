package counters;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class CountersTest {
    private static final int THREAD_COUNT = 4;
    private static final long THREAD_TIME_MS = 2;


    @State(Scope.Benchmark)
    public static class CounterPerfState {
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        AtomicLongCounter atomicLongCounter = new AtomicLongCounter();
        SemaphoreCounter semaphoreCounter = new SemaphoreCounter();
        @Setup(Level.Trial)
        public void setup () {}

        @TearDown(Level.Trial)
        public void teardown () {}
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Group("AtomicLongCounter")
    public void testAtomicLongCounter(CounterPerfState state) {
        state.atomicLongCounter.incrementAndGet();
        try {
            Thread.sleep(CountersTest.THREAD_TIME_MS);
        } catch (InterruptedException e) { }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Group("SemaphoreCounter")

    public void testSemaphoreCounter(CounterPerfState state) {
        state.semaphoreCounter.incrementAndGet();
        try {
            Thread.sleep(CountersTest.THREAD_TIME_MS);
        } catch (InterruptedException e) {}
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    @Group("SynchronizedCounter")
    public void testSynchronizedCounter(CounterPerfState state) {
        state.synchronizedCounter.incrementAndGet();
        try {
            Thread.sleep(CountersTest.THREAD_TIME_MS);
        } catch (InterruptedException e) {}
    }


    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(CountersTest.class.getName())
                .forks(1)
                .threads(THREAD_COUNT)
                .resultFormat(ResultFormatType.JSON)
                .result("classpath*:file"+THREAD_COUNT+".json")
                .build();

        new Runner(options).run();
    }
}
