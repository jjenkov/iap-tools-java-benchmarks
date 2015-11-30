package com.jenkov.iap.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class OpenHashMapBenchmark {


    @State(Scope.Thread)
    public static class BenchmarkState {

        public HashMap mapJdk = new HashMap();
        public OpenHashTable mapIap = new OpenHashTable(64);

        public OpenHashTable.Key[] keys = new OpenHashTable.Key[10];
        public Object[] values = new Object[10];

        @Setup(Level.Trial)
        public void toSetup() {
            for(int i=0; i < keys.length; i++){
                keys[i] = OpenHashTable.key(("field" + i).getBytes());
            }
            for(int i=0; i < keys.length; i++){
                mapIap.put(keys[i], "");
                mapJdk.put(keys[i], "");
            }
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object mapIap10Keys(BenchmarkState state, Blackhole blackhole) {

        for(int i=0; i<state.keys.length; i++){
            state.values[i] = state.mapIap.get(state.keys[i]);
        }

        blackhole.consume(state.values);

        return state.values;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public Object mapJdk10Keys(BenchmarkState state, Blackhole blackhole) {

        for(int i=0; i<state.keys.length; i++){
            state.values[i] = state.mapJdk.get(state.keys[i]);
        }

        blackhole.consume(state.values);

        return state.values;
    }



}
