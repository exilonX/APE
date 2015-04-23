package com.app.ape.volley.request.handlers;

import android.view.View;
import android.widget.TextView;

import com.app.ape.helper.ChallengeItem;
import com.app.ape.helper.ItemInfo;
import com.android.volley.toolbox.ImageLoader;
import com.app.ape.views.FeedImageView;
import com.app.ape.volley.request.handlers.HandleJsonObjectResponse;

import org.json.JSONObject;
import com.app.ape.R;
import com.google.gson.Gson;

/**
 * Created by root on 01.03.2015.
 */
public class ChallengeHandler implements HandleJsonObjectResponse {

    View challenge = null;
    ImageLoader imageLoader = null;

    public ChallengeHandler(View challenge, ImageLoader imageLoader) {
        this.challenge = challenge;
        this.imageLoader = imageLoader;
    }

    @Override
    public void handleJsonObjectResponse(JSONObject response) {
        Gson gson = new Gson();

        ChallengeItem item = gson.fromJson(response.toString(), ChallengeItem.class);

        TextView title = (TextView) challenge.findViewById(R.id.challengeTextView);
        title.setText(item.getTitle());

        FeedImageView feedImgView = (FeedImageView) challenge.findViewById(R.id.challengeImage);
        feedImgView.setImageUrl(item.getThumb_image(), imageLoader);
        feedImgView.setVisibility(View.VISIBLE);
        feedImgView.setResponseObserver(new FeedImageView.ResponseObserver() {
            @Override
            public void onError() {
            }

            @Override
            public void onSuccess() {
            }
        });

    }
}
