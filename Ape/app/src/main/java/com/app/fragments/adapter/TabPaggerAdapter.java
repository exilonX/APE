package com.app.fragments.adapter;


import com.app.ape.constants.Const;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.HandleHasReplied;
import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.CameraFragmentOld;
import com.app.fragments.ChallengeFragment;
import com.app.fragments.FeedFragment;
import com.app.fragments.LoadingFragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;


public class TabPaggerAdapter extends FragmentStatePagerAdapter {

    private Activity activity = null;
    private SharedPreferences pref = null;
    private boolean hasReplied = false;

	public TabPaggerAdapter(FragmentManager fm, Activity activity) {
		super(fm);
        this.activity = activity;
        this.pref = this.activity.getApplicationContext().getSharedPreferences("MyPref", 0);
	}

	@Override
	public Fragment getItem(int item) {

		switch (item) {
		case 0:
            checkHasReplied();;
            // return CameraFragment or Reply Fragment Based on the reply
			return new LoadingFragment();

		case 1:
			return new ChallengeFragment();
			
		case 2:
			return new FeedFragment();
		}
		return null;
	}

    private void checkHasReplied() {
        HashMap<String, String> params = new HashMap<>();

        params.put(Const.KEY_USR, pref.getString(Const.KEY_USR_SHARED, null));

        // if the user has already replied then deactivate the capture button
        HandleHasReplied handler = new HandleHasReplied(this.activity);

        VolleyRequests.jsonObjectPostRequest(ConstRequest.TAG_JSON_OBJECT,
                ConstRequest.GET_HAS_REPLIED,
                handler,
                params);
    }



	@Override
	public int getCount() {
		return 3;
	}

}
