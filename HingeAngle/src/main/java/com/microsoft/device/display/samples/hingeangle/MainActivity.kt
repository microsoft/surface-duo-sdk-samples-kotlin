/*
 * Copyright (c) Microsoft Corporation. All rights reserved.Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.hingeangle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class MainActivity : AppCompatActivity(), ScreenInfoListener {

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

    private fun useSingleMode() {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.first_container_id,
                SingleScreenFragment(),
                null
            ).commit()
    }
}