/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.content.Context
import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.core.ScreenHelper

class MainActivity : AppCompatActivity() {
    companion object {
        fun isPortrait(context: Context): Boolean {
            return ScreenHelper.getCurrentRotation(context) == Surface.ROTATION_90 ||
                ScreenHelper.getCurrentRotation(context) == Surface.ROTATION_270
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ScreenHelper.isDualMode(this) && !isPortrait(applicationContext)) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.first_container_id,
                    ItemsListFragment(),
                    null
                ).commit()
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.second_container_id,
                    ItemDetailFragment()
                ).commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.first_container_id,
                    ItemsListFragment(),
                    null
                )
                .commit()
        }

        // set the first image as the default selection if none is selected
        val viewModel = ViewModelProvider(this).get(SelectionViewModel::class.java)
        if (viewModel.getSelectedItemLiveData().value == 0) {
            viewModel.setSelectedItemLiveData(R.drawable.image_1)
        }
    }
}