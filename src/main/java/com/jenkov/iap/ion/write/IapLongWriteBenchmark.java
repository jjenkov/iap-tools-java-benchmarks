package com.jenkov.iap.ion.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenkov.iap.ion.pojos.Pojo10Long;
import com.jenkov.iap.ion.pojos.Pojo1Long;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IapLongWriteBenchmark {


    @State(Scope.Thread)
    public static class IapState {
        Pojo1Long pojo1Long = new Pojo1Long();
        Pojo10Long pojo10Long = new Pojo10Long();

        IonObjectWriter writerSingle = new IonObjectWriter(Pojo1Long.class);
        IonObjectWriter writerMulti  = new IonObjectWriter(Pojo10Long.class);

        byte[]    dest   = new byte[10 * 1024];
    }

    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Long pojo1Long = new Pojo1Long();
        Pojo10Long pojo10Long = new Pojo10Long();

        ObjectMapper objectMapper = new ObjectMapper();

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        @Setup(Level.Invocation)
        public void doSetup() {
            out.reset();
        }

    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapWriteSingle(IapState state, Blackhole blackhole) {

        state.writerSingle.writeObject(state.pojo1Long, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapWriteMulti(IapState state, Blackhole blackhole) {

        state.writerMulti.writeObject(state.pojo10Long, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonWriteSingle(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapper.writeValue(state.out, state.pojo1Long);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonWriteMulti(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapper.writeValue(state.out, state.pojo10Long);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }






}
