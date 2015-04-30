package com.app.ape.volley.request.handlers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

import com.app.activities.FragmentSwitchListener;
import com.app.ape.constants.Const;
import com.app.ape.helper.CommentTag;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.ReplyFragment;
import com.app.fragments.adapter.CameraTabPaggerAdapter;
import com.app.fragments.adapter.ReplyTabPaggerAdapter;
import com.app.fragments.adapter.TabPaggerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ionel.merca on 4/22/2015.
 */
public class HandleHasReplied implements HandleJsonObjectResponse {

    private Activity activity = null;
    private ViewPager tab = null;
    private FragmentManager fragMgr = null;

    public HandleHasReplied(Activity activity, ViewPager tab, FragmentManager fragMgr) {
        this.activity = activity;
        this.tab = tab;
        this.fragMgr = fragMgr;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {
        // should lauch the CameraFragment and the ReplyFragment
        this.tab.removeAllViews();
        this.tab.setAdapter(null);

        // remove the other 3 fragments
        // TODO research to see if it is a memory leak not to remove the fragments
        // from the fragment manager
        FragmentStatePagerAdapter adapter;
        try {
            String hasReplied = response.getString(Const.KEY_HAS_REPLIED);

            if (hasReplied.equals("true")) {
                // launch Reply Fragment
                JSONObject reply = response.getJSONObject("reply");
                adapter = new ReplyTabPaggerAdapter(fragMgr, activity, new CommentTag(reply));
            } else {
                // launch camera adapter
                adapter = new CameraTabPaggerAdapter(fragMgr, activity);
            }

            this.tab.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
