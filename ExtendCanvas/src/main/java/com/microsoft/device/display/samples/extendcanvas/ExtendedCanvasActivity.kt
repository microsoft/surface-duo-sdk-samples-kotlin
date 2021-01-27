/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *   
 */

package com.microsoft.device.display.samples.extendcanvas

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.extend.R

class ExtendedCanvasActivity : AppCompatActivity() {
    companion object {
        const val GOOGLE_MAPS_URL = "https://www.google.com/maps/@?api=1&map_action=map&center=47.652822, -122.134596&zoom=12"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.extended_canvas_activity_main)
        setupToolbar()
        setupWebView()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(GOOGLE_MAPS_URL)
    }
}