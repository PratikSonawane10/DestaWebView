package com.choicespropertysolutions.desta.Connectivity;

import android.app.ProgressDialog;
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
import com.choicespropertysolutions.desta.Index;
import com.choicespropertysolutions.desta.Register;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RegisterToServer {

    private static Context context = null;
    private static String username;
    private static String userphone;
    private static String userstate;
    private static String useremail;
    private static String userpassword;
    private static String method;
    private static String format;
    private static String registerResponse;
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;

    public static String uploadToRemoteServer(String name, String phone, String state, String email, String password) throws Exception {
        method = "userRegistration";
        format = "json";
        username = name;
        userphone = phone;
        userstate = state;
        useremail = email;
        userpassword = password;
        final String URL = URLInstance.getUrl();
        JSONObject params = new JSONObject();
        try {
            params.put("method", method);
            params.put("format", format);
            params.put("name", username);
            params.put("mobileno", userphone);
            params.put("state", userstate);
            params.put("email", useremail);
            params.put("confirmpassword", userpassword);
        } catch (Exception e) {

        }
        JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.v("Response:%n %s", response.toString());
                        Register register = new Register();
                        try {
                            returnResponse(response.getString("saveUsersDetailsResponse"));
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
        }
        );
        registerRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(registerRequest);
        return registerResponse;
    }

    public RegisterToServer(Context context) {
        this.context = context;
    }

    public static void returnResponse(String response) {
        SessionManager sessionManager;
        sessionManager = new SessionManager(context);
        if (response.equals("USERS_DETAILS_SAVED")) {
            Toast.makeText(context, "Successfully Registered.", Toast.LENGTH_SHORT).show();
            sessionManager.createUserLoginSession(username);
            Intent gotoindexpage = new Intent(context, Index.class);
            gotoindexpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotoindexpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(gotoindexpage);
        } else if (response.equals("No_Is_Already_Registered")) {
            Toast.makeText(context, "This no is already registered!", Toast.LENGTH_SHORT).show();
            sessionManager.createUserLoginSession(username);
            Intent gotoindexpage = new Intent(context, Index.class);
            gotoindexpage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            gotoindexpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(gotoindexpage);
        } else {
            Intent gotosignupgae = new Intent(context, Register.class);
            context.startActivity(gotosignupgae);
        }
    }
}
