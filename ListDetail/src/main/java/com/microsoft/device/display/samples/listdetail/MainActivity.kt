/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.listdetail.extensions.isInPortrait
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class MainActivity : AppCompatActivity(), ScreenInfoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set the first image as the default selection if none is selected
        val viewModel = ViewModelProvider(this).get(SelectionViewModel::class.java)
        if (viewModel.getSelectedItemLiveData().value == 0) {
            viewModel.setSelectedItemLiveData(R.drawable.image_1)
        }
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        setupLayout(screenInfo)
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    private fun setupLayout(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode() && !screenInfo.isInPortrait) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ItemsListFragment(), null)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.second_container_id, ItemDetailFragment(), null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ItemsListFragment(), null)
                .commit()
        }
    }
}