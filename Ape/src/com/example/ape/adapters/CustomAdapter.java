package com.example.ape.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.example.activities.FragmentSwitchListener;
import com.example.activities.MainActivity;
import com.example.ape.R;
import com.example.ape.constants.Const;
import com.example.ape.helper.CommentInfo;
import com.example.ape.helper.CommentTag;
import com.example.ape.views.FeedImageView;
import com.example.ape.volley.request.ConstRequest;
import com.example.ape.volley.request.VolleyRequests;
import com.example.ape.volley.request.handlers.LogHandler;
import com.example.fragments.CommentFragment;
import com.example.fragments.FullImageFragment;
import com.example.volley.AppController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * Custom Adapter used for populating the feed view
 * @author MercaIonel
 *
 */
public class CustomAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	FragmentManager manager = null;
	public boolean isImageFitToScreen;

	public CustomAdapter(Activity a, ArrayList<HashMap<String, String>> data, FragmentManager manager) {
		this.activity 		= a;
		this.data 			= data;
		this.inflater 		= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//		this.imageLoader = new ImageLoader(activity.getApplicationContext());
		this.imageLoader	= AppController.getInstance().getImageLoader();
		this.manager 		= manager;
		this.isImageFitToScreen = false;
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
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView timestamp = (TextView) convertView
				.findViewById(R.id.timestamp);
		TextView statusMsg = (TextView) convertView
				.findViewById(R.id.txtStatusMsg);
		TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
		NetworkImageView profilePic = (NetworkImageView) convertView
				.findViewById(R.id.profilePic);
		FeedImageView feedImageView = (FeedImageView) convertView
				.findViewById(R.id.feedImage1);

		HashMap<String, String> item = new HashMap<String, String>();
		item = data.get(position);

		name.setText(item.get(Const.KEY_USR));
		//		CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
				//                Long.parseLong(item.getTimeStamp()),
		//                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
		timestamp.setText(Const.KEY_TIMESTAMP);

		if (!TextUtils.isEmpty(item.get(Const.KEY_TITLE))) {
			statusMsg.setText(item.get(Const.KEY_TITLE));
			statusMsg.setVisibility(View.VISIBLE);
		} else {
			statusMsg.setVisibility(View.GONE);
		}
		String urlPic = "http://www.insidefacebook.com/wp-content/uploads/2013/01/profile-150x150.png";
		profilePic.setImageUrl(urlPic, imageLoader);
		//		if (item.getUrl() != null) {
		//            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
		//                    + item.getUrl() + "</a> "));
		// 
		//            // Making url clickable
		//            url.setMovementMethod(LinkMovementMethod.getInstance());
		//            url.setVisibility(View.VISIBLE);
		//        } else {
		// url is null, remove from the view
		url.setVisibility(View.GONE);
		//        }


		ImageButton imageBut = (ImageButton)convertView.findViewById(R.id.addComment);
		//thumb_image.setScaleType(ScaleType.FIT_CENTER);

		imageBut.setTag(new CommentTag(item.get(Const.KEY_ID), position,
				item.get(Const.KEY_THUMBNAIL), item.get(Const.KEY_USR), 
				item.get(Const.KEY_DATE_COMM), item.get(Const.KEY_TITLE)));

		if (item.get(Const.KEY_THUMBNAIL) != null) {
			feedImageView.setImageUrl(item.get(Const.KEY_THUMBNAIL), imageLoader);
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

		setOnClickComment(convertView);
		setOnClickApe(convertView);
		setOnClickImage(feedImageView, item.get(Const.KEY_THUMBNAIL));
		
		return convertView;
	}
	
	public void setOnClickImage(final FeedImageView feedImg, final String url) {
		Log.d("CLICK", "in set on click " + isImageFitToScreen);
		feedImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO AICISEA CONTINUARE CU FULL SCREEN IMAGE VIEW
				FragmentSwitchListener activ = (FragmentSwitchListener)activity;
				Fragment commentFrag = new FullImageFragment(url);
				activ.replaceFragment(commentFrag);
			}
		});
	}
	
	public void addData(ArrayList<HashMap<String, String>> data) {
		if (this.data != null) {
			this.data.addAll(data);
		} else {
			this.data = data;
		}
	}


	class OnImageClickListener implements OnClickListener {

		int _postion;


		// constructor
		public OnImageClickListener(int position) {
			this._postion = position;
		}

		@Override
		public void onClick(View v) {
			// on selecting grid view image
			// launch full screen activity
			//            Intent i = new Intent(activity, FullScreenViewActivity.class);
			//            i.putExtra("position", _postion);
			//            activity.startActivity(i);
		}

	}

	public void setOnClickComment(View view) {
		final ImageButton btn = (ImageButton)view.findViewById(R.id.addComment);
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("ONCLICK", "I just clicked this shit" + btn.getTag().toString());

					FragmentSwitchListener activ = (FragmentSwitchListener)activity;
					Fragment commentFrag = new CommentFragment((CommentTag)btn.getTag());
					activ.replaceFragment(commentFrag);

					VolleyRequests.feedRequest(new LogHandler());
				}
			});
		} else {
			Log.d("CLICK", "ESTE NULL");
		}
	}

	/**
	 * On click listener on ape image button
	 * @param view - the main view 
	 */
	public void setOnClickApe(final View view) {
		final ImageButton btn = (ImageButton)view.findViewById(R.id.increaseApes);
		if (btn != null) {
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("JUST CLICKED", "Clicked");
					final TextView nrApes = (TextView)view.findViewById(R.id.numberOfApes);
					int curent = Integer.parseInt(nrApes.getText().toString());
					String str = String.valueOf(curent + 1);
					nrApes.setText(str);
				}
			});
		} else {
			Log.d("CLICK", "ESTE NULL");
		}
	}

}
