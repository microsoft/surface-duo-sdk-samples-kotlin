/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.samples.hingeangle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.samples.hingeangle.fragments.DualScreenFragment
import com.microsoft.device.display.samples.hingeangle.fragments.SingleScreenFragment
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class HingeAngleActivity : AppCompatActivity(), ScreenInfoListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hinge_angle_activity_main)
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

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        addFragments(screenInfo)
    }

    private fun addFragments(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            addFragmentsForDualScreen()
        } else {
            addFragmentsForSingleScreen()
        }
    }

    private fun addFragmentsForDualScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.first_container_id, SingleScreenFragment(), null)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.second_container_id, DualScreenFragment(), null)
            .commit()
    }

    private fun addFragmentsForSingleScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.first_container_id, SingleScreenFragment(), null)
            .commit()
    }
}
