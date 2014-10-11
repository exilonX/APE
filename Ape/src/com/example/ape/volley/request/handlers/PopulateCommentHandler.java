package com.example.ape.volley.request.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import android.app.Activity;
import android.widget.ListView;

import com.example.ape.adapters.CommentAdaptor;
import com.example.ape.constants.Const;
import com.example.ape.helper.CommentInfo;
import com.example.ape.helper.CommentTag;
import com.google.gson.Gson;

/**
 * Populate the comment view
 * @author merca
 * 
 */
public class PopulateCommentHandler implements HandleJsonArrayResponse {

	CommentAdaptor 	adapter;
	Activity		activity;
	ListView		view;
	CommentTag		info;
	
	public PopulateCommentHandler(Activity activity, ListView view, CommentTag info) {
		this.adapter 	= null;
		this.activity 	= activity;
		this.view 		= view;
		this.info		= info;
	}
	
	@Override
	public void handleJsonArrayResp(JSONArray resp) {
		// google's GSON library used to map a JSON into a Java Object
		Gson gson = new Gson();

		// get a list of ItemInfo from the JSON
		CommentInfo[] items = gson.fromJson(resp.toString(), CommentInfo[].class);
		// the data that contains row element information
		ArrayList<HashMap<String, String>> data = buildMap(items);
		

		// get the view, initialize the adapter, populate the view and 
		// set an onclick listener

		adapter = new CommentAdaptor(activity, data, info);
		view.setAdapter(adapter);
	}
	
	public ArrayList<HashMap<String, String>> buildMap(CommentInfo[] items) {
		ArrayList<HashMap<String, String>> data = new ArrayList<>();
		
		HashMap<String, String> map = new HashMap<>();
		map.put(Const.KEY_USR_COMM, "The commentView");
		map.put(Const.KEY_COMMENT_COMM, "The ");
		map.put(Const.KEY_DATE_COMM, "The orice");
		data.add(map);
		
		// iterate through the items and create a new hashMap
		for (CommentInfo item : items) {
			map = new HashMap<>();
			map.put(Const.KEY_USR_COMM, item.username);
			map.put(Const.KEY_COMMENT_COMM, item.comment);
			map.put(Const.KEY_DATE_COMM, item.date);

			data.add(map);
		}
		
		return data;
	}

}
