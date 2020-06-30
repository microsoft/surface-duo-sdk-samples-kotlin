/*
 *
 *  * Copyright (c) Microsoft Corporation. All rights reserved.
 *  * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.dualscreen.layout.ScreenHelper

class MainActivity : AppCompatActivity() {

    private val DEBUG_TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ScreenHelper.isDualMode(this)) {
            Log.d(DEBUG_TAG, "########### dual mode")

            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.dual_screen_start_container_id,
                    SingleScreenFragment(),
                    null
                ).commit()
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.dual_screen_end_container_id,
                    DualScreenFragment(),
                    null
                ).commit()
        } else {
            Log.d(DEBUG_TAG, "########### single mode")
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.single_screen_container_id,
                    SingleScreenFragment(),
                    null
                ).commit()
        }
    }
}