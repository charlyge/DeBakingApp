package com.charlyge.android.debakingapp.model;

public class Ingredients {
    private int quantity;
    private String measure;
    private String ingredient;


    public Ingredients(){}

    public Ingredients(int quantity, String measure,String ingredient){
        this.ingredient =ingredient;
        this.measure = measure;
        this.quantity = quantity;

    }
    public int getQuantity() {
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }
}
