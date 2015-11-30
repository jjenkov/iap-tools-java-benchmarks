package com.jenkov.iap.pojos;

/**
 * Created by jjenkov on 16-11-2015.
 */
public class PojoArray1String {

    public Pojo1String[] pojos = null;

    public PojoArray1String(int count) {
        this.pojos = new Pojo1String[count];
        for(int i=0; i < count; i++){
            this.pojos[i] = new Pojo1String();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + this.pojos.length + ")";
    }

}
