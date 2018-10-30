package com.charlyge.android.debakingapp.fragments;

import android.content.Intent;
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

import com.charlyge.android.debakingapp.Adapters.SelectRecipeDetailAdapter;
import com.charlyge.android.debakingapp.R;
import com.charlyge.android.debakingapp.WatchRecipeSteps;
import com.charlyge.android.debakingapp.model.Steps;

import java.io.Serializable;
import java.util.List;
import static com.charlyge.android.debakingapp.MainActivity.STEPS_KEY;

public class SelectRecipeDetailFragment extends Fragment implements SelectRecipeDetailAdapter.ClickedListener {
    List<Steps> stepsList;
    RecyclerView recyclerView;
    private boolean mTwoPane;
    public static final String ADAPTER_POSITION = "Adapter position";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_recipe_detail, container, false);
         if(view.findViewById(R.id.two_pane) !=null){
             mTwoPane =true;
         }
         else {
             mTwoPane =false;
         }
        Intent intent = getActivity().getIntent();
        if (intent.getSerializableExtra(STEPS_KEY) != null) {
            stepsList = (List<Steps>) intent.getSerializableExtra(STEPS_KEY);

            Log.i("RecipesDetails", stepsList.toString());
            recyclerView = view.findViewById(R.id.recycler_select);
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
           fragmentManager.beginTransaction().add(R.id.watch_video_container,watchRecipeStepsFragment).commit();

       }

       else {
           Intent intent = new Intent(getActivity(), WatchRecipeSteps.class);
           intent.putExtra(ADAPTER_POSITION,AdapterPosition);
           intent.putExtra(STEPS_KEY, (Serializable) stepsList);
           startActivity(intent);

       }

    }
}
