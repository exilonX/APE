package com.example.ape.activities;

import com.example.ape.R;

import com.example.ape.activities.SimpleGestureFilter.SimpleGestureListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * This activity contains the current challenge and represents the main 
 * access point of the application
 * @author MercaIonel
 *
 */
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

	@Override
	public void onSwipe(int direction) {

		switch (direction) {
		// on swipe right the screen is switched to the Reply camera activity
		case SimpleGestureFilter.SWIPE_RIGHT : 
			Intent intent = new Intent(this, ReplyCameraActivity.class);
//			Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1 ,R.anim.animation2).toBundle();
//			startActivity(intent, bndlanimation);
			startActivity(intent);
			break;
		
		// on swipe left the feed activity that contains a list of items
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