package com.example.firstproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import io.opencensus.resource.Resource;

/**
 * Implementation of App Widget functionality.
 */
public class listWidget extends AppWidgetProvider {

    private static final String MyOnClick1 = "btGoToWeb";
    private static final String MyOnClick2 = "prev";
    private static final String MyOnClick3 = "next";
    private static final String PlayMusic = "play";
    private static final String StopMusic = "stop";
    private static final String PrevMusic = "prevMusic";
    private static final String NextMusic = "nextMusic";
    public static int[] picArray = new int[]{R.drawable.pepefroggie, R.drawable.im, R.drawable.internettroll};
    public static int[] soundsArray = new int[]{R.raw.drums, R.raw.strange};
    private static int counter;
    private static int counterMusic;
    static MediaPlayer mPlay = new MediaPlayer();

    @RequiresApi(api = Build.VERSION_CODES.O)
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        mPlay = MediaPlayer.create(context, soundsArray[0]);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);
        views.setOnClickPendingIntent(R.id.btGoToWeb, getPendingSelfIntent(context, MyOnClick1));
        views.setOnClickPendingIntent(R.id.btPrev, getPendingSelfIntent(context, MyOnClick2));
        views.setOnClickPendingIntent(R.id.btNext, getPendingSelfIntent(context, MyOnClick3));
        views.setOnClickPendingIntent(R.id.btPlayMusic, getPendingSelfIntent(context, PlayMusic));
        views.setOnClickPendingIntent(R.id.btStopMusic, getPendingSelfIntent(context, StopMusic));
        views.setOnClickPendingIntent(R.id.btPrevMusic, getPendingSelfIntent(context, PrevMusic));
        views.setOnClickPendingIntent(R.id.btNextMusic, getPendingSelfIntent(context, NextMusic));
        counter = 0;
        counterMusic = 0;
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    protected static PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, listWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);


        if (MyOnClick1.equals(intent.getAction())){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            context.startActivity(browserIntent);
        }
        if(MyOnClick2.equals(intent.getAction())){
            if(counter == 0){
                counter = picArray.length-1;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }else{
                counter = counter-1;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }
        }

        if(MyOnClick3.equals(intent.getAction())){
            if(counter == (picArray.length-1)){
                counter = 0;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }else{
                counter = counter + 1;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }

        }
        if (PlayMusic.equals(intent.getAction())){
            if(mPlay.isPlaying()){
                views.setImageViewResource(R.id.btPlayMusic, R.drawable.ic_play_arrow_white_24dp);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
                mPlay.pause();
            } else {
                views.setImageViewResource(R.id.btPlayMusic, R.drawable.ic_pause_white_24dp);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
                mPlay.start();
            }
        }
        if (StopMusic.equals(intent.getAction())){
            if(mPlay.isPlaying()){
                views.setImageViewResource(R.id.btPlayMusic, R.drawable.ic_play_arrow_white_24dp);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
                mPlay.stop();
                mPlay = MediaPlayer.create(context, soundsArray[counterMusic]);
            }
        }
        if (PrevMusic.equals(intent.getAction())){
            if(counterMusic == 0){
                int i = 0;
                if(mPlay.isPlaying()){
                    i = 1;
                    mPlay.stop();
                }
                counterMusic = soundsArray.length-1;
                mPlay = MediaPlayer.create(context, soundsArray[counterMusic]);
                if(i ==1 ){
                    mPlay.start();
                }
            }else{
                int i = 0;
                if(mPlay.isPlaying()){
                    i = 1;
                    mPlay.stop();
                }
                counterMusic = counterMusic - 1;
                mPlay = MediaPlayer.create(context, soundsArray[counterMusic]);

                if(i ==1 ){
                    mPlay.start();
                }
            }
        }
        if (NextMusic.equals(intent.getAction())){
            if(counterMusic == soundsArray.length-1){
                int i = 0;
                if(mPlay.isPlaying()){
                    i = 1;
                    mPlay.stop();
                }
                counterMusic = 0;
                mPlay = MediaPlayer.create(context, soundsArray[counterMusic]);
                if(i ==1 ){
                    mPlay.start();
                }
            }else{
                int i = 0;
                if(mPlay.isPlaying()){
                    i = 1;
                    mPlay.stop();
                }
                counterMusic = counterMusic + 1;
                mPlay = MediaPlayer.create(context, soundsArray[counterMusic]);

                if(i ==1 ){
                    mPlay.start();
                }
            }
        }
    }
}

