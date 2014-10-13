package com.example.ape.volley.request.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.activities.MainActivity;
import com.example.ape.login.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LoginHandler implements HandleJsonObjectResponse {
	LoginActivity context;
	
	public LoginHandler(LoginActivity context) {
		this.context = context;
	}

	@Override
	public void handleJsonObjectResponse(JSONObject response) {
		try {
			String token = response.getString("token");
			String expires = response.getString("expires");
			String user = response.getString("user");
			Log.d("Login response", token + ", " + expires + ", " + user);
			
			this.context.switchToMain();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
