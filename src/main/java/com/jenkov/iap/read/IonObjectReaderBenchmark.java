package com.jenkov.iap.read;

import com.jenkov.iap.TestPojo;
import com.jenkov.iap.write.IonObjectWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class IonObjectReaderBenchmark {


    @State(Scope.Thread)
    public static class MyState {

        TestPojo testPojo = new TestPojo();
        IonObjectWriter writer = new IonObjectWriter(TestPojo.class);
        IonObjectReader reader = new IonObjectReader(TestPojo.class);

        byte[]    dest   = new byte[10 * 1024];

        @Setup(Level.Trial)
        public void toSetup() {
            writer.writeObject(testPojo,2, dest, 0);
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object testIapReader(MyState state, Blackhole blackhole) {

        TestPojo testPojo2 = (TestPojo) state.reader.read(state.dest, 0);

        blackhole.consume(testPojo2);
        return testPojo2;
    }



}
