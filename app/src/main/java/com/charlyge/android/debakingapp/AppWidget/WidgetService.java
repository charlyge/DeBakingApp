package com.charlyge.android.debakingapp.AppWidget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import static com.charlyge.android.debakingapp.RecipesActivity.INGREDIENT_KEY;
import static com.charlyge.android.debakingapp.fragments.SelectRecipeDetailFragment.MY_PREFERENCE;

public class WidgetService extends IntentService {

    private static String SERVICE_ACTION = "serviceaction";

    public static void StartWidgetService(Context context){
        Intent intent = new Intent(context,WidgetService.class);
        intent.setAction(SERVICE_ACTION);
        context.startService(intent);
    }


    // For Android O and above
    public static void StartWidgetServiceO(Context context){
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(SERVICE_ACTION);
        ContextCompat.startForegroundService(context,intent);
    }

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(SERVICE_ACTION)) {
            UpdateIngredientWidget();

        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "Channelid";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Yummio service",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    private void UpdateIngredientWidget() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCE,MODE_PRIVATE);
        String ingredienlist = sharedPreferences.getString(INGREDIENT_KEY,"");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,BakingAppWidget.class));
        BakingAppWidget.UpdateIngredientWidget(this,appWidgetManager,widgetIds,ingredienlist);


    }
}
