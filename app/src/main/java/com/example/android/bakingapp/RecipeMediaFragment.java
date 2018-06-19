package com.example.android.bakingapp;

import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


/**
 * Created by jordanhaynes on 4/16/18.
 */

public class RecipeMediaFragment extends Fragment {

    private static final String TAG = "RecipeMediaFragment";

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    private String mediaUrl;

    public RecipeMediaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_media, container, false);

        Log.d(TAG, "Creating fragment with media player");

        playerView = view.findViewById(R.id.media_player);

        // Pass in URL here
        if (mediaUrl == null) {
            Log.d(TAG, "No media found.");
        } else {
            initializePlayer(Uri.parse(mediaUrl));
        }

        return view;
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            // Prepare the media source
            String userAgent = Util.getUserAgent(getActivity(), "Recipe Media");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }
}
