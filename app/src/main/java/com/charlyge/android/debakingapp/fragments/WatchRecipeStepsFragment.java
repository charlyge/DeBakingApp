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
import android.widget.ImageView;
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
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WatchRecipeStepsFragment extends Fragment {
    private boolean istwoPane;
    private static final String ADAPTER_POSITION_KEY = "adapetetr";
    private SimpleExoPlayer player;
    private List<Steps> stepsList;


    private int adapterPosition;
    @BindView(R.id.no_video)
    ImageView noVideoImage;
    @BindView(R.id.video_view)
    PlayerView playerView;
    @BindView(R.id.exo_next_bt)
    Button bt_next;
    @BindView(R.id.exo_prev_bt)
    Button bt_prev;
    @BindView(R.id.full_description)
    TextView fullDescriptionTv;
    String videoUrl;

    public void setIsIstwoPane(boolean istwoPane) {
        this.istwoPane=istwoPane;

    }

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
        outState.putInt(ADAPTER_POSITION_KEY,adapterPosition);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watch_recipe_steps, container, false);
        ButterKnife.bind(this, view);
        noVideoImage.setVisibility(View.GONE);
        if(savedInstanceState!=null){

            adapterPosition = savedInstanceState.getInt(ADAPTER_POSITION_KEY);
        }
        if (stepsList != null) {
            fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
            videoUrl = stepsList.get(adapterPosition).getVideoURL();
            Log.i("videoUrl", videoUrl);
            if (videoUrl != null) {
                playerView.setVisibility(View.VISIBLE);
                noVideoImage.setVisibility(View.GONE);
                initializePlayer();
            }
            else {
                playerView.setVisibility(View.GONE);
                noVideoImage.setVisibility(View.VISIBLE);

            }
        }
        if(istwoPane){
            bt_next.setVisibility(View.GONE);
            bt_prev.setVisibility(View.GONE);
        }

        adapterPosition++;

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(adapterPosition>=0){
                if ((stepsList.size() - 1 >= adapterPosition) && (adapterPosition !=-1)) {
                    Log.i("Position", "Entering next bt is " + adapterPosition);
                    // Log.i("Position", "STEPLISTSIZE  is " + stepsList.size());
                    releasePlayer();
                    videoUrl = stepsList.get(adapterPosition).getVideoURL();
                    initializePlayer();

                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    adapterPosition++;
                    Log.i("Position", "else next bt is " + adapterPosition);
                }

                //   }

                else {
                    Log.i("Position", "else next bt is " + adapterPosition);
                    adapterPosition = 0;
                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    releasePlayer();
                    initializePlayer();
                    adapterPosition++;
                    Log.i("Position", "in Else next bt is " + adapterPosition);
                    // Log.i("Position", "STEPLISTSIZE  is " + stepsList.size());
                    Log.i("Position", "else next bt is " + adapterPosition);
                }


            }
        });


        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((stepsList.size() - 1 >= adapterPosition) && (adapterPosition !=-1)) {
                    // adapterPosition--;
                    Log.i("Position", "Entering previous bt is " + adapterPosition);
                    // Log.i("Position", "STEPLISTSIZE  is " + stepsList.size());
                    releasePlayer();
                    videoUrl = stepsList.get(adapterPosition).getVideoURL();
                    initializePlayer();

                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    adapterPosition--;
                    Log.i("Position", "else prev bt is " + adapterPosition);
                }
                else {
                    adapterPosition = stepsList.size() - 1;
                    Log.i("Position", "else prev bt is " + adapterPosition);
                    //  Log.i("Position", "STEPLISTSIZE  is " + stepsList.size());
                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    releasePlayer();
                    initializePlayer();
                    adapterPosition--;
                    Log.i("Position", "else prev bt is " + adapterPosition);
                }


            }
        });

        return view;
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

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
        }

        player = null;

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 && stepsList!=null) {
            noVideoImage.setVisibility(View.GONE);
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (player != null) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && stepsList!=null) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && stepsList!=null) {
            initializePlayer();
        }
    }
}
