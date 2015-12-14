package com.jenkov.iap.ion.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 06-11-2015.
 */
public class ProtocolBuffersReadBenchmarks {

        @State(Scope.Thread)
    public static class MyState {

        TestPojoProtos.TestPojo testPojo = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        byte[] dest = null;// new byte[10 * 1024];

        ByteArrayInputStream in = null;

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
            try {
                testPojo.writeTo(out);

                dest = out.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //@Benchmark
    //@BenchmarkMode(Mode.Throughput)
    public Object testParse(MyState state, Blackhole blackhole) {

        try {
            state.testPojo = TestPojoProtos.TestPojo.parseFrom(state.dest);
            blackhole.consume(state.testPojo);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return state.testPojo;

    }

}
