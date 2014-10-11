package com.example.ape.login;
import com.example.activities.MainActivity;
import com.example.ape.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setting default screen to login.xml
		setContentView(R.layout.login);

		TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
		Button btnLogin = (Button) findViewById(R.id.btnLogin);

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
				//TODO check if the credentials are correct
				//Switch to feed
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);	
			}
		});
	}
}
