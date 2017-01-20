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
/*import com.choicespropertysolutions.desta.SetNewPassword;
import com.choicespropertysolutions.desta.SignIn;*/
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;
import org.json.JSONException;
import org.json.JSONObject;

public class ResetPassword {
    private static Context context = null;

    private static String code;
    private static String password;
    private static String method;
    private static String format;
    private static String setnewpasswordResponse;

    public static String SeToRemoteServer(String code, String password, String email) throws Exception {
        String method = "setNewPassword";
        String format = "json";
        String activationcode = code;
        String newpassword = password;
        String userEmail = email;

        final String URL = URLInstance.getUrl();
        JSONObject params = new JSONObject();
        try {
            params.put("method", method);
            params.put("format", format);
            params.put("code", activationcode);
            params.put("password", newpassword);
            params.put("email", userEmail);

        } catch (Exception e) {

        }
        JsonObjectRequest resetPasswordRequest = new JsonObjectRequest(Request.Method.POST, URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.v("Response: %n %s", response.toString());
                        try {
                            returnResponse(response.getString("setNewPasswordResponse"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                        Intent gotoTimeOutError = new Intent(context, TimeOut_DialogeBox.class);
                        context.startActivity(gotoTimeOutError);
                    }
                }
        );
        resetPasswordRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(resetPasswordRequest);
        return setnewpasswordResponse;

    }

    public ResetPassword(Context context) {
        this.context = context;
    }

    public static void returnResponse(String response) {
        if (response.equals("NEW_PASSWORD_SUCCESSFULLY_SET")) {
            /*Intent gotologinpage = new Intent(context, SignIn.class);
            context.startActivity(gotologinpage);
            Toast.makeText(context, "You Can Login Now", Toast.LENGTH_SHORT).show();*/
        } else if (response.equals("ERROR")) {
            /*Intent gotosetnewpassword = new Intent(context, SetNewPassword.class);
            context.startActivity(gotosetnewpassword);
            Toast.makeText(context, "Please Re-Try", Toast.LENGTH_SHORT).show();*/
        }
    }
}
