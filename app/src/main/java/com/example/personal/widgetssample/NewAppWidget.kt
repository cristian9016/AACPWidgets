package com.example.personal.widgetssample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.*
import com.example.personal.widgetssample.R.id.appWidgetId
import com.example.personal.widgetssample.R.id.appWidgetId





/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {

        const val sharedP = "com.example.android.widgetsample"
        const val countKey = "count"

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            val intent = Intent(context,NewAppWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val idArray = intArrayOf(appWidgetId)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,idArray)
            val pendingIntent = PendingIntent.getBroadcast(context,appWidgetId,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            val prefs = context.getSharedPreferences(sharedP,0)
            var count = prefs.getInt(countKey+appWidgetId,0)
            count++
            val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
//            views.setTextViewText(R.id.appwidget_text, widgetText)
            views.setTextViewText(R.id.appWidgetId,appWidgetId.toString())
            views.setTextViewText(R.id.appWidgetUpdate,context.resources.getString(R.string.date_count_format,count,dateString))

            views.setOnClickPendingIntent(R.id.btnUpdate,pendingIntent)

            prefs.edit().putInt(countKey+appWidgetId,count).apply()
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

