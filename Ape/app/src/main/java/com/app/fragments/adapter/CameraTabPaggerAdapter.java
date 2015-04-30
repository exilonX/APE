package com.app.fragments.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.activities.MainActivity;
import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.ChallengeFragment;
import com.app.fragments.FeedFragment;
import com.app.fragments.LoadingFragment;

/**
 * Created by ionel.merca on 4/30/2015.
 */
public class CameraTabPaggerAdapter extends FragmentStatePagerAdapter {

    private Activity activity = null;

    public CameraTabPaggerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int item) {
        switch (item) {
            case 0:
//            checkHasReplied();
                // return CameraFragment or Reply Fragment Based on the reply
                return new CameraFragmentCWAC();

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
