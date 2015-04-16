package com.app.ape.listeners;

import com.app.ape.adapters.CustomAdapter;
import com.app.ape.constants.Const;
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
import android.util.Log;

public class FeedScrollListener implements OnScrollListener {

	private int visibleThreshold;
    private int currentPage;
    private int pageSize;
    private int previousTotal;
    private boolean loading;

	private LayoutInflater 	inflater;
	private ViewGroup 		container;
	private Bundle 			savedInstanceState;
	private FragmentManager fragmentManager;
	private ListView 		view;
	private Fragment 		fragment;
	private CustomAdapter 	adapter;
	private Activity		activity;
	private LinearLayout 	linear;

    private PopulateFeedPaginatedHandler feed;
    
    public FeedScrollListener() {
		
	}
    
    public FeedScrollListener(int pageSize, int currentPage, LayoutInflater inflater, ViewGroup container,
			Bundle savedInstances, FragmentManager fragmentManager, ListView view,
			CustomAdapter adapter, Activity activity, LinearLayout linear, 
			Fragment fragment, PopulateFeedPaginatedHandler feed) {

        this.currentPage        = currentPage;
        this.pageSize           = pageSize;

		this.inflater 			= inflater;
		this.container 			= container;
		this.savedInstanceState = savedInstances;
		this.fragmentManager 	= fragmentManager;
		this.view 				= view;
		this.adapter 			= adapter;
		this.activity			= activity;
		this.linear 			= linear;
		this.fragment 			= fragment;

        this.feed               = feed;

        this.previousTotal      = 0;
        this.loading            = true;
        this.visibleThreshold   = 1;
    }
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
        final String user = activity.getApplicationContext().getSharedPreferences("MyPref", 0).getString(Const.KEY_USR_SHARED, null);

		if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            VolleyRequests.jsonObjectGetRequest(ConstRequest.TAG_JSON_OBJECT,
                    ConstRequest.getFeedLink(pageSize, currentPage, user), feed);
            loading = true;
        }


	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

}
