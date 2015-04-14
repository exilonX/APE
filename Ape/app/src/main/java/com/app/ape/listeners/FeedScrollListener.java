package com.app.ape.listeners;

import com.app.ape.adapters.CustomAdapter;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.PopulateFeedPaginatedHandler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class FeedScrollListener implements OnScrollListener {

	private int visibleThreshold = 10;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = false;
	private LayoutInflater 	inflater;
	private ViewGroup 		container;
	private Bundle 			savedInstanceState;
	private FragmentManager fragmetManager;
	private ListView 		view;
	private Fragment 		fragment;
	private CustomAdapter 	adapter;
	private Activity		activity;
	private LinearLayout 	linear;
    
    public FeedScrollListener() {
		
	}
    
    public FeedScrollListener(int visibleThreshold, LayoutInflater inflater, ViewGroup container,
			Bundle savedInstances, FragmentManager fragMang, ListView view, 
			CustomAdapter adapter, Activity activity, LinearLayout linear, 
			Fragment fragment) {
    	
    	this.visibleThreshold = visibleThreshold;
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
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleItemCount)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
    		// TODO Auto-generated method stub
    		PopulateFeedPaginatedHandler feed = new PopulateFeedPaginatedHandler(inflater, container,
    				savedInstanceState, fragmetManager, this.view, adapter, this.activity, 
    				linear, this.fragment);
    		VolleyRequests.jsonObjectGetRequest(ConstRequest.TAG_JSON_OBJECT, 
    				ConstRequest.getFeedLink(7, currentPage+1, "x"/* TODO change this with real user name when using scroll*/), feed);
            loading = true;
        }


	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
