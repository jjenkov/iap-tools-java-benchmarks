package com.jenkov.iap.ion.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray10String {

    public Pojo10String[] pojos = null;

    public PojoArray10String(int count) {
        this.pojos = new Pojo10String[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo10String();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
