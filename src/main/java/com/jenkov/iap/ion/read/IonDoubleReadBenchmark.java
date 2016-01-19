package com.jenkov.iap.ion.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.jenkov.iap.ion.pojos.*;
import com.jenkov.iap.ion.write.IonObjectWriter;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jjenkov on 07-11-2015.
 */
public class IonDoubleReadBenchmark {



    @State(Scope.Thread)
    public static class IapState {
        Pojo1Double  pojo1_1  = new Pojo1Double();
        Pojo10Double pojo1_10 = new Pojo10Double();

        PojoArray1Double pojoArray10_1     = new PojoArray1Double(10);
        PojoArray1Double pojoArray100_1    = new PojoArray1Double(100);
        PojoArray1Double pojoArray1000_1   = new PojoArray1Double(1000);

        PojoArray10Double pojoArray10_10   = new PojoArray10Double(10);
        PojoArray10Double pojoArray100_10  = new PojoArray10Double(100);
        PojoArray10Double pojoArray1000_10 = new PojoArray10Double(1000);

        IonObjectWriter writer1_1  = new IonObjectWriter(Pojo1Double.class);
        IonObjectWriter writerN_1  = new IonObjectWriter(PojoArray1Double.class);
        IonObjectWriter writer1_10 = new IonObjectWriter(Pojo10Double.class);
        IonObjectWriter writerN_10 = new IonObjectWriter(PojoArray10Double.class);

        IonObjectReader reader1       = new IonObjectReader(Pojo1Double.class);
        IonObjectReader readerArray1  = new IonObjectReader(PojoArray1Double.class);
        IonObjectReader reader10      = new IonObjectReader(Pojo10Double.class);
        IonObjectReader readerArray10 = new IonObjectReader(PojoArray10Double.class);


        byte[] dest1_1     = new byte[100 * 1024];
        byte[] dest10_1    = new byte[100 * 1024];
        byte[] dest100_1   = new byte[100 * 1024];
        byte[] dest1000_1  = new byte[100 * 1024];
        byte[] dest1_10    = new byte[100 * 1024];
        byte[] dest10_10   = new byte[100 * 1024];
        byte[] dest100_10  = new byte[100 * 1024];
        byte[] dest1000_10 = new byte[100 * 1024];


        @Setup(Level.Trial)
        public void doSetup() {
            writer1_1.writeObject (pojo1_1        , 2, dest1_1   , 0);
            writerN_1.writeObject (pojoArray10_1  , 2, dest10_1  , 0);
            writerN_1.writeObject (pojoArray100_1 , 2, dest100_1 , 0);
            writerN_1.writeObject (pojoArray1000_1, 2, dest1000_1, 0);

            writer1_10.writeObject(pojo1_10         , 2, dest1_10   , 0);
            writerN_10.writeObject(pojoArray10_10   , 2, dest10_10  , 0);
            writerN_10.writeObject(pojoArray100_10  , 2, dest100_10 , 0);
            writerN_10.writeObject(pojoArray1000_10 , 3, dest1000_10, 0);
        }


    }




    @State(Scope.Thread)
    public static class JacksonState {
        Pojo1Double  pojo1  = new Pojo1Double();
        Pojo10Double pojo10 = new Pojo10Double();

        PojoArray1Double  pojoArray10_1    = new PojoArray1Double(10);
        PojoArray1Double  pojoArray100_1   = new PojoArray1Double(100);
        PojoArray1Double  pojoArray1000_1  = new PojoArray1Double(1000);
        PojoArray10Double pojoArray10_10   = new PojoArray10Double(10);
        PojoArray10Double pojoArray100_10  = new PojoArray10Double(100);
        PojoArray10Double pojoArray1000_10 = new PojoArray10Double(1000);

        ObjectMapper objectMapper          = new ObjectMapper();
        ObjectMapper objectMapperMsgPack   = new ObjectMapper(new MessagePackFactory());
        ObjectMapper objectMapperCbor      = new ObjectMapper(new CBORFactory());

        ByteArrayOutputStream out = new ByteArrayOutputStream(10 * 1024);

