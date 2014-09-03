package com.example.ape;


import com.example.ape.SimpleGestureFilter.SimpleGestureListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class FeedActivity extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_screen_left);
		
		// Detect touched area 
		detector = new SimpleGestureFilter(this,this);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}
	
	@Override
	public void onSwipe(int direction) {
		String str = "";

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT : 
			Intent intent = new Intent(this, MainChallengeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
	}

	@Override
	public void onDoubleTap() {
		Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}
	
}
