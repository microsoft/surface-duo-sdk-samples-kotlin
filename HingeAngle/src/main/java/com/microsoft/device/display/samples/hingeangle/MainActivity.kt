/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class MainActivity : AppCompatActivity(), ScreenInfoListener {

    private val DEBUG_TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        ScreenManagerProvider.getScreenManager().onConfigurationChanged()
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        setupLayout(screenInfo)
    }

    private fun setupLayout(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            useDualMode(screenInfo)
        } else {
            useSingleMode()
        }
    }

    private fun useDualMode(screenInfo: ScreenInfo) {
        Log.d(DEBUG_TAG, "########### dual mode")
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.first_container_id,
                SingleScreenFragment(),
                null
            ).commit()
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.second_container_id,
                DualScreenFragment(),
                null
            ).commit()
    }

    private fun useSingleMode() { // Setting layout for single portrait
        Log.d(DEBUG_TAG, "########### single mode")
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.first_container_id,
                SingleScreenFragment(),
                null
            ).commit()
    }
}