package com.app.ape.volley.request.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.ape.adapters.CustomAdapter;
import com.app.ape.constants.Const;
import com.app.ape.helper.ItemInfo;
import com.google.gson.Gson;

public class PopulateFeedPaginatedHandler implements HandleJsonObjectResponse {

	private LayoutInflater 	inflater;
	private ViewGroup 		container;
	private Bundle 			savedInstanceState;
	private FragmentManager fragmetManager;
	private ListView 		view;
	private Fragment 		fragment;
	private CustomAdapter 	adapter;
	private Activity		activity;
	private LinearLayout 	linear;
	ArrayList<HashMap<String, String>> data;

	public LinearLayout getLinearLayout() {
		return linear;
	}

	public PopulateFeedPaginatedHandler(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstances, FragmentManager fragMang, ListView view, 
			CustomAdapter adapter, Activity activity, LinearLayout linear, 
			Fragment fragment) {
		this.inflater 			= inflater;
		this.container 			= container;
		this.savedInstanceState = savedInstances;
		this.fragmetManager 	= fragMang;
		this.view 				= view;
		this.adapter 			= adapter;
		this.activity			= activity;
		this.linear 			= linear;
		this.fragment 			= fragment;
		this.data				= new ArrayList<>();
	}


	public FragmentManager getFragmetManager() {
		return fragmetManager;
	}

	public void setFragmetManager(FragmentManager fragmetManager) {
		this.fragmetManager = fragmetManager;
	}

	public Bundle getSavedInstanceState() {
		return savedInstanceState;
	}

	public void setSavedInstanceState(Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
	}

	@Override
	public void handleJsonObjectResponse(JSONObject response) {
		getFragmetManager().beginTransaction().add(fragment, "FeedFragment");

		// the data that contains row element information
		this.data = new ArrayList<>();

		// google's GSON library used to map a JSON into a Java Object
		Gson gson = new Gson();
		JSONArray result;
		try {
			result = response.getJSONArray(Const.FEED_ARRAY_KEY);
			// get a list of ItemInfo from the JSON
			
			ItemInfo[] items = gson.fromJson(result.toString(), ItemInfo[].class);
			// iterate through the items and create a new hashMap
			for (ItemInfo item : items) {
				HashMap<String, String> map = new HashMap<>();
				map.put(Const.KEY_ID, item.get_id());
				map.put(Const.KEY_USR, item.getUsername());
				map.put(Const.KEY_TITLE, item.getTitle());
				map.put(Const.KEY_TIMESTAMP, item.getTimestamp());
				map.put(Const.KEY_THUMBNAIL, item.getThumb_image());
                map.put(Const.KEY_NOOFLIKES, item.getNoOfLikes().toString());
                map.put(Const.KEY_ISMYLIKE, item.isMyLike().toString());
				data.add(map);
			}
			
			// get the view, initialize the adapter, populate the view and 
			// set an onclick listener
			int currentPosition = view.getFirstVisiblePosition();
			if (adapter == null)
				adapter = new CustomAdapter(activity, data, getFragmetManager());
			else {
				// add data to the listview and notify dataset changed 
				adapter.addData(data);
				adapter.notifyDataSetChanged();
			}
			view.setAdapter(adapter);
			
			view.setSelectionFromTop(currentPosition+1, 0);
		} catch (JSONException e) {
			Log.d("POPULATE_FEED", "Result doesn't have a good format");
			e.printStackTrace();
		}
		
	}

}
