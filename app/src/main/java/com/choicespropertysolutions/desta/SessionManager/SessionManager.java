package com.choicespropertysolutions.desta.SessionManager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import com.choicespropertysolutions.desta.SignIn;

import com.choicespropertysolutions.desta.Login;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;      // Shared pref mode
    SessionManager sessionManager;

    // Sharedpref file name
    private static final String PREF_NAME = "Preference";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";

    public static final String KEY_FIREBASE_TOKEN = "isFirebaseToken";

    public static final String REGISTERED = "registered";

    public SessionManager(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String username) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

        editor.putString(KEY_USERNAME, username);

        // commit changes
        editor.commit();
    }

    public void createUserFirebaseNotificationToken(String isFirebaseToken) {
        // Storing login value as TRUE
        editor.putBoolean(REGISTERED, true);
        editor.commit();

        // Storing firebsase token in pref
        editor.putString(KEY_FIREBASE_TOKEN, isFirebaseToken);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user email id
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // return user
        return user;
    }

    public HashMap<String, String> getUserFirebaseNotificationToken() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user email id
        user.put(KEY_FIREBASE_TOKEN, pref.getString(KEY_FIREBASE_TOKEN, null));

        // return user
        return user;
    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(context, Login.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isRegisteredToken() {
        return pref.getBoolean(REGISTERED, false);
    }

}
