package com.app.ape.volley.request.handlers;

import java.util.LinkedList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.ape.adapters.CustomAdapter;
import com.app.ape.constants.Const;
import com.app.ape.helper.ItemInfo;
import com.google.gson.Gson;

public class PopulateFeedHandler implements HandleJsonArrayResponse {

	private LayoutInflater 	inflater;
	private ViewGroup 		container;
	private Bundle 			savedInstanceState;
	private FragmentManager fragmetManager;
	private ListView 		view;
	private Fragment 		fragment;
	private CustomAdapter 	adapter;
	private Activity		activity;
	private LinearLayout 	linear;
	
	public LinearLayout getLinearLayout() {
		return linear;
	}

	public PopulateFeedHandler(LayoutInflater inflater, ViewGroup container,
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
	}

	@Override
	public void handleJsonArrayResp(JSONArray resp) {
//		this.linear = (LinearLayout) inflater.inflate(
//				R.layout.swipe_screen_left, container, false);
//		view = (ListView) linear.getChildAt(0);

		getFragmetManager().beginTransaction().add(fragment, "FeedFragment");

		// the data that contains row element information
		LinkedList<HashMap<String, String>> data = new LinkedList<>();

		// google's GSON library used to map a JSON into a Java Object
		Gson gson = new Gson();

		// get a list of ItemInfo from the JSON
		ItemInfo[] items = gson.fromJson(resp.toString(), ItemInfo[].class);

		// iterate through the items and create a new hashMap
		for (ItemInfo item : items) {
			HashMap<String, String> map = new HashMap<>();
			map.put(Const.KEY_USR, item.getUsername());
			map.put(Const.KEY_TITLE, item.getTitle());
			map.put(Const.KEY_TIMESTAMP, item.getTimestamp());
			map.put(Const.KEY_THUMBNAIL, item.getThumb_image());
			data.add(map);
		}

		// get the view, initialize the adapter, populate the view and 
		// set an onclick listener

		adapter = new CustomAdapter(activity, data, getFragmetManager());
		view.setAdapter(adapter);

		// set listener on comment click

		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});

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

}
