package edu.tcnj.hacktcnj2015;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private List<Camera.Size> supportedPreviewSizes;

    public CameraPreview(Context context) {
        super(context);

        surfaceView = new SurfaceView(context);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        addView(surfaceView);
    }

    public void setCamera(Camera camera) {
        if (this.camera == camera) {
            return;
        }

        this.camera = camera;

        if (camera != null) {
            List<Camera.Size> localSizes = camera.getParameters().getSupportedPreviewSizes();
            supportedPreviewSizes = localSizes;
//            Camera.Parameters parameters = camera.getParameters();
//            parameters.setPreviewSize(supportedPreviewSizes.get(0).width, supportedPreviewSizes.get(0).height);

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

            requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = camera.getParameters();
//        parameters.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
        // TODO FIx this
        requestLayout();
        camera.setParameters(parameters);

        // Important: Call startPreview() to start updating the preview surface.
        // Preview must be started before you can take a picture.
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}