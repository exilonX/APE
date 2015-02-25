package com.app.fragments;


import com.app.ape.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChallengeFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View challenge = inflater.inflate(R.layout.swipe_screen_right, container, false);
        ((TextView)challenge.findViewById(R.id.text)).setText("Challenge Preview");

        return challenge;
	}
}
