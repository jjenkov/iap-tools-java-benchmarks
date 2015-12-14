package com.jenkov.iap.ion.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jenkov.iap.ion.pojos.Pojo10Boolean;
import com.jenkov.iap.ion.pojos.Pojo1Boolean;
import com.jenkov.iap.ion.write.IonObjectWriter;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IapBooleanReadBenchmark {



    @State(Scope.Thread)
    public static class IapState {
        Pojo1Boolean pojo1Boolean = new Pojo1Boolean();
        Pojo10Boolean pojo10Boolean  = new Pojo10Boolean();

        IonObjectWriter writerSingle = new IonObjectWriter(Pojo1Boolean.class);
        IonObjectWriter writerMulti  = new IonObjectWriter(Pojo10Boolean.class);

        IonObjectReader readerSingle = new IonObjectReader(Pojo1Boolean.class);
        IonObjectReader readerMulti  = new IonObjectReader(Pojo10Boolean.class);

        byte[]    destSingle   = new byte[10 * 1024];
        byte[]    destMulti    = new byte[10 * 1024];

        @Setup(Level.Trial)
        public void doSetup() {
            writerSingle.writeObject(pojo1Boolean, 2, destSingle, 0);
            writerMulti.writeObject(pojo10Boolean, 2, destMulti, 0);
        }

    }




    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Boolean pojo1Boolean = new Pojo1Boolean();
        Pojo10Boolean pojo10Boolean = new Pojo10Boolean();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperMsgPack = new ObjectMapper(new MessagePackFactory());

        ByteArrayOutputStream outSingle = new ByteArrayOutputStream(10 * 1024);
        ByteArrayOutputStream outMulti  = new ByteArrayOutputStream(10 * 1024);

        byte[] bytesSingle = null;
        byte[] bytesMulti  = null;


        @Setup(Level.Trial)
        public void doSetupOnce() {
            try {
                objectMapper.writeValue(outSingle, pojo1Boolean);
                bytesSingle = outSingle.toByteArray();

                objectMapper.writeValue(outMulti, pojo10Boolean);
                bytesMulti = outMulti.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapReadSingle(IapState state, Blackhole blackhole) {

        Pojo1Boolean pojo1Boolean = (Pojo1Boolean) state.readerSingle.read(state.destSingle, 0);

        blackhole.consume(pojo1Boolean);
        return pojo1Boolean;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object iapReadMulti(IapState state, Blackhole blackhole) {

        Pojo10Boolean pojo10Boolean = (Pojo10Boolean) state.readerMulti.read(state.destMulti, 0);

        blackhole.consume(pojo10Boolean);
        return pojo10Boolean;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonReadSingle(JacksonState state, Blackhole blackhole) {

        try {
            Pojo1Boolean pojo1Boolean = state.objectMapper.readValue(state.bytesSingle, Pojo1Boolean.class);
            blackhole.consume(pojo1Boolean);
            return pojo1Boolean;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jacksonReadMulti(JacksonState state, Blackhole blackhole) {
        try {
            Pojo10Boolean pojo10Boolean = state.objectMapper.readValue(state.bytesMulti, Pojo10Boolean.class);
            blackhole.consume(pojo10Boolean);
            return pojo10Boolean;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }






}
