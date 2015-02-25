package com.app.ape.login;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.app.ape.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register);
 
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        final EditText email = (EditText) findViewById(R.id.reg_email);
        final EditText password = (EditText) findViewById(R.id.reg_password);
        final EditText username = (EditText) findViewById(R.id.reg_fullname);
        
        Button registerButton = (Button) findViewById(R.id.btnRegister);
 
        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	finish();
            }
        });
        
        registerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Check fields are completed and correct
				//Check if email address is correct
            	String emailText = email.getText().toString();
            	String usernameText = username.getText().toString();
            	String passwdText = password.getText().toString();
            	
            	if (!isValidText(emailText)) {
            		//TODO display error message
            		return;
            	}
            	if (!isValidText(passwdText)) {
            		//TODO display error message
            		return;
            	}
            	if (!isValidText(usernameText)) {
            		//TODO display error message
            		return;
            	}
            	
            	if (checkEmailAddress(emailText)) {
            		// Switching to Login Screen/closing register screen
            		// Closing registration screen
            		
            	} else {
            		// TODO Display an error message
            	}  
			}
		});
       
    }
    
    public static boolean checkEmailAddress(String email) {
 	   Pattern pattern = Pattern.compile("^.+@.+\\..+$");
 	   Matcher matcher = pattern.matcher(email);
 	   return matcher.matches();
    }
    
    public boolean isValidText(String text) {
    	if (text == null) {
    		return false;
    	}
    	
    	Pattern pattern = Pattern.compile("\\s");
    	Matcher matcher = pattern.matcher(text);
    
    	// if white spaces exist
    	boolean matches = matcher.matches();
    	if (matches) {
    		return false;
    	}
    	
    	return true;
    }
}