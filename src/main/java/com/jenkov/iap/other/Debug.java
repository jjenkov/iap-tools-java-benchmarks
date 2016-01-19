package com.jenkov.iap.other;

import com.jenkov.iap.ion.read.IonDoubleReadBenchmark;

/**
 * Created by jjenkov on 15-12-2015.
 */
public class Debug {

    public static void main(String[] args) {
        IonDoubleReadBenchmark benchmark = new IonDoubleReadBenchmark();

        IonDoubleReadBenchmark.IapState state = new IonDoubleReadBenchmark.IapState();

        state.doSetup();

        benchmark.ionReadArray1000_10(state, null);

        System.out.println("done");

    }
}
