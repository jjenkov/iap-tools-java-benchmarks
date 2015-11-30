package com.jenkov.iap.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.jenkov.iap.pojos.Pojo10Boolean;
import com.jenkov.iap.pojos.Pojo1Boolean;
import com.jenkov.iap.pojos.PojoArray10Boolean;
import com.jenkov.iap.pojos.PojoArray1Boolean;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IapBooleanWriteBenchmark {


    @State(Scope.Thread)
    public static class IapState {
        Pojo1Boolean pojo1Boolean = new Pojo1Boolean();
        Pojo10Boolean pojo10Boolean = new Pojo10Boolean();
        PojoArray10Boolean pojoArray10Boolean10 = new PojoArray10Boolean(10);
        PojoArray10Boolean pojoArray1000Boolean10 = new PojoArray10Boolean(1000);

        IonObjectWriter writerSingle = new IonObjectWriter(Pojo1Boolean.class);
        IonObjectWriter writerMulti  = new IonObjectWriter(Pojo10Boolean.class);
        IonObjectWriter writerArraySingle = new IonObjectWriter(PojoArray1Boolean.class);
        IonObjectWriter writerArrayMulti  = new IonObjectWriter(PojoArray10Boolean.class);

        byte[]    dest   = new byte[10 * 1024];
    }



    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Boolean pojo1Boolean = new Pojo1Boolean();
        Pojo10Boolean pojo10Boolean = new Pojo10Boolean();
        PojoArray10Boolean pojoArray10Boolean10 = new PojoArray10Boolean(10);
        PojoArray10Boolean pojoArray1000Boolean10 = new PojoArray10Boolean(1000);

        ObjectMapper objectMapperJson    = new ObjectMapper();
        ObjectMapper objectMapperMsgPack = new ObjectMapper(new MessagePackFactory());
        ObjectMapper objectMapperCbor    = new ObjectMapper(new CBORFactory());

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        @Setup(Level.Invocation)
        public void doSetup() {
            out.reset();
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapIonWriteSingle(IapState state, Blackhole blackhole) {

        state.writerSingle.writeObject(state.pojo1Boolean, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapIonWriteMulti(IapState state, Blackhole blackhole) {

        state.writerMulti.writeObject(state.pojo10Boolean, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapIonWriteArray10x10(IapState state, Blackhole blackhole) {

        state.writerArrayMulti.writeObject(state.pojoArray10Boolean10, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapIonWriteArray1000x10(IapState state, Blackhole blackhole) {

        state.writerArrayMulti.writeObject(state.pojoArray1000Boolean10, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }




    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWriteSingle(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojo1Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWriteMulti(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojo10Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWriteArray10x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray10Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWriteArray1000x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray1000Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWriteSingle(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojo1Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWriteMulti(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojo10Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWriteArray10x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray10Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWriteArray1000x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray1000Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWriteSingle(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojo1Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWriteMulti(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojo10Boolean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWriteArray10x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray10Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWriteArray1000x10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray1000Boolean10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }




}