        byte[] bytesJson1_1 = null;
        byte[] bytesJson10_1 = null;
        byte[] bytesJson100_1 = null;
        byte[] bytesJson1000_1 = null;

        byte[] bytesJson1_10    = null;
        byte[] bytesJson10_10    = null;
        byte[] bytesJson100_10    = null;
        byte[] bytesJson1000_10    = null;


        byte[] bytesMsgPack1_1 = null;
        byte[] bytesMsgPack10_1 = null;
        byte[] bytesMsgPack100_1 = null;
        byte[] bytesMsgPack1000_1 = null;

        byte[] bytesMsgPack1_10    = null;
        byte[] bytesMsgPack10_10    = null;
        byte[] bytesMsgPack100_10    = null;
        byte[] bytesMsgPack1000_10    = null;


        byte[] bytesCbor1_1 = null;
        byte[] bytesCbor10_1 = null;
        byte[] bytesCbor100_1 = null;
        byte[] bytesCbor1000_1 = null;

        byte[] bytesCbor1_10    = null;
        byte[] bytesCbor10_10    = null;
        byte[] bytesCbor100_10    = null;
        byte[] bytesCbor1000_10    = null;


        @Setup(Level.Trial)
        public void doSetupOnce() {
            try {
                out.reset(); objectMapper.writeValue(out, pojo1);            bytesJson1_1    = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray10_1);    bytesJson10_1   = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray100_1);   bytesJson100_1  = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray1000_1);  bytesJson1000_1 = out.toByteArray();

