package com.charlyge.android.debakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.debakingapp.Adapters.RecipesAdapter;
import com.charlyge.android.debakingapp.Retrofit.NetworkService;
import com.charlyge.android.debakingapp.model.Ingredients;
import com.charlyge.android.debakingapp.model.Recipes;
import com.charlyge.android.debakingapp.model.Steps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.ItemClickListener {
    private ArrayList<Recipes> recipesList;
    private RecipesAdapter recipesAdapter;
    public static final String STEPS_KEY = "recipes";
    public static final String INGREDIENT_KEY = "Ingredient_key";
    private String TAG = RecipesActivity.class.getSimpleName();
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view_main) RecyclerView recyclerView;
    @BindView(R.id.error_tv) TextView errorTv;
    @BindView(R.id.retry_image) ImageView retry;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recipesAdapter = new RecipesAdapter(this);
        recyclerView.setAdapter(recipesAdapter);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        if(networkInfo != null){
            makeNetworkConection();

        }
        else {
            errorTv.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNetworkConection();
            }
        });

    }

    private void makeNetworkConection() {
     NetworkService.getInstance().getNetworkCallbacks().getRecipe().enqueue(new Callback<ArrayList<Recipes>>() {
         @Override
         public void onResponse(@NonNull Call<ArrayList<Recipes>> call, @NonNull Response<ArrayList<Recipes>> response) {
             progressBar.setVisibility(View.GONE);
             errorTv.setVisibility(View.GONE);
             retry.setVisibility(View.GONE);
             recipesList = response.body();

             recipesAdapter.setRecipesList(recipesList);
         }

         @Override
         public void onFailure(@NonNull Call<ArrayList<Recipes>> call, @NonNull Throwable t) {
             progressBar.setVisibility(View.GONE);
             retry.setVisibility(View.VISIBLE);
             Toast.makeText(RecipesActivity.this,"An Error Occurred " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

         }
     });
    }


    @Override
    public void onItemClicked(List<Steps> steps, List<Ingredients> ingredients) {
        final Intent intent = new Intent(this,SelectRecipeDetailViewActivity.class);
        intent.putExtra(STEPS_KEY, (Serializable) steps);
        intent.putExtra(INGREDIENT_KEY, (Serializable) ingredients);
        startActivity(intent);
    }
}
