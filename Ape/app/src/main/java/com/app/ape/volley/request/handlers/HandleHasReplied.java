package com.app.ape.volley.request.handlers;

import android.view.View;
import android.widget.Button;

import com.app.ape.constants.Const;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ionel.merca on 4/22/2015.
 */
public class HandleHasReplied implements HandleJsonObjectResponse {

    private Button captureButton = null;

    public HandleHasReplied(Button captureButton) {
        this.captureButton = captureButton;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {
        try {
            String hasReplied = response.getString(Const.KEY_HAS_REPLIED);
            if (hasReplied.equals("true")) {
                captureButton.setVisibility(View.INVISIBLE);
            } else {
                captureButton.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
