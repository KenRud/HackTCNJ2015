package edu.tcnj.hacktcnj2015;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaybackFragment extends Fragment {
    private View rootView;
    private VideoView videoView;
    private Button playButton;
    private Button replayButton;

    public PlaybackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_playback, container, false);

        // Initialize the video
        videoView = (VideoView) rootView.findViewById(R.id.playback_video);
        videoView.setVideoURI(Uri.parse("https://dl.dropboxusercontent.com/u/1187475/We%20Broke%20Quickie%20-%20Assassins%20Creed%20Unity.mp4"));

        // Play/pause button
        playButton = (Button) rootView.findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    stopVideo();
                } else {
                    playVideo();
                }
            }
        });

        // Restart button
        replayButton = (Button) rootView.findViewById(R.id.rewind_button);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replayVideo();
            }
        });

        return rootView;
    }

    public void playVideo() {
        videoView.start();
        playButton.setText(R.string.stop_button_text);
    }

    public void stopVideo() {
        videoView.pause();
        videoView.seekTo(0);
        playButton.setText(R.string.play_button_text);
    }

    public void replayVideo() {
        if (videoView.isPlaying()) {
            videoView.seekTo(0);
        } else {
            playVideo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopVideo();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopVideo();
    }
}