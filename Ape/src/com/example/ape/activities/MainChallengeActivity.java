package com.example.ape.activities;

import com.example.ape.R;
import com.example.ape.R.layout;
import com.example.ape.activities.SimpleGestureFilter.SimpleGestureListener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainChallengeActivity extends Activity implements SimpleGestureListener{
	private SimpleGestureFilter detector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.swipe_screen);

		// Detect touched area 
		detector = new SimpleGestureFilter(this,this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onSwipe(int direction) {
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT : 
			Intent intent = new Intent(this, ReplyCameraActivity.class);
//			Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1 ,R.anim.animation2).toBundle();
//			startActivity(intent, bndlanimation);
			startActivity(intent);
			break;

		case SimpleGestureFilter.SWIPE_LEFT :
			Intent intent1 = new Intent(this, FeedActivity.class);
			startActivity(intent1);
			break;
		}
	}

	@Override
	public void onDoubleTap() {
		Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

}