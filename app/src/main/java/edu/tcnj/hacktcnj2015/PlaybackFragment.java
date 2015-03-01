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
    private VideoView videoView;
    private Button button;
    private View rootView;

    public PlaybackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_playback, container, false);

        // Initialize the video
        videoView = (VideoView) rootView.findViewById(R.id.playback_video);
        videoView.setVideoURI(Uri.parse("http://media.w3.org/2010/05/sintel/trailer.mp4"));
//        setSeekCompleteListener();

        // Attach button listeners
        Button playButton = (Button) rootView.findViewById(R.id.play_button);
        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                playVideo();
                return false;
            }
        });

        Button replayButton = (Button) rootView.findViewById(R.id.rewind_button);
        replayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                replayVideo();
                return false;
            }
        });

        return rootView;
    }

    private void setSeekCompleteListener() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        playVideo();
                    }
                });
            }
        });
    }

    public void playVideo() {
        videoView.start();
    }

    public void replayVideo() {
        if (videoView.isPlaying()) {
            videoView.seekTo(0);
        } else {
            playVideo();
        }
    }
}