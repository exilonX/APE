package com.app.fragments.adapter;


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

	public TabPaggerAdapter(FragmentManager fm, Activity activity) {
		super(fm);
        this.activity = activity;
        this.pref = this.activity.getApplicationContext().getSharedPreferences("MyPref", 0);
	}

	@Override
	public Fragment getItem(int item) {

		switch (item) {
		case 0:
			return new LoadingFragment();

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
