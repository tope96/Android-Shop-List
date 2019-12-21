package com.example.firstproject;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcast extends IntentService {

    private static final String TAG = "GeofenceTransitions";


    public GeofenceBroadcast() {
        super("GeofenceBroadcast");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
    Log.d("TOMEK", "OnHandle");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Goefencing Error " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            Log.d("TOMEK", "wejscie");
            showNotification("Entered", "Entered the Location");
        }
        else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.d("TOMEK", "wyjscie");
            showNotification("Exited", "Exited the Location");
        } else {
            showNotification("Error", "Error");
        }


    }

    public void showNotification(String text, String bigText) {

        // 1. Create a NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // 2. Create a PendingIntent for AllGeofencesActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 3. Create and send a notification
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(text)
                .setContentText(text)
                .setContentIntent(pendingNotificationIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(0, notification);
    }}