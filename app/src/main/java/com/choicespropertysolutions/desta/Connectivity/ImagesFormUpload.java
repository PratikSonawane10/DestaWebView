package com.choicespropertysolutions.desta.Connectivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.widget.Toast;
import com.android.volley.*;
import com.choicespropertysolutions.desta.DialogBox.TimeOut_DialogeBox;
import com.choicespropertysolutions.desta.ImageUploadForm;
import com.choicespropertysolutions.desta.Singleton.URLInstance;
import com.choicespropertysolutions.desta.app.AppController;
import com.choicespropertysolutions.desta.ImageUploadForm;

import org.json.JSONObject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public  class ImagesFormUpload {
    private static final String SERVER_URL = URLInstance.getUrl();
    private static Context context;
    private static Map<String, String> headerPart;
    private static Map<String, File> filePartData;
    private static Map<String, String> stringPart;
    public static ImageUploadForm imageFormActivity;

    //public static void uploadToRemoteServer(String imageCategoryName, String firstImagePath, String secondImagePath, String thirdImagePath, String fourthImagePath, String fifthImagePath, String phone, ImageUploadForm imageUploadForm) throws Exception {
    public static void uploadToRemoteServer(String firstImagePath, String secondImagePath, String thirdImagePath, String fourthImagePath, String fifthImagePath, String phone, ImageUploadForm imageUploadForm) throws Exception {

        imageFormActivity = imageUploadForm;
        context = imageUploadForm.getApplicationContext();
        int serverResponseCode = 0;
        //String categoryOfImage = imageCategoryName;
        String firstPetImage = firstImagePath;
        String secondPetImage = secondImagePath;
        String thirdPetImage = thirdImagePath;
        String fourthPetImage = fourthImagePath;
        String fifthPetImage = fifthImagePath;
        String userphone = phone;
        String method = "savePhotoDetails";
        String format = "json";

        //Auth header
        headerPart = new HashMap<>();
        headerPart.put("Content-type", "multipart/form-data;");

        //File part
        filePartData = new HashMap<>();
        if(!firstPetImage.isEmpty() && firstPetImage != null) {
            filePartData.put("firstImage", new File(firstPetImage));
        }
        if(!secondPetImage.isEmpty() && secondPetImage != null) {
            filePartData.put("secondImage", new File(secondPetImage));
        }
        if(!thirdPetImage.isEmpty() && thirdPetImage != null) {
            filePartData.put("thirdImage", new File(thirdPetImage));
        }
        if(!fourthPetImage.isEmpty() && fourthPetImage != null) {
            filePartData.put("fourthImage", new File(fourthPetImage));
        }
        if(!fifthPetImage.isEmpty() && fifthPetImage != null) {
            filePartData.put("fifthImage", new File(fifthPetImage));
        }

        //String part
        stringPart = new HashMap<>();
        //stringPart.put("categoryOfPhoto", categoryOfImage);
        stringPart.put("mobileNo", userphone);
        stringPart.put("method", method);
        stringPart.put("format", format);

        new UploadToServerCustomRequest().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public static class UploadToServerCustomRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            CustomMultipartRequest petFormUploadCustomRequest = new CustomMultipartRequest(Request.Method.POST, context, SERVER_URL, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Toast.makeText(context, "Succefully Uploaded", Toast.LENGTH_LONG).show();
                    imageFormActivity.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context, "Error Uploading Images", Toast.LENGTH_LONG).show();
                    Intent gotoTimeOutError = new Intent(context, TimeOut_DialogeBox.class);
                    gotoTimeOutError.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(gotoTimeOutError);
                }
            }, filePartData, stringPart, headerPart);
            petFormUploadCustomRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(petFormUploadCustomRequest);
            return null;
        }
    }
}
