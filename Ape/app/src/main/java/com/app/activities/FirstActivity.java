package com.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.app.ape.R;
import com.app.ape.constants.Const;
import com.app.ape.login.LoginActivity;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String username = pref.getString(Const.KEY_USR_SHARED, null);
        String token = pref.getString(Const.KEY_TOKEN_SHARED, null);

        if (username != null && token != null) {
            // Start the main activity
            if (this.checkAuth(username, token)) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return;
            }
        }

        // Start the login activity
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public boolean checkAuth(String username, String token) {
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
