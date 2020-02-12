/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.device.display.samples.extendcanvas

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.extend.R
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var placeToGo = ""
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        web_view.apply {
            settings.javaScriptEnabled = true
            // Injects the supplied Java object into WebView
            addJavascriptInterface(this@MainActivity, "AndroidFunction")
            webViewClient = WebViewClient()
            loadUrl("file:///android_asset/googlemapsearch.html")
        }
    }

    private fun startSearch() {
        placeToGo = searchView.query.toString()
        setupWebView()
        hideKeyboard()
    }

    // This callback is for assets/googlemapsearch.html
    @JavascriptInterface
    fun placeToGo(): String {
        return placeToGo
    }

    private fun hideKeyboard() {
        currentFocus.also {
            View(this).also {
                (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)?.apply {
                    hideSoftInputFromWindow(it.windowToken, 0)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            if (searchManager.getSearchableInfo(componentName) != null) {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
            }
            isIconifiedByDefault = false
            isFocusable = true
            requestFocusFromTouch()
            onActionViewExpanded()
            setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    startSearch()
                    return@OnTouchListener true
                }
                false
            })
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    startSearch()
                    return false
                }
            })
            searchView = this
        }
        setupWebView()
        return true
    }
}