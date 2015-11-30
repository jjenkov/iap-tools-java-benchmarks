package com.jenkov.iap.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray10Double {

    public Pojo10Double[] pojos = null;

    public PojoArray10Double(int count) {
        this.pojos = new Pojo10Double[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo10Double();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
