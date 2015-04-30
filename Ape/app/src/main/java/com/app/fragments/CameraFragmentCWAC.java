package com.app.fragments;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.activities.MainActivity;
import com.app.ape.R;
import com.app.ape.constants.Const;
import com.app.ape.helper.ChallengeItem;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.HandleHasReplied;
import com.app.ape.volley.request.handlers.HandleJsonObjectResponse;
import com.app.ape.volley.request.handlers.ReplyHandler;
import com.app.ape.volley.request.multipart.UploadFileToServer;
import com.app.camera.src.cwac.CameraView;
import com.app.camerav9.CameraFragment;
import com.app.camera.src.cwac.CameraHost;
import com.app.camera.src.cwac.PictureTransaction;
import com.app.camera.src.cwac.SimpleCameraHost;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CameraFragmentCWAC extends CameraFragment {

    private static final String KEY_USE_FFC = "USE_FFC";

    private boolean singleShotProcessing=false;
    private Button captureButton = null;
    private Button sendButton = null;
    private Button cancelButton = null;
    private ImageView preview = null;
    private boolean shotTaken = false;
    private CameraView cameraView;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        this.editor = pref.edit();

        View content = inflater.inflate(R.layout.cwac_camera_layout, container, false);

        cameraView = (CameraView) content.findViewById(R.id.camera);
        cameraView.setHost(getHost());

        captureButton = (Button) content.findViewById(R.id.capture_button);
        sendButton = (Button) content.findViewById(R.id.send_button);
        cancelButton = (Button) content.findViewById(R.id.cancel_button);

        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!shotTaken) {
                            PictureTransaction xact = new PictureTransaction(getHost());
                            takePicture(xact);

                            shotTaken = true;
                        }
                    }
                }
        );

        // default is invisible if the user hasn't replied then make it visible
//        captureButton.setVisibility(View.INVISIBLE);

//        HashMap<String, String> params = new HashMap<>();
//
//        params.put(Const.KEY_USR, pref.getString(Const.KEY_USR_SHARED, null));
//
//        // if the user has already replied then deactivate the capture button
//        HandleHasReplied handler = new HandleHasReplied(captureButton);
//
//        VolleyRequests.jsonObjectPostRequest(ConstRequest.TAG_JSON_OBJECT,
//                ConstRequest.GET_HAS_REPLIED,
//                handler,
//                params);


        //preview = (ImageView) content.findViewById(R.id.picture_preview);

        setCameraView(cameraView);
        return content;
    }

    private void restartFragmentPreview() {
        Log.d("RestartFragment", "Intra aici in restartFragmentPreview");
        sendButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        captureButton.setVisibility(View.INVISIBLE);
        cameraView.restartPreview();
        shotTaken = false;

    }


    private void onClickSendButton(final File data) {
        sendButton.setVisibility(View.VISIBLE);
        this.sendButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Title");
                        alert.setMessage("The title of the reply");

                        final EditText input = new EditText(getActivity());
                        alert.setView(input);

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String title = input.getText().toString();
                                String username = pref.getString(Const.KEY_USR_SHARED, null);
                                String token = pref.getString(Const.KEY_TOKEN_SHARED, null);

                                ChallengeItem item = new ChallengeItem(null, username, title, null, null);

                                MainActivity activity = (MainActivity)getActivity();


                                UploadFileToServer upload = new UploadFileToServer(item, data, activity, username);
                                upload.execute();
                                restartFragmentPreview();

                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        alert.show();
                    }
                }
        );
    }

    private void onClickCancelButton() {
        cancelButton.setVisibility(View.VISIBLE);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartFragmentPreview();
            }
        });
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
        CurrentCameraHost host = new CurrentCameraHost(getActivity());
        host.useSingleShotMode();
        setHost(host);
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

    class CurrentCameraHost extends SimpleCameraHost {
        private String className = "CurrentCameraHost";
        private boolean frontFacing = false;
        private boolean singleShotMode;
        private boolean takePicture;
        private boolean autoFocus;
        private Bitmap bitmap;
        private File pictureFile;

        public CurrentCameraHost(Context _ctxt) {
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
                takePicture = true;

                Log.d(className, "Saved the image");
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                // TO DO: Do the proper scaling with inJustDecodeBonds
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length,options);

                // Save to file
                pictureFile = getOutputMediaFile();
                if (pictureFile == null){
                    Toast.makeText(getActivity(), "Image retrieval failed.", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                try {
                    // Convert bitmap back to byte array (as there might be resize operations done)
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
                    byte[] bitmapData = outputStream.toByteArray();

                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(bitmapData);
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onClickSendButton(pictureFile);
                        onClickCancelButton();
                    }
                });
            }
            else {
                super.saveImage(xact, image);
            }
        }

        /**
         * Used to return the camera File output.
         * @return
         */
        private File getOutputMediaFile(){

            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "UltimateCameraGuideApp");

            if (! mediaStorageDir.exists()){
                if (! mediaStorageDir.mkdirs()){
                    Log.d("Camera Guide", "Required media storage does not exist");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");

            return mediaFile;
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