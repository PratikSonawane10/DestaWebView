package com.choicespropertysolutions.desta;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.choicespropertysolutions.desta.Adapter.SpinnerItemsAdapter;
import com.choicespropertysolutions.desta.Connectivity.RegisterToServer;
import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;
import com.choicespropertysolutions.desta.SessionManager.SessionManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Register extends AppCompatActivity {

    WebView registerwebview;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerwebview = (WebView) findViewById(R.id.registerwebview);
        registerwebview.loadUrl("http://destatalk.com/register-2/#primary");
        registerwebview.getSettings().setJavaScriptEnabled(true);
        //myWebView.setWebViewClient(new WebViewClient());
        registerwebview.setWebViewClient(new MyWebViewClient());
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
            else if(url.equals("http://destatalk.com/register-2/")) {
                Intent intent = new Intent(view.getContext(), Register.class);
                startActivity(intent);
            }
            else if(url.equals("http://destatalk.com/login-2/")) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
            else if(url.indexOf("destatalk.com/user/") > 0) {
                URL newURL = null;
                try {
                    newURL = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String filename = newURL.getFile();
                String[] urlSplit = filename.split("/");
                String username = urlSplit[2];
                SessionManager sessionManager = new SessionManager(view.getContext());
                sessionManager.createUserLoginSession(username);
                Intent intent = new Intent(view.getContext(), Index.class);
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
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Register.this.getPackageManager();
        ComponentName component = new ComponentName(Register.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}
