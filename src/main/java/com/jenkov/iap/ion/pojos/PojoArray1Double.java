package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray1Double {

    public Pojo1Double[] pojos = null;

    public PojoArray1Double(int count) {
        this.pojos = new Pojo1Double[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo1Double();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
