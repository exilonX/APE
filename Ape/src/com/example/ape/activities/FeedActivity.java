package com.example.ape.activities;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.ape.R;
import com.example.ape.R.id;
import com.example.ape.R.layout;
import com.example.ape.R.raw;
import com.example.ape.activities.SimpleGestureFilter.SimpleGestureListener;
import com.example.ape.adapters.CustomAdapter;
import com.example.ape.utilsFeed.ItemInfo;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FeedActivity extends Activity implements SimpleGestureListener {
	private SimpleGestureFilter detector;

	static final String KEY_USR = "username"; // parent node
	static final String KEY_TITLE = "title";
	static final String KEY_TIMESTAMP = "timestamp";
	static final String KEY_THUMBNAIL = "thumbnail";

	ListView view;
	CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// this layout is a list view
		setContentView(R.layout.swipe_screen_left);

		ArrayList<HashMap<String, String>> data = new ArrayList<>();
		Gson gson = new Gson();

		InputStream inStream = getResources().openRawResource(R.raw.input);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String jsonString = writer.toString();

		ItemInfo[] items = gson.fromJson(jsonString, ItemInfo[].class);
		for (ItemInfo item : items) {
			HashMap<String, String> map = new HashMap<>();
			map.put(KEY_USR, item.getUsername());
			map.put(KEY_TITLE, item.getTitle());
			map.put(KEY_TIMESTAMP, item.getTimestamp());
			map.put(KEY_THUMBNAIL, item.getThumb_image());
			data.add(map);
		}

		view = (ListView)findViewById(R.id.list);

		adapter = new CustomAdapter(this, data);
		view.setAdapter(adapter);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}
			
		});


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
