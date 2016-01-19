package com.jenkov.iap.other;

import com.jenkov.iap.ion.read.IonReader;

/**
 * Created by jjenkov on 18-12-2015.
 */
public class IonReaderExamples {

    public static void createIonReader() {
        byte[] src = new byte[10 * 1024];
        int ionDataLength = 0; //

        // write ION data into src byte array
        // and set ionDataLength to length of ION data.

        IonReader reader = new IonReader(src, 0, ionDataLength);
    }

    public static void createIonReader2() {
        IonReader reader = new IonReader();

        byte[] src = new byte[10 * 1024];
        int ionDataLength = 0; //

        // write ION data into src byte array
        // and set ionDataLength to length of ION data.

        reader.setSource(src, 0, ionDataLength);
    }

    public static void iterate() {
        IonReader reader = new IonReader();

        while(reader.hasNext()) {
            reader.readUtf8String();
            reader.readKeyShortAsUtf8String();
        }
    }




}
