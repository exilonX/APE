package com.app.ape.volley.request.handlers;

import android.app.Activity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ionel.merca on 4/27/2015.
 */
public class HandleRegisterGCM implements  HandleJsonObjectResponse {

    private Activity activity = null;

    public HandleRegisterGCM(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {
        String message = null;
        try {
            message = response.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast toast = Toast.makeText(this.activity, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
