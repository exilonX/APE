package com.app.activities;

import com.app.ape.R;
import com.app.fragments.adapter.TabPaggerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;


public class MainActivity extends FragmentActivity {

	ViewPager tab;
    TabPaggerAdapter tabAdapter;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// remove the title and icon of the app
		actionBar = getActionBar();
		if (actionBar == null)
			Log.d("onCreate", "Action bar cannot be created.");
		
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		tabAdapter = new TabPaggerAdapter(getSupportFragmentManager());
		
		tab = (ViewPager)findViewById(R.id.pager);
		tab.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar = getActionBar();
						actionBar.setSelectedNavigationItem(position);
					}
				});
		tab.setAdapter(tabAdapter);
		
		actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab currentTab, android.app.FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab currentTab, android.app.FragmentTransaction ft) {
                ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
                viewPager.setCurrentItem(currentTab.getPosition());
            }

			@Override
			public void onTabUnselected(Tab currentTab, android.app.FragmentTransaction ft) {
			}
		};
		
		actionBar.addTab(actionBar.newTab().setText("Reply").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Challenge").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Feed").setTabListener(tabListener));
	}
	
}
