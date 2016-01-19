package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray10Long {

    public Pojo10Long[] pojos = null;

    public PojoArray10Long() {

    }

    public PojoArray10Long(int count) {
        this.pojos = new Pojo10Long[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo10Long();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
