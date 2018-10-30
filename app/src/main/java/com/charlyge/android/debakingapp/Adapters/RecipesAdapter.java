package com.charlyge.android.debakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlyge.android.debakingapp.R;
import com.charlyge.android.debakingapp.model.Ingredients;
import com.charlyge.android.debakingapp.model.Recipes;
import com.charlyge.android.debakingapp.model.Steps;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.myViewHolder> {
    private List<Recipes> recipesList;

   private ItemClickListener itemClickListener;
   public RecipesAdapter( ItemClickListener itemClickListener){
    this.itemClickListener =itemClickListener;
   }

    public interface ItemClickListener{
        void onItemClicked(List<Steps> steps, List<Ingredients> ingredients);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       Context context = parent.getContext();
       View view = LayoutInflater.from(context).inflate(R.layout.recipes_list_item,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      Recipes recipes = recipesList.get(holder.getAdapterPosition());
      holder.servingstv.setText(recipes.getServings());
      holder.nametv.setText(recipes.getName());
      String image = recipes.getImage();
      if(!image.isEmpty()){
          Picasso.get().load(image).error(R.drawable.recipes).placeholder(R.drawable.recipes).into(holder.imageView);
      }




    }

    @Override
    public int getItemCount() {
       if (recipesList ==null){
           return 0;
       }
        return recipesList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
   TextView nametv,servingstv;
   ImageView imageView;

        public myViewHolder(View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.tv_name);
            servingstv =itemView.findViewById(R.id.tv_servings);
            imageView = itemView.findViewById(R.id.tv_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Recipes recipes = recipesList.get(getAdapterPosition());
            itemClickListener.onItemClicked(recipes.getStepsArrayList(),recipes.getIngredientsArrayList());

        }
    }

    public void setRecipesList(List<Recipes> recipesList) {
        this.recipesList = recipesList;
        notifyDataSetChanged();
    }
}
