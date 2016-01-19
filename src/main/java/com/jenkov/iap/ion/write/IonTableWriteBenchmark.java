package com.jenkov.iap.ion.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 10-11-2015.
 */
public class IonTableWriteBenchmark {

    public static class ArrayFieldPojo {
        public ThreeFieldPojo[] pojoArray = null;
    }

    public static class ThreeFieldPojo {
        public boolean field0   = true;
        public short   field1   = 1;
        public int     field2   = 12;
        public long    field3   = 123;
        public float   field4   = 1234.12F;
    }


    @State(Scope.Thread)
    public static class IapState {
        ArrayFieldPojo arrayFieldPojo = new ArrayFieldPojo();

        byte[]    dest   = new byte[10 * 1024];

       IonObjectWriter writer = new IonObjectWriter(ArrayFieldPojo.class);


        @Setup(Level.Trial)
        public void doSetup() {
            arrayFieldPojo.pojoArray = new ThreeFieldPojo[10];

            arrayFieldPojo.pojoArray[0] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[1] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[2] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[3] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[4] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[5] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[6] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[7] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[8] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[9] = new ThreeFieldPojo();
        }

    }


    @State(Scope.Thread)
    public static class JacksonState {
        ArrayFieldPojo arrayFieldPojo = new ArrayFieldPojo();

        byte[]    dest   = new byte[10 * 1024];

        ObjectMapper objectMapper = new ObjectMapper();

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);


        @Setup(Level.Trial)
        public void doSetupOnce() {
            arrayFieldPojo.pojoArray = new ThreeFieldPojo[10];

            arrayFieldPojo.pojoArray[0] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[1] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[2] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[3] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[4] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[5] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[6] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[7] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[8] = new ThreeFieldPojo();
            arrayFieldPojo.pojoArray[9] = new ThreeFieldPojo();
        }

        @Setup(Level.Invocation)
        public void doSetup() {
            //System.out.println(out.toByteArray().length);
            out.reset();
        }

    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapWriteSingle(IapState state, Blackhole blackhole) {

        state.writer.writeObject(state.arrayFieldPojo, 2, state.dest, 0);

        //int tableLength = state.writer.writeObject(state.arrayFieldPojo, 2, state.dest, 0);
        //System.out.println("tableLength: " + tableLength);

        blackhole.consume(state.dest);
        return state.dest;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonWriteSingle(JacksonState state, Blackhole blackhole) {

        try {
            state.objectMapper.writeValue(state.out, state.arrayFieldPojo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }


}
