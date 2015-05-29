package com.app.fragments.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.ape.volley.request.ConstRequest;
import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.CameraFragmentIntent;
import com.app.fragments.ChallengeFragment;
import com.app.fragments.FeedFragment;

/**
 * Created by ionel.merca on 5/6/2015.
 */
public class WinnerTabPaggerAdapter extends FragmentStatePagerAdapter {

    private Activity activity = null;

    public WinnerTabPaggerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // return CameraFragment or Reply Fragment Based on the reply
                return CameraFragmentIntent.newInstance(ConstRequest.POST_CHALLENGE);
            case 1:
                return new ChallengeFragment();

            case 2:
                return new FeedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
