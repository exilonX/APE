package com.app.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.activities.MainActivity;
import com.app.ape.R;
import com.app.ape.constants.Const;
import com.app.ape.helper.ChallengeItem;
import com.app.ape.volley.request.multipart.UploadFileToServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ionel.merca on 5/28/2015.
 */
public class CameraFragmentIntent extends Fragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final String KEY_URL = "URL";

    private String url;

    private ImageButton button;
    private ImageView imageView;
    private Button sendButton;
    private Button cancelButton;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;
    private Bitmap bitmap;

    public static CameraFragmentIntent newInstance(String uploadURL) {
        CameraFragmentIntent f = new CameraFragmentIntent();

        Bundle args=new Bundle();
        args.putString(KEY_URL, uploadURL);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.camera_layout, container, false);

        button = (ImageButton)rootView.findViewById(R.id.btnCameraOpen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity)getActivity();
                activity.callCameraIntent();
            }
        });

        sendButton = (Button)rootView.findViewById(R.id.send_button);
        cancelButton = (Button)rootView.findViewById(R.id.cancel_button);

        imageView = (ImageView) rootView.findViewById(R.id.imagePreview);

        this.pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        this.editor = pref.edit();

        Bundle arguments = getArguments();
        this.url = arguments.getString(KEY_URL);


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // convert byte array to Bitmap

            bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);

            // do whatever with the bitmap
            imageView.setImageBitmap(bitmap);
            File image = getOutputMediaFile();
            try {
                FileOutputStream fos = new FileOutputStream(image);
                fos.write(byteArray);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            onClickCancelButton();
            onClickSendButton(image);
        }
    }


    private void restartFragmentPreview() {
        Log.d("RestartFragment", "Intra aici in restartFragmentPreview");
        sendButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        // clear image view
    }

    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ApePhotos");

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

                                UploadFileToServer upload = new UploadFileToServer(item, data, activity, username, url);
                                upload.execute();
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



}