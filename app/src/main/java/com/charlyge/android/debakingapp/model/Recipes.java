package com.charlyge.android.debakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipes implements Parcelable{

    private int id;
    private String name;


    @SerializedName("ingredients")
    private List<Ingredients> ingredientsArrayList;

    @SerializedName("steps")
    private List<Steps> stepsArrayList;
    private String image;
    private String servings;

    public Recipes() {

    }

    public Recipes(int id, String name, List<Ingredients> ingredientsArrayList, List<Steps> stepsArrayList, String image, String servings) {
        this.id = id;
        this.image = image;
        this.ingredientsArrayList = ingredientsArrayList;
        this.stepsArrayList = stepsArrayList;
        this.name = name;
        this.servings = servings;
    }

    private Recipes(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        servings = in.readString();
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public List<Steps> getStepsArrayList() {
        return stepsArrayList;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Ingredients> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public String getServings() {
        return servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeString(servings);
    }
}
