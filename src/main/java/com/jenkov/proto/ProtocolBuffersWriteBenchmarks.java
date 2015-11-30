package com.jenkov.proto;

import com.jenkov.iap.TestPojo;
import com.jenkov.iap.read.IonObjectReader;
import com.jenkov.iap.write.IonObjectWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 06-11-2015.
 */
public class ProtocolBuffersWriteBenchmarks {

    @State(Scope.Thread)
    public static class MyState {

        TestPojoProtos.TestPojo testPojo = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);


        @Setup(Level.Trial)
        public void toSetup() {
            testPojo = TestPojoProtos.TestPojo.newBuilder()
                .setBoolean1(true)
                .setBoolean2(false)
                .setShort1(12)
                .setInt1(13)
                .setLong1(14)
                .setFloat1(14.999F)
                .setDbl1(9999.9999D)
                .setStr1("1234567812345678")
                .build()
            ;
            out.reset();
        }

        @Setup(Level.Invocation)
        public void reset() {
            out.reset();
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object testSerialize(MyState state, Blackhole blackhole) {
        try {
            state.testPojo.writeTo(state.out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

}
