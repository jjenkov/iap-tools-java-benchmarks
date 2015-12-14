package com.jenkov.iap.ion.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jjenkov on 03-11-2015.
 */
public class OpenArrayListBenchmark {


    @State(Scope.Thread)
    public static class BenchmarkState {

        public ArrayList<Long> jdkArrayList10 = new ArrayList<>();
        public ArrayList<Long> jdkArrayList100 = new ArrayList<>();
        public OpenArrayList openArrayList10 = new OpenArrayList(10);
        public OpenArrayList openArrayList100 = new OpenArrayList(100);

        @Setup(Level.Trial)
        public void toSetup() {
            Object[] elements = openArrayList10.elements;
            for(int i=0; i < openArrayList10.capacity; i++){
                elements[i] = new Long(i);
                jdkArrayList10.add(new Long(i));
            }
            openArrayList10.size = openArrayList10.capacity;

            elements = openArrayList100.elements;
            for(int i=0; i < openArrayList100.capacity; i++){
                elements[i] = new Long(i);
                jdkArrayList100.add(new Long(i));
            }
            openArrayList100.size = openArrayList100.capacity;
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long openArrayList10Sum(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        Object[] elements = state.openArrayList10.elements;
        for(int i=0; i<state.openArrayList10.size; i++){
            sum += ((Long) elements[i]).longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long openArrayList100Sum(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        Object[] elements = state.openArrayList100.elements;
        for(int i=0; i<state.openArrayList100.size; i++){
            sum += ((Long) elements[i]).longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList10SumUsingGet(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        ArrayList arrayList = state.jdkArrayList10;
        for(int i=0, n=state.jdkArrayList10.size(); i < n; i++){
            sum += ((Long) arrayList.get(i)).longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList100SumUsingGet(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        ArrayList arrayList = state.jdkArrayList100;
        for(int i=0, n=state.jdkArrayList100.size(); i < n; i++){
            sum += ((Long) arrayList.get(i)).longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList10SumUsingForEach(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;

        for(Long element : state.jdkArrayList10){
            sum += element.longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList100SumUsingForEach(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;

        for(Long element : state.jdkArrayList100){
            sum += element.longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList10SumUsingIterator(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        Iterator<Long> iterator = state.jdkArrayList10.iterator();
        while(iterator.hasNext()){
            sum += iterator.next().longValue();
        }

        blackhole.consume(sum);

        return sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public long jdkArrayList100SumUsingIterator(BenchmarkState state, Blackhole blackhole) {

        long sum = 0;
        Iterator<Long> iterator = state.jdkArrayList100.iterator();
        while(iterator.hasNext()){
            sum += iterator.next().longValue();
        }

        blackhole.consume(sum);

        return sum;
    }



}
