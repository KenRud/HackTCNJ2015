package edu.tcnj.hacktcnj2015;


import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private FrameLayout previewFrame;
    private View rootView;
    private CameraPreview cameraPreview;
    private Camera camera;

    public ChallengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_challenge, container, false);
        cameraPreview = new CameraPreview(rootView.getContext());
        previewFrame = (FrameLayout) rootView.findViewById(R.id.preview_frame);
        previewFrame.addView(cameraPreview);

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

    public void prepareCamera() {
        camera = openCamera();
        cameraPreview.setCamera(camera);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAudio();
        prepareCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAndFreeMediaPlayer();
        stopPreviewAndFreeCamera();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopAndFreeMediaPlayer();
        stopPreviewAndFreeCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAndFreeMediaPlayer();
        stopPreviewAndFreeCamera();
    }

    private Camera openCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return cam;
    }

    private void stopAndFreeMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void stopPreviewAndFreeCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
