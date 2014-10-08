package com.example.ape.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.activities.FragmentSwitchListener;
import com.example.activities.MainActivity;
import com.example.ape.R;
import com.example.ape.constants.FeedConst;
import com.example.ape.helper.CommentTag;
import com.example.ape.utilsFeed.ImageLoader;
import com.example.ape.volley.request.ConstRequest;
import com.example.ape.volley.request.VolleyRequests;
import com.example.ape.volley.request.handlers.LogHandler;
import com.example.fragments.CommentFragment;
import com.example.volley.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	FragmentManager manager = null;

	public CustomAdapter(Activity a, ArrayList<HashMap<String, String>> data, FragmentManager manager) {
		this.activity = a;
		this.data = data;
		this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader = new ImageLoader(activity.getApplicationContext());
		this.manager = manager;
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
			view = inflater.inflate(R.layout.list_row2, null);

		// get each sub-view and populate with coresponding data
		TextView title = (TextView)view.findViewById(R.id.title); 
		TextView username = (TextView)view.findViewById(R.id.username); 
		TextView timestamp = (TextView)view.findViewById(R.id.timestamp);
		ImageView thumb_image=(ImageView)view.findViewById(R.id.list_image);
		ImageButton imageBut = (ImageButton)view.findViewById(R.id.addComment);
		//thumb_image.setScaleType(ScaleType.FIT_CENTER);

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);
		
		imageBut.setTag(new CommentTag(item.get(FeedConst.KEY_ID), position));
		
		// Setting all values in listview
		title.setText(item.get(FeedConst.KEY_TITLE));
		username.setText(item.get(FeedConst.KEY_USR));
		timestamp.setText(item.get(FeedConst.KEY_TIMESTAMP));
		imageLoader.DisplayImage(item.get(FeedConst.KEY_THUMBNAIL), thumb_image);

//		thumb_image.setOnClickListener(new OnImageClickListener(position));

		setOnClickComment(view);
		setOnClickApe(view);

		return view;
	}

	class OnImageClickListener implements OnClickListener {

		int _postion;

		// constructor
		public OnImageClickListener(int position) {
			this._postion = position;
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			//            Intent i = new Intent(activity, FullScreenViewActivity.class);
			//            i.putExtra("position", _postion);
			//            activity.startActivity(i);
		}

	}

	public void setOnClickComment(View view) {
		final ImageButton btn = (ImageButton)view.findViewById(R.id.addComment);
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("ONCLICK", "I just clicked this shit" + btn.getTag().toString());
					//					FragmentTransaction trans = manager.beginTransaction()
					//									.replace(R.id.feedLayout, new CommentFragment());
					//					trans.addToBackStack(null);
					//					trans.commit();

					FragmentSwitchListener activ = (FragmentSwitchListener)activity;
					Fragment commentFrag = new CommentFragment();
					activ.replaceFragment(commentFrag);

					VolleyRequests.feedRequest(new LogHandler());

				}
			});
		} else {
			Log.d("CLICK", "ESTE NULL");
		}
	}

	/**
	 * On click listener on ape image button
	 * @param view - the main view 
	 */
	public void setOnClickApe(final View view) {
		final ImageButton btn = (ImageButton)view.findViewById(R.id.increaseApes);
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("JUST CLICKED", "Clicked");
					final TextView nrApes = (TextView)view.findViewById(R.id.numberOfApes);
					int curent = Integer.parseInt(nrApes.getText().toString());
					String str = String.valueOf(curent + 1);
					nrApes.setText(str);
				}
			});
		} else {
			Log.d("CLICK", "ESTE NULL");
		}
	}

}
