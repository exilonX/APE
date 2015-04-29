package com.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.ape.R;
import com.app.ape.adapters.CommentAdaptor;
import com.app.ape.helper.CommentTag;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.PopulateCommentHandler;

/**
 * Created by ionel.merca on 4/29/2015.
 */
public class ReplyFragment extends Fragment {

    ListView view;
    CommentAdaptor adapter;
    CommentTag info;

    public static ReplyFragment newInstance(CommentTag tag) {
        ReplyFragment fragment = new ReplyFragment();

        Bundle args = tag.toBundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout relative = (RelativeLayout)inflater.inflate(R.layout.reply_view, container, false);
        view = (ListView)relative.findViewById(R.id.myComments);

        this.info = new CommentTag(getArguments());

        getFragmentManager().beginTransaction().add(this, "ReplyFragment");

        PopulateCommentHandler handler = new PopulateCommentHandler(getActivity(), view, info);
        String url = ConstRequest.GET_COMMENTS + info._id;
        VolleyRequests.jsonArrayGetRequest(ConstRequest.TAG_JSON_ARRAY, url, handler);

        return relative;
    }
}
