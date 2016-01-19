package com.jenkov.iap.ion.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.jenkov.iap.ion.IonFieldTypes;
import com.jenkov.iap.ion.pojos.Pojo1Mixed;
import com.jenkov.iap.ion.pojos.PojoArray1Mixed;
import com.jenkov.iap.ion.write.IonObjectWriter;
import com.jenkov.iap.ion.write.IonWriter;
import com.jenkov.iap.ion.proto.TestPojo1MixedOuter;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IonMixedReadBenchmark {



    @State(Scope.Thread)
    public static class IapState {
        Pojo1Mixed      pojo1      = new Pojo1Mixed();
        PojoArray1Mixed pojoArray1 = new PojoArray1Mixed();

        IonObjectWriter objWriter1_1  = new IonObjectWriter(Pojo1Mixed.class);
        IonObjectWriter objWriter10_1 = new IonObjectWriter(PojoArray1Mixed.class);

        IonObjectReader objReader1_1  = new IonObjectReader(Pojo1Mixed.class);
        IonObjectReader objReader10_1 = new IonObjectReader(PojoArray1Mixed.class);

        IonReader       readerSingle    = new IonReader();

        byte[] dest1_1        = new byte[10 * 1024];
        int    dest1_1Length  = 0;

        byte[] dest10_1       = new byte[10 * 1024];
        int    dest10_1Length = 0;

        byte[] destOptimized       = new byte[10 * 1024];
        int    destOptimizedLength = 0;

        byte[] destOptimizedStream       = new byte[10 * 1024];
        int    destOptimizedStreamLength = 0;

        byte[]    field0 = new byte[]{'f','i','e','l','d','0'};
        byte[]    field1 = new byte[]{'f','i','e','l','d','1'};
        byte[]    field2 = new byte[]{'f','i','e','l','d','2'};
        byte[]    field3 = new byte[]{'f','i','e','l','d','3'};
        byte[]    field4 = new byte[]{'f','i','e','l','d','4'};


        @Setup(Level.Trial)
        public void doSetup() {
            dest1_1Length  = objWriter1_1.writeObject (pojo1     , 1, dest1_1 , 0);
            dest10_1Length = objWriter10_1.writeObject(pojoArray1, 1, dest10_1, 0);

            int index = 0;
            index += IonWriter.writeObjectBegin(destOptimized, index, 1);

            index += IonWriter.writeBoolean(destOptimized, index, pojo1.field0);
            index += IonWriter.writeInt64  (destOptimized, index, pojo1.field1);
            index += IonWriter.writeFloat32(destOptimized, index, pojo1.field2);
            index += IonWriter.writeFloat64(destOptimized, index, pojo1.field3);
            index += IonWriter.writeUtf8   (destOptimized, index, pojo1.field4);

            IonWriter.writeObjectEnd(destOptimized, index, 1, index);
            destOptimizedLength = index;

            index = 0;
            index += IonWriter.writeBoolean(destOptimizedStream, index, pojo1.field0);
            index += IonWriter.writeInt64  (destOptimizedStream, index, pojo1.field1);
            index += IonWriter.writeFloat32(destOptimizedStream, index, pojo1.field2);
            index += IonWriter.writeFloat64(destOptimizedStream, index, pojo1.field3);
            index += IonWriter.writeUtf8   (destOptimizedStream, index, pojo1.field4);
            destOptimizedStreamLength = index;
        }
    }



    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Mixed      pojo1      = new Pojo1Mixed();
        PojoArray1Mixed pojoArray1 = new PojoArray1Mixed();

        ObjectMapper objectMapper        = new ObjectMapper();
        ObjectMapper objectMapperMsgPack = new ObjectMapper(new MessagePackFactory());
        ObjectMapper objectMapperCbor    = new ObjectMapper(new CBORFactory());

        ByteArrayOutputStream out     = new ByteArrayOutputStream(10 * 1024);
        ByteArrayOutputStream out10_1 = new ByteArrayOutputStream(10 * 1024);

        byte[] bytesJson     = null;
        byte[] bytesMsgPack  = null;
        byte[] bytesCbor     = null;

        byte[] bytesJson10_1    = null;
        byte[] bytesMsgPack10_1 = null;
        byte[] bytesCbor10_1    = null;


        @Setup(Level.Trial)
        public void doSetupOnce() {
            try {
                objectMapper.writeValue(out, pojo1);
                bytesJson = out.toByteArray();

                objectMapper.writeValue(out10_1, pojoArray1);
                bytesJson10_1 = out10_1.toByteArray();

                out.reset();
                objectMapperMsgPack.writeValue(out, pojo1);
                bytesMsgPack = out.toByteArray();

                out10_1.reset();
                objectMapperMsgPack.writeValue(out10_1, pojoArray1);
                bytesMsgPack10_1 = out10_1.toByteArray();

                out.reset();
                objectMapperCbor.writeValue(out, pojo1);
                bytesCbor = out.toByteArray();

                out10_1.reset();
                objectMapperCbor.writeValue(out10_1, pojoArray1);
                bytesCbor = out10_1.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @State(Scope.Thread)
    public static class ProtobufState {
        TestPojo1MixedOuter.TestPojo1Mixed testPojo = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        byte[] bytesProtobuf = null;


        @Setup(Level.Trial)
        public void doSetupOnce() {
            out.reset();
            testPojo = TestPojo1MixedOuter.TestPojo1Mixed.newBuilder()
                    .setField0(true)
                    .setField1(1234)
                    .setField2(123.12F)
                    .setField3(123456.1234D)
                    .setField4("abcdefg")
                    .build()
            ;

            try {
                testPojo.writeTo(out);
                bytesProtobuf = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionObjectReaderRead1_1(IapState state, Blackhole blackhole) {
        Pojo1Mixed pojo = (Pojo1Mixed) state.objReader1_1.read(state.dest1_1, 0);

        blackhole.consume(pojo);
        return pojo;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionObjectReaderRead10_1(IapState state, Blackhole blackhole) {
        PojoArray1Mixed pojo = (PojoArray1Mixed) state.objReader10_1.read(state.dest10_1, 0);

        blackhole.consume(pojo);
        return pojo;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReaderRead1_1(IapState state, Blackhole blackhole) {

        IonReader reader = state.readerSingle;
        reader.setSource(state.dest1_1, 0, state.dest1_1Length);

        Pojo1Mixed pojo = new Pojo1Mixed();

        reader.parse();
        if(reader.fieldType == IonFieldTypes.OBJECT){
            reader.moveInto();

            while(reader.hasNext()){
                reader.next();
                reader.parse();

                if(reader.fieldType == IonFieldTypes.KEY_SHORT){
                    if(reader.matches(state.field0)){
                        pojo.field0 = reader.next().parse().readBoolean();
                    } else if(reader.matches(state.field1)){
                        pojo.field1 = reader.next().parse().readInt64();
                    } else if(reader.matches(state.field2)){
                        pojo.field2 = reader.next().parse().readFloat32();
                    } else if(reader.matches(state.field3)){
                        pojo.field3 = reader.next().parse().readFloat64();
                    } else if(reader.matches(state.field4)){
                        pojo.field4 = reader.next().parse().readUtf8String();
                    }
                }
            }
        }

        blackhole.consume(pojo);
        return pojo;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReaderOptimizedRead1_1(IapState state, Blackhole blackhole) {

        IonReader reader = state.readerSingle;
        reader.setSource(state.destOptimized, 0, state.destOptimizedLength);

        Pojo1Mixed pojo = new Pojo1Mixed();

        reader.parse();
        if(reader.fieldType == IonFieldTypes.OBJECT){
            reader.moveInto();
            reader.next();
            reader.parse();
            pojo.field0 = reader.readBoolean();
            pojo.field1 = reader.next().parse().readInt64();
            pojo.field2 = reader.next().parse().readFloat32();
            pojo.field3 = reader.next().parse().readFloat64();
            pojo.field4 = reader.next().parse().readUtf8String();
        }

        blackhole.consume(pojo);
        return pojo;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReaderOptimizedStreamRead1_1(IapState state, Blackhole blackhole) {

        IonReader reader = state.readerSingle;
        reader.setSource(state.destOptimizedStream, 0, state.destOptimizedStreamLength);

        Pojo1Mixed pojo = new Pojo1Mixed();

        pojo.field0 = reader.parse().readBoolean();
        pojo.field1 = reader.next().parse().readInt64();
        pojo.field2 = reader.next().parse().readFloat32();
        pojo.field3 = reader.next().parse().readFloat64();
        pojo.field4 = reader.next().parse().readUtf8String();

        blackhole.consume(pojo);
        return pojo;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead1_1(JacksonState state, Blackhole blackhole) {

        try {
            Pojo1Mixed pojo = state.objectMapper.readValue(state.bytesJson, Pojo1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead10_1(JacksonState state, Blackhole blackhole) {

        try {
            PojoArray1Mixed pojo = state.objectMapper.readValue(state.bytesJson10_1, PojoArray1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead1_1(JacksonState state, Blackhole blackhole) {

        try {
            Pojo1Mixed pojo = state.objectMapperMsgPack.readValue(state.bytesMsgPack, Pojo1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead10_1(JacksonState state, Blackhole blackhole) {

        try {
            PojoArray1Mixed pojo = state.objectMapperMsgPack.readValue(state.bytesMsgPack10_1, PojoArray1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead1_1(JacksonState state, Blackhole blackhole) {

        try {
            Pojo1Mixed pojo = state.objectMapperCbor.readValue(state.bytesCbor, Pojo1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead10_1(JacksonState state, Blackhole blackhole) {

        try {
            PojoArray1Mixed pojo = state.objectMapperCbor.readValue(state.bytesCbor10_1, PojoArray1Mixed.class);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object protobufRead1_1(ProtobufState state, Blackhole blackhole) {

        try {
            TestPojo1MixedOuter.TestPojo1Mixed pojo = TestPojo1MixedOuter.TestPojo1Mixed.parseFrom(state.bytesProtobuf);
            blackhole.consume(pojo);
            return pojo;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }







}
