package com.jenkov.iap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class JacksonWriteBenchmarks {

    @State(Scope.Thread)
    public static class MyState {

        TestPojo testPojo = new TestPojo();
        ObjectMapper objectMapper = new ObjectMapper();

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        @Setup(Level.Invocation)
        public void toSetup() {
            //System.out.println("length: " + out.size());
            out.reset();
        }

    }


    //@Benchmark
    //@BenchmarkMode(Mode.Throughput)
    public Object testJacksonSerialization(MyState state, Blackhole blackhole) {

        try {
            state.objectMapper.writeValue(state.out, state.testPojo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }
}
