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


import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ape.R;
import com.example.ape.adapters.CustomAdapter;
import com.example.ape.helper.ItemInfo;
import com.example.ape.volley.request.ConstRequest;
import com.example.ape.volley.request.VolleyRequests;
import com.example.ape.volley.request.handlers.PopulateFeedHandler;
import com.example.ape.volley.request.handlers.PopulateFeedPaginatedHandler;
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

	
	ListView view;			// the list view with the replies
	CustomAdapter adapter;	// the custom adapter used for populating the view

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.swipe_screen_left, container, false);
		view = (ListView) linear.getChildAt(0);
		
		PopulateFeedPaginatedHandler feed = new PopulateFeedPaginatedHandler(inflater, container,
				savedInstanceState, getFragmentManager(), view, adapter, getActivity(), 
				linear, this);
		VolleyRequests.jsonObjectRequest(ConstRequest.TAG_JSON_OBJECT, 
				ConstRequest.GET_FEED, feed);
		return linear;
	}

	
//	public String getJsonString() {
//	// read the data from the JSON 
//	InputStream inStream = getResources().openRawResource(R.raw.input);
//	Writer writer = new StringWriter();
//	char[] buffer = new char[1024];
//	try {
//		Reader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
//		int n;
//		while ((n = reader.read(buffer)) != -1) {
//			writer.write(buffer, 0, n);
//		}
//	} catch (UnsupportedEncodingException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		try {
//			inStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	return writer.toString();
//}


}
