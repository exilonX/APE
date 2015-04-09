package com.app.fragments.adapter;


import com.app.fragments.CameraFragmentCWAC;
import com.app.fragments.CameraFragmentOld;
import com.app.fragments.ChallengeFragment;
import com.app.fragments.FeedFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TabPaggerAdapter extends FragmentStatePagerAdapter {

	public TabPaggerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int item) {
		switch (item) {
		case 0:
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
