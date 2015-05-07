package com.app.ape.volley.request.multipart;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.activities.MainActivity;
import com.app.ape.constants.Const;
import com.app.ape.helper.ChallengeItem;
import com.app.ape.volley.request.ConstRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Uploading the file to server
 * */
public class UploadFileToServer extends AsyncTask<Void, Integer, String> {

    //ProgressBar progressBar;
    //TextView txtPercentage;
    private long totalSize;
    private File sourceFile;
    private ChallengeItem replyInfo = null;
    private MainActivity activity;
    private String username;
    private String url;

    public UploadFileToServer(/* ProgressBar prgBar, TextView txt, */ File file) {
        //progressBar = prgBar;
        //txtPercentage = txt;
        totalSize = 0;
        sourceFile = file;
    }

    public UploadFileToServer(ChallengeItem info, File file, MainActivity activity,
                              String username, String url) {
        this.sourceFile = file;
        this.replyInfo = info;
        this.activity = activity;
        this.username = username;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        // setting progress bar to zero
        // progressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Making progress bar visible
        /*
        progressBar.setVisibility(View.VISIBLE);

        // updating progress bar value
        progressBar.setProgress(progress[0]);

        // updating percentage value
        txtPercentage.setText(String.valueOf(progress[0]) + "%");
        */
    }

    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();
    }

    @SuppressWarnings("deprecation")
    private String uploadFile() {
        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(this.url);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });


            // Adding file data to http body
            entity.addPart("image", new FileBody(sourceFile));

            if (replyInfo == null) {
                // Extra parameters if you want to pass to server
                entity.addPart("username",
                        new StringBody("dummy"));
                entity.addPart("title", new StringBody("Dummy title"));
            } else {
                // Extra parameters if you want to pass to server
                entity.addPart("username",
                        new StringBody(replyInfo.getUsername()));
                entity.addPart("title", new StringBody(replyInfo.getTitle()));
            }

            totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString(r_entity);
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
            }

        } catch (ClientProtocolException e) {
            responseString = e.toString();
        } catch (IOException e) {
            responseString = e.toString();
        }

        return responseString;

    }

    @Override
    protected void onPostExecute(String result) {
        Log.e("TAG", "Response from server: " + result);
        activity.checkReply();

        // showing the server response in an alert dialog
        // showAlert(result);

        super.onPostExecute(result);
    }

}