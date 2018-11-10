package com.charlyge.android.debakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WatchRecipeStepsFragment extends Fragment {
    private static final String ADAPTER_POSITION_KEY = "ADAPTERPOSIOTION";
    private boolean istwoPane;
    private static final String POSITION_KEY = "posk";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready";
    private SimpleExoPlayer player;
    private List<Steps> stepsList;
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String STEP_ID_LIST = "steplist";
    private int currentWindow =0;
    private long mCurrentPosition = 0;
    private boolean mPlayWhenReady = true;
    private int adapterPosition;
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
        this.istwoPane = istwoPane;

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
        outState.putLong(POSITION_KEY, mCurrentPosition);
        outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
        //  outState.putParcelableArrayList(STEP_ID_LIST, (ArrayList<? extends Parcelable>) stepsList);
        outState.putInt(ADAPTER_POSITION_KEY,adapterPosition);
        //  outState.putParcelableArrayList(STEP_ID_LIST, (ArrayList<? extends Parcelable>) stepsList);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_watch_recipe_steps, container, false);
        ButterKnife.bind(this, view);
        Log.i("cureentPlayerPosition", String.valueOf(mCurrentPosition));
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getLong(POSITION_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
            adapterPosition = savedInstanceState.getInt(ADAPTER_POSITION_KEY);
            Log.i("CurrentPositionnotnull", String.valueOf(mCurrentPosition));
            Log.i("AdapterPosition", String.valueOf(adapterPosition));
        }
        if (stepsList != null) {
            fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
            videoUrl = stepsList.get(adapterPosition).getVideoURL();
            Log.i("videoUrl", videoUrl);
            if (videoUrl != null) {
                initializePlayer();
            }
        }
        if (istwoPane) {
            bt_next.setVisibility(View.GONE);
            bt_prev.setVisibility(View.GONE);
        }

        adapterPosition++;

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(adapterPosition>=0){
                if ((stepsList.size() - 1 >= adapterPosition) && (adapterPosition != -1)) {
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

                if ((stepsList.size() - 1 >= adapterPosition) && (adapterPosition != -1)) {
                    // adapterPosition--;
                    Log.i("Position", "Entering previous bt is " + adapterPosition);
                    // Log.i("Position", "STEPLISTSIZE  is " + stepsList.size());
                    releasePlayer();
                    videoUrl = stepsList.get(adapterPosition).getVideoURL();
                    initializePlayer();

                    fullDescriptionTv.setText((stepsList.get(adapterPosition).getDescription()));
                    adapterPosition--;
                    Log.i("Position", "else prev bt is " + adapterPosition);
                } else {
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
    // Initialize exoplayer
    public void initializePlayer(){

        if(player == null){

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            // Bind the player to the view.
            playerView.setPlayer(player);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));

            MediaSource mediaSource =  new ExtractorMediaSource.Factory(
                    new DefaultHttpDataSourceFactory(userAgent)).
                    createMediaSource(Uri.parse(videoUrl));

            if (mCurrentPosition != C.TIME_UNSET) {
                player.seekTo(mCurrentPosition);
            }
            // Prepare the player with the source.
            player.prepare(mediaSource,true,false);
            player.setPlayWhenReady(mPlayWhenReady);
        }
    }

    // Release player
    private void releasePlayer() {
        if (player != null) {
            if (player != null) {
                mPlayWhenReady = player.getPlayWhenReady();
                currentWindow = player.getCurrentWindowIndex();
                mCurrentPosition = player.getCurrentPosition();
            }
            player.stop();
            player.release();
            player = null;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 && stepsList != null) {
            releasePlayer();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && stepsList != null) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && stepsList != null) {
            initializePlayer();
        }
    }
}