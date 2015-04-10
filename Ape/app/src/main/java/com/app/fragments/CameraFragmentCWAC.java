package com.app.fragments;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.ape.R;
import com.app.camera.src.cwac.CameraView;
import com.app.camerav9.CameraFragment;
import com.app.camera.src.cwac.CameraHost;
import com.app.camera.src.cwac.PictureTransaction;
import com.app.camera.src.cwac.SimpleCameraHost;

import java.io.File;

public class CameraFragmentCWAC extends CameraFragment {
    /*
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View content=inflater.inflate(R.layout.fragment_native_camera, container, false);
        CameraView cameraView=(CameraView)content.findViewById(R.id.camera_preview);
        setCameraView(cameraView);
        return(content);
    }
    */

    private static final String KEY_USE_FFC = "com.commonsware.cwac.camera.demo.USE_FFC";

    private boolean singleShotProcessing=false;
    private Button captureButton = null;
    private ImageView preview = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
        View content = inflater.inflate(R.layout.cwac_camera_layout, container, false);

        CameraView cameraView = (CameraView) content.findViewById(R.id.camera);
        cameraView.setHost(getHost());

        Button captureButton = (Button) content.findViewById(R.id.capture_button);

        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        takePicture();
                    }
                }
        );

        preview = (ImageView) content.findViewById(R.id.picture_preview);

        setCameraView(cameraView);
        return content;
    }



    static CameraFragmentCWAC newInstance(boolean useFFC) {
        CameraFragmentCWAC f=new CameraFragmentCWAC();
        Bundle args=new Bundle();
        args.putBoolean(KEY_USE_FFC, useFFC);
        f.setArguments(args);
        return(f);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
        setHost(new DemoCameraHost(getActivity()));
    }



    boolean isSingleShotProcessing() {
        return(singleShotProcessing);
    }

    Contract getContract() {
        return((Contract)getActivity());
    }

    interface Contract {
        boolean isSingleShotMode();
        void setSingleShotMode(boolean mode);
    }

    class DemoCameraHost extends SimpleCameraHost {
        boolean frontFacing = false;
        boolean singleShotMode;
        boolean takePicture;
        boolean autoFocus;

        public DemoCameraHost(Context _ctxt) {
            super(_ctxt);
            singleShotMode = true;
            takePicture = false;
        }
        @Override
        public boolean useFrontFacingCamera() {
            return frontFacing;
        }
        @Override
        public boolean useSingleShotMode() {
            return true;
        }
        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            if (useSingleShotMode()) {
                singleShotProcessing=false;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        takePicture = true;
                    }
                });

                Log.d("SAVING", "Saved the image");
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length,options);
                preview.setImageBitmap(bm);
                preview.setVisibility(View.VISIBLE);
            }
            else {
                super.saveImage(xact, image);
            }
        }
        @Override
        public void autoFocusAvailable() {
            autoFocus = true;
        }
        @Override
        public void autoFocusUnavailable() {
            autoFocus = false;
        }
        @Override
        public void onCameraFail(CameraHost.FailureReason reason) {
            super.onCameraFail(reason);
            Toast.makeText(getActivity(),
                    "Sorry, but you cannot use the camera now!",
                    Toast.LENGTH_LONG).show();
        }
        @Override
        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);
            takePicture = true;
        }

//        @Override
//        public void saveImage(PictureTransaction xact, byte[] bitmap) {
//        }

    }

}