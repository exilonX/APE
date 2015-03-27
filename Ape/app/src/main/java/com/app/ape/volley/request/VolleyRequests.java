package com.app.ape.volley.request;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.app.ape.volley.request.handlers.HandleJsonArrayResponse;
import com.app.ape.volley.request.handlers.HandleJsonObjectResponse;
import com.app.volley.AppController;
import org.apache.http.entity.mime.MultipartEntity;

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
	
	/**
	 * Send a x-www-form-urlencoded POST request with the given parameters.
	 * @param TAG
	 * @param URL
	 * @param handle
	 * @param params
	 */
	public static void jsonObjectPutRequest(String TAG, String URL, final HandleJsonObjectResponse handle,
			final HashMap<String, String> params) {
		
		WWWFormRequest request = new WWWFormRequest(
				Method.PUT,
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


//    public static void jsonMultiPartRequest(String TAG, String URL, final HandleJsonObjectResponse handle,
//                                            File file, String stringPart) {
////        MultiPartRequest request = new MultiPartRequest(Method.POST, ConstRequest.POST_REPLY, null,
////                new Response.Listener<JSONObject>() {
////                    @Override
////                    public void onResponse(JSONObject response) {
////                        Log.d("Response POST", response.toString());
////                        handle.handleJsonObjectResponse(response);
////                    }
////                },
////                new Response.ErrorListener() {
////
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        VolleyLog.d("Post Error: " + error.getMessage());
////                    }
////                });
////
////        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
////
////        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
////        Map<String, File> fileUpload = request.getFileUploads();
//
//        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Response POST", response.toString());
//                        handle.handleJsonObjectResponse(response);
//                    }
//                };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d("Post Error: " + error.getMessage());
//                    }
//                };
//
//        MultiRequest request = new MultiRequest(URL, file, stringPart, errorListener, listener);
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(request, TAG);
//    }

}
