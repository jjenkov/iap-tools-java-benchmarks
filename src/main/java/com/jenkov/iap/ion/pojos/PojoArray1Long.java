package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray1Long {

    public Pojo1Long[] pojos = null;

    public PojoArray1Long(int count) {
        this.pojos = new Pojo1Long[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo1Long();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
