package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray10Boolean {

    public Pojo10Boolean[] pojos = null;

    public PojoArray10Boolean(int count) {
        this.pojos = new Pojo10Boolean[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo10Boolean();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
