package com.app.ape.volley.request.handlers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.activities.FragmentSwitchListener;
import com.app.ape.constants.Const;
import com.app.ape.helper.CommentTag;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.ReplyFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ionel.merca on 4/22/2015.
 */
public class HandleHasReplied implements HandleJsonObjectResponse {
    Activity activity = null;

    public HandleHasReplied(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {
        // should lauch the CameraFragment and the ReplyFragment
        FragmentSwitchListener activ = (FragmentSwitchListener)activity;
        try {
            String hasReplied = response.getString(Const.KEY_HAS_REPLIED);

            if (hasReplied.equals("true")) {
                // launch Reply Fragment
                JSONObject reply = response.getJSONObject("reply");
                CommentTag tag = new CommentTag(reply);
                Fragment replyFragment = ReplyFragment.newInstance(tag);
                activ.replaceFragment(replyFragment);
            } else {
                // launch Camera Fragmentty(View.VISIBLE);
                CameraFragmentCWAC cameraFragment = new CameraFragmentCWAC();
                activ.replaceFragment(cameraFragment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