                out.reset(); objectMapper.writeValue(out, pojo10);            bytesJson1_10    = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray10_10);    bytesJson10_10   = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray100_10);   bytesJson100_10  = out.toByteArray();
                out.reset(); objectMapper.writeValue(out, pojoArray1000_10);  bytesJson1000_10 = out.toByteArray();

                out.reset(); objectMapperMsgPack.writeValue(out, pojo1);            bytesMsgPack1_1    = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray10_1);    bytesMsgPack10_1   = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray100_1);   bytesMsgPack100_1  = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray1000_1);  bytesMsgPack1000_1 = out.toByteArray();

                out.reset(); objectMapperMsgPack.writeValue(out, pojo10);            bytesMsgPack1_10    = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray10_10);    bytesMsgPack10_10   = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray100_10);   bytesMsgPack100_10  = out.toByteArray();
                out.reset(); objectMapperMsgPack.writeValue(out, pojoArray1000_10);  bytesMsgPack1000_10 = out.toByteArray();

                out.reset(); objectMapperCbor.writeValue(out, pojo1);             bytesCbor1_1    = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray10_1);     bytesCbor10_1   = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray100_1);    bytesCbor100_1  = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray1000_1);   bytesCbor1000_1 = out.toByteArray();

                out.reset(); objectMapperCbor.writeValue(out, pojo10);            bytesCbor1_10    = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray10_10);    bytesCbor10_10   = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray100_10);   bytesCbor100_10  = out.toByteArray();
                out.reset(); objectMapperCbor.writeValue(out, pojoArray1000_10);  bytesCbor1000_10 = out.toByteArray();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionRead1_1(IapState state, Blackhole blackhole) {
        Pojo1Double pojo1 = (Pojo1Double) state.reader1.read(state.dest1_1, 0);
        blackhole.consume(pojo1);
        return pojo1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray10_1(IapState state, Blackhole blackhole) {
        PojoArray1Double pojoArray1 = (PojoArray1Double) state.readerArray1.read(state.dest10_1, 0);
        blackhole.consume(pojoArray1);
        return pojoArray1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray100_1(IapState state, Blackhole blackhole) {
        PojoArray1Double pojoArray1 = (PojoArray1Double) state.readerArray1.read(state.dest100_1, 0);
        blackhole.consume(pojoArray1);
        return pojoArray1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray1000_1(IapState state, Blackhole blackhole) {
        PojoArray1Double pojoArray1 = (PojoArray1Double) state.readerArray1.read(state.dest1000_1, 0);
        blackhole.consume(pojoArray1);
        return pojoArray1;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionRead1_10(IapState state, Blackhole blackhole) {
        Pojo10Double pojo10 = (Pojo10Double) state.reader10.read(state.dest1_10, 0);
        blackhole.consume(pojo10);
        return pojo10;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray10_10(IapState state, Blackhole blackhole) {
        PojoArray10Double pojoArray10 = (PojoArray10Double) state.readerArray10.read(state.dest10_10, 0);
        blackhole.consume(pojoArray10);
        return pojoArray10;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray100_10(IapState state, Blackhole blackhole) {
        PojoArray10Double pojoArray10 = (PojoArray10Double) state.readerArray10.read(state.dest100_10, 0);
        blackhole.consume(pojoArray10);
        return pojoArray10;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object ionReadArray1000_10(IapState state, Blackhole blackhole) {
        PojoArray10Double pojoArray10 = (PojoArray10Double) state.readerArray10.read(state.dest1000_10, 0);
        blackhole.consume(pojoArray10);
        return pojoArray10;
    }


    //JSON

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead1_1(JacksonState state, Blackhole blackhole) {
        try {
            Pojo1Double pojo1 = state.objectMapper.readValue(state.bytesJson1_1, Pojo1Double.class);
            blackhole.consume(pojo1);
            return pojo1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead10_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapper.readValue(state.bytesJson10_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead100_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapper.readValue(state.bytesJson100_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead1000_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapper.readValue(state.bytesJson1000_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead1_10(JacksonState state, Blackhole blackhole) {
        try {
            Pojo10Double pojo10 = state.objectMapper.readValue(state.bytesJson1_10, Pojo10Double.class);
            blackhole.consume(pojo10);
            return pojo10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead10_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapper.readValue(state.bytesJson10_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead100_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapper.readValue(state.bytesJson100_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object jsonRead1000_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapper.readValue(state.bytesJson1000_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    //MsgPack

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead1_1(JacksonState state, Blackhole blackhole) {
        try {
            Pojo1Double pojo1 = state.objectMapperMsgPack.readValue(state.bytesMsgPack1_1, Pojo1Double.class);
            blackhole.consume(pojo1);
            return pojo1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead10_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperMsgPack.readValue(state.bytesMsgPack10_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead100_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperMsgPack.readValue(state.bytesMsgPack100_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead1000_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperMsgPack.readValue(state.bytesMsgPack1000_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead1_10(JacksonState state, Blackhole blackhole) {
        try {
            Pojo10Double pojo10 = state.objectMapperMsgPack.readValue(state.bytesMsgPack1_10, Pojo10Double.class);
            blackhole.consume(pojo10);
            return pojo10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead10_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperMsgPack.readValue(state.bytesMsgPack10_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead100_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperMsgPack.readValue(state.bytesMsgPack100_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object msgPackRead1000_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperMsgPack.readValue(state.bytesMsgPack1000_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    //CBOR

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead1_1(JacksonState state, Blackhole blackhole) {
        try {
            Pojo1Double pojo1 = state.objectMapperCbor.readValue(state.bytesCbor1_1, Pojo1Double.class);
            blackhole.consume(pojo1);
            return pojo1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead10_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperCbor.readValue(state.bytesCbor10_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead100_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperCbor.readValue(state.bytesCbor100_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead1000_1(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray1Double pojoArray1 = state.objectMapperCbor.readValue(state.bytesCbor1000_1, PojoArray1Double.class);
            blackhole.consume(pojoArray1);
            return pojoArray1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead1_10(JacksonState state, Blackhole blackhole) {
        try {
            Pojo10Double pojo10 = state.objectMapperCbor.readValue(state.bytesCbor1_10, Pojo10Double.class);
            blackhole.consume(pojo10);
            return pojo10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead10_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperCbor.readValue(state.bytesCbor10_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead100_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperCbor.readValue(state.bytesCbor100_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object cborRead1000_10(JacksonState state, Blackhole blackhole) {
        try {
            PojoArray10Double pojoArray10 = state.objectMapperCbor.readValue(state.bytesCbor1000_10, PojoArray10Double.class);
            blackhole.consume(pojoArray10);
            return pojoArray10;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }











}
