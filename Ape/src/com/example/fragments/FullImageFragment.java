package com.example.fragments;

import com.android.volley.toolbox.ImageLoader;
import com.example.ape.R;
import com.example.ape.constants.Const;
import com.example.ape.views.FeedImageView;
import com.example.volley.AppController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FullImageFragment extends Fragment {
	
	String imageUrl;
	ImageLoader imageLoader;
	
	public FullImageFragment(String imageUrl) {
		this.imageUrl = imageUrl;
		this.imageLoader	= AppController.getInstance().getImageLoader();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.full_image, container, false);
		
		FeedImageView feedImgView = (FeedImageView) linear.findViewById(R.id.feedImage2);
		feedImgView.setImageUrl(imageUrl, imageLoader);
		feedImgView.setVisibility(View.VISIBLE);
		feedImgView.setResponseObserver(new FeedImageView.ResponseObserver() {
			@Override
			public void onError() {
			}

			@Override
			public void onSuccess() {
			}
		});
		
		
		return linear;
	}
	
}
