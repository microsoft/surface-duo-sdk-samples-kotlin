/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.samples.widget.components

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.microsoft.device.display.samples.widget.Helper
import com.microsoft.device.display.samples.widget.R
import com.microsoft.device.display.samples.widget.service.WidgetApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class WidgetFactory(private val context: Context) :
    RemoteViewsFactory {
    private val widgetItems: MutableList<WidgetItem> =
        ArrayList<WidgetItem>()

    override fun onCreate() {
        fetchWebData()
    }

    private fun fetchWebData() {
        // Making Http client

        // Making Http client
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        // Retrofit constructor for surface duo blog data request

        // Retrofit constructor for surface duo blog data request
        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl("https://devblogs.microsoft.com/surface-duo/")
            .client(okHttpClient).build()

        val widgetApi = retrofit.create(WidgetApi::class.java)

        // Making surface duo blog data request asynchronous

        // Making surface duo blog data request asynchronous
        val stringCall = widgetApi.stringResponse
        stringCall!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                parseResponse(response)
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
            }
        })
    }

    private fun parseResponse(response: Response<String?>) {
        if (response.isSuccessful) {
            parseSusccesfulResponse(response.body()!!)
            updateWidgets(context)
        }
    }

    private fun parseSusccesfulResponse(body: String) {
        val doc: Document = Jsoup.parse(body)
        // Pinpoint to the html class that holds item's list
        val itemElements: Elements = doc.getElementsByClass("tab-pane fade in active show")
        parseHtmlElements(itemElements)
    }

    private fun parseHtmlElements(elements: Elements) {
        // Iterate over all html elements to pinpoint to our list (in our case, surface duo posts)
        for (element in elements) {
            if (element.getElementsByClass("entry-box container") != null) {
                val boxElements: Elements = element.getElementsByClass("entry-box container")
                parseEntryBoxElements(boxElements)
            }
        }
    }

    private fun parseEntryBoxElements(elements: Elements) {
        // Pinpoint to the data holder (entry-area row inner-border html class)
        for (element in elements) {
            if (element.getElementsByClass("entry-area row inner-border") != null) {
                // Create the widget item that will hold our posts data
                val widgetItem = WidgetItem()
                val entryAreaElements: Elements =
                    element.getElementsByClass("entry-area row inner-border")
                parseItemEntryArea(entryAreaElements, widgetItem)

                // Add all elements to adapter's internal data holder
                widgetItems.add(widgetItem)
            }
        }
    }

    private fun parseItemEntryArea(elements: Elements, widgetItem: WidgetItem) {
        // Pinpoint to each item's entry-area row inner-border element (post logo and text elements)
        for (element in elements) {
            fetchPostLogo(element, widgetItem)
            parseItemTextElements(element, widgetItem)
        }
    }

    private fun fetchPostLogo(element: Element, widgetItem: WidgetItem) {
        // Pinpoint to logo and fetch it
        if (element.getElementsByClass("entry-image col-md-4") != null) {
            val images: Elements = element.getElementsByClass("entry-image col-md-4").select("img")
            for (image in images) {
                if (!image.absUrl("src").isEmpty()) {
                    widgetItem.image = image.absUrl("src")
                }
            }
        }
    }

    private fun parseItemTextElements(element: Element, widgetItem: WidgetItem) {
        // Pinpoint entry-content col-md-8 class that holds all text elements
        if (element.getElementsByClass("entry-content col-md-8") != null) {
            // Save all text raw data as we don't have a specific element for preview
            val rawText: String = element.getElementsByClass("entry-content col-md-8").text()

            // Pinpoint to header elements
            val headerElements: Elements = element.getElementsByClass("entry-content col-md-8")
            // Intent for each list element where we add http link for each post
            fetchPostTextParseHeaderElements(headerElements, widgetItem, rawText)
        }
    }

    private fun fetchPostTextParseHeaderElements(
        elements: Elements,
        widgetItem: WidgetItem,
        rawText: String
    ) {
        // Pinpoint to header elements
        for (element in elements) {
            if (element.getElementsByClass("entry-header") != null) {

                // Fetch header and substract all header elements from raw text
                // Doing this to get the post text preview
                val header: String = element.getElementsByClass("entry-header").text()
                widgetItem.body = rawText.replace(header, "")

                // Parse all header elements and add them to WidgetItem
                val headerContents: Elements = element.getElementsByClass("entry-header")
                fetchPostTitleLinkDate(headerContents, widgetItem)
            }
        }
    }

    private fun fetchPostTitleLinkDate(elements: Elements, widgetItem: WidgetItem) {
        for (element in elements) {

            // Title
            if (element.getElementsByClass("entry-title") != null) {
                widgetItem.title = element.getElementsByClass("entry-title").text()
            }

            // Post http link (for item click action)
            if (element.getElementsByClass("entry-title").select("a") != null) {
                widgetItem.href =
                    element.getElementsByClass("entry-title").select("a").first().attr("href")
            }

            // Date
            if (element.getElementsByClass("entry-meta") != null) {
                val entryMetas: Elements = element.getElementsByClass("entry-meta")
                for (entryMeta in entryMetas) {
                    if (entryMeta.getElementsByClass("entry-post-date") != null) {
                        widgetItem.date = entryMeta.getElementsByClass("entry-post-date").text()
                    }
                }
            }
        }
    }

    override fun onDataSetChanged() {}
    override fun onDestroy() {
        widgetItems.clear()
    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv =
            RemoteViews(context.packageName, R.layout.collection_widget_list_item)

        // Widget title
        rv.setTextViewText(R.id.widget_item_title, widgetItems[position].title)

        // Widget date
        rv.setTextViewText(R.id.widget_item_date, widgetItems[position].date)

        // Widget text body
        rv.setTextViewText(R.id.widget_item_content, widgetItems[position].body)

        // Widget logo
        try {
            val bitmap: Bitmap = Glide.with(context)
                .asBitmap()
                .load(widgetItems[position].image)
                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
            rv.setImageViewBitmap(
                R.id.widget_item_preview,
                Bitmap.createScaledBitmap(
                    bitmap,
                    Helper.convertDpToPixel(90, context),
                    Helper.convertDpToPixel(80, context), false
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Intent for each list element where we add http link for each post
        addIntentForWidgetItem(rv, widgetItems[position])
        return rv
    }

    private fun addIntentForWidgetItem(
        remoteViews: RemoteViews,
        widgetItem: WidgetItem
    ) {
        val extras = Bundle()
        extras.putString(ACTION_INTENT_VIEW_HREF_TAG, widgetItem.href)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent)
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    companion object {
        const val ACTION_INTENT_VIEW_TAG = "action_intent_view_tag"
        const val ACTION_INTENT_VIEW_HREF_TAG = "action_intent_view_href_tag"

        // Update Widgets method used after http request is made to add data to the widget
        private fun updateWidgets(context: Context) {
            val mgr = AppWidgetManager.getInstance(context)
            val cn = ComponentName(context, WidgetApp::class.java)
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetListView)
        }
    }
}