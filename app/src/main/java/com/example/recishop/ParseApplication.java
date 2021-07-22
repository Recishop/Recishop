package com.example.recishop;

import android.app.Application;
import android.util.Log;
import com.parse.Parse;

/**
 * Parse Application for creating access point to Parse Backend
 *
 * @author tallt
 */
public class ParseApplication extends Application {
    private static final String TAG = ParseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
        Log.d(TAG, "Parse initialized");
    }
}
