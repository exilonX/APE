package com.app.fragments;



import com.app.ape.R;

import com.app.ape.adapters.CustomAdapter;
import com.app.ape.constants.Const;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.PopulateFeedPaginatedHandler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FeedFragment extends Fragment {
	private ListView view;			// the list view with the replies
	private CustomAdapter adapter = null;	// the custom adapter used for populating the view
	private int currentPage = 1;
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		
		final LinearLayout linear = (LinearLayout) inflater.inflate(
				R.layout.swipe_screen_left, 
				container, 
				false);
		view = (ListView) linear.getChildAt(0);
//		view.setOnScrollListener(new FeedScrollListener(7, inflater, container,
//				savedInstanceState, getFragmentManager(), view, adapter, getActivity(), 
//				linear, this));
		Button btnLoadMore = new Button(getActivity());
		btnLoadMore.setText("LoadMore");
		view.addFooterView(btnLoadMore);
		final Fragment sup = this;

        final String user = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0).getString(Const.KEY_USR_SHARED, null);

		final PopulateFeedPaginatedHandler feed = new PopulateFeedPaginatedHandler(inflater, container,
				savedInstanceState, getFragmentManager(), view, adapter, getActivity(), 
				linear, this);
		VolleyRequests.jsonObjectGetRequest(ConstRequest.TAG_JSON_OBJECT, 
				ConstRequest.getFeedLink(7, currentPage, user), feed);
		currentPage++;
		
		btnLoadMore.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VolleyRequests.jsonObjectGetRequest(ConstRequest.TAG_JSON_OBJECT, 
						ConstRequest.getFeedLink(7, currentPage, user), feed);
				currentPage++;
			}
		});
		
		return linear;
	}
	
	public void loadMore(Button btnLoadMore) {

	}

}
