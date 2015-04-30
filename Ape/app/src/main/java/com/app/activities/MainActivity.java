package com.app.activities;

import com.app.ape.R;
import com.app.ape.volley.request.ConstRequest;
import com.app.ape.volley.request.VolleyRequests;
import com.app.ape.constants.Const;
import com.app.ape.volley.request.handlers.HandleHasReplied;
import com.app.ape.volley.request.handlers.HandleRegisterGCM;
import com.app.camera.src.cwac.CameraHost;
import com.app.camera.src.cwac.CameraHostProvider;
import com.app.camera.src.cwac.SimpleCameraHost;
import com.app.fragments.adapter.TabPaggerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.HashMap;


public class MainActivity extends FragmentActivity implements FragmentSwitchListener , CameraHostProvider{

    // DEMO FOR GCM
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    ViewPager tab;
    TabPaggerAdapter tabAdapter;
    ActionBar actionBar;

    // DEMO FOR GCM


    GoogleCloudMessaging gcm;
    String regid;
    Context context;

    String SENDER_ID = "342496740600";
    static final String TAG = "GCM Demo";


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Log.d(TAG, "in On create");

        context = getApplicationContext();
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            // should register the id to the server
            // make a new handler if the user is not registered
            // that user will not get any notifications

            if (regid.isEmpty()) {
                registerInBackground();
            } else {
                this.registerUser(this.getUsername(), regid);
                Log.d(TAG, regid);
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

		// remove the title and icon of the app
		actionBar = getActionBar();
		if (actionBar == null)
			Log.d("onCreate", "Action bar cannot be created.");
		
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		
		tabAdapter = new TabPaggerAdapter(getSupportFragmentManager(), this);
		
		tab = (ViewPager)findViewById(R.id.pager);
		tab.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar = getActionBar();
						actionBar.setSelectedNavigationItem(position);
					}
				});
		tab.setAdapter(tabAdapter);

        checkReply(this.getUsername());

		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab currentTab, android.app.FragmentTransaction ft) {
			}

			@Override
			public void onTabSelected(Tab currentTab, android.app.FragmentTransaction ft) {
                ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
                viewPager.setCurrentItem(currentTab.getPosition());
            }

			@Override
			public void onTabUnselected(Tab currentTab, android.app.FragmentTransaction ft) {
			}
		};
		
		actionBar.addTab(actionBar.newTab().setText("Reply").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Challenge").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("Feed").setTabListener(tabListener));
	}

    private String getUsername() {
        SharedPreferences pref = this.getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getString(Const.KEY_USR_SHARED, null);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    private void registerUser(String username, String registration_id) {
        HandleRegisterGCM handler = new HandleRegisterGCM(this);

        HashMap<String, String> params = new HashMap<>();

        params.put(Const.KEY_GCM_USER, username);
        params.put(Const.KEY_GCM_REG_ID, registration_id);

        VolleyRequests.jsonObjectPostRequest(ConstRequest.TAG_JSON_OBJECT,
                ConstRequest.POST_REGISTER_GCM,
                handler,
                params);
    }

    public void checkReply(String username) {
        HashMap<String, String> params = new HashMap<>();

        params.put(Const.KEY_USR, username);

        // if the user has already replied then deactivate the capture button
        HandleHasReplied handler = new HandleHasReplied(this, tab, getSupportFragmentManager());

        VolleyRequests.jsonObjectPostRequest(ConstRequest.TAG_JSON_OBJECT,
                ConstRequest.GET_HAS_REPLIED,
                handler,
                params);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                mDisplay.append(msg + "\n");
            }
        }.execute(null, null, null);
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        this.registerUser(this.getUsername(), regid);
    }

    public void replaceFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, newFragment, newFragment.toString());
        fragmentTransaction.addToBackStack(newFragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public CameraHost getCameraHost() {
        return new SimpleCameraHost(this);
    }

}
