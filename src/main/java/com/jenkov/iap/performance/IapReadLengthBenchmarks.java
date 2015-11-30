package com.jenkov.iap.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 14-11-2015.
 */
public class IapReadLengthBenchmarks {

    @State(Scope.Thread)
    public static class BenchmarkState {

        byte[]    dest         = new byte[10 * 1024];
        int       lengthLength = 4;

        @Setup(Level.Trial)
        public void toSetup() {
            for(int i=0; i<10; i++){
                dest[i] = (byte) i;
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object oldReadLength(BenchmarkState state, Blackhole blackhole) {
        int lengthLength = state.lengthLength;  //most fields will be less than 4GB in size, so a length length of 4 is a fair number.
        int offset = 0;

        int length = 0;
        for(int i=0; i<lengthLength; i++){
            length <<= 8;
            length |= 255 & state.dest[offset++];
        }

        blackhole.consume(length);
        return length;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object newReadLength(BenchmarkState state, Blackhole blackhole) {
        int lengthLength = state.lengthLength;  //most fields will be less than 4GB in size, so a length length of 4 is a fair number.
        int offset = 0;

        int length = 255 & state.dest[offset++];
        for(int i=1; i<lengthLength; i++){
            length <<= 8;
            length |= 255 & state.dest[offset++];
        }

        blackhole.consume(length);
        return length;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object switchReadLength(BenchmarkState state, Blackhole blackhole) {
        int lengthLength = state.lengthLength;  //most fields will be less than 4GB in size, so a length length of 4 is a fair number.
        int offset = 0;

        int length = 0;
        switch(lengthLength){
            case 7 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 6 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 5 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 4 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 3 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 2 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            case 1 : { length <<= 8; length |= 255 & state.dest[offset++]; }
            default : {
                //do nothing - length length is 0
            }
        }

        blackhole.consume(length);
        return length;
    }


}
