/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.widget.components

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.microsoft.device.display.samples.widget.R
import com.microsoft.device.display.samples.widget.service.WidgetService

/**
 * Implementation of App Widget functionality.
 */
class WidgetApp : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

            // Adding Remote Views adapter for every widget instance
            val views = RemoteViews(
                context.packageName,
                R.layout.collection_widget
            )
            val intent = Intent(context, WidgetService::class.java)
            views.setRemoteAdapter(R.id.widgetListView, intent)

            // Adding pendingIntent for widget list View
            val clickIntent = Intent(context, WidgetApp::class.java)
            clickIntent.action = WidgetFactory.ACTION_INTENT_VIEW_TAG
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val clickPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.widgetListView, clickPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Handling intent for list item click
        if (WidgetFactory.ACTION_INTENT_VIEW_TAG.equals(intent.action)) {
            val url =
                intent.getStringExtra(WidgetFactory.ACTION_INTENT_VIEW_HREF_TAG)
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(webIntent)
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
