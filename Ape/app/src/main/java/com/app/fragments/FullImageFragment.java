package com.app.fragments;

import com.android.volley.toolbox.ImageLoader;
import com.app.ape.R;
import com.app.ape.views.FeedImageView;
import com.app.volley.AppController;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FullImageFragment extends Fragment {
	
	String imageUrl;
	ImageLoader imageLoader;
    private static String imageUrlKey = "ImageURL";
    private static String imageLoaderKey = "ImageLoader";
	
//	public FullImageFragment(String imageUrl) {
//		this.imageUrl = imageUrl;
//		this.imageLoader	= AppController.getInstance().getImageLoader();
//	}

    public static FullImageFragment newInstance(String imageUrl) {
        FullImageFragment frg = new FullImageFragment();
        Bundle bundleArgs = new Bundle();
        bundleArgs.putString(imageUrlKey, imageUrl);
        frg.setArguments(bundleArgs);
        return frg;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout linear = (RelativeLayout) inflater.inflate(R.layout.full_image, container, false);

        Bundle args = getArguments();
        this.imageUrl = args.getString(imageUrlKey);
        this.imageLoader = AppController.getInstance().getImageLoader();

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
