package com.example.ape.volley.request.handlers;

import org.json.JSONArray;

import android.util.Log;


/**
 * Basic test handler
 * @author MercaIonel
 *
 */
public class LogHandler implements HandleJsonArrayResponse {

	@Override
	public void handleJsonArrayResp(JSONArray resp) {
		Log.d("TAG", resp.toString());
	}
	
}
