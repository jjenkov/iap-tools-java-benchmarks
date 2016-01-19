package com.jenkov.iap.ion.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.jenkov.iap.ion.pojos.*;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IonFloatWriteBenchmark {


    @State(Scope.Thread)
    public static class IonState {
        Pojo1Float       pojo1_1  = new Pojo1Float();
        Pojo10Float      pojo1_10 = new Pojo10Float();

        PojoArray1Float pojoArray10_1     = new PojoArray1Float(10);
        PojoArray1Float pojoArray100_1    = new PojoArray1Float(100);
        PojoArray1Float pojoArray1000_1   = new PojoArray1Float(1000);

        PojoArray10Float pojoArray10_10   = new PojoArray10Float(10);
        PojoArray10Float pojoArray100_10  = new PojoArray10Float(100);
        PojoArray10Float pojoArray1000_10 = new PojoArray10Float(1000);

        IonObjectWriter writer1_1  = new IonObjectWriter(Pojo1Float.class);
        IonObjectWriter writerN_1  = new IonObjectWriter(PojoArray1Float.class);
        IonObjectWriter writer1_10 = new IonObjectWriter(Pojo10Float.class);
        IonObjectWriter writerN_10 = new IonObjectWriter(PojoArray10Float.class);

        byte[] dest1_1     = new byte[100 * 1024];
        byte[] dest10_1    = new byte[100 * 1024];
        byte[] dest100_1   = new byte[100 * 1024];
        byte[] dest1000_1  = new byte[100 * 1024];
        byte[] dest1_10    = new byte[100 * 1024];
        byte[] dest10_10   = new byte[100 * 1024];
        byte[] dest100_10  = new byte[100 * 1024];
        byte[] dest1000_10 = new byte[100 * 1024];
    }




    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Float       pojo1_1  = new Pojo1Float();
        Pojo10Float      pojo1_10 = new Pojo10Float();

        PojoArray1Float pojoArray10_1     = new PojoArray1Float(10);
        PojoArray1Float pojoArray100_1    = new PojoArray1Float(100);
        PojoArray1Float pojoArray1000_1   = new PojoArray1Float(1000);

        PojoArray10Float pojoArray10_10   = new PojoArray10Float(10);
        PojoArray10Float pojoArray100_10  = new PojoArray10Float(100);
        PojoArray10Float pojoArray1000_10 = new PojoArray10Float(1000);

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
    public Object ionWrite1_1(IonState state, Blackhole blackhole) {

        state.writer1_1.writeObject(state.pojo1_1, 1, state.dest1_1, 0);

        blackhole.consume(state.dest1_1);
        return state.dest1_1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite10_1(IonState state, Blackhole blackhole) {

        state.writerN_1.writeObject(state.pojoArray10_1, 1, state.dest10_1, 0);

        blackhole.consume(state.dest10_1);
        return state.dest10_1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite100_1(IonState state, Blackhole blackhole) {

        state.writerN_1.writeObject(state.pojoArray100_1, 1, state.dest100_1, 0);

        blackhole.consume(state.dest100_1);
        return state.dest100_1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite1000_1(IonState state, Blackhole blackhole) {

        state.writerN_1.writeObject(state.pojoArray1000_1, 1, state.dest1000_1, 0);

        blackhole.consume(state.dest1000_1);
        return state.dest1000_1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite1_10(IonState state, Blackhole blackhole) {

        state.writer1_10.writeObject(state.pojo1_10, 1, state.dest1_10, 0);

        blackhole.consume(state.dest1_10);
        return state.dest1_10;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite10_10(IonState state, Blackhole blackhole) {

        state.writerN_10.writeObject(state.pojoArray10_10, 1, state.dest10_10, 0);

        blackhole.consume(state.dest10_10);
        return state.dest10_10;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite100_10(IonState state, Blackhole blackhole) {

        state.writerN_10.writeObject(state.pojoArray100_10, 1, state.dest100_10, 0);

        blackhole.consume(state.dest100_10);
        return state.dest100_10;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWrite1000_10(IonState state, Blackhole blackhole) {

        state.writerN_10.writeObject(state.pojoArray1000_10, 1, state.dest1000_10, 0);

        blackhole.consume(state.dest1000_10);
        return state.dest1000_10;
    }





    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite1_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojo1_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite10_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray10_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite100_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray100_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite1000_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray1000_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite1_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojo1_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite10_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray10_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite100_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray100_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonJsonWrite1000_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperJson.writeValue(state.out, state.pojoArray1000_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite1_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojo1_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite10_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray10_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite100_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray100_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite1000_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray1000_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite1_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojo1_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite10_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray10_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite100_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray100_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonMsgPackWrite1000_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperMsgPack.writeValue(state.out, state.pojoArray1000_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite1_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojo1_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite10_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray10_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite100_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray100_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite1000_1(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray1000_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite1_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojo1_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite10_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray10_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite100_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray100_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonCborWrite1000_10(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapperCbor.writeValue(state.out, state.pojoArray1000_10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }




}
