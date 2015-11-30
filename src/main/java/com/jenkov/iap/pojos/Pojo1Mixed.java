package com.jenkov.iap.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class Pojo1Mixed {

    public boolean field0 = true;
    public long    field1 = 1234;
    public float   field2 = 123.12F;
    public double  field3 = 123456.1234D;
    public String  field4 = "abcdefg";

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
