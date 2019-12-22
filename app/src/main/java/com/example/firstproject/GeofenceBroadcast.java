package com.example.firstproject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcast extends BroadcastReceiver {
    static final String ACTION_PROCESS_UPDATES =
            "com.example.firstproject.action" +
                    ".PROCESS_UPDATES";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);


        if (geofencingEvent.hasError()) {
            Log.e("TOMEK", "Goefencing Error " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Wejscie")
                    .setSmallIcon(R.drawable.ic_add_white_24dp)
                    .setChannelId(channelId)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);
        }
        else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("Wyjscie")
                    .setSmallIcon(R.drawable.ic_add_white_24dp)
                    .setChannelId(channelId)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(1, notification);
        } else {

        }
    }

    private void notification(){

    }
}