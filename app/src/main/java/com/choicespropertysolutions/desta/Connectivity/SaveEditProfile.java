package com.choicespropertysolutions.desta.Connectivity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.choicespropertysolutions.desta.DialogBox.TimeOut_DialogeBox;
import com.choicespropertysolutions.desta.EditProfile;
import com.choicespropertysolutions.desta.Index;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SaveEditProfile {

    private static Context context = null;
    private static String username;
    private static String userphone;
    private static String useroldphone;
    private static String userstate;
    private static String method;
    private static String format;
    private static String editResponse;
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public static String uploadEditedDetails(String name, String phone, String oldphone, String state) throws Exception {
        method = "editProfile";
        format = "json";
        username = name;
        userphone = phone;
        useroldphone = oldphone;
        userstate = state;
        final String URL = URLInstance.getUrl();

        JSONObject params = new JSONObject();
        try {
            params.put("method", method);
            params.put("format", format);
            params.put("name", username);
            params.put("mobileNo", userphone);
            params.put("oldMobileNo", useroldphone);
            params.put("state", userstate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest saveEditProfileRequest = new JsonObjectRequest(Request.Method.POST, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.v("Response:%n %s", response.toString());
                        EditProfile editProfile = new EditProfile();
                        try {
                            returnResponse(response.getString("saveUsersEditDetailsResponse"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Intent gotoTimeOutError = new Intent(context, TimeOut_DialogeBox.class);
                context.startActivity(gotoTimeOutError);
            }
        });
        saveEditProfileRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(saveEditProfileRequest);
        return editResponse;
    }

    public SaveEditProfile(Context context) {
        this.context = context;
    }

    public static void returnResponse(String response) {
        SessionManager sessionManager;
        sessionManager = new SessionManager(context);
        if (response.equals("USERS_DETAILS_EDITED")) {
            Toast.makeText(context, "Details Successfully Change.", Toast.LENGTH_SHORT).show();
            sessionManager.logoutUser();
            sessionManager.createUserLoginSession(username);
            Intent gotologinpage = new Intent(context, Index.class);
            context.startActivity(gotologinpage);
        } else if (response.equals("ERROR")) {
            Toast.makeText(context, "Error editing details!", Toast.LENGTH_SHORT).show();
            Intent gotosignupgae = new Intent(context, EditProfile.class);
            context.startActivity(gotosignupgae);
        }
    }
}
