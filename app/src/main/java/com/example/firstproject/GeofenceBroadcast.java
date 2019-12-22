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

import java.util.List;

public class GeofenceBroadcast extends BroadcastReceiver {
    static final String ACTION_PROCESS_UPDATES =
            "com.example.firstproject.action" +
                    ".PROCESS_UPDATES";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.e("TOMEK", "Goefencing Error " + geofencingEvent.getErrorCode());
            return;
        }

        List<Geofence> triggeredFences = geofencingEvent.getTriggeringGeofences();

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            createNotification(context.getString(R.string.enteringShop) + " " + triggeredFences.get(0).getRequestId(), context);
        }
        else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            createNotification(context.getString(R.string.leavingShop) + " " + triggeredFences.get(0).getRequestId(), context);
        } else {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotification(String shopName, Context context){
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "some_channel_id";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(shopName)
                .setSmallIcon(R.drawable.ic_shopping_cart_black_24dp)
                .setChannelId(channelId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }
}