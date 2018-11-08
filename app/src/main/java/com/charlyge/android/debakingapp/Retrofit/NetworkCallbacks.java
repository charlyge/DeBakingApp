package com.charlyge.android.debakingapp.Retrofit;


import com.charlyge.android.debakingapp.model.Recipes;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.Call;

public interface NetworkCallbacks {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipes>> getRecipe();
}
