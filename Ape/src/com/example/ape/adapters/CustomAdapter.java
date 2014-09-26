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
import com.example.ape.utilsFeed.ImageLoader;
import com.example.fragments.CommentFragment;
import com.example.volley.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
		imageBut.setTag(position);
		//thumb_image.setScaleType(ScaleType.FIT_CENTER);
		
		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);

		// Setting all values in listview
		title.setText(item.get("title"));
		username.setText(item.get("username"));
		timestamp.setText(item.get("timestamp"));
		//imageLoader.DisplayImage(item.get("thumbnail"), thumb_image);
		
		setOnClickComment(view);
		
		return view;
	}
	
	public String getJSONLocal() {
		// Tag used to cancel the request
		String tag_json_arry = "json_array_req";

		String url = "http://10.0.2.2:8080/api/feed";

		JsonArrayRequest req = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Log.d("TAG", response.toString());       
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("TAG", "Error: " + error.getMessage());
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, tag_json_arry);
		return null;
	}
	
	public void setOnClickComment(View view) {
		final ImageButton btn = (ImageButton)view.findViewById(R.id.addComment);
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.d("ONCLICK", "I just clicked this shit" + btn.getTag());
//					FragmentTransaction trans = manager.beginTransaction()
//									.replace(R.id.feedLayout, new CommentFragment());
//					trans.addToBackStack(null);
//					trans.commit();
					
					FragmentSwitchListener activ = (FragmentSwitchListener)activity;
					Fragment commentFrag = new CommentFragment();
					activ.replaceFragment(commentFrag);
					
					getJSONLocal();
				}
			});
		} else {
			Log.d("CLICK", "ESTE NULL");
		}
	}

}
