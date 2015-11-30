package com.jenkov.iap.write;

import com.jenkov.iap.TestPojo;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class IonObjectWriterBenchmark {


    @State(Scope.Thread)
    public static class MyState {

        TestPojo testPojo = new TestPojo();
        IonObjectWriter writer = new IonObjectWriter(TestPojo.class);
        byte[]    dest   = new byte[10 * 1024];

        @Setup(Level.Invocation)
        public void toSetup() {

        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object testIapWriter(MyState state, Blackhole blackhole) {

        state.writer.writeObject(state.testPojo, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }

}
