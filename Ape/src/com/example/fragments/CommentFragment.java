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

import com.example.ape.R;
import com.example.ape.adapters.CommentAdaptor;
import com.example.ape.helper.CommentInfo;
import com.google.gson.Gson;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class CommentFragment extends Fragment {

	static final String KEY_USR = "username"; 			// username key
	static final String KEY_COMMENT = "comment";  			// title key
	static final String KEY_TIMESTAMP = "timestamp";	// timestamp key
	
	ListView view;			// the list view with the replies
	CommentAdaptor adapter;	// the custom adapter used for populating the view
	
	public String getJsonString() {
		// read the data from the JSON
		InputStream inStream = getResources().openRawResource(R.raw.comment);
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
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout relative = (RelativeLayout) inflater.inflate(R.layout.comment_view, container, false);
		view = (ListView) relative.findViewById(R.id.commentList);
		
		getFragmentManager().beginTransaction().add(this, "CommentFragment");
		
		String json = getJsonString();
		
		// the data that contains row element information
		ArrayList<HashMap<String, String>> data = new ArrayList<>();

		// google's GSON library used to map a JSON into a Java Object
		Gson gson = new Gson();
		
		// get a list of ItemInfo from the JSON
		CommentInfo[] items = gson.fromJson(json, CommentInfo[].class);
		
		// iterate through the items and create a new hashMap
		for (CommentInfo item : items) {
			HashMap<String, String> map = new HashMap<>();
			map.put(KEY_USR, item.username);
			map.put(KEY_COMMENT, item.comment);
			map.put(KEY_TIMESTAMP, item.timestamp);
			
			data.add(map);
		}
		
		// get the view, initialize the adapter, populate the view and 
		// set an onclick listener
		
		adapter = new CommentAdaptor(getActivity(), data);
		view.setAdapter(adapter);
		
		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		
       return relative;
	}
	
	@Override
	public String toString() {
		return "CommentFragment";
	}
	
	
}
