package com.choicespropertysolutions.desta.Services;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.choicespropertysolutions.desta.Index;
import com.choicespropertysolutions.desta.ImageUploadForm;
import com.choicespropertysolutions.desta.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String str;
    Intent intent;
    NotificationCompat.Builder notificationBuilder;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        str = remoteMessage.getFrom();
        sendNotification(remoteMessage);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void sendNotification(RemoteMessage remoteMessage) {
        if(Objects.equals(remoteMessage.getData().get("PET_NOTIFICATION_TYPE"), "OPEN_ACTIVITY_PET_DETAILS")) {
            intent = new Intent(this, ImageUploadForm.class);
            intent.putExtra("PET_FIRST_IMAGE", remoteMessage.getData().get("PET_FIRST_IMAGE"));
            intent.putExtra("PET_SECOND_IMAGE", remoteMessage.getData().get("PET_SECOND_IMAGE"));
            intent.putExtra("PET_THIRD_IMAGE", remoteMessage.getData().get("PET_THIRD_IMAGE"));
            intent.putExtra("PET_BREED", remoteMessage.getData().get("PET_BREED"));
            intent.putExtra("PET_LISTING_TYPE", remoteMessage.getData().get("PET_LISTING_TYPE"));
            intent.putExtra("PET_AGE_MONTH", remoteMessage.getData().get("PET_AGE_MONTH"));
            intent.putExtra("PET_AGE_YEAR", remoteMessage.getData().get("PET_AGE_YEAR"));
            intent.putExtra("PET_GENDER", remoteMessage.getData().get("PET_GENDER"));
            intent.putExtra("PET_DESCRIPTION", remoteMessage.getData().get("PET_DESCRIPTION"));
            intent.putExtra("POST_OWNER_MOBILENO", remoteMessage.getData().get("POST_OWNER_MOBILENO"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    //.setSmallIcon(R.mipmap.ic_launcher_short)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher))
                    .setContentTitle(remoteMessage.getData().get("PET_BREED"))
                    .setContentText(remoteMessage.getData().get("PET_DESCRIPTION"))
                    .setPriority(2)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("PET_DESCRIPTION")))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLights(Color.GREEN, 1000, 1000)
                    .setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
