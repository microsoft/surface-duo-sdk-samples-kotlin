/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.intentsecondscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        second_activity_button.setOnClickListener { startIntentSecondActivity() }
        link_button.setOnClickListener {
            startIntentBrowserApp(resources.getString(R.string.web_link))
        }
        map_button.setOnClickListener {
            startIntentBrowserApp(resources.getString(R.string.map_link))
        }
    }

    private fun startIntentSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        // Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT is required to launch a second activity
        // on a second display while still keeping the first activity on the first display
        // (not pausing/stopping it)
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun startIntentBrowserApp(urlString: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
        intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
