package edu.tcnj.hacktcnj2015;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import edu.tcnj.hacktcnj2015.R;

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
        videoView.setVideoURI(Uri.parse("http://media.w3.org/2010/05/sintel/trailer.mp4"));

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
}