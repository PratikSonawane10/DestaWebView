package com.choicespropertysolutions.desta;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;

public class ViewActivity extends BaseActivity {

    WebView viewwebview;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

        viewwebview = (WebView) findViewById(R.id.viewwebview);
        viewwebview.loadUrl(url + "#primary");
        viewwebview.getSettings().setJavaScriptEnabled(true);
        //myWebView.setWebViewClient(new WebViewClient());
        viewwebview.setWebViewClient(new MyWebViewClient());
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
            if(url.indexOf("http://destatalk.com") > 0) {
                Intent intent = new Intent(view.getContext(), ViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
            else {
                viewwebview.loadUrl(url);
            }

            return true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = ViewActivity.this.getPackageManager();
        ComponentName component = new ComponentName(ViewActivity.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = ViewActivity.this.getPackageManager();
        ComponentName component = new ComponentName(ViewActivity.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}
