package com.choicespropertysolutions.desta;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;

public class Result extends BaseActivity {

    private WebView resultwebview;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        resultwebview = (WebView) findViewById(R.id.resultwebview);
        resultwebview.loadUrl("http://destatalk.com/desta-krushi-parivar/?gallery-order=points-down#primary");
        resultwebview.getSettings().setJavaScriptEnabled(true);
        //myWebView.setWebViewClient(new WebViewClient());
        resultwebview.setWebViewClient(new MyWebViewClient());
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
        PackageManager pm = Result.this.getPackageManager();
        ComponentName component = new ComponentName(Result.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Result.this.getPackageManager();
        ComponentName component = new ComponentName(Result.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}
