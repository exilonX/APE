package com.app.fragments;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import com.app.ape.R;
import com.app.ape.adapters.CommentAdaptor;
import com.app.ape.constants.Const;
import com.app.ape.helper.CommentTag;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.HandleJsonObjectResponse;
import com.app.ape.volley.request.handlers.PopulateCommentHandler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CommentFragment extends Fragment {

	ListView 		view;			// the list view with the replies
	CommentAdaptor 	adapter;	// the custom adapter used for populating the view
	CommentTag		info;

    public static CommentFragment newInstance(CommentTag tag) {
        CommentFragment fragment = new CommentFragment();

        Bundle args = tag.toBundle();
        fragment.setArguments(args);

        return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout relative = (RelativeLayout) inflater.inflate(R.layout.comment_view,
				container, false);
		view = (ListView) relative.findViewById(R.id.commentList);

        this.info = new CommentTag(getArguments());

		getFragmentManager().beginTransaction().add(this, "CommentFragment");

		PopulateCommentHandler handler = new PopulateCommentHandler(getActivity(), view, info);
		String url = ConstRequest.GET_COMMENTS + info._id;
		VolleyRequests.jsonArrayGetRequest(ConstRequest.TAG_JSON_ARRAY, url, handler);
		
		addCommentListener(relative);
		
		
		return relative;
	}
	
	public void refreshView() {
		final FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.detach(this);
		ft.attach(this);
		ft.commit();
	}
	
	class AddCommentHandler implements HandleJsonObjectResponse {
		
		CommentFragment sup;
		
		public AddCommentHandler(CommentFragment sup) {
			this.sup = sup;
		}
		

		@Override
		public void handleJsonObjectResponse(JSONObject response) {
			String result;
			try {
				result = response.getString("result");
				if (result != null && !result.equals("success")) {
					Log.d("ADDCOMM", response.getString("errors"));
				} else {
					Log.d("ADDCOMM", "success");
					this.sup.refreshView();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Comment add listener for button
	 * @param relative
	 */
	public void addCommentListener(RelativeLayout relative) {
		final EditText textComment 	= (EditText)relative.findViewById(R.id.editTextComment);
		final Button addComment 	= (Button)relative.findViewById(R.id.addCommentButton);
		final String id = this.info._id;
        final String username = this.info.username;
		final AddCommentHandler handler = new AddCommentHandler(this);
		
		addComment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String comment = textComment.getText().toString();
				textComment.getText().clear();

				HashMap<String, String> params = new HashMap<>();
				params.put("_reply_id", id);
				params.put("comment", comment);
                params.put("username", username);
				
				VolleyRequests.jsonObjectPutRequest(ConstRequest.TAG_JSON_OBJECT,
						ConstRequest.POST_COMM_ADD, 
						handler, 
						params);
			}
		});
		
	}

	@Override
	public String toString() {
		return "CommentFragment";
	}


}
