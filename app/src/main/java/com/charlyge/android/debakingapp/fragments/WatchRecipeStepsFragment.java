package com.charlyge.android.debakingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.debakingapp.R;
import com.charlyge.android.debakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;


public class WatchRecipeStepsFragment extends Fragment {

  // private PlayerView playerView;
   //private SimpleExoPlayer player;
   private TextView fullDescriptionTv;
   private List<Steps> stepsList;
   private Button bt_next;
   private int adapterPosition;

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public void setStepsArrayList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public WatchRecipeStepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_watch_recipe_steps, container, false);
    // playerView = view.findViewById(R.id.simple_exoplayer);
     bt_next = view.findViewById(R.id.exo_next_bt);
     fullDescriptionTv = view.findViewById(R.id.full_description);
     fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));

     // initializePlayer();
        Toast.makeText(getActivity(),"Position is "+ adapterPosition,Toast.LENGTH_LONG).show();

        adapterPosition++;
        Toast.makeText(getActivity(),"Position is "+ adapterPosition,Toast.LENGTH_LONG).show();
      bt_next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if(stepsList.size()-1 >= adapterPosition){
                  //Will do for video Url later

                  String videoUrl = stepsList.get(adapterPosition).getVideoURL();
                  Toast.makeText(getActivity(),"Position is "+ adapterPosition,Toast.LENGTH_LONG).show();
                  fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                  adapterPosition++;
                  Toast.makeText(getActivity(),"Position after clicking "+ adapterPosition,Toast.LENGTH_LONG).show();
              }
              else {
                  adapterPosition = 0;
                  fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                  adapterPosition++;
              }


          }
      });

      return view;
    }



  /*  private void initializePlayer(){
      player = ExoPlayerFactory.newSimpleInstance(getActivity(),new DefaultRenderersFactory(getActivity()),new DefaultTrackSelector(),
              new DefaultLoadControl());

      playerView.setPlayer(player);



      //Prepare Media Source
        player.setPlayWhenReady(true);
        player.seekTo(0, 0);

    }

    */

}
