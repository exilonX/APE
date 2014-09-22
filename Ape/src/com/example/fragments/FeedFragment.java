package com.example.fragments;


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

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ape.R;
import com.example.ape.adapters.CustomAdapter;
import com.example.ape.utilsFeed.ItemInfo;
import com.example.volley.AppController;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedFragment extends Fragment {

	static final String KEY_USR = "username"; 			// username key
	static final String KEY_TITLE = "title";  			// title key
	static final String KEY_TIMESTAMP = "timestamp";	// timestamp key
	static final String KEY_THUMBNAIL = "thumbnail"; 	// thumbnail key

	ListView view;			// the list view with the replies
	CustomAdapter adapter;	// the custom adapter used for populating the view

	public String getJsonString() {
		// read the data from the JSON 
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

		return writer.toString();
	}

	public String getJSONLocal() {
		// Tag used to cancel the request
		String tag_json_arry = "json_array_req";

		String url = "http://api.androidhive.info/volley/person_array.json";

	
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.swipe_screen_left, container, false);
		view = (ListView) linear.getChildAt(0);
		String json = getJsonString();

		// the data that contains row element information
		ArrayList<HashMap<String, String>> data = new ArrayList<>();

		// google's GSON library used to map a JSON into a Java Object
		Gson gson = new Gson();

		// get a list of ItemInfo from the JSON
		ItemInfo[] items = gson.fromJson(json, ItemInfo[].class);

		// iterate through the items and create a new hashMap
		for (ItemInfo item : items) {
			HashMap<String, String> map = new HashMap<>();
			map.put(KEY_USR, item.getUsername());
			map.put(KEY_TITLE, item.getTitle());
			map.put(KEY_TIMESTAMP, item.getTimestamp());
			map.put(KEY_THUMBNAIL, item.getThumb_image());
			data.add(map);
		}

		// get the view, initialize the adapter, populate the view and 
		// set an onclick listener

		adapter = new CustomAdapter(getActivity(), data, getFragmentManager());
		view.setAdapter(adapter);

		// set listener on comment click

		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});

		return linear;
	}



}
