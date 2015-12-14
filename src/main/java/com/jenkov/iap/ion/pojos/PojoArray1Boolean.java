package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray1Boolean {

    public Pojo1Boolean[] pojos = null;

    public PojoArray1Boolean(int count) {
        this.pojos = new Pojo1Boolean[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo1Boolean();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
