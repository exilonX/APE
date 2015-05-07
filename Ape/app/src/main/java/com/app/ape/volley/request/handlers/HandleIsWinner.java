package com.app.ape.volley.request.handlers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.app.activities.MainActivity;
import com.app.ape.constants.Const;
import com.app.ape.helper.CommentTag;
import com.app.fragments.adapter.CameraTabPaggerAdapter;
import com.app.fragments.adapter.ReplyTabPaggerAdapter;
import com.app.fragments.adapter.WinnerTabPaggerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ionel.merca on 5/7/2015.
 */
public class HandleIsWinner implements HandleJsonObjectResponse {

    private MainActivity activity;
    private FragmentManager frgManager;
    private ViewPager tab;

    public HandleIsWinner(MainActivity activity, ViewPager tab, FragmentManager frgMgr) {
        this.activity = activity;
        this.tab = tab;
        this.frgManager = frgMgr;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {

        FragmentStatePagerAdapter adapter;
        try {
            String isWinner = response.getString(Const.KEY_IS_WINNER);

            if (isWinner.equals("true")) {
                // launch winner reply fragment

                adapter = new WinnerTabPaggerAdapter(frgManager, activity);
                this.tab.setAdapter(adapter);
            } else {
                // check if the user has replied
                activity.checkReply();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
