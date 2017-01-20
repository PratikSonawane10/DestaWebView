package com.choicespropertysolutions.desta;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.choicespropertysolutions.desta.Adapter.SpinnerItemsAdapter;
import com.choicespropertysolutions.desta.Connectivity.SaveEditProfile;
import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EditProfile extends BaseActivity {

    private WebView editprofilewebview;
    private String username;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        HashMap<String, String> user = sessionManager.getUserDetails();
        username = user.get(SessionManager.KEY_USERNAME);
        editprofilewebview = (WebView) findViewById(R.id.editprofilewebview);
        editprofilewebview.loadUrl("http://destatalk.com/user/" + username + "/?profiletab=main&um_action=edit#primary");
        editprofilewebview.getSettings().setJavaScriptEnabled(true);
        //myWebView.setWebViewClient(new WebViewClient());
        editprofilewebview.setWebViewClient(new MyWebViewClient());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.equals("http://destatalk.com/desta-krushi-parivar/?contest=upload-photo")) {
                Intent intent = new Intent(view.getContext(), ImageUploadForm.class);
                startActivity(intent);
            }
            else if(url.equals("http://destatalk.com/desta-krushi-parivar/?contest=contest-profile")) {
                Intent intent = new Intent(view.getContext(), MyImages.class);
                startActivity(intent);
            }
            else if(url.equals("http://destatalk.com/desta-krushi-parivar/?contest=gallery")) {
                Intent intent = new Intent(view.getContext(), GalleryImages.class);
                startActivity(intent);
            }
            else if(url.equals("http://destatalk.com/desta-krushi-parivar/?contest=contest-condition")) {
                Intent intent = new Intent(view.getContext(), Conditions.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(view.getContext(), ViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
            return true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = EditProfile.this.getPackageManager();
        ComponentName component = new ComponentName(EditProfile.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = EditProfile.this.getPackageManager();
        ComponentName component = new ComponentName(EditProfile.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}
