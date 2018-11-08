package com.charlyge.android.debakingapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.charlyge.android.debakingapp.Adapters.SelectRecipeDetailAdapter;
import com.charlyge.android.debakingapp.AppWidget.WidgetService;
import com.charlyge.android.debakingapp.R;
import com.charlyge.android.debakingapp.WatchRecipeSteps;
import com.charlyge.android.debakingapp.model.Ingredients;
import com.charlyge.android.debakingapp.model.Steps;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.charlyge.android.debakingapp.MainActivity.INGREDIENT_KEY;
import static com.charlyge.android.debakingapp.MainActivity.STEPS_KEY;

public class SelectRecipeDetailFragment extends Fragment implements SelectRecipeDetailAdapter.ClickedListener {
    private List<Steps> stepsList;
    @BindView(R.id.recycler_select)
     RecyclerView recyclerView;
    @BindView(R.id.ingredient_tv)
     TextView ingredientTv;
   List<Ingredients> ingredientsList;

    private boolean mTwoPane;
    public static final String ADAPTER_POSITION = "Adapter position";
    public static String MY_PREFERENCE = " MY_PREFERENCE";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_recipe_detail, container, false);
        ButterKnife.bind(this,view);
        mTwoPane = view.findViewById(R.id.two_pane) != null;
        Intent intent = getActivity().getIntent();
         if(intent.getSerializableExtra(INGREDIENT_KEY) !=null){
             ingredientsList = (List<Ingredients>) intent.getSerializableExtra(INGREDIENT_KEY);
             SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFERENCE,MODE_PRIVATE).edit();

             StringBuilder stringBuilder = new StringBuilder();
             for (int i = 0; i <ingredientsList.size() ; i++) {
                 stringBuilder.append(" . ").append(ingredientsList.get(i).getIngredient()).append("( ").append(ingredientsList.get(i).getQuantity()).append(" ").append(ingredientsList.get(i).getMeasure()).append(" )").append("\n");
                 ingredientTv.append(" . " + ingredientsList.get(i).getIngredient() + "( "+ ingredientsList.get(i).getQuantity()
                         + " " + ingredientsList.get(i).getMeasure() + " )" +"\n");
             }
             String ingredientWidget = stringBuilder.toString();
             editor.putString(INGREDIENT_KEY,ingredientWidget);
             editor.apply();
             WidgetService.StartWidgetService(getActivity());
         }
        if (intent.getSerializableExtra(STEPS_KEY) != null) {
            stepsList = (List<Steps>) intent.getSerializableExtra(STEPS_KEY);

            Log.i("RecipesDetails", stepsList.toString());


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            SelectRecipeDetailAdapter selectRecipeDetailAdapter = new SelectRecipeDetailAdapter(stepsList,this);

            recyclerView.setAdapter(selectRecipeDetailAdapter);


        }
        return view;
    }

    @Override
    public void onItemClicked(int AdapterPosition) {
       if(mTwoPane){
           WatchRecipeStepsFragment watchRecipeStepsFragment = new WatchRecipeStepsFragment();
           watchRecipeStepsFragment.setAdapterPosition(AdapterPosition);
           watchRecipeStepsFragment.setStepsArrayList(stepsList);
           FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
           fragmentManager.beginTransaction().replace(R.id.watch_video_container,watchRecipeStepsFragment).commit();

       }

       else {
           Intent intent = new Intent(getActivity(), WatchRecipeSteps.class);
           intent.putExtra(ADAPTER_POSITION,AdapterPosition);
           intent.putExtra(STEPS_KEY, (Serializable) stepsList);
           startActivity(intent);

       }

    }
}
