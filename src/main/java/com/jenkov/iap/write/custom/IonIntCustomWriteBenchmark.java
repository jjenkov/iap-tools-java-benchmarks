package com.jenkov.iap.write.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenkov.iap.pojos.Pojo10Long;
import com.jenkov.iap.pojos.Pojo1Long;
import com.jenkov.iap.write.IonObjectWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IonIntCustomWriteBenchmark {


    @State(Scope.Thread)
    public static class IapState {
        Pojo1Long pojo1Long = new Pojo1Long();
        Pojo10Long pojo10Long = new Pojo10Long();

        IonObjectWriter writerSingle = new IonObjectWriter(Pojo1Long.class);
        IonObjectWriter writerMulti  = new IonObjectWriter(Pojo10Long.class);

        IonObjectWriterCustomizable  writerCustomSingle = new IonObjectWriterCustomizable();
        IonObjectWriterCustomizable  writerCustomMulti  = new IonObjectWriterCustomizable();
        IonObjectWriterCustomizable  writerCustomMultiOptimized  = new IonObjectWriterCustomizable();

        byte[]    dest   = new byte[10 * 1024];

        @Setup(Level.Trial)
        public void doSetup() {
            writerCustomSingle.addInt64FieldWriter("field0", (source) -> { return ((Pojo1Long) source).field0; });
            writerCustomSingle.init();

            writerCustomMulti.addInt64FieldWriter("field0"  , (source) -> { return ((Pojo10Long) source).field0; });
            writerCustomMulti.addInt64FieldWriter("field1"  , (source) -> { return ((Pojo10Long) source).field1; });
            writerCustomMulti.addInt64FieldWriter("field2"  , (source) -> { return ((Pojo10Long) source).field2; });
            writerCustomMulti.addInt64FieldWriter("field3"  , (source) -> { return ((Pojo10Long) source).field3; });
            writerCustomMulti.addInt64FieldWriter("field4"  , (source) -> { return ((Pojo10Long) source).field4; });
            writerCustomMulti.addInt64FieldWriter("fieldxx5", (source) -> { return ((Pojo10Long) source).field5; });
            writerCustomMulti.addInt64FieldWriter("fieldxx6", (source) -> { return ((Pojo10Long) source).field6; });
            writerCustomMulti.addInt64FieldWriter("fieldxx7", (source) -> { return ((Pojo10Long) source).field7; });
            writerCustomMulti.addInt64FieldWriter("fieldxx8", (source) -> { return ((Pojo10Long) source).field8; });
            writerCustomMulti.addInt64FieldWriter("fieldxx9", (source) -> { return ((Pojo10Long) source).field9; });
            writerCustomMulti.init();

            writerCustomMultiOptimized.addInt64FieldWriter("0", (source) -> { return ((Pojo10Long) source).field0; });
            writerCustomMultiOptimized.addInt64FieldWriter("1", (source) -> { return ((Pojo10Long) source).field1; });
            writerCustomMultiOptimized.addInt64FieldWriter("2", (source) -> { return ((Pojo10Long) source).field2; });
            writerCustomMultiOptimized.addInt64FieldWriter("3", (source) -> { return ((Pojo10Long) source).field3; });
            writerCustomMultiOptimized.addInt64FieldWriter("4", (source) -> { return ((Pojo10Long) source).field4; });
            writerCustomMultiOptimized.addInt64FieldWriter("5", (source) -> { return ((Pojo10Long) source).field5; });
            writerCustomMultiOptimized.addInt64FieldWriter("6", (source) -> { return ((Pojo10Long) source).field6; });
            writerCustomMultiOptimized.addInt64FieldWriter("7", (source) -> { return ((Pojo10Long) source).field7; });
            writerCustomMultiOptimized.addInt64FieldWriter("8", (source) -> { return ((Pojo10Long) source).field8; });
            writerCustomMultiOptimized.addInt64FieldWriter("9", (source) -> { return ((Pojo10Long) source).field9; });
            writerCustomMultiOptimized.init();
        }

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
    public Object iapCustomWriteSingle(IapState state, Blackhole blackhole) {

        state.writerCustomSingle.writeObject(state.pojo1Long, 2, state.dest, 0);

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
    public Object iapCustomWriteMulti(IapState state, Blackhole blackhole) {

        state.writerCustomMulti.writeObject(state.pojo10Long, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapCustomWriteMultiOptimized(IapState state, Blackhole blackhole) {

        state.writerCustomMultiOptimized.writeObject(state.pojo10Long, 2, state.dest, 0);

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
