package com.choicespropertysolutions.desta;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.choicespropertysolutions.desta.SessionManager.SessionManager;

import org.jsoup.Connection;

import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    WebView loginwebview;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginwebview = (WebView) findViewById(R.id.loginwebview);
        SessionManager sessionManager = new SessionManager(this);
        if(!sessionManager.isLoggedIn()) {
            loginwebview.loadUrl("http://destatalk.com/logout/?redirect_to=http://destatalk.com/login-2/");
        }
        else {
            loginwebview.loadUrl("http://destatalk.com/login-2/#primary");
        }
        loginwebview.getSettings().setJavaScriptEnabled(true);
        //myWebView.setWebViewClient(new WebViewClient());
        loginwebview.setWebViewClient(new MyWebViewClient());
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
            else if(url.equals("http://destatalk.com/")) {
                loginwebview.loadUrl("http://destatalk.com/login-2/#primary");
            }
            else {
                Intent intent = new Intent(view.getContext(), ViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
            return true;
        }
    }
}
