package com.example.ape.volley.request;

import org.json.JSONArray;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ape.volley.request.handlers.HandleJsonArrayResponse;
import com.example.volley.AppController;

/**
 * A set of static methods that get data from the webService
 * @author MercaIonel
 *
 */
public class VolleyRequests {
	public static void feedRequest(HandleJsonArrayResponse handle) {
		jsonArrayRequest(ConstRequest.TAG_JSON_ARRAY, ConstRequest.GET_FEED, handle);
	}

	public static void jsonArrayRequest(String TAG, String URL, final HandleJsonArrayResponse handle) {
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				handle.handleJsonArrayResp(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("TAG", "Error: " + error.getMessage());
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, TAG);
		
	}

}
