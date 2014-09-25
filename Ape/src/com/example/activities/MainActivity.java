package com.example.activities;

import com.example.ape.R;
import com.example.fragments.adapter.TabPaggerAdapter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

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
			Log.d("ONCREATE", "MATA");
		
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
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(ActionBar.Tab tab1, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				tab.setCurrentItem(tab1.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		actionBar.addTab(actionBar.newTab().setText("Reply").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Challenge").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Feed").setTabListener(tabListener));
	}
	
}