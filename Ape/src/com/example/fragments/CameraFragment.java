package com.example.fragments;


import com.example.ape.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CameraFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View camera = inflater.inflate(R.layout.swipe_screen_right, container, false);
        ((TextView)camera.findViewById(R.id.text)).setText("Camera Reply");

        return camera;
	}
	
}
