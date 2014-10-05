package com.example.activities;



import com.example.ape.R;
import com.example.ape.adapters.FullScreenImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class FullScreenViewActivity extends Activity{

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fullscreen_image);
//
//		Intent i = getIntent();
//		int position = i.getIntExtra("position", 0);
//
//		adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
//				utils.getFilePaths());
//
//		viewPager.setAdapter(adapter);
//
//		// displaying selected image first
//		viewPager.setCurrentItem(position);
	}
}