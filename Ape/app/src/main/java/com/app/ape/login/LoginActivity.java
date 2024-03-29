package com.app.ape.login;
import java.util.HashMap;

import com.app.activities.MainActivity;
import com.app.ape.R;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.volley.request.handlers.LoginHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		
		final EditText name = (EditText) findViewById(R.id.login_email);
		final EditText password = (EditText) findViewById(R.id.login_password);

		// Listening to register new account link
		registerScreen.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                //Switch to feed
//				Intent i = new Intent(getApplicationContext(), MainActivity.class);
//				startActivity(i);
				LoginHandler loginHandler = new LoginHandler(LoginActivity.this);
				
				HashMap<String, String> params = new HashMap<>();
//				params.put("Content-Type","application/x-www-form-urlencoded");
				params.put("name", name.getText().toString());
				params.put("password", password.getText().toString());

                Log.d("LOGIN ACTIVITY", "SE FACE REQUEST ASYNC");

				VolleyRequests.jsonObjectPostRequest(ConstRequest.TAG_JSON_OBJECT, 
						ConstRequest.POST_LOGIN_URL, 
						loginHandler, 
						params);
			}
		});
	}
	
	public void switchToMain() {
		Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
	}
}
