package com.example.ape.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.ape.R;
import com.example.ape.constants.Const;
import com.example.ape.helper.CommentTag;
import com.example.ape.views.FeedImageView;
import com.example.volley.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdaptor extends BaseAdapter {
	
	private Activity 							activity;
	private ArrayList<HashMap<String, String>> 	data;
	private LayoutInflater 						inflater = null;
	public ImageLoader 							imageLoader;
	public CommentTag 							tag;

	public CommentAdaptor(Activity a, ArrayList<HashMap<String, String>> data,
			CommentTag tag) {
		this.activity 		= a;
		this.data 			= data;
		this.inflater 		= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.imageLoader 	= AppController.getInstance().getImageLoader();
		this.tag 			= tag;
	}


	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		int type = getItemViewType(position);
		
		// inflate the view and populate the other views inside
		if (type == 0) {
			view = inflater.inflate(R.layout.comment_item, null);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView timestamp = (TextView) view
					.findViewById(R.id.timestamp);
			TextView statusMsg = (TextView) view
					.findViewById(R.id.txtStatusMsg);

			NetworkImageView profilePic = (NetworkImageView) view
					.findViewById(R.id.profilePic);
			FeedImageView feedImageView = (FeedImageView) view
					.findViewById(R.id.feedImage1);
			
			name.setText(this.tag.username);
			timestamp.setText(this.tag.date);
			if (!TextUtils.isEmpty(this.tag.description)) {
				statusMsg.setText(this.tag.description);
				statusMsg.setVisibility(View.VISIBLE);
			} else {
				statusMsg.setVisibility(View.GONE);
			}
			
			String urlPic = "http://www.insidefacebook.com/wp-content/uploads/2013/01/profile-150x150.png";
			profilePic.setImageUrl(urlPic, imageLoader);
			
			if (this.tag.imageUrl != null) {
				feedImageView.setImageUrl(this.tag.imageUrl, imageLoader);
				feedImageView.setVisibility(View.VISIBLE);
				feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
					@Override
					public void onError() {
					}
					@Override
					public void onSuccess() {
					}
				});

			} else {
				feedImageView.setVisibility(View.GONE);
			}
			
		} else {
			view = inflater.inflate(R.layout.comment_row, null);
			
			// get each sub-view and populate with coresponding data
			TextView username = (TextView)view.findViewById(R.id.username); 
			TextView comment = (TextView)view.findViewById(R.id.comment); 
			TextView timestamp = (TextView)view.findViewById(R.id.timestamp);
			
			HashMap<String, String> item = new HashMap<String, String>();
			item = data.get(position);

			username.setText(item.get("username"));
			comment.setText(item.get("comment"));
			timestamp.setText(item.get("timestamp"));
		}
		
		return view;
	}
	
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	
	
	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
}
