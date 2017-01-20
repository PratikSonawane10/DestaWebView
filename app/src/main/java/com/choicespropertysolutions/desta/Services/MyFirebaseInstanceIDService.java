package com.choicespropertysolutions.desta.Services;

import android.provider.Settings;
import android.util.Log;

import com.choicespropertysolutions.desta.Connectivity.RegisterFirebaseToServer;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    SessionManager sessionManager;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        sessionManager = new SessionManager(this);
        sessionManager.createUserFirebaseNotificationToken(token);

        RegisterFirebaseToServer registerFirebaseToServer = new RegisterFirebaseToServer(this);
        registerFirebaseToServer.postToRemoteServer(android_id, token);
    }
}
