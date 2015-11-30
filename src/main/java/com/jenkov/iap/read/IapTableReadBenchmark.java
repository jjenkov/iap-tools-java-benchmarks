package com.jenkov.iap.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenkov.iap.write.IonObjectWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 10-11-2015.
 */
public class IapTableReadBenchmark {

    @State(Scope.Thread)
    public static class IapState {

        TestPojoArray   array2Source = new TestPojoArray();
        TestPojoArray   array2Dest   = new TestPojoArray();

        byte[]    dest   = new byte[10 * 1024];

        IonObjectWriter writer = new IonObjectWriter(TestPojoArray.class);
        IonObjectReader reader = new IonObjectReader(TestPojoArray.class);


        @Setup(Level.Trial)
        public void doSetup() {
            array2Source.testObjects    = new TestPojoArray.TestObject[10];
            array2Source.testObjects[0] = new TestPojoArray.TestObject();
            array2Source.testObjects[1] = new TestPojoArray.TestObject();
            array2Source.testObjects[2] = new TestPojoArray.TestObject();
            array2Source.testObjects[3] = new TestPojoArray.TestObject();
            array2Source.testObjects[4] = new TestPojoArray.TestObject();
            array2Source.testObjects[5] = new TestPojoArray.TestObject();
            array2Source.testObjects[6] = new TestPojoArray.TestObject();
            array2Source.testObjects[7] = new TestPojoArray.TestObject();
            array2Source.testObjects[8] = new TestPojoArray.TestObject();
            array2Source.testObjects[9] = new TestPojoArray.TestObject();

            int length = writer.writeObject(array2Source, 2, dest, 0);

        }

    }


    @State(Scope.Thread)
    public static class JacksonState {
        TestPojoArray   array2Source = new TestPojoArray();
        TestPojoArray   array2Dest   = new TestPojoArray();

        byte[]    dest = null;

        ObjectMapper objectMapper = new ObjectMapper();

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);


        @Setup(Level.Trial)
        public void doSetupOnce() {
            array2Source.testObjects    = new TestPojoArray.TestObject[10];
            array2Source.testObjects[0] = new TestPojoArray.TestObject();
            array2Source.testObjects[1] = new TestPojoArray.TestObject();
            array2Source.testObjects[2] = new TestPojoArray.TestObject();
            array2Source.testObjects[3] = new TestPojoArray.TestObject();
            array2Source.testObjects[4] = new TestPojoArray.TestObject();
            array2Source.testObjects[5] = new TestPojoArray.TestObject();
            array2Source.testObjects[6] = new TestPojoArray.TestObject();
            array2Source.testObjects[7] = new TestPojoArray.TestObject();
            array2Source.testObjects[8] = new TestPojoArray.TestObject();
            array2Source.testObjects[9] = new TestPojoArray.TestObject();

            try {
                objectMapper.writeValue(out, array2Source);
                this.dest = out.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Setup(Level.Invocation)
        public void doSetup() {
            //System.out.println(out.toByteArray().length);
            //out.reset();
        }

    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapReadTable (IapState state, Blackhole blackhole) {
        state.array2Dest = (TestPojoArray) state.reader.read(state.dest, 0);

        //System.out.println(state.array2Dest.testObjects.length);

        blackhole.consume(state.array2Dest);
        return state.array2Dest;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonReadTable(JacksonState state, Blackhole blackhole) {

        try {
            state.array2Dest = state.objectMapper.readValue(state.dest, TestPojoArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.array2Dest);
        blackhole.consume(state.array2Dest.testObjects);
        return state.array2Dest;
    }


}
