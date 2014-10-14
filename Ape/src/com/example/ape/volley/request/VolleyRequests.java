package com.example.ape.volley.request;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ape.volley.request.handlers.HandleJsonArrayResponse;
import com.example.ape.volley.request.handlers.HandleJsonObjectResponse;
import com.example.volley.AppController;

/**
 * A set of static methods that get data from the webService
 * @author MercaIonel
 *
 */
public class VolleyRequests {
	public static void feedRequest(HandleJsonArrayResponse handle) {
		jsonArrayGetRequest(ConstRequest.TAG_JSON_ARRAY, ConstRequest.GET_FEED, handle);
	}

	/**
	 * Make a GET request which is expected to answer with a JSON array.
	 * @param TAG
	 * @param URL
	 * @param handle
	 */
	public static void jsonArrayGetRequest(String TAG, String URL, final HandleJsonArrayResponse handle) {
		JsonArrayRequest req = new JsonArrayRequest(URL,
				new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				handle.handleJsonArrayResp(response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("Object response erorr: " + error.getMessage());
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * Make a GET request which is expected to answer with a JSON.
	 * @param TAG
	 * @param URL
	 * @param handle
	 */
	public static void jsonObjectGetRequest(String TAG, String URL, final HandleJsonObjectResponse handle) {


		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				URL, null,
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				handle.handleJsonObjectResponse(response);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("Object response erorr: " + error.getMessage());
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);
	}

	/**
	 * Send a x-www-form-urlencoded POST request with the given parameters.
	 * @param TAG
	 * @param URL
	 * @param handle
	 * @param params
	 */
	public static void jsonObjectPostRequest(String TAG, String URL, final HandleJsonObjectResponse handle,
			final HashMap<String, String> params) {
		WWWFormRequest request = new WWWFormRequest(
				Method.POST,
				URL, 
				params,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("Response POST", response.toString());
						handle.handleJsonObjectResponse(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Post Error: " + error.getMessage());
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(request, TAG);
	}


}
