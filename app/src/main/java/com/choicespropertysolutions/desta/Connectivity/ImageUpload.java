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
import com.choicespropertysolutions.desta.ImageUploadForm;
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class ImageUpload {
    private static String firstImagePath = "";
    private static String secondImagePath = "";
    private static String thirdImagePath = "";
    private static String fourthImagePath = "";
    private static String fifthImagePath = "";
    private static String firstImageName = "";
    private static String secondImageName = "";
    private static String thirdImageName = "";
    private static String fourthImageName = "";
    private static String fifthImageName = "";
    private static List<String> firstImageTagList;
    private static List<String> secondImageTagList;
    private static List<String> thirdImageTagList;
    private static List<String> fourthImageTagList;
    private static List<String> fifthImageTagList;
    private static String method;
    private static String format;
    private static Context context;

    public ImageUpload(ImageUploadForm imageUploadForm) {
        context = imageUploadForm;
    }

    public static void uploadToRemoteServer(String firstImagePath, String firstImageName, List<String> firstImageTagList, String secondImagePath, String secondImageName, List<String> secondImageTagList, String thirdImagePath, String thirdImageName, List<String> thirdImageTagList, String fourthImagePath, String fourthImageName, List<String> fourthImageTagList, String fifthImagePath, String fifthImageName, List<String> fifthImageTagList, String phone, final ImageUploadForm imageUploadForm, final ProgressDialog progressDialog) {
        String url = URLInstance.getUrl();
        ImageUpload.firstImagePath = firstImagePath;
        ImageUpload.secondImagePath = secondImagePath;
        ImageUpload.thirdImagePath = thirdImagePath;
        ImageUpload.fourthImagePath = fourthImagePath;
        ImageUpload.fifthImagePath = fifthImagePath;

        ImageUpload.firstImageName = firstImageName;
        ImageUpload.secondImageName = secondImageName;
        ImageUpload.thirdImageName = thirdImageName;
        ImageUpload.fourthImageName = fourthImageName;
        ImageUpload.fifthImageName = fifthImageName;

        ImageUpload.firstImageTagList = firstImageTagList;
        ImageUpload.secondImageTagList = secondImageTagList;
        ImageUpload.thirdImageTagList = thirdImageTagList;
        ImageUpload.fourthImageTagList = fourthImageTagList;
        ImageUpload.fifthImageTagList = fifthImageTagList;
        method = "savePhotoDetails";
        format = "json";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", method);
        params.put("format", format);
        params.put("userId", "8");

        params.put("firstImage", ImageUpload.firstImagePath);
        params.put("secondImage", ImageUpload.secondImagePath);
        params.put("thirdImage", ImageUpload.thirdImagePath);
        params.put("fourthImage", ImageUpload.fourthImagePath);
        params.put("fifthImage", ImageUpload.fifthImagePath);

        params.put("firstImageName", ImageUpload.firstImageName);
        params.put("secondImageName", ImageUpload.secondImageName);
        params.put("thirdImageName", ImageUpload.thirdImageName);
        params.put("fourthImageName", ImageUpload.fourthImageName);
        params.put("fifthImageName", ImageUpload.fifthImageName);

        JSONArray arrayOfSelectedFirstImageTag = new JSONArray(ImageUpload.firstImageTagList);
        JSONArray arrayOfSelectedSecondImageTag = new JSONArray(ImageUpload.secondImageTagList);
        JSONArray arrayOfSelectedThirdImageTag = new JSONArray(ImageUpload.thirdImageTagList);
        JSONArray arrayOfSelectedFourthImageTag = new JSONArray(ImageUpload.fourthImageTagList);
        JSONArray arrayOfSelectedFifthImageTag = new JSONArray(ImageUpload.fifthImageTagList);
        params.put("firstImageTags", arrayOfSelectedFirstImageTag.toString());
        params.put("secondImageTags", arrayOfSelectedSecondImageTag.toString());
        params.put("thirdImageTags", arrayOfSelectedThirdImageTag.toString());
        params.put("fourthImageTags", arrayOfSelectedFourthImageTag.toString());
        params.put("fifthImageTags", arrayOfSelectedFifthImageTag.toString());

        JsonObjectRequest petFilterListRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.hide();
                        Toast.makeText(context, "Succefully Uploaded", Toast.LENGTH_LONG).show();
                        imageUploadForm.finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                VolleyLog.d("Error: " + error.getMessage());
                Intent gotoTimeOutError = new Intent(context, TimeOut_DialogeBox.class);
                context.startActivity(gotoTimeOutError);
            }
        });
        petFilterListRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(petFilterListRequest);
    }
}