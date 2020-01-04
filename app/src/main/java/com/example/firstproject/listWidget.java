package com.example.firstproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class listWidget extends AppWidgetProvider {

    private static final String MyOnClick1 = "btGoToWeb";
    private static final String MyOnClick2 = "prev";
    private static final String MyOnClick3 = "next";
    public static int[] picArray = new int[]{R.drawable.pepefroggie, R.drawable.im, R.drawable.internettroll};
    private static int counter;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);
        views.setOnClickPendingIntent(R.id.btGoToWeb, getPendingSelfIntent(context, MyOnClick1));
        views.setOnClickPendingIntent(R.id.btPrev, getPendingSelfIntent(context, MyOnClick2));
        views.setOnClickPendingIntent(R.id.btNext, getPendingSelfIntent(context, MyOnClick3));
        counter = 0;

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

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
        if (MyOnClick1.equals(intent.getAction())){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            context.startActivity(browserIntent);
        }
        if(MyOnClick2.equals(intent.getAction())){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);

            if(counter == 0){
                Log.d("Tomek", "Jest 0");
                counter = picArray.length-1;
                Log.d("Tomek", counter+"");
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }else{
                Log.d("Tomek", "Jest inna niz 0");
                counter = counter-1;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }
        }

        if(MyOnClick3.equals(intent.getAction())){
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_widget);

            if(counter == (picArray.length-1)){
                counter = 0;
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }else{
                Log.d("Tomek", "Jest inna niz 0 przy nastepnym. przed zmiana: " + counter);
                counter = counter + 1;
                Log.d("Tomek", "Jest inna niz 0 przy nastepnym. po zmianie: " + counter);
                views.setImageViewResource(R.id.imageView4, picArray[counter]);
                AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, listWidget.class), views);
            }



        }
    }
}

