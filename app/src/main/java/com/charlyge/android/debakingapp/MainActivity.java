package com.charlyge.android.debakingapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.charlyge.android.debakingapp.Adapters.RecipesAdapter;
import com.charlyge.android.debakingapp.Retrofit.NetworkService;
import com.charlyge.android.debakingapp.model.Ingredients;
import com.charlyge.android.debakingapp.model.Recipes;
import com.charlyge.android.debakingapp.model.Steps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener {
    private ArrayList<Recipes> recipesList;
    private RecipesAdapter recipesAdapter;
    public static final String STEPS_KEY = "recipes";
    private String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recipesAdapter = new RecipesAdapter(this);
        recyclerView.setAdapter(recipesAdapter);
        setUpStrictMode();
        makeNetworkConection();
    }

    private void makeNetworkConection() {
     NetworkService.getInstance().getNetworkCallbacks().getRecipe().enqueue(new Callback<ArrayList<Recipes>>() {
         @Override
         public void onResponse(Call<ArrayList<Recipes>> call, Response<ArrayList<Recipes>> response) {
             Log.i(TAG,"sucess" + response.body().get(0).getStepsArrayList().toString());
             recipesList = response.body();

             recipesAdapter.setRecipesList(recipesList);
         }

         @Override
         public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {
          Log.i(TAG,"failure" + t.getLocalizedMessage());
         }
     });
    }

    private void setUpStrictMode() {
        if(BuildConfig.DEBUG){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog().build();
            StrictMode.setThreadPolicy(policy);

        }

    }


    @Override
    public void onItemClicked(List<Steps> steps, List<Ingredients> ingredients) {
        final Intent intent = new Intent(this,SelectRecipeDetailViewActivity.class);
        intent.putExtra(STEPS_KEY, (Serializable) steps);
        startActivity(intent);
    }
}
