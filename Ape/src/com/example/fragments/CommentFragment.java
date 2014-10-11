package com.example.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.ape.R;
import com.example.ape.adapters.CommentAdaptor;
import com.example.ape.constants.Const;
import com.example.ape.helper.CommentInfo;
import com.example.ape.helper.CommentTag;
import com.example.ape.volley.request.ConstRequest;
import com.example.ape.volley.request.VolleyRequests;
import com.example.ape.volley.request.handlers.PopulateCommentHandler;
import com.google.gson.Gson;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CommentFragment extends Fragment {
	
	ListView 		view;			// the list view with the replies
	CommentAdaptor 	adapter;	// the custom adapter used for populating the view
	CommentTag		info;
	
	public CommentFragment(CommentTag info) {
		this.info	= info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout relative = (RelativeLayout) inflater.inflate(R.layout.comment_view, container, false);
		view = (ListView) relative.findViewById(R.id.commentList);
		
		getFragmentManager().beginTransaction().add(this, "CommentFragment");
		
		PopulateCommentHandler handler = new PopulateCommentHandler(getActivity(), view, info);
		String url = ConstRequest.GET_COMMENTS + info._id;
		VolleyRequests.jsonArrayRequest(ConstRequest.TAG_JSON_ARRAY, url, handler);
		
       return relative;
	}
	
	@Override
	public String toString() {
		return "CommentFragment";
	}
	
	
}
