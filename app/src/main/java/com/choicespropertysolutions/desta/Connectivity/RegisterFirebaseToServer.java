package com.choicespropertysolutions.desta.Connectivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.choicespropertysolutions.desta.DialogBox.TimeOut_DialogeBox;
import com.choicespropertysolutions.desta.Services.MyFirebaseInstanceIDService;
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;

import org.json.JSONObject;

public class RegisterFirebaseToServer {
    private static Context context = null;
    String android_id;
    String token;

    public RegisterFirebaseToServer(MyFirebaseInstanceIDService myFirebaseInstanceIDService) {
        this.context = myFirebaseInstanceIDService;
    }

    public void postToRemoteServer(String android_id, String token) {
        String method = "registerFirebaseToken";
        String format = "json";
        this.android_id = android_id;
        this.token = token;

        final String URL = URLInstance.getUrl();
        JSONObject params = new JSONObject();
        try {
            params.put("method", method);
            params.put("format", format);
            params.put("android_id", this.android_id);
            params.put("token", this.token);

        } catch (Exception e) {

        }
        JsonObjectRequest firebaseTokenRequest = new JsonObjectRequest(Request.Method.POST, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.v("Response: %n %s", response.toString());
                        Log.d("",response.toString());
                        //returnResponse(response.getString("loginDetailsResponse"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                }
        );
        firebaseTokenRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(firebaseTokenRequest);
    }
}
