package com.jenkov.iap.ion.readanduse;

import com.jenkov.iap.ion.IonFieldTypes;
import com.jenkov.iap.ion.pojos.Pojo10Boolean;
import com.jenkov.iap.ion.pojos.Pojo1Boolean;
import com.jenkov.iap.ion.pojos.PojoArray1Long;
import com.jenkov.iap.ion.read.IonObjectReader;
import com.jenkov.iap.ion.read.IonReader;
import com.jenkov.iap.ion.write.IonObjectWriter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 04-01-2016.
 */
public class IonReadAndUseBenchmark {


    @State(Scope.Thread)
    public static class IapState {
        PojoArray1Long pojoArray10_1     = new PojoArray1Long(10);

        IonObjectReader readerN_1 = new IonObjectReader(PojoArray1Long.class);
        IonObjectWriter writerN_1 = new IonObjectWriter(PojoArray1Long.class);

        IonReader reader = new IonReader();

        byte[] dest10_1    = new byte[100 * 1024];
        int dest10_1_length = 0;

        byte[] pojosFieldName = "pojos".getBytes();

        @Setup(Level.Trial)
        public void doSetup() {
            dest10_1_length = writerN_1.writeObject (pojoArray10_1  , 2, dest10_1  , 0);
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long iapReadObjectAndUse(IapState state, Blackhole blackhole) {
        PojoArray1Long pojoArray1 = (PojoArray1Long) state.readerN_1.read(state.dest10_1, 0);

        long sum = 0;
        for(int i=0; i<pojoArray1.pojos.length; i++){
            sum += pojoArray1.pojos[i].field0;
        }

        //System.out.println("sum: " + sum);

        blackhole.consume(sum);
        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long iapReadDirectAndUse(IapState state, Blackhole blackhole) {

        IonReader reader = state.reader;
        reader.setSource(state.dest10_1, 0, state.dest10_1_length);

        long sum = 0;

        reader.next();
        reader.parse();
        if(reader.fieldType == IonFieldTypes.OBJECT){
            reader.moveInto();
            reader.next();
            reader.parse();

            if(reader.fieldType == IonFieldTypes.KEY_SHORT){
                if(reader.matches(state.pojosFieldName)){
                    reader.next();
                    reader.parse();

                    if(reader.fieldType == IonFieldTypes.TABLE){
                        reader.moveInto();

                        //skip over first element which we know is an ION Key field representing the column name
                        reader.next();
                        reader.parse();

                        while(reader.hasNext()){
                            reader.next();
                            reader.parse();

                            sum += reader.readInt64();
                        }

                    }
                }
            }

        }

        //System.out.println("sum: " + sum);


        blackhole.consume(sum);
        return sum;
    }




}
