package com.jenkov.iap.write;

import com.jenkov.iap.IonUtil;
import com.jenkov.iap.pojos.Pojo10Long;
import com.jenkov.iap.pojos.Pojo1Long;
import com.jenkov.proto.TestPojo10LongOuter;
import com.jenkov.proto.TestPojoProtos;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by jjenkov on 25-11-2015.
 */
public class IonWriterBenchmark {

    @State(Scope.Thread)
    public static class IapState {
        Pojo1Long pojo1Long = new Pojo1Long();
        Pojo10Long pojo10Long = new Pojo10Long();

        IonObjectWriter writerSingle = new IonObjectWriter(Pojo1Long.class);
        IonObjectWriter writerMulti  = new IonObjectWriter(Pojo10Long.class);

        byte[]    dest   = new byte[10 * 1024];

        byte[] field0KeyFieldBytes = null;
        byte[] field1KeyFieldBytes = null;
        byte[] field2KeyFieldBytes = null;
        byte[] field3KeyFieldBytes = null;
        byte[] field4KeyFieldBytes = null;
        byte[] field5KeyFieldBytes = null;
        byte[] field6KeyFieldBytes = null;
        byte[] field7KeyFieldBytes = null;
        byte[] field8KeyFieldBytes = null;
        byte[] field9KeyFieldBytes = null;

        @Setup(Level.Trial)
        public void doSetup() {
            field0KeyFieldBytes = IonUtil.preGenerateKeyField("field0");
            field1KeyFieldBytes = IonUtil.preGenerateKeyField("field1");
            field2KeyFieldBytes = IonUtil.preGenerateKeyField("field2");
            field3KeyFieldBytes = IonUtil.preGenerateKeyField("field3");
            field4KeyFieldBytes = IonUtil.preGenerateKeyField("field4");
            field5KeyFieldBytes = IonUtil.preGenerateKeyField("field5");
            field6KeyFieldBytes = IonUtil.preGenerateKeyField("field6");
            field7KeyFieldBytes = IonUtil.preGenerateKeyField("field7");
            field8KeyFieldBytes = IonUtil.preGenerateKeyField("field8");
            field9KeyFieldBytes = IonUtil.preGenerateKeyField("field9");
        }
    }


    @State(Scope.Thread)
    public static class ProtoBufState {
        TestPojo10LongOuter.TestPojo10Long testPojo = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);


        @Setup(Level.Trial)
        public void toSetup() {
            testPojo = TestPojo10LongOuter.TestPojo10Long.newBuilder()
                    .setField0(1)
                    .setField1(12)
                    .setField2(123)
                    .setField3(1234)
                    .setField4(12345)
                    .setField5(1)
                    .setField6(12)
                    .setField7(123)
                    .setField8(1234)
                    .setField9(12345)
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
    public Object protoBufWritePojo10Long(ProtoBufState state, Blackhole blackhole) {
        try {
            state.testPojo.writeTo(state.out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        blackhole.consume(state.out);
        return state.out;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWriterPojo1Long(IapState state, Blackhole blackhole) {

        int index = 0;
        index += IonWriter.writeObjectBegin(state.dest, index, 2);
        index += IonWriter.writeDirect(state.dest, index, state.field0KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo1Long.field0);
        IonWriter.writeObjectEnd(state.dest, index, 2, index);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWriterPojo10Long(IapState state, Blackhole blackhole) {

        int index = 0;
        index += IonWriter.writeObjectBegin(state.dest, index, 2);

        index += IonWriter.writeDirect(state.dest, index, state.field0KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field0);
        index += IonWriter.writeDirect(state.dest, index, state.field1KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field1);
        index += IonWriter.writeDirect(state.dest, index, state.field2KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field2);
        index += IonWriter.writeDirect(state.dest, index, state.field3KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field3);
        index += IonWriter.writeDirect(state.dest, index, state.field4KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field4);
        index += IonWriter.writeDirect(state.dest, index, state.field5KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field5);
        index += IonWriter.writeDirect(state.dest, index, state.field6KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field6);
        index += IonWriter.writeDirect(state.dest, index, state.field7KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field7);
        index += IonWriter.writeDirect(state.dest, index, state.field8KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field8);
        index += IonWriter.writeDirect(state.dest, index, state.field9KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field9);

        IonWriter.writeObjectEnd(state.dest, index, 2, index);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWriterPojo10LongNoObject(IapState state, Blackhole blackhole) {

        int index = 0;
        //index += IonWriter.writeObjectBegin(state.dest, index, 2);

        index += IonWriter.writeDirect(state.dest, index, state.field0KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field0);
        index += IonWriter.writeDirect(state.dest, index, state.field1KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field1);
        index += IonWriter.writeDirect(state.dest, index, state.field2KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field2);
        index += IonWriter.writeDirect(state.dest, index, state.field3KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field3);
        index += IonWriter.writeDirect(state.dest, index, state.field4KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field4);
        index += IonWriter.writeDirect(state.dest, index, state.field5KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field5);
        index += IonWriter.writeDirect(state.dest, index, state.field6KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field6);
        index += IonWriter.writeDirect(state.dest, index, state.field7KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field7);
        index += IonWriter.writeDirect(state.dest, index, state.field8KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field8);
        index += IonWriter.writeDirect(state.dest, index, state.field9KeyFieldBytes);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field9);

        //IonWriter.writeObjectEnd(state.dest, index, 2, index);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWriterPojo10LongNoObjectNoKeys(IapState state, Blackhole blackhole) {

        int index = 0;
        //index += IonWriter.writeObjectBegin(state.dest, index, 2);

        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field0);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field1);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field2);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field3);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field4);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field5);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field6);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field7);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field8);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field9);

        //IonWriter.writeObjectEnd(state.dest, index, 2, index);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionWriterPojo10LongNoKeys(IapState state, Blackhole blackhole) {

        int index = 0;
        index += IonWriter.writeObjectBegin(state.dest, index, 2);

        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field0);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field1);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field2);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field3);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field4);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field5);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field6);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field7);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field8);
        index += IonWriter.writeInt64(state.dest, index, state.pojo10Long.field9);

        IonWriter.writeObjectEnd(state.dest, index, 2, index);

        blackhole.consume(state.dest);
        return state.dest;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionObjectWriterPojo1Long(IapState state, Blackhole blackhole) {

        state.writerSingle.writeObject(state.pojo1Long, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionObjectWriterPojo10Long(IapState state, Blackhole blackhole) {

        state.writerMulti.writeObject(state.pojo10Long, 2, state.dest, 0);

        blackhole.consume(state.dest);
        return state.dest;
    }

}
