package com.app.ape.volley.request.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import com.app.ape.login.LoginActivity;
import com.app.ape.constants.Const;

import android.content.SharedPreferences;
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

            SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(Const.KEY_USR_SHARED, user);
            editor.putString(Const.KEY_TOKEN_SHARED, token);
            editor.putString(Const.KEY_EXP_SHARED, expires);

			this.context.switchToMain();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
