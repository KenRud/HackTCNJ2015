package edu.tcnj.hacktcnj2015;

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

    public PlaybackFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playback, container, false);

        // Initialize the video
        videoView = (VideoView) rootView.findViewById(R.id.playback_video);
        videoView.setVideoURI(Uri.parse("http://media.w3.org/2010/05/sintel/trailer.mp4"));

        // PlaybackActivity button
        button = (Button) rootView.findViewById(R.id.play_button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                videoView.start();
                return false;
            }
        });

        return rootView;
    }
}