package edu.tcnj.hacktcnj2015;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private View rootView;

    public ChallengeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_challenge, container, false);

        // Start Challenge listener
        Button startChallengeButton = (Button) rootView.findViewById(R.id.start_challenge_button);
        startChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        return rootView;
    }

    public void loadAudio() {
        // Initiate and prepare the audio player
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(rootView.getContext(), R.raw.test);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
