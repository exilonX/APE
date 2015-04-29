package com.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.ape.R;

/**
 * Created by ionel.merca on 4/29/2015.
 */
public class LoadingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relative = (RelativeLayout)inflater.inflate(R.layout.loading_view, container, false);
        return relative;
    }
}
