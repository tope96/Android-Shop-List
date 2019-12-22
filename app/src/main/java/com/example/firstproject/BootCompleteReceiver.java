package com.example.firstproject;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class BootCompleteReceiver extends BroadcastReceiver {
    private GeofencingClient gc;
    private LocationRequest mLocationRequest;
    private static final long UPDATE_INTERVAL = 10 * 1000;
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;

    @Override
    public void onReceive(final Context context, Intent intent) {
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference shopsRef = rootRef.collection("shops");

        gc = LocationServices.getGeofencingClient(context);

        shopsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        double lat = (double) document.get("latitude");
                        double longi = (double) document.get("longitude");
                        String title = document.getString("name");
                        long radius = (long) document.get("radius");

                        Geofence geo = new Geofence.Builder().setRequestId(title)
                                .setCircularRegion(lat, longi, radius)
                                .setExpirationDuration(1000*60*60)
                                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                                .build();

                        gc.addGeofences(getGeofencingRequest(geo),getGeofencePendingIntent());

                    }
                }
            }

            private GeofencingRequest getGeofencingRequest(Geofence geo) {
                GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
                builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
                builder.addGeofence(geo);
                createLocationRequest();
                return builder.build();
            }

            private PendingIntent getGeofencePendingIntent() {
                Intent intent = new Intent(context, GeofenceBroadcast.class);
                intent.setAction(GeofenceBroadcast.ACTION_PROCESS_UPDATES);
                return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            private void createLocationRequest() {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(UPDATE_INTERVAL);
                mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
            }
        });


    }
}
