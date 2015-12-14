package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class Pojo10Long {
    public long field0 = 1;
    public long field1 = 255;
    public long field2 = 65535;
    public long field3 = 1_000_000;
    public long field4 = 1_000_000_000;
    public long field5 = -1;
    public long field6 = -255;
    public long field7 = -65535;
    public long field8 = -1_000_000;
    public long field9 = -1_000_000_000;

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
