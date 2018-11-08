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
import com.charlyge.android.debakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectRecipeDetailAdapter extends RecyclerView.Adapter<SelectRecipeDetailAdapter.myViewHolder> {

    private final List<Steps> stepsList;
    private final ClickedListener mClickListener;
    public interface ClickedListener{
        void onItemClicked(int AdapterPosition);
    }
    public SelectRecipeDetailAdapter( List<Steps> stepsList, ClickedListener mClickListener){
        this.mClickListener = mClickListener;
        this.stepsList = stepsList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.select_recipe_detail_list_item,parent,false);
        return new myViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    Steps steps = stepsList.get(holder.getAdapterPosition());
    holder.shortDescriptionTv.setText(steps.getShortDescription());
//        Picasso.get().load(steps.getThumbnailURL()).placeholder(R.drawable.recipes).into(holder.thumbNailImag);
    }

    @Override
    public int getItemCount() {
        if(stepsList==null){
            return 0;
        }
        return stepsList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.thumb_nail_img)ImageView thumbNailImag;
        @BindView(R.id.short_description) TextView shortDescriptionTv;


        myViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
