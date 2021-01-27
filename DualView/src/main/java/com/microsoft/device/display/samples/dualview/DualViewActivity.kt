/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.dualview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.dualview.fragments.MapFragment
import com.microsoft.device.display.samples.dualview.fragments.RestaurantsFragment
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

/**
 * [AppCompatActivity] implementation that contains the restaurants screen and the map screen
 */
class DualViewActivity : AppCompatActivity(), ScreenInfoListener {
    companion object {
        private const val FRAGMENT_DUAL_START = "FragmentDualStart"
        private const val FRAGMENT_DUAL_END = "FragmentDualEnd"
        private const val FRAGMENT_SINGLE_SCREEN = "FragmentSingleScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dual_view_activity)
        setupToolbar()
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

    override fun onStart() {
        super.onStart()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            setupDualScreenFragments()
        } else {
            setupSingleScreenFragments()
        }
    }

    /**
     * Setup and adds fragments for single screen mode
     */
    private fun setupSingleScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, RestaurantsFragment(), FRAGMENT_SINGLE_SCREEN)
                .commit()
        }
    }

    /**
     * Setup and adds fragments for dual screen mode
     */
    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_START) == null &&
            supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_END) == null
        ) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, RestaurantsFragment(), FRAGMENT_DUAL_START)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.second_container_id, MapFragment(), FRAGMENT_DUAL_END)
                .commit()
        }
    }
}
