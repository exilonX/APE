package com.example.ape.adapters;

import java.util.ArrayList;
import java.util.HashMap;


import com.example.ape.R;
import com.example.ape.utilsFeed.ImageLoader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

/**
 * Custom Adapter used for populating the feed view
 * @author MercaIonel
 *
 */
public class CustomAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public CustomAdapter(Activity a, ArrayList<HashMap<String, String>> data) {
		this.activity = a;
		this.data = data;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
	}


	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		// inflate the view and populate the other views inside
		if(convertView==null)
			view = inflater.inflate(R.layout.list_row, null);
		
		// get each sub-view and populate with coresponding data
		TextView title = (TextView)view.findViewById(R.id.title); 
		TextView username = (TextView)view.findViewById(R.id.username); 
		TextView timestamp = (TextView)view.findViewById(R.id.timestamp);
		ImageView thumb_image=(ImageView)view.findViewById(R.id.list_image);
		thumb_image.setScaleType(ScaleType.FIT_CENTER);
		
		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);

		// Setting all values in listview
		title.setText(item.get("title"));
		username.setText(item.get("username"));
		timestamp.setText(item.get("timestamp"));
		imageLoader.DisplayImage(item.get("thumbnail"), thumb_image);
		return view;
	}

}
