package com.jenkov.iap.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class SwitchVsForBenchmarks {

    /*
    @State(Scope.Thread)
    public static class BenchmarkState {

        int iterations16 = 16;
        int iterations8  = 8;
        int iterations4  = 4;
        byte[] source    = new byte[16];

        @Setup(Level.Trial)
        public void toSetup() {
            for(int i=0; i<source.length; i++){
                source[i] = (byte) i;
            }
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object switchPerf4(BenchmarkState state, Blackhole blackhole) {
        int result = 0;
        int index  = 0;
        switch(state.iterations4){
            case 16 : result += state.source[index++];
            case 15 : result += state.source[index++];
            case 14 : result += state.source[index++];
            case 13 : result += state.source[index++];
            case 12 : result += state.source[index++];
            case 11 : result += state.source[index++];
            case 10 : result += state.source[index++];
            case  9 : result += state.source[index++];
            case  8 : result += state.source[index++];
            case  7 : result += state.source[index++];
            case  6 : result += state.source[index++];
            case  5 : result += state.source[index++];
            case  4 : result += state.source[index++];
            case  3 : result += state.source[index++];
            case  2 : result += state.source[index++];
            case  1 : result += state.source[index++];

        }

        blackhole.consume(result);

        return result;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object switchPerf8(BenchmarkState state, Blackhole blackhole) {
        int result = 0;
        int index  = 0;
        switch(state.iterations8){
            case 16 : result += state.source[index++];
            case 15 : result += state.source[index++];
            case 14 : result += state.source[index++];
            case 13 : result += state.source[index++];
            case 12 : result += state.source[index++];
            case 11 : result += state.source[index++];
            case 10 : result += state.source[index++];
            case  9 : result += state.source[index++];
            case  8 : result += state.source[index++];
            case  7 : result += state.source[index++];
            case  6 : result += state.source[index++];
            case  5 : result += state.source[index++];
            case  4 : result += state.source[index++];
            case  3 : result += state.source[index++];
            case  2 : result += state.source[index++];
            case  1 : result += state.source[index++];
        }

        blackhole.consume(result);

        return result;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object switchPerf16(BenchmarkState state, Blackhole blackhole) {
        int result = 0;
        int index  = 0;
        switch(state.iterations16){
            case 16 : result += state.source[index++];
            case 15 : result += state.source[index++];
            case 14 : result += state.source[index++];
            case 13 : result += state.source[index++];
            case 12 : result += state.source[index++];
            case 11 : result += state.source[index++];
            case 10 : result += state.source[index++];
            case  9 : result += state.source[index++];
            case  8 : result += state.source[index++];
            case  7 : result += state.source[index++];
            case  6 : result += state.source[index++];
            case  5 : result += state.source[index++];
            case  4 : result += state.source[index++];
            case  3 : result += state.source[index++];
            case  2 : result += state.source[index++];
            case  1 : result += state.source[index++];
        }

        blackhole.consume(result);

        return result;
    }



    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object forPerf4(BenchmarkState state, Blackhole blackhole) {
        int result = 0;

        for(int i=0; i<state.iterations4; i++){
            result += state.source[i];
        }

        blackhole.consume(result);
        return result;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object forPerf8(BenchmarkState state, Blackhole blackhole) {
        int result = 0;

        for(int i=0; i<state.iterations8; i++){
            result += state.source[i];
        }

        blackhole.consume(result);
        return result;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object forPerf16(BenchmarkState state, Blackhole blackhole) {
        int result = 0;

        for(int i=0; i<state.iterations16; i++){
            result += state.source[i];
        }

        blackhole.consume(result);
        return result;
    }


   */

}
