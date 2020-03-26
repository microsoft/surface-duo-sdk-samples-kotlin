/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.masterdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.microsoft.device.display.samples.masterdetail.model.DataProvider
import com.microsoft.device.dualscreen.layout.ScreenModeListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MasterDetailApp).surfaceDuoScreenManager.addScreenModeListener(
            object : ScreenModeListener {
                override fun onSwitchToSingleScreenMode() {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.single_screen_container_id,
                            ItemsListFragment(),
                            null
                        )
                        .commit()
                }

                override fun onSwitchToDualScreenMode() {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.dual_screen_start_container_id,
                            ItemsListFragment(),
                            null
                        ).commit()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.dual_screen_end_container_id,
                            ItemDetailFragment.newInstance(DataProvider.movieMocks[0]),
                            null
                        ).commit()
                }
            }
        )
    }
}