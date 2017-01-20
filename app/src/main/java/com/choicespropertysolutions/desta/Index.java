package com.choicespropertysolutions.desta;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.choicespropertysolutions.desta.DialogBox.NotifyNetworkConnectionMessage;
import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;

public class Index extends BaseActivity implements View.OnClickListener {

    private static Index instance = new Index();
    static Context context;
    private Button uploadImagesForm;
    private TextView notifyText;
    private Button galleryImages;
    private WebView indexwebview;
    private ProgressDialog progressDialog;

    public static Index getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        //notifyText = (TextView) findViewById(R.id.notifyText);
        //notifyText.setSelected(true);

        ConnectivityManager cm =
                (ConnectivityManager)Index.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();

        if(isConnected) {
            /*uploadImagesForm = (Button) findViewById(R.id.uploadImagesForm);
            uploadImagesForm.setOnClickListener(this);
            galleryImages = (Button) findViewById(R.id.galleryImages);
            galleryImages.setOnClickListener(this);*/
            indexwebview = (WebView) findViewById(R.id.indexwebview);
            indexwebview.getSettings().setJavaScriptEnabled(true);
            //myWebView.setWebViewClient(new WebViewClient());
            indexwebview.setWebViewClient(new MyWebViewClient());

            isCheckLogin();
        }
        else {
            Intent networkReciever = new Intent(Index.this, NotifyNetworkConnectionMessage.class);
            networkReciever.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            networkReciever.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Index.this.startActivity(networkReciever);
        }
    }

    public void isCheckLogin() {
        boolean checkLogin = sessionManager.isLoggedIn();
        if (!checkLogin) {
            // start your home screen
            Intent intent = new Intent(Index.this, Login.class);
            startActivity(intent);
            this.finish();
        }
        else {
            indexwebview.loadUrl("http://destatalk.com/desta-krushi-parivar/#primary");
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait");
        }
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
    public void onClick(View view) {
        /*if(view.getId() == R.id.uploadImagesForm) {
            Intent intent = new Intent(this, ImageUploadForm.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.galleryImages) {
            Intent intent = new Intent(this, GalleryImages.class);
            startActivity(intent);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = Index.this.getPackageManager();
        ComponentName component = new ComponentName(Index.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PackageManager pm = Index.this.getPackageManager();
        ComponentName component = new ComponentName(Index.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.GET_ACTIVITIES);
    }
}