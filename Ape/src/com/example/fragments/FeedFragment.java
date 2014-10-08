package com.example.fragments;



import com.example.ape.R;
import com.example.ape.adapters.CustomAdapter;
import com.example.ape.volley.request.ConstRequest;
import com.example.ape.volley.request.VolleyRequests;
import com.example.ape.volley.request.handlers.PopulateFeedPaginatedHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

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
