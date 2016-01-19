package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray1Mixed {

    public Pojo1Mixed[] pojos = null;

    public PojoArray1Mixed() {

    }

    public PojoArray1Mixed(int count) {
        this.pojos = new Pojo1Mixed[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo1Mixed();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
