package com.charlyge.android.debakingapp.AppWidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.charlyge.android.debakingapp.model.Ingredients;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.charlyge.android.debakingapp.MainActivity.INGREDIENT_KEY;
import static com.charlyge.android.debakingapp.fragments.SelectRecipeDetailFragment.MY_PREFERENCE;

public class WidgetService extends IntentService {

    private static String SERVICE_ACTION = "serviceaction";

    public static void StartWidgetService(Context context){
        Intent intent = new Intent(context,WidgetService.class);
        intent.setAction(SERVICE_ACTION);
        context.startService(intent);

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

    private void UpdateIngredientWidget() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFERENCE,MODE_PRIVATE);
        String ingredienlist = sharedPreferences.getString(INGREDIENT_KEY,"");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,BakingAppWidget.class));
         BakingAppWidget.UpdateIngredientWidget(this,appWidgetManager,widgetIds,ingredienlist);


    }
}
