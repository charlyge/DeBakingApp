package com.charlyge.android.debakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.charlyge.android.debakingapp.R;
import com.charlyge.android.debakingapp.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WatchRecipeStepsFragment extends Fragment {

   private SimpleExoPlayer player;
   private List<Steps> stepsList;



    private int adapterPosition;
   @BindView(R.id.video_view)
   PlayerView playerView;
    @BindView(R.id.exo_next_bt) Button bt_next;
    @BindView(R.id.exo_prev_bt) Button bt_prev;
    @BindView(R.id.full_description)TextView fullDescriptionTv;
    String videoUrl;

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View view = inflater.inflate(R.layout.fragment_watch_recipe_steps, container, false);
     ButterKnife.bind(this,view);

     if(stepsList!=null){
         fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
         videoUrl = stepsList.get(adapterPosition).getVideoURL();
         Log.i("videoUrl",videoUrl);
         if(videoUrl != null){
             initializePlayer();
         }
     }



        adapterPosition++;

      bt_next.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if(stepsList.size()-1 >= adapterPosition){

                   releasePlayer();
                   videoUrl = stepsList.get(adapterPosition).getVideoURL();
                 initializePlayer();

                  fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                  adapterPosition++;
              }
              else {
                  adapterPosition = 0;
                  fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                  releasePlayer();
                  initializePlayer();
                  adapterPosition++;
              }


          }
      });


        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapterPosition>=0){
                    if(stepsList.size()-1 >= adapterPosition){

                        releasePlayer();
                        videoUrl = stepsList.get(adapterPosition).getVideoURL();
                        initializePlayer();

                        fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                        adapterPosition--;
                    }
                }

                else {
                    adapterPosition = 12;
                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    releasePlayer();
                    initializePlayer();
                    adapterPosition--;
                }


            }
        });

      return view;
    }


    @Override
    public void onStop() {
        super.onStop();
       releasePlayer();
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
        player.seekTo(0, 0);
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }

private void releasePlayer(){
        if(player!=null){
            player.stop();
            player.release();
        }

    player = null;

}
}
