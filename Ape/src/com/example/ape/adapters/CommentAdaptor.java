package com.example.ape.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.ape.R;
import com.example.ape.constants.Const;
import com.example.ape.helper.CommentTag;
import com.example.volley.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

	public CommentAdaptor(Activity a, ArrayList<HashMap<String, String>> data, CommentTag tag) {
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
	
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		int type = getItemViewType(position);
		// inflate the view and populate the other views inside
		if(convertView==null)
			if (type == 0) {
				view = inflater.inflate(R.layout.comment_row2, null);
				final ImageView	thumbImage = (ImageView)view.findViewById(R.id.list_image);
//				imageLoader.DisplayImage(tag.imageUrl, thumbImage);
				
				imageLoader.get(tag.imageUrl, new ImageListener() {
					
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Volley image Loader", "Image Load Error: " + error.getMessage());
					}
					
					@Override
					public void onResponse(ImageContainer response, boolean arg1) {
						if (response.getBitmap() != null) {
							thumbImage.setImageBitmap(response.getBitmap());
						}
					}
				});
				
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
