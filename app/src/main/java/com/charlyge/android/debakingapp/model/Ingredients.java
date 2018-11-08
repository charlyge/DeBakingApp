package com.charlyge.android.debakingapp.model;

import java.io.Serializable;

public class Ingredients implements Serializable {
    private Float quantity;
    private String measure;
    private String ingredient;


    public Ingredients(){}

    public Ingredients(Float quantity, String measure,String ingredient){
        this.ingredient =ingredient;
        this.measure = measure;
        this.quantity = quantity;

    }
    public Float getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }
}
