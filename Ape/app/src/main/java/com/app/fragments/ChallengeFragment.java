package com.app.fragments;


import com.android.volley.toolbox.ImageLoader;
import com.app.ape.R;
import com.app.ape.constants.Const;
import com.app.ape.helper.ChallengeItem;
import com.app.ape.views.FeedImageView;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.ChallengeHandler;
import com.app.volley.AppController;
import com.app.ape.volley.request.ConstRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChallengeFragment extends Fragment {
    String imageUrl;
    ImageLoader imageLoader;

    public ChallengeFragment() {
        imageUrl = ConstRequest.BASE_URL + "/api/challenge";
        imageLoader = AppController.getInstance().getImageLoader();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View challenge = inflater.inflate(R.layout.challenge, container, false);

        ChallengeHandler challengeHandler = new ChallengeHandler(challenge, imageLoader);

        VolleyRequests.jsonObjectGetRequest(ConstRequest.TAG_JSON_OBJECT,
                imageUrl, challengeHandler);

        return challenge;
	}
}
