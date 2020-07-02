package com.example.song_player;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;

    //Overriding onCreate method(first method to be called) to create the activity
    //and initialise all the variable to their respective views in layout file and
    //adding listeners to required views
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //method to fill the activity that is launched with  the activity_player.xml layout file
        setContentView(R.layout.song);

        //getting youtube player view object
        playerView = (YouTubePlayerView)findViewById(R.id.player_view);

        //initialize method of YouTubePlayerView used to play videos and control video playback
        //arguments are a valid API key that is enabled to use the YouTube Data API v3 service
        //and YouTubePlayer.OnInitializedListener object or the callbacks that will be invoked
        //when the initialization succeeds or fails
        //as in this case the activity implements OnInitializedListener
        playerView.initialize(YoutubeConnector.KEY, this);

        //initialising various descriptive data in the UI and player


        //setting text of each View form UI
        //setText method used to change the text shown in the view
        //getIntent method returns the object of current Intent
        //of which getStringExtra returns the string which was passed while calling the intent
        //by using the name that was associated during call

    }

    //method called if the YouTubePlayerView fails to initialize
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_LONG).show();
    }

    //method called if the YouTubePlayerView succeeds to load the video
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean restored) {

        //initialise the video player only if it is not restored or is not yet set
        if(!restored){

            //cueVideo takes video ID as argument and initialise the player with that video
            //this method just prepares the player to play the video
            //but does not download any of the video stream until play() is called
            player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
        }
    }
}