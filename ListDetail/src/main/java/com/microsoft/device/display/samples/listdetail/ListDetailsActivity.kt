/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.listdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.microsoft.device.display.samples.listdetail.extensions.TAG
import com.microsoft.device.display.samples.listdetail.extensions.isBackStackEmpty
import com.microsoft.device.display.samples.listdetail.extensions.isInPortrait
import com.microsoft.device.display.samples.listdetail.model.SelectionViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

/**
 * Contains the image gallery and selected image details but only in dual screen mode.
 */
class ListDetailsActivity : AppCompatActivity(), ScreenInfoListener {
    private var screenInfo: ScreenInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_details_activity_main)
        setupToolbar()
        observeSelectedItem()
        observeNavigationStack()
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

    override fun onBackPressed() {
        if (supportFragmentManager.isBackStackEmpty) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun observeSelectedItem() {
        val viewModel = ViewModelProvider(this).get(SelectionViewModel::class.java)
        viewModel.selectedItem.observe(
            this,
            {
                screenInfo?.let {
                    if (it.isDualMode() && !it.isInPortrait) {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.second_container_id, ImageDetailsFragment(), null)
                            .commit()
                    } else {
                        val detailsFragment = ImageDetailsFragment()
                        supportFragmentManager
                            .beginTransaction()
                            .add(R.id.first_container_id, detailsFragment, detailsFragment.TAG)
                            .addToBackStack(detailsFragment.TAG)
                            .commit()
                    }
                }
            }
        )
    }

    private fun observeNavigationStack() {
        supportFragmentManager.addOnBackStackChangedListener {
            val backButtonEnabled = !supportFragmentManager.isBackStackEmpty
            supportActionBar?.setHomeButtonEnabled(backButtonEnabled)
            supportActionBar?.setDisplayHomeAsUpEnabled(backButtonEnabled)
        }
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        this.screenInfo = screenInfo
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
        supportFragmentManager.popBackStack()
        if (screenInfo.isDualMode() && !screenInfo.isInPortrait) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ImagesFragment(), null)
                .commit()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.second_container_id, ImageDetailsFragment(), null)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.first_container_id, ImagesFragment(), null)
                .commit()
        }
    }
}