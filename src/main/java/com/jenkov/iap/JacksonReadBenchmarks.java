package com.jenkov.iap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class JacksonReadBenchmarks {


    @State(Scope.Thread)
    public static class MyState {

        TestPojo testPojo = new TestPojo();
        ObjectMapper objectMapper = new ObjectMapper();

        byte[] dest   = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        @Setup(Level.Trial)
        public void toSetup() {
            out.reset();
            try {
                objectMapper.writeValue(out, testPojo);
                dest = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //@Benchmark
    //@BenchmarkMode(Mode.Throughput)
    public Object testRead(MyState state, Blackhole blackhole) {

        try {
            TestPojo testPojo2 = state.objectMapper.readValue(state.dest, TestPojo.class);
            blackhole.consume(testPojo2);
            return testPojo2;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
