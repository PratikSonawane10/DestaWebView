package com.choicespropertysolutions.desta.DialogBox;

import android.app.Activity;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.choicespropertysolutions.desta.InternetConnectivity.NetworkChangeReceiver;
import com.choicespropertysolutions.desta.SplashScreen;

public class NotifyNetworkConnectionMessage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayAlert();
    }

    private void displayAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet connection!");
        builder.setMessage("Please check your internet connection.").setCancelable(
                false).setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PackageManager pm = NotifyNetworkConnectionMessage.this.getPackageManager();
        ComponentName component = new ComponentName(NotifyNetworkConnectionMessage.this, NetworkChangeReceiver.class);
        pm.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent splashScreenIntent = new Intent(NotifyNetworkConnectionMessage.this, SplashScreen.class);
        splashScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(splashScreenIntent);
    }
}